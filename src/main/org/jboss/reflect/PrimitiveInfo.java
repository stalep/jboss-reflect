/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.util.HashMap;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class PrimitiveInfo implements TypeInfo, Serializable
{
   protected final transient String name;
   protected final int ordinal;

   protected PrimitiveInfo(String name, int ordinal)
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
      if (!(o instanceof PrimitiveInfo)) return false;
      if (!o.getClass().equals(this.getClass())) return false;
      PrimitiveInfo en = (PrimitiveInfo)o;
      return en.ordinal == this.ordinal;
   }

   public int hashCode()
   {
      return name.hashCode();
   }


   public static final PrimitiveInfo BOOLEAN = new PrimitiveInfo("boolean", 0);
   public static final PrimitiveInfo BYTE = new PrimitiveInfo("byte", 1);
   public static final PrimitiveInfo CHAR = new PrimitiveInfo("char", 2);
   public static final PrimitiveInfo DOUBLE = new PrimitiveInfo("double", 3);
   public static final PrimitiveInfo FLOAT = new PrimitiveInfo("float", 4);
   public static final PrimitiveInfo INT = new PrimitiveInfo("int", 5);
   public static final PrimitiveInfo LONG = new PrimitiveInfo("long", 6);
   public static final PrimitiveInfo SHORT = new PrimitiveInfo("short", 7);
   public static final PrimitiveInfo VOID = new PrimitiveInfo("void", 8);
   private static final PrimitiveInfo[] values = {BOOLEAN, BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, VOID};
   private static HashMap map = new HashMap();

   static
   {
      map.put("boolean", BOOLEAN);
      map.put("byte", BYTE);
      map.put("char", CHAR);
      map.put("double", DOUBLE);
      map.put("float", FLOAT);
      map.put("int", INT);
      map.put("long", LONG);
      map.put("short", SHORT);
      map.put("void", VOID);

   }

   public static PrimitiveInfo valueOf(String name)
   {
      return (PrimitiveInfo)map.get(name);
   }

   Object readResolve() throws ObjectStreamException
   {
      return values[ordinal];
   }
}
