/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jboss.joinpoint.MethodJoinpoint;

/**
 * A method joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectMethodJoinPoint extends ReflectTargettedJoinPoint implements MethodJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The method */
   protected Method method;

   /** The arguments */
   protected Object[] arguments;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new method join point
    * 
    * @param method the method
    */
   public ReflectMethodJoinPoint(Method method)
   {
      this.method = method;
   }
   
   // Public --------------------------------------------------------
   
   // MethodJoinpoint implementation --------------------------------

   public Object[] getArguments()
   {
      return arguments;
   }

   public void setArguments(Object[] args)
   {
      this.arguments = args;
   }
   
   // Joinpoint implementation --------------------------------------

   public Object dispatch() throws Throwable
   {
      try
      {
         return method.invoke(target, arguments);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
   }
   
   public String toHumanReadableString()
   {
      return method.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
