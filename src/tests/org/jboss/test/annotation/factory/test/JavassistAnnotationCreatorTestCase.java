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

import junit.framework.Test;

import org.jboss.annotation.factory.AnnotationCreator;
import org.jboss.test.annotation.factory.support.ComplexWithDefault;
import org.jboss.test.annotation.factory.support.MyEnum;


/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class JavassistAnnotationCreatorTestCase extends AnnotationCreatorTest
{
   public static Test suite()
   {
      return suite(JavassistAnnotationCreatorTestCase.class);
   }
   
   public JavassistAnnotationCreatorTestCase(String name)
   {
      super(name);
   }
   
   public void testDefaultValues() throws Exception
   {
      String expr = "@org.jboss.test.annotation.factory.support.ComplexWithDefault";
      Annotation annotation = (Annotation)AnnotationCreator.createAnnotation(expr, ComplexWithDefault.class);
      assertEquals(ComplexWithDefault.class, annotation.annotationType());
      ComplexWithDefault complex = (ComplexWithDefault)annotation;
      assertEquals('d', complex.ch());
      assertEquals("default", complex.string());
      assertEquals(1.0, complex.flt());
      assertEquals(2.3, complex.dbl());
      assertEquals(2, complex.shrt());
      assertEquals(123456789, complex.lng());
      assertEquals(123, complex.integer());
      assertEquals(true, complex.bool());
      assertEquals(String.class, complex.clazz());
      assertEquals(MyEnum.ONE, complex.enumVal());
      assertEquals("default", complex.annotation().value());
      assertEquals(new String[]{"The", "defaults"}, complex.array());

      int[] expectedIntArray = new int[] {1,2,3};
      int[] actualIntArray = complex.intArray();
      assertEquals(expectedIntArray.length, actualIntArray.length);
      for (int i = 0 ; i < expectedIntArray.length ; i++)
      {
         assertEquals(expectedIntArray[i], actualIntArray[i]);
      }
   }
   
}
