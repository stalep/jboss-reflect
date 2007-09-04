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
package org.jboss.reflect.plugins.introspection;

import java.lang.reflect.Field;

import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * A field info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldInfoImpl extends FieldInfoImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 2;

   /** The field */
   protected transient Field field;

   /**
    * Create a new field info
    */
   public ReflectFieldInfoImpl()
   {
   }

   /**
    * Create a new FieldInfo.
    * 
    * @param annotations the annotations
    * @param name the name
    * @param type the field type
    * @param modifiers the field modifiers
    * @param declaring the declaring class
    */
   public ReflectFieldInfoImpl(AnnotationValue[] annotations, String name, TypeInfo type, int modifiers, ClassInfo declaring)
   {
      super(annotations, name, type, modifiers, declaring);
   }

   /**
    * Set the field
    * 
    * @param field the field
    */
   public void setField(Field field)
   {
      this.field = field;
   }

   /**
    * Get the field
    * 
    * @return the field
    */
   public Field getField()
   {
      return field;
   }

   public Object get(Object target) throws Throwable
   {
      return ReflectionUtils.getField(field, target);
   }

   public Object set(Object target, Object value) throws Throwable
   {
      return ReflectionUtils.setField(field, target, value);
   }
}
