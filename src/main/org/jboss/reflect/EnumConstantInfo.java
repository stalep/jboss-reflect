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
public class EnumConstantInfo
{
   protected String name;
   protected int ordinal;
   protected EnumInfo declaring;
   protected int hash = -1;

   public EnumConstantInfo(String name, int ordinal, EnumInfo declaring)
   {
      this.name = name;
      this.ordinal = ordinal;
      this.declaring = declaring;
      calculateHash();
   }

   public String getName()
   {
      return name;
   }

   public int getOrdinal()
   {
      return ordinal;
   }

   public EnumInfo getDeclaring()
   {
      return declaring;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof EnumConstantInfo)) return false;

      final EnumConstantInfo enumConstantInfo = (EnumConstantInfo) o;

      if (ordinal != enumConstantInfo.ordinal) return false;
      if (!declaring.equals(enumConstantInfo.declaring)) return false;

      return true;
   }

   public int hashCode() { return hash; }

   public void calculateHash()
   {
      int result;
      result = ordinal;
      result = 29 * result + declaring.hashCode();
      hash = result;
   }


}
