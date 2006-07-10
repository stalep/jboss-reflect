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
import java.util.concurrent.ConcurrentHashMap;

/**
 * ScopeLevel.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ScopeLevel implements Serializable, Comparable<ScopeLevel>
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 9090783215048463821L;

   /** The levels by name */
   private static final ConcurrentHashMap<String, Integer> levelsByName = new ConcurrentHashMap<String, Integer>();  
   
   /** The scope level */
   private final int level;
   
   /** The scope level name */
   private final String name;

   /**
    * Get the scope level for a name
    * 
    * @param name the name
    * @return the level or zero if no such level name
    */
   public static int getScopeLevel(String name)
   {
      Integer result = levelsByName.get(name);
      if (result != null)
         return result;
      else
         return 0;
   }
   
   /**
    * Create a new ScopeLevel.
    * 
    * @param level the level
    * @param name the name
    */
   public ScopeLevel(int level, String name)
   {
      if (level <= 0)
         throw new IllegalArgumentException("Invalid level");
      if (name == null)
         throw new IllegalArgumentException("Null name");
      
      this.level = level;
      this.name = name;
      levelsByName.put(name, level);
   }
   
   public int getLevel()
   {
      return level;
   }

   public String getName()
   {
      return name;
   }

   public String toString()
   {
      return name;
   }
   
   public int compareTo(ScopeLevel o)
   {
      return level - o.level;
   }

   public boolean equals(Object object)
   {
      if (object == this)
         return true;
      if (object == null || object instanceof ScopeLevel == false)
         return false;
      
      ScopeLevel other = (ScopeLevel) object;
      return level == other.level;
   }
   
   public int hashCode()
   {
      return level;
   }
}
