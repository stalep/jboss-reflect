/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.spi;

import org.jboss.util.JBossInterface;

/**
 * Information about a Parameter.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface ParameterInfo extends JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Get the parameter name
    * 
    * @return the parameter name
    */
   String getName();
   
   /**
    * Get the parameter type
    * 
    * @return the type info
    */
   TypeInfo getTypeInfo();
   
   /**
    * Set the parameter type
    * 
    * @param typeInfo the type info
    */
   void setTypeInfo(TypeInfo typeInfo);
   
   // Inner classes -------------------------------------------------
}
