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
public class AnnotationHolder implements AnnotatedInfo
{
   protected HashMap annotationMap;
   protected AnnotationValue[] annotationsArray;


   public AnnotationHolder()
   {
   }

   public AnnotationHolder(AnnotationValue[] annotations)
   {
      setupAnnotations(annotations);
   }


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

   protected void setupAnnotations(AnnotationValue[] annotations)
   {
      if (annotations != null && annotations.length > 0)
      {
         this.annotationsArray = annotations;
         for (int i = 0; i < annotations.length; i++)
         {
            annotationMap.put(annotations[i].getAnnotationType().getName(), annotations[i]);
         }
      }
   }

}
