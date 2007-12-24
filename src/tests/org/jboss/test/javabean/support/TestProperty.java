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
package org.jboss.test.javabean.support;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

/**
 * TestStaticMethodInstantiate.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class TestProperty
{
   /** Object */
   private Object anObject;
   
   /** A string */
   private String aString;

   /** Byte */
   private Byte aByte;

   /** Boolean */
   private Boolean aBoolean;

   /** Character */
   private Character aCharacter;

   /** Short */
   private Short aShort;

   /** Int */
   private Integer anInt;

   /** Long */
   private Long aLong;

   /** Float */
   private Float aFloat;

   /** Double */
   private Double aDouble;

   /** Date */
   private Date aDate;

   /** BigDecimal */
   private BigDecimal aBigDecimal;

   /** BigDecimal */
   private BigInteger aBigInteger;

   /** byte */
   private byte abyte;

   /** boolean */
   private boolean aboolean;

   /** char */
   private char achar;

   /** short */
   private short ashort;

   /** int */
   private int anint;

   /** long */
   private long along;

   /** float */
   private float afloat;

   /** double */
   private double adouble;

   /** number */
   private Number aNumber;
   
   /** Overloaded property */
   private String overloadedProperty;
   private String xyz;
   private String abc;

   public Object getAnObject()
   {
      return anObject;
   }

   public void setAnObject(Object object)
   {
      anObject = object;
   }

   public BigDecimal getABigDecimal()
   {
      return aBigDecimal;
   }

   public void setABigDecimal(BigDecimal bigDecimal)
   {
      aBigDecimal = bigDecimal;
   }

   public BigInteger getABigInteger()
   {
      return aBigInteger;
   }

   public void setABigInteger(BigInteger bigInteger)
   {
      aBigInteger = bigInteger;
   }

   public boolean isAboolean()
   {
      return aboolean;
   }

   public void setAboolean(boolean aboolean)
   {
      this.aboolean = aboolean;
   }

   public Boolean getABoolean()
   {
      return aBoolean;
   }

   public void setABoolean(Boolean boolean1)
   {
      aBoolean = boolean1;
   }

   public Number getANumber()
   {
      return aNumber;
   }

   public void setANumber(Number number)
   {
      aNumber = number;
   }

   public byte getAbyte()
   {
      return abyte;
   }

   public void setAbyte(byte abyte)
   {
      this.abyte = abyte;
   }

   public Byte getAByte()
   {
      return aByte;
   }

   public void setAByte(Byte byte1)
   {
      aByte = byte1;
   }

   public char getAchar()
   {
      return achar;
   }

   public void setAchar(char achar)
   {
      this.achar = achar;
   }

   public Character getACharacter()
   {
      return aCharacter;
   }

   public void setACharacter(Character character)
   {
      aCharacter = character;
   }

   public Date getADate()
   {
      return aDate;
   }

   public void setADate(Date date)
   {
      aDate = date;
   }

   public double getAdouble()
   {
      return adouble;
   }

   public void setAdouble(double adouble)
   {
      this.adouble = adouble;
   }

   public Double getADouble()
   {
      return aDouble;
   }

   public void setADouble(Double double1)
   {
      aDouble = double1;
   }

   public float getAfloat()
   {
      return afloat;
   }

   public void setAfloat(float afloat)
   {
      this.afloat = afloat;
   }

   public Float getAFloat()
   {
      return aFloat;
   }

   public void setAFloat(Float float1)
   {
      aFloat = float1;
   }

   public long getAlong()
   {
      return along;
   }

   public void setAlong(long along)
   {
      this.along = along;
   }

   public Long getALong()
   {
      return aLong;
   }

   public void setALong(Long long1)
   {
      aLong = long1;
   }

   public int getAnint()
   {
      return anint;
   }

   public void setAnint(int anint)
   {
      this.anint = anint;
   }

   public Integer getAnInt()
   {
      return anInt;
   }

   public void setAnInt(Integer anInt)
   {
      this.anInt = anInt;
   }

   public short getAshort()
   {
      return ashort;
   }

   public void setAshort(short ashort)
   {
      this.ashort = ashort;
   }

   public Short getAShort()
   {
      return aShort;
   }

   public void setAShort(Short short1)
   {
      aShort = short1;
   }

   public String getAString()
   {
      return aString;
   }

   public void setAString(String string)
   {
      aString = string;
   }
   
   public String getOverloadedProperty()
   {
      return overloadedProperty;
   }
   
   public void setOverloadedProperty(Long broken)
   {
      throw new RuntimeException("Invoked the wrong setter");
   }
   
   public void setOverloadedProperty(String overloadedProperty)
   {
      this.overloadedProperty = overloadedProperty;
   }
   
   public void setOverloadedProperty(Integer broken)
   {
      throw new RuntimeException("Invoked the wrong setter");
   }

   public String getXYZ()
   {
      return xyz;
   }
   public void setXYZ(String xyz)
   {
      this.xyz = xyz;
   }

   public String getAbc()
   {
      return abc;
   }
   public void setAbc(String abc)
   {
      this.abc = abc;
   }
}
