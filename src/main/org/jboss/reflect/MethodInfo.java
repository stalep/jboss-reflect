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
public class MethodInfo extends AnnotationHolder
{
   protected String name;
   protected ClassInfo declaringClass;
   protected TypeInfo[] parameterTypes;
   protected ClassInfo[] exceptionTypes;
   protected int modifiers;
   protected TypeInfo returnType;
   protected int hash;

   public MethodInfo()
   {
   }

   public MethodInfo(AnnotationValue[] annotations, String name, TypeInfo returnType, TypeInfo[] parameterTypes, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      this.parameterTypes = parameterTypes;
      this.exceptionTypes = exceptionTypes;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      this.returnType = returnType;
      calculateHash();
   }

   public String getName()
   {
      return name;
   }

   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }

   public TypeInfo[] getParameterTypes()
   {
      return parameterTypes;
   }

   public ClassInfo[] getExceptionTypes()
   {
      return exceptionTypes;
   }

   public int getModifiers()
   {
      return modifiers;
   }

   public TypeInfo getReturnType()
   {
      return returnType;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof MethodInfo)) return false;

      final MethodInfo methodInfo = (MethodInfo) o;

      if (!declaringClass.equals(methodInfo.declaringClass)) return false;
      if (!name.equals(methodInfo.name)) return false;
      if (parameterTypes != null ? !parameterTypes.equals(methodInfo.parameterTypes) : methodInfo.parameterTypes != null) return false;
      if (!returnType.equals(methodInfo.returnType)) return false;

      return true;
   }

   public int hashCode() { return hash; }

   public void  calculateHash()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + declaringClass.hashCode();
      if (parameterTypes != null)
      {
         for (int i = 0; i < parameterTypes.length; i++)
         {
            result = 29 * result + parameterTypes[i].hashCode();
         }
      }
      hash = result;
   }


}
