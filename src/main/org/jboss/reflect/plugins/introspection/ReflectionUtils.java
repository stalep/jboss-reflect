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
package org.jboss.reflect.plugins.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.jboss.util.Strings;

/**
 * ReflectionUtils.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ReflectionUtils
{
   /**
    * Invoke on a method
    * 
    * @param method the method
    * @param target the target
    * @param arguments the arguments
    * @return the result of the invocation
    * @throws Throwable for any error
    */
   public static Object invoke(Method method, Object target, Object[] arguments) throws Throwable
   {
      if (method == null)
         throw new IllegalArgumentException("Null method");
      try
      {
         return method.invoke(target, arguments);
      }
      catch (Throwable t)
      {
         throw handleErrors(method.getName(), Strings.defaultToString(target), method.getParameterTypes(), arguments, t);
      }
   }

   /**
    * Create a new instance
    * 
    * @param clazz the class
    * @return the constructed object
    * @throws Throwable for any error
    */
   public static Object newInstance(Class clazz) throws Throwable
   {
      if (clazz == null)
         throw new IllegalArgumentException("Null clazz");
      try
      {
         return clazz.newInstance();
      }
      catch (Throwable t)
      {
         throw handleErrors("new", clazz.getName(), null, null, t);
      }
   }

   /**
    * Create a new instance
    * 
    * @param className the class name
    * @param cl the classloader
    * @return the constructed object
    * @throws Throwable for any error
    */
   public static Object newInstance(String className, ClassLoader cl) throws Throwable
   {
      if (className == null)
         throw new IllegalArgumentException("Null class name");
      if (cl == null)
         throw new IllegalArgumentException("Null classloader");
      Class clazz = cl.loadClass(className);
      try
      {
         return clazz.newInstance();
      }
      catch (Throwable t)
      {
         throw handleErrors("new", clazz.getName(), null, null, t);
      }
   }

   /**
    * Create a new instance
    * 
    * @param className the class name
    * @return the constructed object
    * @throws Throwable for any error
    */
   public static Object newInstance(String className) throws Throwable
   {
      ClassLoader cl = Thread.currentThread().getContextClassLoader();
      return newInstance(className, cl);
   }

   /**
    * Create a new instance
    * 
    * @param constructor the constructor
    * @param arguments the arguments
    * @return the constructed object
    * @throws Throwable for any error
    */
   public static Object newInstance(Constructor constructor, Object[] arguments) throws Throwable
   {
      if (constructor == null)
         throw new IllegalArgumentException("Null constructor");
      try
      {
         return constructor.newInstance(arguments);
      }
      catch (Throwable t)
      {
         throw handleErrors("new", constructor.getClass().getName(), constructor.getParameterTypes(), arguments, t);
      }
   }

   /**
    * Get a field
    * 
    * @param field the field
    * @param target the target
    * @return null
    * @throws Throwable for any error
    */
   public static Object getField(Field field, Object target) throws Throwable
   {
      if (field == null)
         throw new IllegalArgumentException("Null field");
      try
      {
         return field.get(target);
      }
      catch (Throwable t)
      {
         throw handleErrors("set", field, target, null, t);
      }
   }

   /**
    * Set a field
    * 
    * @param field the field
    * @param target the target
    * @param value the value
    * @return null
    * @throws Throwable for any error
    */
   public static Object setField(Field field, Object target, Object value) throws Throwable
   {
      if (field == null)
         throw new IllegalArgumentException("Null field");
      try
      {
         field.set(target, value);
         return null;
      }
      catch (Throwable t)
      {
         throw handleErrors("set", field, target, value, t);
      }
   }
   
   /**
    * Handle errors
    * 
    * @param context the context
    * @param target the target
    * @param parameters the parameters
    * @param arguments the arguments
    * @param t the error
    * @return never
    * @throws Throwable always
    */
   public static Throwable handleErrors(String context, Object target, Class[] parameters, Object[] arguments, Throwable t) throws Throwable
   {
      if (t instanceof IllegalArgumentException)
      {
         if (target == null)
            throw new IllegalArgumentException("Null target for " + context);
         ArrayList expected = new ArrayList();
         if (parameters != null)
         {
            for (int i = 0; i < parameters.length; ++i)
               expected.add(parameters[i].getName());
         }
         ArrayList actual = new ArrayList();
         if (arguments != null)
         {
            for (int i = 0; i < arguments.length; ++i)
            {
               if (arguments[i] == null)
                  actual.add(null);
               else
                  actual.add(arguments[i].getClass().getName());
            }
         }
         throw new IllegalArgumentException("Wrong arguments. " + context + " for target " + target + " expected=" + expected + " actual=" + actual);
      }
      else if (t instanceof InvocationTargetException)
      {
         throw ((InvocationTargetException) t).getTargetException();
      }
      throw t;
   }
   
   /**
    * Handle errors
    * 
    * @param context the context
    * @param field the field
    * @param target the target
    * @param value the value
    * @param t the error
    * @return never
    * @throws Throwable always
    */
   public static Throwable handleErrors(String context, Field field, Object target, Object value, Throwable t) throws Throwable
   {
      if (t instanceof IllegalArgumentException)
      {
         String valueType = null;
         if (value != null)
            valueType = value.getClass().getName();
         throw new IllegalArgumentException("Error invoking field " + context + " for target " + target + " field " + field.getName() + " expected=" + field.getType().getName() + " actual=" + valueType);
      }
      throw t;
   }
}
