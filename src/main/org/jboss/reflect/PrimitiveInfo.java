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
 * Primitive info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class PrimitiveInfo implements TypeInfo, Serializable
{
   // Constants -----------------------------------------------------

   /** The boolean info */
   public static final PrimitiveInfo BOOLEAN = new PrimitiveInfo("boolean", 0);

   /** The byte info */
   public static final PrimitiveInfo BYTE = new PrimitiveInfo("byte", 1);

   /** The char info */
   public static final PrimitiveInfo CHAR = new PrimitiveInfo("char", 2);

   /** The double info */
   public static final PrimitiveInfo DOUBLE = new PrimitiveInfo("double", 3);

   /** The float info */
   public static final PrimitiveInfo FLOAT = new PrimitiveInfo("float", 4);

   /** The int info */
   public static final PrimitiveInfo INT = new PrimitiveInfo("int", 5);

   /** The long info */
   public static final PrimitiveInfo LONG = new PrimitiveInfo("long", 6);

   /** The short info */
   public static final PrimitiveInfo SHORT = new PrimitiveInfo("short", 7);

   /** The void info */
   public static final PrimitiveInfo VOID = new PrimitiveInfo("void", 8);

   /** The primitives */
   private static final PrimitiveInfo[] values = 
   {
      BOOLEAN, BYTE, CHAR, DOUBLE, FLOAT, INT, LONG, SHORT, VOID
   };
   
   // Attributes ----------------------------------------------------

   /** The name */
   protected final transient String name;
   
   /** The ordinal */
   protected final int ordinal;

   // Static --------------------------------------------------------
   
   /** The primitives */
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

   /**
    *  Get the primitive info for a type
    * 
    * @param name the name
    * @return the info
    */
   public static PrimitiveInfo valueOf(String name)
   {
      return (PrimitiveInfo)map.get(name);
   }
   
   // Constructors --------------------------------------------------

   protected PrimitiveInfo(String name, int ordinal)
   {
      this.name = name;
      this.ordinal = ordinal;
   }

   // Public --------------------------------------------------------

   /**
    * Get the name
    * 
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the ordinal
    * 
    * @return the oridinal
    */
   public int ordinal()
   {
      return ordinal;
   }

   // Object overrides ----------------------------------------------

   public String toString()
   {
      return name;
   }

   public boolean equals(Object obj)
   {
      if (obj == this) return true;
      if (obj == null) return false;
      if (!(obj instanceof PrimitiveInfo)) return false;
      if (!obj.getClass().equals(this.getClass())) return false;
      PrimitiveInfo other = (PrimitiveInfo) obj;
      return other.ordinal == this.ordinal;
   }

   public int hashCode()
   {
      return name.hashCode();
   }


   // Serializable implementation -----------------------------------

   Object readResolve() throws ObjectStreamException
   {
      return values[ordinal];
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
