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

import org.jboss.reflect.plugins.javassist.JavassistTypeInfoFactoryImpl;
import org.jboss.reflect.spi.InsertBeforeJavassistBody;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableMethodInfo;
import org.jboss.test.ContainerTest;

/**
 * A JavassistMutableMethodInfoTestCase.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class JavassistMutableMethodInfoTestCase extends ContainerTest
{

   /**
    * Create a new JavassistMutableMethodInfoTestCase.
    * 
    * @param name
    */
   public JavassistMutableMethodInfoTestCase(String name)
   {
      super(name);
   }
   
   public void testMethods()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.Pojo3", null);
      
      MutableMethodInfo[] methods = mci.getDeclaredMethods();
      try
      {
         MutableMethodInfo bar = mci.getDeclaredMethod("bar", new String[] {"java.lang.String"});

         assertEquals(3, methods.length);
         assertEquals("bar", bar.getName());
         assertEquals("java.lang.String", bar.getParameterTypes()[0].getName());
      
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }
   }
   
   public void testRemoveMethod()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.Pojo3", null);
      
      try
      {
         MutableMethodInfo bar = mci.getDeclaredMethod("bar", new String[] {"java.lang.String"});
         mci.removeMethod(bar);
         assertEquals(2, mci.getDeclaredMethods().length);  
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }      
   }
   
   @SuppressWarnings("deprecation")
   public void testNewMethods()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.Pojo3", null);
      MutableMethodInfo newMethod1 = mci.createMutableMethod(new InsertBeforeJavassistBody("public void test1() { }"));
      mci.addMethod(newMethod1);
      MutableMethodInfo newMethod2 = mci.createMutableMethod(new InsertBeforeJavassistBody("public String test2() { return \"foo\"; }"));
      try
      {
      newMethod2.setReturnType("java.lang.String");
      assertFalse("MutableMethoInfo should throw an exception!", true);
      }
      catch(RuntimeException re)
      {
      }
      newMethod2.setName("test3");
      mci.addMethod(newMethod2);
       
      assertEquals(4, mci.getDeclaredMethods().length);
      Class<?> theClass = mci.getType();
      assertEquals(4, theClass.getDeclaredMethods().length);
      try
      {
         assertNotNull(theClass.getDeclaredMethod("test3", new Class[] { }));
      }
      catch (SecurityException e)
      {
      }
      catch (NoSuchMethodException e)
      {
      }
   }
   

}
