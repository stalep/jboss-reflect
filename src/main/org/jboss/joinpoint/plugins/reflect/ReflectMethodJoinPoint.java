/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.InvocationTargetException;

import org.jboss.joinpoint.MethodJoinpoint;
import org.jboss.reflect.MethodInfo;

/**
 * A method joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectMethodJoinPoint extends ReflectTargettedJoinPoint implements MethodJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The method info */
   protected MethodInfo methodInfo;

   /** The arguments */
   protected Object[] arguments;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new method join point
    * 
    * @param methodInfo the methodInfo
    */
   public ReflectMethodJoinPoint(MethodInfo methodInfo)
   {
      this.methodInfo = methodInfo;
   }
   
   // Public --------------------------------------------------------
   
   // MethodJoinpoint implementation --------------------------------

   public MethodInfo getMethodInfo()
   {
      return methodInfo;
   }
   
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
         return methodInfo.getMethod().invoke(target, arguments);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
   }
   
   public String toHumanReadableString()
   {
      return methodInfo.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
