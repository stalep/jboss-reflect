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

import java.lang.reflect.AccessibleObject;
import java.security.AccessControlException;

import org.jboss.config.plugins.BasicConfiguration;
import org.jboss.config.spi.Configuration;
import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.test.classinfo.support.ErrorHolderThread;

/**
 * Access restriction test.
 *
 * @param <T> exact tester type
 * @param <U> exact annotated info
 * @param <V> exact accessible object
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public abstract class AccessRestrictionTest<T, U extends AnnotatedInfo, V extends AccessibleObject> extends AbstractTestCaseWithSetup
{
   /** The bean info factory */
   protected Configuration configuration = new BasicConfiguration();

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

   protected abstract T getInstance();
   protected abstract Class<T> getInstanceClass();
   protected abstract U getInfo();
   protected abstract U getSetAnnotatedInfo(ClassInfo info, String member);
   protected abstract U getGetAnnotatedInfo(ClassInfo info, String member);
   protected abstract V getAccessibleObject(String member) throws Exception;
   protected abstract void set(U annotatedInfo, T instance, String string) throws Throwable;
   protected abstract Object get(U annotatedInfo, T instance) throws Throwable;
   protected abstract void set(V accessibleObject, T instance, String string) throws Exception;
   protected abstract void set(U info, V accessibleObject);
   protected abstract String getPrivateString(T instance);
   protected abstract V getAccessibleObject(U info);

   public void testFieldAcessFromMain() throws Throwable
   {
      final T tester = getInstance();
      final U impl = getInfo();

      // I can't do setAccesible
      V accessibleObject = getAccessibleObject("privString");
      // let's try accessible
      try
      {
         accessibleObject.setAccessible(true);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      // ok, setAccessible not set, so set should also fail
      try
      {
         set(accessibleObject, tester, "foobar");
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalAccessException.class);
      }

      try
      {
         set(impl, accessibleObject);
         fail("Should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(AccessControlException.class, t);
      }
      try
      {
         set(accessibleObject, tester, "foobar");
         fail("Should not be here");
      }
      catch (Throwable t)
      {
         checkThrowable(IllegalAccessException.class, t);
      }
      assertNull(getPrivateString(tester));

      Runnable runnable = new Runnable()
      {
         public void run()
         {
            try
            {
               V ao = getAccessibleObject(impl); // This should have an access check
               set(ao, tester, "something"); // this should check for caller
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
      final T tester = getInstance();
      ClassInfo classInfo = configuration.getClassInfo(getInstanceClass());
      // we should not fail
      U pub = getGetAnnotatedInfo(classInfo, "pubString");
      assertNotNull(pub);
      final U priSet = getSetAnnotatedInfo(classInfo, "privString");
      final U priGet = getGetAnnotatedInfo(classInfo, "privString");

      // Shouldn't be able to set the private field
      try
      {
         set(priSet, tester, "foobar");
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      // Shouldn't be able to get the private field
      try
      {
         get(priGet, tester);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, AccessControlException.class);
      }
      // Shouldn't be able to steal the private field which has setAccessible(true)
      try
      {
         getAccessibleObject(priGet);
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
               set(priSet, tester, "foobar");
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
      assertNull(getPrivateString(tester));
      other.start();
      other.join();
      assertNull(other.getError());
      assertEquals("foobar", getPrivateString(tester));
   }
}
