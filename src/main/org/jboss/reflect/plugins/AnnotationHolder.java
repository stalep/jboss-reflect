/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.util.JBossObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * An annotation holder
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationHolder extends JBossObject implements AnnotatedInfo, Serializable
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3546645408219542832L;
   
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
         annotationMap = new HashMap();
         for (int i = 0; i < annotations.length; i++)
         {
            AnnotationInfo type = annotations[i].getAnnotationType();
            annotationMap.put(type.getName(), annotations[i]);
         }
      }
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
