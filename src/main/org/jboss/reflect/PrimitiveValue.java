/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * A primitive value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class PrimitiveValue implements Value
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The value */
   protected String value;
   
   /** The type */
   protected PrimitiveInfo type;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a primitive value
    */
   public PrimitiveValue()
   {
   }

   /**
    * Create a primitive value
    * 
    * @param value the value
    * @param type the type
    */
   public PrimitiveValue(String value, PrimitiveInfo type)
   {
      this.value = value;
      this.type = type;
   }

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
      if (!(o instanceof PrimitiveValue)) return false;

      final PrimitiveValue primitiveValue = (PrimitiveValue) o;

      if (!type.equals(primitiveValue.type)) return false;
      if (!value.equals(primitiveValue.value)) return false;

      return true;
   }

   public int hashCode()
   {
      int result;
      result = value.hashCode();
      result = 29 * result + type.hashCode();
      return result;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
