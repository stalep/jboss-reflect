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
package org.jboss.test.metadata.scope.test;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;

import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * ScopeUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ScopeLevelUnitTestCase extends AbstractMetaDataTest
{
   public ScopeLevelUnitTestCase(String name)
   {
      super(name);
   }

   public void testBasicScopeLevel() throws Exception
   {
      ScopeLevel test = new ScopeLevel(99, "HELLO");
      assertEquals(99, test.getLevel());
      assertEquals("HELLO", test.getName());
      assertEquals(99, test.hashCode());
      assertEquals("HELLO", test.toString());
   }

   public void testScopeLevelEquals() throws Exception
   {
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      ScopeLevel test2 = new ScopeLevel(1, "HELLO");
      assertEquals(test1, test2);

      ScopeLevel test3 = new ScopeLevel(1, "DIFFERENT");
      assertEquals(test1, test3);
   }

   public void testScopeLevelNotEquals() throws Exception
   {
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      ScopeLevel test2 = new ScopeLevel(2, "HELLO");
      assertFalse(test1.equals(test2));
   }

   public void testScopeLevelSerialization() throws Exception
   {
      ScopeLevel test1 = new ScopeLevel(99, "HELLO");

      byte[] bytes = serialize(test1);
      ScopeLevel test2 = (ScopeLevel) deserialize(bytes);

      assertEquals(99, test2.getLevel());
      assertEquals("HELLO", test2.getName());
      assertEquals(99, test2.hashCode());
      assertEquals("HELLO", test2.toString());
      
      assertEquals(test1, test2);
   }

   public void testScopeLevelComparison() throws Exception
   {
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      ScopeLevel test2 = new ScopeLevel(2, "HELLO");

      assertTrue(test1.compareTo(test2) < 0);
      assertTrue(test2.compareTo(test1) > 0);

      ScopeLevel test3 = new ScopeLevel(3, "HELLO");

      assertTrue(test1.compareTo(test3) < 0);
      assertTrue(test3.compareTo(test1) > 0);
      assertTrue(test2.compareTo(test3) < 0);
      assertTrue(test3.compareTo(test2) > 0);

      ScopeLevel test4 = new ScopeLevel(1, "HELLO");

      assertTrue(test1.compareTo(test4) == 0);
      
      ScopeLevel test5 = new ScopeLevel(1, "DIFFERENT");

      assertTrue(test1.compareTo(test5) == 0);
   }

   public void testScopeLevelSetBehaviourDuplicates() throws Exception
   {
      HashSet<ScopeLevel> set = new HashSet<ScopeLevel>(); 
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      set.add(test1);
      checkSet(set);
      ScopeLevel test2 = new ScopeLevel(1, "HELLO");
      set.add(test2);
      checkSet(set);
      ScopeLevel test3 = new ScopeLevel(1, "DIFFERENT");
      set.add(test3);
      checkSet(set);
   }
   
   protected void checkSet(HashSet<ScopeLevel> set) throws Exception
   {
      assertEquals(1, set.size());
      ScopeLevel test = set.iterator().next();
      assertEquals(1, test.getLevel());
      assertEquals("HELLO", test.getName());
   }

   public void testScopeLevelSet() throws Exception
   {
      LinkedHashSet<ScopeLevel> set = new LinkedHashSet<ScopeLevel>(); 
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      set.add(test1);
      ScopeLevel test2 = new ScopeLevel(2, "HELLO");
      set.add(test2);
      ScopeLevel test3 = new ScopeLevel(3, "DIFFERENT");
      set.add(test3);
      
      assertEquals(3, set.size());
      Iterator<ScopeLevel> i = set.iterator();
      ScopeLevel test = i.next();
      assertEquals(1, test.getLevel());
      assertEquals("HELLO", test.getName());
      test = i.next();
      assertEquals(2, test.getLevel());
      assertEquals("HELLO", test.getName());
      test = i.next();
      assertEquals(3, test.getLevel());
      assertEquals("DIFFERENT", test.getName());
   }

   public void testScopeLevelMapBehaviourDuplicates() throws Exception
   {
      HashMap<ScopeLevel, Object> map = new HashMap<ScopeLevel, Object>(); 
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      map.put(test1, new Object());
      checkMap(map);
      ScopeLevel test2 = new ScopeLevel(1, "HELLO");
      map.put(test2, new Object());
      checkMap(map);
      ScopeLevel test3 = new ScopeLevel(1, "DIFFERENT");
      map.put(test3, new Object());
      checkMap(map);
   }
   
   protected void checkMap(HashMap<ScopeLevel, Object> map) throws Exception
   {
      assertEquals(1, map.size());
      ScopeLevel test = map.keySet().iterator().next();
      assertEquals(1, test.getLevel());
      assertEquals("HELLO", test.getName());
   }

   public void testScopeLevelMap() throws Exception
   {
      LinkedHashMap<ScopeLevel, Object> map = new LinkedHashMap<ScopeLevel, Object>(); 
      ScopeLevel test1 = new ScopeLevel(1, "HELLO");
      Object object1 = new Object();
      map.put(test1, object1);
      ScopeLevel test2 = new ScopeLevel(2, "HELLO");
      Object object2 = new Object();
      map.put(test2, object2);
      ScopeLevel test3 = new ScopeLevel(3, "DIFFERENT");
      Object object3 = new Object();
      map.put(test3, object3);
      
      assertEquals(3, map.size());
      Iterator<ScopeLevel> i = map.keySet().iterator();
      ScopeLevel test = i.next();
      assertEquals(1, test.getLevel());
      assertEquals("HELLO", test.getName());
      test = i.next();
      assertEquals(2, test.getLevel());
      assertEquals("HELLO", test.getName());
      test = i.next();
      assertEquals(3, test.getLevel());
      assertEquals("DIFFERENT", test.getName());
   }
}
