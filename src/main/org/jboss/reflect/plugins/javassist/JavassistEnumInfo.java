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
package org.jboss.reflect.plugins.javassist;

import java.util.HashMap;

import javassist.CtClass;

import org.jboss.reflect.plugins.EnumConstantInfoImpl;
import org.jboss.reflect.spi.EnumConstantInfo;
import org.jboss.reflect.spi.EnumInfo;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class JavassistEnumInfo extends JavassistTypeInfo implements EnumInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3546645408219542832L;
   
   /** Enumeration constants */
   protected EnumConstantInfoImpl[] enumConstants;
   
   /** The constants */
   protected HashMap<String, EnumConstantInfo> constants = new HashMap<String, EnumConstantInfo>();

   /**
    * Create a new JavassistEnumInfo.
    * 
    * @param factory the factory
    * @param ctClass the ctClass
    * @param clazz the class
    */
   public JavassistEnumInfo(JavassistTypeInfoFactoryImpl factory, CtClass ctClass, Class<? extends Object> clazz)
   {
      super(factory, ctClass, clazz);
   }
   
   /**
    * Set the enumeration constants
    * 
    * @param enumConstants the enumeration constants
    */
   public void setEnumConstants(EnumConstantInfoImpl[] enumConstants)
   {
      for (int i = 0; i < enumConstants.length; i++)
         constants.put(enumConstants[i].getName(), enumConstants[i]);
      this.enumConstants = enumConstants;
   }

   public EnumConstantInfo[] getEnumConstants()
   {
      return enumConstants;
   }

   public EnumConstantInfo getEnumConstant(String name)
   {
      return constants.get(name);
   }

   @SuppressWarnings("unchecked")
   public Object getEnumValue(String name)
   {
      return Enum.valueOf((Class<Enum>) getType(), name);
   }

   @Override
   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (o == null || !(o instanceof EnumInfo)) return false;
      if (!super.equals(o)) return false;
      
      final EnumInfo enumInfo = (EnumInfo) o;

      if (!getName().equals(enumInfo.getName())) return false;

      return true;
   }

   @Override
   public int hashCode()
   {
      return getName().hashCode();
   }
   
}
