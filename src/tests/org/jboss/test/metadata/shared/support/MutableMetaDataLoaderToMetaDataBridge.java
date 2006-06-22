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
package org.jboss.test.metadata.shared.support;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.MutableMetaData;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;

public class MutableMetaDataLoaderToMetaDataBridge extends MetaDataRetrievalToMetaDataBridge implements MutableMetaData
{
   /**
    * Create a new MutableMetaDataLoaderToMetaDataBridge.
    * 
    * @param loader the loader
    */
   public MutableMetaDataLoaderToMetaDataBridge(MutableMetaDataLoader loader)
   {
      super(loader);
   }

   public <T extends Annotation> T addAnnotation(T annotation)
   {
      return getMetaDataLoader().addAnnotation(annotation);
   }

   public <T extends Annotation> T removeAnnotation(Class<T> annotationType)
   {
      return getMetaDataLoader().removeAnnotation(annotationType);
   }

   public <T> T addMetaData(T metaData, Class<T> type)
   {
      return getMetaDataLoader().addMetaData(metaData, type);
   }

   public <T> T removeMetaData(Class<T> type)
   {
      return getMetaDataLoader().removeMetaData(type);
   }

   public <T> T addMetaData(String name, T metaData, Class<T> type)
   {
      return getMetaDataLoader().addMetaData(name, metaData, type);
   }

   public <T> T removeMetaData(String name, Class<T> type)
   {
      return getMetaDataLoader().removeMetaData(name, type);
   }

   /**
    * Get the meta data loader
    * 
    * @return the loader
    */
   protected MutableMetaDataLoader getMetaDataLoader()
   {
      return (MutableMetaDataLoader) getMetaDataRetrieval();
   }
}
