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
package org.jboss.metadata.spi;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.signature.Signature;

/**
 * MetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface MetaData
{
   /** No annotations */
   Annotation[] NO_ANNOTATIONS = new Annotation[0];

   /** No meta data */
   Object[] NO_METADATA = new Object[0];

   /**
    * Get the valid time
    * 
    * @return the valid time
    */
   long getValidTime();

   /**
    * Get annotation
    * 
    * @param <T> the annotation type
    * @param annotationType the annotation type 
    * @return the annotation or null if not present
    */
   <T extends Annotation> T getAnnotation(Class<T> annotationType);

   /**
    * Get all the annotations
    * 
    * @return the annotations
    */
   Annotation[] getAnnotations();

   /**
    * Get all the local annotations
    * 
    * @return the annotations
    */
   Annotation[] getLocalAnnotations();

   /**
    * Is the annotation present?
    * 
    * @param annotationType the annotation type
    * @return true when present
    */
   boolean isAnnotationPresent(Class<? extends Annotation> annotationType);

   /**
    * Get metadata
    *
    * @param <T> the metadata type
    * @param type the type
    * @return the metadata or null if not present
    */
   <T> T getMetaData(Class<T> type);

   /**
    * Get all the metadata
    * 
    * @return the metadata
    */
   Object[] getMetaData();

   /**
    * Get all the local metadata
    * 
    * @return the metadata
    */
   Object[] getLocalMetaData();

   /**
    * Is the metadata present
    * 
    * @param type the type of the meta data
    * @return true when the metadata is present
    */
   boolean isMetaDataPresent(Class<?> type);

   /**
    * Get metadata
    * 
    * @param name the name of the meta data
    * @return the metadata or null if not present
    */
   Object getMetaData(String name);

   /**
    * Get metadata
    * 
    * @param <T> the metadata type
    * @param name the name of the meta data
    * @param type the expected type of the metadata
    * @return the metadata or null if not present
    */
   <T> T getMetaData(String name, Class<T> type);
   
   /**
    * Is the metadata present
    * 
    * @param name the name of the meta data
    * @return true when the metadata is present
    */
   boolean isMetaDataPresent(String name);

   /**
    * Is the metadata present
    * 
    * @param name the name of the meta data
    * @param type the expected type of the metadata
    * @return true when the metadata is present
    */
   boolean isMetaDataPresent(String name, Class<?> type);
   
   /**
    * Get the component metadata
    * 
    * @param signature the signature
    * @return the component metadata
    */
   MetaData getComponentMetaData(Signature signature);
}
