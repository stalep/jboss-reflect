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
package org.jboss.test.classinfo.support;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * ClassInfoGenericImplementsCollection.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ClassInfoGenericImplementsMap implements Map<Long, Integer>
{
   public void clear()
   {
   }

   public boolean containsKey(Object key)
   {
      return false;
   }

   public boolean containsValue(Object value)
   {
      return false;
   }

   public Set<java.util.Map.Entry<Long, Integer>> entrySet()
   {
      return null;
   }

   public Integer get(Object key)
   {
      return null;
   }

   public boolean isEmpty()
   {
      return false;
   }

   public Set<Long> keySet()
   {
      return null;
   }

   public Integer put(Long key, Integer value)
   {
      return null;
   }

   public void putAll(Map<? extends Long, ? extends Integer> t)
   {
   }

   public Integer remove(Object key)
   {
      return null;
   }

   public int size()
   {
      return 0;
   }

   public Collection<Integer> values()
   {
      return null;
   }
}
