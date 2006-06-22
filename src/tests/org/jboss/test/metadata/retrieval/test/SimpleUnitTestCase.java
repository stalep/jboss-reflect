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

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationsItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleMetaDataItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleMetaDatasItem;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * SimpleUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SimpleUnitTestCase extends AbstractMetaDataTest
{
   public SimpleUnitTestCase(String name)
   {
      super(name);
   }

   public void testSimpleAnnotation() throws Exception
   {
      TestAnnotationImpl annotation = new TestAnnotationImpl();
      SimpleAnnotationItem<TestAnnotation> item = new SimpleAnnotationItem<TestAnnotation>(annotation);
      assertEquals(annotation, item.getAnnotation());
      assertEquals(annotation, item.getValue());
      
      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testSimpleAnnotations() throws Exception
   {
      TestAnnotation1Impl annotation1 = new TestAnnotation1Impl();
      TestAnnotation2Impl annotation2 = new TestAnnotation2Impl();
      
      SimpleAnnotationItem<TestAnnotation1> item1 = new SimpleAnnotationItem<TestAnnotation1>(annotation1);
      SimpleAnnotationItem<TestAnnotation2> item2 = new SimpleAnnotationItem<TestAnnotation2>(annotation2);

      AnnotationItem[] items = { item1, item2 };
      
      SimpleAnnotationsItem item = new SimpleAnnotationsItem(items);
      AnnotationItem[] result = item.getAnnotations();
      assertUnorderedArrayEquals(items, result);

      Annotation[] expected = { annotation1, annotation2 };
      Annotation[] annotations = item.getValue();
      assertUnorderedArrayEquals(expected, annotations);

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testNoSimpleAnnotations() throws Exception
   {
      AnnotationsItem item = SimpleAnnotationsItem.NO_ANNOTATIONS;

      AnnotationItem[] items = item.getAnnotations();
      assertNotNull(items);
      assertEquals(0, items.length);

      Annotation[] annotations = item.getValue();
      assertNotNull(annotations);
      assertEquals(0, annotations.length);

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testSimpleMetaData() throws Exception
   {
      String object = "Hello";
      SimpleMetaDataItem<String> item = new SimpleMetaDataItem<String>(String.class.getName(), object);
      assertEquals(object, item.getValue());

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testSimpleMetaDatas() throws Exception
   {
      Object object1 = new Object();
      String object2 = "Hello";
      
      SimpleMetaDataItem<Object> item1 = new SimpleMetaDataItem<Object>(Object.class.getName(), object1);
      SimpleMetaDataItem<String> item2 = new SimpleMetaDataItem<String>(String.class.getName(), object2);

      MetaDataItem[] items = { item1, item2 };
      
      SimpleMetaDatasItem item = new SimpleMetaDatasItem(items);
      MetaDataItem[] result = item.getMetaDatas();
      assertUnorderedArrayEquals(items, result);

      Object[] expected = { object1, object2 };
      Object[] objects = item.getValue();
      assertUnorderedArrayEquals(expected, objects);

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }

   public void testNoSimpleMetaDatas() throws Exception
   {
      MetaDatasItem item = SimpleMetaDatasItem.NO_META_DATA;

      MetaDataItem[] items = item.getMetaDatas();
      assertNotNull(items);
      assertEquals(0, items.length);

      Object[] objects = item.getValue();
      assertNotNull(objects);
      assertEquals(0, objects.length);

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
   }
}
