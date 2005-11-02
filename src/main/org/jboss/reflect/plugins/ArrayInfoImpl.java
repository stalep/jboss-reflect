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

import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Array information
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ArrayInfoImpl extends ClassInfoImpl implements ArrayInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3905804162787980599L;

   /** The component type */
   protected TypeInfo componentType;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new ArrayInfo.
    */
   public ArrayInfoImpl()
   {
   }

   /**
    * Create a new ArrayInfo.
    * 
    * @param componentType the component type
    */
   public ArrayInfoImpl(TypeInfo componentType)
   {
      this.componentType = componentType;
      calculateHash();
   }

   public TypeInfo getComponentType()
   {
      return componentType;
   }

   public String getName()
   {
      return componentType.getName() + "[]";
   }
   
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ArrayInfoImpl)) return false;
      if (!super.equals(o)) return false;

      final ArrayInfoImpl arrayInfo = (ArrayInfoImpl) o;

      if (!componentType.equals(arrayInfo.componentType)) return false;

      return true;
   }

   public int hashCode() { return hash; }

   /**
    * Calculate the hash code
    */
   protected void calculateHash()
   {
      int result = super.hashCode();
      result = 29 * result + componentType.hashCode();
      hash = result;
   }
}
