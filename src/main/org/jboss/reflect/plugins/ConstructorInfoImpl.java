/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Arrays;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Constructor info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ConstructorInfoImpl extends AnnotationHolder implements ConstructorInfo
{
   // Constants -----------------------------------------------------
   
   /** serialVersionUID */
   private static final long serialVersionUID = 3256727273163272758L;
   
   // Attributes ----------------------------------------------------

   /** The constructor */
   protected Constructor constructor;
   
   /** The declring class */
   protected ClassInfo declaringClass;
   
   /** The parameter types */
   protected TypeInfo[] parameterTypes;
   
   /** The parameters */
   protected ParameterInfo[] parameters;
   
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
      calculateHash();
   }

   /**
    * Create a new ConstructorInfo.
    * 
    * @param annotations the annotations
    * @param parameters the parameters
    * @param exceptionTypes the exception types
    * @param modifiers the modifiers
    * @param declaring the declaring class
    */
   public ConstructorInfoImpl(AnnotationValue[] annotations, ParameterInfo[] parameters, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations);
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
      calculateHash();
   }

   // Public --------------------------------------------------------

   /**
    * Set the constructor
    * 
    * @param constructor the constructor
    */
   public void setConstructor(Constructor constructor)
   {
      this.constructor = constructor;
   }
   
   // ConstructorInfo implementation --------------------------------

   public Constructor getConstructor()
   {
      return constructor;
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
      buffer.append(Arrays.asList(parameterTypes));
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj) 
         return true;
      if (obj == null || obj instanceof ConstructorInfoImpl == false)
         return false;

      final ConstructorInfoImpl other = (ConstructorInfoImpl) obj;
      
      if (declaringClass.equals(other.declaringClass) == false)
         return false;
      if (Arrays.equals(parameterTypes, other.parameterTypes) == false)
         return false;
      
      return true;
   }

   public int hashCode()
   {
      return hash;
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   protected void calculateHash()
   {
      int result = declaringClass.hashCode();
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
