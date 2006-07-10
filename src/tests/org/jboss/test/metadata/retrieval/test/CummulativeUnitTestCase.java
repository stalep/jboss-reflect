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

import org.jboss.metadata.plugins.context.AbstractMetaDataContext;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;

/**
 * CummulativeUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class CummulativeUnitTestCase extends AbstractMetaDataTest
{
   public CummulativeUnitTestCase(String name)
   {
      super(name);
   }

   public void testCummulativeAnnotationsItem() throws Exception
   {
      MutableMetaDataLoader parentLoader = createTestMutableMetaDataLoader();
      AbstractMetaDataContext parent = new AbstractMetaDataContext(parentLoader);
      
      MutableMetaDataLoader childLoader = createTestMutableMetaDataLoader();
      AbstractMetaDataContext child = new AbstractMetaDataContext(parent, childLoader);

      AnnotationsItem item = child.retrieveAnnotations();
      
      assertTrue(item.getAnnotations().length == 0);
      assertTrue(item.getValue().length == 0);
      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      TestAnnotation1Impl annotation1 = new TestAnnotation1Impl();
      parentLoader.addAnnotation(annotation1);
      AnnotationItem annotationItem1Parent = parentLoader.retrieveAnnotation(TestAnnotation1.class);

      AnnotationItem[] expectedItems = { annotationItem1Parent };
      assertUnorderedArrayEquals(expectedItems, item.getAnnotations());
      Annotation[] expected = { annotation1 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      TestAnnotation2Impl annotation2 = new TestAnnotation2Impl();
      childLoader.addAnnotation(annotation2);
      AnnotationItem annotationItem2Child = childLoader.retrieveAnnotation(TestAnnotation2.class);

      expectedItems = new AnnotationItem[] { annotationItem1Parent, annotationItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getAnnotations());
      expected = new Annotation[] { annotation1, annotation2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      TestAnnotation1Impl annotation1Child = new TestAnnotation1Impl();
      childLoader.addAnnotation(annotation1Child);
      AnnotationItem annotationItem1Child = childLoader.retrieveAnnotation(TestAnnotation1.class);

      expectedItems = new AnnotationItem[] { annotationItem1Child, annotationItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getAnnotations());
      expected = new Annotation[] { annotation1Child, annotation2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      childLoader.removeAnnotation(TestAnnotation1.class);

      expectedItems = new AnnotationItem[] { annotationItem1Parent, annotationItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getAnnotations());
      expected = new Annotation[] { annotation1, annotation2 };
      assertUnorderedArrayEquals(expected, item.getValue());

      parentLoader.removeAnnotation(TestAnnotation1.class);

      expectedItems = new AnnotationItem[] { annotationItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getAnnotations());
      expected = new Annotation[] { annotation2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      childLoader.removeAnnotation(TestAnnotation2.class);

      assertTrue(item.getAnnotations().length == 0);
      assertTrue(item.getValue().length == 0);
   }

   public void testCummulativeMetaDatasItem() throws Exception
   {
      MutableMetaDataLoader parentLoader = createTestMutableMetaDataLoader();
      AbstractMetaDataContext parent = new AbstractMetaDataContext(parentLoader);
      
      MutableMetaDataLoader childLoader = createTestMutableMetaDataLoader();
      AbstractMetaDataContext child = new AbstractMetaDataContext(parent, childLoader);

      MetaDatasItem item = child.retrieveMetaData();
      
      assertTrue(item.getMetaDatas().length == 0);
      assertTrue(item.getValue().length == 0);
      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      Object object1 = new Object();
      parentLoader.addMetaData(object1, Object.class);
      MetaDataItem<Object> metaDataItem1Parent = parentLoader.retrieveMetaData(Object.class);

      MetaDataItem[] expectedItems = { metaDataItem1Parent };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      Object[] expected = { object1 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      String object2 = "Hello";
      childLoader.addMetaData(object2, String.class);
      MetaDataItem<String> metaDataItem2Child = childLoader.retrieveMetaData(String.class);

      expectedItems = new MetaDataItem[] { metaDataItem1Parent, metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object1, object2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      Object object1Child = new Object();
      childLoader.addMetaData(object1Child, Object.class);
      MetaDataItem<Object> metaDataItem1Child = childLoader.retrieveMetaData(Object.class);

      expectedItems = new MetaDataItem[] { metaDataItem1Child, metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object1Child, object2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      childLoader.removeMetaData(Object.class);

      expectedItems = new MetaDataItem[] { metaDataItem1Parent, metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object1, object2 };
      assertUnorderedArrayEquals(expected, item.getValue());

      parentLoader.removeMetaData(Object.class);

      expectedItems = new MetaDataItem[] { metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      childLoader.removeMetaData(String.class);

      assertTrue(item.getMetaDatas().length == 0);
      assertTrue(item.getValue().length == 0);
   }

   public void testCummulativeMetaDatasItemByName() throws Exception
   {
      MutableMetaDataLoader parentLoader = createTestMutableMetaDataLoader();
      AbstractMetaDataContext parent = new AbstractMetaDataContext(parentLoader);
      
      MutableMetaDataLoader childLoader = createTestMutableMetaDataLoader();
      AbstractMetaDataContext child = new AbstractMetaDataContext(parent, childLoader);

      MetaDatasItem item = child.retrieveMetaData();
      
      assertTrue(item.getMetaDatas().length == 0);
      assertTrue(item.getValue().length == 0);
      assertTrue(item.isCachable());
      assertTrue(item.isValid());
      
      Object object1 = new Object();
      parentLoader.addMetaData("Object", object1, Object.class);
      MetaDataItem metaDataItem1Parent = parentLoader.retrieveMetaData("Object");

      MetaDataItem[] expectedItems = { metaDataItem1Parent };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      Object[] expected = { object1 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      String object2 = "Hello";
      childLoader.addMetaData("String", object2, String.class);
      MetaDataItem metaDataItem2Child = childLoader.retrieveMetaData("String");

      expectedItems = new MetaDataItem[] { metaDataItem1Parent, metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object1, object2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      Object object1Child = new Object();
      childLoader.addMetaData("Object", object1Child, Object.class);
      MetaDataItem metaDataItem1Child = childLoader.retrieveMetaData("Object");

      expectedItems = new MetaDataItem[] { metaDataItem1Child, metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object1Child, object2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      childLoader.removeMetaData("Object", Object.class);

      expectedItems = new MetaDataItem[] { metaDataItem1Parent, metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object1, object2 };
      assertUnorderedArrayEquals(expected, item.getValue());

      parentLoader.removeMetaData("Object", Object.class);

      expectedItems = new MetaDataItem[] { metaDataItem2Child };
      assertUnorderedArrayEquals(expectedItems, item.getMetaDatas());
      expected = new Object[] { object2 };
      assertUnorderedArrayEquals(expected, item.getValue());
      
      childLoader.removeMetaData("String", String.class);

      assertTrue(item.getMetaDatas().length == 0);
      assertTrue(item.getValue().length == 0);
   }
}
