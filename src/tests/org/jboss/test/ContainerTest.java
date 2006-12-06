/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test;

import java.util.Arrays;

import junit.framework.AssertionFailedError;

/**
 * A ContainerTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ContainerTest extends AbstractTestCaseWithSetup
{
   /**
    * Create a new ContainerTest.
    * 
    * @param name the test name
    */
   public ContainerTest(String name)
   {
      super(name);
   }
   
   /**
    * Assert an array is empty or null
    * 
    * TODO Move this to the AbstractTestCase
    * @param array the array
    */
   protected static void assertEmpty(Object[] array)
   {
      if (array != null)
         assertEquals(Arrays.asList(array).toString(), 0, array.length);
   }

   /**
    * Assert two arrays are equal
    *
    * TODO fix the abstract test case
    * @param expected the expected array
    * @param actual the actual array
    */
   protected void assertEquals(Object[] expected, Object[] actual)
   {
      if (Arrays.equals(expected, actual) == false)
      {
         String expectedString = null;
         if (expected != null)
            expectedString = Arrays.asList(expected).toString();
         String actualString = null;
         if (actual != null)
            actualString = Arrays.asList(actual).toString();
         throw new AssertionFailedError("expected: " + expectedString + " actual: " + actualString);
      }
   }

   /**
    * Default setup with security manager enabled
    * 
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class clazz) throws Exception
   {
      AbstractTestDelegate delegate = new AbstractTestDelegate(clazz);
      //delegate.enableSecurity = true;
      return delegate;
   }
}
