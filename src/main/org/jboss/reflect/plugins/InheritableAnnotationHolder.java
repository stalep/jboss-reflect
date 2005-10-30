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

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.util.JBossObject;

import java.io.Serializable;
import java.util.HashMap;

/**
 * An annotation holder
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class InheritableAnnotationHolder extends JBossObject implements AnnotatedInfo, Serializable
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3257290210164289843L;
   
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

   /**
    * Create a new InheritableAnnotationHolder.
    */
   public InheritableAnnotationHolder()
   {
   }

   /**
    * Get the declared annotations
    * 
    * @return the declared annotations
    */
   public AnnotationValue[] getDeclaredAnnotations()
   {
      return declaredAnnotationsArray;
   }

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

   /**
    * Set up the annotations
    * 
    * @param annotations the annotations
    */
   public void setupAnnotations(AnnotationValue[] annotations)
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
}
