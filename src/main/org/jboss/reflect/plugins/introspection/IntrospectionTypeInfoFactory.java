/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins.introspection;

import java.lang.ref.SoftReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;

import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.ConstructorInfo;
import org.jboss.reflect.FieldInfo;
import org.jboss.reflect.InterfaceInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.TypeInfo;
import org.jboss.reflect.TypeInfoFactory;
import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.plugins.InterfaceInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.util.CollectionsFactory;

/**
 * An introspection type factory.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class IntrospectionTypeInfoFactory implements TypeInfoFactory
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The cache */
   protected Map cache = new WeakHashMap(); 

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Generate the type info for a class
    * 
    * @param clazz the class
    * @return the type info
    */
   public TypeInfo generateTypeInfo(Class clazz)
   {
      ClassInfoImpl result;
      if (clazz.isInterface())
         result = new InterfaceInfoImpl(clazz.getName());
      else
      {
         result = new ClassInfoImpl(clazz.getName());
         Class superClazz = clazz.getSuperclass();
         if (superClazz != null)
         {
            ClassInfoImpl superType = (ClassInfoImpl) getTypeInfo(superClazz);
            result.setSuperclass(superType);
            result.setDeclaredConstructors(getConstructors(clazz, result));
         }
      }
      result.setDeclaredFields(getFields(clazz, result));
      result.setDeclaredMethods(getMethods(clazz, result));
      result.setInterfaces(getInterfaces(clazz));
      
      return result;
   }
   
   /**
    * Get the constructors
    * 
    * @param clazz the class
    * @param declaring the declaring class
    * @return the constructor info
    */
   public ConstructorInfoImpl[] getConstructors(Class clazz, ClassInfo declaring)
   {
      Constructor[] constructors = clazz.getDeclaredConstructors();
      if (constructors == null || constructors.length == 0)
         return null;
      
      ConstructorInfoImpl[] infos = new ConstructorInfoImpl[constructors.length];
      for (int i = 0; i < constructors.length; ++i)
         infos[i] = new ReflectConstructorInfo(this, declaring, constructors[i]);
      return infos;
   }
   
   /**
    * Get the fields
    * 
    * @param clazz the class
    * @param declaring the declaring class
    * @return the field info
    */
   public FieldInfoImpl[] getFields(Class clazz, ClassInfo declaring)
   {
      Field[] fields = clazz.getDeclaredFields();
      if (fields == null || fields.length == 0)
         return null;
      
      FieldInfoImpl[] infos = new FieldInfoImpl[fields.length];
      for (int i = 0; i < fields.length; ++i)
         infos[i] = new ReflectFieldInfo(this, declaring, fields[i]);
      return infos;
   }
   
   /**
    * Get the methods
    * 
    * @param clazz the class
    * @param declaring the declaring class
    * @return the method info
    */
   protected MethodInfoImpl[] getMethods(Class clazz, ClassInfo declaring)
   {
      Method[] methods = clazz.getDeclaredMethods();
      if (methods == null || methods.length == 0)
         return null;
      
      MethodInfoImpl[] infos = new MethodInfoImpl[methods.length];
      for (int i = 0; i < methods.length; ++i)
         infos[i] = new ReflectMethodInfo(this, declaring, methods[i]);
      return infos;
   }
   
   /**
    * Get the interfaces
    * 
    * @param clazz the class
    * @return the interface info
    */
   public InterfaceInfo[] getInterfaces(Class clazz)
   {
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

   // TypeInfoFactory implementation --------------------------------

   public TypeInfo getTypeInfo(Class clazz)
   {
      Map classLoaderCache = getClassLoaderCache(clazz.getClassLoader());

      SoftReference soft = (SoftReference) classLoaderCache.get(clazz.getName());
      if (soft != null)
      {
         TypeInfo info = (TypeInfo) soft.get();
         if (info != null)
            return info;
      }
      
      TypeInfo result = generateTypeInfo(clazz);
      soft = new SoftReference(result);
      classLoaderCache.put(clazz.getName(), soft);
      return result;
   }
   
   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      Class clazz = cl.loadClass(name);
      return getTypeInfo(clazz);
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   /**
    * Get the cache for the classloader
    * 
    * @param cl the classloader
    * @return the map
    */
   protected Map getClassLoaderCache(ClassLoader cl)
   {
      synchronized (cache)
      {
         Map result = (Map) cache.get(cl);
         if (result == null)
         {
            result = CollectionsFactory.createMap();
            cache.put(cl, result);
         }
         return result;
      }
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
