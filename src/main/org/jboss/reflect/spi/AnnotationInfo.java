/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;


/**
 * Annotation Info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface AnnotationInfo extends InterfaceInfo, ModifierInfo
{
   // Constants -----------------------------------------------------

   // Static --------------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the attributes
    * 
    * @return the attributes
    */
   AnnotationAttribute[] getAttributes();

   /**
    * Get an attribute
    * 
    * @param name the name of the attribute
    * @return the attribute
    */
   AnnotationAttribute getAttribute(String name);
   
   // Inner classes -------------------------------------------------
}
