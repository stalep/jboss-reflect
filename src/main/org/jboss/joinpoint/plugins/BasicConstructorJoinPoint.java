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
package org.jboss.joinpoint.plugins;

import org.jboss.joinpoint.spi.ConstructorJoinpoint;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.util.UnreachableStatementException;

/**
 * A constructor joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class BasicConstructorJoinPoint implements ConstructorJoinpoint
{
   /** The constructor info */
   protected ConstructorInfo constructorInfo;

   /** The arguments */
   protected Object[] arguments;

   /**
    * Create a new constructor join point
    * 
    * @param constructorInfo the constructor info
    */
   public BasicConstructorJoinPoint(ConstructorInfo constructorInfo)
   {
      this.constructorInfo = constructorInfo;
   }

   public ConstructorInfo getConstructorInfo()
   {
      return constructorInfo;
   }
   
   public Object[] getArguments()
   {
      return arguments;
   }

   public void setArguments(Object[] args)
   {
      this.arguments = args;
   }

   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         throw new UnreachableStatementException();
      }
   }

   public Object dispatch() throws Throwable
   {
      return constructorInfo.newInstance(arguments);
   }
   
   public String toHumanReadableString()
   {
      return constructorInfo.toString();
   }
}
