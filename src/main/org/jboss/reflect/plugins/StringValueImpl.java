/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.io.Serializable;

import org.jboss.reflect.StringValue;
import org.jboss.reflect.TypeInfo;
import org.jboss.util.JBossObject;

/**
 * A string value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class StringValueImpl extends JBossObject implements StringValue, Serializable
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3977862864859836468L;
   
   // Attributes ----------------------------------------------------

   /** The value */
   protected String value;
   
   /** The type */
   protected TypeInfo type;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new string value
    */
   public StringValueImpl()
   {
   }
   
   /**
    * Create a new string value
    * 
    * @param value the value
    * @param type the type
    */
   public StringValueImpl(String value, TypeInfo type)
   {
      this.value = value;
      this.type = type;
   }

   // Public --------------------------------------------------------

   // StringValue implementation ------------------------------------

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
      if (!(o instanceof StringValueImpl)) return false;

      final StringValueImpl primitiveValue = (StringValueImpl) o;

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
