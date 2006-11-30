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

import java.util.HashSet;

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.ArrayValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ClassValue;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.EnumValue;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.PrimitiveValue;
import org.jboss.reflect.spi.StringValue;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.reflect.spi.Value;
import org.jboss.test.ContainerTest;
import org.jboss.test.classinfo.support.AnnotatedClass;
import org.jboss.test.classinfo.support.AnnotatedSubClass;
import org.jboss.test.classinfo.support.AnotherAnnotation;
import org.jboss.test.classinfo.support.ComplexAnnotation;
import org.jboss.test.classinfo.support.ExpectedAnnotations;
import org.jboss.test.classinfo.support.JDK14ExpectedAnnotations;
import org.jboss.test.classinfo.support.JDK50ExpectedAnnotations;
import org.jboss.test.classinfo.support.SimpleAnnotation;
import org.jboss.test.classinfo.support.TestEnum;
import org.jboss.test.classinfo.support.ValueAnnotation;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public abstract class AnnotatedClassInfoTest extends ContainerTest
{
   /** <code>@Retention</code> and <code>@Target</code> are ignored for JDK 1.4 */
   final static ExpectedAnnotations expected;
   static
   {
      boolean haveJDK5 = false;
      try
      {
         Class.forName("java.lang.annotation.Target");
         haveJDK5 = true;
      }
      catch(Exception e)
      {
      }
      
      if (haveJDK5)
      {
         expected = new JDK50ExpectedAnnotations();
      }
      else
      {
         expected = new JDK14ExpectedAnnotations();
      }
   }
   final static Class[] EXPECTED_ANNOTATIONS = expected.getExpectedAnnotations();
   final static Class[] ANNOTATION_EXPECTED_ANNOTATIONS = expected.getAnnotationExpectedAnnotations();   
   final static Class[] COMPLEXANNOTATION_EXPECTED_ANNOTATIONS = expected.getComplexExpectedAnnotations();
   final static Class[] FIRST_PARAM_EXPECTED_ANNOTATIONS = expected.getFirstParamExpectedAnnotations();
   final static Class[] SECOND_PARAM_EXPECTED_ANNOTATIONS = expected.getSecondParamExpectedAnnotations();
   
   
   final static ExpectedComplexAnnotationData CLASS_DATA = 
      new ExpectedComplexAnnotationData(
            "_class", 
            1, 
            "class_", 
            AnnotatedClass.class, 
            "CLASS", 
            new String[] {"A", "class"},
            new Integer[] {new Integer(10), new Integer(1)},
            new String[] {"stuffer", "class"},
            new Class[] {AnnotatedClass.class},
            new String[] {"CLASS", "ARRAY_STUFF"});
   
   final static ExpectedComplexAnnotationData CONSTRUCTOR_DATA = 
      new ExpectedComplexAnnotationData(
            "_ctor", 
            3, 
            "ctor_", 
            AnnotatedClass.class, 
            "CONSTRUCTOR", 
            new String[] {"A", "ctor"},
            new Integer[] {new Integer(10), new Integer(3)},
            new String[] {"stuffer", "ctor"},
            new Class[] {AnnotatedClass.class},
            new String[] {"CONSTRUCTOR", "ARRAY_STUFF"});
 
   final static ExpectedComplexAnnotationData FIELD_DATA = 
      new ExpectedComplexAnnotationData(
            "_field", 
            2, 
            "field_", 
            AnnotatedClass.class, 
            "FIELD", 
            new String[] {"A", "field"},
            new Integer[] {new Integer(10), new Integer(2)},
            new String[] {"stuffer", "field"},
            new Class[] {AnnotatedClass.class},
            new String[] {"FIELD", "ARRAY_STUFF"});

   final static ExpectedComplexAnnotationData METHOD_DATA = 
      new ExpectedComplexAnnotationData(
            "The answer is", 
            42, 
            "method_", 
            Integer.class, 
            "METHOD", 
            new String[] {"The", "answer", "is"},
            new Integer[] {new Integer(4), new Integer(2)},
            new String[] {"Ann1", "Ann2"},
            new Class[] {Integer.class, Long.class},
            new String[] {"ARRAY_STUFF", "METHOD"});

   final static ExpectedComplexAnnotationData PARAM_DATA = 
      new ExpectedComplexAnnotationData(
            "1", 
            42, 
            "method_", 
            Integer.class, 
            "METHOD", 
            new String[] {"The", "answer", "is"},
            new Integer[] {new Integer(1)},
            new String[] {"Ann1", "Ann2"},
            new Class[] {Integer.class, Long.class},
            new String[] {"ARRAY_STUFF", "METHOD"});
   
   public AnnotatedClassInfoTest(String name)
   {
      super(name);
   }
   
   public void testClassAnnotations() throws Exception
   {
      ClassInfo info = getClassInfo(AnnotatedClass.class);

      checkAnnotations(info, CLASS_DATA);
   }
   
   public void testSubClassAnnotations() throws Exception 
   {
      ClassInfo info = getClassInfo(AnnotatedSubClass.class);

      System.out.println("---> Getting annotations");
      AnnotationValue[] annotations = info.getAnnotations();
      assertEquals(2, annotations.length);
      AnnotationValue anotherAnnotation = getAnnotationCheckTypeAndName(info, AnotherAnnotation.class.getName());
      AnnotationValue simpleAnnotation = getAnnotationCheckTypeAndName(info, SimpleAnnotation.class.getName());
      
      HashSet<AnnotationValue> set = new HashSet<AnnotationValue>();
      set.add(anotherAnnotation);
      set.add(simpleAnnotation);
      
      for (int i = 0 ; i < annotations.length ; i++)
      {
         set.remove(annotations[i]);
      }
      
      assertTrue("Not found annotations " + set, set.isEmpty());
   }
   
   public void testClassArrayAnnotations() throws Exception
   {
      Class classArray = new AnnotatedClass[0].getClass();
      
      ClassInfo info = getClassInfo(classArray);
      assertTrue(ArrayInfo.class.isAssignableFrom(info.getClass()));
      
      assertTrue(info.getAnnotations().length == 0);
      for (Class annotation : EXPECTED_ANNOTATIONS)
      {
         assertNull(info.getAnnotation(annotation.getName()));
         assertFalse(info.isAnnotationPresent(annotation.getName()));
      }
      
      ClassInfo componentInfo = (ClassInfo)((ArrayInfo)info).getComponentType();
      checkAnnotations(componentInfo, CLASS_DATA);
   }
   
   public void testSubClassArrayAnnotations() throws Exception
   {
      Class classArray = new AnnotatedSubClass[0].getClass();
      
      ClassInfo info = getClassInfo(classArray);
      assertTrue(ArrayInfo.class.isAssignableFrom(info.getClass()));

      assertTrue(info.getAnnotations().length == 0);
      assertNull(info.getAnnotation(AnotherAnnotation.class.getName()));
      assertFalse(info.isAnnotationPresent(AnotherAnnotation.class.getName()));
      assertNull(info.getAnnotation(SimpleAnnotation.class.getName()));
      assertFalse(info.isAnnotationPresent(SimpleAnnotation.class.getName()));
      
      ClassInfo componentInfo = (ClassInfo)((ArrayInfo)info).getComponentType();
      AnnotationValue[] annotations = componentInfo.getAnnotations();
      assertEquals(2, annotations.length);
      AnnotationValue anotherAnnotation = getAnnotationCheckTypeAndName(componentInfo, AnotherAnnotation.class.getName());
      AnnotationValue simpleAnnotation = getAnnotationCheckTypeAndName(componentInfo, SimpleAnnotation.class.getName());
      
      HashSet<AnnotationValue> set = new HashSet<AnnotationValue>();
      set.add(anotherAnnotation);
      set.add(simpleAnnotation);
      
      for (int i = 0 ; i < annotations.length ; i++)
      {
         set.remove(annotations[i]);
      }
      
      assertTrue("Not found annotations " + set, set.isEmpty());
   }
   
   public void testConstructorAnnotations() throws Exception
   {
      ClassInfo info = getClassInfo(AnnotatedClass.class);
      
      ConstructorInfo[] constructors = info.getDeclaredConstructors();
      assertEquals(1, constructors.length);

      checkAnnotations(constructors[0], CONSTRUCTOR_DATA);
   }
   
   public void testFieldAnnotations() throws Exception
   {
      ClassInfo info = getClassInfo(AnnotatedClass.class);

      FieldInfo[] fields = info.getDeclaredFields();
      assertEquals(1, fields.length);

      checkAnnotations(fields[0], FIELD_DATA);
   }
   
   public void testMethodAnnotations() throws Exception
   {
      ClassInfo info = getClassInfo(AnnotatedClass.class);
      
      MethodInfo[] methods = info.getDeclaredMethods();
      assertEquals(1, methods.length);

      checkAnnotations(methods[0], METHOD_DATA);
   }
   
   public void testConstructorParameterAnnotations() throws Exception
   {
      ClassInfo info = getClassInfo(AnnotatedClass.class);

      ConstructorInfo[] constructors = info.getDeclaredConstructors();
      assertEquals(1, constructors.length);

      checkParameterAnnotations(constructors[0].getParameters());
   }
   
   public void testMethodParameterAnnotations() throws Exception
   {
      ClassInfo info = getClassInfo(AnnotatedClass.class);

      MethodInfo[] methods = info.getDeclaredMethods();
      assertEquals(1, methods.length);
      
      checkParameterAnnotations(methods[0].getParameters());
   }

   private void checkAnnotations(AnnotatedInfo info, ExpectedComplexAnnotationData expectedComplexAnnotationData)
   {
      AnnotationValue[] annotations = info.getAnnotations();
      checkExpectedAnnotations(annotations, EXPECTED_ANNOTATIONS);
      checkSimpleAnnotation(info);
      checkComplexAnnotation(info, expectedComplexAnnotationData);
   }
   
   private void checkSimpleAnnotation(AnnotatedInfo info)
   {
      AnnotationValue annotation = getAnnotationCheckTypeAndName(info, SimpleAnnotation.class.getName());

      AnnotationValue[] annotationAnns = annotation.getAnnotationType().getAnnotations();
      assertNotNull(annotationAnns);
      checkExpectedAnnotations(annotationAnns, ANNOTATION_EXPECTED_ANNOTATIONS);
   }

   private void checkComplexAnnotation(AnnotatedInfo info, ExpectedComplexAnnotationData expectedComplexAnnotationData)
   {
      AnnotationValue complexAnnotation = getAnnotationCheckTypeAndName(info, ComplexAnnotation.class.getName());
      checkComplexAnnotation(complexAnnotation, expectedComplexAnnotationData);
   }   
   
   private void checkComplexAnnotation(AnnotationValue complexAnnotation, ExpectedComplexAnnotationData expectedComplexAnnotationData)
   {
      AnnotationValue[] annotationAnns = complexAnnotation.getAnnotationType().getAnnotations();
      assertNotNull(annotationAnns);
      checkExpectedAnnotations(annotationAnns, COMPLEXANNOTATION_EXPECTED_ANNOTATIONS);
      
      Value value = complexAnnotation.getValue("stringValue");
      checkStringValue(value, expectedComplexAnnotationData.stringAttribute);
      
      value = complexAnnotation.getValue("intValue");
      checkPrimitiveValue(value, "int", expectedComplexAnnotationData.intAttribute);

      value = complexAnnotation.getValue("annotationValue");
      checkContainedAnnotationValue(value, expectedComplexAnnotationData.containedValueAnnotationString);

      value = complexAnnotation.getValue("clazzValue");
      checkClassValue(value, expectedComplexAnnotationData.classAttribute);
      
      value = complexAnnotation.getValue("enumValue");
      checkEnumValue(value, expectedComplexAnnotationData.enumAttribute);
       
      value = complexAnnotation.getValue("stringArrayValue");
      checkStringArrayValue(value, expectedComplexAnnotationData.stringArrayAttribute);

      value = complexAnnotation.getValue("intArrayValue");
      checkPrimitiveArrayValue(value, "int", expectedComplexAnnotationData.intArrayAttribute);

      value = complexAnnotation.getValue("annotationArrayValue");
      checkContainedAnnotationArrayValue(value, expectedComplexAnnotationData.containedValueAnnotationArrayString);

      value = complexAnnotation.getValue("clazzArrayValue");
      checkClassArrayValue(value, expectedComplexAnnotationData.classArrayAttribute);

      value = complexAnnotation.getValue("enumArrayValue");
      checkEnumArrayValue(value, expectedComplexAnnotationData.enumArrayAttribute);

      AnnotationValue valueAnnotation = ((AnnotationInfo)complexAnnotation.getType()).getAnnotation(ValueAnnotation.class.getName());
      checkStringValue(valueAnnotation.getValue("value"), expectedComplexAnnotationData.annotationValueAnnotationString);
   }

   private void checkStringValue(Value value, String expected)
   {
      assertNotNull(value);
      assertTrue(StringValue.class.isAssignableFrom(value.getClass()));
      StringValue theVal = (StringValue)value;
      assertTrue(theVal.getType().getName().equals(String.class.getName()));
      String val = theVal.getValue();
      assertNotNull(val);
      assertEquals(expected, val);
   }

   private void checkStringArrayValue(Value value, String[] expected)
   {
      Value[] values = getArrayValuesWithCheck(value, expected);
      for (int i = 0 ; i < values.length ; i++)
      {
         checkStringValue(values[i], expected[i]);
      }
   }

   private void checkPrimitiveValue(Value value, String name, Object expected)
   {
      assertNotNull(value);
      assertTrue(PrimitiveValue.class.isAssignableFrom(value.getClass()));
      PrimitiveValue val = (PrimitiveValue)value;
      TypeInfo type = val.getType();
      assertTrue(PrimitiveInfo.class.isAssignableFrom(type.getClass()));
      assertTrue((type).getName().equals(name));
      Object obj;
      try
      {
         obj = type.convertValue(val.getValue());
      }
      catch (Throwable e)
      {
         throw new RuntimeException(e);
      } 
      assertNotNull(obj);
      assertEquals(expected, obj);
   }

   private void checkPrimitiveArrayValue(Value value, String name, Object[] expected)
   {
      Value[] values = getArrayValuesWithCheck(value, expected);
      for (int i = 0 ; i < values.length ; i++)
      {
         checkPrimitiveValue(values[i], name, expected[i]);
      }
   }

   private void checkClassValue(Value value, Class expected)
   {
      assertNotNull(value);
      ClassValue theVal = (ClassValue)value;
      String val = theVal.getValue();
      assertNotNull(val);
      assertEquals(val, expected.getName());
   }

   private void checkClassArrayValue(Value value, Class[] expected)
   {
      Value[] values = getArrayValuesWithCheck(value, expected);
      for (int i = 0 ; i < values.length ; i++)
      {
         checkClassValue(values[i], expected[i]);
      }
   }

   private void checkEnumValue(Value value, String expected)
   {
      assertNotNull(value);
      assertTrue(EnumValue.class.isAssignableFrom(value.getClass()));
      EnumValue theVal = (EnumValue)value;
      TypeInfo info = theVal.getType();
      assertNotNull(info);
      assertEquals(TestEnum.class.getName(), info.getName());
      String val = theVal.getValue();
      assertNotNull(val);
      assertEquals(expected, val);
   }
   
   private void checkEnumArrayValue(Value value, String[] expected)
   {
      Value[] values = getArrayValuesWithCheck(value, expected);
      for (int i = 0 ; i < values.length ; i++)
      {
         checkEnumValue(values[i], expected[i]);
      }
   }

   private void checkContainedAnnotationValue(Value value, String expected)
   {
      assertNotNull(value);
      assertTrue(AnnotationValue.class.isAssignableFrom(value.getClass()));
      AnnotationValue val = (AnnotationValue)value;
      checkValueAnnotation(val, expected);
   }
   
   private void checkValueAnnotation(AnnotationValue val, String expected)
   {
      AnnotationInfo info = val.getAnnotationType();
      assertTrue(info.getName().equals(ValueAnnotation.class.getName()));
      Object obj = val.getValue("value");
      assertNotNull(obj);
      assertTrue(StringValue.class.isAssignableFrom(obj.getClass()));
      checkStringValue((Value)obj, expected);
   }
   
   private void checkContainedAnnotationArrayValue(Value value, String[] expected)
   {
      Value[] values = getArrayValuesWithCheck(value, expected);
      for (int i = 0 ; i < values.length ; i++)
      {
         checkContainedAnnotationValue(values[i], expected[i]);
      }
   }

   private Value[] getArrayValuesWithCheck(Value value, Object[] expected)
   {
      assertNotNull(value);
      assertTrue(ArrayValue.class.isAssignableFrom(value.getClass()));
      ArrayValue array = (ArrayValue)value;
      Value[] values = array.getValues();
      assertNotNull(values);
      assertEquals("Wrong length", expected.length, values.length);
      return values;
   }

   
   private AnnotationValue getAnnotationCheckTypeAndName(AnnotatedInfo info, String name)
   {
      AnnotationValue annotation = info.getAnnotation(name);
      assertTrue(info.isAnnotationPresent(name));
      assertNotNull(annotation);
      assertNotNull(annotation.getAnnotationType());
      assertEquals(name, annotation.getAnnotationType().getName());
      return annotation;
   }
   
   private void checkExpectedAnnotations(AnnotationValue[] values, Class[] expected)
   {
      assertEquals(expected.length, values.length);
      
      for (int i = 0 ; i < expected.length ; i++)
      {
         boolean found = false;
         for (int j = 0 ; j < values.length ; j++)
         {
            if (expected[i].getName().equals(values[j].getAnnotationType().getName()))
            {
               found = true;
               break;
            }
         }
         assertTrue("Found annotation " + expected[i].getName(), found);
      }
   }
   
   protected ClassInfo getClassInfo(Class clazz)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo info = factory.getTypeInfo(clazz);
      assertNotNull(info);
      assertTrue(info instanceof ClassInfo);
      ClassInfo cinfo = (ClassInfo) info;
      getLog().debug(cinfo);
      return cinfo;
   }

   private void checkParameterAnnotations(ParameterInfo[] params)
   {
      assertEquals(2, params.length);
      
      ParameterInfo param = getCheckParameter(params, 0, "int");
      AnnotationValue[] annotations = param.getAnnotations();
      checkExpectedAnnotations(annotations, FIRST_PARAM_EXPECTED_ANNOTATIONS);
      checkValueAnnotation(annotations[0], "int");
      checkComplexAnnotation(annotations[1], PARAM_DATA);
      
      param = getCheckParameter(params, 1, "java.lang.String");
      annotations = param.getAnnotations();
      checkExpectedAnnotations(annotations, SECOND_PARAM_EXPECTED_ANNOTATIONS);
      checkValueAnnotation(annotations[0], "string");
      AnnotationInfo type = annotations[1].getAnnotationType();
      assertEquals(SimpleAnnotation.class.getName(), type.getName());
   }
   
   private ParameterInfo getCheckParameter(ParameterInfo[] params, int index, String typeName)
   {
      ParameterInfo param = params[index];
      TypeInfo info = param.getParameterType();
      assertEquals(typeName, info.getName());
      
      return param;
   }
   
   protected abstract TypeInfoFactory getTypeInfoFactory();
   
   private static class ExpectedComplexAnnotationData
   {
      String annotationValueAnnotationString = "annotation_";

      String stringAttribute;
      Integer intAttribute;
      String containedValueAnnotationString;
      Class classAttribute;
      String enumAttribute;

      String[] stringArrayAttribute;
      Integer[] intArrayAttribute;
      String[] containedValueAnnotationArrayString;
      Class[] classArrayAttribute;
      String[] enumArrayAttribute;

      public ExpectedComplexAnnotationData(
            String stringAttribute, 
            int intAttribute, 
            String containedValueAnnotationString, 
            Class classAttribute, 
            String enumAttribute,
            String[] stringArrayAttribute, 
            Integer[] intArrayAttribute, 
            String[] containedValueAnnotationArrayString, 
            Class[] classArrayAttribute, 
            String[] enumArrayAttribute)
      {
//         this.annotationValueAnnotationString = annotationValueAnnotationString;
         this.containedValueAnnotationString = containedValueAnnotationString;
         this.stringAttribute = stringAttribute;
         this.intAttribute = new Integer(intAttribute);
         this.classAttribute = classAttribute;
         this.enumAttribute = enumAttribute;

         this.stringArrayAttribute = stringArrayAttribute;
         this.intArrayAttribute = intArrayAttribute;
         this.containedValueAnnotationArrayString = containedValueAnnotationArrayString;
         this.classArrayAttribute = classArrayAttribute;
         this.enumArrayAttribute = enumArrayAttribute;
      }
      
   }
   
   
}
