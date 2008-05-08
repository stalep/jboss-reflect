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

import java.lang.reflect.Field;

import junit.framework.Test;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.plugins.introspection.ReflectFieldInfoImpl;
import org.jboss.test.classinfo.support.FieldsClass;

/**
 * Access restriction test.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class FieldAccessRestrictionTestCase extends AccessRestrictionTest<FieldsClass, FieldInfo, Field>
{
   /**
    * Create a new FieldAccessRestrictionTestCase.
    *
    * @param name the test name
    */
   public FieldAccessRestrictionTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(FieldAccessRestrictionTestCase.class);
   }

   protected FieldsClass getInstance()
   {
      return new FieldsClass();
   }

   protected Class<FieldsClass> getInstanceClass()
   {
      return FieldsClass.class;
   }

   protected FieldInfo getInfo()
   {
      return new ReflectFieldInfoImpl();
   }

   protected FieldInfo getSetAnnotatedInfo(ClassInfo info, String member)
   {
      return info.getDeclaredField(member);  
   }

   protected FieldInfo getGetAnnotatedInfo(ClassInfo info, String member)
   {
      return info.getDeclaredField(member);
   }

   protected Field getAccessibleObject(String member) throws NoSuchFieldException
   {
      return getInstanceClass().getDeclaredField(member);
   }

   protected void set(FieldInfo annotatedInfo, FieldsClass instance, String string) throws Throwable
   {
      annotatedInfo.set(instance, string);
   }

   protected Object get(FieldInfo annotatedInfo, FieldsClass instance) throws Throwable
   {
      return annotatedInfo.get(instance);
   }

   protected void set(Field accessibleObject, FieldsClass instance, String string) throws IllegalAccessException
   {
      accessibleObject.set(instance, string);
   }

   protected void set(FieldInfo info, Field accessibleObject)
   {
      ((ReflectFieldInfoImpl)info).setField(accessibleObject);
   }

   protected String getPrivateString(FieldsClass instance)
   {
      return instance.getPrivString();
   }

   protected Field getAccessibleObject(FieldInfo info)
   {
      return ((ReflectFieldInfoImpl)info).getField();
   }
}