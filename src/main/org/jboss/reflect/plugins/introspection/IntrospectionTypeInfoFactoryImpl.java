/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.reflect.plugins.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.jboss.reflect.plugins.AnnotationAttributeImpl;
import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.plugins.AnnotationInfoImpl;
import org.jboss.reflect.plugins.AnnotationValueFactory;
import org.jboss.reflect.plugins.ArrayInfoImpl;
import org.jboss.reflect.plugins.ClassInfoHelper;
import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.EnumInfoImpl;
import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.collection.WeakClassCache;

/**
 * An introspection type factory.
 *
 * FIXME: use lazy loading to avoid reading the entire class model
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class IntrospectionTypeInfoFactoryImpl extends WeakClassCache implements TypeInfoFactory, AnnotationHelper, ClassInfoHelper
{
   final static AnnotationValue[] NO_ANNOTATIONS = new AnnotationValue[0];

   /**
    * Generate the type info for a class
    *
    * @param clazz the class
    * @param info the class info
    */
   public void generateTypeInfo(Class clazz, ClassInfoImpl info)
   {
      // Everything is done lazily
   }

   public ClassInfoImpl getSuperClass(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      ClassInfoImpl superType = null;
      if (clazz.isInterface() == false)
      {
         Class superClazz = clazz.getSuperclass();
         if (superClazz != null)
            superType = (ClassInfoImpl) getTypeInfo(superClazz);
      }
      return superType;
   }

   public AnnotationValue[] getAnnotations(Object obj)
   {
      Annotation[] annotations;
      if (obj instanceof AccessibleObject)
      {
         annotations = readAnnotations((AccessibleObject)obj);
      }
      else if (obj instanceof Class)
      {
         annotations = readAnnotations((Class)obj);
      }
      else
      {
         throw new RuntimeException("Attempt was made to read annotations from unsupported type " + obj.getClass().getName() + ": " + obj);
      }

      if (annotations.length == 0)
      {
         return NO_ANNOTATIONS;
      }

      AnnotationValue[] annotationValues = new AnnotationValue[annotations.length];
      for (int i = 0 ; i < annotations.length ; i++)
      {
         AnnotationInfo info = (AnnotationInfo)getTypeInfo(annotations[i].annotationType());
         annotationValues[i] = createAnnotationValue(info, annotations[i]);
      }
      return annotationValues;
   }

   public AnnotationValue createAnnotationValue(AnnotationInfo info, Object ann)
   {
      return AnnotationValueFactory.createAnnotationValue(this, this, info, ann);
   }

   public ConstructorInfoImpl[] getConstructors(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      ReflectConstructorInfoImpl[] infos = null;
      if (clazz.isInterface() == false)
      {
         Constructor[] constructors = getDeclaredConstructors(clazz);
         if (constructors != null && constructors.length > 0)
         {
            infos = new ReflectConstructorInfoImpl[constructors.length];
            for (int i = 0; i < constructors.length; ++i)
            {
               AnnotationValue[] annotations = getAnnotations(constructors[i]);
               infos[i] = new ReflectConstructorInfoImpl(annotations, getTypeInfos(constructors[i].getParameterTypes()), getParameterAnnotations(constructors[i].getParameterAnnotations()), getClassInfos(constructors[i].getExceptionTypes()), constructors[i].getModifiers(), (ClassInfo) getTypeInfo(constructors[i].getDeclaringClass()));
               infos[i].setConstructor(constructors[i]);
            }
         }
      }
      return infos;
   }

   public FieldInfoImpl[] getFields(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Field[] fields = getDeclaredFields(clazz);
      if (fields == null || fields.length == 0)
         return null;

      ReflectFieldInfoImpl[] infos = new ReflectFieldInfoImpl[fields.length];
      for (int i = 0; i < fields.length; ++i)
      {
         AnnotationValue[] annotations = getAnnotations(fields[i]);
         infos[i] = new ReflectFieldInfoImpl(annotations, fields[i].getName(), getTypeInfo(fields[i].getType()), fields[i].getModifiers(), (ClassInfo) getTypeInfo(fields[i].getDeclaringClass()));
         infos[i].setField(fields[i]);
      }
      return infos;
   }

   public MethodInfoImpl[] getMethods(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Method[] methods = getDeclaredMethods(clazz);
      if (methods == null || methods.length == 0)
         return null;

      ReflectMethodInfoImpl[] infos = new ReflectMethodInfoImpl[methods.length];
      for (int i = 0; i < methods.length; ++i)
      {
         AnnotationValue[] annotations = getAnnotations(methods[i]);
         infos[i] = new ReflectMethodInfoImpl(annotations, methods[i].getName(), getTypeInfo(methods[i].getReturnType()), getTypeInfos(methods[i].getParameterTypes()), getParameterAnnotations(methods[i].getParameterAnnotations()), getClassInfos(methods[i].getExceptionTypes()), methods[i].getModifiers(), (ClassInfo) getTypeInfo(methods[i].getDeclaringClass()));
         infos[i].setMethod(methods[i]);
      }
      return infos;
   }

   public InterfaceInfo[] getInterfaces(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Class[] interfaces = clazz.getInterfaces();
      if (interfaces == null || interfaces.length == 0)
         return null;

      InterfaceInfo[] infos = new InterfaceInfo[interfaces.length];
      for (int i = 0; i < interfaces.length; ++i)
         infos[i] = (InterfaceInfo) getTypeInfo(interfaces[i]);

      return infos;
   }

   /**
    * Get the type infos for some classes
    *
    * @param classes the classes
    * @return the type infos
    */
   public TypeInfo[] getTypeInfos(Class[] classes)
   {
      if (classes == null || classes.length == 0)
         return null;

      TypeInfo[] result = new TypeInfo[classes.length];
      for (int i = 0; i < classes.length; ++i)
         result[i] = getTypeInfo(classes[i]);
      return result;
   }

   /**
    * Get the class infos for some classes
    *
    * @param classes the classes
    * @return the class infos
    */
   public ClassInfo[] getClassInfos(Class[] classes)
   {
      if (classes == null || classes.length == 0)
         return null;

      ClassInfo[] result = new ClassInfo[classes.length];
      for (int i = 0; i < classes.length; ++i)
         result[i] = (ClassInfo) getTypeInfo(classes[i]);
      return result;
   }

   public TypeInfo getTypeInfo(Class clazz)
   {
      if (clazz == null)
         throw new IllegalArgumentException("Null class");

      TypeInfo primitive = PrimitiveInfo.valueOf(clazz.getName());
      if (primitive != null)
         return primitive;

      return (TypeInfo) get(clazz);
   }

   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      if (name == null)
         throw new IllegalArgumentException("Null class name");
      if (cl == null)
         cl = Thread.currentThread().getContextClassLoader();

      Class clazz = cl.loadClass(name);
      return getTypeInfo(clazz);
   }

   protected Object instantiate(Class clazz)
   {
      ClassInfoImpl result;
      if (clazz.isArray())
      {
         TypeInfo componentType = getTypeInfo(clazz.getComponentType());
         result = new ArrayInfoImpl(componentType);
      }
      else if (clazz.isEnum())
      {
         result = new EnumInfoImpl(clazz.getName(), clazz.getModifiers());
      }
      else if (clazz.isAnnotation())
      {
         result = new AnnotationInfoImpl(clazz.getName(), clazz.getModifiers());
         Method[] methods = getDeclaredMethods(clazz);
         AnnotationAttributeImpl[] atttributes = new AnnotationAttributeImpl[methods.length];
         for (int i = 0 ; i < methods.length ; i++)
         {
            atttributes[i] = new AnnotationAttributeImpl(methods[i].getName(), getTypeInfo(methods[i].getReturnType()), null);
         }
         ((AnnotationInfoImpl)result).setAttributes(atttributes);
      }
      else if (clazz.isInterface())
      {
         result = new ReflectClassInfoImpl(clazz.getName());
      }
      else
      {
         result = new ReflectClassInfoImpl(clazz.getName());
      }
      result.setType(clazz);
      result.setClassInfoHelper(this);
      result.setAnnotationHelper(this);
      return result;
   }

   protected void generate(Class clazz, Object result)
   {
      generateTypeInfo(clazz, (ClassInfoImpl) result);
   }

   protected Constructor[] getDeclaredConstructors(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getDeclaredConstructors();
      else
      {
         PrivilegedAction<Constructor[]> action = new PrivilegedAction<Constructor[]>()
         {
            public Constructor[] run()
            {
               return clazz.getDeclaredConstructors();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }

   protected Field[] getDeclaredFields(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getDeclaredFields();
      else
      {
         PrivilegedAction<Field[]> action = new PrivilegedAction<Field[]>()
         {
            public Field[] run()
            {
               return clazz.getDeclaredFields();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }

   protected Method[] getDeclaredMethods(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getDeclaredMethods();
      else
      {
         PrivilegedAction<Method[]> action = new PrivilegedAction<Method[]>()
         {
            public Method[] run()
            {
               return clazz.getDeclaredMethods();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }

   private Annotation[] readAnnotations(final AccessibleObject ao)
   {
      if (System.getSecurityManager() == null)
         return ao.getAnnotations();
      else
      {
         PrivilegedAction<Annotation[]> action = new PrivilegedAction<Annotation[]>()
         {
            public Annotation[] run()
            {
               return ao.getAnnotations();
            }
         };
         
         return AccessController.doPrivileged(action);
      }
   }
   
   private Annotation[] readAnnotations(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getAnnotations();
      else
      {
         PrivilegedAction<Annotation[]> action = new PrivilegedAction<Annotation[]>()
         {
            public Annotation[] run()
            {
               return clazz.getAnnotations();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }
   

   protected AnnotationValue[][] getParameterAnnotations(Annotation[][] annotations)
   {
      AnnotationValue[][] annotationValues = new AnnotationValue[annotations.length][];
      for (int param = 0 ; param < annotations.length ; param++)
      {
         annotationValues[param] = new AnnotationValue[annotations[param].length];
         for (int ann = 0 ; ann < annotations[param].length ; ann++)
         {
            AnnotationInfo info = (AnnotationInfo)getTypeInfo(annotations[param][ann].annotationType());
            annotationValues[param][ann] = createAnnotationValue(info, annotations[param][ann]);
         }
      }
      return annotationValues;
   }

}
