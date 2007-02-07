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
package org.jboss.test.ioc.test;

import java.net.URL;

import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;

/**
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public abstract class AbstractIoCTest extends AbstractTestCaseWithSetup
{
   protected String rootName = getRootName();

   /**
    * Create a new AbstractJBossXBTest.
    *
    * @param name the name of the test
    */
   public AbstractIoCTest(String name)
   {
      super(name);
   }

   /**
    * Unmarshal some xml
    *
    * @param <T> the expected type
    * @param name the name
    * @param expected the expected type
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected <T> T unmarshal(String name, Class<T> expected) throws Exception
   {
      Object object = unmarshal(name);
      if (object == null)
         fail("No object from " + name);
      assertTrue("Object '" + object + "' cannot be assigned to " + expected.getName(), expected.isAssignableFrom(object.getClass()));
      return expected.cast(object);
   }

   /**
    * Unmarshal some xml
    *
    * @param name the name
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected Object unmarshal(String name) throws Exception
   {
      String url = findXML(name);
      return getJBossXBDelegate().unmarshal(url);
   }

   /**
    * Find the xml
    *
    * @param name the name
    * @return the url of the xml
    */
   protected String findXML(String name)
   {
      URL url = getResource(name);
      if (url == null)
         fail(name + " not found");
      return url.toString();
   }

   protected IoCTestDelegate getJBossXBDelegate()
   {
      return (IoCTestDelegate) getDelegate();
   }

   /**
    * Setup the test delegate
    *
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class clazz) throws Exception
   {
      return new IoCTestDelegate(clazz);
   }

   protected void setUp() throws Exception
   {
      super.setUp();
      configureLogging();
   }

   /**
    * Get the package root name
    *
    * @return the root name
    */
   protected String getRootName()
   {
      String longName = getClass().getName();
      int dot = longName.lastIndexOf('.');
      if (dot != -1)
         return longName.substring(dot + 1);
      return longName;
   }

   protected void configureLogging()
   {
      //enableTrace("org.jboss.xb");
   }
}

