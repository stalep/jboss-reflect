/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;


/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class AnnotationValue implements Value
{
   protected AnnotationInfo annotationType;
   protected HashMap attributeValues;
   protected int hash = -1;

   public AnnotationValue(AnnotationInfo annotationType, HashMap attributeValues)
   {
      this.annotationType = annotationType;
      this.attributeValues = attributeValues;
      calculateHash();
   }

   public AnnotationInfo getAnnotationType()
   {
      return annotationType;
   }
   public TypeInfo getType()
   {
      return annotationType;
   }
   public Value getValue(String attributeName)
   {
      return (Value)attributeValues.get(attributeName);
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AnnotationValue)) return false;

      final AnnotationValue annotationValue = (AnnotationValue) o;

      if (!annotationType.equals(annotationValue.annotationType)) return false;
      if (!attributeValues.equals(annotationValue.attributeValues)) return false;

      return true;
   }

   public void calculateHash()
   {
      int result;
      result = annotationType.hashCode();
      result = 29 * result + attributeValues.hashCode();
      hash = result;
   }

   public int hashCode() { return hash; }
}
