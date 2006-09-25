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
package org.jboss.test.metadata;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.MutableMetaData;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.BaseTestCase;
import org.jboss.test.metadata.shared.support.ExpectedAnnotations;
import org.jboss.test.metadata.shared.support.ExpectedMetaData;

/**
 * AbstractMetaDataTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractMetaDataTest extends BaseTestCase
{
   public AbstractMetaDataTest(String name)
   {
      super(name);
   }

   /**
    * Assert two unordered arrays are equal
    * 
    * @param expected the expected values
    * @param actual the actual values
    */
   public void assertUnorderedArrayEquals(Object[] expected, Object[] actual)
   {
      if (expected == null && actual == null)
         return;
      if (expected == null && actual != null || (expected != null && actual == null))
         fail("Expected " + expected + " got " + actual);
      
      List<Object> expectedList = Arrays.asList(expected);
      List<Object> actualList = Arrays.asList(actual);

      if (expectedList.containsAll(actualList) == false || actualList.containsAll(expectedList) == false)
         fail("Expected " + expectedList + " got " + actualList);
   }
   
   /**
    * Create a test mutable meta data loader
    * 
    * @return the implementation
    */
   public MutableMetaDataLoader createTestMutableMetaDataLoader()
   {
      return new MemoryMetaDataLoader();
   }
   
   /**
    * Create a test mutable meta data loader
    *
    * @param key the scope key
    * @return the implementation
    */
   public MutableMetaDataLoader createTestMutableMetaDataLoader(ScopeKey key)
   {
      return new MemoryMetaDataLoader(key);
   }
   
   /**
    * Assert an annotation
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param type the annotation type
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertAnnotation(MetaData metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);
      
      assertTrue(metaData.isAnnotationPresent(type));
      checkAnnotation(type, metaData.getAnnotation(type));
      assertTrue(metaData.isMetaDataPresent(type));
      checkAnnotation(type, metaData.getMetaData(type));
      assertTrue(metaData.isMetaDataPresent(type.getName()));
      checkAnnotation(type, metaData.getMetaData(type.getName()));
      assertTrue(metaData.isMetaDataPresent(type.getName(), type));
      checkAnnotation(type, metaData.getMetaData(type.getName(), type));
   }

   /**
    * Assert an annotation
    * 
    * @param <T> the type
    * @param metaData the retrieval
    * @param type the annotation type
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertAnnotation(MetaDataRetrieval metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);
      
      checkAnnotation(type, metaData.retrieveAnnotation(type));
      checkAnnotation(type, metaData.retrieveMetaData(type));
      checkAnnotation(type, metaData.retrieveMetaData(type.getName()));
   }

   /**
    * Assert meta data
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertMetaData(MetaData metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);
      
      assertTrue(metaData.isMetaDataPresent(type));
      checkMetaData(type, metaData.getMetaData(type));
      assertTrue(metaData.isMetaDataPresent(type.getName()));
      checkMetaData(type, metaData.getMetaData(type.getName()));
      assertTrue(metaData.isMetaDataPresent(type.getName(), type));
      checkMetaData(type, metaData.getMetaData(type.getName(), type));
   }

   /**
    * Assert meta data
    * 
    * @param <T> the type
    * @param metaData the retrieval
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertMetaData(MetaDataRetrieval metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);
      
      checkMetaData(type, metaData.retrieveMetaData(type));
      checkMetaData(type, metaData.retrieveMetaData(type.getName()));
   }

   /**
    * Assert meta data
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param name the name
    * @param type the type
    * @throws Exception for any error
    */
   @SuppressWarnings("unchecked")
   protected <T> void assertMetaData(MetaData metaData, String name, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(name);
      assertNotNull(type);
      
      assertTrue(metaData.isMetaDataPresent(name));
      checkMetaData(type, metaData.getMetaData(name));
      assertTrue(metaData.isMetaDataPresent(name, type));
      checkMetaData(type, metaData.getMetaData(name, type));
   }

   /**
    * Assert meta data
    * 
    * @param <T> the type
    * @param metaData the retrieval
    * @param name the name
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertMetaData(MetaDataRetrieval metaData, String name, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(name);
      assertNotNull(type);
      
      checkMetaData(type, metaData.retrieveMetaData(name));
   }

   /**
    * Assert an annotation is not present
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param type the annotation type
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertNoAnnotation(MetaData metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);

      assertFalse(metaData.isAnnotationPresent(type));
      assertFalse(metaData.isMetaDataPresent(type));
      assertFalse(metaData.isMetaDataPresent(type.getName()));
      assertFalse(metaData.isMetaDataPresent(type.getName(), type));
   }

   /**
    * Assert an annotation is not present
    * 
    * @param <T> the type
    * @param metaData the retrieval
    * @param type the annotation type
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertNoAnnotation(MetaDataRetrieval metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);

      assertNull(metaData.retrieveAnnotation(type));
      assertNull(metaData.retrieveMetaData(type));
      assertNull(metaData.retrieveMetaData(type.getName()));
   }

   /**
    * Assert a metadata type is not present
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertNoMetaData(MetaData metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);

      assertFalse(metaData.isMetaDataPresent(type));
      assertFalse(metaData.isMetaDataPresent(type.getName()));
      assertFalse(metaData.isMetaDataPresent(type.getName(), type));
   }

   /**
    * Assert a metadata type is not present
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertNoMetaData(MetaDataRetrieval metaData, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(type);

      assertNull(metaData.retrieveMetaData(type));
      assertNull(metaData.retrieveMetaData(type.getName()));
   }

   /**
    * Assert a metadata name is not present
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param name the name
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertNoMetaData(MetaData metaData, String name, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(name);
      assertNotNull(type);

      assertFalse(metaData.isMetaDataPresent(type.getName()));
      assertFalse(metaData.isMetaDataPresent(type.getName(), type));
   }

   /**
    * Assert a metadata name is not present
    * 
    * @param <T> the type
    * @param metaData the retrieval
    * @param name the name
    * @param type the type
    * @throws Exception for any error
    */
   protected <T> void assertNoMetaData(MetaDataRetrieval metaData, String name, Class<T> type) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(name);
      assertNotNull(type);

      assertNull(metaData.retrieveMetaData(name));
   }
   
   /**
    * Check an annotation has the expected type
    * 
    * @param expected the expected type
    * @param result the annotation
    * @throws Exception for any error
    */
   protected void checkAnnotation(Class expected, Object result) throws Exception
   {
      assertNotNull("Annotation should not be null", result);
      assertTrue(result.getClass().getName() + " should be an instance of " + expected.getName(), expected.isInstance(result));
   }
   
   /**
    * Check an annotation has the expected type
    * 
    * @param expected the expected type
    * @param result the annotation
    * @throws Exception for any error
    */
   protected void checkAnnotation(Class expected, AnnotationItem result) throws Exception
   {
      assertNotNull("AnnotationItem should not be null", result);
      assertTrue(result.isValid());
      Annotation annotation = result.getAnnotation();
      checkAnnotation(expected, annotation);
   }
   
   /**
    * Check an annotation has the expected type
    * 
    * @param expected the expected type
    * @param result the annotation
    * @throws Exception for any error
    */
   protected void checkAnnotation(Class expected, MetaDataItem result) throws Exception
   {
      assertNotNull("MetaDataItem should not be null", result);
      assertTrue(result.isValid());
      Object object = result.getValue();
      checkAnnotation(expected, object);
   }
   
   /**
    * Check meta data has the expected type
    * 
    * @param expected the expected type
    * @param result the meta data
    * @throws Exception for any error
    */
   protected void checkMetaData(Class expected, Object result) throws Exception
   {
      assertNotNull("MetaData should not be null", result);
      assertTrue(result.getClass().getName() + " should be an instance of " + expected.getName(), expected.isInstance(result));
   }
   
   /**
    * Check meta data has the expected type
    * 
    * @param expected the expected type
    * @param result the meta data
    * @throws Exception for any error
    */
   protected void checkMetaData(Class expected, MetaDataItem result) throws Exception
   {
      assertNotNull("MetaDataItem should not be null", result);
      assertTrue(result.isValid());
      Object object = result.getValue();
      checkMetaData(expected, object);
   }

   /**
    * Check an annotation is added with no previous
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param annotation the annotation
    * @param expectedAnnotations the expected annotations
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> long assertAddAnnotationNoPrevious(MutableMetaData mutable, T annotation, ExpectedAnnotations expectedAnnotations, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      return assertAddAnnotationNoPrevious(metaData, mutable, annotation, expectedAnnotations, last);
   }

   /**
    * Check an annotation is added with no previous
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param annotation the annotation
    * @param expectedAnnotations the expected annotations
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> long assertAddAnnotationNoPrevious(MetaData metaData, MutableMetaData mutable, T annotation, ExpectedAnnotations expectedAnnotations, long last) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(annotation);
      Class<? extends Annotation> expected = annotation.annotationType();
      expectedAnnotations.add(expected);

      assertFalse(metaData.isAnnotationPresent(expected));
      assertFalse(metaData.isMetaDataPresent(expected));
      assertFalse(metaData.isMetaDataPresent(expected.getName()));
      assertFalse(metaData.isMetaDataPresent(expected.getName(), expected));
      
      Annotation result = mutable.addAnnotation(annotation);
      assertNull(result);
      assertTrue(metaData.isAnnotationPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected.getName()));
      assertTrue(metaData.isMetaDataPresent(expected.getName(), expected));

      return assertValidTimeChanged(metaData, last);
   }

   /**
    * Check an annotation is added with no previous
    * 
    * @param <T> the type
    * @param mutable the retrieval
    * @param annotation the annotation
    * @param expectedAnnotations the expected annotations
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> long assertAddAnnotationNoPrevious(MutableMetaDataLoader mutable, T annotation, ExpectedAnnotations expectedAnnotations, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(annotation);
      Class<? extends Annotation> expected = annotation.annotationType();
      expectedAnnotations.add(expected);

      assertNull(mutable.retrieveAnnotation(expected));
      assertNull(mutable.retrieveMetaData(expected));
      assertNull(mutable.retrieveMetaData(expected.getName()));
      
      Annotation result = mutable.addAnnotation(annotation);
      assertNull(result);
      checkAnnotation(expected, mutable.retrieveAnnotation(expected));
      checkAnnotation(expected, mutable.retrieveMetaData(expected));
      checkAnnotation(expected, mutable.retrieveMetaData(expected.getName()));

      return assertValidTimeChanged(mutable, last);
   }

   /**
    * Check an meta data is added with no previous
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the meta data
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataNoPrevious(MutableMetaData mutable, T object, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      return assertAddMetaDataNoPrevious(metaData, mutable, object, type, expected, last);
   }

   /**
    * Check an meta data is added with no previous
    *
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param object the object
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataNoPrevious(MetaData metaData, MutableMetaData mutable, T object, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(object);
      expected.add(type);

      assertFalse(metaData.isMetaDataPresent(type));
      assertFalse(metaData.isMetaDataPresent(type.getName()));
      assertFalse(metaData.isMetaDataPresent(type.getName(), type));
      
      Object result = mutable.addMetaData(object, type);
      assertNull(result);
      assertTrue(metaData.isMetaDataPresent(type));
      assertTrue(metaData.isMetaDataPresent(type.getName()));
      assertTrue(metaData.isMetaDataPresent(type.getName(), type));

      return assertValidTimeChanged(metaData, last);
   }

   /**
    * Check an meta data is added with no previous
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the meta data
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataNoPrevious(MutableMetaDataLoader mutable, T object, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      expected.add(type);

      assertNull(mutable.retrieveMetaData(type));
      assertNull(mutable.retrieveMetaData(type.getName()));
      
      Object result = mutable.addMetaData(object, type);
      assertNull(result);
      checkMetaData(type, mutable.retrieveMetaData(type));
      checkMetaData(type, mutable.retrieveMetaData(type.getName()));

      return assertValidTimeChanged(mutable, last);
   }

   /**
    * Check an meta data is added with no previous
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the meta data
    * @param name the name
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataNoPrevious(MutableMetaData mutable, T object, String name, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      return assertAddMetaDataNoPrevious(metaData, mutable, object, name, type, expected, last);
   }

   /**
    * Check an meta data is added with no previous
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param object the object
    * @param name the name
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataNoPrevious(MetaData metaData, MutableMetaData mutable, T object, String name, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(name);
      expected.add(type);

      assertFalse(metaData.isMetaDataPresent(name));
      assertFalse(metaData.isMetaDataPresent(name, type));
      
      Object result = mutable.addMetaData(name, object, type);
      assertNull(result);
      assertTrue(metaData.isMetaDataPresent(name));
      assertTrue(metaData.isMetaDataPresent(name, type));

      return assertValidTimeChanged(metaData, last);
   }

   /**
    * Check an meta data is added with no previous
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the meta data
    * @param name the name
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @return the new valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataNoPrevious(MutableMetaDataLoader mutable, T object, String name, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(name);
      expected.add(type);

      assertNull(mutable.retrieveMetaData(name));
      
      Object result = mutable.addMetaData(name, object, type);
      assertNull(result);
      checkMetaData(type, mutable.retrieveMetaData(name));

      return assertValidTimeChanged(mutable, last);
   }

   /**
    * Check an annotation is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param annotation the annotation
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> long assertAddAnnotationWithPrevious(MutableMetaData mutable, T annotation, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      return assertAddAnnotationWithPrevious(metaData, mutable, annotation, last);
   }

   /**
    * Check an annotation is added with a previous value
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param annotation the annotation
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> long assertAddAnnotationWithPrevious(MetaData metaData, MutableMetaData mutable, T annotation, long last) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(annotation);
      Class<? extends Annotation> expected = annotation.annotationType();

      assertTrue(metaData.isAnnotationPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected.getName()));
      assertTrue(metaData.isMetaDataPresent(expected.getName(), expected));
      
      Annotation result = mutable.addAnnotation(annotation);
      if (metaData == mutable)
         checkAnnotation(expected, result);
      assertTrue(metaData.isAnnotationPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected.getName()));
      assertTrue(metaData.isMetaDataPresent(expected.getName(), expected));

      return assertValidTimeChanged(metaData, last);
   }

   /**
    * Check an annotation is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the retrieval
    * @param annotation the annotation
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> long assertAddAnnotationWithPrevious(MutableMetaDataLoader mutable, T annotation, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(annotation);
      Class<? extends Annotation> expected = annotation.annotationType();

      AnnotationItem annotationItem = mutable.retrieveAnnotation(expected);
      checkAnnotation(expected, annotationItem);
      MetaDataItem metaDataItem1 = mutable.retrieveMetaData(expected);
      checkAnnotation(expected, metaDataItem1);
      MetaDataItem metaDataItem2 = mutable.retrieveMetaData(expected.getName());
      checkAnnotation(expected, metaDataItem2);
      
      Annotation result = mutable.addAnnotation(annotation);
      checkAnnotation(expected, result);
      checkAnnotation(expected, mutable.retrieveAnnotation(expected));
      checkAnnotation(expected, mutable.retrieveMetaData(expected));
      checkAnnotation(expected, mutable.retrieveMetaData(expected.getName()));
      assertFalse(annotationItem.isValid());
      assertFalse(metaDataItem1.isValid());
      assertFalse(metaDataItem2.isValid());

      return assertValidTimeChanged(mutable, last);
   }

   /**
    * Check an annotation is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the retrieval
    * @param annotation the annotation
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertAddAnnotationWithPreviousSameObject(MutableMetaData mutable, T annotation, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(annotation);
      Class<? extends Annotation> expected = annotation.annotationType();
      MetaData metaData = (MetaData) mutable;

      checkAnnotation(expected, metaData.getAnnotation(expected));
      checkAnnotation(expected, metaData.getMetaData(expected));
      checkAnnotation(expected, metaData.getMetaData(expected.getName(), expected));
      checkAnnotation(expected, metaData.getMetaData(expected.getName()));
      
      Annotation result = mutable.addAnnotation(annotation);
      checkAnnotation(expected, result);
      checkAnnotation(expected, metaData.getAnnotation(expected));
      checkAnnotation(expected, metaData.getMetaData(expected));
      checkAnnotation(expected, metaData.getMetaData(expected.getName(), expected));
      checkAnnotation(expected, metaData.getMetaData(expected.getName()));

      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check an annotation is added with a previous value
    *
    * @param <T> the type
    * @param mutable the retrieval
    * @param annotation the annotation
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertAddAnnotationWithPreviousSameObject(MutableMetaDataLoader mutable, T annotation, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(annotation);
      Class<? extends Annotation> expected = annotation.annotationType();

      AnnotationItem annotationItem = mutable.retrieveAnnotation(expected);
      checkAnnotation(expected, annotationItem);
      MetaDataItem metaDataItem1 = mutable.retrieveMetaData(expected);
      checkAnnotation(expected, metaDataItem1);
      MetaDataItem metaDataItem2 = mutable.retrieveMetaData(expected.getName());
      checkAnnotation(expected, metaDataItem2);
      
      Annotation result = mutable.addAnnotation(annotation);
      checkAnnotation(expected, result);
      checkAnnotation(expected, mutable.retrieveAnnotation(expected));
      checkAnnotation(expected, mutable.retrieveMetaData(expected));
      checkAnnotation(expected, mutable.retrieveMetaData(expected.getName()));
      assertTrue(annotationItem.isValid());
      assertTrue(metaDataItem1.isValid());
      assertTrue(metaDataItem2.isValid());

      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check metadata is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param type the type
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataWithPrevious(MutableMetaData mutable, T object, Class<T> type, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      return assertAddMetaDataWithPrevious(metaData, mutable, object, type, last);
   }

   /**
    * Check metadata is added with a previous value
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param object the object
    * @param type the type
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataWithPrevious(MetaData metaData, MutableMetaData mutable, T object, Class<T> type, long last) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);

      assertTrue(metaData.isMetaDataPresent(type));
      assertTrue(metaData.isMetaDataPresent(type.getName()));
      assertTrue(metaData.isMetaDataPresent(type.getName(), type));
      
      Object result = mutable.addMetaData(object, type);
      if (metaData == mutable)
         checkMetaData(type, result);
      assertTrue(metaData.isMetaDataPresent(type));
      assertTrue(metaData.isMetaDataPresent(type.getName()));
      assertTrue(metaData.isMetaDataPresent(type.getName(), type));

      return assertValidTimeChanged(metaData, last);
   }

   /**
    * Check metadata is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param type the type
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataWithPrevious(MutableMetaDataLoader mutable, T object, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);

      checkMetaData(type, mutable.retrieveMetaData(type));
      checkMetaData(type, mutable.retrieveMetaData(type.getName()));
      
      Object result = mutable.addMetaData(object, type);
      checkMetaData(type, result);
      checkMetaData(type, mutable.retrieveMetaData(type));
      checkMetaData(type, mutable.retrieveMetaData(type.getName()));

      return assertValidTimeChanged(mutable, last);
   }

   /**
    * Check metadata is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param name the name 
    * @param type the type
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataWithPrevious(MutableMetaData mutable, T object, String name, Class<T> type, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      return assertAddMetaDataWithPrevious(metaData, mutable, object, name, type, last);
   }

   /**
    * Check metadata is added with a previous value
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param object the object
    * @param name the name 
    * @param type the type
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataWithPrevious(MetaData metaData, MutableMetaData mutable, T object, String name, Class<T> type, long last) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);
      assertNotNull(name);

      assertTrue(metaData.isMetaDataPresent(name));
      assertTrue(metaData.isMetaDataPresent(name, type));
      
      Object result = mutable.addMetaData(name, object, type);
      if (metaData == mutable)
         checkMetaData(type, result);
      assertTrue(metaData.isMetaDataPresent(name));
      assertTrue(metaData.isMetaDataPresent(name, type));

      return assertValidTimeChanged(metaData, last);
   }

   /**
    * Check metadata is added with a previous value
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param name the name 
    * @param type the type
    * @param last the last valid time
    * @return the new last valid time
    * @throws Exception for any error
    */
   protected <T> long assertAddMetaDataWithPrevious(MutableMetaDataLoader mutable, T object, String name, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);
      assertNotNull(name);

      checkMetaData(type, mutable.retrieveMetaData(name));
      
      Object result = mutable.addMetaData(name, object, type);
      checkMetaData(type, result);
      checkMetaData(type, mutable.retrieveMetaData(name));

      return assertValidTimeChanged(mutable, last);
   }

   /**
    * Check metadata is added with a previous value the same object
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertAddMetaDataWithPreviousSameObject(MutableMetaData mutable, T object, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);
      MetaData metaData = (MetaData) mutable;

      checkMetaData(type, metaData.getMetaData(type));
      checkMetaData(type, metaData.getMetaData(type.getName(), type));
      checkMetaData(type, metaData.getMetaData(type.getName()));
      
      Object result = mutable.addMetaData(object, type);
      checkMetaData(type, result);
      checkMetaData(type, metaData.getMetaData(type));
      checkMetaData(type, metaData.getMetaData(type.getName(), type));
      checkMetaData(type, metaData.getMetaData(type.getName()));

      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check metadata is added with a previous value the same object
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertAddMetaDataWithPreviousSameObject(MutableMetaDataLoader mutable, T object, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);

      checkMetaData(type, mutable.retrieveMetaData(type));
      checkMetaData(type, mutable.retrieveMetaData(type.getName()));
      
      Object result = mutable.addMetaData(object, type);
      checkMetaData(type, result);
      checkMetaData(type, mutable.retrieveMetaData(type));
      checkMetaData(type, mutable.retrieveMetaData(type.getName()));

      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check metadata is added with a previous value the same object
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param name the name
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertAddMetaDataWithPreviousSameObject(MutableMetaData mutable, T object, String name, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);
      assertNotNull(name);
      MetaData metaData = (MetaData) mutable;

      checkMetaData(type, metaData.getMetaData(name, type));
      checkMetaData(type, metaData.getMetaData(name));
      
      Object result = mutable.addMetaData(name, object, type);
      checkMetaData(type, result);
      checkMetaData(type, metaData.getMetaData(name, type));
      checkMetaData(type, metaData.getMetaData(name));

      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check metadata is added with a previous value the same object
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param object the object
    * @param name the name
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertAddMetaDataWithPreviousSameObject(MutableMetaDataLoader mutable, T object, String name, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(object);
      assertNotNull(type);
      assertNotNull(name);

      checkMetaData(type, mutable.retrieveMetaData(name));
      
      Object result = mutable.addMetaData(name, object, type);
      checkMetaData(type, result);
      checkMetaData(type, mutable.retrieveMetaData(name));

      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check an annotation is removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param expected the expected type
    * @param expectedAnnotations the expected annotations
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertRemoveAnnotation(MutableMetaData mutable, Class<T> expected, ExpectedAnnotations expectedAnnotations, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      assertRemoveAnnotation(metaData, mutable, expected, expectedAnnotations, last, false);
   }

   /**
    * Check an annotation is removed
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the meta data loader
    * @param expected the expected type
    * @param expectedAnnotations the expected annotations
    * @param last the last valid time
    * @param stillExpected whether it is still expected elsewhere
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertRemoveAnnotation(MetaData metaData, MutableMetaData mutable, Class<T> expected, ExpectedAnnotations expectedAnnotations, long last, boolean stillExpected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(expected);
      if (stillExpected == false)
         expectedAnnotations.remove(expected);

      assertTrue(metaData.isAnnotationPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected));
      assertTrue(metaData.isMetaDataPresent(expected.getName()));
      assertTrue(metaData.isMetaDataPresent(expected.getName(), expected));
      
      Annotation result = mutable.removeAnnotation(expected);
      checkAnnotation(expected, result);
      if (stillExpected)
      {
         assertTrue(metaData.isAnnotationPresent(expected));
         assertTrue(metaData.isMetaDataPresent(expected));
         assertTrue(metaData.isMetaDataPresent(expected.getName()));
         assertTrue(metaData.isMetaDataPresent(expected.getName(), expected));
      }
      else
      {
         assertFalse(metaData.isAnnotationPresent(expected));
         assertFalse(metaData.isMetaDataPresent(expected));
         assertFalse(metaData.isMetaDataPresent(expected.getName()));
         assertFalse(metaData.isMetaDataPresent(expected.getName(), expected));
      }
      
      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check an annotation is removed
    * 
    * @param <T> the type
    * @param mutable the retrieval
    * @param expected the expected type
    * @param expectedAnnotations the expected annotations
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertRemoveAnnotation(MutableMetaDataLoader mutable, Class<T> expected, ExpectedAnnotations expectedAnnotations, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(expected);
      expectedAnnotations.remove(expected);

      AnnotationItem annotationItem = mutable.retrieveAnnotation(expected);
      checkAnnotation(expected, annotationItem);
      MetaDataItem metaDataItem1 = mutable.retrieveMetaData(expected);
      checkAnnotation(expected, metaDataItem1);
      MetaDataItem metaDataItem2 = mutable.retrieveMetaData(expected.getName());
      checkAnnotation(expected, metaDataItem2);
      
      Annotation result = mutable.removeAnnotation(expected);
      checkAnnotation(expected, result);
      assertNull(mutable.retrieveAnnotation(expected));
      assertNull(mutable.retrieveMetaData(expected));
      assertNull(mutable.retrieveMetaData(expected.getName()));
      assertFalse(annotationItem.isValid());
      assertFalse(metaDataItem1.isValid());
      assertFalse(metaDataItem2.isValid());
      
      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check meta data is removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertRemoveMetaData(MutableMetaData mutable, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      assertRemoveMetaData(metaData, mutable, type, expected, last, false);
   }

   /**
    * Check meta data is removed
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @param stillExpected whether it is still expected elsewhere
    * @throws Exception for any error
    */
   protected <T> void assertRemoveMetaData(MetaData metaData, MutableMetaData mutable, Class<T> type, ExpectedMetaData expected, long last, boolean stillExpected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(type);
      if (stillExpected == false)
         expected.remove(type);

      assertTrue(metaData.isMetaDataPresent(type));
      assertTrue(metaData.isMetaDataPresent(type.getName()));
      assertTrue(metaData.isMetaDataPresent(type.getName(), type));
      
      Object result = mutable.removeMetaData(type);
      checkMetaData(type, result);
      if (stillExpected)
      {
         assertTrue(metaData.isMetaDataPresent(type));
         assertTrue(metaData.isMetaDataPresent(type.getName()));
         assertTrue(metaData.isMetaDataPresent(type.getName(), type));
      }
      else
      {
         assertFalse(metaData.isMetaDataPresent(type));
         assertFalse(metaData.isMetaDataPresent(type.getName()));
         assertFalse(metaData.isMetaDataPresent(type.getName(), type));
      }
      
      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check meta data is removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertRemoveMetaData(MutableMetaDataLoader mutable, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(type);
      expected.remove(type);

      checkMetaData(type, mutable.retrieveMetaData(type));
      checkMetaData(type, mutable.retrieveMetaData(type.getName()));
      
      Object result = mutable.removeMetaData(type);
      checkMetaData(type, result);
      assertNull(mutable.retrieveMetaData(type));
      assertNull(mutable.retrieveMetaData(type.getName()));
      
      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check meta data is removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param name the name
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertRemoveMetaData(MutableMetaData mutable, String name, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      MetaData metaData = (MetaData) mutable;
      assertRemoveMetaData(metaData, mutable, name, type, expected, last, false);
   }

   /**
    * Check meta data is removed
    * 
    * @param <T> the type
    * @param metaData the meta data
    * @param mutable the mutable
    * @param name the name
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @param stillExpected whether it is still expected elsewhere
    * @throws Exception for any error
    */
   protected <T> void assertRemoveMetaData(MetaData metaData, MutableMetaData mutable, String name, Class<T> type, ExpectedMetaData expected, long last, boolean stillExpected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(mutable);
      assertNotNull(type);
      assertNotNull(name);
      if (stillExpected == false)
         expected.remove(type);

      assertTrue(metaData.isMetaDataPresent(name));
      assertTrue(metaData.isMetaDataPresent(name, type));
      
      Object result = mutable.removeMetaData(name, type);
      checkMetaData(type, result);
      if (stillExpected)
      {
         assertTrue(metaData.isMetaDataPresent(name));
         assertTrue(metaData.isMetaDataPresent(name, type));
      }
      else
      {
         assertFalse(metaData.isMetaDataPresent(name));
         assertFalse(metaData.isMetaDataPresent(name, type));
      }
      
      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check meta data is removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param name the name
    * @param type the type
    * @param expected the expected meta data
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertRemoveMetaData(MutableMetaDataLoader mutable, String name, Class<T> type, ExpectedMetaData expected, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(type);
      expected.remove(type);

      checkMetaData(type, mutable.retrieveMetaData(name));
      
      Object result = mutable.removeMetaData(name, type);
      checkMetaData(type, result);
      assertNull(mutable.retrieveMetaData(name));
      
      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check an annotation is not removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param expected the expected type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertNotRemovedAnnotation(MutableMetaData mutable, Class<T> expected, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(expected);
      MetaData metaData = (MetaData) mutable;

      assertFalse(metaData.isAnnotationPresent(expected));
      assertFalse(metaData.isMetaDataPresent(expected));
      assertFalse(metaData.isMetaDataPresent(expected.getName()));
      assertFalse(metaData.isMetaDataPresent(expected.getName(), expected));
      
      Annotation result = mutable.removeAnnotation(expected);
      assertNull(result);
      assertFalse(metaData.isAnnotationPresent(expected));
      assertFalse(metaData.isMetaDataPresent(expected));
      assertFalse(metaData.isMetaDataPresent(expected.getName()));
      assertFalse(metaData.isMetaDataPresent(expected.getName(), expected));

      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check an annotation is not removed
    * 
    * @param <T> the type
    * @param mutable the retrieval
    * @param expected the expected type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T extends Annotation> void assertNotRemovedAnnotation(MutableMetaDataLoader mutable, Class<T> expected, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(expected);

      assertNull(mutable.retrieveAnnotation(expected));
      assertNull(mutable.retrieveMetaData(expected));
      assertNull(mutable.retrieveMetaData(expected.getName()));
      
      Annotation result = mutable.removeAnnotation(expected);
      assertNull(result);
      assertNull(mutable.retrieveAnnotation(expected));
      assertNull(mutable.retrieveMetaData(expected));
      assertNull(mutable.retrieveMetaData(expected.getName()));

      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check meta data is not removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertNotRemovedMetaData(MutableMetaData mutable, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(type);
      MetaData metaData = (MetaData) mutable;

      assertFalse(metaData.isMetaDataPresent(type));
      assertFalse(metaData.isMetaDataPresent(type.getName()));
      assertFalse(metaData.isMetaDataPresent(type.getName(), type));
      
      Object result = mutable.removeMetaData(type);
      assertNull(result);
      assertFalse(metaData.isMetaDataPresent(type));
      assertFalse(metaData.isMetaDataPresent(type.getName()));
      assertFalse(metaData.isMetaDataPresent(type.getName(), type));

      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check meta data is not removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertNotRemovedMetaData(MutableMetaDataLoader mutable, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(type);

      assertNull(mutable.retrieveMetaData(type));
      assertNull(mutable.retrieveMetaData(type.getName()));
      
      Object result = mutable.removeMetaData(type);
      assertNull(result);
      assertNull(mutable.retrieveMetaData(type));
      assertNull(mutable.retrieveMetaData(type.getName()));

      assertValidTimeUnchanged(mutable, last);
   }

   /**
    * Check meta data is not removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param name the name
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertNotRemovedMetaData(MutableMetaData mutable, String name, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(type);
      assertNotNull(name);
      MetaData metaData = (MetaData) mutable;

      assertFalse(metaData.isMetaDataPresent(name));
      assertFalse(metaData.isMetaDataPresent(name, type));
      
      Object result = mutable.removeMetaData(name, type);
      assertNull(result);
      assertFalse(metaData.isMetaDataPresent(name));
      assertFalse(metaData.isMetaDataPresent(name, type));

      assertValidTimeUnchanged(metaData, last);
   }

   /**
    * Check meta data is not removed
    * 
    * @param <T> the type
    * @param mutable the meta data
    * @param name the name
    * @param type the type
    * @param last the last valid time
    * @throws Exception for any error
    */
   protected <T> void assertNotRemovedMetaData(MutableMetaDataLoader mutable, String name, Class<T> type, long last) throws Exception
   {
      assertNotNull(mutable);
      assertNotNull(type);
      assertNotNull(name);

      assertNull(mutable.retrieveMetaData(name));
      
      Object result = mutable.removeMetaData(name, type);
      assertNull(result);
      assertNull(mutable.retrieveMetaData(name));

      assertValidTimeUnchanged(mutable, last);
   }
   
   /**
    * Assert the valid time changed
    * 
    * @param metaData the meta data
    * @param last the last valid time
    * @return the current valid time
    */
   protected long assertValidTimeChanged(MetaData metaData, long last)
   {
      assertNotNull(metaData);
      
      long now = metaData.getValidTime();
      assertTrue("ValidTime should change now=" + now + " last=" + last, now > last);
      return now;
   }
   
   /**
    * Assert the valid time changed
    * 
    * @param metaData the retrieval
    * @param last the last valid time
    * @return the current valid time
    */
   protected long assertValidTimeChanged(MetaDataRetrieval metaData, long last)
   {
      assertNotNull(metaData);
      
      long now = metaData.getValidTime().getValidTime();
      assertTrue("ValidTime should change now=" + now + " last=" + last, now > last);
      return now;
   }

   /**
    * Assert the valid time has not changed
    * 
    * @param metaData the meta data
    * @param last the last valid time
    */
   protected void assertValidTimeUnchanged(MetaData metaData, long last)
   {
      assertNotNull(metaData);

      long now = metaData.getValidTime();
      assertEquals("ValidTime should not change now=" + now + " last=" + last, now, last);
   }

   /**
    * Assert the valid time has not changed
    * 
    * @param metaData the retrieval
    * @param last the last valid time
    */
   protected void assertValidTimeUnchanged(MetaDataRetrieval metaData, long last)
   {
      assertNotNull(metaData);

      long now = metaData.getValidTime().getValidTime();
      assertEquals("ValidTime should not change now=" + now + " last=" + last, now, last);
   }
   
   /**
    * Create an empty expected annotations
    * 
    * @return the empty expected annotations
    */
   protected ExpectedAnnotations emptyExpectedAnnotations()
   {
      return new ExpectedAnnotations();
   }
   
   /**
    * Create an empty expected metadata
    * 
    * @return the empty expected metaData
    */
   protected ExpectedMetaData emptyExpectedMetaData()
   {
      return new ExpectedMetaData();
   }

   /**
    * Check the annotations from all retrieval methods
    * 
    * @param metaData the meta data
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertAllAnnotations(MetaData metaData, ExpectedAnnotations expected) throws Exception
   {
      assertAllAnnotations(metaData, expected, true);
   }

   /**
    * Check the annotations from all retrieval methods
    * 
    * @param metaData the meta data
    * @param expected the expected annotation classes
    * @param local whether to test the local annotations
    * @throws Exception for any error
    */
   protected void assertAllAnnotations(MetaData metaData, ExpectedAnnotations expected, boolean local) throws Exception
   {
      assertAnnotations(metaData, expected);
      if (local)
         assertLocalAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      if (local)
         assertLocalAnnotationMetaDatas(metaData, expected);
   }
   
   /**
    * Check the annotations
    * 
    * @param metaData the meta data
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertAnnotations(MetaData metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      Annotation[] result = metaData.getAnnotations();
      assertNotNull("Null result", result);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (Annotation annotation : result)
      {
         assertNotNull("Null annotation " + Arrays.asList(result), annotation);
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("Annotations", expected, actual);
   }
   
   /**
    * Check the local annotations
    * 
    * @param metaData the meta data
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertLocalAnnotations(MetaData metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      Annotation[] result = metaData.getLocalAnnotations();
      assertNotNull("Null result", result);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (Annotation annotation : result)
      {
         assertNotNull("Null annotation " + Arrays.asList(result), annotation);
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("Annotations", expected, actual);
   }

   /**
    * Check the annotations from all retrieval methods
    * 
    * @param metaData the retrieval
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertAllAnnotations(MetaDataRetrieval metaData, ExpectedAnnotations expected) throws Exception
   {
      assertAllAnnotations(metaData, expected, true);
   }

   /**
    * Check the annotations from all retrieval methods
    * 
    * @param metaData the retrieval
    * @param expected the expected annotation classes
    * @param local whether to test the local annotations
    * @throws Exception for any error
    */
   protected void assertAllAnnotations(MetaDataRetrieval metaData, ExpectedAnnotations expected, boolean local) throws Exception
   {
      assertAnnotations(metaData, expected);
      if (local)
         assertLocalAnnotations(metaData, expected);
      assertAnnotationMetaDatas(metaData, expected);
      if (local)
         assertLocalAnnotationMetaDatas(metaData, expected);
   }
   
   /**
    * Check the annotations
    * 
    * @param metaData the retrieval
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertAnnotations(MetaDataRetrieval metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      AnnotationsItem result = metaData.retrieveAnnotations();
      assertNotNull("Null result", result);
      AnnotationItem[] items = result.getAnnotations();
      assertNotNull("Null items", items);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (AnnotationItem item : items)
      {
         assertNotNull("Null annotation item " + Arrays.asList(items), item);
         Annotation annotation = item.getAnnotation();
         assertNotNull("Null annotation " + Arrays.asList(items), annotation);
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("Annotations", expected, actual);
   }
   
   /**
    * Check the local annotations
    * 
    * @param metaData the retrieval
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertLocalAnnotations(MetaDataRetrieval metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      AnnotationsItem result = metaData.retrieveLocalAnnotations();
      assertNotNull("Null result", result);
      AnnotationItem[] items = result.getAnnotations();
      assertNotNull("Null items", items);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (AnnotationItem item : items)
      {
         assertNotNull("Null annotation item " + Arrays.asList(items), item);
         Annotation annotation = item.getAnnotation();
         assertNotNull("Null annotation " + Arrays.asList(items), annotation);
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("Annotations", expected, actual);
   }
   
   /**
    * Check the meta data from all retrieval methods
    * 
    * @param metaData the meta data
    * @param expected the expected types
    * @throws Exception for any error
    */
   protected void assertAllMetaData(MetaData metaData, ExpectedMetaData expected) throws Exception
   {
      assertAllMetaData(metaData, expected, true);
   }
   
   /**
    * Check the meta data from all retrieval methods
    * 
    * @param metaData the meta data
    * @param expected the expected types
    * @param local whether to include the local meta data
    * @throws Exception for any error
    */
   protected void assertAllMetaData(MetaData metaData, ExpectedMetaData expected, boolean local) throws Exception
   {
      assertMetaData(metaData, expected);
      if (local)
         assertLocalMetaData(metaData, expected);
   }
   
   /**
    * Check the meta data
    * 
    * @param metaData the meta data
    * @param expected the expected types
    * @throws Exception for any error
    */
   protected void assertMetaData(MetaData metaData, ExpectedMetaData expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      Object[] result = metaData.getMetaData();
      assertNotNull("Null result", result);
      ExpectedMetaData actual = emptyExpectedMetaData();
      for (Object object : result)
      {
         assertNotNull("Null object " + Arrays.asList(result), object);
         actual.add(getType(object));
      }
      
      assertExpectedMetaData("MetaData", expected, actual);
   }
   
   /**
    * Check the local meta data
    * 
    * @param metaData the meta data
    * @param expected the expected types
    * @throws Exception for any error
    */
   protected void assertLocalMetaData(MetaData metaData, ExpectedMetaData expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      Object[] result = metaData.getLocalMetaData();
      assertNotNull("Null result", result);
      ExpectedMetaData actual = emptyExpectedMetaData();
      for (Object object : result)
      {
         assertNotNull("Null object " + Arrays.asList(result), object);
         actual.add(getType(object));
      }
      
      assertExpectedMetaData("MetaData", expected, actual);
   }
   
   /**
    * Check the meta data from all retrieval methods
    * 
    * @param metaData the retrieval
    * @param expected the expected types
    * @throws Exception for any error
    */
   protected void assertAllMetaData(MetaDataRetrieval metaData, ExpectedMetaData expected) throws Exception
   {
      assertAllMetaData(metaData, expected, true);
   }
   
   /**
    * Check the meta data from all retrieval methods
    * 
    * @param metaData the retrieval
    * @param expected the expected types
    * @param local whether to include the local meta data
    * @throws Exception for any error
    */
   protected void assertAllMetaData(MetaDataRetrieval metaData, ExpectedMetaData expected, boolean local) throws Exception
   {
      assertMetaData(metaData, expected);
      if (local)
         assertLocalMetaData(metaData, expected);
   }
   
   /**
    * Check the meta data
    * 
    * @param metaData the retrieval
    * @param expected the expected types
    * @throws Exception for any error
    */
   protected void assertMetaData(MetaDataRetrieval metaData, ExpectedMetaData expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      MetaDatasItem result = metaData.retrieveMetaData();
      assertNotNull("Null result", result);
      MetaDataItem[] items = result.getMetaDatas();
      assertNotNull("Null items", items);
      ExpectedMetaData actual = emptyExpectedMetaData();
      for (MetaDataItem item : items)
      {
         assertNotNull("Null meta data item " + Arrays.asList(items), item);
         Object object = item.getValue();
         assertNotNull("Null object " + Arrays.asList(items), object);
         actual.add(getType(object));
      }
      
      assertExpectedMetaData("MetaData", expected, actual);
   }
   
   /**
    * Check the local meta data
    * 
    * @param metaData the retrieval
    * @param expected the expected types
    * @throws Exception for any error
    */
   protected void assertLocalMetaData(MetaDataRetrieval metaData, ExpectedMetaData expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      MetaDatasItem result = metaData.retrieveLocalMetaData();
      assertNotNull("Null result", result);
      MetaDataItem[] items = result.getMetaDatas();
      assertNotNull("Null items", items);
      ExpectedMetaData actual = emptyExpectedMetaData();
      for (MetaDataItem item : items)
      {
         assertNotNull("Null meta data item " + Arrays.asList(items), item);
         Object object = item.getValue();
         assertNotNull("Null object " + Arrays.asList(items), object);
         actual.add(getType(object));
      }
      
      assertExpectedMetaData("MetaData", expected, actual);
   }

   /**
    * Get the type for the object
    * 
    * @param object the object
    * @return the type
    */
   protected Class getType(Object object)
   {
      Class type = object.getClass();
      Class[] interfaces = type.getInterfaces();
      if (interfaces.length == 1)
         return interfaces[0];
      return type;
   }
   
   /**
    * Check the meta data
    * 
    * @param metaData the meta data
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertAnnotationMetaDatas(MetaData metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      Object[] result = metaData.getMetaData();
      assertNotNull("Null result", result);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (Object object : result)
      {
         assertNotNull("Null annotation " + Arrays.asList(result), object);
         assertTrue("Not an annotation " + Arrays.asList(result), object instanceof Annotation);
         Annotation annotation = (Annotation) object;
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("AnnotationMetaDatas", expected, actual);
   }
   
   /**
    * Check the local meta data
    * 
    * @param metaData the meta data
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertLocalAnnotationMetaDatas(MetaData metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      Object[] result = metaData.getLocalMetaData();
      assertNotNull("Null result", result);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (Object object : result)
      {
         assertNotNull("Null annotation " + Arrays.asList(result), object);
         assertTrue("Not an annotation " + Arrays.asList(result), object instanceof Annotation);
         Annotation annotation = (Annotation) object;
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("AnnotationMetaDatas", expected, actual);
   }
   
   /**
    * Check the meta data
    * 
    * @param metaData the retrieval
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertAnnotationMetaDatas(MetaDataRetrieval metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      MetaDatasItem result = metaData.retrieveMetaData();
      assertNotNull("Null result", result);
      MetaDataItem[] items = result.getMetaDatas();
      assertNotNull("Null items", items);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (MetaDataItem item : items)
      {
         assertNotNull("Null item " + Arrays.asList(items), item);
         Object object = item.getValue();
         assertNotNull("Null object " + Arrays.asList(items), object);
         assertTrue("Not an annotation " + Arrays.asList(items), object instanceof Annotation);
         Annotation annotation = (Annotation) object;
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("AnnotationMetaDatas", expected, actual);
   }
   
   /**
    * Check the local meta data
    * 
    * @param metaData the retrieval
    * @param expected the expected annotation classes
    * @throws Exception for any error
    */
   protected void assertLocalAnnotationMetaDatas(MetaDataRetrieval metaData, ExpectedAnnotations expected) throws Exception
   {
      assertNotNull(metaData);
      assertNotNull(expected);
      
      MetaDatasItem result = metaData.retrieveLocalMetaData();
      assertNotNull("Null result", result);
      MetaDataItem[] items = result.getMetaDatas();
      assertNotNull("Null items", items);
      ExpectedAnnotations actual = emptyExpectedAnnotations();
      for (MetaDataItem item : items)
      {
         assertNotNull("Null item " + Arrays.asList(items), item);
         Object object = item.getValue();
         assertNotNull("Null object " + Arrays.asList(items), object);
         assertTrue("Not an annotation " + Arrays.asList(items), object instanceof Annotation);
         Annotation annotation = (Annotation) object;
         actual.add(annotation.annotationType());
      }
      
      assertExpectedAnnotations("AnnotationMetaDatas", expected, actual);
   }
   
   /**
    * Assert two expected annotations
    * 
    * @param context the context
    * @param expected the expected
    * @param actual the actual
    */
   protected void assertExpectedAnnotations(String context, ExpectedAnnotations expected, ExpectedAnnotations actual)
   {
      assertEquals(context, expected.get(), actual.get());
   }
   
   /**
    * Assert two expected meta datas
    * 
    * @param context the context
    * @param expected the expected
    * @param actual the actual
    */
   protected void assertExpectedMetaData(String context, ExpectedMetaData expected, ExpectedMetaData actual)
   {
      assertEquals(context, expected.get(), actual.get());
   }
   
   /**
    * Assert two collections are equal
    * 
    * @param <T> the type
    * @param context the context
    * @param expected the expected
    * @param actual the actual
    */
   protected <T> void assertEquals(String context, Collection<T> expected, Collection<T> actual)
   {
      if (expected == null && actual == null)
         return;
      if ((expected == null && actual != null) || (expected != null && actual == null))
         fail(context, expected, actual);
      if (expected.size() != actual.size())
         fail(context, expected, actual);
      if (expected.containsAll(actual) == false)
         fail(context, expected, actual);
      if (actual.containsAll(expected) == false)
         fail(context, expected, actual);
   }

   /**
    * Raise an assertion failure for unequal collections
    * 
    * @param context the context
    * @param expected the expected
    * @param actual the actual
    */
   private void fail(String context, Collection expected, Collection actual)
   {
      assertTrue("Unexpected " + context + " expected=" + expected + " actual=" + actual, false);
   }
}
