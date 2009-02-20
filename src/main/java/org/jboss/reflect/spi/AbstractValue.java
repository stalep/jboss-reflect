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
package org.jboss.reflect.spi;

import java.io.Serializable;

import org.jboss.util.JBossObject;

/**
 * AbstractValue.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractValue extends JBossObject implements Value, Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -6618658144629045180L;

   public AnnotationValue asAnnotation()
   {
      if (isAnnotation() == false)
         throw new IllegalStateException("Not an annotation");
      return (AnnotationValue) this;
   }

   public ArrayValue asArray()
   {
      if (isArray() == false)
         throw new IllegalStateException("Not an array");
      return (ArrayValue) this;
   }

   public ClassValue asClass()
   {
      if (isClass() == false)
         throw new IllegalStateException("Not a class");
      return (ClassValue) this;
   }

   public EnumValue asEnum()
   {
      if (isEnum() == false)
         throw new IllegalStateException("Not an enum");
      return (EnumValue) this;
   }

   public PrimitiveValue asPrimitive()
   {
      if (isPrimitive() == false)
         throw new IllegalStateException("Not a primitive");
      return (PrimitiveValue) this;
   }

   public StringValue asString()
   {
      if (isString() == false)
         throw new IllegalStateException("Not a string");
      return (StringValue) this;
   }

   public boolean isAnnotation()
   {
      return false;
   }

   public boolean isArray()
   {
      return false;
   }

   public boolean isClass()
   {
      return false;
   }

   public boolean isEnum()
   {
      return false;
   }

   public boolean isPrimitive()
   {
      return false;
   }

   public boolean isString()
   {
      return false;
   }
}
