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
package org.jboss.joinpoint.spi;

import org.jboss.reflect.spi.MethodInfo;

/**
 * A method join point
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface MethodJoinpoint extends TargettedJoinpoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the method info
    * 
    * @return the method info
    */
   MethodInfo getMethodInfo();
   
   /**
    * Get the arguments of the join point
    * 
    * @return the arguments
    */
   Object[] getArguments();
   
   /**
    * Set the arguments of the method invocation
    * 
    * @param args the arguments
    */
   void setArguments(Object[] args);
   
   // Inner classes -------------------------------------------------
}
