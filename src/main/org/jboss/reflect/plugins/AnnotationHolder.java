/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.util.HashMap;

import org.jboss.reflect.AnnotatedInfo;
import org.jboss.reflect.AnnotationValue;

/**
 * An annotation holder
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationHolder implements AnnotatedInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The annotations */
   protected AnnotationValue[] annotationsArray;

   /** Annotations map Map<String, AnnotationValue> */
   protected HashMap annotationMap;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new annotation holder
    */
   public AnnotationHolder()
   {
   }

   /**
    * Create a new AnnotationHolder.
    * 
    * @param annotations the annotations
    */
   public AnnotationHolder(AnnotationValue[] annotations)
   {
      setupAnnotations(annotations);
   }

   // Public --------------------------------------------------------

   // AnnotatedInfo implementation ----------------------------------

   public AnnotationValue[] getAnnotations()
   {
      return annotationsArray;
   }

   public AnnotationValue getAnnotation(String name)
   {
      return (AnnotationValue) annotationMap.get(name);
   }

   public boolean isAnnotationPresent(String name)
   {
      return annotationMap.containsKey(name);
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Set up the annotations
    * 
    * @param annotations the annotations
    */
   protected void setupAnnotations(AnnotationValue[] annotations)
   {
      if (annotations != null && annotations.length > 0)
      {
         this.annotationsArray = annotations;
         for (int i = 0; i < annotations.length; i++)
            annotationMap.put(annotations[i].getAnnotationType().getName(), annotations[i]);
      }
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
