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

import java.lang.reflect.Method;

import junit.framework.Test;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.plugins.introspection.ReflectMethodInfoImpl;
import org.jboss.test.classinfo.support.MethodsClass;

/**
 * Access restriction test.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class MethodAccessRestrictionTestCase extends AccessRestrictionTest<MethodsClass, MethodInfo, Method>
{
   /**
    * Create a new MethodAccessRestrictionTestCase.
    *
    * @param name the test name
    */
   public MethodAccessRestrictionTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(MethodAccessRestrictionTestCase.class);
   }

   protected MethodsClass getInstance()
   {
      return new MethodsClass();
   }

   protected Class<MethodsClass> getInstanceClass()
   {
      return MethodsClass.class;
   }

   protected MethodInfo getInfo()
   {
      return new ReflectMethodInfoImpl();
   }

   protected String getGetter(String member)
   {
      return "get" + member.substring(0, 1).toUpperCase() + member.substring(1);
   }

   protected String getSetter(String member)
   {
      return "set" + member.substring(0, 1).toUpperCase() + member.substring(1);
   }

   protected MethodInfo getSetAnnotatedInfo(ClassInfo info, String member)
   {
      return info.getDeclaredMethod(getSetter(member), new TypeInfo[]{configuration.getClassInfo(String.class)});  
   }

   protected MethodInfo getGetAnnotatedInfo(ClassInfo info, String member)
   {
      return info.getDeclaredMethod(getGetter(member), new TypeInfo[]{});
   }

   protected Method getAccessibleObject(String member) throws NoSuchMethodException
   {
      return getInstanceClass().getDeclaredMethod(getGetter(member));
   }

   protected void set(MethodInfo annotatedInfo, MethodsClass instance, String string) throws Throwable
   {
      annotatedInfo.invoke(instance, new Object[]{string});
   }

   protected Object get(MethodInfo annotatedInfo, MethodsClass instance) throws Throwable
   {
      return annotatedInfo.invoke(instance, new Object[]{});
   }

   protected void set(Method accessibleObject, MethodsClass instance, String string) throws Exception
   {
      accessibleObject.invoke(instance, string);
   }

   protected void set(MethodInfo info, Method accessibleObject)
   {
      ((ReflectMethodInfoImpl)info).setMethod(accessibleObject);
   }

   protected String getPrivateString(MethodsClass instance)
   {
      return instance.getPrivStringNotGetter();
   }

   protected Method getAccessibleObject(MethodInfo info)
   {
      return ((ReflectMethodInfoImpl)info).getMethod();
   }
}