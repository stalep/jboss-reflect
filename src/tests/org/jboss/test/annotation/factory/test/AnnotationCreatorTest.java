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
package org.jboss.test.annotation.factory.test;

import java.lang.annotation.Annotation;

import org.jboss.annotation.factory.AnnotationCreator;
import org.jboss.annotation.factory.AnnotationValidationException;
import org.jboss.test.ContainerTest;
import org.jboss.test.annotation.factory.support.Complex;
import org.jboss.test.annotation.factory.support.MyEnum;
import org.jboss.test.annotation.factory.support.Simple;
import org.jboss.test.annotation.factory.support.SimpleValue;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public abstract class AnnotationCreatorTest extends ContainerTest
{
   public AnnotationCreatorTest(String name)
   {
      super(name);
   }
   
   public void testSimple() throws Exception
   {
      String expr = "@org.jboss.test.annotation.factory.support.Simple";
      Annotation annotation = (Annotation)AnnotationCreator.createAnnotation(expr, Simple.class);
      assertEquals(Simple.class, annotation.annotationType());
   }
   
   public void testSimpleValue() throws Exception
   {
      String expr = "@org.jboss.test.annotation.factory.support.SimpleValue(\"Test\")";
      Annotation annotation  = (Annotation)AnnotationCreator.createAnnotation(expr, SimpleValue.class);
      assertEquals(SimpleValue.class, annotation.annotationType());
      assertEquals("Test", ((SimpleValue)annotation).value());
   }
   
   public void testComplex() throws Exception
   {
      String expr = "@org.jboss.test.annotation.factory.support.Complex(ch='a', string=\"Test123\", flt=9.9, dbl=123456789.99, shrt=1, lng=987654321, integer=123, bool=true, annotation=@org.jboss.test.annotation.factory.support.SimpleValue(\"Yes\"), array={\"Test\", \"123\"}, clazz=java.lang.Long.class, enumVal=org.jboss.test.annotation.factory.support.MyEnum.TWO)";      
      Annotation annotation  = (Annotation)AnnotationCreator.createAnnotation(expr, Complex.class);
      assertEquals(Complex.class, annotation.annotationType());
      Complex complex = (Complex)annotation;
      assertEquals('a', complex.ch());
      assertEquals("Test123", complex.string());
      assertEquals(9,9, complex.flt());
      assertEquals(123456789.99, complex.dbl());
      assertEquals(1, complex.shrt());
      assertEquals(987654321, complex.lng());
      assertEquals(123, complex.integer());
      assertEquals(true, complex.bool());
      assertEquals(Long.class, complex.clazz());
      assertEquals("Yes", complex.annotation().value());
      assertEquals(new String[]{"Test", "123"}, complex.array());
      assertEquals(MyEnum.TWO, complex.enumVal());
   }
   
   public void testMissingAttributeAndNoDefault() throws Exception
   {
      try
      {
         String expr = "@org.jboss.test.annotation.factory.support.SimpleValue";
         Annotation annotation = (Annotation)AnnotationCreator.createAnnotation(expr, SimpleValue.class);
         fail("Should have picked up on missing attribute");
      }
      catch (AnnotationValidationException expected)
      {
      }
   }

}
