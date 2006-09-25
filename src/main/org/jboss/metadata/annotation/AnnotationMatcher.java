/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.annotation;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.retrieval.AnnotationItem;

/**
 * AnnotationMatcher.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public final class AnnotationMatcher
{
   /**
    * Match an annotation
    * 
    * @param <T> the annotation type
    * @param annotations the annotations
    * @param annotationType the annotation type
    * @return the matched annotation or null if no match
    */
   @SuppressWarnings("unchecked")
   public static final <T extends Annotation> T matchAnnotation(Annotation[] annotations, Class<T> annotationType)
   {
      if (annotations != null)
      {
         for (Annotation a : annotations)
         {
            if (annotationType.equals(a.annotationType()))
               return (T) a;
         }
      }
      
      return null;
   }

   /**
    * Match an annotation item
    * 
    * @param <T> the annotation type
    * @param annotations the annotation items
    * @param annotationType the annotation type
    * @return the matched annotation item or null if no match
    */
   @SuppressWarnings("unchecked")
   public static final <T extends Annotation> AnnotationItem<T> matchAnnotationItem(AnnotationItem[] annotations, Class<T> annotationType)
   {
      if (annotations != null)
      {
         for (AnnotationItem item : annotations)
         {
            Annotation a = item.getAnnotation();
            if (annotationType.equals(a.annotationType()))
               return item;
         }
      }
      
      return null;
   }
}
