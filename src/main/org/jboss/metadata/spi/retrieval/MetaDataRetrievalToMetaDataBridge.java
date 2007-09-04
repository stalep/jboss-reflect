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
package org.jboss.metadata.spi.retrieval;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.ArrayList;

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.metadata.plugins.context.AbstractMetaDataContext;

/**
 * MetaDataRetrievalToMetaDataBridge.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MetaDataRetrievalToMetaDataBridge implements MetaData
{
   /** The meta data retrieval method */
   private MetaDataRetrieval retrieval;
   
   /**
    * Create a new MetaDataRetrievalToMetaDataBridge.
    * 
    * @param retrieval the retrieval
    */
   public MetaDataRetrievalToMetaDataBridge(MetaDataRetrieval retrieval)
   {
      if (retrieval == null)
         throw new IllegalArgumentException("Null retrieval");
      this.retrieval = retrieval;
   }

   public long getValidTime()
   {
      ValidTime validTime = retrieval.getValidTime();
      return validTime.getValidTime();
   }

   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
   {
      if (annotationType == null)
         throw new IllegalArgumentException("Null annotationType");
      AnnotationItem<T> item = retrieval.retrieveAnnotation(annotationType);
      if (item == null)
         return null;
      return item.getValue();
   }

   public Annotation[] getAnnotations()
   {
      AnnotationsItem item = retrieval.retrieveAnnotations();
      if (item == null)
         return NO_ANNOTATIONS;
      return item.getValue();
   }

   public Annotation[] getLocalAnnotations()
   {
      AnnotationsItem item = retrieval.retrieveLocalAnnotations();
      if (item == null)
         return NO_ANNOTATIONS;
      return item.getValue();
   }

   public Object[] getMetaData()
   {
      MetaDatasItem item = retrieval.retrieveMetaData();
      if (item == null)
         return NO_METADATA;
      return item.getValue();
   }

   public Object[] getLocalMetaData()
   {
      MetaDatasItem item = retrieval.retrieveLocalMetaData();
      if (item == null)
         return NO_METADATA;
      return item.getValue();
   }

   public <T> T getMetaData(Class<T> type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      MetaDataItem<T> item = retrieval.retrieveMetaData(type);
      if (item == null)
         return null;
      return item.getValue();
   }

   public <T> T getMetaData(String name, Class<T> type)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      if (type == null)
         throw new IllegalArgumentException("Null type");
      MetaDataItem item = retrieval.retrieveMetaData(name);
      if (item == null)
         return null;
      return type.cast(item.getValue());
   }

   public Object getMetaData(String name)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      MetaDataItem item = retrieval.retrieveMetaData(name);
      if (item == null)
         return null;
      return item.getValue();
   }

   public boolean isAnnotationPresent(Class<? extends Annotation> annotationType)
   {
      return getAnnotation(annotationType) != null;
   }

   public boolean isMetaDataPresent(Class<?> type)
   {
      return getMetaData(type) != null;
   }

   public boolean isMetaDataPresent(String name, Class<?> type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
       Object value = getMetaData(name);
       if (value == null)
          return false;
       return type.isInstance(value);
   }

   public boolean isMetaDataPresent(String name)
   {
      return getMetaData(name) != null;
   }

   public MetaData getComponentMetaData(Signature signature)
   {
      MetaDataRetrieval component = retrieval.getComponentMetaDataRetrieval(signature);
      if (component == null)
         return null;
      return new MetaDataRetrievalToMetaDataBridge(component);
   }

   public MetaData getScopeMetaData(ScopeLevel level)
   {
      if (level == null)
         throw new IllegalArgumentException("Null scope level");

      if (retrieval instanceof MetaDataContext)
      {
         MetaDataContext context = (MetaDataContext)retrieval;
         List<MetaDataRetrieval> matchingRetrievals = new ArrayList<MetaDataRetrieval>();
         List<MetaDataRetrieval> localRetrievals = context.getLocalRetrievals();
         for (MetaDataRetrieval localRetrieval : localRetrievals)
         {
            ScopeKey scopeKey = localRetrieval.getScope();
            if (scopeKey.getScopeLevel(level) != null)
               matchingRetrievals.add(localRetrieval);
         }
         if (matchingRetrievals.isEmpty() == false)
            return new MetaDataRetrievalToMetaDataBridge(new AbstractMetaDataContext(context, matchingRetrievals));
      }
      else if (retrieval.getScope().getScopeLevel(level) != null)
         return new MetaDataRetrievalToMetaDataBridge(new AbstractMetaDataContext(retrieval));
      return null;
   }

   public boolean isEmpty()
   {
      return retrieval.isEmpty();
   }

   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof MetaDataRetrievalToMetaDataBridge == false)
         return false;
      
      MetaDataRetrievalToMetaDataBridge other = (MetaDataRetrievalToMetaDataBridge) obj;
      return retrieval.equals(other.retrieval);
   }

   public int hashCode()
   {
      return retrieval.hashCode();
   }

   public String toString()
   {
      return super.toString() + "{" + retrieval.getScope() + "}";
   }

   /**
    * Get the meta data retrieval
    * 
    * @return the retrieval
    */
   protected MetaDataRetrieval getMetaDataRetrieval()
   {
      return retrieval;
   }
}
