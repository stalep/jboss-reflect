/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

import org.jboss.util.JBossInterface;

/**
 * Annotated info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface AnnotatedInfo extends JBossInterface
{
   // Constants -----------------------------------------------------
   
   // Public --------------------------------------------------------

   /**
    * Get the annotations
    * 
    * @return the annotations
    */
   AnnotationValue[] getAnnotations();
   
   /**
    * Get an annotation
    * 
    * @param name the name
    * @return the annotation
    */
   AnnotationValue getAnnotation(String name);
   
   /**
    * Test whether an annotation is present
    * 
    * @param name the name
    * @return true when the annotation is present
    */
   boolean isAnnotationPresent(String name);
   
   // Inner classes -------------------------------------------------
}
