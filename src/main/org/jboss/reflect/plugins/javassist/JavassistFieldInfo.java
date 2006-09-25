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
package org.jboss.reflect.plugins.javassist;

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

import javassist.CtClass;
import javassist.CtField;
import javassist.Modifier;
import javassist.NotFoundException;

/**
 * JavassistFieldInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistFieldInfo extends JavassistAnnotatedInfo implements FieldInfo
{
   /** The reflection factory */
   private static final JavassistReflectionFactory reflectionFactory = new JavassistReflectionFactory(true);

   /** The field */
   private CtField ctField;
   
   /** The field implementation */
   private transient JavassistField field;

   /** The type */
   private transient TypeInfo fieldType;
   
   /** The type info */
   protected JavassistTypeInfo typeInfo;

   /**
    * Create a new JavassistFieldInfo.
    * 
    * @param annotationHelper the annotation helper
    * @param typeInfo the type info
    * @param ctField the field
    */
   public JavassistFieldInfo(AnnotationHelper annotationHelper, JavassistTypeInfo typeInfo, CtField ctField)
   {
      super(annotationHelper);
      this.typeInfo = typeInfo;
      this.ctField = ctField;
   }

   public String getName()
   {
      return ctField.getName();
   }

   public int getModifiers()
   {
      return ctField.getModifiers();
   }

   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   public ClassInfo getDeclaringClass()
   {
      return typeInfo;
   }

   public TypeInfo getType()
   {
      if (fieldType != null)
         return fieldType;
      try
      {
         CtClass clazz = ctField.getType();
         fieldType = typeInfo.getFactory().getTypeInfo(clazz);
         return fieldType;
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseFieldNotFound(getName(), e);
      }
   }

   public Object get(Object target) throws Throwable
   {
      if (field == null)
         field = reflectionFactory.createField(ctField);
      return field.get(target);
   }

   public Object set(Object target, Object value) throws Throwable
   {
      if (field == null)
         field = reflectionFactory.createField(ctField);
      field.set(target, value);
      return null;
   }

   protected int getHashCode()
   {
      return getName().hashCode();
   }

   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof FieldInfo == false)
         return false;
      
      FieldInfo other = (FieldInfo) obj;
      if (getName().equals(other.getName()) == false)
         return false;
      return getDeclaringClass().equals(other.getDeclaringClass());
   }

   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(getName());
   }

   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(getName());
      super.toString(buffer);
   }
   
   public AnnotationValue[] getAnnotations()
   {
      return getAnnotations(ctField);
   }
}
