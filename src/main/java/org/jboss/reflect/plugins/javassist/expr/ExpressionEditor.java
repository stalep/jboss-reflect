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

/**
 * A ExpressionEditor, a translator of method bodies.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface ExpressionEditor
{

   /**
    * Edits a <tt>new</tt> expression (overridable).
    * The default implementation performs nothing.
    * 
    * @param e
    * @throws CannotCompileException
    */
   public void edit(NewExpression e) throws CannotCompileException;
   
   /**
    * Edits an expression for array creation (overridable).
    * The default implementation performs nothing.
    * 
    * @param a
    * @throws CannotCompileException
    */
   public void edit(NewArray a) throws CannotCompileException;
   
   /**
    * Edits a method call (overridable).
    * 
    * @param m
    * @throws CannotCompileException
    */
   public void edit(MethodCall m) throws CannotCompileException;
   
   /**
    * Edits a constructor call (overridable).
    * The constructor call is either
    * <code>super()</code> or <code>this()</code>
    * included in a constructor body.
    * 
    * @param c
    * @throws CannotCompileException
    */
   public void edit(ConstructorCall c) throws CannotCompileException;
   
   /**
    * Edits a field-access expression (overridable).
    * Field access means both read and write.
    * 
    * @param f
    * @throws CannotCompileException
    */
   public void edit(FieldAccess f) throws CannotCompileException;
   
   /**
    * Edits an instanceof expression (overridable).
    * 
    * @param i
    * @throws CannotCompileException
    */
   public void edit(Instanceof i) throws CannotCompileException;
   
   /**
    * Edits an expression for explicit type casting (overridable).
    * 
    * @param c
    * @throws CannotCompileException
    */
   public void edit(Cast c) throws CannotCompileException;
   
   /**
    * Edits a catch clause (overridable).
    * 
    * @param h
    * @throws CannotCompileException
    */
   public void edit(Handler h) throws CannotCompileException;
}
