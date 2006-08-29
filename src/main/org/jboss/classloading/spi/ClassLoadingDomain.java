/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classloading.spi;

import java.net.URL;
import java.util.Enumeration;

import org.jboss.util.loading.Translator;

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
   URL getResource(String name, DomainClassLoader classLoader);

   Enumeration<URL> findResources(String name);

   Translator getTranslator();
   // Inner classes -------------------------------------------------
}
