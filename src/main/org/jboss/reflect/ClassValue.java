/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * Annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class ClassValue implements Value
{
   protected String value;
   protected TypeInfo type;
   protected int hash = -1;
   public ClassValue()
   {

   }

   public ClassValue(String value, TypeInfo type)
   {
      this.value = value;
      this.type = type;
      calculateHash();
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
      if (!(o instanceof ClassValue)) return false;

      final ClassValue classValue = (ClassValue) o;

      if (!type.equals(classValue.type)) return false;
      if (!value.equals(classValue.value)) return false;

      return true;
   }

   public int hashCode() { return hash; }

   public void calculateHash()
   {
      int result;
      result = value.hashCode();
      result = 29 * result + type.hashCode();
      hash = result;
   }

}
