/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.ArrayInfo;
import org.jboss.reflect.TypeInfo;

/**
 * Array information
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ArrayInfoImpl extends ClassInfoImpl implements ArrayInfo
{
   // Constants -----------------------------------------------------
   
   /** serialVersionUID */
   private static final long serialVersionUID = 3905804162787980599L;
   
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
   public ArrayInfoImpl()
   {
   }

   /**
    * Create a new ArrayInfo.
    * 
    * @param componentType the component type
    */
   public ArrayInfoImpl(TypeInfo componentType)
   {
      this.componentType = componentType;
      calculateHash();
   }

   // Public --------------------------------------------------------

   // ArrayInfo implementation --------------------------------------

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
      if (!(o instanceof ArrayInfoImpl)) return false;
      if (!super.equals(o)) return false;

      final ArrayInfoImpl arrayInfo = (ArrayInfoImpl) o;

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
