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
public interface ConstructorInfo extends AnnotatedInfo, MemberInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the declaring class
    * 
    * @return the class
    */
   ClassInfo getDeclaringClass();

   /**
    * Get the parameter types
    * 
    * @return the parameters types
    */
   TypeInfo[] getParameterTypes();

   /**
    * Get the exception types
    * 
    * @return the exception types
    */
   ClassInfo[] getExceptionTypes();

   /**
    * Get the constructor modifier
    * 
    * @return the constructor modifier
    */
   int getModifiers();
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
