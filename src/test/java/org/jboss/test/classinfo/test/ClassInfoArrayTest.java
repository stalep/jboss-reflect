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

import org.jboss.reflect.plugins.ArrayInfoImpl;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.classinfo.support.ClassInfoAnnotationClass;

/**
 * ClassInfoArrayTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoArrayTest extends AbstractClassInfoTest
{
   public ClassInfoArrayTest(String name)
   {
      super(name);
   }
   
   public void testSimpleArray() throws Throwable
   {
      String[] array = new String[0];
      testArray(array);
   }
   public void testByteArray()
      throws Throwable
   {
      byte[] array = {1, 2, 3, 4};
      testArray(array);
   }
   public void test2DByteArray()
      throws Throwable
   {
      byte[][] array = {{1, 2, 3, 4}, {5, 6, 7, 8}};
      testArray(array);
   }
   public void testCharArray()
      throws Throwable
   {
      char[] array = {'h', 'e', 'l', 'l', 'o'};
      testArray(array);
   }
   public void testCharacterArray()
      throws Throwable
   {
      Character[] array = {'h', 'e', 'l', 'l', 'o'};
      testArray(array);
   }
   public void test2DCharacterArray()
      throws Throwable
   {
      Character[][] array = {{'h', 'e', 'l', 'l', 'o'}, {'w', 'o', 'r', 'l', 'd'}};
      testArray(array);
   }
   public void testfloatArray()
      throws Throwable
   {
      float[] array = {1.0f, 2.0f, 3f, 4f};
      testArray(array);
   }
   public void testFloatArray()
      throws Throwable
   {
      Float[] array = {1.0f, 2.0f, 3f, 4f};
      testArray(array);
   }
   public void testNumberArray()
      throws Throwable
   {
      Number[] array = {1, 2, 3, 4};
      testArray(array);
   }

   public void testArrayType()
   {
      String[] array = {"hello", "world"};
      TypeInfoFactory factory = getTypeInfoFactory();
      ArrayInfo info = (ArrayInfo) factory.getTypeInfo(array.getClass());

      assertEquals(info.getName(), "[Ljava.lang.String;", info.getName());
      TypeInfo info0 = info.getComponentType();
      assertEquals(info0.getName(), "java.lang.String", info0.getName());
   }
   public void test2DArrayType()
   {
      String[][] array = {{"hello"}, {"world"}};
      TypeInfoFactory factory = getTypeInfoFactory();
      ArrayInfo info = (ArrayInfo) factory.getTypeInfo(array.getClass());
      
      assertEquals(info.getName(), "[[Ljava.lang.String;", info.getName());
      ArrayInfo info0 = (ArrayInfo) info.getComponentType();
      assertEquals(info0.getName(), "[Ljava.lang.String;", info0.getName());
      TypeInfo info1 = info0.getComponentType();
      assertEquals(info1.getName(), "java.lang.String", info1.getName());
   }

   public void testDeepArray() throws Throwable
   {
      String[][][][][] array = new String[1][2][3][4][0];
      testArray(array);
   }
   
   public void testAnnotationArray() throws Throwable
   {
      ClassInfoAnnotationClass[] array = new ClassInfoAnnotationClass[0];
      testArray(array);
   }
   
   private void testArray(Object array) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Class<?> arrayClass = array.getClass();
      Class<?> componentClass = arrayClass.getComponentType();
      TypeInfo componentType = factory.getTypeInfo(componentClass);
      ArrayInfoImpl expected = new ArrayInfoImpl(componentType);
      TypeInfo info = testBasics(array.getClass(), expected);
      
      assertTrue(info.isArray());
      assertFalse(info.isEnum());
      assertFalse(info.isPrimitive());
      
      ArrayInfo arrayInfo = (ArrayInfo) info;
      assertEquals(componentType, arrayInfo.getComponentType());
      assertClassInfo(arrayInfo, arrayClass);
   }
}
