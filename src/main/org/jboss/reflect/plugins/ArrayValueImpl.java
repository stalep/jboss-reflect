/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.util.Arrays;

import org.jboss.reflect.ArrayValue;
import org.jboss.reflect.TypeInfo;
import org.jboss.reflect.Value;

/**
 * Annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ArrayValueImpl implements ArrayValue
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
   public ArrayValueImpl()
   {
   }

   /**
    * Create a new ArrayValue
    * 
    * @param type the type
    * @param values the values
    */
   public ArrayValueImpl(TypeInfo type, Value[] values)
   {
      this.type = type;
      this.values = values;
      calculateHash();

   }

   // Public --------------------------------------------------------

   // ArrayValue implementation -------------------------------------

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
      if (!(o instanceof ArrayValueImpl)) return false;

      final ArrayValueImpl arrayValue = (ArrayValueImpl) o;

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
