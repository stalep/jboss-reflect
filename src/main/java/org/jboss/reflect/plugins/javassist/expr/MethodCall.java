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
package org.jboss.reflect.plugins.javassist.expr;

import org.jboss.reflect.spi.CannotCompileException;
import org.jboss.reflect.spi.MutableMethodInfo;
import org.jboss.reflect.spi.NotFoundException;

/**
 * A MethodCall.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface MethodCall extends Expression
{

   /**
    * Returns the class name of the target object,
     * which the method is called on.
    * 
    * @return
    */
   public String getClassName();
   
   /**
    * Returns the name of the called method. 
    * 
    * @return
    */
   public String getMethodName();
   
   /**
    * Get the called method
    * 
    * @return
    * @throws NotFoundException
    */
   public MutableMethodInfo getMethod() throws NotFoundException;
   
   
   /**
    * Return the method signature
    * 
    * @return
    */
   public String getSignatue();
   
   /**
    * Returns true if the called method is of a superclass of the current
     * class.
    * 
    * @return
    */
   public boolean isSuper();
   
   /**
    * Replaces the method call with the bytecode derived from
    * the given source text.
    */
   public void replace(String statement) throws CannotCompileException;
   
   
}
