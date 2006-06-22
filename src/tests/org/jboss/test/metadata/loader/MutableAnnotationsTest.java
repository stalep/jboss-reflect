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
package org.jboss.test.metadata.loader;

import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.shared.support.ExpectedAnnotations;
import org.jboss.test.metadata.shared.support.MutableMetaDataLoaderToMetaDataBridge;
import org.jboss.test.metadata.shared.support.NotPresentAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * MutableAnnotationsTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MutableAnnotationsTest extends AbstractMetaDataTest
{
   public MutableAnnotationsTest(String name)
   {
      super(name);
   }
   
   public void testEmpty() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);

      assertValidTimeUnchanged(metaData, last);
   }
   
   public void testTestAnnotation() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      last = assertAddAnnotationNoPrevious(metaData, new TestAnnotationImpl(), expectedAnnotations, last);
      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      assertRemoveAnnotation(metaData, TestAnnotation.class, expectedAnnotations, last);
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
   }
   
   public void testTestAnnotation12() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertNoAnnotation(metaData, TestAnnotation1.class);
      assertNoAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      last = assertAddAnnotationNoPrevious(metaData, new TestAnnotation1Impl(), expectedAnnotations, last);
      assertAnnotation(metaData, TestAnnotation1.class);
      assertNoAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);

      last = assertAddAnnotationNoPrevious(metaData, new TestAnnotation2Impl(), expectedAnnotations, last);
      assertAnnotation(metaData, TestAnnotation1.class);
      assertAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      assertRemoveAnnotation(metaData, TestAnnotation1.class, expectedAnnotations, last);
      assertNoAnnotation(metaData, TestAnnotation1.class);
      assertAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);

      assertRemoveAnnotation(metaData, TestAnnotation2.class, expectedAnnotations, last);
      assertNoAnnotation(metaData, TestAnnotation1.class);
      assertNoAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
   }
   
   public void testAddTwice() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      last = assertAddAnnotationNoPrevious(metaData, new TestAnnotationImpl(), expectedAnnotations, last);
      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      last = assertAddAnnotationWithPrevious(metaData, new TestAnnotationImpl(), last);
      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
   }
   
   public void testAddTwiceSameObject() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertNoAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      TestAnnotation annotation = new TestAnnotationImpl();
      last = assertAddAnnotationNoPrevious(metaData, annotation, expectedAnnotations, last);
      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
      
      assertAddAnnotationWithPreviousSameObject(metaData, annotation, last);
      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
   }

   public void testRemoveDoesNotExist() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();

      assertNotRemovedAnnotation(metaData, TestAnnotation.class, last);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
   }
   
   public void testRemoveTwice() throws Exception
   {
      MutableMetaDataLoaderToMetaDataBridge metaData = setupEmpty();
      long last = metaData.getValidTime();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();

      last = assertAddAnnotationNoPrevious(metaData, new TestAnnotationImpl(), expectedAnnotations, last);
      assertRemoveAnnotation(metaData, TestAnnotation.class, expectedAnnotations, last);
      assertNotRemovedAnnotation(metaData, TestAnnotation.class, last);
      assertAnnotations(metaData, expectedAnnotations);
      assertAnnotationMetaDatas(metaData, expectedAnnotations);
   }
   
   protected abstract MutableMetaDataLoaderToMetaDataBridge setupEmpty();
}
