/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.util.HashMap;

import org.jboss.reflect.spi.EnumConstantInfo;
import org.jboss.reflect.spi.EnumInfo;

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
      super(name, modifiers, null, null, null);
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
