/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

/**
 * An annotation attribute
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface AnnotationAttribute
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the attribute name
    * 
    * @return the attribute name
    */
   String getName();

   /**
    * Get the attribute type
    * 
    * @return the attribute type
    */
   TypeInfo getType();

   /**
    * Get the default value
    * 
    * @return the default value
    */
   Value getDefaultValue();
   
   // Inner classes -------------------------------------------------
}
