/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.joinpoint.ConstructorJoinpoint;
import org.jboss.joinpoint.FieldGetJoinpoint;
import org.jboss.joinpoint.FieldSetJoinpoint;
import org.jboss.joinpoint.JoinpointException;
import org.jboss.joinpoint.MethodJoinpoint;

/**
 * A join point factory based on reflection
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectJoinpointFactory
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The class */
   protected Class clazz;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   public ReflectJoinpointFactory(Class clazz)
   {
      this.clazz = clazz;
   }
   
   // Public --------------------------------------------------------

   public ConstructorJoinpoint getConstructorJoinpoint(Class[] argumentTypes) throws JoinpointException
   {
      try
      {
         Constructor constructor = clazz.getConstructor(argumentTypes);
         return new ReflectConstructorJoinPoint(constructor);
      }
      catch (NoSuchMethodException e)
      {
         throw new JoinpointException("Constructor joinpoint not found", e);
      }
   }

   public FieldGetJoinpoint getFieldGetJoinpoint(String field) throws JoinpointException
   {
      try
      {
         Field f = clazz.getField(field);
         return new ReflectFieldGetJoinPoint(f);
      }
      catch (NoSuchFieldException e)
      {
         throw new JoinpointException("Field joinpoint not found", e);
      }
   }

   public FieldSetJoinpoint getFieldSetJoinpoint(String field) throws JoinpointException
   {
      try
      {
         Field f = clazz.getField(field);
         return new ReflectFieldSetJoinPoint(f);
      }
      catch (NoSuchFieldException e)
      {
         throw new JoinpointException("Field joinpoint not found", e);
      }
   }

   public MethodJoinpoint getMethodJoinpoint(String name, Class[] argumentTypes, boolean isStatic) throws JoinpointException
   {
      try
      {
         Method method = clazz.getMethod(name, argumentTypes);
         return new ReflectMethodJoinPoint(method);
      }
      catch (NoSuchMethodException e)
      {
         throw new JoinpointException("Method joinpoint not found", e);
      }
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
