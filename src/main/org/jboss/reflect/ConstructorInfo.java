/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * Constructor info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ConstructorInfo extends AnnotationHolder implements MemberInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The declring class */
   protected ClassInfo declaringClass;
   
   /** The parameter types */
   protected TypeInfo[] parameterTypes;
   
   /** The exception types */
   protected ClassInfo[] exceptionTypes;
   
   /** The modifiers */
   protected int modifiers;
   
   /** The hash code */
   protected int hash;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new ConstructorInfo.
    */   
   public ConstructorInfo()
   {
   }

   /**
    * Create a new ConstructorInfo.
    * 
    * @param annotations the annotations
    * @param parameterTypes the parameter types
    * @param exceptionTypes the exception types
    * @param modifiers the modifiers
    * @param declaring the declaring class
    */
   public ConstructorInfo(AnnotationValue[] annotations, TypeInfo[] parameterTypes, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      if (parameterTypes == null) 
         this.parameterTypes = MethodInfo.NO_PARAMS;
      else 
         this.parameterTypes = parameterTypes;
      if (exceptionTypes == null)
         this.exceptionTypes = MethodInfo.NO_EXCEPTIONS;
      else
         this.exceptionTypes = exceptionTypes;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Get the declaring class
    * 
    * @return the class
    */
   public ClassInfo getDeclaringClass()
   {
      return declaringClass;
   }

   /**
    * Get the parameter types
    * 
    * @return the parameters types
    */
   public TypeInfo[] getParameterTypes()
   {
      return parameterTypes;
   }

   /**
    * Get the exception types
    * 
    * @return the exception types
    */
   public ClassInfo[] getExceptionTypes()
   {
      return exceptionTypes;
   }

   /**
    * Get the constructor modifier
    * 
    * @return the constructor modifier
    */
   public int getModifiers()
   {
      return modifiers;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ConstructorInfo)) return false;

      final ConstructorInfo constructorInfo = (ConstructorInfo) o;

      if (!declaringClass.equals(constructorInfo.declaringClass)) return false;
      if (parameterTypes != null ? !parameterTypes.equals(constructorInfo.parameterTypes) : constructorInfo.parameterTypes != null) return false;

      return true;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   protected void calculateHash()
   {
      int result;
      result = declaringClass.hashCode();
      if (parameterTypes != null)
      {
         for (int i = 0; i < parameterTypes.length; i++)
         {
            result = 29 * result + parameterTypes[i].hashCode();
         }
      }
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
