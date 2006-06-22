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


/**
 * MetaDataRetrieval.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface MetaDataRetrieval
{
   /**
    * The valid time
    * 
    * @return the valid time
    */
   ValidTime getValidTime();
   
   /**
    * Get all the annotations
    * 
    * @return the annotations
    */
   AnnotationsItem retrieveAnnotations();

   /**
    * Get annotation
    * 
    * @param annotationType the annotation type 
    * @return the annotation or null if not present
    */
   <T extends Annotation> AnnotationItem<T> retrieveAnnotation(Class<T> annotationType);

   /**
    * Get all the metadata
    * 
    * @return the metadata
    */
   MetaDatasItem retrieveMetaData();

   /**
    * Get metadata
    * 
    * @param type the type
    * @return the metadata or null if not present
    */
   <T> MetaDataItem<T> retrieveMetaData(Class<T> type);

   /**
    * Get metadata
    * 
    * @param name the name of the meta data
    * @return the metadata or null if not present
    */
   MetaDataItem retrieveMetaData(String name);
}
