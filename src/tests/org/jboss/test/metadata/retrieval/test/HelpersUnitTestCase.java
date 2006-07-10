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
package org.jboss.test.metadata.retrieval.test;

import java.util.Arrays;

import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.metadata.spi.retrieval.ValidTime;
import org.jboss.metadata.spi.retrieval.helper.AnnotationToMetaDataBridge;
import org.jboss.metadata.spi.retrieval.helper.AnnotationsToMetaDatasBridge;
import org.jboss.metadata.spi.retrieval.helper.MetaDataToAnnotationBridge;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationsItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleMetaDataItem;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.retrieval.support.TestMetaDataRetrieval;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * HelpersUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class HelpersUnitTestCase extends AbstractMetaDataTest
{
   public HelpersUnitTestCase(String name)
   {
      super(name);
   }

   public void testValidTime() throws Exception
   {
      ValidTime time1 = new ValidTime();
      ValidTime time2 = new ValidTime();
      assertTrue(time2.getValidTime() >= time1.getValidTime());
      
      time1.invalidate();
      assertTrue(time1.getValidTime() > time2.getValidTime());

      time2.invalidate();
      assertTrue(time2.getValidTime() > time1.getValidTime());

      ValidTime time3 = new ValidTime();
      assertTrue(time3.getValidTime() >= time1.getValidTime());
      assertTrue(time3.getValidTime() >= time2.getValidTime());
   }

   public void testMetaDataRetrievalToMetaDataBridge() throws Exception
   {
      TestMetaDataRetrieval test = new TestMetaDataRetrieval();
      MetaDataRetrievalToMetaDataBridge metaData = new MetaDataRetrievalToMetaDataBridge(test);
      
      metaData.getAnnotation(TestAnnotation.class);
      assertEquals("retrieveAnnotation", test.lastMethod);

      metaData.getAnnotations();
      assertEquals("retrieveAnnotations", test.lastMethod);

      metaData.getLocalAnnotations();
      assertEquals("retrieveLocalAnnotations", test.lastMethod);

      metaData.getMetaData();
      assertEquals("retrieveMetaData", test.lastMethod);

      metaData.getLocalMetaData();
      assertEquals("retrieveLocalMetaData", test.lastMethod);

      metaData.getMetaData(Object.class);
      assertEquals("retrieveMetaData(Class)", test.lastMethod);

      metaData.getMetaData("Hello");
      assertEquals("retrieveMetaData(String)", test.lastMethod);

      metaData.getMetaData("Hello", Object.class);
      assertEquals("retrieveMetaData(String)", test.lastMethod);

      metaData.getValidTime();
      assertEquals("getValidTime", test.lastMethod);

      metaData.isAnnotationPresent(TestAnnotation.class);
      assertEquals("retrieveAnnotation", test.lastMethod);

      metaData.isMetaDataPresent(Object.class);
      assertEquals("retrieveMetaData(Class)", test.lastMethod);

      metaData.isMetaDataPresent("Hello");
      assertEquals("retrieveMetaData(String)", test.lastMethod);

      metaData.isMetaDataPresent("Hello", Object.class);
      assertEquals("retrieveMetaData(String)", test.lastMethod);
   }

   public void testAnnotationsToMetaDatasBridge() throws Exception
   {
      TestAnnotation1Impl annotation1 = new TestAnnotation1Impl();
      TestAnnotation2Impl annotation2 = new TestAnnotation2Impl();
      
      SimpleAnnotationItem<TestAnnotation1> item1 = new SimpleAnnotationItem<TestAnnotation1>(annotation1);
      SimpleAnnotationItem<TestAnnotation2> item2 = new SimpleAnnotationItem<TestAnnotation2>(annotation2);

      AnnotationItem[] items = new AnnotationItem[] { item1, item2 };
      SimpleAnnotationsItem item = new SimpleAnnotationsItem(items);
      AnnotationsToMetaDatasBridge bridge = new AnnotationsToMetaDatasBridge(item);
      
      MetaDataItem[] result = bridge.getMetaDatas();
      assertTrue(Arrays.equals(items, result));

      Object[] expected = new Object[] { annotation1, annotation2 };
      Object[] objects = item.getValue();
      assertTrue(Arrays.equals(expected, objects));

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testAnnotationToMetaDataBridge() throws Exception
   {
      TestAnnotation annotation = new TestAnnotationImpl();
      
      SimpleAnnotationItem<TestAnnotation> item = new SimpleAnnotationItem<TestAnnotation>(annotation);
      AnnotationToMetaDataBridge bridge = new AnnotationToMetaDataBridge(item);
      
      assertEquals(annotation, bridge.getValue());

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testMetaDataToAnnotationBridge() throws Exception
   {
      TestAnnotation annotation = new TestAnnotationImpl();
      
      SimpleMetaDataItem<TestAnnotation> item = new SimpleMetaDataItem<TestAnnotation>(annotation.annotationType().getName(), annotation);
      MetaDataToAnnotationBridge bridge = new MetaDataToAnnotationBridge(item);
      
      assertEquals(annotation, bridge.getAnnotation());
      assertEquals(annotation, bridge.getValue());

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }
}
