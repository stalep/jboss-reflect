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

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.jboss.reflect.plugins.AnnotationInfoImpl;
import org.jboss.reflect.plugins.AnnotationValueImpl;
import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.PackageInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.reflect.spi.Value;
import org.jboss.test.ContainerTest;

/**
 * AbstractClassInfoTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractClassInfoTest extends ContainerTest
{
   public AbstractClassInfoTest(String name)
   {
      super(name);
   }
   
   protected TypeInfo testBasics(Class<?> clazz, TypeInfo expected) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      getLog().debug("Using typeInfoFactory: " + factory);
      
      TypeInfo info = factory.getTypeInfo(clazz);
      getLog().debug("Got: " + info + " from " + clazz);
      assertNotNull(info);
      assertEquals(info, expected);

      ClassLoader cl = getClass().getClassLoader();
      info = factory.getTypeInfo(clazz.getName(), getClass().getClassLoader());
      getLog().debug("Got: " + info + " from " + clazz.getName() + " cl=" + cl);
      assertNotNull(info);
      assertEquals(info, expected);
      
      getLog().debug("Name: " + info.getName());
      assertEquals(clazz.getName(), info.getName());
      
      getLog().debug("Type: " + info.getType());
      assertEquals(clazz, info.getType());
      
      // TODO JBMICROCONT-128 fix the serialization
      if (isJavassistTestCase() == false)
      {
         byte[] bytes = serialize(info);
         Object deserialized = deserialize(bytes);
         assertTrue("Not the same object: " + info + " != " + deserialized, info == deserialized);
      }
      
      return info;
   }

   protected boolean isJavassistTestCase()
   {
      return (getClass().getName().contains("Javassist"));  
   }

   protected void testArray(Class<?> clazz, TypeInfo info) throws Throwable
   {
      TypeInfo arrayType = info.getArrayType();
      getLog().debug("ArrayType(0): " + arrayType);
      assertTrue(arrayType.isArray());
      Class<?> arrayClass = Array.newInstance(clazz, 0).getClass();
      assertEquals(arrayClass, arrayType.getType());

      arrayType = info.getArrayType();
      getLog().debug("ArrayType(0): " + arrayType);
      assertTrue(arrayType.isArray());
      arrayClass = Array.newInstance(clazz, 0).getClass();
      assertEquals(arrayClass, arrayType.getType());
   }
   
   protected <T> void testValues(Class<T> clazz, TypeInfo info, String[] values, T[] types) throws Throwable
   {
      int i = 0;
      for (String value : values)
      {
         Object result = info.convertValue(value);
         getLog().debug("Converted: " + value + " to " + result);
         assertEquals(result, types[i++]);
      }
   }
   
   protected void assertClassInfo(ClassInfo classInfo, Class<?> clazz) throws Throwable
   {
      assertEquals(clazz.isInterface(), classInfo.isInterface());
      assertInterfaces(clazz, classInfo);
      assertDeclaredMethods(clazz, classInfo);
      assertDeclaredFields(clazz, classInfo);
      assertDeclaredConstructors(clazz, classInfo);
      assertSuperClass(clazz, classInfo);
      assertModifiers(clazz, classInfo);
      assertPackage(clazz, classInfo);
      assertAnnotations(clazz, classInfo);
      
      testArray(clazz, classInfo);
   }

   protected void assertModifiers(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      int expected = clazz.getModifiers();
      int actual = classInfo.getModifiers();
      getLog().debug(clazz + " modifier expected=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }

   protected void assertPackage(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      Package pkg = clazz.getPackage();
      PackageInfo packageInfo = classInfo.getPackage();
      getLog().debug(clazz + " package=" + pkg + " packageInfo=" + packageInfo);
      if (pkg == null)
      {
         assertNullPackageInfo(packageInfo);
         return;
      }
      assertNotNull(packageInfo);
      String expected = pkg.getName();
      String actual = packageInfo.getName();
      getLog().debug(clazz + " package expected=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
      assertPackageAnnotations(pkg, packageInfo);
   }

   protected void assertNullPackageInfo(PackageInfo packageInfo)
   {
      assertNull(packageInfo);
   }
   
   protected void assertSuperClass(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Class<?> superClass = clazz.getSuperclass();
      TypeInfo superType = classInfo.getSuperclass();
      getLog().debug(clazz + " superClass: " + superClass + " superType=" + superType);
      if (superClass == null)
      {
         assertNull(classInfo.getSuperclass());
         return;
      }
      TypeInfo expected = factory.getTypeInfo(clazz.getSuperclass());

      TypeInfo actual = classInfo.getSuperclass();
      assertEquals(expected, actual);
   }
   
   protected void assertInterfaces(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Set<TypeInfo> expected = new HashSet<TypeInfo>();
      for (Class<?> c : clazz.getInterfaces())
      {
         TypeInfo type = factory.getTypeInfo(c);
         expected.add(type);
      }
      
      InterfaceInfo[] interfaces = classInfo.getInterfaces();
      if (expected.isEmpty())
      {
         assertEmpty(interfaces);
         return;
      }
      assertNotNull(interfaces);
      assertEquals(expected.size(), interfaces.length);
      Set<TypeInfo> actual = new HashSet<TypeInfo>();
      for (TypeInfo intf : interfaces)
         actual.add(intf);
      getLog().debug(clazz + " expected interfaces=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertDeclaredFields(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Set<FieldInfo> expected = new HashSet<FieldInfo>();
      for (Field field : clazz.getDeclaredFields())
      {
         TypeInfo type = factory.getTypeInfo(field.getGenericType());
         FieldInfo f = new FieldInfoImpl(null, field.getName(), type, field.getModifiers(), classInfo);
         expected.add(f);
      }
      
      FieldInfo[] result = classInfo.getDeclaredFields();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<FieldInfo> actual = new HashSet<FieldInfo>();
      for (FieldInfo f : result)
         actual.add(f);
      getLog().debug(clazz + " expected fields=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
      
      for (Field field : clazz.getDeclaredFields())
         assertDeclaredField(clazz, field, classInfo);
   }
   
   protected void assertDeclaredField(Class<?> clazz, Field field, ClassInfo classInfo) throws Throwable
   {
      getLog().debug("Checking field " + field.getName());
      
      TypeInfoFactory factory = getTypeInfoFactory();
      
      FieldInfo fieldInfo = classInfo.getDeclaredField(field.getName());
      assertNotNull(field.getName(), fieldInfo);
      TypeInfo type = factory.getTypeInfo(field.getGenericType());
      assertTypeEquals(field.getName(), type, fieldInfo.getType());
      assertEquals(classInfo, fieldInfo.getDeclaringClass());
      assertEquals(field.getModifiers(), fieldInfo.getModifiers());
      assertFieldAnnotations(field, fieldInfo);
   }
   
   protected void assertDeclaredMethods(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Set<MethodInfo> expected = new HashSet<MethodInfo>();
      for (Method method : clazz.getDeclaredMethods())
      {
         TypeInfo returnType = factory.getTypeInfo(method.getReturnType());
         Class<?>[] paramClasses = method.getParameterTypes();
         TypeInfo[] paramTypes = new TypeInfo[paramClasses.length];
         AnnotationValue[][] paramAnnotations = new AnnotationValue[paramClasses.length][0];
         int i = 0;
         for (Class<?> c : paramClasses)
            paramTypes[i++] = factory.getTypeInfo(c);
         MethodInfo m = new MethodInfoImpl(null, method.getName(), returnType, paramTypes, paramAnnotations, null, method.getModifiers(), classInfo);
         expected.add(m);
      }
      
      MethodInfo[] result = classInfo.getDeclaredMethods();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<MethodInfo> actual = new HashSet<MethodInfo>();
      for (MethodInfo f : result)
         actual.add(f);
      getLog().debug(clazz + " expected methods=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
      
      for (Method method : clazz.getDeclaredMethods())
         assertDeclaredMethod(clazz, method, classInfo);
   }
   
   protected void assertDeclaredMethod(Class<?> clazz, Method method, ClassInfo classInfo) throws Throwable
   {
      getLog().debug("Checking method " + method.getName() + Arrays.asList(method.getParameterTypes()));

      TypeInfoFactory factory = getTypeInfoFactory();
      
      Type[] paramClasses = method.getGenericParameterTypes();
      TypeInfo[] paramTypes = new TypeInfo[paramClasses.length];
      for (int i = 0; i < paramClasses.length; ++i)
         paramTypes[i] = factory.getTypeInfo(paramClasses[i]);
      MethodInfo methodInfo = classInfo.getDeclaredMethod(method.getName(), paramTypes);
      assertNotNull(method.getName(), methodInfo);
      TypeInfo returnType = factory.getTypeInfo(method.getGenericReturnType());
      TypeInfo[] actualParamTypes = methodInfo.getParameterTypes();
      for (int i = 0; i < paramTypes.length; ++i)
         assertTypeEquals(method.getName() + " param" + i, paramTypes[i], actualParamTypes[i]);
      Class<?>[] exceptionClasses = method.getExceptionTypes();
      TypeInfo[] expectedExceptionTypes = new TypeInfo[exceptionClasses.length];
      for (int i = 0; i < exceptionClasses.length; ++i)
         expectedExceptionTypes[i] = factory.getTypeInfo(exceptionClasses[i]);
      TypeInfo[] actualExceptionTypes = methodInfo.getExceptionTypes();
      for (int i = 0; i < exceptionClasses.length; ++i)
         assertTypeEquals(method.getName() + " exception" + i, expectedExceptionTypes[i], actualExceptionTypes[i]);
      assertTypeEquals(method.getName() + " returnType", returnType, methodInfo.getReturnType());
      assertEquals(classInfo, methodInfo.getDeclaringClass());
      assertEquals(method.getModifiers(), methodInfo.getModifiers());
      assertMethodAnnotations(method, methodInfo);
      assertParameterAnnotations(method, methodInfo);
   }
   
   protected void assertDeclaredConstructors(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      Set<ConstructorInfo> expected = new HashSet<ConstructorInfo>();
      for (Constructor<?> constructor : clazz.getDeclaredConstructors())
      {
         Class<?>[] paramClasses = constructor.getParameterTypes();
         TypeInfo[] paramTypes = new TypeInfo[paramClasses.length];
         AnnotationValue[][] paramAnnotations = new AnnotationValue[paramClasses.length][0];
         int i = 0;
         for (Class<?> c : paramClasses)
            paramTypes[i++] = factory.getTypeInfo(c);
         ConstructorInfo c = new ConstructorInfoImpl(null, paramTypes, paramAnnotations, null, constructor.getModifiers(), classInfo);
         expected.add(c);
      }
      
      ConstructorInfo[] result = classInfo.getDeclaredConstructors();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<ConstructorInfo> actual = new HashSet<ConstructorInfo>();
      for (ConstructorInfo f : result)
         actual.add(f);
      getLog().debug(clazz + " expected constructors=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
      
      for (Constructor<?> constructor : clazz.getDeclaredConstructors())
         assertDeclaredConstructor(clazz, constructor, classInfo);
   }
   
   protected void assertDeclaredConstructor(Class<?> clazz, Constructor<?> constructor, ClassInfo classInfo) throws Throwable
   {
      getLog().debug("Checking constructor " + Arrays.asList(constructor.getParameterTypes()));

      TypeInfoFactory factory = getTypeInfoFactory();
      
      Type[] paramClasses = constructor.getGenericParameterTypes();
      
      // HACK: This is to workaround a bug in Sun's compiler related to enum constructors
      //       having no generic parameters?
      Type[] parameterTypes = constructor.getParameterTypes();
      if (paramClasses.length != parameterTypes.length)
         paramClasses = parameterTypes;
      
      TypeInfo[] paramTypes = new TypeInfo[paramClasses.length];
      for (int i = 0; i < paramClasses.length; ++i)
         paramTypes[i] = factory.getTypeInfo(paramClasses[i]);
      ConstructorInfo constructorInfo = classInfo.getDeclaredConstructor(paramTypes);
      assertNotNull(constructorInfo);
      TypeInfo[] actualParamTypes = constructorInfo.getParameterTypes();
      for (int i = 0; i < paramTypes.length; ++i)
         assertTypeEquals(clazz + " constructorParameter" + i, paramTypes[i], actualParamTypes[i]);
      Class<?>[] exceptionClasses = constructor.getExceptionTypes();
      TypeInfo[] expectedExceptionTypes = new TypeInfo[exceptionClasses.length];
      for (int i = 0; i < exceptionClasses.length; ++i)
         expectedExceptionTypes[i] = factory.getTypeInfo(exceptionClasses[i]);
      TypeInfo[] actualExceptionTypes = constructorInfo.getExceptionTypes();
      for (int i = 0; i < exceptionClasses.length; ++i)
         assertTypeEquals(clazz + " constructorException" + i, expectedExceptionTypes[i], actualExceptionTypes[i]);
      assertEquals(classInfo, constructorInfo.getDeclaringClass());
      assertEquals(constructor.getModifiers(), constructorInfo.getModifiers());
      assertConstructorAnnotations(constructor, constructorInfo);
      assertParameterAnnotations(constructor, constructorInfo);
   }
   
   protected void assertAnnotations(Class<?> clazz, ClassInfo classInfo) throws Throwable
   {
      Set<AnnotationValue> expected = getExpectedAnnotations(clazz.getDeclaredAnnotations());
      
      AnnotationValue[] result = classInfo.getAnnotations();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<AnnotationValue> actual = new HashSet<AnnotationValue>();
      for (AnnotationValue f : result)
         actual.add(f);
      getLog().debug(clazz + " expected annotations=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertFieldAnnotations(Field field, FieldInfo fieldInfo) throws Throwable
   {
      Set<AnnotationValue> expected = getExpectedAnnotations(field.getDeclaredAnnotations());
      
      AnnotationValue[] result = fieldInfo.getAnnotations();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<AnnotationValue> actual = new HashSet<AnnotationValue>();
      for (AnnotationValue f : result)
         actual.add(f);
      getLog().debug(field.getName() + " expected annotations=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertMethodAnnotations(Method method, MethodInfo methodInfo) throws Throwable
   {
      Set<AnnotationValue> expected = getExpectedAnnotations(method.getDeclaredAnnotations());
      
      AnnotationValue[] result = methodInfo.getAnnotations();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<AnnotationValue> actual = new HashSet<AnnotationValue>();
      for (AnnotationValue f : result)
         actual.add(f);
      getLog().debug(method.getName() + " expected annotations=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertParameterAnnotations(Method method, MethodInfo methodInfo) throws Throwable
   {
      Annotation[][] annotations = method.getParameterAnnotations();
      ParameterInfo[] parameters = methodInfo.getParameters();
      for (int i = 0; i < annotations.length; ++i)
         assertParameterAnnotations(annotations[i], parameters[i]);
   }
   
   protected void assertConstructorAnnotations(Constructor<?> constructor, ConstructorInfo constructorInfo) throws Throwable
   {
      Set<AnnotationValue> expected = getExpectedAnnotations(constructor.getDeclaredAnnotations());
      
      AnnotationValue[] result = constructorInfo.getAnnotations();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<AnnotationValue> actual = new HashSet<AnnotationValue>();
      for (AnnotationValue f : result)
         actual.add(f);
      getLog().debug("Expected annotations=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertPackageAnnotations(Package pkg, PackageInfo packageInfo) throws Throwable
   {
      Set<AnnotationValue> expected = getExpectedAnnotations(pkg.getDeclaredAnnotations());
      
      AnnotationValue[] result = packageInfo.getAnnotations();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<AnnotationValue> actual = new HashSet<AnnotationValue>();
      for (AnnotationValue f : result)
         actual.add(f);
      getLog().debug("Expected annotations=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }
   
   protected void assertParameterAnnotations(Constructor<?> constructor, ConstructorInfo constructorInfo) throws Throwable
   {
      Annotation[][] annotations = constructor.getParameterAnnotations();
      ParameterInfo[] parameters = constructorInfo.getParameters();
      for (int i = 0; i < annotations.length; ++i)
         assertParameterAnnotations(annotations[i], parameters[i]);
   }
   
   protected void assertParameterAnnotations(Annotation[] annotations, ParameterInfo parameter) throws Throwable
   {
      Set<AnnotationValue> expected = getExpectedAnnotations(annotations);
      
      AnnotationValue[] result = parameter.getAnnotations();
      if (expected.isEmpty())
      {
         assertEmpty(result);
         return;
      }
      assertNotNull(result);
      assertEquals(expected.size(), result.length);
      Set<AnnotationValue> actual = new HashSet<AnnotationValue>();
      for (AnnotationValue f : result)
         actual.add(f);
      getLog().debug(parameter.getName() + " expected parameter annotations=" + expected + " actual=" + actual);
      assertEquals(expected, actual);
   }

   protected Set<AnnotationValue> getExpectedAnnotations(Annotation[] annotations)
   {
      Set<AnnotationValue> expected = new HashSet<AnnotationValue>();
      for (Annotation annotation : annotations)
      {
         Class<?> type = annotation.annotationType();
         AnnotationInfoImpl info = new AnnotationInfoImpl(type.getName(), type.getModifiers());
         // TODO JBMICROCONT-127 attributes
         AnnotationValue a = new AnnotationValueImpl(info, new HashMap<String, Value>(), annotation);
         expected.add(a);
      }
      return expected;
   }

   protected void assertTypeEquals(String context, TypeInfo expected, TypeInfo actual) throws Exception
   {
      assertEquals(expected, actual);
      if (expected instanceof ClassInfo)
      {
         ClassInfo expectedClassInfo = (ClassInfo) expected;
         ClassInfo actualClassInfo = (ClassInfo) actual;
         assertEquals(context, expectedClassInfo.getOwnerType(), actualClassInfo.getOwnerType());
         assertEquals(context, expectedClassInfo.getRawType(), actualClassInfo.getRawType());
         TypeInfo[] expectedTypeArgs = expectedClassInfo.getActualTypeArguments();
         TypeInfo[] actualTypeArgs = expectedClassInfo.getActualTypeArguments();
         if (expectedTypeArgs == null)
            assertNull(context, actualTypeArgs);
         else
         {
            assertNotNull(context, actualTypeArgs);
            getLog().debug("Checking type args for " + context + " expected: " + Arrays.asList(expectedTypeArgs) + " actual: " + Arrays.asList(actualTypeArgs));
            assertEquals(expectedTypeArgs.length, actualTypeArgs.length);
            for (int i = 0; i < expectedTypeArgs.length; ++i)
               assertTypeEquals(context + "arg" + i, expectedTypeArgs[i], actualTypeArgs[i]);
         }
      }
   }
   
   protected abstract TypeInfoFactory getTypeInfoFactory();
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.reflect");
   }
}
