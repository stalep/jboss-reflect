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

/**
 * Scope.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class Scope implements Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 5255750644324593361L;

   /** The scope level */
   private final ScopeLevel level;
   
   /** The scope qualifier */
   private final Object qualifier;
   
   public Scope(ScopeLevel level, Object qualifier)
   {
      if (level == null)
         throw new IllegalArgumentException("Null level");
      if (qualifier == null)
         throw new IllegalArgumentException("Null qualifier");
      
      this.level = level;
      this.qualifier = qualifier;
   }

   public ScopeLevel getScopeLevel()
   {
      return level;
   }

   public Object getQualifier()
   {
      return qualifier;
   }

   public String toString()
   {
      return level.getName() + "=" + qualifier;
   }
   
   public boolean equals(Object object)
   {
      if (object == this)
         return true;
      if (object == null || object instanceof Scope == false)
         return false;
      
      Scope other = (Scope) object;
      if (level.compareTo(other.getScopeLevel()) != 0)
         return false;
      
      return qualifier.equals(other.qualifier);
   }
   
   public int hashCode()
   {
      return level.hashCode();
   }
}
