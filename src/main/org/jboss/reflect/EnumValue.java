/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * An enumeration value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface EnumValue extends Value
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the value
    * 
    * @return the value
    */
   String getValue();
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
