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
import java.util.Arrays;

import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.classinfo.support.ClassInfoEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoGenericClass;
import org.jboss.test.classinfo.support.ClassInfoGenericConstructorsClass;
import org.jboss.test.classinfo.support.ClassInfoGenericFieldsClass;
import org.jboss.test.classinfo.support.ClassInfoGenericInterface;
import org.jboss.test.classinfo.support.ClassInfoGenericMethodsClass;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperClassEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperClassString;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperInterfaceEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperInterfaceString;


/**
 * ClassInfoGenericClassTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoGenericClassTest extends AbstractClassInfoTest
{
   public ClassInfoGenericClassTest(String name)
   {
      super(name);
   }
   
   public void testGenericSuperClassString()
   {
      testGenericSuperClass(ClassInfoGenericSuperClassString.class, ClassInfoGenericClass.class, new Class[] { String.class });
   }
   
   public void testGenericSuperClassEmptyClass()
   {
      testGenericSuperClass(ClassInfoGenericSuperClassEmptyClass.class, ClassInfoGenericClass.class, new Class[] { ClassInfoEmptyClass.class });
   }
   
   public void testGenericSuperInterfaceString()
   {
      testGenericSuperInterface(ClassInfoGenericSuperInterfaceString.class, ClassInfoGenericInterface.class, new Class[] { String.class });
   }
   
   public void testGenericSuperInterfaceEmptyClass()
   {
      testGenericSuperInterface(ClassInfoGenericSuperInterfaceEmptyClass.class, ClassInfoGenericInterface.class, new Class[] { ClassInfoEmptyClass.class });
   }
   
   public void testGenericSuperClass(Class clazz, Class genericClass, Class[] genericTypes)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      ClassInfo typeInfo = (ClassInfo) factory.getTypeInfo(clazz);
      ClassInfo superClassInfo = typeInfo.getGenericSuperclass();
      ClassInfo genericClassInfo = (ClassInfo) factory.getTypeInfo(genericClass);
      getLog().debug("Checking superClass: " + genericClass + " against " + superClassInfo);
      assertEquals(genericClassInfo, superClassInfo);

      TypeInfo[] types = new TypeInfo[genericTypes.length];
      for (int i = 0; i < types.length; ++i)
         types[i] = factory.getTypeInfo(genericTypes[i]);
      TypeInfo[] actualTypes = superClassInfo.getActualTypeArguments();
      getLog().debug("Checking superClass types: " + Arrays.asList(genericTypes) + " against " + Arrays.asList(actualTypes));
      assertEquals(types.length, actualTypes.length);
      for (int i = 0; i < types.length; ++i)
         assertEquals(types[i], actualTypes[i]);
   }
   
   public void testGenericSuperInterface(Class clazz, Class genericClass, Class[] genericTypes)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      ClassInfo typeInfo = (ClassInfo) factory.getTypeInfo(clazz);
      InterfaceInfo[] superInterfaces = typeInfo.getGenericInterfaces();
      assertNotNull(superInterfaces);
      assertEquals(1, superInterfaces.length);
      InterfaceInfo superInterface = superInterfaces[0]; 
      getLog().debug("Checking superInterface: " + genericClass + " against " + superInterface);
      ClassInfo genericClassInfo = (ClassInfo) factory.getTypeInfo(genericClass);
      assertEquals(genericClassInfo, superInterface);

      TypeInfo[] types = new TypeInfo[genericTypes.length];
      for (int i = 0; i < types.length; ++i)
         types[i] = factory.getTypeInfo(genericTypes[i]);
      TypeInfo[] actualTypes = superInterface.getActualTypeArguments();
      getLog().debug("Checking superInterface types: " + Arrays.asList(genericTypes) + " against " + Arrays.asList(actualTypes));
      assertEquals(types.length, actualTypes.length);
      for (int i = 0; i < types.length; ++i)
         assertEquals(types[i], actualTypes[i]);
   }

   public void testGenericMethodsClass() throws Throwable
   {
      testGenericClass(ClassInfoGenericMethodsClass.class);
   }

   public void testGenericConstructorsClass() throws Throwable
   {
      testGenericClass(ClassInfoGenericConstructorsClass.class);
   }
   
   public void testGenericFieldsClass() throws Throwable
   {
      testGenericClass(ClassInfoGenericFieldsClass.class);
   }
   
   private void testGenericClass(Class clazz) throws Throwable
   {
      ClassInfoImpl expected = new ClassInfoImpl(clazz.getName(), Modifier.PUBLIC);
      TypeInfo info = testBasics(clazz, expected);
      
      assertFalse(info.isArray());
      assertFalse(info.isEnum());
      assertFalse(info.isPrimitive());
      
      ClassInfo classInfo = (ClassInfo) info;
      assertClassInfo(classInfo, clazz);
   }
}
