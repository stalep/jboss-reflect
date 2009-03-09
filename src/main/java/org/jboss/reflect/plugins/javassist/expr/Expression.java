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
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableConstructorInfo;
import org.jboss.reflect.spi.MutableMethodInfo;

/**
 * A Expression.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface Expression
{
   /**
    * Returns the method containing the <tt>new</tt>
    * expression represented by this object.
    * 
    * @return
    */
   public MutableMethodInfo whereMethod();
   
   /**
    * Returns the constructor containing the <tt>new</tt>
    * expression represented by this object.
    * 
    * @return
    */
   public MutableConstructorInfo whereConstructor();

   /**
    * FIXME Comment this
    * 
    * @return
    */
   public MutableClassInfo[] mayThrow();
   
   /**
    * FIXME Comment this
    * 
    * @return
    */
   public int indexOfBytecode();
   
   /**
    * Returns the line number of the source line containing the
     * <tt>new</tt> expression.
    * 
    * @return
    */
   public int getLineNumber();
   
   /**
    * Returns the source file containing the <tt>new</tt> expression.
    * 
    * @return
    */
   public String getFileName();
   
   public abstract void replace(String statement) throws CannotCompileException;
   
   public void replace(String statement, ExpressionEditor recursive) throws CannotCompileException;
   
   
}
