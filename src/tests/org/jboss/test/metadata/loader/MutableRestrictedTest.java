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
import org.jboss.test.metadata.shared.support.RestrictedAnnotation;
import org.jboss.test.metadata.shared.support.RestrictedAnnotationImpl;
import org.jboss.test.metadata.shared.support.RestrictedImpl;
import org.jboss.test.metadata.shared.support.RestrictedInterface;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * MutableRestrictedTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class MutableRestrictedTest extends AbstractMetaDataTest
{
   public MutableRestrictedTest(String name)
   {
      super(name);
   }
   
   protected abstract MutableMetaDataLoader setUpRestricted();
   
   public void testRestrictedAnnotation() throws Exception
   {
      MutableMetaDataLoader loader = setUpRestricted();
      
      TestAnnotation testAnnotation = new TestAnnotationImpl();
      loader.addAnnotation(testAnnotation);
      assertNotNull(loader.retrieveAnnotation(TestAnnotation.class));

      RestrictedAnnotation restrictedAnnotation = new RestrictedAnnotationImpl();
      try
      {
         loader.addAnnotation(restrictedAnnotation);
      }
      catch (Throwable t)
      {
         checkThrowable(SecurityException.class, t);
      }
   }
   
   public void testRestrictedMetaData() throws Exception
   {
      MutableMetaDataLoader loader = setUpRestricted();

      Object object = new Object();
      loader.addMetaData(object, Object.class);
      assertNotNull(loader.retrieveMetaData(Object.class));

      RestrictedInterface restrictedMetaData = new RestrictedImpl();
      try
      {
         loader.addMetaData(restrictedMetaData, RestrictedInterface.class);
      }
      catch (Throwable t)
      {
         checkThrowable(SecurityException.class, t);
      }
   }
   
   public void testRestrictedMetaDataByName() throws Exception
   {
      MutableMetaDataLoader loader = setUpRestricted();

      Object object = new Object();
      loader.addMetaData("Object", object, Object.class);
      assertNotNull(loader.retrieveMetaData("Object"));

      RestrictedInterface restrictedMetaData = new RestrictedImpl();
      try
      {
         loader.addMetaData("Restricted", restrictedMetaData, RestrictedInterface.class);
      }
      catch (Throwable t)
      {
         checkThrowable(SecurityException.class, t);
      }
   }
}
