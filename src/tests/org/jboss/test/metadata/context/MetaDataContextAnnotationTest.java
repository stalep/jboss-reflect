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
package org.jboss.test.metadata.context;

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.test.metadata.shared.support.ExpectedAnnotations;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * MetaDataContextAnnotationTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MetaDataContextAnnotationTest extends AbstractMetaDataContextMemoryLoaderTest
{
   public MetaDataContextAnnotationTest(String name)
   {
      super(name);
   }
   
   protected void testAnnotation(MetaData metaData, MutableMetaDataLoader loader) throws Exception
   {
      ExpectedAnnotations expected = emptyExpectedAnnotations();
      long last = metaData.getValidTime();
      assertNoAnnotation(metaData, TestAnnotation.class);

      TestAnnotation annotation = new TestAnnotationImpl();
      last = assertAddAnnotationNoPrevious(metaData, loader, annotation, expected, last);
      assertAnnotation(metaData, TestAnnotation.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      
      assertRemoveAnnotation(metaData, loader, TestAnnotation.class, expected, last, false);
      assertNoAnnotation(metaData, TestAnnotation.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
   }
   
   public void testAnnotationFromFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getFirstChild());
   }
   
   public void testAnnotationFromSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getSecondChild());
   }
   
   public void testAnnotationFromFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getFirstParent());
   }
   
   public void testAnnotationFromSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation(metaData, getSecondParent());
   }
   
   protected void testAnnotation12(MetaData metaData, MutableMetaDataLoader loader1, MutableMetaDataLoader loader2) throws Exception
   {
      ExpectedAnnotations expected = emptyExpectedAnnotations();
      long last = metaData.getValidTime();
      assertNoAnnotation(metaData, TestAnnotation1.class);
      assertNoAnnotation(metaData, TestAnnotation2.class);

      TestAnnotation1 annotation1 = new TestAnnotation1Impl();
      last = assertAddAnnotationNoPrevious(metaData, loader1, annotation1, expected, last);
      assertAnnotation(metaData, TestAnnotation1.class);
      assertNoAnnotation(metaData, TestAnnotation2.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);

      TestAnnotation2 annotation2 = new TestAnnotation2Impl();
      last = assertAddAnnotationNoPrevious(metaData, loader2, annotation2, expected, last);
      assertAnnotation(metaData, TestAnnotation1.class);
      assertAnnotation(metaData, TestAnnotation2.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      
      assertRemoveAnnotation(metaData, loader1, TestAnnotation1.class, expected, last, false);
      assertNoAnnotation(metaData, TestAnnotation1.class);
      assertAnnotation(metaData, TestAnnotation2.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      
      assertRemoveAnnotation(metaData, loader2, TestAnnotation2.class, expected, last, false);
      assertNoAnnotation(metaData, TestAnnotation1.class);
      assertNoAnnotation(metaData, TestAnnotation2.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
   }
   
   public void testAnnotation12FromFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstChild(), getFirstChild());
   }
   
   public void testAnnotation12FromFirstChildSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstChild(), getSecondChild());
   }
   
   public void testAnnotation12FromFirstChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstChild(), getFirstParent());
   }
   
   public void testAnnotation12FromFirstChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstChild(), getSecondParent());
   }
   
   public void testAnnotation12FromSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondChild(), getSecondChild());
   }
   
   public void testAnnotation12FromSecondChildFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondChild(), getFirstChild());
   }
   
   public void testAnnotation12FromSecondChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondChild(), getFirstParent());
   }
   
   public void testAnnotation12FromSecondChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondChild(), getSecondParent());
   }
   
   public void testAnnotation12FromFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstParent(), getFirstParent());
   }
   
   public void testAnnotation12FromFirstParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstParent(), getFirstChild());
   }
   
   public void testAnnotation12FromFirstParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstParent(), getSecondChild());
   }
   
   public void testAnnotation12FromFirstParentSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getFirstParent(), getSecondParent());
   }
   
   public void testAnnotation12FromSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondParent(), getSecondParent());
   }
   
   public void testAnnotation12FromSecondParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondParent(), getFirstChild());
   }
   
   public void testAnnotation12FromSecondParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondParent(), getSecondChild());
   }
   
   public void testAnnotation12FromSecondParentFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotation12(metaData, getSecondParent(), getFirstParent());
   }
   
   protected void testAnnotationOverride(MetaData metaData, MutableMetaDataLoader loader1, MutableMetaDataLoader loader2) throws Exception
   {
      ExpectedAnnotations expected = emptyExpectedAnnotations();
      long last = metaData.getValidTime();
      assertNoAnnotation(metaData, TestAnnotation.class);

      TestAnnotation annotation1 = new TestAnnotationImpl();
      last = assertAddAnnotationNoPrevious(metaData, loader1, annotation1, expected, last);
      assertAnnotation(metaData, TestAnnotation.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);

      TestAnnotation annotation2 = new TestAnnotationImpl();
      last = assertAddAnnotationWithPrevious(metaData, loader2, annotation2, last);
      assertAnnotation(metaData, TestAnnotation.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      
      assertRemoveAnnotation(metaData, loader1, TestAnnotation.class, expected, last, true);
      assertAnnotation(metaData, TestAnnotation.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      
      assertRemoveAnnotation(metaData, loader2, TestAnnotation.class, expected, last, false);
      assertNoAnnotation(metaData, TestAnnotation.class);
      
      assertAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
   }
   
   public void testAnnotationOverrideFromFirstChildSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getFirstChild(), getSecondChild());
   }
   
   public void testAnnotationOverrideFromFirstChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getFirstChild(), getFirstParent());
   }
   
   public void testAnnotationOverrideFromFirstChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getFirstChild(), getSecondParent());
   }
   
   public void testAnnotationOverrideFromSecondChildFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getSecondChild(), getFirstChild());
   }
   
   public void testAnnotationOverrideFromSecondChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getSecondChild(), getFirstParent());
   }
   
   public void testAnnotationOverrideFromSecondChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getSecondChild(), getSecondParent());
   }
   
   public void testAnnotationOverrideFromFirstParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getFirstParent(), getFirstChild());
   }
   
   public void testAnnotationOverrideFromFirstParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getFirstParent(), getSecondChild());
   }
   
   public void testAnnotationOverrideFromFirstParentSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getFirstParent(), getSecondParent());
   }
   
   public void testAnnotationOverrideFromSecondParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getSecondParent(), getFirstChild());
   }
   
   public void testAnnotationOverrideFromSecondParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getSecondParent(), getSecondChild());
   }
   
   public void testAnnotationOverrideFromSecondParentFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testAnnotationOverride(metaData, getSecondParent(), getFirstParent());
   }
}
