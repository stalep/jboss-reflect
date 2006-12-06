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
package org.jboss.reflect.plugins;

import java.io.Serializable;
import java.util.Arrays;

import org.jboss.reflect.spi.ArrayValue;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.Value;
import org.jboss.util.JBossObject;

/**
 * Annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ArrayValueImpl extends JBossObject implements ArrayValue, Serializable
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3979266949899367475L;

   /** The type */
   protected TypeInfo type;
   
   /** The values */
   protected Value[] values;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new ArrayValue
    */
   public ArrayValueImpl()
   {
   }

   /**
    * Create a new ArrayValue
    * 
    * @param type the type
    * @param values the values
    */
   public ArrayValueImpl(TypeInfo type, Value[] values)
   {
      this.type = type;
      this.values = values;
      calculateHash();

   }

   public Value[] getValues()
   {
      return values;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ArrayValueImpl)) return false;

      final ArrayValueImpl arrayValue = (ArrayValueImpl) o;

      if (!type.equals(arrayValue.type)) return false;
      if (!Arrays.equals(values, arrayValue.values)) return false;

      return true;
   }

   public int hashCode()
   {
      return hash;
   }

   /**
    * Calculate the hash code
    */
   protected void calculateHash()
   {
      hash = Arrays.hashCode(values);
   }
}
