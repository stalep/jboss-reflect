/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * Array information
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ArrayInfo extends ClassInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The component type */
   protected TypeInfo componentType;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new ArrayInfo.
    */
   public ArrayInfo()
   {
   }

   /**
    * Create a new ArrayInfo.
    * 
    * @param componentType the component type
    */
   public ArrayInfo(TypeInfo componentType)
   {
      this.componentType = componentType;
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Get the component type
    * 
    * @return the component type
    */
   public TypeInfo getComponentType()
   {
      return componentType;
   }

   // TypeInfo implementation ---------------------------------------

   public String getName()
   {
      return componentType.getName() + "[]";
   }
   
   
   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ArrayInfo)) return false;
      if (!super.equals(o)) return false;

      final ArrayInfo arrayInfo = (ArrayInfo) o;

      if (!componentType.equals(arrayInfo.componentType)) return false;

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
      int result = super.hashCode();
      result = 29 * result + componentType.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
