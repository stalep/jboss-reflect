/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class StringValue implements Value
{
   protected String value;
   protected TypeInfo type;

   public StringValue(String value, TypeInfo type)
   {
      this.value = value;
      this.type = type;
   }

   public String getValue()
   {
      return value;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof StringValue)) return false;

      final StringValue primitiveValue = (StringValue) o;

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
}
