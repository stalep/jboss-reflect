/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * Method info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class MethodInfo extends AnnotationHolder implements MemberInfo
{
   // Constants -----------------------------------------------------
   
   /** No parameters */
   public static final TypeInfo[] NO_PARAMS = {};
   
   /** No Exceptions */
   public static final ClassInfo[] NO_EXCEPTIONS = {};
   
   // Attributes ----------------------------------------------------

   /** The method name */
   protected String name;
   
   /** The declaring class */
   protected ClassInfo declaringClass;
   
   /** The parameter types */
   protected TypeInfo[] parameterTypes;
   
   /** The exception types */
   protected ClassInfo[] exceptionTypes;
   
   /** The modifiers */
   protected int modifiers;
   
   /** The return type */
   protected TypeInfo returnType;
   
   /** The hash code */
   protected int hash;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new method info
    */
   public MethodInfo()
   {
   }

   /**
    * Create a new MethodInfo.
    * 
    * @param annotations the annotations
    * @param name the method name
    * @param returnType the return type
    * @param parameterTypes the parameter types
    * @param exceptionTypes the exception types
    * @param modifiers the modifiers
    * @param declaring the declaring class
    */
   public MethodInfo(AnnotationValue[] annotations, String name, TypeInfo returnType, TypeInfo[] parameterTypes, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
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
      this.returnType = returnType;
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Get the method name
    * 
    * @return the method name
    */
   public String getName()
   {
      return name;
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

   /**
    * Get the parameter types
    * 
    * @return the parameter types
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
    * Get the method modifiers
    * 
    * @return the modifiers
    */
   public int getModifiers()
   {
      return modifiers;
   }
   
   /**
    * Get the return type
    * 
    * @return the return type
    */
   public TypeInfo getReturnType()
   {
      return returnType;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null || obj instanceof MethodInfo == false)
         return false;

      final MethodInfo other = (MethodInfo) obj;

      if (!declaringClass.equals(other.declaringClass))
         return false;
      if (!name.equals(other.name))
         return false;
      if (parameterTypes != null ? !parameterTypes.equals(other.parameterTypes) : other.parameterTypes != null)
         return false;
      if (!returnType.equals(other.returnType))
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
      if (parameterTypes != null)
      {
         for (int i = 0; i < parameterTypes.length; i++)
            result = 29 * result + parameterTypes[i].hashCode();
      }
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
