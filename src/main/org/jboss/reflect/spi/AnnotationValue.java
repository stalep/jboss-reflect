/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;


/**
 * An annotation value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface AnnotationValue extends Value
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the annotation type
    * 
    * @return the annotation type
    */
   AnnotationInfo getAnnotationType();
   
   /**
    * Get an attribute value
    * 
    * @param attributeName the attribute name
    * @return the value
    */
   Value getValue(String attributeName);
   
   // Inner classes -------------------------------------------------
}
