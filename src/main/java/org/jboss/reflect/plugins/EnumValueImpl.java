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

import org.jboss.reflect.spi.AbstractValue;
import org.jboss.reflect.spi.EnumValue;
import org.jboss.reflect.spi.TypeInfo;

/**
 * An enumeration value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumValueImpl extends AbstractValue implements EnumValue
{
   /** serialVersionUID */
   private static final long serialVersionUID = 4120848858889662517L;

   /** The type */
   protected TypeInfo type;
   
   /** The value */
   protected String value;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new EnumValue.
    */
   public EnumValueImpl()
   {
   }

   /**
    * Create a new EnumValue.
    * 
    * @param type the type
    * @param value the value
    */
   public EnumValueImpl(TypeInfo type, String value)
   {
      this.type = type;
      this.value = value;
      calculateHash();
   }

   @Override
   public boolean isEnum()
   {
      return true;
   }

   public String getValue()
   {
      return value;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof EnumValueImpl)) return false;

      final EnumValueImpl enumValue = (EnumValueImpl) o;

      if (type != null ? !type.equals(enumValue.type) : enumValue.type != null) return false;
      if (value != null ? !value.equals(enumValue.value) : enumValue.value != null) return false;

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
      int result;
      result = (type != null ? type.hashCode() : 0);
      result = 29 * result + (value != null ? value.hashCode() : 0);
      hash = result;
   }
}
