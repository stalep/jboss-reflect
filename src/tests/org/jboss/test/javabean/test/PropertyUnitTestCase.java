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
package org.jboss.test.javabean.test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.Test;

import org.jboss.test.javabean.support.TestProperty;

/**
 * PropertyInstantiateUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class PropertyUnitTestCase extends AbstractJavaBeanTest
{
   private static DateFormat dateFormat = new SimpleDateFormat("MMM d HH:mm:ss z yyyy");

   String stringValue =  "StringValue";
   Byte byteValue = new Byte("12");
   Boolean booleanValue = Boolean.TRUE;
   // TODO character
   // Character characterValue = new Character('a');
   Short shortValue = new Short("123");
   Integer integerValue = new Integer("1234");
   Long longValue = new Long("12345");
   Float floatValue = new Float("3.14");
   Double doubleValue = new Double("3.14e12");
//   Date dateValue = createDate("Mon Jan 01 00:00:00 CET 2001");
//   Date dateValue = createDate("Jan 01 00:00:00 CET 2001");
   BigDecimal bigDecimalValue = new BigDecimal("12e4");
   BigInteger bigIntegerValue = new BigInteger("123456");

   public static Test suite()
   {
      return suite(PropertyUnitTestCase.class);
   }
   
   public PropertyUnitTestCase(String name)
   {
      super(name);
   }

   public void testProperty() throws Exception
   {
      TestProperty bean = unmarshalJavaBean(TestProperty.class);

      assertEquals(stringValue, bean.getAString());
      assertEquals(byteValue, bean.getAByte());
      assertEquals(booleanValue, bean.getABoolean());
      // TODO character
      // assertEquals(characterValue, bean.getACharacter());
      assertEquals(shortValue, bean.getAShort());
      assertEquals(integerValue, bean.getAnInt());
      assertEquals(longValue, bean.getALong());
      assertEquals(floatValue, bean.getAFloat());
      assertEquals(doubleValue, bean.getADouble());
      // TODO assertEquals(dateValue, bean.getADate());
      assertEquals(bigDecimalValue, bean.getABigDecimal());
      assertEquals(bigIntegerValue, bean.getABigInteger());
      assertEquals(byteValue.byteValue(), bean.getAbyte());
      assertEquals(booleanValue.booleanValue(), bean.isAboolean());
      // TODO character
      // assertEquals(characterValue.charValue(), bean.getAchar());
      assertEquals(shortValue.shortValue(), bean.getAshort());
      assertEquals(integerValue.intValue(), bean.getAnint());
      assertEquals(longValue.longValue(), bean.getAlong());
      assertEquals(floatValue.floatValue(), bean.getAfloat());
      assertEquals(doubleValue.doubleValue(), bean.getAdouble());
      Number number = bean.getANumber();
      assertEquals(Long.class, number.getClass());
      assertEquals(longValue, number);
      assertEquals(stringValue, bean.getOverloadedProperty());
      // An all uppercase property
      assertEquals("XYZ", bean.getXYZ());
      assertEquals("abc", bean.getAbc());      
   }

   protected Date createDate(String date)
   {
      try
      {
         return dateFormat.parse(date);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
}
