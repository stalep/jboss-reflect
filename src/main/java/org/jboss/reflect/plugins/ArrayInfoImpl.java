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

import java.lang.reflect.UndeclaredThrowableException;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * Array information
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ArrayInfoImpl extends ClassInfoImpl implements ArrayInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3905804162787980599L;

   /** The component type */
   protected TypeInfo componentType;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new ArrayInfo.
    */
   public ArrayInfoImpl()
   {
   }

   /**
    * Create a new ArrayInfo.
    * 
    * @param componentType the component type
    */
   public ArrayInfoImpl(TypeInfo componentType)
   {
      if (componentType == null)
         throw new IllegalArgumentException("Null component type.");

      this.componentType = componentType;
      StringBuilder builder = new StringBuilder();
      builder.append("[");
      TypeInfo temp = componentType;
      while (temp.isArray())
      {
         builder.append("[");
         temp = ((ArrayInfo) temp).getComponentType();
      }
      if (PrimitiveInfo.class.equals(temp.getClass()))
      {
         String encodedName = PrimitiveInfo.getPrimativeArrayType(temp.getName());
         builder.append(encodedName);
      }
      else
      {
         builder.append("L").append(temp.getName()).append(";");
      }
      name = builder.toString();
      calculateHash();
   }

   @Deprecated
   @SuppressWarnings("unchecked")
   public Class<? extends Object> getType()
   {
      if (annotatedElement == null)
      {
         try
         {
            TypeInfoFactory tif = SerializationHelper.getTypeInfoFactory();
            annotatedElement = tif.getTypeInfo(name, componentType.getType().getClassLoader()).getType();
         }
         catch (Throwable t)
         {
            throw new UndeclaredThrowableException(t);
         }
      }
      return (Class<? extends Object>)annotatedElement;
   }

   public TypeInfo getComponentType()
   {
      return componentType;
   }
   
   public AnnotationValue getAnnotation(String name)
   {
      return null;
   }

   public AnnotationValue[] getAnnotations()
   {
      return UNKNOWN_ANNOTATIONS;
   }

   public boolean isAnnotationPresent(String name)
   {
      return false;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ArrayInfo)) return false;
      if (!super.equals(o)) return false;

      final ArrayInfo arrayInfo = (ArrayInfo) o;

      if (!componentType.equals(arrayInfo.getComponentType())) return false;

      return true;
   }

   public int hashCode() { return hash; }

   /**
    * Calculate the hash code
    */
   protected void calculateHash()
   {
      int result = super.hashCode();
      result = 29 * result + componentType.hashCode();
      hash = result;
   }
}
