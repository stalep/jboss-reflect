/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classadapter.spi;

/**
 * A class adapter factory.<p>
 * 
 * The ClassAdapterFactory serves as the entry point
 * for deciding whether it supports the class. e.g.
 * the AOP ClassAdapterFactory may return null
 * allowing the microcontainer to default back to
 * reflection for the class.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ClassAdapterFactory
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get a class adapter
    * 
    * @param name the class name
    * @param cl the classloader
    * @return the class adapter
    * @throws ClassNotFoundException when there is no such class
    */
   ClassAdapter getClassAdapter(String name, ClassLoader cl) throws ClassNotFoundException;

   /**
    * Get a class adapter
    * 
    * @param clazz the class
    * @return the class adapter
    */
   ClassAdapter getClassAdapter(Class clazz);
   
   // Inner classes -------------------------------------------------
}
