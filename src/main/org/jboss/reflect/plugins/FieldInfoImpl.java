/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.lang.reflect.Modifier;

import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.FieldInfo;
import org.jboss.reflect.TypeInfo;

/**
 * A field info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class FieldInfoImpl extends AnnotationHolder implements FieldInfo
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3546084661584539959L;
   
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
   public FieldInfoImpl()
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
   public FieldInfoImpl(AnnotationValue[] annotations, String name, TypeInfo type, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      this.type = type;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      calculateHash();
   }

   // Public --------------------------------------------------------

   // FieldInfo implementation --------------------------------------

   public String getName()
   {
      return name;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }
   
   // ModifierInfo implementation -----------------------------------
   
   public int getModifiers()
   {
      return modifiers;
   }
   
   public boolean isStatic()
   {
      return Modifier.isStatic(modifiers);
   }
   
   public boolean isPublic()
   {
      return Modifier.isPublic(modifiers);
   }

   // JBossObject overrides -----------------------------------------
   
   protected void toString(StringBuffer buffer)
   {
      buffer.append("name=").append(name);
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null || obj instanceof FieldInfoImpl == false) 
         return false;

      final FieldInfoImpl other = (FieldInfoImpl) obj;

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
