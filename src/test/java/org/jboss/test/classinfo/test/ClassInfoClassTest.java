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

import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.test.classinfo.support.ClassInfoAbstractMethodsClass;
import org.jboss.test.classinfo.support.ClassInfoAnnotationClass;
import org.jboss.test.classinfo.support.ClassInfoConstructorAnnotationClass;
import org.jboss.test.classinfo.support.ClassInfoConstructorClass;
import org.jboss.test.classinfo.support.ClassInfoConstructorParameterAnnotationClass;
import org.jboss.test.classinfo.support.ClassInfoEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoFieldAnnotationClass;
import org.jboss.test.classinfo.support.ClassInfoFieldsClass;
import org.jboss.test.classinfo.support.ClassInfoInterfacesClass;
import org.jboss.test.classinfo.support.ClassInfoMethodAnnotationClass;
import org.jboss.test.classinfo.support.ClassInfoMethodParameterAnnotationClass;
import org.jboss.test.classinfo.support.ClassInfoMethodsClass;
import org.jboss.test.classinfo.support.ClassInfoSuperClass;

/**
 * ClassInfoClassTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoClassTest extends AbstractClassInfoTest
{
   public ClassInfoClassTest(String name)
   {
      super(name);
   }
   
   public void testEmptyClass() throws Throwable
   {
      testClass(ClassInfoEmptyClass.class);
   }
   
   public void testSuperClass() throws Throwable
   {
      testClass(ClassInfoSuperClass.class);
   }
   
   public void testInterfacesClass() throws Throwable
   {
      testClass(ClassInfoInterfacesClass.class);
   }
   
   public void testMethodsClass() throws Throwable
   {
      testClass(ClassInfoMethodsClass.class);
   }
   
   public void testAbstractMethodsClass() throws Throwable
   {
      testClass(ClassInfoAbstractMethodsClass.class);
   }
   
   public void testFieldsClass() throws Throwable
   {
      testClass(ClassInfoFieldsClass.class);
   }
   
   public void testConstructorClass() throws Throwable
   {
      testClass(ClassInfoConstructorClass.class);
   }
   
   public void testAnnotationClass() throws Throwable
   {
      testClass(ClassInfoAnnotationClass.class);
   }
   
   public void testFieldAnnotationClass() throws Throwable
   {
      testClass(ClassInfoFieldAnnotationClass.class);
   }
   
   public void testMethodAnnotationClass() throws Throwable
   {
      testClass(ClassInfoMethodAnnotationClass.class);
   }
   
   public void testMethodParameterAnnotationClass() throws Throwable
   {
      testClass(ClassInfoMethodParameterAnnotationClass.class);
   }
   
   public void testConstructorAnnotationClass() throws Throwable
   {
      testClass(ClassInfoConstructorAnnotationClass.class);
   }
   
   public void testConstructorParameterAnnotationClass() throws Throwable
   {
      testClass(ClassInfoConstructorParameterAnnotationClass.class);
   }
   
   private void testClass(Class<?> clazz) throws Throwable
   {
      ClassInfoImpl expected = new ClassInfoImpl(clazz.getName(), ModifierInfo.PUBLIC);
      TypeInfo info = testBasics(clazz, expected);
      
      assertFalse(info.isArray());
      assertFalse(info.isEnum());
      assertFalse(info.isPrimitive());
      
      ClassInfo classInfo = (ClassInfo) info;
      assertClassInfo(classInfo, clazz);
   }
}
