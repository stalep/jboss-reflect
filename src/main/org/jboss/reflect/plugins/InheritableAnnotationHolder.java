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

import java.lang.annotation.Inherited;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.jboss.reflect.spi.AnnotationValue;

/**
 * An annotation holder
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public abstract class InheritableAnnotationHolder extends AbstractAnnotatedInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3257290210164289843L;
   
   /** The classname of the <code>@Inherited</code> annotation, this needs retroing to work on JDK 1.4 */
   private static final String INHERITED_NAME = Inherited.class.getName();//This 
   
   /** Unknown annotations map */
   static final Map<String, AnnotationValue> UNKNOWN_ANNOTATIONS_MAP = Collections.unmodifiableMap(new HashMap<String, AnnotationValue>());
   
   /** Unknown annotations */
   static final AnnotationValue[] UNKNOWN_ANNOTATIONS = new AnnotationValue[0];
   
   /** Declared annotations Map<String, AnnotationValue> */
   protected Map<String, AnnotationValue> declaredAnnotations = UNKNOWN_ANNOTATIONS_MAP;
   
   /** All annotations Map<String, AnnotationValue> */
   protected Map<String, AnnotationValue> allAnnotations = UNKNOWN_ANNOTATIONS_MAP;
   
   /** All annotations */
   protected AnnotationValue[] allAnnotationsArray = UNKNOWN_ANNOTATIONS;
   
   /** Declared annotations */
   protected AnnotationValue[] declaredAnnotationsArray = UNKNOWN_ANNOTATIONS;

   /** The annotated element */
   protected Object annotatedElement;
   
   /** The annotation helper */
   protected transient AnnotationHelper annotationHelper;
   
   /**
    * Create a new InheritableAnnotationHolder.
    */
   public InheritableAnnotationHolder()
   {
   }

   public void setAnnotatedElement(Object annotatedElement)
   {
      this.annotatedElement = annotatedElement;
   }

   public void setAnnotationHelper(AnnotationHelper annotationHelper)
   {
      this.annotationHelper = annotationHelper;
   }

   /**
    * Get the declared annotations
    * 
    * @return the declared annotations
    */
   public AnnotationValue[] getDeclaredAnnotations()
   {
      if (declaredAnnotationsArray == UNKNOWN_ANNOTATIONS)
         setupAnnotations(annotationHelper.getAnnotations(annotatedElement));
      return declaredAnnotationsArray;
   }

   public AnnotationValue[] getAnnotations()
   {
      if (allAnnotationsArray == UNKNOWN_ANNOTATIONS)
         setupAnnotations(annotationHelper.getAnnotations(annotatedElement));
      return allAnnotationsArray;
   }

   public AnnotationValue getAnnotation(String name)
   {
      if (allAnnotations == UNKNOWN_ANNOTATIONS_MAP)
         setupAnnotations(annotationHelper.getAnnotations(annotatedElement));
      return allAnnotations.get(name);
   }

   public boolean isAnnotationPresent(String name)
   {
      if (allAnnotations == UNKNOWN_ANNOTATIONS_MAP)
         setupAnnotations(annotationHelper.getAnnotations(annotatedElement));
      return allAnnotations.containsKey(name);
   }

   /**
    * Set up the annotations
    * 
    * @param annotations the annotations
    */
   public void setupAnnotations(AnnotationValue[] annotations)
   {
      InheritableAnnotationHolder superHolder = getSuperHolder();
      AnnotationValue[] superAllAnnotations = (superHolder != null) ? superHolder.getAnnotations() : null;
      
      if (annotations != null && annotations.length > 0)
      {
         declaredAnnotations = new HashMap<String, AnnotationValue>();
         declaredAnnotationsArray = annotations;
         for (int i = 0; i < annotations.length; i++)
            declaredAnnotations.put(annotations[i].getAnnotationType().getName(), annotations[i]);
         allAnnotations = new HashMap<String, AnnotationValue>();
         
         if (superHolder != null && superAllAnnotations != null && superAllAnnotations.length != 0)
         {
            for (int i = 0; i < superAllAnnotations.length; i++)
            {
               AnnotationValue av = superAllAnnotations[i];
               if (av.getAnnotationType().isAnnotationPresent(INHERITED_NAME))
               {
                  allAnnotations.put(av.getAnnotationType().getName(), av);
               }
            }
         }
         else
            allAnnotationsArray = declaredAnnotationsArray;

         for (int i = 0; i < annotations.length; i++)
            allAnnotations.put(annotations[i].getAnnotationType().getName(), annotations[i]);

         allAnnotationsArray = allAnnotations.values().toArray(new AnnotationValue[allAnnotations.size()]);
      }
      else
      {
         if (superHolder != null)
         {
            allAnnotations = superHolder.getAllAnnotations();
            allAnnotationsArray = superAllAnnotations;
         }
      }
   }
   
   /**
    * Get all the annotations as a map
    * 
    * @return the map
    */
   protected Map<String, AnnotationValue> getAllAnnotations()
   {
      if (allAnnotations == UNKNOWN_ANNOTATIONS_MAP)
         setupAnnotations(annotationHelper.getAnnotations(annotatedElement));
      return allAnnotations;
   }
   
   /**
    * Get the super holder of annoations
    * 
    * @return the super holder
    */
   protected abstract InheritableAnnotationHolder getSuperHolder();
}
