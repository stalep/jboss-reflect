/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * A field info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class FieldInfo extends AnnotationHolder implements MemberInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The field name */
   protected String name;
   
   /** The field type */
   protected TypeInfo type;
   
   /** The field modifier */
   protected int modifiers;
   
   /** The declaring class */
   protected ClassInfo declaringClass;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new field info
    */
   public FieldInfo()
   {
   }

   /**
    * Create a new FieldInfo.
    * 
    * @param annotations the annotations
    * @param name the name
    * @param type the field type
    * @param modifiers the field modifiers
    * @param declaring the declaring class
    */
   public FieldInfo(AnnotationValue[] annotations, String name, TypeInfo type, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      this.type = type;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Get the name
    * 
    * @return the field name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the field type
    * 
    * @return the field type
    */
   public TypeInfo getType()
   {
      return type;
   }

   /**
    * Get the field modifiers
    * 
    * @return the field modifiers
    */
   public int getModifiers()
   {
      return modifiers;
   }

   /**
    * Get the declaring class
    * 
    * @return the declaring class
    */
   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null || obj instanceof FieldInfo == false) 
         return false;

      final FieldInfo other = (FieldInfo) obj;

      if (!declaringClass.equals(other.declaringClass))
         return false;
      if (!name.equals(other.name))
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
   protected void calculateHash()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + declaringClass.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
