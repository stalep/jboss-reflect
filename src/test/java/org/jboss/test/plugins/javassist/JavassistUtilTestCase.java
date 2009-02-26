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


import java.util.HashMap;
import java.util.Map;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.javassist.JavassistUtil;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.test.ContainerTest;

/**
 * A JavassistUtilTestCase.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class JavassistUtilTestCase extends ContainerTest
{
   
   final static Map<String, Class<?>> primitives;
   static
   {
      primitives = new HashMap<String, Class<?>>();
      primitives.put(CtClass.booleanType.getName(), Boolean.TYPE);
      primitives.put(CtClass.byteType.getName(), Byte.TYPE);
      primitives.put(CtClass.charType.getName(), Character.TYPE);
      primitives.put(CtClass.doubleType.getName(), Double.TYPE);
      primitives.put(CtClass.floatType.getName(), Float.TYPE);
      primitives.put(CtClass.intType.getName(), Integer.TYPE);
      primitives.put(CtClass.longType.getName(), Long.TYPE);
      primitives.put(CtClass.shortType.getName(), Short.TYPE);
      primitives.put(CtClass.voidType.getName(), Void.TYPE);
   }
   /**
    * Create a new JavassistUtilTestCase.
    * 
    * @param name
    */
   public JavassistUtilTestCase(String name)
   {
      super(name);
   }
   
   public void testCtClassToClass()
   {
      Class<?> clazz = loadClass("int");
      assertEquals("int", clazz.getName());
      clazz = loadClass("byte[][][]");
      assertEquals("[[[B", clazz.getName());
      clazz = loadClass("java.lang.String[][]");
      assertEquals("[[Ljava.lang.String;", clazz.getName());
      clazz = loadClass("java.lang.String[][][]");
      assertEquals("[[[Ljava.lang.String;", clazz.getName());
   }
   
   public void testGeneratedCtClassToClass()
   {
      CtClass clazz = ClassPool.getDefault().makeClass("TestClass");
      try
      {
         CtMethod foo = CtNewMethod.make("public void foo() { }", clazz);
         clazz.addMethod(foo);
         Class<?> theClass = JavassistUtil.ctClassToClass(clazz);
         assertEquals("TestClass", theClass.getName());
      }
      catch (CannotCompileException e)
      {
         e.printStackTrace();
      }
   }
   
   public void testChangedCtClassToClass()
   {
      try
      {
         CtClass clazz = ClassPool.getDefault().get("org.jboss.test.plugins.javassist.Pojo2");
         CtMethod foo = CtNewMethod.make("public void test1() { }", clazz);
         clazz.addMethod(foo);
         
         
         Class theClass = JavassistUtil.ctClassToClass(clazz);
         assertEquals(2, theClass.getDeclaredMethods().length);
      }
      catch (CannotCompileException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
      catch (NotFoundException e)
      {
         e.printStackTrace();
      }
      
   }
   
   public void testModifier()
   {
      assertEquals(ModifierInfo.PUBLIC, Modifier.PUBLIC);
      assertEquals(ModifierInfo.PRIVATE, Modifier.PRIVATE);
      assertEquals(ModifierInfo.PROTECTED, Modifier.PROTECTED);
      assertEquals(ModifierInfo.STATIC, Modifier.STATIC);
   }
   
   public void testSignature()
   {
      try
      {
         CtClass pojo = ClassPool.getDefault().get("org.jboss.test.plugins.javassist.Pojo");
         
         CtMethod[] methods = pojo.getDeclaredMethods();
         for(CtMethod m : methods)
         {
            System.out.println("Method "+m.getName()+", description: "+m.getSignature()+", get longname: "+m.getLongName());
         }
         CtField[] fields = pojo.getDeclaredFields();
         for(CtField f : fields)
            System.out.println("Field "+f.getName()+", description: "+f.getSignature());
         
         CtConstructor[] constructors = pojo.getConstructors();
         for(CtConstructor c : constructors)
            System.out.println("Constructor "+c.getName()+", description: "+c.getSignature()+", longname: "+c.getLongName());
      }
      catch (NotFoundException e)
      {
         // TODO Auto-generated catch block
         e.printStackTrace();
      }
   }
   
   private Class<?> loadClass(String name)
   {
      if (primitives.containsKey(name))
      {
         return primitives.get(name);
      }
      
      try
      {
         return JavassistUtil.ctClassToClass(ClassPool.getDefault().get(name));
      }
      catch (NotFoundException e)
      {
         return null;
      }
   }

}
