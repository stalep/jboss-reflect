/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.ConstructorInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.TypeInfo;

/**
 * Constructor info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ConstructorInfoImpl extends AnnotationHolder implements ConstructorInfo
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
   public ConstructorInfoImpl()
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
   public ConstructorInfoImpl(AnnotationValue[] annotations, TypeInfo[] parameterTypes, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
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

   // ConstructorInfo implementation --------------------------------

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
      if (!(o instanceof ConstructorInfoImpl)) return false;

      final ConstructorInfoImpl constructorInfo = (ConstructorInfoImpl) o;

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
