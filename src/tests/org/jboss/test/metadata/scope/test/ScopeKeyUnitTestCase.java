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

import java.util.Collection;
import java.util.HashSet;

import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * ScopeKeyUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ScopeKeyUnitTestCase extends AbstractMetaDataTest
{
   private static ScopeLevel testLevel1 = new ScopeLevel(1, "ONE");
   private static ScopeLevel testLevel2 = new ScopeLevel(2, "TWO");
   private static ScopeLevel testLevel3 = new ScopeLevel(3, "THREE");
   private static ScopeLevel testLevel4 = new ScopeLevel(4, "FOUR");
   private static ScopeLevel testLevel5 = new ScopeLevel(5, "FIVE");
   
   private static String testQualifier1 = "qualifier1";
   private static String testQualifier1Different = "qualifier1Different";
   private static String testQualifier2 = "qualifier2";
   private static String testQualifier3 = "qualifier3";
   private static String testQualifier3Different = "qualifier3Different";
   private static String testQualifier4 = "qualifier4";
   private static String testQualifier5 = "qualifier5";
   
   private static Scope testScope1 = new Scope(testLevel1, testQualifier1);
   private static Scope testScope1Different = new Scope(testLevel1, testQualifier1Different);
   private static Scope testScope2 = new Scope(testLevel2, testQualifier2);
   private static Scope testScope3 = new Scope(testLevel3, testQualifier3);
   private static Scope testScope3Different = new Scope(testLevel3, testQualifier3Different);
   private static Scope testScope4 = new Scope(testLevel4, testQualifier4);
   private static Scope testScope5 = new Scope(testLevel5, testQualifier5);
   
   public ScopeKeyUnitTestCase(String name)
   {
      super(name);
   }

   public void testBasicScopeKeyConstructor() throws Exception
   {
      ScopeKey key = new ScopeKey();
      Scope[] expected = new Scope[0];
      assertScopeKey(expected, key);
      assertNull(key.getMaxScopeLevel());
   }

   public void testSingleScopeKeyConstructor() throws Exception
   {
      ScopeKey key = new ScopeKey(testScope1);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testLevelQualifierScopeKeyConstructor() throws Exception
   {
      ScopeKey key = new ScopeKey(testLevel1, testQualifier1);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testCollectionScopeKeyConstructor() throws Exception
   {
      Scope[] expected = new Scope[] {};
      HashSet<Scope> set = new HashSet<Scope>();
      ScopeKey key = new ScopeKey(set);
      assertScopeKey(expected, key);
      
      expected = new Scope[] { testScope1 };
      set = new HashSet<Scope>();
      for (Scope scope : expected)
         set.add(scope);
      key = new ScopeKey(set);
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());
      
      expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      set = new HashSet<Scope>();
      for (Scope scope : expected)
         set.add(scope);
      key = new ScopeKey(set);
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testCollectionScopeKeyConstructorSorts() throws Exception
   {
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      HashSet<Scope> set = new HashSet<Scope>();
      set.add(testScope2);
      set.add(testScope4);
      set.add(testScope5);
      set.add(testScope3);
      set.add(testScope1);
      ScopeKey key = new ScopeKey(set);
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testArrayScopeKeyConstructor() throws Exception
   {
      Scope[] expected = new Scope[] {};
      ScopeKey key = new ScopeKey(expected);
      assertScopeKey(expected, key);
      
      expected = new Scope[] { testScope1 };
      key = new ScopeKey(expected);
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());
      
      expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      key = new ScopeKey(expected);
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testArrayScopeKeyConstructorSorts() throws Exception
   {
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      Scope[] array = new Scope[] { testScope5, testScope3, testScope1, testScope2, testScope4 };
      ScopeKey key = new ScopeKey(array);
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }
   
   public void testAddScope() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testScope2, null);
      expected = new Scope[] { testScope1, testScope2 };
      assertScopeKey(expected, key);
      assertEquals(testScope2.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeDuplicate() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testScope1, testScope1);
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeSorts() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      assertAddScope(key, testScope3, null);
      assertAddScope(key, testScope5, null);
      assertAddScope(key, testScope2, null);
      assertAddScope(key, testScope4, null);
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeChange() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testScope1Different, testScope1);
      expected = new Scope[] { testScope1Different };
      assertScopeKey(expected, key);
      assertEquals(testScope1Different.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeChangeSorts() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      assertAddScope(key, testScope2, null);
      assertAddScope(key, testScope3, null);
      assertAddScope(key, testScope4, null);
      assertAddScope(key, testScope5, null);
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testScope3Different, testScope3);
      expected = new Scope[] { testScope1, testScope2, testScope3Different, testScope4, testScope5 };
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeLevelQualifier() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel1, testQualifier1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testLevel2, testQualifier2, null);
      expected = new Scope[] { testScope1, testScope2 };
      assertScopeKey(expected, key);
      assertEquals(testScope2.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeLevelQualifierDuplicate() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel1, testQualifier1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testLevel1, testQualifier1, testScope1);
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeLevelQualifierSorts() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel4, testQualifier4, null);
      assertAddScope(key, testLevel2, testQualifier2, null);
      assertAddScope(key, testLevel1, testQualifier1, null);
      assertAddScope(key, testLevel3, testQualifier3, null);
      assertAddScope(key, testLevel5, testQualifier5, null);
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testAddScopeLevelChange() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel1, testQualifier1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testLevel1, testQualifier1Different, testScope1);
      expected = new Scope[] { testScope1Different };
      assertScopeKey(expected, key);
      assertEquals(testScope1Different.getScopeLevel(), key.getMaxScopeLevel());
 }

   public void testAddScopeLevelChangeSorts() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel1, testQualifier1, null);
      assertAddScope(key, testLevel2, testQualifier2, null);
      assertAddScope(key, testLevel3, testQualifier3, null);
      assertAddScope(key, testLevel4, testQualifier4, null);
      assertAddScope(key, testLevel5, testQualifier5, null);
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3, testScope4, testScope5 };
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());

      assertAddScope(key, testLevel3, testQualifier3Different, testScope3);
      expected = new Scope[] { testScope1, testScope2, testScope3Different, testScope4, testScope5 };
      assertScopeKey(expected, key);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testRemoveScope() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertRemoveScope(key, testScope1, testScope1);
      expected = new Scope[0];
      assertScopeKey(expected, key);
      assertNull(key.getMaxScopeLevel());
   }
   
   public void testRemoveScopeSorts() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testScope1, null);
      assertAddScope(key, testScope2, null);
      assertAddScope(key, testScope3, null);
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3 };
      assertScopeKey(expected, key);
      assertEquals(testScope3.getScopeLevel(), key.getMaxScopeLevel());

      assertRemoveScope(key, testScope2, testScope2);
      expected = new Scope[] { testScope1, testScope3 };
      assertScopeKey(expected, key);
      assertEquals(testScope3.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testRemoveScopeLevel() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel1, testQualifier1, null);
      Scope[] expected = new Scope[] { testScope1 };
      assertScopeKey(expected, key);
      assertEquals(testScope1.getScopeLevel(), key.getMaxScopeLevel());

      assertRemoveScope(key, testLevel1, testScope1);
      expected = new Scope[0];
      assertScopeKey(expected, key);
      assertNull(key.getMaxScopeLevel());
   }

   public void testRemoveScopeLevelSorts() throws Exception
   {
      ScopeKey key = new ScopeKey();
      assertAddScope(key, testLevel1, testQualifier1, null);
      assertAddScope(key, testLevel2, testQualifier2, null);
      assertAddScope(key, testLevel3, testQualifier3, null);
      Scope[] expected = new Scope[] { testScope1, testScope2, testScope3 };
      assertScopeKey(expected, key);
      assertEquals(testScope3.getScopeLevel(), key.getMaxScopeLevel());

      assertRemoveScope(key, testLevel2, testScope2);
      expected = new Scope[] { testScope1, testScope3 };
      assertScopeKey(expected, key);
      assertEquals(testScope3.getScopeLevel(), key.getMaxScopeLevel());
   }

   public void testFreeze() throws Exception
   {
      ScopeKey key = new ScopeKey(testScope1);
      key.freeze();
      try
      {
         key.addScope(testScope1);
         fail("Should not be here!");
      }
      catch (IllegalStateException expected)
      {
      }
      try
      {
         key.addScope(testLevel1, testQualifier1);
         fail("Should not be here!");
      }
      catch (IllegalStateException expected)
      {
      }
      try
      {
         key.removeScope(testScope1);
         fail("Should not be here!");
      }
      catch (IllegalStateException expected)
      {
      }
      try
      {
         key.removeScopeLevel(testLevel1);
         fail("Should not be here!");
      }
      catch (IllegalStateException expected)
      {
      }
   }

   public void testNoParentEmpty() throws Exception
   {
      ScopeKey key = new ScopeKey();
      ScopeKey parent = key.getParent();
      assertNull(parent);
   }

   public void testNoParentSingleScope() throws Exception
   {
      ScopeKey key = new ScopeKey();
      key.addScope(testScope1);
      ScopeKey parent = key.getParent();
      assertNull(parent);
   }

   public void testSimpleParent() throws Exception
   {
      ScopeKey key = new ScopeKey();
      key.addScope(testScope1);
      key.addScope(testScope2);
      ScopeKey parent = key.getParent();
      assertNotNull(parent);
      
      ScopeKey expected = new ScopeKey();
      expected.addScope(testScope1);
      assertEquals(expected, parent);
      assertEquals(testScope1.getScopeLevel(), parent.getMaxScopeLevel());
   }

   public void testComplexParent() throws Exception
   {
      ScopeKey key = new ScopeKey();
      key.addScope(testScope1);
      key.addScope(testScope2);
      key.addScope(testScope3);
      key.addScope(testScope4);
      key.addScope(testScope5);
      assertEquals(testScope5.getScopeLevel(), key.getMaxScopeLevel());

      ScopeKey parent = key.getParent();
      assertNotNull(parent);
      ScopeKey expected = new ScopeKey();
      expected.addScope(testScope1);
      expected.addScope(testScope2);
      expected.addScope(testScope3);
      expected.addScope(testScope4);
      assertEquals(expected, parent);
      assertEquals(testScope4.getScopeLevel(), parent.getMaxScopeLevel());

      parent = parent.getParent();
      assertNotNull(parent);
      expected = new ScopeKey();
      expected.addScope(testScope1);
      expected.addScope(testScope2);
      expected.addScope(testScope3);
      assertEquals(expected, parent);
      assertEquals(testScope3.getScopeLevel(), parent.getMaxScopeLevel());

      parent = parent.getParent();
      assertNotNull(parent);
      expected = new ScopeKey();
      expected.addScope(testScope1);
      expected.addScope(testScope2);
      assertEquals(expected, parent);
      assertEquals(testScope2.getScopeLevel(), parent.getMaxScopeLevel());

      parent = parent.getParent();
      assertNotNull(parent);
      expected = new ScopeKey();
      expected.addScope(testScope1);
      assertEquals(expected, parent);
      assertEquals(testScope1.getScopeLevel(), parent.getMaxScopeLevel());

      parent = parent.getParent();
      assertNull(parent);
   }

   public void testIsParentNoScopes() throws Exception
   {
      ScopeKey parent = new ScopeKey(testScope1);
      ScopeKey test = new ScopeKey();
      assertFalse(parent.isParent(test));
   }

   public void testIsParentSameScope() throws Exception
   {
      ScopeKey parent = new ScopeKey(testScope1);
      assertFalse(parent.isParent(parent));
      
      ScopeKey test = new ScopeKey(testScope1);
      assertFalse(parent.isParent(test));
   }

   public void testIsParentDifferentLevel() throws Exception
   {
      ScopeKey parent = new ScopeKey(testScope1);
      ScopeKey test = new ScopeKey(testScope2);
      assertFalse(parent.isParent(test));
   }

   public void testIsParentDifferentQualifier() throws Exception
   {
      ScopeKey parent = new ScopeKey(testScope1);
      ScopeKey test = new ScopeKey(testScope1Different);
      assertFalse(parent.isParent(test));
   }

   public void testIsParent() throws Exception
   {
      ScopeKey parent = new ScopeKey(testScope1);
      ScopeKey test = new ScopeKey();
      test.addScope(testScope1);
      test.addScope(testScope2);
      assertTrue(parent.isParent(test));

      test = new ScopeKey();
      test.addScope(testScope1);
      test.addScope(testScope3);
      assertTrue(parent.isParent(test));
   }

   public void testIsParentNotDirectly() throws Exception
   {
      ScopeKey parent = new ScopeKey(testScope1);
      ScopeKey test = new ScopeKey();
      test.addScope(testScope1);
      test.addScope(testScope2);
      test.addScope(testScope3);
      assertFalse(parent.isParent(test));
   }

   public void testIsParentNotChild() throws Exception
   {
      ScopeKey parent = new ScopeKey();
      parent.addScope(testScope1);
      parent.addScope(testScope2);
      
      ScopeKey test = new ScopeKey();
      test.addScope(testScope1);
      assertFalse(parent.isParent(test));
   }

   public void testIsParentComplicated() throws Exception
   {
      ScopeKey parent = new ScopeKey();
      parent.addScope(testScope1);
      parent.addScope(testScope2);
      parent.addScope(testScope3);
      parent.addScope(testScope4);
      
      ScopeKey test = new ScopeKey();
      test.addScope(testScope1);
      test.addScope(testScope2);
      test.addScope(testScope3);
      test.addScope(testScope4);
      test.addScope(testScope5);
      assertTrue(parent.isParent(test));
   }
   
   public void testEqualsEmpty() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertEquals(key1, key2);
   }

   public void testEqualsAfterAdd() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertAddScope(key1, testScope1, null);
      assertAddScope(key2, testScope1, null);
      assertAddScope(key1, testScope2, null);
      assertAddScope(key2, testScope2, null);
      assertAddScope(key1, testScope3, null);
      assertAddScope(key2, testScope3, null);
      assertEquals(key1, key2);
   }

   public void testEqualsAfterRemove() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertAddScope(key1, testScope1, null);
      assertAddScope(key2, testScope1, null);
      assertAddScope(key1, testScope2, null);
      assertAddScope(key2, testScope2, null);
      assertAddScope(key1, testScope3, null);
      assertAddScope(key2, testScope3, null);
      assertRemoveScope(key1, testScope2, testScope2);
      assertRemoveScope(key2, testScope2, testScope2);
      assertEquals(key1, key2);
   }

   public void testEqualsAfterReplace() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertAddScope(key1, testScope1, null);
      assertAddScope(key2, testScope1, null);
      assertAddScope(key1, testScope2, null);
      assertAddScope(key2, testScope2, null);
      assertAddScope(key1, testScope3, null);
      assertAddScope(key2, testScope3, null);
      assertAddScope(key1, testScope3Different, testScope3);
      assertAddScope(key2, testScope3Different, testScope3);
      assertEquals(key1, key2);
   }

   public void testNotEqualsAfterAdd() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertAddScope(key1, testScope1, null);
      assertAddScope(key2, testScope1, null);
      assertAddScope(key1, testScope2, null);
      assertAddScope(key2, testScope2, null);
      assertEquals(key1, key2);
      assertAddScope(key1, testScope3, null);
      assertFalse(key1.equals(key2));
   }

   public void testNotEqualsAfterRemove() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertAddScope(key1, testScope1, null);
      assertAddScope(key2, testScope1, null);
      assertAddScope(key1, testScope2, null);
      assertAddScope(key2, testScope2, null);
      assertAddScope(key1, testScope3, null);
      assertAddScope(key2, testScope3, null);
      assertEquals(key1, key2);
      assertRemoveScope(key1, testScope2, testScope2);
      assertFalse(key1.equals(key2));
   }

   public void testNotEqualsAfterReplace() throws Exception
   {
      ScopeKey key1 = new ScopeKey();
      ScopeKey key2 = new ScopeKey();
      assertAddScope(key1, testScope1, null);
      assertAddScope(key2, testScope1, null);
      assertAddScope(key1, testScope2, null);
      assertAddScope(key2, testScope2, null);
      assertAddScope(key1, testScope3, null);
      assertAddScope(key2, testScope3, null);
      assertEquals(key1, key2);
      assertAddScope(key1, testScope3Different, testScope3);
      assertFalse(key1.equals(key2));
   }
   
   protected void assertScopeKey(Scope[] scopes, ScopeKey key) throws Exception
   {
      assertNotNull(scopes);
      assertNotNull(key);
      
      Collection<Scope> result = key.getScopes();
      assertEquals(scopes.length, result.size());
      
      int index = 0;
      for (Scope scope : result)
         assertEquals(scopes[index++], scope);
   }

   protected void assertAddScope(ScopeKey key, Scope add, Scope expected) throws Exception
   {
      Scope previous = key.addScope(add);
      assertEquals(expected, previous);
   }

   protected void assertRemoveScope(ScopeKey key, Scope remove, Scope expected) throws Exception
   {
      Scope previous = key.removeScope(remove);
      assertEquals(expected, previous);
   }

   protected void assertAddScope(ScopeKey key, ScopeLevel level, String qualifier, Scope expected) throws Exception
   {
      Scope previous = key.addScope(level, qualifier);
      assertEquals(expected, previous);
   }

   protected void assertRemoveScope(ScopeKey key, ScopeLevel level, Scope expected) throws Exception
   {
      Scope previous = key.removeScopeLevel(level);
      assertEquals(expected, previous);
   }
}
