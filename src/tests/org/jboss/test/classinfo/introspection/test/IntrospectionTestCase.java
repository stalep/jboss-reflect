/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.classinfo.introspection.test;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.ConstructorInfo;
import org.jboss.reflect.FieldInfo;
import org.jboss.reflect.InterfaceInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.ModifierInfo;
import org.jboss.reflect.ParameterInfo;
import org.jboss.reflect.PrimitiveInfo;
import org.jboss.reflect.TypeInfo;
import org.jboss.reflect.TypeInfoFactory;
import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.plugins.ParameterInfoImpl;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.test.BaseTestCase;
import org.jboss.test.classinfo.introspection.support.SimpleBean;
import org.jboss.test.classinfo.introspection.support.SimpleInterface;

/**
 * Introspection Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class IntrospectionTestCase extends BaseTestCase
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   public IntrospectionTestCase(String name)
   {
      super(name);
   }

   // Public --------------------------------------------------------
   
   public void testSimpleBeanClassInfo() throws Throwable
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);

      assertEquals(SimpleBean.class.getName(), cinfo.getName());
      ClassInfo supercinfo = cinfo.getSuperclass();
      assertNotNull(supercinfo);
      assertEquals(Object.class.getName(), supercinfo.getName());
      
      HashSet expected = new HashSet();
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

   public void testSimpleBeanConstructors() throws Throwable
   {
      ClassInfo cinfo = getClassInfo(SimpleBean.class);
      
      checkConstructors(getSimpleBeanConstructors(), cinfo.getDeclaredConstructors());
   }

   // TestCase overrides --------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   protected ClassInfo getClassInfo(Class clazz)
   {
      IntrospectionTypeInfoFactory factory = new IntrospectionTypeInfoFactory();
      TypeInfo info = factory.getTypeInfo(clazz);
      assertNotNull(info);
      assertTrue(info instanceof ClassInfo);
      ClassInfo cinfo = (ClassInfo) info;
      log.debug(cinfo);
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
      log.debug(simpleInterfaceInfo);

      return simpleInterfaceInfo;
   }

   protected void checkTypeSet(HashSet expected, TypeInfo[] typeInfos) throws Throwable
   {
      HashSet actual = new HashSet();
      for (int i = 0; i < typeInfos.length; ++i)
         actual.add(typeInfos[i].getName());
      
      HashSet expectClone = new HashSet(expected);
      HashSet actualClone = new HashSet(actual);
      
      log.debug("checkTypeSet expect=" + expected);
      log.debug("checkTypeSet actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }

   protected void checkFields(Set expected, FieldInfo[] actually) throws Throwable
   {
      HashSet expectClone = new HashSet(expected);
      ArrayList actualClone = new ArrayList(Arrays.asList(actually));
      
      log.debug("checkFields expect=" + expectClone);
      log.debug("checkFields actual=" + actualClone);
      
      expectClone.removeAll(actualClone);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());

      expectClone = new HashSet(expected);
      actualClone = new ArrayList(Arrays.asList(actually));
      for (Iterator i = expectClone.iterator(); i.hasNext();)
      {
         FieldInfo expect = (FieldInfo) i.next();
         int j = actualClone.indexOf(expect);
         FieldInfo actual = (FieldInfo) actualClone.get(j);
         compareField(expect, actual);
      }
   }

   protected void compareField(FieldInfo expect, FieldInfo actual) throws Throwable
   {
      log.debug("CompareField expect=" + expect + " actual=" + actual);

      assertEquals("Name", expect.getName(), actual.getName());
      assertEquals("Type", expect.getType(), actual.getType());
      assertEquals("Modifiers", expect.getModifiers(), actual.getModifiers());
      assertEquals("Declaring Class", expect.getDeclaringClass(), actual.getDeclaringClass());
      assertEquals("Annotations", expect.getAnnotations(), actual.getAnnotations());
   }
   
   protected void checkMethods(Set expected, MethodInfo[] actually) throws Throwable
   {
      HashSet expectClone = new HashSet(expected);
      ArrayList actualClone = new ArrayList(Arrays.asList(actually));
      
      log.debug("checkMethods expect=" + expectClone);
      log.debug("checkMethods actual=" + actualClone);
      
      expectClone.removeAll(actualClone);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());

      expectClone = new HashSet(expected);
      actualClone = new ArrayList(Arrays.asList(actually));
      for (Iterator i = expectClone.iterator(); i.hasNext();)
      {
         MethodInfo expect = (MethodInfo) i.next();
         int j = actualClone.indexOf(expect);
         MethodInfo actual = (MethodInfo) actualClone.get(j);
         compareMethod(expect, actual);
      }
   }

   protected void compareMethod(MethodInfo expect, MethodInfo actual) throws Throwable
   {
      log.debug("MethodField expect=" + expect + " actual=" + actual);

      assertEquals("Name", expect.getName(), actual.getName());
      assertEquals("ReturnType", expect.getReturnType(), actual.getReturnType());
      assertEquals("ParameterTypes", expect.getParameterTypes(), actual.getParameterTypes());
      assertEquals("Parameters", expect.getParameters(), actual.getParameters());
      assertEquals("Exceptions", expect.getExceptionTypes(), actual.getExceptionTypes());
      assertEquals("Modifiers", expect.getModifiers(), actual.getModifiers());
      assertEquals("Declaring Class", expect.getDeclaringClass(), actual.getDeclaringClass());
      assertEquals("Annotations", expect.getAnnotations(), actual.getAnnotations());
   }

   protected void checkConstructors(Set expected, ConstructorInfo[] actually) throws Throwable
   {
      HashSet expectClone = new HashSet(expected);
      ArrayList actualClone = new ArrayList(Arrays.asList(actually));
      
      log.debug("checkConstructors expect=" + expectClone);
      log.debug("checkConstructors actual=" + actualClone);
      
      expectClone.removeAll(actualClone);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());

      expectClone = new HashSet(expected);
      actualClone = new ArrayList(Arrays.asList(actually));
      for (Iterator i = expectClone.iterator(); i.hasNext();)
      {
         ConstructorInfo expect = (ConstructorInfo) i.next();
         int j = actualClone.indexOf(expect);
         ConstructorInfo actual = (ConstructorInfo) actualClone.get(j);
         compareConstructor(expect, actual);
      }
   }

   protected void compareConstructor(ConstructorInfo expect, ConstructorInfo actual) throws Throwable
   {
      log.debug("ConstructorField expect=" + expect + " actual=" + actual);

      assertEquals("ParameterTypes", expect.getParameterTypes(), actual.getParameterTypes());
      assertEquals("Parameters", expect.getParameters(), actual.getParameters());
      assertEquals("Exceptions", expect.getExceptionTypes(), actual.getExceptionTypes());
      assertEquals("Modifiers", expect.getModifiers(), actual.getModifiers());
      assertEquals("Declaring Class", expect.getDeclaringClass(), actual.getDeclaringClass());
      assertEquals("Annotations", expect.getAnnotations(), actual.getAnnotations());
   }
   
   protected Set getSimpleInterfaceFields()
   {
      TypeInfoFactory factory = new IntrospectionTypeInfoFactory(); 

      ClassInfo simpleInterface = (ClassInfo) factory.getTypeInfo(SimpleInterface.class);
      
      TypeInfo objectType = factory.getTypeInfo(Object.class);
      
      HashSet result = new HashSet();
      result.add(new FieldInfoImpl(null, "A_CONSTANT", objectType, ModifierInfo.PUBLIC_CONSTANT, simpleInterface));
      return result;
   }
   
   protected Set getSimpleInterfaceMethods()
   {
      TypeInfoFactory factory = new IntrospectionTypeInfoFactory(); 

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
      
      HashSet result = new HashSet();
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
   
   protected Set getSimpleBeanFields()
   {
      TypeInfoFactory factory = new IntrospectionTypeInfoFactory(); 

      TypeInfo longType = PrimitiveInfo.LONG;
      
      ClassInfo simpleBean = (ClassInfo) factory.getTypeInfo(SimpleBean.class);
      
      TypeInfo objectType = factory.getTypeInfo(Object.class);
      
      HashSet result = new HashSet();
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
   
   protected Set getSimpleBeanMethods()
   {
      TypeInfoFactory factory = new IntrospectionTypeInfoFactory(); 

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
      
      HashSet result = new HashSet();
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
   
   protected Set getSimpleBeanConstructors()
   {
      TypeInfoFactory factory = new IntrospectionTypeInfoFactory(); 

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

      HashSet result = new HashSet();
      result.add(new ConstructorInfoImpl(null, MethodInfo.NO_PARAMS, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new ConstructorInfoImpl(null, stringParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PUBLIC, simpleBean));
      result.add(new ConstructorInfoImpl(null, objectParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PACKAGE, simpleBean));
      result.add(new ConstructorInfoImpl(null, intParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PROTECTED, simpleBean));
      result.add(new ConstructorInfoImpl(null, boolParameters, MethodInfo.NO_EXCEPTIONS, ModifierInfo.PRIVATE, simpleBean));
      
      return result;
   }
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.reflect");
   }
   
   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------

}