/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

/**
 * A type info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface TypeInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Get the type name
    * 
    * @return the name
    */
   String getName();
   
   /**
    * Get the class
    * 
    * @return the class
    */
   Class getType();
   
   // Inner classes -------------------------------------------------
}
