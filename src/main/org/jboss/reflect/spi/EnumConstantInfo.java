/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

/**
 * An enumeration constant
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface EnumConstantInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the declaring enumeration
    * 
    * @return the enumeration
    */
   EnumInfo getDeclaring();
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
