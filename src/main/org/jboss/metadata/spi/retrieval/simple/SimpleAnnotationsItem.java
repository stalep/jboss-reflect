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
package org.jboss.metadata.spi.retrieval.simple;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;

/**
 * SimpleAnnotationsItem.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SimpleAnnotationsItem extends SimpleItem<Annotation[]> implements AnnotationsItem 
{
   /** No annotations */
   public static final SimpleAnnotationsItem NO_ANNOTATIONS = new SimpleAnnotationsItem();
   
   /** The annotation items */
   private AnnotationItem[] annotationItems;
   
   /** The annotations */
   private Annotation[] annotations;
   
   /**
    * Create a new SimpleAnnotationsItem with no annotations
    */
   protected SimpleAnnotationsItem()
   {
      this(NO_ANNOTATION_ITEMS);
   }
   
   /**
    * Create a new SimpleAnnotationsItem.
    * 
    * @param annotationItems the annotation items
    */
   public SimpleAnnotationsItem(AnnotationItem[] annotationItems)
   {
      setAnnotationItems(annotationItems);
   }

   public Annotation[] getValue()
   {
      if (annotations == null)
      {
         Annotation[] temp = new Annotation[annotationItems.length];
         for (int i = 0; i < temp.length; ++i)
            temp[i] = annotationItems[i].getAnnotation();
         annotations = temp;
      }
      return annotations;
   }

   public AnnotationItem[] getAnnotations()
   {
      return annotationItems;
   }
   
   /**
    * Set the annotation items
    * 
    * @param annotationItems the annotation items 
    */
   protected void setAnnotationItems(AnnotationItem[] annotationItems)
   {
      if (annotationItems == null)
         throw new IllegalArgumentException("Null annotation items");
      
      this.annotationItems = annotationItems;
      if (annotationItems.length == 0)
         annotations = MetaData.NO_ANNOTATIONS;
      else
         annotations = null;
   }
}
