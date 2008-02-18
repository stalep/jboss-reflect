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

import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.test.classinfo.support.ClassInfoAnnotationInterface;
import org.jboss.test.classinfo.support.ClassInfoEmptyInterface;
import org.jboss.test.classinfo.support.ClassInfoFieldAnnotationInterface;
import org.jboss.test.classinfo.support.ClassInfoFieldsInterface;
import org.jboss.test.classinfo.support.ClassInfoInterfacesInterface;
import org.jboss.test.classinfo.support.ClassInfoMethodAnnotationInterface;
import org.jboss.test.classinfo.support.ClassInfoMethodParameterAnnotationInterface;
import org.jboss.test.classinfo.support.ClassInfoMethodsInterface;

/**
 * ClassInfoInterfaceTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoInterfaceTest extends AbstractClassInfoTest
{
   public ClassInfoInterfaceTest(String name)
   {
      super(name);
   }
   
   public void testEmptyInterface() throws Throwable
   {
      testInterface(ClassInfoEmptyInterface.class);
   }
   
   public void testInterfacesInterface() throws Throwable
   {
      testInterface(ClassInfoInterfacesInterface.class);
   }
   
   public void testMethodsInterface() throws Throwable
   {
      testInterface(ClassInfoMethodsInterface.class);
   }
   
   public void testFieldsInterface() throws Throwable
   {
      testInterface(ClassInfoFieldsInterface.class);
   }
   
   public void testAnnotationInterface() throws Throwable
   {
      testInterface(ClassInfoAnnotationInterface.class);
   }
   
   public void testFieldAnnotationInterface() throws Throwable
   {
      testInterface(ClassInfoFieldAnnotationInterface.class);
   }
   
   public void testMethodAnnotationInterface() throws Throwable
   {
      testInterface(ClassInfoMethodAnnotationInterface.class);
   }
   
   public void testMethodParameterAnnotationInterface() throws Throwable
   {
      testInterface(ClassInfoMethodParameterAnnotationInterface.class);
   }
   
   private void testInterface(Class<?> clazz) throws Throwable
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
