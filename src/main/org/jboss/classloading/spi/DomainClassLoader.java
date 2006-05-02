/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classloading.spi;

import java.net.URL;

/**
 * A classloader that can be put in a domain
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface DomainClassLoader
{
   // Constants -----------------------------------------------------
   
   // Public --------------------------------------------------------

   /**
    * The domain of the classloader
    * 
    * @return the domain
    */
   ClassLoadingDomain getDomain();

   /**
    * Set the domain of the classloader
    * 
    */
   void setDomain(ClassLoadingDomain domain);
   
   /**
    * Load a class
    * 
    * @param name the class name
    * @param resolve whether to resolve the class 
    * @return the class
    * @throws ClassNotFoundException when there is not class
    */
   Class loadClassLocally(String name, boolean resolve) throws ClassNotFoundException;
   
   /**
    * Get a resource
    * 
    * @param name the resource name
    * @return the resource or null if not found
    */
   URL loadResourceLocally(String name);

   /**
    * Get the packages defined by the classloader
    * 
    * @return the packages
    */
   Package[] getPackages();

   /**
    * Get a package defined by the classloader
    * 
    * @param name the name of the package
    * @return the package
    */
   Package getPackage(String name);
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------
}