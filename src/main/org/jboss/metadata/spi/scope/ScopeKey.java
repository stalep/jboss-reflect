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
package org.jboss.metadata.spi.scope;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * ScopeKey.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ScopeKey implements Serializable, Cloneable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -496238095349593371L;

   /** The default scope */
   public static ScopeKey DEFAULT_SCOPE = new ScopeKey(new Scope(CommonLevels.JVM, "THIS"));
   
   /** The scopes */
   private SortedMap<ScopeLevel, Scope> scopes = Collections.synchronizedSortedMap(new TreeMap<ScopeLevel, Scope>());
   
   /** The scope level for this key */
   private ScopeLevel maxScopeLevel;
   
   /** Whether the key is frozen */
   private volatile boolean frozen = false;

   static
   {
      DEFAULT_SCOPE.freeze();
   }
   
   /**
    * Create a new ScopeKey.
    */
   public ScopeKey()
   {
   }

   /**
    * Create a new ScopeKey.
    * 
    * @param scope the scope
    */
   public ScopeKey(Scope scope)
   {
      addScope(scope);
   }

   /**
    * Create a new ScopeKey.
    * 
    * @param level the scope level
    * @param qualifier the scope qualifier
    */
   public ScopeKey(ScopeLevel level, String qualifier)
   {
      addScope(level, qualifier);
   }

   /**
    * Create a new ScopeKey.
    * 
    * @param scopes the scopes
    */
   public ScopeKey(Collection<Scope> scopes)
   {
      if (scopes == null)
         throw new IllegalArgumentException("Null scopes");
      for (Scope scope : scopes)
         addScope(scope);
   }

   /**
    * Create a new ScopeKey.
    * 
    * @param scopes the scopes
    */
   public ScopeKey(Scope[] scopes)
   {
      if (scopes == null)
         throw new IllegalArgumentException("Null scopes");
      for (Scope scope : scopes)
         addScope(scope);
   }
   
   /**
    * Get the frozen.
    * 
    * @return the frozen.
    */
   public boolean isFrozen()
   {
      return frozen;
   }

   /**
    * Set to frozen.
    */
   public void freeze()
   {
      if (scopes.isEmpty())
         throw new IllegalStateException("Attempt to freeze an empty key");
      this.frozen = true;
   }
   
   /**
    * Get the scopes
    * 
    * @return the scopes
    */
   public Collection<Scope> getScopes()
   {
      return Collections.unmodifiableCollection(scopes.values());
   }
   
   /**
    * Get the maximum scope level
    * 
    * @return the largest scope level
    */
   public ScopeLevel getMaxScopeLevel()
   {
      return maxScopeLevel;
   }
   
   /**
    * Get the parent scope key
    * 
    * @return the parent or null if there is no parent
    */
   public ScopeKey getParent()
   {
      if (scopes.size() < 2)
         return null;
      
      ScopeKey result = new ScopeKey();
      for (Iterator<Scope> i = scopes.values().iterator(); i.hasNext();)
      {
         Scope scope = i.next();
         if (i.hasNext())
            result.addScope(scope);
      }
      return result;
   }
   
   public boolean isParent(ScopeKey key)
   {
      // The passed key doesn't have a parent
      if (key.scopes.size() < 2)
         return false;
      
      // If it is a child, it will have one more scope
      if (scopes.size() != key.scopes.size() - 1)
         return false;

      Iterator<Scope> thisScopes = scopes.values().iterator();
      Iterator<Scope> keyScopes = key.scopes.values().iterator();
      
      while (thisScopes.hasNext())
      {
         Scope thisScope = thisScopes.next();
         Scope keyScope = keyScopes.next();
         if (thisScope.equals(keyScope) == false)
            return false;
      }
      
      return true;
   }
   
   /**
    * Add a scope
    * 
    * @param scope the scope
    * @return the previous value or null if there wasn't one
    */
   public Scope addScope(Scope scope)
   {
      if (scope == null)
         throw new IllegalArgumentException("Null scope");
      if (frozen)
         throw new IllegalStateException("The scope key is frozen");
      
      ScopeLevel level = scope.getScopeLevel();
      Scope result = scopes.put(level, scope);
      if (maxScopeLevel == null || level.compareTo(maxScopeLevel) >= 0)
         maxScopeLevel = level;
      return result;
   }
   
   /**
    * Add a scope
    * 
    * @param level the scope level
    * @param qualifier the scope qualifier
    * @return the previous value or null if there wasn't one
    */
   public Scope addScope(ScopeLevel level, String qualifier)
   {
      Scope scope = new Scope(level, qualifier);
      return addScope(scope);
   }
   
   /**
    * Remove a scope
    * 
    * @param scope the scope
    * @return the previous value or null if there wasn't one
    */
   public Scope removeScope(Scope scope)
   {
      if (scope == null)
         throw new IllegalArgumentException("Null scope");

      return removeScopeLevel(scope.getScopeLevel());
   }
   
   /**
    * Get a scope level
    * 
    * @param scopeLevel the scope level
    * @return the scope or null if there is no such level
    */
   public Scope getScopeLevel(ScopeLevel scopeLevel)
   {
      if (scopeLevel == null)
         throw new IllegalArgumentException("Null scope level");

      return scopes.get(scopeLevel);
   }
   
   /**
    * Remove a scope level
    * 
    * @param scopeLevel the scope level
    * @return the scope or null if there is no such level
    */
   public Scope removeScopeLevel(ScopeLevel scopeLevel)
   {
      if (scopeLevel == null)
         throw new IllegalArgumentException("Null scope level");
      if (frozen)
         throw new IllegalStateException("The scope key is frozen");

      Scope result = scopes.remove(scopeLevel);
      if (scopeLevel.equals(maxScopeLevel))
      {
         maxScopeLevel = null;
         for (ScopeLevel level : scopes.keySet())
            maxScopeLevel = level; 
      }
      return result;
   }

   public String toString()
   {
      return scopes.values().toString();
   }
   
   public boolean equals(Object object)
   {
      if (object == this)
         return true;
      if (object == null || object instanceof ScopeKey == false)
         return false;
      
      ScopeKey other = (ScopeKey) object;
      return scopes.equals(other.scopes);
   }
   
   public int hashCode()
   {
      return scopes.hashCode();
   }

   protected ScopeKey clone()
   {
      try
      {
         return (ScopeKey) super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         throw new Error(e);
      }
   }
}
