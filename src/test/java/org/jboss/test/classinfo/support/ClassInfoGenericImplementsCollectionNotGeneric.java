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
* License aObject with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.test.classinfo.support;

import java.util.Collection;
import java.util.Iterator;

/**
 * ClassInfoGenericImplementsCollection.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@SuppressWarnings("unchecked")
public class ClassInfoGenericImplementsCollectionNotGeneric implements Collection
{
   public boolean add(Object o)
   {
      return false;
   }

   @SuppressWarnings("unchecked")
   public boolean addAll(Collection c)
   {
      return false;
   }

   public void clear()
   {
   }

   public boolean contains(Object o)
   {
      return false;
   }

   @SuppressWarnings("unchecked")
   public boolean containsAll(Collection c)
   {
      return false;
   }

   public boolean isEmpty()
   {
      return false;
   }

   public Iterator<Object> iterator()
   {
      return null;
   }

   public boolean remove(Object o)
   {
      return false;
   }

   @SuppressWarnings("unchecked")
   public boolean removeAll(Collection c)
   {
      return false;
   }

   @SuppressWarnings("unchecked")
   public boolean retainAll(Collection c)
   {
      return false;
   }

   public int size()
   {
      return 0;
   }

   public Object[] toArray()
   {
      return null;
   }

   @SuppressWarnings("unchecked")
   public Object[] toArray(Object[] a)
   {
      return null;
   }
}
