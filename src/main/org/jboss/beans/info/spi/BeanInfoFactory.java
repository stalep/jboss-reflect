/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.spi;



/**
 * BeanInfo Factory.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface BeanInfoFactory
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Retrieve the bean information
    * 
    * @param className the bean class name
    * @return the bean information
    */
   BeanInfo getBeanInfo(String className);
   
   // Inner classes -------------------------------------------------
}
