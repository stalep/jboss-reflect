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
public class EnumValue implements Value
{
   protected TypeInfo type;
   protected String value;
   protected int hash = -1;

   public EnumValue(TypeInfo type, String value)
   {
      this.type = type;
      this.value = value;
      calculateHash();
   }

   public TypeInfo getType()
   {
      return type;
   }

   public String getValue()
   {
      return value;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof EnumValue)) return false;

      final EnumValue enumValue = (EnumValue) o;

      if (type != null ? !type.equals(enumValue.type) : enumValue.type != null) return false;
      if (value != null ? !value.equals(enumValue.value) : enumValue.value != null) return false;

      return true;
   }

   public int hashCode() { return hash; }

   public void calculateHash()
   {
      int result;
      result = (type != null ? type.hashCode() : 0);
      result = 29 * result + (value != null ? value.hashCode() : 0);
      hash = result;
   }

}
