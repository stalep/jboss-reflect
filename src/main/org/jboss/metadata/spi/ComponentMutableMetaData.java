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
import java.lang.reflect.Member;

import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.reflect.spi.MemberInfo;

/**
 * ComponentMutableMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 57133 $
 */
public interface ComponentMutableMetaData
{
   /**
    * Add a component metadata
    * 
    * @param signature the signature
    * @param component the component
    * @return any previous component at that signature
    */
   MetaDataRetrieval addComponentMetaDataRetrieval(Signature signature, MetaDataRetrieval component);

   /**
    * Remove a component metadata
    * 
    * @param signature the signature
    * @return any previous component at that signature
    */
   MetaDataRetrieval removeComponentMetaDataRetrieval(Signature signature);

   /**
    * Add a component annotation
    * 
    * @param <T> the annotation type
    * @param signature the signature
    * @param annotation the annotation
    * @return any previous annotation
    */
   <T extends Annotation> T addAnnotation(Signature signature, T annotation);

   /**
    * Remove a member annotation
    * 
    * @param <T> the annotation type
    * @param signature the signature
    * @param annotationType the annotation type
    * @return any previous annotation
    */
   <T extends Annotation> T removeAnnotation(Signature signature, Class<T> annotationType);

   /**
    * Add member  metaData
    * 
    * @param <T> the metadata type
    * @param signature the signature
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(Signature signature, T metaData, Class<T> type);

   /**
    * Remove member metaData
    * 
    * @param <T> the metadata type
    * @param signature the signature
    * @param type the meta data type
    * @return any previous meta data
    */
   <T> T removeMetaData(Signature signature, Class<T> type);

   /**
    * Add member metaData
    *
    * @param <T> the metadata type
    * @param signature the signature
    * @param name the name
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(Signature signature, String name, T metaData, Class<T> type);

   /**
    * Remove member metadata
    * 
    * @param <T> the metadata type
    * @param signature the signature
    * @param name the name of the meta data
    * @param type the expected type of the metadata
    * @return the metadata or null if not present
    */
   <T> T removeMetaData(Signature signature, String name, Class<T> type);

   /**
    * Add a component annotation
    * 
    * @param <T> the annotation type
    * @param member member
    * @param annotation the annotation
    * @return any previous annotation
    */
   <T extends Annotation> T addAnnotation(Member member, T annotation);

   /**
    * Remove a member annotation
    * 
    * @param <T> the annotation type
    * @param member member
    * @param annotationType the annotation type
    * @return any previous annotation
    */
   <T extends Annotation> T removeAnnotation(Member member, Class<T> annotationType);

   /**
    * Add member  metaData
    * 
    * @param <T> the metadata type
    * @param member member
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(Member member, T metaData, Class<T> type);

   /**
    * Remove member metaData
    * 
    * @param <T> the metadata type
    * @param member member
    * @param type the meta data type
    * @return any previous meta data
    */
   <T> T removeMetaData(Member member, Class<T> type);

   /**
    * Add member metaData
    *
    * @param <T> the metadata type
    * @param member member
    * @param name the name
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(Member member, String name, T metaData, Class<T> type);

   /**
    * Remove member metadata
    * 
    * @param <T> the metadata type
    * @param member member
    * @param name the name of the meta data
    * @param type the expected type of the metadata
    * @return the metadata or null if not present
    */
   <T> T removeMetaData(Member member, String name, Class<T> type);

   /**
    * Add a member annotation
    * 
    * @param <T> the annotation type
    * @param member member
    * @param annotation the annotation
    * @return any previous annotation
    */
   <T extends Annotation> T addAnnotation(MemberInfo member, T annotation);

   /**
    * Remove a member annotation
    * 
    * @param <T> the annotation type
    * @param member member
    * @param annotationType the annotation type
    * @return any previous annotation
    */
   <T extends Annotation> T removeAnnotation(MemberInfo member, Class<T> annotationType);

   /**
    * Add member  metaData
    * 
    * @param <T> the metadata type
    * @param member member
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(MemberInfo member, T metaData, Class<T> type);

   /**
    * Remove member metaData
    * 
    * @param <T> the metadata type
    * @param member member
    * @param type the meta data type
    * @return any previous meta data
    */
   <T> T removeMetaData(MemberInfo member, Class<T> type);

   /**
    * Add member metaData
    *
    * @param <T> the metadata type
    * @param member member
    * @param name the name
    * @param metaData the meta data
    * @param type the expected type
    * @return any previous meta data
    */
   <T> T addMetaData(MemberInfo member, String name, T metaData, Class<T> type);

   /**
    * Remove member metadata
    * 
    * @param <T> the metadata type
    * @param member member
    * @param name the name of the meta data
    * @param type the expected type of the metadata
    * @return the metadata or null if not present
    */
   <T> T removeMetaData(MemberInfo member, String name, Class<T> type);
}
