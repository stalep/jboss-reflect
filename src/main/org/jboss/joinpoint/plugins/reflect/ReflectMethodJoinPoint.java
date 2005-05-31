/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.Method;

import org.jboss.joinpoint.spi.MethodJoinpoint;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.util.UnreachableStatementException;

/**
 * A method joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectMethodJoinPoint extends ReflectTargettedJoinPoint implements MethodJoinpoint
{
   /** The method info */
   protected MethodInfo methodInfo;

   /** The arguments */
   protected Object[] arguments;
   /**
    * Create a new method join point
    * 
    * @param methodInfo the methodInfo
    */
   public ReflectMethodJoinPoint(MethodInfo methodInfo)
   {
      this.methodInfo = methodInfo;
   }

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

   public Object dispatch() throws Throwable
   {
      Method method = methodInfo.getMethod();
      try
      {
         return method.invoke(target, arguments);
      }
      catch (Throwable t)
      {
         ReflectJoinpointFactory.handleErrors(method.getName(), method.getParameterTypes(), arguments, t);
         throw new UnreachableStatementException();
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
