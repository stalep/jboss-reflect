/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.ParameterInfo;
import org.jboss.reflect.TypeInfo;

/**
 * Method info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class MethodInfoImpl extends AnnotationHolder implements MethodInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The method name */
   protected String name;
   
   /** The declaring class */
   protected ClassInfo declaringClass;
   
   /** The parameter types */
   protected TypeInfo[] parameterTypes;
   
   /** The parameters */
   protected ParameterInfo[] parameters;
   
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
   public MethodInfoImpl()
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
   public MethodInfoImpl(AnnotationValue[] annotations, String name, TypeInfo returnType, TypeInfo[] parameterTypes, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      if (parameterTypes == null)
      {
         this.parameterTypes = MethodInfo.NO_PARAMS_TYPES;
         this.parameters = MethodInfo.NO_PARAMS;
      }
      else
      {
         this.parameterTypes = parameterTypes;
         this.parameters = new ParameterInfoImpl[parameterTypes.length];
         for (int i = 0; i < parameterTypes.length; ++i)
            this.parameters[i] = new ParameterInfoImpl(null, null, parameterTypes[i]);
      }
      if (exceptionTypes == null)
         this.exceptionTypes = MethodInfo.NO_EXCEPTIONS;
      else
         this.exceptionTypes = exceptionTypes;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      this.returnType = returnType;
      calculateHash();
   }

   /**
    * Create a new MethodInfo.
    * 
    * @param annotations the annotations
    * @param name the method name
    * @param returnType the return type
    * @param parameters the parameters
    * @param exceptionTypes the exception types
    * @param modifiers the modifiers
    * @param declaring the declaring class
    */
   public MethodInfoImpl(AnnotationValue[] annotations, String name, TypeInfo returnType, ParameterInfo[] parameters, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations);
      this.name = name;
      if (parameters == null || parameters.length == 0)
      {
         this.parameterTypes = MethodInfo.NO_PARAMS_TYPES;
         this.parameters = MethodInfo.NO_PARAMS;
      }
      else
      {
         this.parameters = parameters;
         this.parameterTypes = new TypeInfo[parameters.length];
         for (int i = 0; i < parameters.length; ++i)
            this.parameterTypes[i] = parameters[i].getParameterType();
      }
      if (exceptionTypes == null || exceptionTypes.length == 0)
         this.exceptionTypes = MethodInfo.NO_EXCEPTIONS;
      else
         this.exceptionTypes = exceptionTypes;
      this.modifiers = modifiers;
      this.declaringClass = declaring;
      this.returnType = returnType;
      calculateHash();
   }

   // Public --------------------------------------------------------

   // MethodInfo implementation -------------------------------------

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

   public ParameterInfo[] getParameters()
   {
      return parameters;
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

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj) return true;
      if (obj == null || obj instanceof MethodInfoImpl == false)
         return false;

      final MethodInfoImpl other = (MethodInfoImpl) obj;

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
