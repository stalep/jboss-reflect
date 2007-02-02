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

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.EnumConstantInfo;
import org.jboss.reflect.spi.EnumInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * An enumeration constant
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumConstantInfoImpl extends AnnotationHolder implements EnumConstantInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3761411923568243761L;

   /** The constant name */
   protected String name;
   
   /** The enumeration */
   protected EnumInfo declaring;
   
   /** The hash code */
   protected int hash = -1;

   /**
    * Create a new constant
    */
   public EnumConstantInfoImpl()
   {
   }
      
   /**
    * Create a new constant
    * 
    * @param name the name
    * @param declaring the enumeration
    */
   public EnumConstantInfoImpl(String name, EnumInfo declaring)
   {
      this.name = name;
      this.declaring = declaring;
   }
   
   /**
    * Create a new constant
    * 
    * @param name the name
    * @param declaring the enumeration
    * @param annotations the annotations
    */
   public EnumConstantInfoImpl(String name, EnumInfo declaring, AnnotationValue[] annotations)
   {
      super(annotations);
      this.name = name;
      this.declaring = declaring;
   }

   public String getName()
   {
      return name;
   }

   public EnumInfo getDeclaring()
   {
      return declaring;
   }

   public Object getValue()
   {
      return declaring.getEnumValue(getName());
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (o == null || !(o instanceof EnumConstantInfo)) return false;

      final EnumConstantInfo enumConstantInfo = (EnumConstantInfo) o;

      if (name.equals(enumConstantInfo.getName()) == false)
         return false;
      if (!declaring.equals(enumConstantInfo.getDeclaring()))
         return false;

      return true;
   }

   @Override
   public int getHashCode()
   {
      int result = name.hashCode();
      result = 29 * result + declaring.hashCode();
      return result;
   }

   @Override
   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(name);
   }

   @Override
   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" declaring=").append(declaring);
   }
}
