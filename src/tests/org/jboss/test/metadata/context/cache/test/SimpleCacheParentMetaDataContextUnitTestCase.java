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
package org.jboss.test.metadata.context.cache.test;

import java.lang.annotation.Annotation;
import java.util.List;

import org.jboss.metadata.plugins.context.AbstractMetaDataContext;
import org.jboss.metadata.plugins.context.CachingMetaDataContext;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.basic.BasicAnnotationItem;
import org.jboss.metadata.spi.retrieval.basic.BasicAnnotationsItem;
import org.jboss.test.metadata.context.AbstractMetaDataContextTest;
import org.jboss.test.metadata.context.cache.support.TestMetaDataLoader;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * SimpleCacheMetaDataContextUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SimpleCacheParentMetaDataContextUnitTestCase extends AbstractMetaDataContextTest
{
   public SimpleCacheParentMetaDataContextUnitTestCase(String name)
   {
      super(name);
   }

   protected MetaDataRetrieval createRetrieval()
   {
      return new TestMetaDataLoader();
   }

   protected MetaDataContext createContext(MetaDataContext parent, List<MetaDataRetrieval> retrievals)
   {
      return new CachingMetaDataContext(parent, retrievals);
   }

   protected MetaDataContext createChildContext(MetaDataContext parent, List<MetaDataRetrieval> retrievals)
   {
      return new AbstractMetaDataContext(parent, retrievals);
   }
   
   protected TestMetaDataLoader getFirstParent()
   {
      return (TestMetaDataLoader) firstParent;
   }
   
   protected TestMetaDataLoader getSecondParent()
   {
      return (TestMetaDataLoader) secondParent;
   }
   
   protected TestMetaDataLoader getFirstChild()
   {
      return (TestMetaDataLoader) firstChild;
   }
   
   protected TestMetaDataLoader getSecondChild()
   {
      return (TestMetaDataLoader) secondChild;
   }

   protected <T extends Annotation> BasicAnnotationItem<T> createAnnotationItem(TestMetaDataLoader loader, T annotation, Class<T> type)
   {
      return  new BasicAnnotationItem<T>(loader, annotation);
   }

   protected <T extends Annotation> void setAnnotations(TestMetaDataLoader loader, AnnotationItem<T> annotationItem)
   {
      AnnotationItem[] items = { annotationItem };
      BasicAnnotationsItem item = new BasicAnnotationsItem(loader, items);
      loader.setAnnotationsItem(item);
   }
   
   protected void testAnnotation(MetaData metaData, TestMetaDataLoader loader, boolean isChild) throws Exception
   {
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertTrue(loader.isRetrieved());
      
      BasicAnnotationItem<TestAnnotation> annotationItem = createAnnotationItem(loader, new TestAnnotationImpl(), TestAnnotation.class);
      setAnnotations(loader, annotationItem);
      
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(loader.isRetrieved());
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(isChild, loader.isRetrieved());
      
      loader.clear();

      assertNoAnnotation(metaData, TestAnnotation.class);
      assertTrue(loader.isRetrieved());
   }
   
   public void testAnnotationFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getFirstChild(), true);
   }
   
   public void testAnnotationSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getSecondChild(), true);
   }
   
   public void testAnnotationFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getFirstParent(), false);
   }
   
   public void testAnnotationSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getSecondParent(), false);
   }
   
   protected void testBelowAnnotation(MetaData metaData, TestMetaDataLoader aboveLoader, TestMetaDataLoader belowLoader, boolean aboveChild, boolean belowChild) throws Exception
   {
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertTrue(belowLoader.isRetrieved());
      
      BasicAnnotationItem<TestAnnotation> annotationItem = createAnnotationItem(aboveLoader, new TestAnnotationImpl(), TestAnnotation.class);
      setAnnotations(aboveLoader, annotationItem);
      
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(aboveChild, aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      annotationItem = createAnnotationItem(belowLoader, new TestAnnotationImpl(), TestAnnotation.class);
      setAnnotations(belowLoader, annotationItem);
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(aboveChild, aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      annotationItem.invalidate();
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(aboveChild, aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      belowLoader.clear();
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      aboveLoader.clear();
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertTrue(belowLoader.isRetrieved());
   }
   
   public void testBelowAnnotationFirstChildSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testBelowAnnotation(metaData, getFirstChild(), getSecondChild(), true, true);
   }
   
   public void testBelowAnnotationFirstChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testBelowAnnotation(metaData, getFirstChild(), getFirstParent(), true, false);
   }
   
   public void testBelowAnnotationFirstChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testBelowAnnotation(metaData, getFirstChild(), getSecondParent(), true, false);
   }
   
   public void testBelowAnnotationSecondChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testBelowAnnotation(metaData, getSecondChild(), getFirstParent(), true, false);
   }
   
   public void testBelowAnnotationSecondChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testBelowAnnotation(metaData, getSecondChild(), getSecondParent(), true, false);
   }
   
   public void testBelowAnnotationFirstParentSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testBelowAnnotation(metaData, getFirstParent(), getSecondParent(), false, false);
   }
   
   protected void testAboveAnnotation(MetaData metaData, TestMetaDataLoader aboveLoader, TestMetaDataLoader belowLoader, boolean aboveChild, boolean belowChild) throws Exception
   {
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertTrue(belowLoader.isRetrieved());
      
      BasicAnnotationItem<TestAnnotation> annotationItem = createAnnotationItem(belowLoader, new TestAnnotationImpl(), TestAnnotation.class);
      setAnnotations(belowLoader, annotationItem);
      
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertTrue(belowLoader.isRetrieved());
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(aboveChild, aboveLoader.isRetrieved());
      assertEquals(belowChild, belowLoader.isRetrieved());
      
      annotationItem = createAnnotationItem(aboveLoader, new TestAnnotationImpl(), TestAnnotation.class);
      setAnnotations(aboveLoader, annotationItem);
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(aboveChild, aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      belowLoader.clear();
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      annotationItem = createAnnotationItem(belowLoader, new TestAnnotationImpl(), TestAnnotation.class);
      setAnnotations(belowLoader, annotationItem);
      
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      assertAnnotation(metaData, TestAnnotation.class);
      assertEquals(aboveChild, aboveLoader.isRetrieved());
      assertFalse(belowLoader.isRetrieved());
      
      aboveLoader.clear();
      assertAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertTrue(belowLoader.isRetrieved());
      
      belowLoader.clear();
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertTrue(aboveLoader.isRetrieved());
      assertTrue(belowLoader.isRetrieved());
   }
   
   public void testAboveAnnotationFirstChildSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAboveAnnotation(metaData, getFirstChild(), getSecondChild(), true, true);
   }
   
   public void testAboveAnnotationFirstChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAboveAnnotation(metaData, getFirstChild(), getFirstParent(), true, false);
   }
   
   public void testAboveAnnotationFirstChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAboveAnnotation(metaData, getFirstChild(), getSecondParent(), true, false);
   }
   
   public void testAboveAnnotationSecondChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAboveAnnotation(metaData, getSecondChild(), getFirstParent(), true, false);
   }
   
   public void testAboveAnnotationSecondChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAboveAnnotation(metaData, getSecondChild(), getSecondParent(), true, false);
   }
   
   public void testAboveAnnotationFirstParentSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAboveAnnotation(metaData, getFirstParent(), getSecondParent(), false, false);
   }
}
