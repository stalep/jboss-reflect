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

import org.jboss.metadata.plugins.repository.visitor.FuzzyMetaDataRepositoryVisitor;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.repository.MutableMetaDataRepository;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * MutableMetaDataRepositoryMatchingTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MutableMetaDataRepositoryMatchingTest extends AbstractMetaDataTest
{
   private static ScopeLevel testLevel1 = new ScopeLevel(1, "ONE");
   private static ScopeLevel testLevel2 = new ScopeLevel(2, "TWO");
   private static ScopeLevel testLevel3 = new ScopeLevel(3, "THREE");
   private static ScopeLevel testLevel4 = new ScopeLevel(4, "FOUR");
   private static ScopeLevel testLevel5 = new ScopeLevel(5, "FIVE");
   private static ScopeLevel testLevel6 = new ScopeLevel(6, "FIVE");
   
   private static String testQualifier1 = "qualifier1";
   private static String testQualifier1x = "qualifier1x";
   private static String testQualifier2 = "qualifier2";
   private static String testQualifier2x = "qualifier2x";
   private static String testQualifier3 = "qualifier3";
   private static String testQualifier4 = "qualifier4";
   private static String testQualifier5 = "qualifier5";
   private static String testQualifier6 = "qualifier6";
   
   private static Scope testLevel1Qual1 = new Scope(testLevel1, testQualifier1);
   private static Scope testLevel1Qual1x = new Scope(testLevel1, testQualifier1x);
   private static Scope testLevel2Qual2 = new Scope(testLevel2, testQualifier2);
   private static Scope testLevel2Qual2x = new Scope(testLevel2, testQualifier2x);
   private static Scope testLevel3Qual3 = new Scope(testLevel3, testQualifier3);
   private static Scope testLevel4Qual4 = new Scope(testLevel4, testQualifier4);
   private static Scope testLevel5Qual5 = new Scope(testLevel5, testQualifier5);
   private static Scope testLevel6Qual6 = new Scope(testLevel6, testQualifier6);

   private static ScopeKey testKey1 = new ScopeKey(testLevel1Qual1);
   private static ScopeKey testKey1x = new ScopeKey(testLevel1Qual1x);
   private static ScopeKey testKey2 = new ScopeKey(testLevel2Qual2);
   private static ScopeKey testKey3 = new ScopeKey(testLevel3Qual3);

   private static ScopeKey testKey12 = new ScopeKey(new Scope[] { testLevel1Qual1, testLevel2Qual2 });
   private static ScopeKey testKey13 = new ScopeKey(new Scope[] { testLevel1Qual1, testLevel3Qual3 });
   private static ScopeKey testKey14 = new ScopeKey(new Scope[] { testLevel1Qual1, testLevel4Qual4 });
   private static ScopeKey testKey15 = new ScopeKey(new Scope[] { testLevel1Qual1, testLevel5Qual5 });
   private static ScopeKey testKey16 = new ScopeKey(new Scope[] { testLevel1Qual1, testLevel6Qual6 });
   private static ScopeKey testKey23 = new ScopeKey(new Scope[] { testLevel2Qual2, testLevel3Qual3 });
   private static ScopeKey testKey2x3 = new ScopeKey(new Scope[] { testLevel2Qual2x, testLevel3Qual3 });
   
   public MutableMetaDataRepositoryMatchingTest(String name)
   {
      super(name);
   }

   protected abstract MutableMetaDataRepository setupEmpty();
   
   public void testNoMatchEmpty() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey1);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   
   public void testSimpleMatch() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      repository.addMetaDataRetrieval(loader1);
      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey2);
      repository.addMetaDataRetrieval(loader2);
      MutableMetaDataLoader loader3 = createTestMutableMetaDataLoader(testKey3);
      repository.addMetaDataRetrieval(loader3);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey1);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      
      HashSet<ScopeKey> expected = new HashSet<ScopeKey>();
      expected.add(testKey1);
      
      assertEquals(expected, result);
   }
   
   public void testNotSimpleMatch() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      repository.addMetaDataRetrieval(loader1);
      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey2);
      repository.addMetaDataRetrieval(loader2);
      MutableMetaDataLoader loader3 = createTestMutableMetaDataLoader(testKey3);
      repository.addMetaDataRetrieval(loader3);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey1x);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   
   public void testNoMatchAgainstSimple() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader1 = createTestMutableMetaDataLoader(testKey1);
      repository.addMetaDataRetrieval(loader1);
      MutableMetaDataLoader loader2 = createTestMutableMetaDataLoader(testKey2);
      repository.addMetaDataRetrieval(loader2);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey3);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   
   public void testCompositeMatch() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader23 = createTestMutableMetaDataLoader(testKey23);
      repository.addMetaDataRetrieval(loader23);
      MutableMetaDataLoader loader14 = createTestMutableMetaDataLoader(testKey14);
      repository.addMetaDataRetrieval(loader14);
      MutableMetaDataLoader loader15 = createTestMutableMetaDataLoader(testKey15);
      repository.addMetaDataRetrieval(loader15);
      MutableMetaDataLoader loader16 = createTestMutableMetaDataLoader(testKey16);
      repository.addMetaDataRetrieval(loader16);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey2);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      
      HashSet<ScopeKey> expected = new HashSet<ScopeKey>();
      expected.add(testKey23);
      
      assertEquals(expected, result);
   }
   
   public void testNotCompositeMatch() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader23 = createTestMutableMetaDataLoader(testKey23);
      repository.addMetaDataRetrieval(loader23);
      MutableMetaDataLoader loader14 = createTestMutableMetaDataLoader(testKey14);
      repository.addMetaDataRetrieval(loader14);
      MutableMetaDataLoader loader15 = createTestMutableMetaDataLoader(testKey15);
      repository.addMetaDataRetrieval(loader15);
      MutableMetaDataLoader loader16 = createTestMutableMetaDataLoader(testKey16);
      repository.addMetaDataRetrieval(loader16);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey2x3);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   
   public void testNoMatchEmptyAgainstComposite() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader12 = createTestMutableMetaDataLoader(testKey12);
      repository.addMetaDataRetrieval(loader12);
      MutableMetaDataLoader loader14 = createTestMutableMetaDataLoader(testKey14);
      repository.addMetaDataRetrieval(loader14);
      MutableMetaDataLoader loader15 = createTestMutableMetaDataLoader(testKey15);
      repository.addMetaDataRetrieval(loader15);
      MutableMetaDataLoader loader16 = createTestMutableMetaDataLoader(testKey16);
      repository.addMetaDataRetrieval(loader16);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey3);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      assertNotNull(result);
      assertEquals(0, result.size());
   }
   
   public void testPartialMatch() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader12 = createTestMutableMetaDataLoader(testKey12);
      repository.addMetaDataRetrieval(loader12);
      MutableMetaDataLoader loader13 = createTestMutableMetaDataLoader(testKey13);
      repository.addMetaDataRetrieval(loader13);
      MutableMetaDataLoader loader23 = createTestMutableMetaDataLoader(testKey23);
      repository.addMetaDataRetrieval(loader23);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey1);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      
      HashSet<ScopeKey> expected = new HashSet<ScopeKey>();
      expected.add(testKey12);
      expected.add(testKey13);
      
      assertEquals(expected, result);
   }
   
   public void testNotPartialMatch() throws Exception
   {
      MutableMetaDataRepository repository = setupEmpty();
      MutableMetaDataLoader loader12 = createTestMutableMetaDataLoader(testKey12);
      repository.addMetaDataRetrieval(loader12);
      MutableMetaDataLoader loader13 = createTestMutableMetaDataLoader(testKey13);
      repository.addMetaDataRetrieval(loader13);
      MutableMetaDataLoader loader23 = createTestMutableMetaDataLoader(testKey23);
      repository.addMetaDataRetrieval(loader23);
      
      FuzzyMetaDataRepositoryVisitor visitor = new FuzzyMetaDataRepositoryVisitor(testKey1x);
      Set<ScopeKey> result = repository.matchScopes(visitor);
      assertNotNull(result);
      assertEquals(0, result.size());
   }
}
