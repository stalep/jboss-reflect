/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.EnumValue;
import org.jboss.reflect.TypeInfo;

/**
 * An enumeration value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumValueImpl implements EnumValue
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

   // Public --------------------------------------------------------

   // EnumValue implementation --------------------------------------

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
