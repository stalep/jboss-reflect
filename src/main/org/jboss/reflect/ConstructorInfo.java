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
public class ConstructorInfo extends AnnotationHolder
{
   protected ClassInfo declaringClass;
   protected TypeInfo parameterTypes;
   protected ClassInfo exceptionTypes;
   protected int modifiers;
   protected int hash;

   public ConstructorInfo()
   {
   }

   public ConstructorInfo(AnnotationValue[] annotations, ClassInfo declaringClass, TypeInfo parameterTypes, ClassInfo exceptionTypes, int modifiers)
   {
      super(annotations);
      this.declaringClass = declaringClass;
      this.parameterTypes = parameterTypes;
      this.exceptionTypes = exceptionTypes;
      this.modifiers = modifiers;
      calculateHash();
   }

   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }

   public TypeInfo getParameterTypes()
   {
      return parameterTypes;
   }

   public ClassInfo getExceptionTypes()
   {
      return exceptionTypes;
   }

   public int getModifiers()
   {
      return modifiers;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ConstructorInfo)) return false;

      final ConstructorInfo constructorInfo = (ConstructorInfo) o;

      if (!declaringClass.equals(constructorInfo.declaringClass)) return false;
      if (parameterTypes != null ? !parameterTypes.equals(constructorInfo.parameterTypes) : constructorInfo.parameterTypes != null) return false;

      return true;
   }

   public void calculateHash()
   {
      int result;
      result = declaringClass.hashCode();
      result = 29 * result + (parameterTypes != null ? parameterTypes.hashCode() : 0);
      hash = result;
   }

}
