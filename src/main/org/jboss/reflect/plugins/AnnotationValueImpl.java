/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.io.Serializable;
import java.util.HashMap;

import org.jboss.reflect.AnnotationInfo;
import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.TypeInfo;
import org.jboss.reflect.Value;
import org.jboss.util.JBossObject;

/**
 * An annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationValueImpl extends JBossObject implements AnnotationValue, Serializable
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3257290210164289843L;
   
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
   public AnnotationValueImpl()
   {
   }

   /**
    * Create a new Annotation value
    * 
    * @param annotationType the annotation info
    * @param attributeValues the attribute values
    */
   public AnnotationValueImpl(AnnotationInfo annotationType, HashMap attributeValues)
   {
      this.annotationType = annotationType;
      this.attributeValues = attributeValues;
      calculateHash();
   }

   // Public --------------------------------------------------------

   // AnnotationValue implementation --------------------------------

   public AnnotationInfo getAnnotationType()
   {
      return annotationType;
   }
   
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
      if (!(o instanceof AnnotationValueImpl)) return false;

      final AnnotationValueImpl annotationValue = (AnnotationValueImpl) o;

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
