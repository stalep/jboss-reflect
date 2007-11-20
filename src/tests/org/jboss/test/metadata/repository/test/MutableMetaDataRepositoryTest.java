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
package org.jboss.test.metadata.repository.test;

import java.util.HashSet;
import java.util.Set;

import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.repository.support.TestMetaDataRetrievalFactory;

/**
 * MutableMetaDataRepositoryTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MutableMetaDataRepositoryTest extends AbstractMetaDataTest
{
   private static ScopeLevel testLevel1 = new ScopeLevel(1, "ONE");
   private static ScopeLevel testLevel2 = new ScopeLevel(2, "TWO");
   private static ScopeLevel testLevel3 = new ScopeLevel(3, "THREE");
   
   private static String testQualifier1 = "qualifier1";
   private static String testQualifier1Different = "qualifier1Different";
   private static String testQualifier2 = "qualifier2";
   private static String testQualifier2Different = "qualifier2Different";
   private static String testQualifier3 = "qualifier3";
   
   private static Scope testScope1 = new Scope(testLevel1, testQualifier1);
   private static Scope testScope1Different = new Scope(testLevel1, testQualifier1Different);
   private static Scope testScope2 = new Scope(testLevel2, testQualifier2);
   private static Scope testScope2Different = new Scope(testLevel2, testQualifier2Different);
   private static Scope testScope3 = new Scope(testLevel3, testQualifier3);
   
   private static ScopeKey testKey1 = new ScopeKey(testScope1);
   private static ScopeKey testKey2 = new ScopeKey(testScope2);
   private static ScopeKey testKey1Different = new ScopeKey(testScope1Different);
   private static ScopeKey testKey12 = new ScopeKey(new Scope[] { testScope1, testScope2 });
   private static ScopeKey testKey12Different = new ScopeKey(new Scope[] { testScope1, testScope2Different });
   private static ScopeKey testKey123 = new ScopeKey(new Scope[] { testScope1, testScope2, testScope3 });
   private static ScopeKey testKey123Different = new ScopeKey(new Scope[] { testScope1Different, testScope2, testScope3 });

   public MutableMetaDataRepositoryTest(String name)
   {
      super(name);
   }

   protected abstract MutableMetaDataRepository setupEmpty();
   
   public void testAddOneScope() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader == retrieval);
   }
   
   public void testAddOneScopeDifferent() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);
      MutableMetaDataLoader loader1Different = createTestMutableMetaDataLoader(testKey1Different);
      assertAddMetaDataRetrieval(repository, loader1Different, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader1 == retrieval);
      
      retrieval = repository.getMetaDataRetrieval(testKey1Different);
      assertTrue(loader1Different == retrieval);
   }
   
   public void testReplaceOneScope() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader1 == retrieval);

      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader2, loader1);
      
      retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader2 == retrieval);
   }
   
   public void testRemoveOneScope() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader1 == retrieval);

      assertRemoveMetaDataRetrieval(repository, testKey1, loader1);
      
      retrieval = repository.getMetaDataRetrieval(testKey1);
      assertNull(retrieval);
   }
   
   public void testRemoveOneScopeDifferent() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader1 == retrieval);

      assertRemoveMetaDataRetrieval(repository, testKey1Different, null);
      
      retrieval = repository.getMetaDataRetrieval(testKey1);
      assertTrue(loader1 == retrieval);
   }
   
   public void testAddMutlipleScope() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader == retrieval);
   }
   
   public void testAddMultipleScopeDifferent() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader1, null);
      MutableMetaDataLoader loader1Different = createTestMutableMetaDataLoader(testKey123Different);
      assertAddMetaDataRetrieval(repository, loader1Different, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader1 == retrieval);
      
      retrieval = repository.getMetaDataRetrieval(testKey123Different);
      assertTrue(loader1Different == retrieval);
   }
   
   public void testReplaceMultipleScope() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader1, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader1 == retrieval);

      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader2, loader1);
      
      retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader2 == retrieval);
   }
   
   public void testRemoveMultipleScope() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader1, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader1 == retrieval);

      assertRemoveMetaDataRetrieval(repository, testKey123, loader1);
      
      retrieval = repository.getMetaDataRetrieval(testKey1);
      assertNull(retrieval);
   }
   
   public void testRemoveMultipleScopeDifferent() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader1, null);
      
      MetaDataRetrieval retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader1 == retrieval);

      assertRemoveMetaDataRetrieval(repository, testKey123Different, null);
      
      retrieval = repository.getMetaDataRetrieval(testKey123);
      assertTrue(loader1 == retrieval);
   }
   
   public void testGetChildrenEmpty() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      Set<ScopeKey> result = repository.getChildren(testKey1);
      assertNotNull(result);
      assertEquals(0, result.size()); 
   }
   
   public void testGetNoChildren() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);

      Set<ScopeKey> result = repository.getChildren(testKey1);
      assertNotNull(result);
      assertEquals(0, result.size()); 
   }
   
   public void testOneChild() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);

      MutableMetaDataLoader loader12 = createTestMutableMetaDataLoader(testKey12);
      assertAddMetaDataRetrieval(repository, loader12, null);

      Set<ScopeKey> result = repository.getChildren(testKey1);
      assertNotNull(result);
      
      Set<ScopeKey> expected = new HashSet<ScopeKey>();
      expected.add(testKey12);
      assertEquals(expected, result);
   }
   
   public void testTwoChild() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);

      MutableMetaDataLoader loader12 = createTestMutableMetaDataLoader(testKey12);
      assertAddMetaDataRetrieval(repository, loader12, null);

      MutableMetaDataLoader loader12Different = createTestMutableMetaDataLoader(testKey12Different);
      assertAddMetaDataRetrieval(repository, loader12Different, null);

      Set<ScopeKey> result = repository.getChildren(testKey1);
      assertNotNull(result);
      
      Set<ScopeKey> expected = new HashSet<ScopeKey>();
      expected.add(testKey12);
      expected.add(testKey12Different);
      assertEquals(expected, result);
   }
   
   public void testTooManyScopes() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      assertAddMetaDataRetrieval(repository, loader1, null);
      MutableMetaDataLoader loader123 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader123, null);

      Set<ScopeKey> result = repository.getChildren(testKey1);
      assertNotNull(result);
      assertEquals(0, result.size()); 
   }
   
   public void testManyScopes() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader12 = createTestMutableMetaDataLoader(testKey12);
      assertAddMetaDataRetrieval(repository, loader12, null);
      MutableMetaDataLoader loader123 = createTestMutableMetaDataLoader(testKey123);
      assertAddMetaDataRetrieval(repository, loader123, null);

      Set<ScopeKey> result = repository.getChildren(testKey12);
      assertNotNull(result);
      
      Set<ScopeKey> expected = new HashSet<ScopeKey>();
      expected.add(testKey123);
      assertEquals(expected, result);
   }
   
   public void testAddMetaDataRetrievalFactory() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      assertNull(repository.getMetaDataRetrievalFactory(testLevel1));
      TestMetaDataRetrievalFactory factory1 = new TestMetaDataRetrievalFactory();
      assertNull(repository.addMetaDataRetrievalFactory(testLevel1, factory1));
      assertEquals(factory1, repository.getMetaDataRetrievalFactory(testLevel1));
      TestMetaDataRetrievalFactory factory2 = new TestMetaDataRetrievalFactory();
      assertEquals(factory1, repository.addMetaDataRetrievalFactory(testLevel1, factory2));
      assertEquals(factory2, repository.getMetaDataRetrievalFactory(testLevel1));
      TestMetaDataRetrievalFactory factory3 = new TestMetaDataRetrievalFactory();
      assertNull(repository.addMetaDataRetrievalFactory(testLevel2, factory3));
      assertEquals(factory2, repository.getMetaDataRetrievalFactory(testLevel1));
      assertEquals(factory3, repository.getMetaDataRetrievalFactory(testLevel2));
   }
   
   public void testRemoveMetaDataRetrievalFactory() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      assertNull(repository.getMetaDataRetrievalFactory(testLevel1));
      TestMetaDataRetrievalFactory factory1 = new TestMetaDataRetrievalFactory();
      assertNull(repository.addMetaDataRetrievalFactory(testLevel1, factory1));
      assertEquals(factory1, repository.removeMetaDataRetrievalFactory(testLevel1));
      assertNull(repository.getMetaDataRetrievalFactory(testLevel1));
      assertNull(repository.removeMetaDataRetrievalFactory(testLevel1));
      TestMetaDataRetrievalFactory factory2 = new TestMetaDataRetrievalFactory();
      assertNull(repository.addMetaDataRetrievalFactory(testLevel1, factory1));
      assertNull(repository.addMetaDataRetrievalFactory(testLevel2, factory2));
      assertEquals(factory1, repository.removeMetaDataRetrievalFactory(testLevel1));
      assertNull(repository.getMetaDataRetrievalFactory(testLevel1));
      assertNull(repository.removeMetaDataRetrievalFactory(testLevel1));
      assertEquals(factory2, repository.getMetaDataRetrievalFactory(testLevel2));
   }

   public void testBasicMetaDataRetrievalFactory() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      TestMetaDataRetrievalFactory factory1 = new TestMetaDataRetrievalFactory();
      repository.addMetaDataRetrievalFactory(testLevel1, factory1);
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      factory1.retrievals.put(testScope1, loader1);
      assertEquals(loader1, repository.getMetaDataRetrieval(testKey1));
   }

   public void testMultipleRetrievalMetaDataRetrievalFactory() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      TestMetaDataRetrievalFactory factory1 = new TestMetaDataRetrievalFactory();
      repository.addMetaDataRetrievalFactory(testLevel1, factory1);
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      factory1.retrievals.put(testScope1, loader1);
      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey1Different);
      factory1.retrievals.put(testScope1Different, loader2);
      assertEquals(loader1, repository.getMetaDataRetrieval(testKey1));
      assertEquals(loader2, repository.getMetaDataRetrieval(testKey1Different));
   }

   public void testMultipleMetaDataRetrievalFactories() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      TestMetaDataRetrievalFactory factory1 = new TestMetaDataRetrievalFactory();
      repository.addMetaDataRetrievalFactory(testLevel1, factory1);
      TestMetaDataRetrievalFactory factory2 = new TestMetaDataRetrievalFactory();
      repository.addMetaDataRetrievalFactory(testLevel2, factory2);
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      factory1.retrievals.put(testScope1, loader1);
      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey2);
      factory2.retrievals.put(testScope2, loader2);
      assertEquals(loader1, repository.getMetaDataRetrieval(testKey1));
      assertEquals(loader2, repository.getMetaDataRetrieval(testKey2));
   }
   
   protected void assertAddMetaDataRetrieval(MutableMetaDataRepository repository, MetaDataRetrieval add, MetaDataRetrieval expected) throws Exception
   {
      MetaDataRetrieval previous = repository.addMetaDataRetrieval(add);
      assertEquals(expected, previous);
   }
   
   protected void assertRemoveMetaDataRetrieval(MutableMetaDataRepository repository, ScopeKey key, MetaDataRetrieval expected) throws Exception
   {
      MetaDataRetrieval previous = repository.removeMetaDataRetrieval(key);
      assertEquals(expected, previous);
   }
}
