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
package org.jboss.test.beaninfo.test;

import java.security.AccessControlException;

import org.jboss.beans.info.spi.BeanAccessMode;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.config.plugins.BasicConfiguration;
import org.jboss.config.spi.Configuration;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;

/**
 * Access restriction test.
 *
 * @param <T> exact tester class
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public abstract class AccessRestrictionTest<T> extends AbstractTestCaseWithSetup
{
   /** The bean info factory */
   private Configuration configuration = new BasicConfiguration();

   /**
    * Create a new AccessRestrictionTest.
    *
    * @param name the test name
    */
   protected AccessRestrictionTest(String name)
   {
      super(name);
   }

   /**
    * Default setup with security manager enabled
    *
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      AbstractTestDelegate delegate = new AbstractTestDelegate(clazz);
      delegate.enableSecurity = true;
      return delegate;
   }

   protected abstract T getInstance();
   protected abstract Class<T> getInstanceClass();
   protected abstract String getPublicString(T instance);
   protected abstract String getPrivateString(T instance);

   public void testBeanFieldAccess() throws Throwable
   {
      // First try to get the Bean info without the priviledge on the private field
      T test = getInstance();
      // This should work
      BeanInfo beanInfo = configuration.getBeanInfo(getInstanceClass(), BeanAccessMode.ALL);
      
      // We should be able to set the public field
      beanInfo.setProperty(test, "pubString", "public");
      assertEquals("public", getPublicString(test));
      
      // But we shouldn't be able to set the private field
      try
      {
         beanInfo.setProperty(test, "privString", "private");
         fail("should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(AccessControlException.class, t);
      }
      try
      {
         beanInfo.getProperty(test, "privString");
         fail("should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(AccessControlException.class, t);
      }
      assertNull(getPrivateString(test));

      // Repeat for the properties
      PropertyInfo pubProp = beanInfo.getProperty("pubString");
      test = getInstance();
      
      pubProp.set(test, "public");
      assertEquals("public", getPublicString(test));
      
      PropertyInfo privProp = beanInfo.getProperty("privString");
      try
      {
         privProp.set(test, "private");
         fail("should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(AccessControlException.class, t);
      }
      try
      {
         privProp.get(test);
         fail("should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(AccessControlException.class, t);
      }
      assertNull(getPrivateString(test));
      
      // Now lets disable security and check we can do what we couldn't do before
      SecurityManager sm = suspendSecurity();
      try
      {
         test = getInstance();
         beanInfo.setProperty(test, "privString", "private");
         assertEquals("private", beanInfo.getProperty(test, "privString"));

         test = getInstance();
         privProp.set(test, "private");
         assertEquals("private", privProp.get(test));
      }
      finally
      {
         resumeSecurity(sm);
      }
   }
}
