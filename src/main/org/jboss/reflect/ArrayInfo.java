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
public class ArrayInfo extends ClassInfo
{
   protected TypeInfo componentType;
   protected int hash = -1;

   public ArrayInfo(TypeInfo componentType)
   {
      super();
      this.componentType = componentType;
      calculateHash();
   }

   public TypeInfo getComponentType()
   {
      return componentType;
   }

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

   public void calculateHash()
   {
      int result = super.hashCode();
      result = 29 * result + componentType.hashCode();
      hash = result;
   }
}
