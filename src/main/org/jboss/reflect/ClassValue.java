/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * Class value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ClassValue implements Value
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The value */
   protected String value;
   
   /** The type */
   protected TypeInfo type;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new class value
    */
   public ClassValue()
   {
   }

   /**
    * Create a new ClassValue.
    * 
    * @param value the value
    * @param type the type
    */
   public ClassValue(String value, TypeInfo type)
   {
      this.value = value;
      this.type = type;
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
      if (!(o instanceof ClassValue)) return false;

      final ClassValue classValue = (ClassValue) o;

      if (!type.equals(classValue.type)) return false;
      if (!value.equals(classValue.value)) return false;

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
      int result;
      result = value.hashCode();
      result = 29 * result + type.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
