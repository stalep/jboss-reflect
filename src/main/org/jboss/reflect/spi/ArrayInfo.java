/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

/**
 * Array information
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ArrayInfo extends ClassInfo
{
   // Constants -----------------------------------------------------

   // Static --------------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the component type
    * 
    * @return the component type
    */
   TypeInfo getComponentType();
   
   // Inner classes -------------------------------------------------
}
