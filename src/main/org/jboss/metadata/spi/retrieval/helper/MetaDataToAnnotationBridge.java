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

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;

/**
 * MetaDataToAnnotationBridge.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MetaDataToAnnotationBridge<T extends Annotation> implements AnnotationItem<T>
{
   /** The meta data */
   private MetaDataItem metaData;

   /**
    * Create a new MetaDataToAnnotationBridge.
    * 
    * @param metaData the meta data
    */
   public MetaDataToAnnotationBridge(MetaDataItem metaData)
   {
      if (metaData == null)
         throw new IllegalArgumentException("Null metaData");
      this.metaData = metaData;
   }

   public boolean isCachable()
   {
      return metaData.isCachable();
   }

   public boolean isValid()
   {
      return metaData.isValid();
   }

   @SuppressWarnings("unchecked")
   public T getValue()
   {
      return (T) metaData.getValue();
   }

   public T getAnnotation()
   {
      return getValue();
   }

   public String getName()
   {
      return getValue().annotationType().getName();
   }
}
