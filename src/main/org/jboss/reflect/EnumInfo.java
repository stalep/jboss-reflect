/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;


/**
 * Enumeration info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface EnumInfo extends ClassInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the constants
    * 
    * @return the constants
    */
   EnumConstantInfo[] getEnumConstants();

   /**
    * Get a constant
    * 
    * @param name the name
    * @return the constant
    */
   EnumConstantInfo getEnumConstant(String name);
   
   // Inner classes -------------------------------------------------
}
