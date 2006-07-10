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

import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.shared.support.ExpectedMetaData;
import org.jboss.test.metadata.shared.support.NotPresentType;
import org.jboss.test.metadata.shared.support.TestMetaData;
import org.jboss.test.metadata.shared.support.TestMetaData1;
import org.jboss.test.metadata.shared.support.TestMetaData1Impl;
import org.jboss.test.metadata.shared.support.TestMetaData2;
import org.jboss.test.metadata.shared.support.TestMetaData2Impl;
import org.jboss.test.metadata.shared.support.TestMetaDataImpl;

/**
 * MutableMetaDataLoaderMetaDataInvalidationTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MutableMetaDataLoaderMetaDataInvalidationTest extends AbstractMetaDataTest
{
   public MutableMetaDataLoaderMetaDataInvalidationTest(String name)
   {
      super(name);
   }
   
   public void testEmpty() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);

      assertValidTimeUnchanged(metaData, last);
   }
   
   public void testTestMetaData() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataNoPrevious(metaData, new TestMetaDataImpl(), TestMetaData.class, expected, last);
      assertMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      assertRemoveMetaData(metaData, TestMetaData.class, expected, last);
      assertNoMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }
   
   public void testTestMetaData12() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, TestMetaData1.class);
      assertNoMetaData(metaData, TestMetaData2.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataNoPrevious(metaData, new TestMetaData1Impl(), TestMetaData1.class, expected, last);
      assertMetaData(metaData, TestMetaData1.class);
      assertNoMetaData(metaData, TestMetaData2.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);

      last = assertAddMetaDataNoPrevious(metaData, new TestMetaData2Impl(), TestMetaData2.class, expected, last);
      assertMetaData(metaData, TestMetaData1.class);
      assertMetaData(metaData, TestMetaData2.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      assertRemoveMetaData(metaData, TestMetaData1.class, expected, last);
      assertNoMetaData(metaData, TestMetaData1.class);
      assertMetaData(metaData, TestMetaData2.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);

      assertRemoveMetaData(metaData, TestMetaData2.class, expected, last);
      assertNoMetaData(metaData, TestMetaData1.class);
      assertNoMetaData(metaData, TestMetaData2.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }
   
   public void testAddTwice() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataNoPrevious(metaData, new TestMetaDataImpl(), TestMetaData.class, expected, last);
      assertMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataWithPrevious(metaData, new TestMetaDataImpl(), TestMetaData.class, last);
      assertMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }
   
   public void testAddTwiceSameObject() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      TestMetaData object = new TestMetaDataImpl();
      last = assertAddMetaDataNoPrevious(metaData, object, TestMetaData.class, expected, last);
      assertMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      assertAddMetaDataWithPreviousSameObject(metaData, object, TestMetaData.class, last);
      assertMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }

   public void testRemoveDoesNotExist() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();

      assertNotRemovedMetaData(metaData, TestMetaData.class, last);
      assertAllMetaData(metaData, expected);
   }
   
   public void testRemoveTwice() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();

      last = assertAddMetaDataNoPrevious(metaData, new TestMetaDataImpl(), TestMetaData.class, expected, last);
      assertRemoveMetaData(metaData, TestMetaData.class, expected, last);
      assertNotRemovedMetaData(metaData, TestMetaData.class, last);
      assertAllMetaData(metaData, expected);
   }
   
   public void testTestMetaDataByName() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataNoPrevious(metaData, new TestMetaDataImpl(), "Test", TestMetaData.class, expected, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      assertRemoveMetaData(metaData, "Test", TestMetaData.class, expected, last);
      assertNoMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }
   
   public void testTestMetaData12ByName() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, "Test1", TestMetaData1.class);
      assertNoMetaData(metaData, "Test2", TestMetaData2.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataNoPrevious(metaData, new TestMetaData1Impl(), "Test1", TestMetaData1.class, expected, last);
      assertMetaData(metaData, "Test1", TestMetaData1.class);
      assertNoMetaData(metaData, "Test2", TestMetaData2.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);

      last = assertAddMetaDataNoPrevious(metaData, new TestMetaData2Impl(), "Test2", TestMetaData2.class, expected, last);
      assertMetaData(metaData, "Test1", TestMetaData1.class);
      assertMetaData(metaData, "Test2", TestMetaData2.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      assertRemoveMetaData(metaData, "Test1", TestMetaData1.class, expected, last);
      assertNoMetaData(metaData, "Test1", TestMetaData1.class);
      assertMetaData(metaData, "Test2", TestMetaData2.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);

      assertRemoveMetaData(metaData, "Test2", TestMetaData2.class, expected, last);
      assertNoMetaData(metaData, "Test1", TestMetaData1.class);
      assertNoMetaData(metaData, "Test2", TestMetaData2.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }
   
   public void testAddTwiceByName() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataNoPrevious(metaData, new TestMetaDataImpl(), "Test", TestMetaData.class, expected, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      last = assertAddMetaDataWithPrevious(metaData, new TestMetaDataImpl(), "Test", TestMetaData.class, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }
   
   public void testAddTwiceSameObjectByName() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertNoMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      TestMetaData object = new TestMetaDataImpl();
      last = assertAddMetaDataNoPrevious(metaData, object, "Test", TestMetaData.class, expected, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
      
      assertAddMetaDataWithPreviousSameObject(metaData, object, "Test", TestMetaData.class, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      assertAllMetaData(metaData, expected);
   }

   public void testRemoveDoesNotExistByName() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();

      assertNotRemovedMetaData(metaData, "Test", TestMetaData.class, last);
      assertAllMetaData(metaData, expected);
   }
   
   public void testRemoveTwiceByName() throws Exception
   {
      MutableMetaDataLoader metaData = setupEmpty();
      long last = metaData.getValidTime().getValidTime();
      ExpectedMetaData expected = emptyExpectedMetaData();

      last = assertAddMetaDataNoPrevious(metaData, new TestMetaDataImpl(), "Test", TestMetaData.class, expected, last);
      assertRemoveMetaData(metaData, "Test", TestMetaData.class, expected, last);
      assertNotRemovedMetaData(metaData, "Test", TestMetaData.class, last);
      assertAllMetaData(metaData, expected);
   }
   
   protected abstract MutableMetaDataLoader setupEmpty();
}
