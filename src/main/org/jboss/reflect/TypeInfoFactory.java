/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * A type info factory.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface TypeInfoFactory
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get a type info
    * 
    * @param name the class name
    * @param cl the classloader
    * @return the type info
    * @throws ClassNotFoundException when there is no such class
    */
   TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException;

   /**
    * Get a type info
    * 
    * @param clazz the class
    * @return the type info
    */
   TypeInfo getTypeInfo(Class clazz);
   
   // Inner classes -------------------------------------------------
}
