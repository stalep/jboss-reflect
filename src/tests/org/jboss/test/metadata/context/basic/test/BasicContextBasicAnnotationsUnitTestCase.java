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
package org.jboss.test.metadata.context.basic.test;

import org.jboss.metadata.plugins.context.AbstractMetaDataContext;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.loader.MutableMetaDataLoader;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.test.metadata.shared.BasicAnnotationsTest;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * BasicContextBasicAnnotationsUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicContextBasicAnnotationsUnitTestCase extends BasicAnnotationsTest
{
   public BasicContextBasicAnnotationsUnitTestCase(String name)
   {
      super(name, true);
   }

   protected MetaData setupMetaData(MetaDataLoader loader)
   {
      AbstractMetaDataContext context = new AbstractMetaDataContext(null, loader);
      return new MetaDataRetrievalToMetaDataBridge(context);
   }
   
   protected MetaData setupEmpty()
   {
      MutableMetaDataLoader loader = createTestMutableMetaDataLoader();
      return setupMetaData(loader);
   }

   protected MetaData setupTestAnnotation()
   {
      MutableMetaDataLoader loader = createTestMutableMetaDataLoader();
      loader.addAnnotation(new TestAnnotationImpl());
      return setupMetaData(loader);
   }

   protected MetaData setupTestAnnotation12()
   {
      MutableMetaDataLoader loader = createTestMutableMetaDataLoader();
      loader.addAnnotation(new TestAnnotation1Impl());
      loader.addAnnotation(new TestAnnotation2Impl());
      return setupMetaData(loader);
   }
}
