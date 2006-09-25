/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.classloading.spi;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

/**
 * A classloader that can be put in a domain
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface DomainClassLoader
{
   /**
    * The domain of the classloader
    * 
    * @return the domain
    */
   ClassLoadingDomain getDomain();

   /**
    * Set the domain of the classloader
    * 
    * @param domain the domain
    */
   void setDomain(ClassLoadingDomain domain);

   /**
    * Get the classpath
    * 
    * @return the classpath
    */
   public URL[] getClasspath();

   /**
    * Load a class
    * 
    * @param name the name
    * @return the class
    * @throws ClassNotFoundException
    */
   Class loadClass(String name) throws ClassNotFoundException;

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
    * Find resources locally
    * 
    * @param name the name of the resource
    * @return the resources
    * @throws IOException for any error
    */
   Enumeration<URL> findResourcesLocally(String name) throws IOException;

   /**
    * Get the possible package names associated with the class loader. This
    * may be a superset of the currently defined Packages.
    * 
    * @return unique package names of classes available to the class loader.
    */
   public String[] getPackageNames();
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
}
