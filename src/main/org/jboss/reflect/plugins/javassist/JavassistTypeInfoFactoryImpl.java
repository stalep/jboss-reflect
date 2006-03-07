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
package org.jboss.reflect.plugins.javassist;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;

import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.JBossStringBuilder;
import org.jboss.util.collection.WeakClassCache;

/**
 * A javassist type factory.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class JavassistTypeInfoFactoryImpl extends WeakClassCache implements TypeInfoFactory
{
   static final ClassPool pool = ClassPool.getDefault();

   /**
    * Raise NoClassDefFoundError for javassist not found
    * 
    * @param name the name
    * @param e the not found error
    * @return never
    * @throws NoClassDefFoundError always 
    */
   public static NoClassDefFoundError raiseClassNotFound(String name, NotFoundException e) throws NoClassDefFoundError
   {
      NoClassDefFoundError ex = new NoClassDefFoundError("Unable to find class");
      if (e.getCause() != null)
         ex.initCause(e.getCause()); // Hide the javassist error
      throw ex;
   }

   /**
    * Raise NoClassDefFoundError for javassist not found
    * 
    * @param name the name
    * @param e the not found error
    * @return never
    * @throws NoClassDefFoundError always 
    */
   public static NoClassDefFoundError raiseClassNotFound(String name, ClassNotFoundException e) throws NoClassDefFoundError
   {
      NoClassDefFoundError ex = new NoClassDefFoundError("Unable to find class");
      ex.initCause(e);
      throw ex;
   }

   /**
    * Raise NoSuchMethodError for javassist not found
    * 
    * @param name the name
    * @param e the not found error
    * @return never
    * @throws NoSuchMethodError always 
    */
   public static NoClassDefFoundError raiseMethodNotFound(String name, NotFoundException e) throws NoClassDefFoundError
   {
      NoSuchMethodError ex = new NoSuchMethodError("Unable to find method " + name);
      if (e.getCause() != null)
         ex.initCause(e.getCause()); // Hide the javassist error
      throw ex;
   }

   /**
    * Raise NoSuchFieldError for javassist not found
    * 
    * @param name the name
    * @param e the not found error
    * @return never
    * @throws NoSuchFieldError always 
    */
   public static NoClassDefFoundError raiseFieldNotFound(String name, NotFoundException e) throws NoClassDefFoundError
   {
      NoSuchFieldError ex = new NoSuchFieldError("Unable to find field");
      if (e.getCause() != null)
         ex.initCause(e.getCause()); // Hide the javassist error
      throw ex;
   }

   protected Object instantiate(Class clazz)
   {
      CtClass ctClass = getCtClass(clazz.getName());
      return new JavassistTypeInfo(this, ctClass, clazz);
   }

   /**
    * Get the type info
    * 
    * @param ctClass the ctClass
    * @return the typeinfo
    */
   protected TypeInfo getTypeInfo(CtClass ctClass)
   {
      try
      {
         String name = convertName(ctClass);
         return getTypeInfo(name, null);
      }
      catch (ClassNotFoundException e)
      {
         throw raiseClassNotFound(ctClass.getName(), e);
      }
   }

   /**
    * Convert name
    * 
    * @param clazz the class
    * @return the converted name
    */
   protected String convertName(CtClass clazz)
   {
      CtClass temp = clazz;
      if (temp.isArray())
      {
         JBossStringBuilder buffer = new JBossStringBuilder();
         try
         {
            while (temp.isArray())
            {
               buffer.append('[');
               temp = temp.getComponentType();
            }
            if (temp.isPrimitive())
            {
               CtPrimitiveType primitive = (CtPrimitiveType) temp;
               buffer.append(Character.toString(primitive.getDescriptor()));
            }
            else
            {
               buffer.append('L');
               buffer.append(temp.getName());
               buffer.append(';');
            }
            return buffer.toString();
         }
         catch (NotFoundException e)
         {
            throw raiseClassNotFound(clazz.getName(), e);
         }
      }
      return clazz.getName();
   }

   /**
    * Get the CtClass
    * 
    * @param name the name
    * @return the CtClass
    */
   protected CtClass getCtClass(String name)
   {
      try
      {
         return pool.get(name);
      }
      catch (NotFoundException e)
      {
         throw raiseClassNotFound(name, e);
      }
   }

   protected void generate(Class clazz, Object result)
   {
      // Everything is done lazily
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

      TypeInfo primitive = PrimitiveInfo.valueOf(name);
      if (primitive != null)
         return primitive;

      Class clazz = cl.loadClass(name);
      return getTypeInfo(clazz);
   }
}
