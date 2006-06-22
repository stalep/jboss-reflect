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
package org.jboss.metadata.plugins.loader.thread;

import java.lang.annotation.Annotation;

import org.jboss.metadata.plugins.loader.AbstractMutableMetaDataLoader;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.Item;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.retrieval.ValidTime;
import org.jboss.metadata.spi.retrieval.basic.BasicAnnotationsItem;
import org.jboss.metadata.spi.retrieval.basic.BasicMetaDatasItem;

/**
 * ThreadLocalMetaDataLoader.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ThreadLocalMetaDataLoader extends AbstractMutableMetaDataLoader
{
   /** The singleton instance */
   public static final ThreadLocalMetaDataLoader INSTANCE = new ThreadLocalMetaDataLoader();
   
   /** The thread local */
   private ThreadLocal<MemoryMetaDataLoader> threadLocal = new ThreadLocal<MemoryMetaDataLoader>();

   /** No annotations */
   private final BasicAnnotationsItem NO_ANNOTATIONS = new BasicAnnotationsItem(this, BasicAnnotationsItem.NO_ANNOTATION_ITEMS);

   /** No meta data */
   private final BasicMetaDatasItem NO_META_DATA = new BasicMetaDatasItem(this, BasicMetaDatasItem.NO_META_DATA_ITEMS);

   /**
    * Singleton
    */
   private ThreadLocalMetaDataLoader()
   {
      super(true);
   }
 
   /**
    * Clear all the values for the current thread
    */
   public void clear()
   {
      threadLocal.set(null);
   }
   
   // The only items we produce are the no annotations and no metadata
   // these should not be cached
   public <T> boolean isCachable(Item<T> item)
   {
      return false;
   }

   public ValidTime getValidTime()
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return super.getValidTime();
      return delegate.getValidTime();
   }

   public void invalidate()
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         super.invalidate();
      else
         delegate.invalidate();
   }

   public AnnotationsItem retrieveAnnotations()
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return NO_ANNOTATIONS;
      return delegate.retrieveAnnotations();
   }
   
   public <T extends Annotation> AnnotationItem<T> retrieveAnnotation(Class<T> annotationType)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return null;
      return delegate.retrieveAnnotation(annotationType);
   }

   public <T extends Annotation> T addAnnotation(T annotation)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
      {
         delegate = new MemoryMetaDataLoader(false, true);
         threadLocal.set(delegate);
      }
      return delegate.addAnnotation(annotation);
   }
   
   public <T extends Annotation> T removeAnnotation(Class<T> annotationType)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return null;
      return delegate.removeAnnotation(annotationType);
   }

   public MetaDatasItem retrieveMetaData()
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return NO_META_DATA;
      return delegate.retrieveMetaData();
   }

   public <T> MetaDataItem<T> retrieveMetaData(Class<T> type)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return null;
      return delegate.retrieveMetaData(type);
   }

   public MetaDataItem retrieveMetaData(String name)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return null;
      return delegate.retrieveMetaData(name);
   }

   public <T> T addMetaData(T metaData, Class<T> type)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
      {
         delegate = new MemoryMetaDataLoader(false, true);
         threadLocal.set(delegate);
      }
      return delegate.addMetaData(metaData, type);
   }

   public <T> T removeMetaData(Class<T> type)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return null;
      return delegate.removeMetaData(type);
   }

   public <T> T addMetaData(String name, T metaData, Class<T> type)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
      {
         delegate = new MemoryMetaDataLoader(false, true);
         threadLocal.set(delegate);
      }
      return delegate.addMetaData(name, metaData, type);
   }

   public <T> T removeMetaData(String name, Class<T> type)
   {
      MemoryMetaDataLoader delegate = threadLocal.get();
      if (delegate == null)
         return null;
      return delegate.removeMetaData(name, type);
   }
}
