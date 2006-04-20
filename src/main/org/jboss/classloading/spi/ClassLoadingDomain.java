/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classloading.spi;

import java.net.URL;

/**
 * A ClassLoadingDomain holds a number of classloaders.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface ClassLoadingDomain
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Whether we implement java2 classloading compliance
    * 
    * @return true when delegate first to parent
    */
   boolean getJava2ClassLoadingCompliance();
   
   /**
    * Get the parent classloading domain
    * 
    * @return the parent or null if there isn't one
    */
   ClassLoadingDomain getParent();
   
   /**
    * Load a class from this domain
    * 
    * @param name the class to load
    * @param resolve whether to resolve the class
    * @param classLoader the requesting classloader
    * @return the class
    * @throws ClassNotFoundException when the class is not found
    */
   Class loadClass(String name, boolean resolve, DomainClassLoader classLoader) throws ClassNotFoundException;
   
   /**
    * Get a resource
    * 
    * @param name the resource name
    * @param classLoader the requesting classloader
    * @return the resource or null if not found
    */
   URL loadResource(String name, DomainClassLoader classLoader);
   
   // Inner classes -------------------------------------------------
}
