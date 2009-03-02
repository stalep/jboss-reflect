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
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.classinfo.support.ClassInfoEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoGenericClass;
import org.jboss.test.classinfo.support.ClassInfoGenericConstructorsClass;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsCollection;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsCollectionAndChangesParameter;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsCollectionInComplicatedWay;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsCollectionInComplicatedWayWIthSpecificType;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsCollectionNotGeneric;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsMap;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsMapAndChangesParameters;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsMapInComplicatedWay;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsMapInComplicatedWayWIthSpecificType;
import org.jboss.test.classinfo.support.ClassInfoGenericExtendsMapNotGeneric;
import org.jboss.test.classinfo.support.ClassInfoGenericFieldsClass;
import org.jboss.test.classinfo.support.ClassInfoGenericImplementsCollection;
import org.jboss.test.classinfo.support.ClassInfoGenericImplementsCollectionNotGeneric;
import org.jboss.test.classinfo.support.ClassInfoGenericImplementsMap;
import org.jboss.test.classinfo.support.ClassInfoGenericImplementsMapNotGeneric;
import org.jboss.test.classinfo.support.ClassInfoGenericInterface;
import org.jboss.test.classinfo.support.ClassInfoGenericMethodsClass;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperClassEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperClassString;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperInterfaceEmptyClass;
import org.jboss.test.classinfo.support.ClassInfoGenericSuperInterfaceString;


/**
 * ClassInfoGenericClassTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoGenericClassTest extends AbstractClassInfoTest
{
   public ClassInfoGenericClassTest(String name)
   {
      super(name);
   }
   
   public void testGenericSuperClassString()
   {
      testGenericSuperClass(ClassInfoGenericSuperClassString.class, ClassInfoGenericClass.class, new Class[] { String.class });
   }
   
   public void testGenericSuperClassEmptyClass()
   {
      testGenericSuperClass(ClassInfoGenericSuperClassEmptyClass.class, ClassInfoGenericClass.class, new Class[] { ClassInfoEmptyClass.class });
   }
   
   public void testGenericSuperInterfaceString()
   {
      testGenericSuperInterface(ClassInfoGenericSuperInterfaceString.class, ClassInfoGenericInterface.class, new Class[] { String.class });
   }
   
   public void testGenericSuperInterfaceEmptyClass()
   {
      testGenericSuperInterface(ClassInfoGenericSuperInterfaceEmptyClass.class, ClassInfoGenericInterface.class, new Class[] { ClassInfoEmptyClass.class });
   }
   
   public void testGenericSuperClass(Class<?> clazz, Class<?> genericClass, Class<?>[] genericTypes)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      ClassInfo typeInfo = (ClassInfo) factory.getTypeInfo(clazz);
      ClassInfo superClassInfo = typeInfo.getGenericSuperclass();
      ClassInfo genericClassInfo = (ClassInfo) factory.getTypeInfo(genericClass);
      getLog().debug("Checking superClass: " + genericClass + " against " + superClassInfo);
      assertEquals(genericClassInfo, superClassInfo);

      TypeInfo[] types = new TypeInfo[genericTypes.length];
      for (int i = 0; i < types.length; ++i)
         types[i] = factory.getTypeInfo(genericTypes[i]);
      TypeInfo[] actualTypes = superClassInfo.getActualTypeArguments();
      getLog().debug("Checking superClass types: " + Arrays.asList(genericTypes) + " against " + Arrays.asList(actualTypes));
      assertEquals(types.length, actualTypes.length);
      for (int i = 0; i < types.length; ++i)
         assertEquals(types[i], actualTypes[i]);
   }
   
   public void testGenericSuperInterface(Class<?> clazz, Class<?> genericClass, Class<?>[] genericTypes)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      ClassInfo typeInfo = (ClassInfo) factory.getTypeInfo(clazz);
      InterfaceInfo[] superInterfaces = typeInfo.getGenericInterfaces();
      assertNotNull(superInterfaces);
      assertEquals(1, superInterfaces.length);
      InterfaceInfo superInterface = superInterfaces[0]; 
      getLog().debug("Checking superInterface: " + genericClass + " against " + superInterface);
      ClassInfo genericClassInfo = (ClassInfo) factory.getTypeInfo(genericClass);
      assertEquals(genericClassInfo, superInterface);

      TypeInfo[] types = new TypeInfo[genericTypes.length];
      for (int i = 0; i < types.length; ++i)
         types[i] = factory.getTypeInfo(genericTypes[i]);
      TypeInfo[] actualTypes = superInterface.getActualTypeArguments();
      getLog().debug("Checking superInterface types: " + Arrays.asList(genericTypes) + " against " + Arrays.asList(actualTypes));
      assertEquals(types.length, actualTypes.length);
      for (int i = 0; i < types.length; ++i)
         assertEquals(types[i], actualTypes[i]);
   }

   public void testGenericMethodsClass() throws Throwable
   {
      testGenericClass(ClassInfoGenericMethodsClass.class);
   }

   public void testGenericConstructorsClass() throws Throwable
   {
      testGenericClass(ClassInfoGenericConstructorsClass.class);
   }
   
   public void testGenericFieldsClass() throws Throwable
   {
      testGenericClass(ClassInfoGenericFieldsClass.class);
   }
   
   public void testComponentTypeNotGeneric() throws Throwable
   {
      assertComponentType(Collection.class, Object.class);
   }
   
   public void testComponentTypeImplementsCollectionNotGeneric() throws Throwable
   {
      assertComponentType(ClassInfoGenericImplementsCollectionNotGeneric.class, Object.class);
   }
   
   public void testComponentTypeExtendsCollectionNotGeneric() throws Throwable
   {
      assertComponentType(ClassInfoGenericExtendsCollectionNotGeneric.class, Object.class);
   }
   
   public static Collection<Boolean> signatureCollectionBoolean() 
   {
      return null;
   }
   
   public void testComponentTypeCollectionBoolean() throws Throwable
   {
      assertComponentType("signatureCollectionBoolean", Boolean.class);
   }
   
   public void testComponentTypeImplementsCollection() throws Throwable
   {
      assertComponentType(ClassInfoGenericImplementsCollection.class, Long.class);
   }
   
   public void testComponentTypeExtendsCollection() throws Throwable
   {
      assertComponentType(ClassInfoGenericExtendsCollection.class, Short.class);
   }
   
   public void testComponentTypeExtendsCollectionAndChangesParameter() throws Throwable
   {
      assertComponentType(ClassInfoGenericExtendsCollectionAndChangesParameter.class, Object.class);
   }
   
   public static ClassInfoGenericExtendsCollectionAndChangesParameter<Float> signatureChangesParameter() 
   {
      return null;
   }
   
   public void testComponentTypeExtendsCollectionAndChangesParameterExplicit() throws Throwable
   {
      assertComponentType("signatureChangesParameter", Float.class);
   }
   
   public void testComponentTypeExtendsCollectionInAComplicatedWay() throws Throwable
   {
      assertComponentType(ClassInfoGenericExtendsCollectionInComplicatedWay.class, Object.class);
   }
   
   public static ClassInfoGenericExtendsCollectionInComplicatedWay<String, Float, Date> signatureComplicatedWay() 
   {
      return null;
   }
   
   public void testComponentTypeExtendsCollectionInAComplicatedWayExplicit() throws Throwable
   {
      assertComponentType("signatureComplicatedWay", Date.class);
   }
   
   public void testComponentTypeExtendsCollectionInAComplicatedWayWithSpecificType() throws Throwable
   {
      assertComponentType(ClassInfoGenericExtendsCollectionInComplicatedWayWIthSpecificType.class, Double.class);
   }
   
   public void testKeyValueTypeNotGeneric() throws Throwable
   {
      assertKeyValueType(Map.class, Object.class, Object.class);
   }
   
   public void testKeyValueTypeImplementsMapNotGeneric() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericImplementsMapNotGeneric.class, Object.class, Object.class);
   }
   
   public void testKeyValueTypeExtendsMapNotGeneric() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericExtendsMapNotGeneric.class, Object.class, Object.class);
   }

   public static Map<Boolean, Date> signatureMapBooleanDate() 
   {
      return null;
   }
   
   public void testKeyValueTypeMapBooleanDate() throws Throwable
   {
      assertKeyValueType("signatureMapBooleanDate", Boolean.class, Date.class);
   }
   
   public void testKeyValueTypeImplementsMap() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericImplementsMap.class, Long.class, Integer.class);
   }
   
   public void testKeyValueTypeExtendsMap() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericExtendsMap.class, Short.class, Double.class);
   }
   
   public void testKeyValueTypeExtendsMapAndChangesParameters() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericExtendsMapAndChangesParameters.class, Object.class, Object.class);
   }
   
   public static ClassInfoGenericExtendsMapAndChangesParameters<Float, Double> signatureMapChangesParameter() 
   {
      return null;
   }
   
   public void testKeyValueTypeExtendsMapAndChangesParameterExplicit() throws Throwable
   {
      assertKeyValueType("signatureMapChangesParameter", Float.class, Double.class);
   }
   
   public void testKeyValueTypeExtendsMapInAComplicatedWay() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericExtendsMapInComplicatedWay.class, Object.class, Object.class);
   }
   
   public static ClassInfoGenericExtendsMapInComplicatedWay<String, Float, Date, StringBuffer> signatureMapComplicatedWay() 
   {
      return null;
   }
   
   public void testComponentTypeExtendsMapInAComplicatedWayExplicit() throws Throwable
   {
      assertKeyValueType("signatureMapComplicatedWay", Date.class, StringBuffer.class);
   }
   
   public void testComponentTypeExtendsMapInAComplicatedWayWithSpecificType() throws Throwable
   {
      assertKeyValueType(ClassInfoGenericExtendsMapInComplicatedWayWIthSpecificType.class, Double.class, Short.class);
   }
   
   private void assertComponentType(String methodName, Class<?> expected) throws Exception
   {
      Method method = ClassInfoGenericClassTest.class.getMethod(methodName, (Class[]) null);
      Type type = method.getGenericReturnType();
      assertComponentType(type, expected);
   }
   
   private void assertComponentType(Type type, Class<?> expected) throws Exception
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo typeInfo = factory.getTypeInfo(type);
      ClassInfo classInfo = assertInstanceOf(typeInfo, ClassInfo.class);
      assertTrue(classInfo.isCollection());

      TypeInfo expectedInfo = factory.getTypeInfo(expected);
      assertEquals(expectedInfo, classInfo.getComponentType());
   }
   
   private void assertKeyValueType(String methodName, Class<?> keyExpected, Class<?> valueExpected) throws Exception
   {
      Method method = ClassInfoGenericClassTest.class.getMethod(methodName, (Class[]) null);
      Type type = method.getGenericReturnType();
      assertKeyValueType(type, keyExpected, valueExpected);
   }
   
   private void assertKeyValueType(Type type, Class<?> keyExpected, Class<?> valueExpected) throws Exception
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo typeInfo = factory.getTypeInfo(type);
      ClassInfo classInfo = assertInstanceOf(typeInfo, ClassInfo.class);
      assertTrue(classInfo.isMap());

      TypeInfo expectedInfo = factory.getTypeInfo(keyExpected);
      assertEquals(expectedInfo, classInfo.getKeyType());

      expectedInfo = factory.getTypeInfo(valueExpected);
      assertEquals(expectedInfo, classInfo.getValueType());
   }
   
   private void testGenericClass(Class<?> clazz) throws Throwable
   {
      ClassInfoImpl expected = new ClassInfoImpl(clazz.getName(), ModifierInfo.PUBLIC);
      TypeInfo info = testBasics(clazz, expected);
      
      assertFalse(info.isArray());
      assertFalse(info.isEnum());
      assertFalse(info.isPrimitive());
      
      ClassInfo classInfo = (ClassInfo) info;
      assertClassInfo(classInfo, clazz);
   }
}
