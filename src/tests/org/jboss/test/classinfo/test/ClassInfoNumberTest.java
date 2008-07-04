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

import org.jboss.reflect.spi.NumberInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * ClassInfoNumberTest.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public abstract class ClassInfoNumberTest extends AbstractClassInfoTest
{
   public ClassInfoNumberTest(String name)
   {
      super(name);
   }

   public void testByte() throws Throwable
   {
      testNumber(Byte.class, NumberInfo.BYTE_OBJECT, new String[] { "1", "2" }, new Byte[] { 1, 2 });
   }

   public void testDouble() throws Throwable
   {
      testNumber(Double.class, NumberInfo.DOUBLE_OBJECT, new String[] { "1.2", "3.14" }, new Double[] { 1.2, 3.14 });
   }

   public void testFloat() throws Throwable
   {
      testNumber(Float.class, NumberInfo.FLOAT_OBJECT, new String[] { "1.0e10", "4.2e5" }, new Float[] { 1.0e10f, 4.2e5f });
   }

   public void testInt() throws Throwable
   {
      testNumber(Integer.class, NumberInfo.INT_OBJECT, new String[] { "1", "2" }, new Integer[] { 1, 2 });
   }

   public void testLong() throws Throwable
   {
      testNumber(Long.class, NumberInfo.LONG_OBJECT, new String[] { "1", "2" }, new Long[] { 1l, 2l });
   }

   public void testShort() throws Throwable
   {
      testNumber(Short.class, NumberInfo.SHORT_OBJECT, new String[] { "1", "2" }, new Short[] { 1, 2 });
   }

/*
   public void testAtomicInt() throws Throwable
   {
      testNumber(AtomicInteger.class, NumberInfo.ATOMIC_INT, new String[] { "1", "2" }, new AtomicInteger[] {new AtomicInteger(1), new AtomicInteger(2)});
   }

   public void testAtomicLong() throws Throwable
   {
      testNumber(AtomicLong.class, NumberInfo.ATOMIC_INT, new String[] { "1", "2" }, new AtomicLong[] {new AtomicLong(1), new AtomicLong(2)});
   }
*/

   protected <T> void testNumber(Class<T> clazz, NumberInfo number, String[] values, T[] types) throws Throwable
   {
      TypeInfo info = testBasics(clazz, number);
      assertFalse(info.isArray());
      assertFalse(info.isEnum());
      assertFalse(info.isPrimitive());

      if (values == null)
         return;

      testArray(clazz, info);
      testValues(clazz, info, values, types);
   }
}