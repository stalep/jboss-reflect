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
public interface MethodInfo extends AnnotatedInfo, MemberInfo
{
   // Constants -----------------------------------------------------
   
   /** No parameters */
   public static final TypeInfo[] NO_PARAMS = {};
   
   /** No Exceptions */
   public static final ClassInfo[] NO_EXCEPTIONS = {};

   // Public --------------------------------------------------------

   /**
    * Get the method name
    * 
    * @return the method name
    */
   String getName();

   /**
    * Get the declaring class
    * 
    * @return the declaring class
    */
   ClassInfo getDeclaringClass();

   /**
    * Get the parameter types
    * 
    * @return the parameter types
    */
   TypeInfo[] getParameterTypes();

   /**
    * Get the exception types
    * 
    * @return the exception types
    */
   ClassInfo[] getExceptionTypes();

   /**
    * Get the method modifiers
    * 
    * @return the modifiers
    */
   int getModifiers();
   
   /**
    * Get the return type
    * 
    * @return the return type
    */
   TypeInfo getReturnType();
   
   // Inner classes -------------------------------------------------
}
