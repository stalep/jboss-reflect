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
import org.jboss.test.metadata.shared.support.ExpectedAnnotations;
import org.jboss.test.metadata.shared.support.NotPresentAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotation1;
import org.jboss.test.metadata.shared.support.TestAnnotation2;

/**
 * BasicAnnotationsTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class BasicAnnotationsTest extends AbstractMetaDataTest
{
   protected boolean local;
   
   public BasicAnnotationsTest(String name, boolean local)
   {
      super(name);
      this.local = local;
   }
   
   public void testEmpty() throws Exception
   {
      MetaData metaData = setupEmpty();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();

      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      
      assertAllAnnotations(metaData, expectedAnnotations, local);
   }
   
   protected abstract MetaData setupEmpty();
   
   public void testTestAnnotation() throws Exception
   {
      MetaData metaData = setupTestAnnotation();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();

      assertAnnotation(metaData, TestAnnotation.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      
      expectedAnnotations.add(TestAnnotation.class);
      assertAllAnnotations(metaData, expectedAnnotations, local);
   }
   
   protected abstract MetaData setupTestAnnotation();
   
   public void testTestAnnotation12() throws Exception
   {
      MetaData metaData = setupTestAnnotation12();
      ExpectedAnnotations expectedAnnotations = emptyExpectedAnnotations();
      
      assertAnnotation(metaData, TestAnnotation1.class);
      assertAnnotation(metaData, TestAnnotation2.class);
      assertNoAnnotation(metaData, NotPresentAnnotation.class);
      
      expectedAnnotations.add(TestAnnotation1.class);
      expectedAnnotations.add(TestAnnotation2.class);
      assertAllAnnotations(metaData, expectedAnnotations, local);
   }
   
   protected abstract MetaData setupTestAnnotation12();
}
