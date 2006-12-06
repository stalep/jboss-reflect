/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.test.beaninfo.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jboss.beans.info.plugins.AbstractPropertyInfo;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.config.plugins.BasicConfiguration;
import org.jboss.config.spi.Configuration;
import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.classinfo.test.AbstractClassInfoTest;

/**
 * AbstractBeanInfoTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractBeanInfoTest extends AbstractClassInfoTest
{
   private Configuration configuration = new BasicConfiguration();
   
   public AbstractBeanInfoTest(String name)
   {
      super(name);
   }
   
   protected void assertBeanInfo(BeanInfo beanInfo, Class<?> clazz) throws Throwable
   {
      assertEquals(clazz.getName(), beanInfo.getName());
      ClassInfo classInfo = beanInfo.getClassInfo();
      assertClassInfo(classInfo, clazz);
      assertBeanConstructors(beanInfo, clazz);
      assertBeanMethods(beanInfo, clazz);
      assertBeanProperties(beanInfo, clazz);
   }
   
   protected void assertBeanConstructors(BeanInfo beanInfo, Class clazz)
   {
      ClassInfo classInfo = beanInfo.getClassInfo();
      
      TypeInfoFactory factory = getTypeInfoFactory();
      Set<ConstructorInfo> expected = new HashSet<ConstructorInfo>();
      for (Constructor constructor : clazz.getConstructors())
      {
         Class[] paramClasses = constructor.getParameterTypes();
         TypeInfo[] paramTypes = new TypeInfo[paramClasses.length];
         AnnotationValue[][] paramAnnotations = new AnnotationValue[paramClasses.length][0];
         int i = 0;
         for (Class c : paramClasses)
            paramTypes[i++] = factory.getTypeInfo(c);
         ConstructorInfo c = new ConstructorInfoImpl(null, paramTypes, paramAnnotations, null, constructor.getModifiers(), classInfo);
         expected.add(c);
      }
      
      Set<ConstructorInfo> actual = beanInfo.getConstructors();
      if (expected.isEmpty())
      {
         assertEmpty(actual);
         return;
      }
      assertNotNull(actual);
      assertEquals(expected.size(), actual.size());
      getLog().debug(clazz + " expected constructors=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertBeanMethods(BeanInfo beanInfo, Class<?> clazz) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Set<MethodInfo> expected = new HashSet<MethodInfo>();
      for (Method method : clazz.getMethods())
      {
         TypeInfo returnType = factory.getTypeInfo(method.getReturnType());
         Class[] paramClasses = method.getParameterTypes();
         TypeInfo[] paramTypes = new TypeInfo[paramClasses.length];
         AnnotationValue[][] paramAnnotations = new AnnotationValue[paramClasses.length][0];
         int i = 0;
         for (Class c : paramClasses)
            paramTypes[i++] = factory.getTypeInfo(c);
         ClassInfo classInfo = (ClassInfo) factory.getTypeInfo(method.getDeclaringClass());
         MethodInfo m = new MethodInfoImpl(null, method.getName(), returnType, paramTypes, paramAnnotations, null, method.getModifiers(), classInfo);
         expected.add(m);
      }
      
      Set<MethodInfo> actual = beanInfo.getMethods();
      if (expected.isEmpty())
      {
         assertEmpty(actual);
         return;
      }
      getLog().debug(clazz + " expected methods=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertBeanProperties(BeanInfo beanInfo, Class<?> clazz) throws Throwable
   {
      Set<PropertyInfo> expected = getExpectedProperties(clazz);
      
      Set<PropertyInfo> actual = beanInfo.getProperties();
      if (expected.isEmpty())
      {
         assertEmpty(actual);
         return;
      }
      getLog().debug(clazz + " expected properties=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
      
      HashMap<String, PropertyInfo> actualProps = new HashMap<String, PropertyInfo>();
      for (PropertyInfo prop : actual)
         actualProps.put(prop.getName(), prop);
      
      for (PropertyInfo propExpected : expected)
      {
         PropertyInfo propActual = actualProps.get(propExpected.getName());
         AnnotationValue[] annotationsExpected = propExpected.getAnnotations();
         AnnotationValue[] annotationsActual = propActual.getAnnotations();
         Set<AnnotationValue> expectedSet = new HashSet<AnnotationValue>();
         if (annotationsExpected != null)
         {
            for (AnnotationValue a : annotationsExpected)
               expectedSet.add(a);
         }
         Set<AnnotationValue> actualSet = new HashSet<AnnotationValue>();
         if (annotationsActual != null)
         {
            for (AnnotationValue a : annotationsActual)
               actualSet.add(a);
         }
         getLog().debug("Checking annotations for " + propExpected.getName() + " expected: " + expectedSet + " actual=" + actualSet);
         assertEquals(expectedSet, actualSet);
      }
   }

   protected Set<PropertyInfo> getExpectedProperties(Class clazz)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Method[] methods = clazz.getMethods();
      HashMap<String, Method> getters = new HashMap<String, Method>();
      HashMap<String, List<Method>> setters = new HashMap<String, List<Method>>();
      if (methods.length > 0)
      {
         for (Method method : methods)
         {
            String name = method.getName();
            String upperName = getUpperPropertyName(name);
            if (isGetter(method))
               getters.put(upperName, method);
            else if (isSetter(method))
            {
               List<Method> list = setters.get(upperName);
               if (list == null)
               {
                  list = new ArrayList<Method>();
                  setters.put(upperName, list);
               }
               list.add(method);
            }
         }
      }

      HashSet<PropertyInfo> properties = new HashSet<PropertyInfo>();
      if (getters.isEmpty() == false)
      {
         for (Iterator<Map.Entry<String, Method>> i = getters.entrySet().iterator(); i.hasNext();)
         {
            Map.Entry<String, Method> entry = i.next();
            String name = entry.getKey();
            Method getter = entry.getValue();
            Method setter = null;
            List<Method> setterList = setters.remove(name);
            if (setterList != null && setterList.size() != 0)
            {
               for (int j = 0; j < setterList.size(); ++j)
               {
                  Method thisSetter = setterList.get(j);
                  Type pinfo = thisSetter.getGenericParameterTypes()[0];
                  if (getter.getGenericReturnType().equals(pinfo) == true)
                  {
                     setter = thisSetter;
                     break;
                  }
               }
            }
            String lowerName = getLowerPropertyName(name);
            
            // Merge the annotations between the getters and setters
            Set<AnnotationValue> getterAnnotations = getExpectedAnnotations(getter.getAnnotations());
            Set<AnnotationValue> setterAnnotations = null;
            if (setter != null)
               setterAnnotations = getExpectedAnnotations(setter.getAnnotations());
            AnnotationValue[] annotations = getterAnnotations.toArray(new AnnotationValue[getterAnnotations.size()]);
            if (annotations == null || annotations.length == 0)
            {
               if (setterAnnotations != null)
                  annotations = setterAnnotations.toArray(new AnnotationValue[setterAnnotations.size()]);
            }
            else if (setterAnnotations != null && setterAnnotations.size() > 0)
            {
               HashSet<AnnotationValue> merged = new HashSet<AnnotationValue>();
               merged.addAll(getterAnnotations);
               merged.addAll(setterAnnotations);
               annotations = merged.toArray(new AnnotationValue[merged.size()]);
            }
            
            TypeInfo type = factory.getTypeInfo(getter.getGenericReturnType());
            ClassInfo declaringType = (ClassInfo) factory.getTypeInfo(getter.getDeclaringClass());
            MethodInfo getterInfo = new MethodInfoImpl(null, getter.getName(), type, new TypeInfo[0], null, null, getter.getModifiers(), declaringType);
            MethodInfo setterInfo = null;
            if (setter != null)
            {
               declaringType = (ClassInfo) factory.getTypeInfo(setter.getDeclaringClass());
               AnnotationValue[][] paramAnnotations = new AnnotationValue[1][];
               setterAnnotations = getExpectedAnnotations(setter.getParameterAnnotations()[0]); 
               paramAnnotations[0] = setterAnnotations.toArray(new AnnotationValue[setterAnnotations.size()]);
               setterInfo = new MethodInfoImpl(null, setter.getName(), PrimitiveInfo.VOID, new TypeInfo[] { type }, paramAnnotations, null, setter.getModifiers(), declaringType);
            }
            properties.add(new AbstractPropertyInfo(lowerName, name, type, getterInfo, setterInfo, annotations));
         }
      }
      if (setters.isEmpty() == false)
      {
         for (Iterator<Map.Entry<String, List<Method>>> i = setters.entrySet().iterator(); i.hasNext();)
         {
            Map.Entry<String, List<Method>> entry = i.next();
            String name = entry.getKey();
            List<Method> setterList = entry.getValue();
            // TODO JBMICROCONT-125 Maybe should just create duplicate propertyInfo and let the configurator guess?
            if (setterList.size() == 1)
            {
               Method setter = setterList.get(0);
               Type pinfo = setter.getGenericParameterTypes()[0];
               TypeInfo type = factory.getTypeInfo(pinfo);
               String lowerName = getLowerPropertyName(name);
               Set<AnnotationValue> setterAnnotations = getExpectedAnnotations(setter.getAnnotations()); 
               AnnotationValue[] annotations = setterAnnotations.toArray(new AnnotationValue[setterAnnotations.size()]);
               ClassInfo declaringType = (ClassInfo) factory.getTypeInfo(setter.getDeclaringClass());
               AnnotationValue[][] paramAnnotations = new AnnotationValue[1][];
               setterAnnotations = getExpectedAnnotations(setter.getParameterAnnotations()[0]); 
               paramAnnotations[0] = setterAnnotations.toArray(new AnnotationValue[setterAnnotations.size()]);
               MethodInfo setterInfo = new MethodInfoImpl(null, setter.getName(), PrimitiveInfo.VOID, new TypeInfo[] { type }, paramAnnotations, null, setter.getModifiers(), declaringType);
               properties.add(new AbstractPropertyInfo(lowerName, name, type, null, setterInfo, annotations));
            }
         }
      }
      return properties;
   }
   
   protected static String getUpperPropertyName(String name)
   {
      int start = 3;
      if (name.startsWith("is"))
         start = 2;
      
      return name.substring(start);
   }
   
   protected static String getLowerPropertyName(String name)
   {
      // If the second character is upper case then we don't make
      // the first character lower case
      if (name.length() > 1)
      {
         if (Character.isUpperCase(name.charAt(1)))
            return name;
      }

      StringBuilder buffer = new StringBuilder(name.length());
      buffer.append(Character.toLowerCase(name.charAt(0)));
      if (name.length() > 1)
         buffer.append(name.substring(1));
      return buffer.toString();
   }
   
   protected static boolean isGetter(Method method)
   {
      String name = method.getName();
      Class returnType = method.getReturnType();
      Class[] parameters = method.getParameterTypes();
      if ((name.length() > 3 && name.startsWith("get")) || (name.length() > 2 && name.startsWith("is")))
      {
         // isBoolean() is not a getter for java.lang.Boolean
         if (name.startsWith("is") && returnType.equals(Boolean.TYPE) == false)
            return false;
         if (parameters.length == 0 && Void.TYPE.equals(returnType) == false)
            return true;
      }
      return false;
   }
   
   protected static boolean isSetter(Method method)
   {
      String name = method.getName();
      Class returnType = method.getReturnType();
      Class[] parameters = method.getParameterTypes();
      if ((name.length() > 3 && name.startsWith("set")))
      {
         if (parameters.length == 1 && Void.TYPE.equals(returnType))
            return true;
      }
      return false;
   }

   protected TypeInfoFactory getTypeInfoFactory()
   {
      return configuration.getTypeInfoFactory();
   }
   
   protected BeanInfo getBeanInfo(Class clazz) throws Throwable
   {
      return configuration.getBeanInfo(clazz);
   }
   
   protected Configuration getConfiguration()
   {
      return configuration;
   }
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.beans");
   }
}
