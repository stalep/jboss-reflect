/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.Arrays;

/**
 * Annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class ArrayValue implements Value
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The type */
   protected TypeInfo type;
   
   /** The values */
   protected Value[] values;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new ArrayValue
    */
   public ArrayValue()
   {
   }

   /**
    * Create a new ArrayValue
    * 
    * @param type the type
    * @param values the values
    */
   public ArrayValue(TypeInfo type, Value[] values)
   {
      this.type = type;
      this.values = values;
      calculateHash();

   }

   // Public --------------------------------------------------------

   /**
    * Get the values
    * 
    * @return the values
    */
   public Value[] getValues()
   {
      return values;
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
      if (!(o instanceof ArrayValue)) return false;

      final ArrayValue arrayValue = (ArrayValue) o;

      if (!type.equals(arrayValue.type)) return false;
      if (!Arrays.equals(values, arrayValue.values)) return false;

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
      // FIXME java5 hash = Arrays.hashCode(values);
      hash = hash * 29 +  type.hashCode();
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
