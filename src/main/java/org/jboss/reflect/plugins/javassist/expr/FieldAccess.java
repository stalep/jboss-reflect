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
import org.jboss.reflect.spi.MutableFieldInfo;
import org.jboss.reflect.spi.NotFoundException;

/**
 * A FieldAccess.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface FieldAccess extends Expression
{

   /**
    * Returns true if the field is static.
    * 
    * @return
    */
   public boolean isStatic();
   
   /**
    * Returns true if the field is read.
    * 
    * @return
    */
   public boolean isReader();
   
   /**
    * Returns true if the field is written in.
    * 
    * @return
    */
   public boolean isWriter();
   
   /**
    * Returns the name of the class in which the field is declared.
    * 
    * @return
    */
   public String getClassName();
   
   /**
    * Returns the name of the field.
    * 
    * @return
    */
   public String getFieldName();
   
   /**
    * Returns the field accessed by this expression.
    * 
    * @return
    * @throws NotFoundException
    */
   public MutableFieldInfo getField() throws NotFoundException;
   
   /**
    * Returns the signature of the field type.
    * The signature is represented by a character string
    * called field descriptor, which is defined in the JVM specification.
    * 
    * @return
    */
   public String getSignature();
   
   /**
    * Replaces the method call with the bytecode derived from
    * the given source text.
    */
   public void replace(String statement) throws CannotCompileException;
   
}
