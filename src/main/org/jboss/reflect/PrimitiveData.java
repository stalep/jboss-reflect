/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.io.ObjectStreamException;
import java.io.Serializable;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class PrimitiveData implements TypeData, Serializable
{
   protected final transient String name;
   protected final int ordinal;

   protected PrimitiveData(String name, int ordinal)
   {
      this.name = name;
      this.ordinal = ordinal;
   }
   public String toString()
   {
      return name;
   }

   public String name()
   {
      return name;
   }

   public int ordinal()
   {
      return ordinal;
   }

   public boolean equals(Object o)
   {
      if (o == this) return true;
      if (o == null) return false;
      if (!(o instanceof PrimitiveData)) return false;
      if (!o.getClass().equals(this.getClass())) return false;
      PrimitiveData en = (PrimitiveData)o;
      return en.ordinal == this.ordinal;
   }

   public int hashCode()
   {
      return name.hashCode();
   }


   public static final PrimitiveData BOOLEAN = new PrimitiveData("boolean", 0);
   public static final PrimitiveData BYTE = new PrimitiveData("byte", 1);
   public static final PrimitiveData CHAR = new PrimitiveData("char", 2);
   public static final PrimitiveData DOUBLE = new PrimitiveData("double", 3);
   public static final PrimitiveData FLOAT = new PrimitiveData("float", 4);
   public static final PrimitiveData INT = new PrimitiveData("int", 5);
   public static final PrimitiveData LONG = new PrimitiveData("long", 6);
   public static final PrimitiveData SHORT = new PrimitiveData("short", 7);
   public static final PrimitiveData VOID = new PrimitiveData("void", 8);
   private static final PrimitiveData[] values = {BOOLEAN, BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, VOID};

   Object readResolve() throws ObjectStreamException
   {
      return values[ordinal];
   }
}
