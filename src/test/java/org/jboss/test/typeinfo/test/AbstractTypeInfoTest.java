/*
 * JBoss, Home of Professional Open Source.
 * Copyright 2008, Red Hat Middleware LLC, and individual contributors
 * as indicated by the @author tags. See the copyright.txt file in the
 * distribution for a full listing of individual contributors.
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
package org.jboss.test.typeinfo.test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;

import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.ContainerTest;
import org.jboss.test.typeinfo.support.GenericInterface;
import org.jboss.test.typeinfo.support.TestImpl;
import org.jboss.test.typeinfo.support.TestInterface;
import org.jboss.test.typeinfo.support.GenericStringImpl;
import org.jboss.test.typeinfo.support.GenericIntegerImpl;

/**
 * Type info test.
 *
 * @author <a href="mailto:ales.justin@jboss.org">Ales Justin</a>
 */
public abstract class AbstractTypeInfoTest extends ContainerTest
{
   protected AbstractTypeInfoTest(String name)
   {
      super(name);
   }

   protected abstract TypeInfoFactory getTypeInfoFactory();

   // TODO - remove this after JBMICROCONT-129 is done
   protected abstract boolean isTypeSupported();

   protected TypeInfo getTypeInfo(Type type) throws Exception
   {
      return getTypeInfoFactory().getTypeInfo(type);
   }

   public void testIsAssignableFrom() throws Throwable
   {
      TypeInfo first = getTypeInfo(int.class);
      TypeInfo second = getTypeInfo(double.class);

      assertIsAssignableFrom(first, second, true);
      second = getTypeInfo(Date.class);
      assertIsAssignableFrom(first, second, false);

      first = getTypeInfo(TestInterface.class);
      assertIsAssignableFrom(first, second, false);

      second = getTypeInfo(TestInterface.class);
      assertIsAssignableFrom(first, second, true);
      second = getTypeInfo(TestImpl.class);
      assertIsAssignableFrom(first, second, true);

      if (isTypeSupported())
      {
         first = getTypeInfo(getType("getGenericStringInterface"));
         second = getTypeInfo(GenericStringImpl.class);
         assertIsAssignableFrom(first, second, true);

         second = getTypeInfo(GenericIntegerImpl.class);
         // TODO - better impl could return false?
         assertIsAssignableFrom(first, second, true);
      }
   }

   public void testIsInstance() throws Throwable
   {
      TypeInfo first = getTypeInfo(int.class);
      Object second = 123d;

      assertIsInstance(first, second, true);
      second = (byte)123;
      assertIsInstance(first, second, true);
      second = new Date();
      assertIsInstance(first, second, false);

      first = getTypeInfo(TestInterface.class);
      assertIsInstance(first, second, false);

      second = new TestImpl();
      assertIsInstance(first, second, true);

      if (isTypeSupported())
      {
         first = getTypeInfo(getType("getGenericStringInterface"));
         second = new GenericStringImpl();
         assertIsInstance(first, second, true);

         second = new GenericIntegerImpl();
         // TODO - better impl could return false?
         assertIsInstance(first, second, true);
      }
   }

   protected void assertIsAssignableFrom(TypeInfo first, TypeInfo second, boolean expected) throws Throwable
   {
      assertEquals(expected, first.isAssignableFrom(second));
   }

   protected void assertIsInstance(TypeInfo typeInfo, Object object, boolean expected) throws Throwable
   {
      assertEquals(expected, typeInfo.isInstance(object));
   }

   protected Type getType(String methodName, Class<?>... parameters) throws Exception
   {
      Method method = getClass().getMethod(methodName, parameters);
      return method.getGenericReturnType();
   }

   public GenericInterface<String> getGenericStringInterface()
   {
      return null;
   }
}