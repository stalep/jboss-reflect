/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.spi;

import java.util.Set;

import org.jboss.util.JBossInterface;

/**
 * Description of a class.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface ClassInfo extends TypeInfo, JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get any super class information
    * 
    * @return the super class information
    */
   ClassInfo getSuperClassInfo();

   /**
    * Set any super class information
    * 
    * @param superClassInfo the super class information
    */
   void setSuperClassInfo(ClassInfo superClassInfo);

   /**
    * Get any constructors
    * 
    * @return Set<ConstructorInfo>
    */
   Set getConstructors();

   /**
    * Set the constructors
    * 
    * @param constructors Set<ConstructorInfo>
    */
   void setConstructors(Set constructors);
   
   // Inner classes -------------------------------------------------
}
