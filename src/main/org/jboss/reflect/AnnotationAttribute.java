/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * An annotation attribute
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class AnnotationAttribute
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The name */
   protected String name;
   
   /** The attribute type */
   protected TypeInfo type;
   
   /** The default value */
   protected Value defaultValue;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new annotation attribute
    */
   public AnnotationAttribute()
   {
   }

   /**
    * Create a new AnnotationAttribute.
    * 
    * @param name the annotation name
    * @param type the attribute type
    * @param defaultValue the default value
    */
   public AnnotationAttribute(String name, TypeInfo type, Value defaultValue)
   {
      this.name = name;
      this.type = type;
      this.defaultValue = defaultValue;
      calcHashCode();
   }

   // Public --------------------------------------------------------

   /**
    * Get the attribute name
    * 
    * @return the attribute name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the attribute type
    * 
    * @return the attribute type
    */
   public TypeInfo getType()
   {
      return type;
   }

   /**
    * Get the default value
    * 
    * @return the default value
    */
   public Value getDefaultValue()
   {
      return defaultValue;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof AnnotationAttribute == false)
         return false;

      final AnnotationAttribute other = (AnnotationAttribute) obj;

      if (!name.equals(other.name))
         return false;
      if (!type.equals(other.type))
         return false;

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
   protected void calcHashCode()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + type.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
