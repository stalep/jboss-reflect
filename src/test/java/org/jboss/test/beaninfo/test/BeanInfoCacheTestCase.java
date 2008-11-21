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
package org.jboss.test.beaninfo.test;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

import junit.framework.Test;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.test.beaninfo.support.BeanInfoAnnotation;
import org.jboss.test.beaninfo.support.BeanInfoEmpty;
import org.jboss.test.beaninfo.support.BeanInfoGenericInterface;

/**
 * BeanInfoCache Test Case.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class BeanInfoCacheTestCase extends AbstractBeanInfoTest
{
   public static Test suite()
   {
      return suite(BeanInfoCacheTestCase.class);
   }

   public BeanInfoCacheTestCase(String name)
   {
      super(name);
   }

   public void testBeanInfoCaching() throws Exception
   {
      assertBeanInfoCaching("Integer", List.class);
      assertBeanInfoCaching("String", List.class);
      assertBeanInfoCaching("Integer", Set.class);
      assertBeanInfoCaching("String", Set.class);
      assertBeanInfoCaching("Integer", BeanInfoGenericInterface.class);
      assertBeanInfoCaching("String", BeanInfoGenericInterface.class);
      assertBeanInfoCaching("", String.class);
      assertBeanInfoCaching("", BeanInfoAnnotation.class);
      assertBeanInfoCaching("", BeanInfoEmpty.class);
   }

   private void assertBeanInfoCaching(String string, Class<?> clazz) throws Exception
   {
      Type type = getType(string, clazz);
      assertBeanInfoCaching(type);
   }

   private void assertBeanInfoCaching(Type type) throws Exception
   {
      assertClassInfo(type);
      TypeInfo typeInfo = getConfiguration().getTypeInfo(type);
      ClassInfo classInfo = assertInstanceOf(typeInfo, ClassInfo.class);
      assertClassInfo(classInfo);
      if (type instanceof Class)
      {
         Class<?> clazz = Class.class.cast(type);
         assertClassInfo(classInfo, clazz.getName(), clazz.getClassLoader());
      }
   }

   private void assertClassInfo(Type type)
   {
      TypeInfo typeInfo = getConfiguration().getTypeInfo(type);
      ClassInfo classInfo = assertInstanceOf(typeInfo, ClassInfo.class);
      assertClassInfo(classInfo);
   }

   private void assertClassInfo(ClassInfo typeInfo)
   {
      BeanInfo beanInfo = getConfiguration().getBeanInfo(typeInfo);
      ClassInfo typeInfo2 = beanInfo.getClassInfo();
      assertSame(typeInfo, typeInfo2);
   }

   private void assertClassInfo(ClassInfo typeInfo, String className, ClassLoader cl) throws Exception
   {
      BeanInfo beanInfo = getConfiguration().getBeanInfo(className, cl);
      ClassInfo typeInfo2 = beanInfo.getClassInfo();
      assertSame(typeInfo, typeInfo2);
   }

   @SuppressWarnings("unchecked")
   protected Type getType(String type, Class<?> clazz) throws Exception
   {
      Method method = getClass().getDeclaredMethod("get" + type + clazz.getSimpleName());
      return method.getGenericReturnType();
   }

   public List<Integer> getIntegerList()
   {
      return null;
   }

   public Set<Integer> getIntegerSet()
   {
      return null;
   }

   public List<String> getStringList()
   {
      return null;
   }

   public Set<String> getStringSet()
   {
      return null;
   }

   public BeanInfoGenericInterface<Integer> getIntegerBeanInfoGenericInterface()
   {
      return null;
   }

   public BeanInfoGenericInterface<String> getStringBeanInfoGenericInterface()
   {
      return null;
   }

   public String getString()
   {
      return null;
   }

   public BeanInfoAnnotation getBeanInfoAnnotation()
   {
      return null;
   }

   public BeanInfoEmpty getBeanInfoEmpty()
   {
      return null;
   }
}