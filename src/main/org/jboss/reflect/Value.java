/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * A value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public interface Value
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the type of the value
    * 
    * @return the type
    */
   TypeInfo getType();
   
   // Inner classes -------------------------------------------------
}
