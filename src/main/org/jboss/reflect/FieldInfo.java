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
public class FieldInfo extends AnnotationHolder
{
   protected String name;
   protected TypeInfo type;
   protected int modifiers;
   protected ClassInfo declaringClass;
   protected int hash = -1;

   public FieldInfo()
   {
   }

   public FieldInfo(AnnotationValue[] annotations, String name, TypeInfo type, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      this.type = type;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      calculateHash();
   }

   public String getName()
   {
      return name;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public int getModifiers()
   {
      return modifiers;
   }

   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof FieldInfo)) return false;

      final FieldInfo fieldInfo = (FieldInfo) o;

      if (!declaringClass.equals(fieldInfo.declaringClass)) return false;
      if (!name.equals(fieldInfo.name)) return false;

      return true;
   }

   public void calculateHash()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + declaringClass.hashCode();
      hash = result;
   }
}
