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
package org.jboss.reflect.plugins.javassist;

import javassist.CtClass;

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Javassist array info
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class JavassistArrayInfoImpl extends JavassistTypeInfo implements ArrayInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 9195834689976459024L;
   
   /** Unknown annotations */
   static final AnnotationValue[] UNKNOWN_ANNOTATIONS = new AnnotationValue[0];

   /** The component type */
   protected TypeInfo componentType;
   
   /** The hash code */
   protected int hash = -1;

   private static String getName(TypeInfo componentType)
   {
      StringBuilder builder = new StringBuilder();
      builder.append("[");
      TypeInfo temp = componentType;
      while (temp.isArray())
      {
         builder.append("[");
         temp = ((JavassistArrayInfoImpl) temp).componentType;
      }
      builder.append("L").append(temp.getName()).append(";");
      return builder.toString();
   }
   
   /**
    * Create a new JavassistArrayInfoImpl.
    * 
    * @param factory the factory
    * @param ctClass the ctClass
    * @param clazz the class
    */
   JavassistArrayInfoImpl(JavassistTypeInfoFactoryImpl factory, CtClass ctClass, Class<? extends Object> clazz, TypeInfo componentType)
   {
      super(factory, getName(componentType), ctClass, clazz);
      this.componentType = componentType;
      calculateHash();
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
