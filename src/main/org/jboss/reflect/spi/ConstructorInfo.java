/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

import java.lang.reflect.Constructor;

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
    * Get the constructor
    * 
    * @return the constructor
    */
   Constructor getConstructor();
   
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
    * Get the parameters
    * 
    * @return the parameters
    */
   ParameterInfo[] getParameters();

   /**
    * Get the exception types
    * 
    * @return the exception types
    */
   ClassInfo[] getExceptionTypes();
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
