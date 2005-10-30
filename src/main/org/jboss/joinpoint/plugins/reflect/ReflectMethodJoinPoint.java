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
