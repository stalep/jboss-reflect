/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.plugins.javassist;

import java.lang.reflect.Method;

import org.jboss.reflect.plugins.javassist.JavassistTypeInfoFactoryImpl;
import org.jboss.reflect.spi.InsertBeforeJavassistBody;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableMethodInfo;
import org.jboss.test.ContainerTest;

/**
 * A JavassistMutableTypeInfoTestCase.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class JavassistMutableTypeInfoTestCase extends ContainerTest
{

   /**
    * Create a new JavassistMutableTypeInfoTestCase.
    * 
    * @param name
    */
   public JavassistMutableTypeInfoTestCase(String name)
   {
      super(name);
   }
   
   @SuppressWarnings("deprecation")
   public void testNewClass()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().createNewMutableClass("org.jboss.test.plugins.javassist.PojoNew");
      assertEquals(mci.getName(), "org.jboss.test.plugins.javassist.PojoNew");
      assertEquals(mci.getModifiers().isPublic(), true);
      MutableMethodInfo mmi1 = mci.createMutableMethod(new InsertBeforeJavassistBody("public String getFoo() { return \"foo\"; }"));
      mci.addMethod(mmi1);
      assertEquals(mci.getDeclaredMethods().length, 1);
      MethodInfo mi = mci.getDeclaredMethods()[0];
      assertEquals(mi.getReturnType().getName(), "java.lang.String");
      
      Class<?> clazz = mci.getType();
      try
      {
         Object pojo = clazz.newInstance();
         Method m1 = clazz.getMethod("getFoo", new Class[0]);
         assertEquals("foo", m1.invoke(pojo, new Object[0]));
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }      
   }

}
