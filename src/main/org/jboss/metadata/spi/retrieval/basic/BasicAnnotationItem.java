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

import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;

/**
 * BasicAnnotationItem.
 * 
 * @param <T> the annotation type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicAnnotationItem<T extends Annotation> extends BasicMetaDataItem<T> implements AnnotationItem<T>
{
   /**
    * Create a new BasicAnnotationItem.
    * 
    * @param loader the loader
    * @param annotation the annotation
    */
   public BasicAnnotationItem(MetaDataLoader loader, T annotation)
   {
      super(loader, annotation.annotationType().getName(), annotation);
   }

   public T getAnnotation()
   {
      return getValue();
   }
   
   public boolean equals(Object object)
   {
      if (object == this)
         return true;
      if (object == null || object instanceof AnnotationItem == false)
         return false;
      
      AnnotationItem other = (AnnotationItem) object;
      return getAnnotation().annotationType().equals(other.getAnnotation().annotationType());
   }
}
