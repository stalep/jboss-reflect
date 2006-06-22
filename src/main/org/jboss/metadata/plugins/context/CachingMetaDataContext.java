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
package org.jboss.metadata.plugins.context;

import java.lang.annotation.Annotation;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;

/**
 * CachingMetaDataContext.
 * 
 * @TODO LRU Cache
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class CachingMetaDataContext extends AbstractMetaDataContext
{
   /** The annotations */
   private volatile Map<String, AnnotationItem> annotations;

   /** MetaData by name */
   private volatile Map<String, MetaDataItem> metaDataByName;

   /** All annotations */
   private volatile AnnotationsItem cachedAnnotationsItem;

   /** All meta data */
   private volatile MetaDatasItem cachedMetaDatasItem;

   /** The valid time */
   private volatile long validTime;
   
   /**
    * Create a new CachingMetaDataContext.
    * 
    * @param retrieval the retrieval
    */
   public CachingMetaDataContext(MetaDataRetrieval retrieval)
   {
      this(null, retrieval);
   }
   
   /**
    * Create a new CachingMetaDataContext.
    * 
    * @param parent the parent
    * @param retrieval the retrieval
    */
   public CachingMetaDataContext(MetaDataContext parent, MetaDataRetrieval retrieval)
   {
      this(parent, Collections.singletonList(retrieval));
   }
   
   /**
    * Create a new CachingMetaDataContext.
    * 
    * @param parent the parent
    * @param retrievals the retrievals
    */
   public CachingMetaDataContext(MetaDataContext parent, List<MetaDataRetrieval> retrievals)
   {
      super(parent, retrievals);
      validTime = super.getValidTime().getValidTime();
   }

   public AnnotationsItem retrieveAnnotations()
   {
      if (cachedAnnotationsItem == null)
         cachedAnnotationsItem = super.retrieveAnnotations();
      return cachedAnnotationsItem;
   }

   @SuppressWarnings("unchecked")
   public <T extends Annotation> AnnotationItem<T> retrieveAnnotation(Class<T> annotationType)
   {
      if (annotationType == null)
         throw new IllegalArgumentException("Null annotationType");
      
      String annotationName = annotationType.getName();

      long newValidTime = super.getValidTime().getValidTime();
      if (validTime < newValidTime)
      {
         if (annotations != null)
            annotations.clear();
         if (metaDataByName != null)
            metaDataByName.clear();
         validTime = newValidTime;
      }
      
      if (annotations != null)
      {
         AnnotationItem<T> result = (AnnotationItem<T>) annotations.get(annotationName);
         if (result != null)
         {
            if (result.isValid())
               return result;
            annotations.remove(annotationName);
         }
      }

      AnnotationItem<T> result = super.retrieveAnnotation(annotationType);
      if (result != null && result.isCachable())
      {
         if (annotations == null)
            annotations = new ConcurrentHashMap<String, AnnotationItem>();
         annotations.put(annotationName, result);
      }
      
      return result;
   }

   public MetaDatasItem retrieveMetaData()
   {
      if (cachedMetaDatasItem == null)
         cachedMetaDatasItem = super.retrieveMetaData();
      return cachedMetaDatasItem;
   }

   @SuppressWarnings("unchecked")
   public <T> MetaDataItem<T> retrieveMetaData(Class<T> type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      
      String name = type.getName();

      long newValidTime = super.getValidTime().getValidTime();
      if (validTime < newValidTime)
      {
         if (annotations != null)
            annotations.clear();
         if (metaDataByName != null)
            metaDataByName.clear();
         validTime = newValidTime;
      }

      if (metaDataByName != null)
      {
         MetaDataItem<T> result = (MetaDataItem<T>) metaDataByName.get(name);
         if (result != null)
         {
            if (result.isValid())
               return result;
            metaDataByName.remove(name);
         }
      }

      MetaDataItem<T> result = super.retrieveMetaData(type);
      if (result != null && result.isCachable())
      {
         if (metaDataByName == null)
            metaDataByName = new ConcurrentHashMap<String, MetaDataItem>();
         metaDataByName.put(name, result);
      }
      
      return result;
   }

   public MetaDataItem retrieveMetaData(String name)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");

      long newValidTime = super.getValidTime().getValidTime();
      if (validTime < newValidTime)
      {
         if (annotations != null)
            annotations.clear();
         if (metaDataByName != null)
            metaDataByName.clear();
         validTime = newValidTime;
      }

      if (metaDataByName != null)
      {
         MetaDataItem result = metaDataByName.get(name);
         if (result != null)
         {
            if (result.isValid())
               return result;
            metaDataByName.remove(name);
         }
      }

      MetaDataItem result = super.retrieveMetaData(name);
      if (result != null && result.isCachable())
      {
         if (metaDataByName == null)
            metaDataByName = new ConcurrentHashMap<String, MetaDataItem>();
         metaDataByName.put(name, result);
      }
      
      return result;
   }
}
