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

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.MutableMetaData;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.metadata.spi.signature.Signature;

/**
 * MetaDataAndMutableMetaDataImpl.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MetaDataAndMutableMetaDataImpl implements MetaDataAndMutableMetaData
{
   private MetaData metaData;
   private MutableMetaData mutable;

   public MetaDataAndMutableMetaDataImpl(MetaData metaData, MutableMetaData mutable)
   {
      this.metaData = metaData;
      this.mutable = mutable;
   }
   
   public <T extends Annotation> T getAnnotation(Class<T> annotationType)
   {
      return metaData.getAnnotation(annotationType);
   }

   public Annotation[] getAnnotations()
   {
      return metaData.getAnnotations();
   }

   public Annotation[] getLocalAnnotations()
   {
      return metaData.getLocalAnnotations();
   }

   public Object[] getLocalMetaData()
   {
      return metaData.getLocalMetaData();
   }

   public Object[] getMetaData()
   {
      return metaData.getMetaData();
   }

   public <T> T getMetaData(Class<T> type)
   {
      return metaData.getMetaData(type);
   }

   public <T> T getMetaData(String name, Class<T> type)
   {
      return metaData.getMetaData(name, type);
   }

   public Object getMetaData(String name)
   {
      return metaData.getMetaData(name);
   }

   public long getValidTime()
   {
      return metaData.getValidTime();
   }

   public boolean isAnnotationPresent(Class<? extends Annotation> annotationType)
   {
      return metaData.isAnnotationPresent(annotationType);
   }

   public boolean isMetaDataPresent(Class<?> type)
   {
      return metaData.isMetaDataPresent(type);
   }

   public boolean isMetaDataPresent(String name, Class<?> type)
   {
      return metaData.isMetaDataPresent(name, type);
   }

   public boolean isMetaDataPresent(String name)
   {
      return metaData.isMetaDataPresent(name);
   }

   public <T extends Annotation> T addAnnotation(T annotation)
   {
      return mutable.addAnnotation(annotation);
   }

   public <T> T addMetaData(String name, T metaData, Class<T> type)
   {
      return mutable.addMetaData(name, metaData, type);
   }

   public <T> T addMetaData(T metaData, Class<T> type)
   {
      return mutable.addMetaData(metaData, type);
   }

   public <T extends Annotation> T removeAnnotation(Class<T> annotationType)
   {
      return mutable.removeAnnotation(annotationType);
   }

   public <T> T removeMetaData(Class<T> type)
   {
      return mutable.removeMetaData(type);
   }

   public <T> T removeMetaData(String name, Class<T> type)
   {
      return mutable.removeMetaData(name, type);
   }

   public MetaData getComponentMetaData(Signature signature)
   {
      return metaData.getComponentMetaData(signature);
   }

   public MetaData getScopeMetaData(ScopeLevel level)
   {
      return metaData.getScopeMetaData(level);
   }

   public boolean isEmpty()
   {
      return metaData.isEmpty();
   }
}
