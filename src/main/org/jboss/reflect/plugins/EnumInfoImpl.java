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

import org.jboss.reflect.spi.EnumConstantInfo;
import org.jboss.reflect.spi.EnumInfo;

import java.util.HashMap;

/**
 * Enumeration info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumInfoImpl extends ClassInfoImpl implements EnumInfo
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3617851958849713457L;
   
   // Attributes ----------------------------------------------------

   /** Enumeration constants */
   protected EnumConstantInfoImpl[] enumConstants;
   
   /** The constants */
   protected HashMap constants = new HashMap();

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new EnumInfo.
    */
   public EnumInfoImpl()
   {
   }

   /**
    * Create a new EnumInfo.
    * 
    * @param name the enumeration name
    * @param modifiers the modifiers
    */
   public EnumInfoImpl(String name, int modifiers)
   {
      super(name, modifiers, null, null);
   }

   // Public --------------------------------------------------------
   
   /**
    * Set the enumeration constants
    * 
    * @param enumConstants the enumeration constants
    */
   public void setEnumConstants(EnumConstantInfoImpl[] enumConstants)
   {
      for (int i = 0; i < enumConstants.length; i++)
         constants.put(enumConstants[i].getName(), enumConstants[i]);
   }

   // EnumInfo implementation ---------------------------------------

   public EnumConstantInfo[] getEnumConstants()
   {
      return enumConstants;
   }

   public EnumConstantInfo getEnumConstant(String name)
   {
      return (EnumConstantInfo) constants.get(name);
   }

   // Object overrides ----------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
