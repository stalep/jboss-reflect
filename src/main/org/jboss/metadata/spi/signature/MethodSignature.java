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
package org.jboss.metadata.spi.signature;

import java.lang.reflect.Method;

/**
 * Method Signature.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodSignature extends Signature
{
   /**
    * Create a new Signature.
    * 
    * @param name the name
    * @param parameters the parameters
    */
   public MethodSignature(String name, String[] parameters)
   {
      super(name, parameters);
   }

   /**
    * Create a new Signature.
    * 
    * @param name the name
    * @param parameters the parameters
    */
   public MethodSignature(String name, Class... parameters)
   {
      super(name, parameters);
   }

   /**
    * Create a new Signature.
    * 
    * @param method the method
    */
   public MethodSignature(Method method)
   {
      super(method.getName(), method.getParameterTypes());
   }
}
