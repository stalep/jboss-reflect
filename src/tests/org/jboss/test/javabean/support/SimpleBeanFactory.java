/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
 * Factory for javabean tests.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class SimpleBeanFactory
{
   public static SimpleBean newInstance()
   {
      SimpleBean bean = new SimpleBean();
      bean.setFactoryUsed("SimpleBeanFactory.newInstance()");
      return bean;      
   }
   public static SimpleBean newInstance(Object anObject, String string, Byte byte1,
         Boolean boolean1, Character character, Short short1,
         Integer anInt, Long long1, Float float1, Double double1,
         Date date, BigDecimal bigDecimal, BigInteger bigInteger,
         byte abyte, boolean aboolean, char achar, short ashort,
         int anint2, long along, float afloat, double adouble,
         Number number, String overloadedProperty, String xyz, String abc)
   {
      SimpleBean bean = new SimpleBean(anObject, string, byte1,
            boolean1, character, short1,
            anInt, long1, float1, double1,
            date, bigDecimal, bigInteger,
            abyte, aboolean, achar, ashort,
            anint2, along, afloat, adouble,
            number, overloadedProperty, xyz, abc);
      bean.setFactoryUsed("SimpleBeanFactory.newInstance(<all-fields>)");
      return bean;
   }
}
