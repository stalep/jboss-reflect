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
import org.jboss.test.metadata.shared.support.ExpectedMetaData;
import org.jboss.test.metadata.shared.support.TestMetaData;
import org.jboss.test.metadata.shared.support.TestMetaData1;
import org.jboss.test.metadata.shared.support.TestMetaData1Impl;
import org.jboss.test.metadata.shared.support.TestMetaData2;
import org.jboss.test.metadata.shared.support.TestMetaData2Impl;
import org.jboss.test.metadata.shared.support.TestMetaDataImpl;

/**
 * MetaDataContextMetaDataTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MetaDataContextMetaDataTest extends AbstractMetaDataContextMemoryLoaderTest
{
   public MetaDataContextMetaDataTest(String name)
   {
      super(name);
   }
   
   protected void testMetaData(MetaData metaData, MutableMetaDataLoader loader, boolean local) throws Exception
   {
      ExpectedMetaData expected = emptyExpectedMetaData();
      long last = metaData.getValidTime();
      assertNoMetaData(metaData, TestMetaData.class);

      TestMetaData object = new TestMetaDataImpl();
      last = assertAddMetaDataNoPrevious(metaData, loader, object, TestMetaData.class, expected, last);
      assertMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader, TestMetaData.class, expected, last, false);
      assertNoMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
   }
   
   public void testMetaDataFromFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData(metaData, getFirstChild(), true);
   }
   
   public void testMetaDataFromSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData(metaData, getSecondChild(), true);
   }
   
   public void testMetaDataFromFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData(metaData, getFirstParent(), false);
   }
   
   public void testMetaDataFromSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData(metaData, getSecondParent(), false);
   }
   
   protected void testMetaDataByName(MetaData metaData, MutableMetaDataLoader loader, boolean local) throws Exception
   {
      ExpectedMetaData expected = emptyExpectedMetaData();
      long last = metaData.getValidTime();
      assertNoMetaData(metaData, "Test", TestMetaData.class);

      TestMetaData object = new TestMetaDataImpl();
      last = assertAddMetaDataNoPrevious(metaData, loader, object, "Test", TestMetaData.class, expected, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader, "Test", TestMetaData.class, expected, last, false);
      assertNoMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
   }
   
   public void testMetaDataFromFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataByName(metaData, getFirstChild(), true);
   }
   
   public void testMetaDataFromSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataByName(metaData, getSecondChild(), true);
   }
   
   public void testMetaDataFromFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataByName(metaData, getFirstParent(), false);
   }
   
   public void testMetaDataFromSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataByName(metaData, getSecondParent(), false);
   }
   
   protected void testMetaData12(MetaData metaData, MutableMetaDataLoader loader1, MutableMetaDataLoader loader2, boolean local) throws Exception
   {
      ExpectedMetaData expected = emptyExpectedMetaData();
      long last = metaData.getValidTime();
      assertNoMetaData(metaData, TestMetaData1.class);
      assertNoMetaData(metaData, TestMetaData2.class);

      TestMetaData1 object1 = new TestMetaData1Impl();
      last = assertAddMetaDataNoPrevious(metaData, loader1, object1, TestMetaData1.class, expected, last);
      assertMetaData(metaData, TestMetaData1.class);
      assertNoMetaData(metaData, TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);

      TestMetaData2 object2 = new TestMetaData2Impl();
      last = assertAddMetaDataNoPrevious(metaData, loader2, object2, TestMetaData2.class, expected, last);
      assertMetaData(metaData, TestMetaData1.class);
      assertMetaData(metaData, TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader1, TestMetaData1.class, expected, last, false);
      assertNoMetaData(metaData, TestMetaData1.class);
      assertMetaData(metaData, TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader2, TestMetaData2.class, expected, last, false);
      assertNoMetaData(metaData, TestMetaData1.class);
      assertNoMetaData(metaData, TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);
   }
   
   public void testMetaData12FromFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstChild(), getFirstChild(), true);
   }
   
   public void testMetaData12FromFirstChildSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstChild(), getSecondChild(), true);
   }
   
   public void testMetaData12FromFirstChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstChild(), getFirstParent(), false);
   }
   
   public void testMetaData12FromFirstChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstChild(), getSecondParent(), false);
   }
   
   public void testMetaData12FromSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondChild(), getSecondChild(), true);
   }
   
   public void testMetaData12FromSecondChildFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondChild(), getFirstChild(), true);
   }
   
   public void testMetaData12FromSecondChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondChild(), getFirstParent(), false);
   }
   
   public void testMetaData12FromSecondChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondChild(), getSecondParent(), false);
   }
   
   public void testMetaData12FromFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstParent(), getFirstParent(), false);
   }
   
   public void testMetaData12FromFirstParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstParent(), getFirstChild(), false);
   }
   
   public void testMetaData12FromFirstParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstParent(), getSecondChild(), false);
   }
   
   public void testMetaData12FromFirstParentSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getFirstParent(), getSecondParent(), false);
   }
   
   public void testMetaData12FromSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondParent(), getSecondParent(), false);
   }
   
   public void testMetaData12FromSecondParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondParent(), getFirstChild(), false);
   }
   
   public void testMetaData12FromSecondParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondParent(), getSecondChild(), false);
   }
   
   public void testMetaData12FromSecondParentFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12(metaData, getSecondParent(), getFirstParent(), false);
   }
   
   protected void testMetaData12ByName(MetaData metaData, MutableMetaDataLoader loader1, MutableMetaDataLoader loader2, boolean local) throws Exception
   {
      ExpectedMetaData expected = emptyExpectedMetaData();
      long last = metaData.getValidTime();
      assertNoMetaData(metaData, "Test1", TestMetaData1.class);
      assertNoMetaData(metaData, "Test2", TestMetaData2.class);

      TestMetaData1 object1 = new TestMetaData1Impl();
      last = assertAddMetaDataNoPrevious(metaData, loader1, object1, "Test1", TestMetaData1.class, expected, last);
      assertMetaData(metaData, "Test1", TestMetaData1.class);
      assertNoMetaData(metaData, "Test2", TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);

      TestMetaData2 object2 = new TestMetaData2Impl();
      last = assertAddMetaDataNoPrevious(metaData, loader2, object2, "Test2", TestMetaData2.class, expected, last);
      assertMetaData(metaData, "Test1", TestMetaData1.class);
      assertMetaData(metaData, "Test2", TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader1, "Test1", TestMetaData1.class, expected, last, false);
      assertNoMetaData(metaData, "Test1", TestMetaData1.class);
      assertMetaData(metaData, "Test2", TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader2, "Test2", TestMetaData2.class, expected, last, false);
      assertNoMetaData(metaData, "Test1", TestMetaData1.class);
      assertNoMetaData(metaData, "Test2", TestMetaData2.class);
      
      assertAllMetaData(metaData, expected, local);
   }
   
   public void testMetaData12FromFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstChild(), getFirstChild(), true);
   }
   
   public void testMetaData12FromFirstChildSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstChild(), getSecondChild(), true);
   }
   
   public void testMetaData12FromFirstChildFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstChild(), getFirstParent(), false);
   }
   
   public void testMetaData12FromFirstChildSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstChild(), getSecondParent(), false);
   }
   
   public void testMetaData12FromSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondChild(), getSecondChild(), true);
   }
   
   public void testMetaData12FromSecondChildFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondChild(), getFirstChild(), true);
   }
   
   public void testMetaData12FromSecondChildFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondChild(), getFirstParent(), false);
   }
   
   public void testMetaData12FromSecondChildSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondChild(), getSecondParent(), false);
   }
   
   public void testMetaData12FromFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstParent(), getFirstParent(), false);
   }
   
   public void testMetaData12FromFirstParentFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstParent(), getFirstChild(), false);
   }
   
   public void testMetaData12FromFirstParentSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstParent(), getSecondChild(), false);
   }
   
   public void testMetaData12FromFirstParentSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getFirstParent(), getSecondParent(), false);
   }
   
   public void testMetaData12FromSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondParent(), getSecondParent(), false);
   }
   
   public void testMetaData12FromSecondParentFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondParent(), getFirstChild(), false);
   }
   
   public void testMetaData12FromSecondParentSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondParent(), getSecondChild(), false);
   }
   
   public void testMetaData12FromSecondParentFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaData12ByName(metaData, getSecondParent(), getFirstParent(), false);
   }
   
   protected void testMetaDataOverride(MetaData metaData, MutableMetaDataLoader loader1, MutableMetaDataLoader loader2, boolean local) throws Exception
   {
      ExpectedMetaData expected = emptyExpectedMetaData();
      long last = metaData.getValidTime();
      assertNoMetaData(metaData, TestMetaData.class);

      TestMetaData object1 = new TestMetaDataImpl();
      last = assertAddMetaDataNoPrevious(metaData, loader1, object1, TestMetaData.class, expected, last);
      assertMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);

      TestMetaData object2 = new TestMetaDataImpl();
      last = assertAddMetaDataWithPrevious(metaData, loader2, object2, TestMetaData.class, last);
      assertMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader1, TestMetaData.class, expected, last, true);
      assertMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader2, TestMetaData.class, expected, last, false);
      assertNoMetaData(metaData, TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
   }
   
   public void testMetaDataOverrideFromFirstChildSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getFirstChild(), getSecondChild(), true);
   }
   
   public void testMetaDataOverrideFromFirstChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getFirstChild(), getFirstParent(), false);
   }
   
   public void testMetaDataOverrideFromFirstChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getFirstChild(), getSecondParent(), false);
   }
   
   public void testMetaDataOverrideFromSecondChildFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getSecondChild(), getFirstChild(), true);
   }
   
   public void testMetaDataOverrideFromSecondChildFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getSecondChild(), getFirstParent(), false);
   }
   
   public void testMetaDataOverrideFromSecondChildSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getSecondChild(), getSecondParent(), false);
   }
   
   public void testMetaDataOverrideFromFirstParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getFirstParent(), getFirstChild(), false);
   }
   
   public void testMetaDataOverrideFromFirstParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getFirstParent(), getSecondChild(), false);
   }
   
   public void testMetaDataOverrideFromFirstParentSecondParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getFirstParent(), getSecondParent(), false);
   }
   
   public void testMetaDataOverrideFromSecondParentFirstChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getSecondParent(), getFirstChild(), false);
   }
   
   public void testMetaDataOverrideFromSecondParentSecondChild() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getSecondParent(), getSecondChild(), false);
   }
   
   public void testMetaDataOverrideFromSecondParentFirstParent() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverride(metaData, getSecondParent(), getFirstParent(), false);
   }
   
   protected void testMetaDataOverrideByName(MetaData metaData, MutableMetaDataLoader loader1, MutableMetaDataLoader loader2, boolean local) throws Exception
   {
      ExpectedMetaData expected = emptyExpectedMetaData();
      long last = metaData.getValidTime();
      assertNoMetaData(metaData, "Test", TestMetaData.class);

      TestMetaData object1 = new TestMetaDataImpl();
      last = assertAddMetaDataNoPrevious(metaData, loader1, object1, "Test", TestMetaData.class, expected, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);

      TestMetaData object2 = new TestMetaDataImpl();
      last = assertAddMetaDataWithPrevious(metaData, loader2, object2, "Test", TestMetaData.class, last);
      assertMetaData(metaData, "Test", TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader1, "Test", TestMetaData.class, expected, last, true);
      assertMetaData(metaData, "Test", TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
      
      assertRemoveMetaData(metaData, loader2, "Test", TestMetaData.class, expected, last, false);
      assertNoMetaData(metaData, "Test", TestMetaData.class);
      
      assertAllMetaData(metaData, expected, local);
   }
   
   public void testMetaDataOverrideFromFirstChildSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getFirstChild(), getSecondChild(), true);
   }
   
   public void testMetaDataOverrideFromFirstChildFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getFirstChild(), getFirstParent(), false);
   }
   
   public void testMetaDataOverrideFromFirstChildSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getFirstChild(), getSecondParent(), false);
   }
   
   public void testMetaDataOverrideFromSecondChildFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getSecondChild(), getFirstChild(), true);
   }
   
   public void testMetaDataOverrideFromSecondChildFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getSecondChild(), getFirstParent(), false);
   }
   
   public void testMetaDataOverrideFromSecondChildSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getSecondChild(), getSecondParent(), false);
   }
   
   public void testMetaDataOverrideFromFirstParentFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getFirstParent(), getFirstChild(), false);
   }
   
   public void testMetaDataOverrideFromFirstParentSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getFirstParent(), getSecondChild(), false);
   }
   
   public void testMetaDataOverrideFromFirstParentSecondParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getFirstParent(), getSecondParent(), false);
   }
   
   public void testMetaDataOverrideFromSecondParentFirstChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getSecondParent(), getFirstChild(), false);
   }
   
   public void testMetaDataOverrideFromSecondParentSecondChildByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getSecondParent(), getSecondChild(), false);
   }
   
   public void testMetaDataOverrideFromSecondParentFirstParentByName() throws Exception
   {
      MetaData metaData = createTestContext();
      testMetaDataOverrideByName(metaData, getSecondParent(), getFirstParent(), false);
   }
}
