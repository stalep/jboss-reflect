/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.jboss.beans.info.plugins.AbstractAttributeInfo;
import org.jboss.beans.info.plugins.AbstractClassInfo;
import org.jboss.beans.info.plugins.AbstractConstructorInfo;
import org.jboss.beans.info.plugins.AbstractInterfaceInfo;
import org.jboss.beans.info.plugins.AbstractMethodInfo;
import org.jboss.beans.info.plugins.AbstractParameterInfo;
import org.jboss.beans.info.plugins.AbstractTypeInfo;
import org.jboss.beans.info.spi.ClassInfo;
import org.jboss.beans.info.spi.InterfaceInfo;
import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.beans.info.spi.ParameterInfo;
import org.jboss.beans.info.spi.TypeInfo;

/**
 * A repository for introspected objects
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class Introspection
{
   // Constants -----------------------------------------------------

   /** The introspected objects by class loader Map<ClassLoader, Introspection> */
   private static final Map introspections = Collections.synchronizedMap(new WeakHashMap());

   // Attributes ----------------------------------------------------

   /** The interface infos Map<Clazz, InterfaceInfo> */
   private static Map infos = Collections.synchronizedMap(new WeakHashMap());
   
   // Static --------------------------------------------------------

   /**
    * Generate the class info for a class
    * 
    * @param name the name of the class
    */
   public static TypeInfo getTypeInfo(String name)
   {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      Class clazz;
      try
      {
         clazz = ClassLoading.loadClass(cl, name);
      }
      catch (Throwable t)
      {
         throw new RuntimeException(t);
      }

      return getTypeInfo(clazz);
   }

   /**
    * Generate the class info for a class
    * 
    * @param clazz the class
    */
   protected static TypeInfo getTypeInfo(Class clazz)
   {
      // Get the introspection object for this classloader
      ClassLoader cl = clazz.getClassLoader();
      Introspection introspection;
      synchronized (introspections)
      {
         introspection = (Introspection) introspections.get(cl);
         if (introspection == null)
         {
            introspection = new Introspection();
            introspections.put(cl, introspection);
         }
      }
      return introspection.generateTypeInfo(clazz);
   }
   
   protected String getUpperAttributeName(String name)
   {
      int start = 3;
      if (name.startsWith("is"))
         start = 2;
      
      return name.substring(start);
   }
   
   protected String getLowerAttributeName(String name)
   {
      StringBuffer buffer = new StringBuffer(name.length());
      buffer.append(Character.toLowerCase(name.charAt(0)));
      if (name.length() > 1)
         buffer.append(name.substring(1));
      return buffer.toString();
   }
   
   // Constructors --------------------------------------------------
   
   /**
    * Not for external use
    */
   protected Introspection()
   {
   }
   
   // Public --------------------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Generate the class info for a class
    * 
    * @param clazz the class
    */
   protected TypeInfo generateTypeInfo(Class clazz)
   {
      AbstractTypeInfo info;
      synchronized (infos)
      {
         info = (AbstractTypeInfo) infos.get(clazz);
         if (info != null)
            return info;
         if (clazz.isInterface())
            info = new AbstractInterfaceInfo(clazz.getName());
         else
            info = new AbstractClassInfo(clazz.getName());
         info.setType(clazz);
         infos.put(clazz, info);
      }
      
      info.setSuperInterfaceInfo(getSuperInterfaces(clazz));
      Set methods = getMethods(clazz);
      info.setMethods(methods);
      info.setAttributes(getAttributes(methods));
      
      if (clazz.isInterface() == false)
      {
         ClassInfo classInfo = (ClassInfo) info;
         classInfo.setSuperClassInfo(getSuperClassInfo(clazz));
         classInfo.setConstructors(getConstructors(clazz));
      }
      return info;
   }

   /**
    * Get the super interface infos for a class
    * 
    * @param clazz the class
    * @return the interface infos
    */
   protected Set getSuperInterfaces(Class clazz)
   {
      HashSet superInterfaces = new HashSet();
      Class[] interfaces = clazz.getInterfaces();
      for (int i = 0; i < interfaces.length; ++i)
      {
         TypeInfo intf = getTypeInfo(interfaces[i]);
         superInterfaces.add(intf);
      }
      return superInterfaces;
   }
   
   /**
    * Get the methods for a class
    * 
    * @param clazz the class
    * @return the method infos
    */
   protected Set getMethods(Class clazz)
   {
      HashSet methodInfos = new HashSet();
      Method[] methods = clazz.getMethods();
      for (int i = 0; i < methods.length; ++i)
      {
         Method method = findDeclaringMethod(methods[i]);
         int modifier = method.getModifiers();
         TypeInfo returnType = getTypeInfo(method.getReturnType());
         ArrayList parameters = new ArrayList();
         Class[] params = method.getParameterTypes();
         for (int j = 0; j < params.length; ++j)
         {
            TypeInfo parameterType = getTypeInfo(params[j]);
            parameters.add(new AbstractParameterInfo("arg" + j, parameterType));
         }
         AbstractMethodInfo info = new AbstractMethodInfo(method.getName(), returnType, parameters, modifier);
         info.setMethod(method);
         methodInfos.add(info);
      }
      return methodInfos;
   }

   /**
    * Get the attributes for a class
    * 
    * @param methods the methods
    * @return the attribute infos
    */
   protected Set getAttributes(Set methods)
   {
      HashMap getters = new HashMap();
      HashMap setters = new HashMap();
      if (methods.isEmpty() == false)
      {
         for (Iterator i = methods.iterator(); i.hasNext();)
         {
            MethodInfo methodInfo = (MethodInfo) i.next();
            if (methodInfo.isPublic() && methodInfo.isStatic() == false)
            {
               String name = methodInfo.getName();
               if (methodInfo.isGetter())
                  getters.put(getUpperAttributeName(name), methodInfo);
               else if (methodInfo.isSetter())
                  setters.put(getUpperAttributeName(name), methodInfo);
            }
         }
      }

      HashSet attributes = new HashSet();
      if (getters.isEmpty() == false)
      {
         for (Iterator i = getters.entrySet().iterator(); i.hasNext();)
         {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            MethodInfo getter = (MethodInfo) entry.getValue();
            MethodInfo setter = (MethodInfo) setters.remove(name);
            if (setter != null)
            {
               ParameterInfo pinfo = (ParameterInfo) setter.getParameters().get(0);
               if (getter.getReturnType().equals(pinfo.getTypeInfo()) == false)
                  setter = null;
            }
            String lowerName = getLowerAttributeName(name);
            attributes.add(new AbstractAttributeInfo(lowerName, name, getter.getReturnType(), getter, setter));
         }
      }
      if (setters.isEmpty() == false)
      {
         for (Iterator i = setters.entrySet().iterator(); i.hasNext();)
         {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            MethodInfo setter = (MethodInfo) entry.getValue();
            ParameterInfo pinfo = (ParameterInfo) setter.getParameters().get(0);
            String lowerName = getLowerAttributeName(name);
            attributes.add(new AbstractAttributeInfo(lowerName, name, pinfo.getTypeInfo(), null, setter));
         }
      }
      return attributes;
   }

   /**
    * Get the super class info for a class
    * 
    * @param clazz the class
    * @return the super class info
    */
   protected ClassInfo getSuperClassInfo(Class clazz)
   {
      if (clazz.getSuperclass() != null)
         return (ClassInfo) getTypeInfo(clazz.getSuperclass());
      return null;
   }

   /**
    * Get the constructors for a class
    * 
    * @param clazz the class
    * @return the constructor infos
    */
   protected Set getConstructors(Class clazz)
   {
      HashSet constructorInfos = new HashSet();
      Constructor[] constructors = clazz.getConstructors();
      for (int i = 0; i < constructors.length; ++i)
      {
         int modifier = constructors[i].getModifiers();
         ArrayList parameters = new ArrayList();
         Class[] params = constructors[i].getParameterTypes();
         for (int j = 0; j < params.length; ++j)
         {
            TypeInfo parameterType = getTypeInfo(params[j]);
            parameters.add(new AbstractParameterInfo("arg" + j, parameterType));
         }
         AbstractConstructorInfo info = new AbstractConstructorInfo(parameters, modifier);
         info.setConstructor(constructors[i]);
         constructorInfos.add(info);
      }
      return constructorInfos;
   }

   /**
    * Find real declaring method
    * 
    * @param method the method we are given
    * @return the real declaring method
    */
   protected Method findDeclaringMethod(Method method)
   {
      Method result = method;
      TypeInfo type = getTypeInfo(method.getDeclaringClass());
      Method matched = matchMethod(type, method);
      if (matched != null)
         result = matched;
      return result;
   }
   
   /**
    * Match a method against the highest super interface
    * 
    * @param type the current type info
    * @param method the method to check
    * @return any matched method or null if not found
    */
   protected Method matchMethod(TypeInfo type, Method method)
   {
      Set intfs = type.getSuperInterfaceInfo();
      if (intfs != null && intfs.isEmpty() == false)
      {
         for (Iterator i = intfs.iterator(); i.hasNext();)
         {
            InterfaceInfo info = (InterfaceInfo) i.next();
            Method matched = matchMethod(info, method);
            if (matched != null)
               return matched;
         }
      }
      
      Class[] parameters = method.getParameterTypes();
      
      Set methods = type.getMethods();
      if (methods != null && methods.isEmpty() == false)
      {
         for (Iterator i = methods.iterator(); i.hasNext();)
         {
            MethodInfo minfo = (MethodInfo) i.next();
            if (method.getName().equals(minfo.getName()))
            {
               Class[] params = minfo.getMethod().getParameterTypes();
               if (Arrays.equals(parameters, params))
                  return minfo.getMethod();
            }
         }
      }
      return null;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
