/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.EnumConstantInfo;
import org.jboss.reflect.EnumInfo;

/**
 * An enumeration constant
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumConstantInfoImpl implements EnumConstantInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The constant name */
   protected String name;
   
   /** The enumeration */
   protected EnumInfo declaring;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

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
      calculateHash();
   }

   // Public --------------------------------------------------------

   // EnumContstantInfo implementation ------------------------------

   /**
    * Get the name
    * 
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the declaring enumeration
    * 
    * @return the enumeration
    */
   public EnumInfo getDeclaring()
   {
      return declaring;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof EnumConstantInfoImpl)) return false;

      final EnumConstantInfoImpl enumConstantInfo = (EnumConstantInfoImpl) o;

      if (name != enumConstantInfo.name)
         return false;
      if (!declaring.equals(enumConstantInfo.declaring))
         return false;

      return true;
   }

   public int hashCode() { return hash; }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Calculate the hash code
    */
   protected void calculateHash()
   {
      int result = name.hashCode();
      result = 29 * result + declaring.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
