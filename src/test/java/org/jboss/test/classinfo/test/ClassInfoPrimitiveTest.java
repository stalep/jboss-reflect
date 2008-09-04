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

import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * ClassInfoPrimitiveTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoPrimitiveTest extends AbstractClassInfoTest
{
   public ClassInfoPrimitiveTest(String name)
   {
      super(name);
   }
   
   public void testBoolean() throws Throwable
   {
      testPrimitive(Boolean.TYPE, PrimitiveInfo.BOOLEAN, new String[] { "true", "false" }, new Boolean[] { true, false } );
   }
   
   public void testByte() throws Throwable
   {
      testPrimitive(Byte.TYPE, PrimitiveInfo.BYTE, new String[] { "1", "2" }, new Byte[] { 1, 2 });
   }
   
   public void testChar() throws Throwable
   {
      testPrimitive(Character.TYPE, PrimitiveInfo.CHAR, new String[] { "a", "b" }, new Character[] { 'a', 'b' });
   }
   
   public void testDouble() throws Throwable
   {
      testPrimitive(Double.TYPE, PrimitiveInfo.DOUBLE, new String[] { "1.2", "3.14" }, new Double[] { 1.2, 3.14 });
   }
   
   public void testFloat() throws Throwable
   {
      testPrimitive(Float.TYPE, PrimitiveInfo.FLOAT, new String[] { "1.0e10", "4.2e5" }, new Float[] { 1.0e10f, 4.2e5f });
   }
   
   public void testInt() throws Throwable
   {
      testPrimitive(Integer.TYPE, PrimitiveInfo.INT, new String[] { "1", "2" }, new Integer[] { 1, 2 });
   }
   
   public void testLong() throws Throwable
   {
      testPrimitive(Long.TYPE, PrimitiveInfo.LONG, new String[] { "1", "2" }, new Long[] { 1l, 2l });
   }
   
   public void testShort() throws Throwable
   {
      testPrimitive(Short.TYPE, PrimitiveInfo.SHORT, new String[] { "1", "2" }, new Short[] { 1, 2 });
   }
   
   public void testVoid() throws Throwable
   {
      testPrimitive(Void.TYPE, PrimitiveInfo.VOID, null, null);
   }
   
   protected <T> void testPrimitive(Class<T> clazz, PrimitiveInfo primitive, String[] values, T[] types) throws Throwable
   {
      TypeInfo info = testBasics(clazz, primitive);
      assertFalse(info.isArray());
      assertFalse(info.isEnum());
      assertTrue(info.isPrimitive());
      
      if (values == null)
         return;

      testArray(clazz, info);
      
      testValues(clazz, info, values, types);
   }
}
