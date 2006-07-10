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
package org.jboss.metadata.plugins.repository.visitor;

import java.util.Collection;
import java.util.Iterator;

import org.jboss.metadata.spi.repository.MetaDataRepository;
import org.jboss.metadata.spi.repository.visitor.MetaDataRepositoryVisitor;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;

/**
 * FuzzyMetaDataRepositoryVisitor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class FuzzyMetaDataRepositoryVisitor implements MetaDataRepositoryVisitor
{
   /** The key scopes */
   private Scope[] matchScopes;
   
   /**
    * Create a new FuzzyMetaDataRepositoryVisitor.
    * 
    * @param matchKey the match key
    */
   public FuzzyMetaDataRepositoryVisitor(ScopeKey matchKey)
   {
      if (matchKey != null)
      {
         Collection<Scope> testScopes = matchKey.getScopes();
         matchScopes = testScopes.toArray(new Scope[testScopes.size()]);
      }
   }
   
   public boolean matchScope(MetaDataRepository repository, ScopeKey key)
   {
      if (matchScopes == null || matchScopes.length == 0)
         return true;
      
      boolean match = false;
      int index = 0;
      Iterator<Scope> i = key.getScopes().iterator();
      Scope repositoryScope = i.next();
      Scope matchScope = matchScopes[index];
      while (true)
      {
         ScopeLevel keyLevel = matchScope.getScopeLevel();
         ScopeLevel repositoryLevel = repositoryScope.getScopeLevel();

         // Same level
         if (keyLevel.compareTo(repositoryLevel) == 0)
         {  
            if (matchScope.equals(repositoryScope))
               match = true;
            else
            {
               // No match, we are done
               match = false;
               break;
            }
         }
         
         // Key is next
         if (keyLevel.compareTo(repositoryLevel) <= 0)
         {
            // Ran out of keys
            if (++index == matchScopes.length)
               break;
            matchScope = matchScopes[index];
         }
         else
         {
            // Ran out of keys
            if (i.hasNext() == false)
               break;
            repositoryScope = i.next();
         }
      }
      return match;
   }
}
