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
import org.jboss.metadata.spi.signature.ConstructorSignature;
import org.jboss.metadata.spi.signature.FieldSignature;
import org.jboss.metadata.spi.signature.MethodParametersSignature;
import org.jboss.metadata.spi.signature.MethodSignature;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.shared.support.ExpectedAnnotations;
import org.jboss.test.metadata.shared.support.NotPresentAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation2;

/**
 * ComponentBasicAnnotationsTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 46146 $
 */
public abstract class ComponentBasicAnnotationsTest extends AbstractMetaDataTest
{
   protected boolean local;
   
   public ComponentBasicAnnotationsTest(String name, boolean local)
   {
      super(name);
      this.local = local;
   }
   
   protected abstract MetaData setupConstructor();
   
   protected abstract MetaData setupField();
   
   protected abstract MetaData setupMethod();
   
   protected abstract MetaData setupMethodParams();
   
   public void testFieldNotFound() throws Exception
   {
      MetaData metaData = setupField();
      metaData = metaData.getComponentMetaData(new FieldSignature("notFound"));
      assertNull(metaData);
   }
   
   public void testConstructorNotFound() throws Exception
   {
      MetaData metaData = setupConstructor();
      metaData = metaData.getComponentMetaData(new ConstructorSignature(Void.class));
      assertNull(metaData);
   }
   
   public void testMethodNotFound() throws Exception
   {
      MetaData metaData = setupMethod();
      metaData = metaData.getComponentMetaData(new MethodSignature("notFound"));
      assertNull(metaData);
   }
   
   public void testMethodParamsNotFound() throws Exception
   {
      MetaData metaData = setupMethodParams();
      metaData = metaData.getComponentMetaData(new MethodParametersSignature("notFound", 0, Void.class));
      assertNull(metaData);
   }
   
   public void testFieldEmpty() throws Exception
   {
      MetaData metaData = setupField();
      metaData = metaData.getComponentMetaData(new FieldSignature("empty"));
      testEmpty(metaData);
   }
   
   public void testConstructorEmpty() throws Exception
   {
      MetaData metaData = setupConstructor();
      metaData = metaData.getComponentMetaData(new ConstructorSignature());
      testEmpty(metaData);
   }
   
   public void testMethodEmpty() throws Exception
   {
      MetaData metaData = setupMethod();
      metaData = metaData.getComponentMetaData(new MethodSignature("empty"));
      testEmpty(metaData);
   }
   
   public void testMethodParamsEmpty() throws Exception
   {
      MetaData metaData = setupMethodParams();
      metaData = metaData.getComponentMetaData(new MethodParametersSignature("empty", 0, String.class));
      testEmpty(metaData);
   }
      
   protected void testEmpty(MetaData metaData) throws Exception
   {
      assertNotNull(metaData);
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();

      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      
      assertAllAnnotations(metaData, expectedAnnotations, local);
   }
   
   public void testFieldTestAnnotation() throws Exception
   {
      MetaData metaData = setupField();
      metaData = metaData.getComponentMetaData(new FieldSignature("testAnnotation"));
      testTestAnnotation(metaData);
   }
   
   public void testConstructorTestAnnotation() throws Exception
   {
      MetaData metaData = setupConstructor();
      metaData = metaData.getComponentMetaData(new ConstructorSignature(String.class));
      testTestAnnotation(metaData);
   }
   
   public void testMethodTestAnnotation() throws Exception
   {
      MetaData metaData = setupMethod();
      metaData = metaData.getComponentMetaData(new MethodSignature("testAnnotation", String.class));
      testTestAnnotation(metaData);
   }
   
   public void testMethodParamsTestAnnotation() throws Exception
   {
      MetaData metaData = setupMethodParams();
      metaData = metaData.getComponentMetaData(new MethodParametersSignature("testAnnotation", 0, String.class));
      testTestAnnotation(metaData);
   }
   
   protected void testTestAnnotation(MetaData metaData) throws Exception
   {
      assertNotNull(metaData);

      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();

      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      
      expectedAnnotations.add(TestAnnotation.class);
      assertAllAnnotations(metaData, expectedAnnotations, local);
   }
   
   public void testFieldTestAnnotation12() throws Exception
   {
      MetaData metaData = setupField();
      metaData = metaData.getComponentMetaData(new FieldSignature("testAnnotation12"));
      testTestAnnotation12(metaData);
   }
   
   public void testConstructorTestAnnotation12() throws Exception
   {
      MetaData metaData = setupConstructor();
      metaData = metaData.getComponentMetaData(new ConstructorSignature(String.class, Class.class));
      testTestAnnotation12(metaData);
   }
   
   public void testMethodTestAnnotation12() throws Exception
   {
      MetaData metaData = setupMethod();
      metaData = metaData.getComponentMetaData(new MethodSignature("testAnnotation12", String.class, Class.class));
      testTestAnnotation12(metaData);
   }
   
   public void testMethodParamTestAnnotation12() throws Exception
   {
      MetaData metaData = setupMethodParams();
      metaData = metaData.getComponentMetaData(new MethodParametersSignature("testAnnotation12", 1, String.class, Class.class));
      testTestAnnotation12(metaData);
   }

   protected void testTestAnnotation12(MetaData metaData) throws Exception
   {
      assertNotNull(metaData);

      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertAnnotation(metaData, TestAnnotation1.class);
      assertAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      
      expectedAnnotations.add(TestAnnotation1.class);
      expectedAnnotations.add(TestAnnotation2.class);
      assertAllAnnotations(metaData, expectedAnnotations, local);
   }
}
