/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * An enumeration value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumValue implements Value
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The type */
   protected TypeInfo type;
   
   /** The value */
   protected String value;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new EnumValue.
    */
   public EnumValue()
   {
   }

   /**
    * Create a new EnumValue.
    * 
    * @param type the type
    * @param value the value
    */
   public EnumValue(TypeInfo type, String value)
   {
      this.type = type;
      this.value = value;
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Get the value
    * 
    * @return the value
    */
   public String getValue()
   {
      return value;
   }

   // Value implementation ------------------------------------------

   public TypeInfo getType()
   {
      return type;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof EnumValue)) return false;

      final EnumValue enumValue = (EnumValue) o;

      if (type != null ? !type.equals(enumValue.type) : enumValue.type != null) return false;
      if (value != null ? !value.equals(enumValue.value) : enumValue.value != null) return false;

      return true;
   }

   public int hashCode()
   {
      return hash;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

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
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
