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
package org.jboss.test.classinfo.test;

import java.lang.reflect.Field;
import java.security.AccessControlException;

import junit.framework.Test;

import org.jboss.config.plugins.BasicConfiguration;
import org.jboss.config.spi.Configuration;
import org.jboss.reflect.plugins.introspection.ReflectFieldInfoImpl;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.classinfo.support.ErrorHolderThread;
import org.jboss.test.classinfo.support.FieldsClass;

/**
 * Access restriction test.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class AccessRestrictionTestCase extends AbstractTestCaseWithSetup
{
   /** The bean info factory */
   private Configuration configuration = new BasicConfiguration();

   /**
    * Create a new ContainerTest.
    *
    * @param name the test name
    */
   public AccessRestrictionTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(AccessRestrictionTestCase.class);
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

   protected ClassInfo getClassInfo(Class<?> clazz)
   {
      SecurityManager sm = suspendSecurity();
      try
      {
         return configuration.getClassInfo(clazz);
      }
      finally
      {
         resumeSecurity(sm);
      }
   }

   public void testFieldAcessFromMain() throws Throwable
   {
      final FieldsClass tester = new FieldsClass();
      final ReflectFieldInfoImpl impl = new ReflectFieldInfoImpl();

      // I can't do setAccesible
      Field field = FieldsClass.class.getDeclaredField("privString");
      // let's try accessible
      try
      {
         field.setAccessible(true);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      // ok, setAccessible not set, so set should also fail
      try
      {
         field.set(tester, "foobar");
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalAccessException.class);
      }

      try
      {
         impl.setField(field);
         fail("Should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(AccessControlException.class, t);
      }
      try
      {
         field.set(tester, "foobar");
         fail("Should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(IllegalAccessException.class, t);
      }
      assertNull("foobar", tester.getPrivString());

      Runnable runnable = new Runnable()
      {
         public void run()
         {
            try
            {
               Field fi = impl.getField(); // This should have an access check
               fi.set(tester, "something"); // this should check for caller
            }
            catch (Throwable t)
            {
               throw new RuntimeException(t);
            }
         }
      };
      ErrorHolderThread other = new ErrorHolderThread(runnable);
      other.start();
      other.join();
      // we should get an error here
      assertNotNull("Should get access restriction exception.", other.getError());
      RuntimeException re = assertInstanceOf(other.getError(), RuntimeException.class);
      Throwable cause = re.getCause();
      assertNotNull(cause);
      assertInstanceOf(cause, AccessControlException.class, false);
   }

   public void testFieldAccessFromOther() throws Throwable
   {
      final FieldsClass tester = new FieldsClass();
      ClassInfo classInfo = configuration.getClassInfo(FieldsClass.class);
      // we should not fail
      FieldInfo pub = classInfo.getDeclaredField("pubString");
      assertNotNull(pub);
      final FieldInfo pri = classInfo.getDeclaredField("privString");

      // Shouldn't be able to set the private field
      try
      {
         pri.set(tester, "foobar");
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      // Shouldn't be able to get the private field
      try
      {
         pri.get(tester);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      // Shouldn't be able to steal the private field which has setAccessible(true)
      try
      {
         ((ReflectFieldInfoImpl) pri).getField();
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      ErrorHolderThread other = new ErrorHolderThread(new Runnable()
      {
         public void run()
         {
            SecurityManager sm = suspendSecurity();
            try
            {
               pri.set(tester, "foobar");
            }
            catch(Throwable t)
            {
               throw new RuntimeException(t);
            }
            finally
            {
               resumeSecurity(sm);
            }
         }
      });
      assertNull(tester.getPrivString());
      other.start();
      other.join();
      assertNull(other.getError());
      assertEquals("foobar", tester.getPrivString());
   }
}
