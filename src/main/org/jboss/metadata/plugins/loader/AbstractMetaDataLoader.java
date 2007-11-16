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
package org.jboss.metadata.plugins.loader;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.Item;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.retrieval.ValidTime;
import org.jboss.metadata.spi.retrieval.helper.AnnotationToMetaDataBridge;
import org.jboss.metadata.spi.retrieval.helper.AnnotationsToMetaDatasBridge;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;

/**
 * AbstractMetaDataLoader.
 * 
 * <p>The default behaviour is to assume there are only
 * annotations with the types and names of the getMetadata()
 * methods interpreted as annotation types and class names.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractMetaDataLoader implements MetaDataLoader
{
   /** The valid time */
   private ValidTime validTime;

   /** The scope key */
   private ScopeKey scopeKey;
   
   /**
    * Create a new AbstractMetaDataLoader.
    */
   public AbstractMetaDataLoader()
   {
      this(ScopeKey.DEFAULT_SCOPE);
   }

   /**
    * Create a new AbstractMetaDataLoader.
    * 
    * @param key the scope
    */
   public AbstractMetaDataLoader(ScopeKey key)
   {
      this.scopeKey = key;
      validTime = new ValidTime();
   }
   
   public ScopeKey getScope()
   {
      return scopeKey;
   }

   public ValidTime getValidTime()
   {
      return validTime;
   }
   
   public <T> boolean isCachable(Item<T> item)
   {
      return true;
   }

   public AnnotationsItem retrieveLocalAnnotations()
   {
      return retrieveAnnotations();
   }

   @SuppressWarnings("unchecked")
   public <T> MetaDataItem<T> retrieveMetaData(Class<T> type)
   {
      if (type.isAnnotation() == false)
         return null;
      AnnotationItem annotation = retrieveAnnotation((Class<Annotation>) type);
      if (annotation == null)
         return null;
      return new AnnotationToMetaDataBridge<T>(annotation);
   }

   public MetaDatasItem retrieveLocalMetaData()
   {
      return retrieveMetaData();
   }

   public MetaDatasItem retrieveMetaData()
   {
      AnnotationsItem annotations = retrieveAnnotations();
      return new AnnotationsToMetaDatasBridge(annotations);
   }

   public MetaDataRetrieval getScopedRetrieval(ScopeLevel level)
   {
      if (getScope().getScopeLevel(level) != null)
         return this;

      return null;
   }

   /**
    * Invalidate
    */
   public void invalidate()
   {
      validTime.invalidate();
   }
}
