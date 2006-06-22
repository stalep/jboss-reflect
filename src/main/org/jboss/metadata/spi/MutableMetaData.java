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

/**
 * MutableMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface MutableMetaData
{
   /**
    * Add an annotation
    * 
    * @param annotation the annotation
    * @param annotationType the annotation type
    * @return any previous annotation
    */
   <T extends Annotation> T addAnnotation(T annotation);

   /**
    * Remove an annotation
    * 
    * @param annotationType the annotation type
    * @return any previous annotation
    */
   <T extends Annotation> T removeAnnotation(Class<T> annotationType);

   /**
    * Add metaData
    * 
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(T metaData, Class<T> type);

   /**
    * Remove metaData
    * 
    * @param type the meta data type
    * @return any previous meta data
    */
   <T> T removeMetaData(Class<T> type);

   /**
    * Add metaData
    *
    * @param name the name
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(String name, T metaData, Class<T> type);

   /**
    * Remove metadata
    * 
    * @param name the name of the meta data
    * @param type the expected type of the metadata
    * @return the metadata or null if not present
    */
   <T> T removeMetaData(String name, Class<T> type);
}
