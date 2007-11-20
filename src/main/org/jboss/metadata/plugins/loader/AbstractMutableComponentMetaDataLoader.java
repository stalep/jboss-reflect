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
import java.lang.reflect.Member;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.metadata.spi.ComponentMutableMetaData;
import org.jboss.metadata.spi.MutableMetaData;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.reflect.spi.MemberInfo;

/**
 * AbstractMutableComponentMetaDataLoader.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractMutableComponentMetaDataLoader extends AbstractMutableMetaDataLoader implements ComponentMutableMetaData
{
   /** The component metadata */
   private volatile Map<Signature, MetaDataRetrieval> components; 

   /**
    * Create a new AbstractMutableComponentMetaDataLoader.
    */
   public AbstractMutableComponentMetaDataLoader()
   {
      this(false);
   }

   /**
    * Create a new AbstractMutableComponentMetaDataLoader.
    * 
    * @param restricted whether items are restricted
    */
   public AbstractMutableComponentMetaDataLoader(boolean restricted)
   {
      super(restricted);
   }
   
   /**
    * Create a new AbstractComponentMutableMetaDataLoader.
    * 
    * @param key the scope key
    */
   public AbstractMutableComponentMetaDataLoader(ScopeKey key)
   {
      this(key, false);
   }
   
   /**
    * Create a new AbstractComponentMetaDataLoader.
    * 
    * @param key the scope key
    * @param restricted whether the context is restricted
    */
   public AbstractMutableComponentMetaDataLoader(ScopeKey key, boolean restricted)
   {
      super(key, restricted);
   }

   public boolean isEmpty()
   {
      return isNullOrEmpty(components);
   }

   /**
    * Is map null or empty.
    *
    * @param map the map
    * @return is null or empty
    */
   @SuppressWarnings("unchecked")
   protected static boolean isNullOrEmpty(Map map)
   {
      return map == null || map.isEmpty();
   }

   public MetaDataRetrieval addComponentMetaDataRetrieval(Signature signature, MetaDataRetrieval component)
   {
      if (signature == null)
         throw new IllegalArgumentException("Null signature");
      
      if (components == null)
         components = new ConcurrentHashMap<Signature, MetaDataRetrieval>();
      
      return components.put(signature, component);
   }

   public MetaDataRetrieval removeComponentMetaDataRetrieval(Signature signature)
   {
      if (signature == null)
         throw new IllegalArgumentException("Null signature");
      
      if (components == null)
         return null;
      
      return components.remove(signature);
   }

   public MetaDataRetrieval getComponentMetaDataRetrieval(Signature signature)
   {
      if (components == null)
         return null;
      
      return components.get(signature);
   }

   /**
    * Initialise the retrieval
    * 
    * @param signature the signature
    * @return the result
    */
   protected MutableMetaData initRetrieval(Signature signature)
   {
      MetaDataRetrieval result = getComponentMetaDataRetrieval(signature);
      if (result == null)
      {
         result = initComponentRetrieval(signature);
         addComponentMetaDataRetrieval(signature, result);
      }
      if (result instanceof MutableMetaData == false)
         throw new IllegalStateException("Component is not mutable: " + signature);
      return (MutableMetaData) result;
   }

   /**
    * Initialise a component metadata retrieval
    * 
    * @param signature the signature
    * @return the result
    */
   protected abstract MetaDataRetrieval initComponentRetrieval(Signature signature);
   
   public <T extends Annotation> T addAnnotation(Signature signature, T annotation)
   {
      MutableMetaData component = initRetrieval(signature);
      return component.addAnnotation(annotation);
   }

   public <T> T addMetaData(Signature signature, String name, T metaData, Class<T> type)
   {
      MutableMetaData component = initRetrieval(signature);
      return component.addMetaData(name, metaData, type);
   }

   public <T> T addMetaData(Signature signature, T metaData, Class<T> type)
   {
      MutableMetaData component = initRetrieval(signature);
      return component.addMetaData(metaData, type);
   }

   public <T extends Annotation> T removeAnnotation(Signature signature, Class<T> annotationType)
   {
      MutableMetaData component = initRetrieval(signature);
      return component.removeAnnotation(annotationType);
   }

   public <T> T removeMetaData(Signature signature, Class<T> type)
   {
      MutableMetaData component = initRetrieval(signature);
      return component.removeMetaData(type);
   }

   public <T> T removeMetaData(Signature signature, String name, Class<T> type)
   {
      MutableMetaData component = initRetrieval(signature);
      return component.removeMetaData(name, type);
   }

   public <T extends Annotation> T addAnnotation(Member member, T annotation)
   {
      return addAnnotation(Signature.getSignature(member), annotation);
   }

   public <T extends Annotation> T addAnnotation(MemberInfo member, T annotation)
   {
      return addAnnotation(Signature.getSignature(member), annotation);
   }

   public <T> T addMetaData(Member member, String name, T metaData, Class<T> type)
   {
      return addMetaData(Signature.getSignature(member), name, metaData, type);
   }

   public <T> T addMetaData(Member member, T metaData, Class<T> type)
   {
      return addMetaData(Signature.getSignature(member), metaData, type);
   }

   public <T> T addMetaData(MemberInfo member, String name, T metaData, Class<T> type)
   {
      return addMetaData(Signature.getSignature(member), name, metaData, type);
   }

   public <T> T addMetaData(MemberInfo member, T metaData, Class<T> type)
   {
      return addMetaData(Signature.getSignature(member), metaData, type);
   }

   public <T extends Annotation> T removeAnnotation(Member member, Class<T> annotationType)
   {
      return removeAnnotation(Signature.getSignature(member), annotationType);
   }

   public <T extends Annotation> T removeAnnotation(MemberInfo member, Class<T> annotationType)
   {
      return removeAnnotation(Signature.getSignature(member), annotationType);
   }

   public <T> T removeMetaData(Member member, Class<T> type)
   {
      return removeMetaData(Signature.getSignature(member), type);
   }

   public <T> T removeMetaData(Member member, String name, Class<T> type)
   {
      return removeMetaData(Signature.getSignature(member), name, type);
   }

   public <T> T removeMetaData(MemberInfo member, Class<T> type)
   {
      return removeMetaData(Signature.getSignature(member), type);
   }

   public <T> T removeMetaData(MemberInfo member, String name, Class<T> type)
   {
      return removeMetaData(Signature.getSignature(member), name, type);
   }
}
