/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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

import java.lang.reflect.Method;
import java.lang.reflect.Type;

import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * Tests of the TypeInfo for byte[] types
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ByteArrayUnitTestCase extends AbstractClassInfoTest
{
   
   public ByteArrayUnitTestCase(String name)
   {
      super(name);
   }

   public void testByteArrayInfo()
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      byte[] x = {};
      TypeInfo xinfo = factory.getTypeInfo(x.getClass());
      getLog().debug(xinfo);
      assertTrue(xinfo instanceof ArrayInfo);
      ArrayInfo ainfo = (ArrayInfo) xinfo;
      TypeInfo compType = ainfo.getComponentType();
      assertEquals(PrimitiveInfo.BYTE, compType);
   }
   public void testClassLoaderDefineClass()
      throws Exception
   {
      byte[] x = {};
      Class<?>[] parameterTypes = {x.getClass(), int.class, int.class};
      Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", parameterTypes);
      assertNotNull(defineClass);
      Class<?>[] types = defineClass.getParameterTypes();
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo arg0Info = factory.getTypeInfo(types[0]);
      assertTrue(arg0Info instanceof ArrayInfo);
      getLog().debug(arg0Info);
      ArrayInfo ainfo = (ArrayInfo) arg0Info;
      TypeInfo compType = ainfo.getComponentType();
      assertEquals(PrimitiveInfo.BYTE, compType);
   }
   public void testClassLoaderDefineClassGenericParams()
      throws Exception
   {
      byte[] x = {};
      Class<?>[] parameterTypes = {x.getClass(), int.class, int.class};
      Method defineClass = ClassLoader.class.getDeclaredMethod("defineClass", parameterTypes);
      assertNotNull(defineClass);
      Type[] types = defineClass.getGenericParameterTypes();
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo arg0Info = factory.getTypeInfo(types[0]);
      assertTrue(arg0Info instanceof ArrayInfo);
      getLog().debug(arg0Info);
      ArrayInfo ainfo = (ArrayInfo) arg0Info;
      TypeInfo compType = ainfo.getComponentType();
      assertEquals(PrimitiveInfo.BYTE, compType);
   }

   protected ClassInfo getClassInfo(Class<?> clazz)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo info = factory.getTypeInfo(clazz);
      assertNotNull(info);
      assertTrue(info instanceof ClassInfo);
      ClassInfo cinfo = (ClassInfo) info;
      getLog().debug(cinfo);
      return cinfo;
   }

   protected TypeInfoFactory getTypeInfoFactory()
   {
      return new IntrospectionTypeInfoFactory();
   }

}
