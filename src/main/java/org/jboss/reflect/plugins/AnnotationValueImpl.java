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

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import org.jboss.reflect.spi.AbstractValue;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.Value;
import org.jboss.util.JBossStringBuilder;

/**
 * An annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationValueImpl extends AbstractValue implements AnnotationValue
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3257290210164289843L;

   /** The annotation type */
   protected AnnotationInfo annotationType;
   
   /** The attribute values */
   protected HashMap<String, Value> attributeValues;
   
   /** The underlying annotation */
   protected Annotation underlying;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new Annotation value
    */
   public AnnotationValueImpl()
   {
   }

   /**
    * Create a new Annotation value
    * 
    * @param annotationType the annotation info
    * @param attributeValues the attribute values
    */
   @Deprecated
   public AnnotationValueImpl(AnnotationInfo annotationType, HashMap<String, Value> attributeValues)
   {
      this(annotationType, attributeValues, null);
   }

   /**
    * Create a new Annotation value
    * 
    * @param annotationType the annotation info
    * @param attributeValues the attribute values
    * @param underlying the underlying annotation
    */
   public AnnotationValueImpl(AnnotationInfo annotationType, HashMap<String, Value> attributeValues, Annotation underlying)
   {
      if (annotationType == null)
         throw new IllegalArgumentException("Null annotationType");
      if (attributeValues == null)
         throw new IllegalArgumentException("Null attribute values");

      this.annotationType = annotationType;
      this.attributeValues = attributeValues;
      this.underlying = underlying;
      calculateHash();
   }

   @Override
   public boolean isAnnotation()
   {
      return true;
   }
   
   public AnnotationInfo getAnnotationType()
   {
      return annotationType;
   }
   
   public Value getValue(String attributeName)
   {
      return attributeValues.get(attributeName);
   }

   public Map<String, Value> getValues()
   {
      return attributeValues;
   }

   public TypeInfo getType()
   {
      return annotationType;
   }

   public Annotation getUnderlyingAnnotation()
   {
      return underlying;
   }

   public <T extends Annotation> T getUnderlyingAnnotation(Class<T> type)
   {
      return type.cast(underlying);
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (o == null || !(o instanceof AnnotationValue)) return false;

      final AnnotationValue annotationValue = (AnnotationValue) o;

      if (!annotationType.equals(annotationValue.getAnnotationType())) return false;
      if (!attributeValues.equals(annotationValue.getValues())) return false;

      Annotation otherUnderlying = annotationValue.getUnderlyingAnnotation();
      if (underlying == null && otherUnderlying != null)
         return false;
      if (underlying != null && otherUnderlying == null)
         return false;
      return underlying.equals(otherUnderlying);
   }

   @Override
   public int hashCode()
   {
      return hash;
   }

   /**
    * Calculate the hashcode
    */
   protected void calculateHash()
   {
      int result;
      result = (annotationType != null) ? annotationType.hashCode() : 0;
      result = 29 * result + attributeValues.hashCode();
      hash = result;
   }

   @Override
   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(annotationType.getName());
   }

   @Override
   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(annotationType.getName());
      if (attributeValues != null && attributeValues.size() > 0)
         buffer.append(" values=").append(attributeValues);
   }
}
