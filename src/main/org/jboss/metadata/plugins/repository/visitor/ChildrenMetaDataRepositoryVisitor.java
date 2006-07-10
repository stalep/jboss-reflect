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

import org.jboss.metadata.spi.repository.MetaDataRepository;
import org.jboss.metadata.spi.repository.visitor.MetaDataRepositoryVisitor;
import org.jboss.metadata.spi.scope.ScopeKey;

/**
 * FuzzyMetaDataRepositoryVisitor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ChildrenMetaDataRepositoryVisitor implements MetaDataRepositoryVisitor
{
   /** The parent */
   private ScopeKey parent;
   
   /**
    * Create a new ChildrenMetaDataRepositoryVisitor.
    * 
    * @param parent the parent
    */
   public ChildrenMetaDataRepositoryVisitor(ScopeKey parent)
   {
      if (parent == null)
         throw new IllegalArgumentException("Null parent");
      
      this.parent = parent;
   }
   
   public boolean matchScope(MetaDataRepository repository, ScopeKey key)
   {
      return parent.isParent(key);
   }
}
