/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

/**
 * Parameter info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ParameterInfo extends AnnotatedInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the parameter name
    * 
    * @return the parameter name
    */
   String getName();

   /**
    * Get the parameter type
    * 
    * @return the parameter types
    */
   TypeInfo getParameterType();
   
   // Inner classes -------------------------------------------------
}
