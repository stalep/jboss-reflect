/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * An annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationValue implements Value
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The annotation type */
   protected AnnotationInfo annotationType;
   
   /** The attribute values */
   protected HashMap attributeValues;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new Annotation value
    */
   public AnnotationValue()
   {
   }

   /**
    * Create a new Annotation value
    * 
    * @param annotationType the annotation info
    * @param attributeValues the attribute values
    */
   public AnnotationValue(AnnotationInfo annotationType, HashMap attributeValues)
   {
      this.annotationType = annotationType;
      this.attributeValues = attributeValues;
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Get the annotation type
    * 
    * @return the annotation type
    */
   public AnnotationInfo getAnnotationType()
   {
      return annotationType;
   }
   
   /**
    * Get an attribute value
    * 
    * @param attributeName the attribute name
    * @return the value
    */
   public Value getValue(String attributeName)
   {
      return (Value) attributeValues.get(attributeName);
   }

   // Value implementation ------------------------------------------

   public TypeInfo getType()
   {
      return annotationType;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AnnotationValue)) return false;

      final AnnotationValue annotationValue = (AnnotationValue) o;

      if (!annotationType.equals(annotationValue.annotationType)) return false;
      if (!attributeValues.equals(annotationValue.attributeValues)) return false;

      return true;
   }

   public int hashCode()
   {
      return hash;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Calculate the hashcode
    */
   protected void calculateHash()
   {
      int result;
      result = annotationType.hashCode();
      result = 29 * result + attributeValues.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
