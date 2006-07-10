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
package org.jboss.test.metadata.loader.memory.test;

import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.test.metadata.shared.BasicMetaDataTest;
import org.jboss.test.metadata.shared.support.TestMetaData;
import org.jboss.test.metadata.shared.support.TestMetaData1;
import org.jboss.test.metadata.shared.support.TestMetaData1Impl;
import org.jboss.test.metadata.shared.support.TestMetaData2;
import org.jboss.test.metadata.shared.support.TestMetaData2Impl;
import org.jboss.test.metadata.shared.support.TestMetaDataImpl;

/**
 * MemoryLoaderBasicMetaDataUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MemoryLoaderBasicMetaDataUnitTestCase extends BasicMetaDataTest
{
   public MemoryLoaderBasicMetaDataUnitTestCase(String name)
   {
      super(name, true);
   }

   protected MetaData setupEmpty()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupTestMetaData()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      loader.addMetaData(new TestMetaDataImpl(), TestMetaData.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupTestMetaData12()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      loader.addMetaData(new TestMetaData1Impl(), TestMetaData1.class);
      loader.addMetaData(new TestMetaData2Impl(), TestMetaData2.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupTestMetaDataByName()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      loader.addMetaData("Test", new TestMetaDataImpl(), TestMetaData.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupTestMetaData12ByName()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      loader.addMetaData("Test1", new TestMetaData1Impl(), TestMetaData1.class);
      loader.addMetaData("Test2", new TestMetaData2Impl(), TestMetaData2.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }
}
