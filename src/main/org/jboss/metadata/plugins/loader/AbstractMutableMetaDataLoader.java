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

import org.jboss.metadata.spi.Restricted;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.scope.ScopeKey;

/**
 * AbstractMutableMetaDataLoader.
 * 
 * <p>The default behaviour is to assume there are only
 * annotations with the types and names of the getMetadata()
 * methods interprets as annotation types and class names.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractMutableMetaDataLoader extends BasicMetaDataLoader implements MutableMetaDataLoader
{
   /** Whether restricted items are allowed */
   private final boolean restricted;
   
   /**
    * Create a new AbstractMutableMetaDataLoader.
    */
   public AbstractMutableMetaDataLoader()
   {
      this(false);
   }
   
   /**
    * Create a new AbstractMutableMetaDataLoader.
    * 
    * @param restricted whether the context is restricted
    */
   public AbstractMutableMetaDataLoader(boolean restricted)
   {
      this.restricted = restricted;
   }
   
   /**
    * Create a new AbstractMutableMetaDataLoader.
    * 
    * @param key the scope key
    */
   public AbstractMutableMetaDataLoader(ScopeKey key)
   {
      this(key, false);
   }
   
   /**
    * Create a new AbstractMutableMetaDataLoader.
    * 
    * @param key the scope key
    * @param restricted whether the context is restricted
    */
   public AbstractMutableMetaDataLoader(ScopeKey key, boolean restricted)
   {
      super(key);
      this.restricted = restricted;
   }

   /**
    * Check whether an annotation is retricted
    * 
    * @param annotation the annotation
    */
   public void checkRestricted(Annotation annotation)
   {
      if (restricted)
      {
         Class<? extends Annotation> annotationType = annotation.annotationType();
         if (annotationType.isAnnotationPresent(Restricted.class))
            throw new SecurityException("Context is restricted, not allowed to add " + annotationType.getName());
      }
   }
   
   /**
    * Check whether an object is retricted
    * 
    * @param type the type
    */
   public void checkRestricted(Class<?> type)
   {
      if (restricted && type.isAnnotationPresent(Restricted.class))
         throw new SecurityException("Context is restricted, not allowed to add " + type.getName());
   }
   
   @SuppressWarnings("unchecked")
   public <T> T addMetaData(T metaData, Class<T> type)
   {
      if (metaData == null)
         throw new IllegalArgumentException("Null metaData");
      if (type == null)
         throw new IllegalArgumentException("Null type");
      if (type.isAnnotation() == false)
         throw new IllegalArgumentException("Only annotation types are supported: " + type.getClass().getName());

      Annotation annotation = (Annotation) metaData;
      return (T) addAnnotation(annotation);
   }

   @SuppressWarnings("unchecked")
   public <T> T removeMetaData(Class<T> type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");
      if (type.isAnnotation() == false)
         throw new IllegalArgumentException("Only annotation types are supported: " + type.getName());

      return (T) removeAnnotation((Class<Annotation>) type);
   }

   public <T> T addMetaData(String name, T metaData, Class<T> type)
   {
      return addMetaData(metaData, type);
   }

   public <T> T removeMetaData(String name, Class<T> type)
   {
      return removeMetaData(type);
   }
}
