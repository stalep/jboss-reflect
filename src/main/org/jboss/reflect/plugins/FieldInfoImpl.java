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
package org.jboss.reflect.plugins;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * A field info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class FieldInfoImpl extends AnnotationHolder implements FieldInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3546084661584539959L;

   /** The field name */
   protected String name;
   
   /** The field */
   protected Field field;
   
   /** The field type */
   protected TypeInfo type;
   
   /** The field modifier */
   protected int modifiers;
   
   /** The declaring class */
   protected ClassInfo declaringClass;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new field info
    */
   public FieldInfoImpl()
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
   public FieldInfoImpl(AnnotationValue[] annotations, String name, TypeInfo type, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      this.type = type;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      calculateHash();
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

   public String getName()
   {
      return name;
   }
   
   public Field getField()
   {
      return field;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }
   
   public int getModifiers()
   {
      return modifiers;
   }
   
   public boolean isStatic()
   {
      return Modifier.isStatic(modifiers);
   }
   
   public boolean isPublic()
   {
      return Modifier.isPublic(modifiers);
   }
   
   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(name);
   }

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null || obj instanceof FieldInfoImpl == false) 
         return false;

      final FieldInfoImpl other = (FieldInfoImpl) obj;

      if (!declaringClass.equals(other.declaringClass))
         return false;
      if (!name.equals(other.name))
         return false;

      return true;
   }

   public int hashCode()
   {
      return hash;
   }

   /**
    * Calculate the hash code
    */
   protected void calculateHash()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + declaringClass.hashCode();
      hash = result;
   }
}
