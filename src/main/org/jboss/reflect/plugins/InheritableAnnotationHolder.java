/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.io.Serializable;
import java.util.HashMap;

import org.jboss.reflect.AnnotatedInfo;
import org.jboss.reflect.AnnotationValue;
import org.jboss.util.JBossObject;

/**
 * An annotation holder
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class InheritableAnnotationHolder extends JBossObject implements AnnotatedInfo, Serializable
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3257290210164289843L;
   
   // Attributes ----------------------------------------------------
   
   /** Declared annotations Map<String, AnnotationValue> */
   protected HashMap declaredAnnotations;
   
   /** All annotations Map<String, AnnotationValue> */
   protected HashMap allAnnotations;
   
   /** All annotations */
   protected AnnotationValue[] allAnnotationsArray;
   
   /** Declared annotations */
   protected AnnotationValue[] declaredAnnotationsArray;
   
   /** The super holder of annotations */
   protected InheritableAnnotationHolder superHolder;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new InheritableAnnotationHolder.
    */
   public InheritableAnnotationHolder()
   {
   }

   /**
    * Create a new InheritableAnnotationHolder.
    * 
    * @param annotations the annotations
    */
   public InheritableAnnotationHolder(AnnotationValue[] annotations)
   {
      setupAnnotations(annotations);
   }


   // Public --------------------------------------------------------

   /**
    * Get the declared annotations
    * 
    * @return the declared annotations
    */
   public AnnotationValue[] getDeclaredAnnotations()
   {
      return declaredAnnotationsArray;
   }

   // AnnotatedInfo implementation ----------------------------------

   public AnnotationValue[] getAnnotations()
   {
      return allAnnotationsArray;
   }

   public AnnotationValue getAnnotation(String name)
   {
      return (AnnotationValue) allAnnotations.get(name);
   }

   public boolean isAnnotationPresent(String name)
   {
      return allAnnotations.containsKey(name);
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
         declaredAnnotations = new HashMap();
         declaredAnnotationsArray = annotations;
         for (int i = 0; i < annotations.length; i++)
            declaredAnnotations.put(annotations[i].getAnnotationType().getName(), annotations[i]);
         allAnnotations = new HashMap();

         if (superHolder != null && superHolder.allAnnotationsArray != null)
         {
            for (int i = 0; i < superHolder.allAnnotationsArray.length; i++)
            {
               AnnotationValue av = superHolder.allAnnotationsArray[i];
               if (av.getAnnotationType().isAnnotationPresent("java.lang.annotation.Inherited"));
                  allAnnotations.put(av.getAnnotationType().getName(), av);
            }
         }
         else
            allAnnotationsArray = declaredAnnotationsArray;

         for (int i = 0; i < annotations.length; i++)
            allAnnotations.put(annotations[i].getAnnotationType().getName(), annotations[i]);

         allAnnotationsArray = (AnnotationValue[])allAnnotations.values().toArray(new AnnotationValue[allAnnotations.size()]);
      }
      else
      {
         if (superHolder != null)
         {
            allAnnotations = superHolder.allAnnotations;
            allAnnotationsArray = superHolder.allAnnotationsArray;
         }
      }
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
