/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.util.Set;

import org.jboss.beans.info.spi.InterfaceInfo;

/**
 * Interface info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractInterfaceInfo extends AbstractTypeInfo implements InterfaceInfo
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------
   
   /**
    * Create a new interface info
    * 
    * @param name the class name
    */
   public AbstractInterfaceInfo(String name) 
   {
      super(name);
   }
   
   /**
    * Create a new interface info
    * 
    * @param name the class name
    * @param superInterfaces the super interfaces
    * @param attributes the attributes
    * @param methods the methods
    */
   public AbstractInterfaceInfo(String name, Set superInterfaces, Set attributes, 
         Set methods)
   {
      super(name, superInterfaces, attributes, methods);
   }
   
   // Public --------------------------------------------------------

   // InterfaceInfo implementation ----------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
