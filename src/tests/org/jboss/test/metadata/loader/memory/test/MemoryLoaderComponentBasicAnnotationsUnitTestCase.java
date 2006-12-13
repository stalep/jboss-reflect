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
import org.jboss.metadata.spi.signature.ConstructorSignature;
import org.jboss.metadata.spi.signature.FieldSignature;
import org.jboss.metadata.spi.signature.MethodParametersSignature;
import org.jboss.metadata.spi.signature.MethodSignature;
import org.jboss.test.metadata.shared.ComponentBasicAnnotationsTest;
import org.jboss.test.metadata.shared.support.TestAnnotation1Impl;
import org.jboss.test.metadata.shared.support.TestAnnotation2Impl;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * MemoryLoaderComponentBasicAnnotationsUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MemoryLoaderComponentBasicAnnotationsUnitTestCase extends ComponentBasicAnnotationsTest
{
   public MemoryLoaderComponentBasicAnnotationsUnitTestCase(String name)
   {
      super(name, true);
   }

   protected MetaData setupField()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      MemoryMetaDataLoader component = new MemoryMetaDataLoader();
      loader.addComponentMetaDataRetrieval(new FieldSignature("empty"), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotationImpl());
      loader.addComponentMetaDataRetrieval(new FieldSignature("testAnnotation"), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotation1Impl());
      component.addAnnotation(new TestAnnotation2Impl());
      loader.addComponentMetaDataRetrieval(new FieldSignature("testAnnotation12"), component);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupConstructor()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      MemoryMetaDataLoader component = new MemoryMetaDataLoader();
      loader.addComponentMetaDataRetrieval(new ConstructorSignature(), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotationImpl());
      loader.addComponentMetaDataRetrieval(new ConstructorSignature(String.class), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotation1Impl());
      component.addAnnotation(new TestAnnotation2Impl());
      loader.addComponentMetaDataRetrieval(new ConstructorSignature(String.class, Class.class), component);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupMethod()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      MemoryMetaDataLoader component = new MemoryMetaDataLoader();
      loader.addComponentMetaDataRetrieval(new MethodSignature("empty"), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotationImpl());
      loader.addComponentMetaDataRetrieval(new MethodSignature("testAnnotation", String.class), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotation1Impl());
      component.addAnnotation(new TestAnnotation2Impl());
      loader.addComponentMetaDataRetrieval(new MethodSignature("testAnnotation12", String.class, Class.class), component);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupMethodParams()
   {
      MemoryMetaDataLoader loader = new MemoryMetaDataLoader();
      MemoryMetaDataLoader component = new MemoryMetaDataLoader();
      loader.addComponentMetaDataRetrieval(new MethodParametersSignature("empty", 0, String.class), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotationImpl());
      loader.addComponentMetaDataRetrieval(new MethodParametersSignature("testAnnotation", 0, String.class), component);
      component = new MemoryMetaDataLoader();
      component.addAnnotation(new TestAnnotation1Impl());
      component.addAnnotation(new TestAnnotation2Impl());
      loader.addComponentMetaDataRetrieval(new MethodParametersSignature("testAnnotation12", 1, String.class, Class.class), component);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }
}
