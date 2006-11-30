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

import java.lang.reflect.Modifier;
import java.util.HashSet;

import org.jboss.reflect.plugins.EnumConstantInfoImpl;
import org.jboss.reflect.plugins.EnumInfoImpl;
import org.jboss.reflect.spi.EnumConstantInfo;
import org.jboss.reflect.spi.EnumInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.test.classinfo.support.ClassInfoEnum;
import org.jboss.test.classinfo.support.ClassInfoEnumAnnotation;
import org.jboss.test.classinfo.support.ClassInfoEnumFieldAnnotation;

/**
 * ClassInfoEnumTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoEnumTest extends AbstractClassInfoTest
{
   public ClassInfoEnumTest(String name)
   {
      super(name);
   }
   
   public void testEnum() throws Throwable
   {
      testEnum(ClassInfoEnum.class);
   }
   
   public void testEnumAnnotation() throws Throwable
   {
      testEnum(ClassInfoEnumAnnotation.class);
   }
   
   public void testEnumFieldAnnotation() throws Throwable
   {
      testEnum(ClassInfoEnumFieldAnnotation.class);
   }
   
   private void testEnum(Class enumClass) throws Throwable
   {
      EnumInfoImpl expected = new EnumInfoImpl(enumClass.getName(), Modifier.PUBLIC);
      TypeInfo info = testBasics(enumClass, expected);
      
      assertFalse(info.isArray());
      assertTrue(info.isEnum());
      assertFalse(info.isPrimitive());
      
      EnumInfo enumInfo = (EnumInfo) info;
      assertEnumConstants(enumInfo);
      
      assertClassInfo(enumInfo, enumClass);
   }
   
   protected void assertEnumConstants(EnumInfo enumInfo) throws Throwable
   {
      HashSet<EnumConstantInfo> expected = new HashSet<EnumConstantInfo>();

      EnumConstantInfoImpl constant = new EnumConstantInfoImpl("ONE", enumInfo);
      expected.add(constant);
      constant = new EnumConstantInfoImpl("TWO", enumInfo);
      expected.add(constant);
      constant = new EnumConstantInfoImpl("THREE", enumInfo);
      expected.add(constant);
      
      EnumConstantInfo[] constants = enumInfo.getEnumConstants();
      assertNotNull(constants);
      HashSet<EnumConstantInfo> actual = new HashSet<EnumConstantInfo>();
      for (EnumConstantInfo c : constants)
         actual.add(c);
      assertEquals(expected, actual);
   }
}
