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
package org.jboss.test.classinfo.test;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.plugins.ParameterInfoImpl;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.ContainerTest;
import org.jboss.test.classinfo.support.AnotherBean;
import org.jboss.test.classinfo.support.AnotherInterface;
import org.jboss.test.classinfo.support.SimpleBean;
import org.jboss.test.classinfo.support.SimpleInterface;

/**
 * ClassInfo Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class ClassInfoTest extends ContainerTest
{
   public ClassInfoTest(String name)
   {
      super(name);
   }
   
   public void testSimpleBeanClassInfo() throws Throwable
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);

      assertEquals(SimpleBean.class.getName(), cinfo.getName());
      ClassInfo supercinfo = cinfo.getSuperclass();
      assertNotNull(supercinfo);
      assertEquals(Object.class.getName(), supercinfo.getName());
      
      HashSet<String> expected = new HashSet<String>();
      expected.add(Serializable.class.getName());
      expected.add(SimpleInterface.class.getName());
      checkTypeSet(expected, cinfo.getInterfaces());
   }

   public void testSimpleInterfaceClassInfo() throws Throwable
   {
      InterfaceInfo simpleInterfaceInfo = getSimpleInterfaceInfo();
      
      assertEquals(SimpleInterface.class.getName(), simpleInterfaceInfo.getName());
      InterfaceInfo[] superInterfaces = simpleInterfaceInfo.getInterfaces();
      assertTrue("No super interfaces ", superInterfaces == null);
   }

   public void testSimpleInterfaceFields() throws Throwable
   {
      InterfaceInfo simpleInterfaceInfo = getSimpleInterfaceInfo();
      
      checkFields(getSimpleInterfaceFields(), simpleInterfaceInfo.getDeclaredFields());
   }

   public void testSimpleInterfaceMethods() throws Throwable
   {
      InterfaceInfo simpleInterfaceInfo = getSimpleInterfaceInfo();
      
      checkMethods(getSimpleInterfaceMethods(), simpleInterfaceInfo.getDeclaredMethods());
   }

   public void testAnotherInterfaceMethod() throws Throwable
   {
      InterfaceInfo anotherInterfaceInfo = getAnotherInterfaceInfo();
      
      compareMethod(getAnotherInterfaceSomeMethod(), anotherInterfaceInfo.getDeclaredMethod("someMethod", null));
   }

   public void testSimpleBeanFields() throws Throwable
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);
      
      checkFields(getSimpleBeanFields(), cinfo.getDeclaredFields());
   }

   public void testSimpleBeanMethods() throws Throwable
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);
      
      checkMethods(getSimpleBeanMethods(), cinfo.getDeclaredMethods());
   }

   public void testAnotherBeanMethod() throws Throwable
   {
      ClassInfo anotherBeanInfo = getClassInfo(AnotherBean.class);
      
      compareMethod(getAnotherBeanSomeMethod(), anotherBeanInfo.getDeclaredMethod("someMethod", null));
   }

   public void testSimpleBeanConstructors() throws Throwable
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);
      
      checkConstructors(getSimpleBeanConstructors(), cinfo.getDeclaredConstructors());
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
   
   protected InterfaceInfo getSimpleInterfaceInfo()
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);
      
      InterfaceInfo simpleInterfaceInfo = null;
      InterfaceInfo[] interfaces = cinfo.getInterfaces();
      for (int i = 0; i < interfaces.length; ++i)
      {
         if (SimpleInterface.class.getName().equals(interfaces[i].getName()))
         {
            simpleInterfaceInfo = interfaces[i];
            break;
         }
      }
      assertNotNull(simpleInterfaceInfo);
      getLog().debug(simpleInterfaceInfo);

      return simpleInterfaceInfo;
   }
   
   protected InterfaceInfo getAnotherInterfaceInfo()
   {
      ClassInfo cinfo = getClassInfo(AnotherBean.class);
      
      InterfaceInfo anotherInterfaceInfo = null;
      InterfaceInfo[] interfaces = cinfo.getInterfaces();
      for (int i = 0; i < interfaces.length; ++i)
      {
         if (AnotherInterface.class.getName().equals(interfaces[i].getName()))
         {
            anotherInterfaceInfo = interfaces[i];
            break;
         }
      }
      assertNotNull(anotherInterfaceInfo);
      getLog().debug(anotherInterfaceInfo);

      return anotherInterfaceInfo;
   }

   protected void checkTypeSet(HashSet<String> expected, TypeInfo[] typeInfos) throws Throwable
   {
      HashSet<String> actual = new HashSet<String>();
      for (int i = 0; i < typeInfos.length; ++i)
         actual.add(typeInfos[i].getName());
      
      HashSet<String> expectClone = new HashSet<String>(expected);
      HashSet<String> actualClone = new HashSet<String>(actual);
      
      getLog().debug("checkTypeSet expect=" + expected);
      getLog().debug("checkTypeSet actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }

   protected void checkFields(Set<FieldInfo> expected, FieldInfo[] actually) throws Throwable
   {
      HashSet<FieldInfo> expectClone = new HashSet<FieldInfo>(expected);
      ArrayList<FieldInfo> actualClone = new ArrayList<FieldInfo>(Arrays.asList(actually));
      
      getLog().debug("checkFields expect=" + expectClone);
      getLog().debug("checkFields actual=" + actualClone);
      
      expectClone.removeAll(actualClone);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());

      expectClone = new HashSet<FieldInfo>(expected);
      actualClone = new ArrayList<FieldInfo>(Arrays.asList(actually));
      for (Iterator<FieldInfo> i = expectClone.iterator(); i.hasNext();)
      {
         FieldInfo expect = i.next();
         int j = actualClone.indexOf(expect);
         FieldInfo actual = actualClone.get(j);
         compareField(expect, actual);
      }
   }

   protected void compareField(FieldInfo expect, FieldInfo actual) throws Throwable
   {
      getLog().debug("CompareField expect=" + expect + " actual=" + actual);

      assertEquals("Name", expect.getName(), actual.getName());
      assertEquals("Type", expect.getType(), actual.getType());
      assertEquals("Modifiers", expect.getModifiers(), actual.getModifiers());
      assertEquals("Declaring Class", expect.getDeclaringClass(), actual.getDeclaringClass());
      assertEquals("Annotations", expect.getAnnotations(), actual.getAnnotations());
   }
   
   protected void checkMethods(Set<MethodInfo> expected, MethodInfo[] actually) throws Throwable
   {
      HashSet<MethodInfo> expectClone = new HashSet<MethodInfo>(expected);
      ArrayList<MethodInfo> actualClone = new ArrayList<MethodInfo>(Arrays.asList(actually));
      
      getLog().debug("checkMethods expect=" + expectClone);
      getLog().debug("checkMethods actual=" + actualClone);
      
      expectClone.removeAll(actualClone);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());

      expectClone = new HashSet<MethodInfo>(expected);
      actualClone = new ArrayList<MethodInfo>(Arrays.asList(actually));
      for (Iterator<MethodInfo> i = expectClone.iterator(); i.hasNext();)
      {
         MethodInfo expect = i.next();
         int j = actualClone.indexOf(expect);
         MethodInfo actual = actualClone.get(j);
         compareMethod(expect, actual);
      }
   }

   protected void compareMethod(MethodInfo expect, MethodInfo actual) throws Throwable
   {
      getLog().debug("MethodField expect=" + expect + " actual=" + actual);

      assertNotNull("Null method info", actual);
      assertEquals("Name", expect.getName(), actual.getName());
      assertEquals("ReturnType", expect.getReturnType(), actual.getReturnType());
      assertEquals("ParameterTypes", expect.getParameterTypes(), actual.getParameterTypes());
      assertEquals("Parameters", expect.getParameters(), actual.getParameters());
      assertEquals("Exceptions", expect.getExceptionTypes(), actual.getExceptionTypes());
      assertEquals("Modifiers", expect.getModifiers(), actual.getModifiers());
      assertEquals("Declaring Class", expect.getDeclaringClass(), actual.getDeclaringClass());
      assertEquals("Annotations", expect.getAnnotations(), actual.getAnnotations());
   }

   protected void checkConstructors(Set<ConstructorInfo> expected, ConstructorInfo[] actually) throws Throwable
   {
      HashSet<ConstructorInfo> expectClone = new HashSet<ConstructorInfo>(expected);
      ArrayList<ConstructorInfo> actualClone = new ArrayList<ConstructorInfo>(Arrays.asList(actually));
      
      getLog().debug("checkConstructors expect=" + expectClone);
      getLog().debug("checkConstructors actual=" + actualClone);
      
      expectClone.removeAll(actualClone);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());

      expectClone = new HashSet<ConstructorInfo>(expected);
      actualClone = new ArrayList<ConstructorInfo>(Arrays.asList(actually));
      for (Iterator<ConstructorInfo> i = expectClone.iterator(); i.hasNext();)
      {
         ConstructorInfo expect = i.next();
         int j = actualClone.indexOf(expect);
         ConstructorInfo actual = actualClone.get(j);
         compareConstructor(expect, actual);
      }
   }

   protected void compareConstructor(ConstructorInfo expect, ConstructorInfo actual) throws Throwable
   {
      getLog().debug("ConstructorField expect=" + expect + " actual=" + actual);

      assertEquals("ParameterTypes", expect.getParameterTypes(), actual.getParameterTypes());
      assertEquals("Parameters", expect.getParameters(), actual.getParameters());
      assertEquals("Exceptions", expect.getExceptionTypes(), actual.getExceptionTypes());
      assertEquals("Modifiers", expect.getModifiers(), actual.getModifiers());
      assertEquals("Declaring Class", expect.getDeclaringClass(), actual.getDeclaringClass());
      assertEquals("Annotations", expect.getAnnotations(), actual.getAnnotations());
   }
   
   protected Set<FieldInfo> getSimpleInterfaceFields()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      ClassInfo simpleInterface = (ClassInfo) factory.getTypeInfo(SimpleInterface.class);
      
      TypeInfo objectType = factory.getTypeInfo(Object.class);
      
      HashSet<FieldInfo> result = new HashSet<FieldInfo>();
      result.add(new FieldInfoImpl(null, "A_CONSTANT", objectType, ModifierInfo.PUBLIC_CONSTANT, simpleInterface));
      return result;
   }
   
   protected Set<MethodInfo> getSimpleInterfaceMethods()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      ClassInfo simpleInterface = (ClassInfo) factory.getTypeInfo(SimpleInterface.class);
      
      TypeInfo boolType = PrimitiveInfo.BOOLEAN;
      ParameterInfo boolParam = new ParameterInfoImpl(null, "arg0", boolType);
      ParameterInfo[] boolParameters = new ParameterInfo[] { boolParam };

      TypeInfo booleanType = factory.getTypeInfo(Boolean.class);

      TypeInfo intType = PrimitiveInfo.INT;
      ParameterInfo intParam = new ParameterInfoImpl(null, "arg0", intType);
      ParameterInfo[] intParameters = new ParameterInfo[] { intParam };

      TypeInfo objectType = factory.getTypeInfo(Object.class);
      ParameterInfo objectParam = new ParameterInfoImpl(null, "arg0", objectType);
      ParameterInfo[] objectParameters = new ParameterInfo[] { objectParam };

      TypeInfo stringType = factory.getTypeInfo(String.class);
      ParameterInfo stringParam = new ParameterInfoImpl(null, "arg0", stringType);
      ParameterInfo[] stringParameters = new ParameterInfo[] { stringParam };

      TypeInfo urlType = factory.getTypeInfo(URL.class);
      ParameterInfo urlParam = new ParameterInfoImpl(null, "arg1", urlType);
      ParameterInfo[] twoParameters = new ParameterInfo[] { stringParam, urlParam };

      TypeInfo voidType = PrimitiveInfo.VOID;
      
      HashSet<MethodInfo> result = new HashSet<MethodInfo>();
      result.add(new MethodInfoImpl(null, "getWithoutSetter", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "getWithNoSetterOnInterface", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "setWithNoGetterOnInterface", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "setWithoutGetter", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "getWithSetter", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "setWithSetter", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "isPrimitiveIS", boolType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "isBooleanIS", booleanType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "getA", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "setA", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "isB", boolType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "setB", voidType, boolParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "getDoesNotMatchSetter", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "setDoesNotMatchGetter", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithNoReturnTypeNoParameters", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithNoReturnTypeOneParameter", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithNoReturnTypeTwoParameters", voidType, twoParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithReturnTypeNoParameters", objectType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithReturnTypeOneParameter", stringType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithReturnTypeTwoParameters", urlType, twoParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "get", objectType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "is", boolType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "set", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithPrimitiveReturnType", intType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "methodWithPrimitiveParameter", voidType, intParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "overloadedMethod", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      result.add(new MethodInfoImpl(null, "overloadedMethod", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, simpleInterface));
      return result;
   }
   
   protected MethodInfo getAnotherInterfaceSomeMethod()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      TypeInfo voidType = PrimitiveInfo.VOID;

      ClassInfo anotherInterface = (ClassInfo) factory.getTypeInfo(AnotherInterface.class);

      return new MethodInfoImpl(null, "someMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_ABSTRACT, anotherInterface);
   }
   
   protected Set<FieldInfo> getSimpleBeanFields()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      TypeInfo longType = PrimitiveInfo.LONG;
      
      ClassInfo simpleBean = (ClassInfo) factory.getTypeInfo(SimpleBean.class);
      
      TypeInfo objectType = factory.getTypeInfo(Object.class);
      
      HashSet<FieldInfo> result = new HashSet<FieldInfo>();
      result.add(new FieldInfoImpl(null, "PUBLIC_CONSTANT", objectType, ModifierInfo.PUBLIC_CONSTANT, simpleBean));
      result.add(new FieldInfoImpl(null, "PACKAGE_PRIVATE_CONSTANT", objectType, ModifierInfo.PACKAGE_CONSTANT, simpleBean));
      result.add(new FieldInfoImpl(null, "PROTECTED_CONSTANT", objectType, ModifierInfo.PROTECTED_CONSTANT, simpleBean));
      result.add(new FieldInfoImpl(null, "PRIVATE_CONSTANT", objectType, ModifierInfo.PRIVATE_CONSTANT, simpleBean));
      result.add(new FieldInfoImpl(null, "serialVersionUID", longType, ModifierInfo.PRIVATE_CONSTANT, simpleBean));
      result.add(new FieldInfoImpl(null, "publicAttribute", objectType, ModifierInfo.PUBLIC, simpleBean));
      result.add(new FieldInfoImpl(null, "packagePrivateAttribute", objectType, ModifierInfo.PACKAGE, simpleBean));
      result.add(new FieldInfoImpl(null, "protectedAttribute", objectType, ModifierInfo.PROTECTED, simpleBean));
      result.add(new FieldInfoImpl(null, "privateAttribute", objectType, ModifierInfo.PRIVATE, simpleBean));
      return result;
   }
   
   protected Set<MethodInfo> getSimpleBeanMethods()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      ClassInfo simpleBean = (ClassInfo) factory.getTypeInfo(SimpleBean.class);
      
      TypeInfo boolType = PrimitiveInfo.BOOLEAN;
      ParameterInfo boolParam = new ParameterInfoImpl(null, "arg0", boolType);
      ParameterInfo[] boolParameters = new ParameterInfo[] { boolParam };

      TypeInfo booleanType = factory.getTypeInfo(Boolean.class);

      TypeInfo intType = PrimitiveInfo.INT;
      ParameterInfo intParam = new ParameterInfoImpl(null, "arg0", intType);
      ParameterInfo[] intParameters = new ParameterInfo[] { intParam };

      TypeInfo objectType = factory.getTypeInfo(Object.class);
      ParameterInfo objectParam = new ParameterInfoImpl(null, "arg0", objectType);
      ParameterInfo[] objectParameters = new ParameterInfo[] { objectParam };

      TypeInfo stringType = factory.getTypeInfo(String.class);
      ParameterInfo stringParam = new ParameterInfoImpl(null, "arg0", stringType);
      ParameterInfo[] stringParameters = new ParameterInfo[] { stringParam };

      TypeInfo urlType = factory.getTypeInfo(URL.class);
      ParameterInfo urlParam = new ParameterInfoImpl(null, "arg1", urlType);
      ParameterInfo[] twoParameters = new ParameterInfo[] { stringParam, urlParam };

      TypeInfo voidType = PrimitiveInfo.VOID;
      
      HashSet<MethodInfo> result = new HashSet<MethodInfo>();
      result.add(new MethodInfoImpl(null, "getWithoutSetter", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "getWithNoSetterOnInterface", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "setWithNoGetterOnInterface", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "setWithoutGetter", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "getWithSetter", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "setWithSetter", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "isPrimitiveIS", boolType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "isBooleanIS", booleanType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "getA", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "setA", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "isB", boolType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "setB", voidType, boolParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "getDoesNotMatchSetter", stringType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "setDoesNotMatchGetter", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithNoReturnTypeNoParameters", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithNoReturnTypeOneParameter", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithNoReturnTypeTwoParameters", voidType, twoParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithReturnTypeNoParameters", objectType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithReturnTypeOneParameter", stringType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithReturnTypeTwoParameters", urlType, twoParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "get", objectType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "is", boolType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "set", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithPrimitiveReturnType", intType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "methodWithPrimitiveParameter", voidType, intParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "overloadedMethod", voidType, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "overloadedMethod", voidType, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new MethodInfoImpl(null, "publicStaticMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC_STATIC, simpleBean));
      result.add(new MethodInfoImpl(null, "packagePrivateStaticMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PACKAGE_STATIC, simpleBean));
      result.add(new MethodInfoImpl(null, "protectedStaticMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PROTECTED_STATIC, simpleBean));
      result.add(new MethodInfoImpl(null, "privateStaticMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PRIVATE_STATIC, simpleBean));
      result.add(new MethodInfoImpl(null, "packagePrivateMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PACKAGE, simpleBean));
      result.add(new MethodInfoImpl(null, "protectedMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PROTECTED, simpleBean));
      result.add(new MethodInfoImpl(null, "privateMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PRIVATE, simpleBean));
      return result;
   }
   
   protected MethodInfo getAnotherBeanSomeMethod()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      TypeInfo voidType = PrimitiveInfo.VOID;

      ClassInfo anotherBean = (ClassInfo) factory.getTypeInfo(AnotherBean.class);

      return new MethodInfoImpl(null, "someMethod", voidType, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, anotherBean);
   }
   
   protected Set<ConstructorInfo> getSimpleBeanConstructors()
   {
      TypeInfoFactory factory = getTypeInfoFactory();

      ClassInfo simpleBean = (ClassInfo) factory.getTypeInfo(SimpleBean.class);

      TypeInfo stringType = factory.getTypeInfo(String.class);
      ParameterInfo stringParam = new ParameterInfoImpl(null, "arg0", stringType);
      ParameterInfo[] stringParameters = new ParameterInfo[] { stringParam };
      
      TypeInfo boolType = PrimitiveInfo.BOOLEAN;
      ParameterInfo boolParam = new ParameterInfoImpl(null, "arg0", boolType);
      ParameterInfo[] boolParameters = new ParameterInfo[] { boolParam };

      TypeInfo intType = PrimitiveInfo.INT;
      ParameterInfo intParam = new ParameterInfoImpl(null, "arg0", intType);
      ParameterInfo[] intParameters = new ParameterInfo[] { intParam };

      TypeInfo objectType = factory.getTypeInfo(Object.class);
      ParameterInfo objectParam = new ParameterInfoImpl(null, "arg0", objectType);
      ParameterInfo[] objectParameters = new ParameterInfo[] { objectParam };

      HashSet<ConstructorInfo> result = new HashSet<ConstructorInfo>();
      result.add(new ConstructorInfoImpl(null, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new ConstructorInfoImpl(null, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new ConstructorInfoImpl(null, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PACKAGE, simpleBean));
      result.add(new ConstructorInfoImpl(null, intParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PROTECTED, simpleBean));
      result.add(new ConstructorInfoImpl(null, boolParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PRIVATE, simpleBean));
      
      return result;
   }

   protected abstract TypeInfoFactory getTypeInfoFactory();
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.reflect");
   }
}