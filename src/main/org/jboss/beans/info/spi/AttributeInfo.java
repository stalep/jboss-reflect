/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.spi;

import org.jboss.util.JBossInterface;

/**
 * Description of an attribute.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface AttributeInfo extends JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the attribute name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the uppercase version of the attribute name
    * 
    * @return the name
    */
   String getUpperName();

   /**
    * Get the type
    * 
    * @return the type
    */
   TypeInfo getType();

   /**
    * Get the getter
    * 
    * @return the getter
    */
   MethodInfo getGetter();

   /**
    * Set the getter
    * 
    * @param getter the getter
    */
   void setGetter(MethodInfo getter);

   /**
    * Get the setter
    * 
    * @return the setter
    */
   MethodInfo getSetter();

   /**
    * Set the setter
    * 
    * @param setter the setter
    */
   void setSetter(MethodInfo setter);
   
   // Inner classes -------------------------------------------------
}
