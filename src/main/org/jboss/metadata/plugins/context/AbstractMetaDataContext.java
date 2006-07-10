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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.retrieval.ValidTime;
import org.jboss.metadata.spi.retrieval.cummulative.CummulativeAnnotationsItem;
import org.jboss.metadata.spi.retrieval.cummulative.CummulativeMetaDatasItem;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;

/**
 * AbstractMetaDataContext.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractMetaDataContext implements MetaDataContext
{
   /** The meta data retrievals */
   private List<MetaDataRetrieval> retrievals;

   /** The parent context */
   private MetaDataContext parent;
   
   /** The scope */
   private volatile ScopeKey scopeKey;
   
   /**
    * Create a new AbstractMetaDataContext.
    * 
    * @param retrieval the retrieval
    */
   public AbstractMetaDataContext(MetaDataRetrieval retrieval)
   {
      this(null, retrieval);
   }
   
   /**
    * Create a new AbstractMetaDataContext.
    * 
    * @param parent the parent
    * @param retrieval the retrieval
    */
   public AbstractMetaDataContext(MetaDataContext parent, MetaDataRetrieval retrieval)
   {
      this(parent, Collections.singletonList(retrieval));
   }
   
   /**
    * Create a new AbstractMetaDataContext.
    * 
    * @param parent the parent
    * @param retrievals the retrievals
    */
   public AbstractMetaDataContext(MetaDataContext parent, List<MetaDataRetrieval> retrievals)
   {
      if (retrievals == null)
         throw new IllegalArgumentException("Null retrievals");
      if (retrievals.isEmpty())
         throw new IllegalArgumentException("Must have at least one retrieval");
      for (MetaDataRetrieval retrieval : retrievals)
      {
         if (retrieval == null)
            throw new IllegalArgumentException("Null retrieval");
      }

      this.parent = parent;
      this.retrievals = retrievals;
   }
   
   public ScopeKey getScope()
   {
      if (scopeKey == null)
      {
         ScopeKey key = new ScopeKey();
         for (MetaDataRetrieval retrieval : getRetrievals())
         {
            ScopeKey retrievalKey = retrieval.getScope();
            Collection<Scope> scopes = retrievalKey.getScopes();
            for (Scope scope : scopes)
               key.addScope(scope);
         }
         scopeKey = key;
      }
      return scopeKey;
   }

   public ValidTime getValidTime()
   {
      ValidTime result = null;
      long resultLong = Long.MIN_VALUE;

      if (parent != null)
      {
         result = parent.getValidTime();
         resultLong = result.getValidTime();
      }
      
      for (int i = 0; i < retrievals.size(); ++i)
      {
         MetaDataRetrieval retrieval = retrievals.get(i);
         ValidTime temp = retrieval.getValidTime();
         long tempLong = temp.getValidTime();
         if (tempLong > resultLong || result == null)
         {
            result = temp;
            resultLong = tempLong;
         }
      }
      
      return result;
   }

   public MetaDataContext getParent()
   {
      return parent;
   }
   
   public List<MetaDataRetrieval> getRetrievals()
   {
      if (parent == null)
         return retrievals;
      
      List<MetaDataRetrieval> result = new ArrayList<MetaDataRetrieval>(retrievals);
      result.add(parent);
      return result;
   }

   public List<MetaDataRetrieval> getLocalRetrievals()
   {
      return retrievals;
   }
   
   public void append(MetaDataRetrieval retrieval)
   {
      if (retrieval == null)
         throw new IllegalArgumentException("Null retrieval");
      
      if (retrievals instanceof CopyOnWriteArrayList == false)
         retrievals = new CopyOnWriteArrayList<MetaDataRetrieval>(retrievals);
      
      retrievals.add(retrieval);
      scopeKey = null;
   }

   public void prepend(MetaDataRetrieval retrieval)
   {
      if (retrieval == null)
         throw new IllegalArgumentException("Null retrieval");
      
      if (retrievals instanceof CopyOnWriteArrayList == false)
         retrievals = new CopyOnWriteArrayList<MetaDataRetrieval>(retrievals);
      
      retrievals.add(0, retrieval);
      scopeKey = null;
   }

   public void remove(MetaDataRetrieval retrieval)
   {
      if (retrieval == null)
         throw new IllegalArgumentException("Null retrieval");

      if (retrievals.size() == 1)
         throw new IllegalStateException("Must have at least one retrieval");
      
      retrievals.remove(retrieval);
      scopeKey = null;
   }

   public AnnotationsItem retrieveAnnotations()
   {
      return new CummulativeAnnotationsItem(this, true);
   }

   public AnnotationsItem retrieveLocalAnnotations()
   {
      return new CummulativeAnnotationsItem(this, false);
   }

   public <T extends Annotation> AnnotationItem<T> retrieveAnnotation(Class<T> annotationType)
   {
      for (int i = 0; i < retrievals.size(); ++i)
      {
         MetaDataRetrieval retrieval = retrievals.get(i);
         AnnotationItem<T> item = retrieval.retrieveAnnotation(annotationType);
         if (item != null)
            return item;
      }
      
      if (parent != null)
         return parent.retrieveAnnotation(annotationType);
      
      return null;
   }

   public MetaDatasItem retrieveMetaData()
   {
      return new CummulativeMetaDatasItem(this, true);
   }

   public MetaDatasItem retrieveLocalMetaData()
   {
      return new CummulativeMetaDatasItem(this, false);
   }

   public <T> MetaDataItem<T> retrieveMetaData(Class<T> type)
   {
      for (int i = 0; i < retrievals.size(); ++i)
      {
         MetaDataRetrieval retrieval = retrievals.get(i);
         MetaDataItem<T> item = retrieval.retrieveMetaData(type);
         if (item != null)
            return item;
      }
      
      if (parent != null)
         return parent.retrieveMetaData(type);
      
      return null;
   }

   public MetaDataItem retrieveMetaData(String name)
   {
      for (int i = 0; i < retrievals.size(); ++i)
      {
         MetaDataRetrieval retrieval = retrievals.get(i);
         MetaDataItem item = retrieval.retrieveMetaData(name);
         if (item != null)
            return item;
      }
      
      if (parent != null)
         return parent.retrieveMetaData(name);
      
      return null;
   }
}
