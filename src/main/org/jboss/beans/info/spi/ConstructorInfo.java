/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.spi;

import java.lang.reflect.Constructor;
import java.util.List;

import org.jboss.util.JBossInterface;

/**
 * Information about a constructor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface ConstructorInfo extends JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the parameters
    * 
    * @return List<ParameterInfo>
    */
   List getParameters();

   /**
    * Set the parameters
    * 
    * @param parameters List<ParameterInfo>
    */
   void setParameters(List parameters);

   /**
    * Whether the constructor is static
    * 
    * @return true when static
    */
   boolean isStatic();

   /**
    * Whether the constructor is public
    * 
    * @return true when public
    */
   boolean isPublic();

   /**
    * Get the constructor
    * 
    * @return the constructor
    */
   Constructor getConstructor();
   
   // Inner classes -------------------------------------------------
}
