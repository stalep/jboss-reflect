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
package org.jboss.reflect.plugins;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.math.BigInteger;
import java.math.BigDecimal;

import org.jboss.reflect.spi.ProgressionConvertor;

/**
 * Simple progression code.
 * @see javax.management.monitor.GaugeMonitor
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class SimpleProgressionConvertor implements ProgressionConvertor
{

   public boolean canProgress(Class<? extends Object> target, Class<? extends Object> source)
   {
      // see progression comment
      if (target == null || source == null || BigInteger.class == target || BigDecimal.class == target)
      {
         return false;
      }
      // ipnbX = is primitive non boolean X
      boolean ipnbt = target.isPrimitive() && target != Boolean.TYPE;
      boolean ipnbs = source.isPrimitive() && source != Boolean.TYPE;
      return (ipnbt || Number.class.isAssignableFrom(target)) && (ipnbs || Number.class.isAssignableFrom(source));
   }

   public Object doProgression(Class<? extends Object> target, Object value) throws Throwable
   {
      if (value == null || target == value.getClass())
      {
         return value;
      }
      if (canProgress(target, value.getClass()) == false)
      {
         throw new IllegalArgumentException("This convertor only handles Numbers: " + target + "/" + value);
      }

      Number source = (Number) value;
      // set the right value
      if (Byte.class == target || byte.class == target)
      {
         return source.byteValue();
      }
      else if (Double.class == target || double.class == target)
      {
         return source.doubleValue();
      }
      else if (Float.class == target || float.class == target)
      {
         return source.floatValue();
      }
      else if (Integer.class == target || int.class == target)
      {
         return source.intValue();
      }
      else if (Long.class == target || long.class == target)
      {
         return source.longValue();
      }
      else if (Short.class == target || short.class == target)
      {
         return source.shortValue();
      }
      else if (AtomicInteger.class == target)
      {
         return new AtomicInteger(source.intValue());
      }
      else if (AtomicLong.class == target)
      {
         return new AtomicLong(source.longValue());
      }
/*
      // maybe leave this two off - with String constructor
      if (BigInteger.class == target)
      {
         return new BigInteger(source.intValue());
      }
      else if (BigDecimal.class == target)
      {
         return new BigDecimal(source.doubleValue());
      }
*/
      throw new IllegalArgumentException("Unsupported Number subclass: " + target);
   }

}
