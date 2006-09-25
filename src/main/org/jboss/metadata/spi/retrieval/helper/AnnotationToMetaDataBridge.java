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
package org.jboss.metadata.spi.retrieval.helper;

import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;

/**
 * AnnotationToMetaDataBridge.
 * 
 * @param <T> the item type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AnnotationToMetaDataBridge<T> implements MetaDataItem<T>
{
   /** The annotation */
   private AnnotationItem annotation;

   /**
    * Create a new AnnotationToMetaDataBridge.
    * 
    * @param annotation the annotation
    */
   public AnnotationToMetaDataBridge(AnnotationItem annotation)
   {
      if (annotation == null)
         throw new IllegalArgumentException("Null annotation");
      this.annotation = annotation;
   }

   public boolean isCachable()
   {
      return annotation.isCachable();
   }

   public boolean isValid()
   {
      return annotation.isValid();
   }

   @SuppressWarnings("unchecked")
   public T getValue()
   {
      return (T) annotation.getValue();
   }

   public String getName()
   {
      return annotation.getName();
   }
}
