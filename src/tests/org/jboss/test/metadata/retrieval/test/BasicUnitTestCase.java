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
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.basic.BasicAnnotationItem;
import org.jboss.metadata.spi.retrieval.basic.BasicAnnotationsItem;
import org.jboss.metadata.spi.retrieval.basic.BasicMetaDataItem;
import org.jboss.metadata.spi.retrieval.basic.BasicMetaDatasItem;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.retrieval.support.TestBasicItemMetaDataLoader;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * BasicUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicUnitTestCase extends AbstractMetaDataTest
{
   public BasicUnitTestCase(String name)
   {
      super(name);
   }

   public void testBasicAnnotation() throws Exception
   {
      TestBasicItemMetaDataLoader loader = new TestBasicItemMetaDataLoader();
      
      TestAnnotationImpl annotation = new TestAnnotationImpl();
      BasicAnnotationItem<TestAnnotation> item = new BasicAnnotationItem<TestAnnotation>(loader, annotation);
      assertEquals(annotation, item.getAnnotation());
      assertEquals(annotation, item.getValue());
      
      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      loader.setCachable(false);
      assertFalse(item.isCachable());
      assertTrue(item.isValid());
      
      item.invalidate();
      assertFalse(item.isCachable());
      assertFalse(item.isValid());
   }

   public void testBasicAnnotations() throws Exception
   {
      TestBasicItemMetaDataLoader loader = new TestBasicItemMetaDataLoader();

      TestAnnotation1Impl annotation1 = new TestAnnotation1Impl();
      TestAnnotation2Impl annotation2 = new TestAnnotation2Impl();
      
      BasicAnnotationItem<TestAnnotation1> item1 = new BasicAnnotationItem<TestAnnotation1>(loader, annotation1);
      BasicAnnotationItem<TestAnnotation2> item2 = new BasicAnnotationItem<TestAnnotation2>(loader, annotation2);

      @SuppressWarnings("unchecked")
      AnnotationItem[] items = { item1, item2 };
      
      @SuppressWarnings("unchecked")
      BasicAnnotationsItem item = new BasicAnnotationsItem(loader, items);
      AnnotationItem<? extends Annotation>[] result = item.getAnnotations();
      assertUnorderedArrayEquals(items, result);

      Annotation[] expected = { annotation1, annotation2 };
      Annotation[] annotations = item.getValue();
      assertUnorderedArrayEquals(expected, annotations);

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      loader.setCachable(false);
      assertFalse(item.isCachable());
      assertTrue(item.isValid());
      
      item1.invalidate();
      assertFalse(item.isCachable());
      assertFalse(item.isValid());
   }

   public void testBasicMetaData() throws Exception
   {
      TestBasicItemMetaDataLoader loader = new TestBasicItemMetaDataLoader();

      String object = "Hello";
      BasicMetaDataItem<String> item = new BasicMetaDataItem<String>(loader, String.class.getName(), object);
      assertEquals(object, item.getValue());

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      loader.setCachable(false);
      assertFalse(item.isCachable());
      assertTrue(item.isValid());
      
      item.invalidate();
      assertFalse(item.isCachable());
      assertFalse(item.isValid());
   }

   public void testBasicMetaDatas() throws Exception
   {
      TestBasicItemMetaDataLoader loader = new TestBasicItemMetaDataLoader();

      Object object1 = new Object();
      String object2 = "Hello";
      
      BasicMetaDataItem<Object> item1 = new BasicMetaDataItem<Object>(loader, Object.class.getName(), object1);
      BasicMetaDataItem<String> item2 = new BasicMetaDataItem<String>(loader, String.class.getName(), object2);

      MetaDataItem<?>[] items =  { item1, item2 };
      
      BasicMetaDatasItem item = new BasicMetaDatasItem(loader, items);
      MetaDataItem<?>[] result = item.getMetaDatas();
      assertUnorderedArrayEquals(items, result);

      Object[] expected = { object1, object2 };
      Object[] objects = item.getValue();
      assertUnorderedArrayEquals(expected, objects);

      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      loader.setCachable(false);
      assertFalse(item.isCachable());
      assertTrue(item.isValid());
      
      item.invalidate();
      assertFalse(item.isCachable());
      assertFalse(item.isValid());
   }
}
