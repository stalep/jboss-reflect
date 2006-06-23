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

import java.util.HashMap;
import java.util.Map;

import javassist.CtClass;

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AnnotationValue;

/**
 *
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public abstract class JavassistInheritableAnnotationHolder extends JavassistAnnotatedInfo
{
   /** All annotations Map<String, AnnotationValue> */
   protected Map<String, AnnotationValue> allAnnotations;

   /** All annotations */
   protected AnnotationValue[] allAnnotationsArray = NOT_CONFIGURED;

   protected CtClass ctClass;

   public JavassistInheritableAnnotationHolder(CtClass ctClass, AnnotationHelper annotationHelper)
   {
      super(annotationHelper);
      this.ctClass = ctClass;
   }

   public AnnotationValue[] getAnnotations()
   {
      if (allAnnotationsArray == NOT_CONFIGURED)
         setupAnnotations(annotationHelper.getAnnotations(ctClass));
      return allAnnotationsArray;
   }

   protected AnnotationValue[] getAnnotations(Object obj)
   {
      synchronized (this)
      {
         if (allAnnotationsArray == NOT_CONFIGURED)
         {
            allAnnotationsArray = annotationHelper.getAnnotations(obj);
            setupAnnotations(allAnnotationsArray);

         }
      }
      return allAnnotationsArray;
   }

   public AnnotationValue getAnnotation(String name)
   {
      getAnnotations();
      return allAnnotations.get(name);
   }

   public boolean isAnnotationPresent(String name)
   {
      getAnnotations();
      return allAnnotations.containsKey(name);
   }


   /**
    * Set up the annotations
    *
    * @param annotations the annotations
    */
   public void setupAnnotations(AnnotationValue[] annotations)
   {
      JavassistInheritableAnnotationHolder superHolder = getSuperHolder();
      AnnotationValue[] superAllAnnotations = (superHolder != null) ? superHolder.getAnnotations() : null;

      if (annotations != null && annotations.length > 0)
      {
         annotationMap = new HashMap<String, AnnotationValue>();
         annotationsArray = annotations;
         for (int i = 0; i < annotations.length; i++)
         {
            annotationMap.put(annotations[i].getAnnotationType().getName(), annotations[i]);
         }
         allAnnotations = new HashMap<String, AnnotationValue>();

         if (superHolder != null && superAllAnnotations != null && superAllAnnotations.length != 0)
         {
            for (int i = 0; i < superAllAnnotations.length; i++)
            {
               AnnotationValue av = superAllAnnotations[i];
               if (av.getAnnotationType().isAnnotationPresent("java.lang.annotation.Inherited"))
               {
                  allAnnotations.put(av.getAnnotationType().getName(), av);
               }
            }
         }
         else
            allAnnotationsArray = annotationsArray;

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
      if (allAnnotations == null)
         setupAnnotations(annotationHelper.getAnnotations(ctClass));
      return allAnnotations;
   }


   public abstract JavassistInheritableAnnotationHolder getSuperHolder();
}
