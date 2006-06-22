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
package org.jboss.metadata.spi.retrieval.basic;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;

/**
 * BasicAnnotationsItem.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicAnnotationsItem extends BasicItem<Annotation[]> implements AnnotationsItem
{
   /** The annotation items */
   private AnnotationItem[] annotationItems;
   
   /** The annotations */
   private Annotation[] annotations;
   
   /**
    * Create a new BasicAnnotationsItem.
    * 
    * @param loader the loader
    * @param annotationItems the annotation items
    */
   public BasicAnnotationsItem(MetaDataLoader loader, AnnotationItem[] annotationItems)
   {
      super(loader);

      if (annotationItems == null)
         throw new IllegalArgumentException("Null annotation items");

      this.annotationItems = annotationItems;
      
      if (annotationItems.length == 0)
         annotations = MetaData.NO_ANNOTATIONS;
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

   public boolean isCachable()
   {
      if (super.isCachable() == false)
         return false;
      
      for (AnnotationItem item : annotationItems)
      {
         if (item.isCachable() == false)
            return false;
      }
      
      return true;
   }

   public boolean isValid()
   {
      if (super.isValid() == false)
         return false;
      
      for (AnnotationItem item : annotationItems)
      {
         if (item.isValid() == false)
            return false;
      }
      
      return true;
   }
}
