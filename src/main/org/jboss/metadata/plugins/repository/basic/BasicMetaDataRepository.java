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
package org.jboss.metadata.plugins.repository.basic;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.metadata.plugins.loader.reflection.ClassMetaDataRetrievalFactory;
import org.jboss.metadata.plugins.repository.AbstractMetaDataRepository;
import org.jboss.metadata.plugins.repository.visitor.ChildrenMetaDataRepositoryVisitor;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.repository.visitor.MetaDataRepositoryVisitor;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalFactory;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;

/**
 * BasicMetaDataRepository.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicMetaDataRepository extends AbstractMetaDataRepository implements MutableMetaDataRepository
{
   /** The retrievals */
   private Map<ScopeKey, MetaDataRetrieval> retrievals = new ConcurrentHashMap<ScopeKey, MetaDataRetrieval>();
   
   /** The retrieval factories by scope level */
   private Map<ScopeLevel, MetaDataRetrievalFactory> factories = new ConcurrentHashMap<ScopeLevel, MetaDataRetrievalFactory>(); 
   
   /**
    * Create a new BasicMetaDataRepository.
    */
   public BasicMetaDataRepository()
   {
      // ClassMetaData retrieval by default
      addMetaDataRetrievalFactory(CommonLevels.CLASS, ClassMetaDataRetrievalFactory.INSTANCE);
   }
   
   public MetaDataRetrieval getMetaDataRetrieval(ScopeKey key)
   {
      MetaDataRetrieval result = retrievals.get(key);
      if (result != null)
         return result;
      
      // Is this a single level?
      Collection<Scope> scopes = key.getScopes();
      if (scopes.size() != 1)
         return null;
      
      // See if we have a factory
      Scope scope = scopes.iterator().next();
      ScopeLevel scopeLevel = scope.getScopeLevel();
      MetaDataRetrievalFactory factory = getMetaDataRetrievalFactory(scopeLevel);
      if (factory == null)
         return null;
      
      // We have a factory, use it
      return factory.getMetaDataRetrieval(scope);
   }

   public Set<ScopeKey> getChildren(ScopeKey key)
   {
      if (key == null)
         return retrievals.keySet();
      
      ChildrenMetaDataRepositoryVisitor visitor = new ChildrenMetaDataRepositoryVisitor(key);
      return matchScopes(visitor);
   }

   public Set<ScopeKey> matchScopes(MetaDataRepositoryVisitor visitor)
   {
      Set<ScopeKey> result = new HashSet<ScopeKey>();
      for (ScopeKey repositoryKey : retrievals.keySet())
      {
         if (visitor.matchScope(this, repositoryKey))
            result.add(repositoryKey);
      }
      return result;
   }
   
   public MetaDataRetrieval addMetaDataRetrieval(MetaDataRetrieval retrieval)
   {
      if (retrieval == null)
         throw new IllegalArgumentException("Null retrieval");
      ScopeKey key = retrieval.getScope();
      key.freeze();
      return retrievals.put(key, retrieval);
   }

   public MetaDataRetrieval removeMetaDataRetrieval(ScopeKey key)
   {
      if (key == null)
         throw new IllegalArgumentException("Null key");
      return retrievals.remove(key);
   }

   public MetaDataRetrievalFactory addMetaDataRetrievalFactory(ScopeLevel level, MetaDataRetrievalFactory factory)
   {
      if (level == null)
         throw new IllegalArgumentException("Null level");
      if (factory == null)
         throw new IllegalArgumentException("Null factory");
      return factories.put(level,factory);
   }

   public MetaDataRetrievalFactory getMetaDataRetrievalFactory(ScopeLevel level)
   {
      if (level == null)
         throw new IllegalArgumentException("Null level");
      return factories.get(level);
   }

   public MetaDataRetrievalFactory removeMetaDataRetrievalFactory(ScopeLevel level)
   {
      if (level == null)
         throw new IllegalArgumentException("Null level");
      return factories.remove(level);
   }
}
