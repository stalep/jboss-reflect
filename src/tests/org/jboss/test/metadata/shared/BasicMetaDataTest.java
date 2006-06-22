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
package org.jboss.test.metadata.shared;

import org.jboss.metadata.spi.MetaData;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.shared.support.ExpectedMetaData;
import org.jboss.test.metadata.shared.support.NotPresentType;
import org.jboss.test.metadata.shared.support.TestMetaData;
import org.jboss.test.metadata.shared.support.TestMetaData1;
import org.jboss.test.metadata.shared.support.TestMetaData2;

/**
 * BasicMetaDataTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class BasicMetaDataTest extends AbstractMetaDataTest
{
   public BasicMetaDataTest(String name)
   {
      super(name);
   }
   
   public void testEmpty() throws Exception
   {
      MetaData metaData = setupEmpty();
      ExpectedMetaData expected = emptyExpectedMetaData();

      assertNoMetaData(metaData, NotPresentType.class);
      
      assertMetaData(metaData, expected);
   }
   
   protected abstract MetaData setupEmpty();
   
   public void testTestMetaData() throws Exception
   {
      MetaData metaData = setupTestMetaData();
      ExpectedMetaData expected = emptyExpectedMetaData();

      assertMetaData(metaData, TestMetaData.class);
      assertNoMetaData(metaData, NotPresentType.class);
      
      expected.add(TestMetaData.class);
      assertMetaData(metaData, expected);
   }
   
   protected abstract MetaData setupTestMetaData();
   
   public void testTestMetaData12() throws Exception
   {
      MetaData metaData = setupTestMetaData12();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertMetaData(metaData, TestMetaData1.class);
      assertMetaData(metaData, TestMetaData2.class);
      assertNoMetaData(metaData, NotPresentType.class);
      
      expected.add(TestMetaData1.class);
      expected.add(TestMetaData2.class);
      assertMetaData(metaData, expected);
   }
   
   protected abstract MetaData setupTestMetaData12();
   
   public void testTestMetaDataByName() throws Exception
   {
      MetaData metaData = setupTestMetaDataByName();
      ExpectedMetaData expected = emptyExpectedMetaData();

      assertMetaData(metaData, "Test", TestMetaData.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      
      expected.add(TestMetaData.class);
      assertMetaData(metaData, expected);
   }
   
   protected abstract MetaData setupTestMetaDataByName();
   
   public void testTestMetaData12ByName() throws Exception
   {
      MetaData metaData = setupTestMetaData12ByName();
      ExpectedMetaData expected = emptyExpectedMetaData();
      
      assertMetaData(metaData, "Test1", TestMetaData1.class);
      assertMetaData(metaData, "Test2", TestMetaData2.class);
      assertNoMetaData(metaData, "NotPresent", NotPresentType.class);
      
      expected.add(TestMetaData1.class);
      expected.add(TestMetaData2.class);
      assertMetaData(metaData, expected);
   }
   
   protected abstract MetaData setupTestMetaData12ByName();
}
