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
package org.jboss.metadata.spi.helpers;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.signature.Signature;

/**
 * An unmodifiable view of the specified meta data instance.
 * MetaData already exposes unmodifiable model,
 * but just to make sure we cannot do the cast to
 * mutable metadata interface, and actually change something.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class UnmodifiableMetaData implements MetaData
{
   private MetaData metaData;

   public UnmodifiableMetaData(MetaData metaData)
   {
      if (metaData == null)
         throw new IllegalArgumentException("Null meta data.");
      this.metaData = metaData;
   }

   public long getValidTime()
   {
      return metaData.getValidTime();
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

   public boolean isAnnotationPresent(Class<? extends Annotation> annotationType)
   {
      return metaData.isAnnotationPresent(annotationType);
   }

   public <T> T getMetaData(Class<T> type)
   {
      return metaData.getMetaData(type);
   }

   public Object[] getMetaData()
   {
      return metaData.getMetaData();
   }

   public Object[] getLocalMetaData()
   {
      return metaData.getLocalMetaData();
   }

   public boolean isMetaDataPresent(Class<?> type)
   {
      return metaData.isMetaDataPresent(type);
   }

   public Object getMetaData(String name)
   {
      return metaData.getMetaData(name);
   }

   public <T> T getMetaData(String name, Class<T> type)
   {
      return metaData.getMetaData(name, type);
   }

   public boolean isMetaDataPresent(String name)
   {
      return metaData.isMetaDataPresent(name);
   }

   public boolean isMetaDataPresent(String name, Class<?> type)
   {
      return metaData.isMetaDataPresent(name, type);
   }

   public MetaData getComponentMetaData(Signature signature)
   {
      return metaData.getComponentMetaData(signature);
   }
}
