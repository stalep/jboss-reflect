/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * Enumeration info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumInfo extends ClassInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** Enumeration constants */
   protected EnumConstantInfo[] enumConstants;
   
   /** The constants */
   protected HashMap constants = new HashMap();

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new EnumInfo.
    */
   public EnumInfo()
   {
   }

   /**
    * Create a new EnumInfo.
    * 
    * @param name the enumeration name
    * @param modifiers the modifiers
    */
   public EnumInfo(String name, int modifiers)
   {
      super(name, modifiers, null, null, null);
   }

   // Public --------------------------------------------------------

   /**
    * Get the constants
    * 
    * @return the constants
    */
   public EnumConstantInfo[] getEnumConstants()
   {
      return enumConstants;
   }
   
   /**
    * Set the enumeration constants
    * 
    * @param enumConstants the enumeration constants
    */
   public void setEnumConstants(EnumConstantInfo[] enumConstants)
   {
      for (int i = 0; i < enumConstants.length; i++)
         constants.put(enumConstants[i].getName(), enumConstants[i]);
   }

   /**
    * Get a constant
    * 
    * @param name the name
    * @return the constant
    */
   public EnumConstantInfo getEnumConstant(String name)
   {
      return (EnumConstantInfo)constants.get(name);
   }

   // Object overrides ----------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
