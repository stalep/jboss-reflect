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
 * Information about a type.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface TypeInfo extends JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the type name
    * 
    * @return the name
    */
   String getName();
   
   /**
    * Get the super interfaces.
    *
    * @return a Set<InterfaceInfo> 
    */
   Set getSuperInterfaceInfo();
   
   /**
    * Set the super interfaces.
    *
    * @param superInterfaces a Set<InterfaceInfo> 
    */
   void setSuperInterfaceInfo(Set superInterfaces);
   
   /**
    * Get the attributes.
    *
    * @return a Set<AttributeInfo> 
    */
   Set getAttributes();
   
   /**
    * Set the attributes.
    *
    * @param attributes a Set<AttributeInfo> 
    */
   void setAttributes(Set attributes);
   
   /**
    * Get the methods.
    *
    * @return a Set<MethodInfo> 
    */
   Set getMethods();
   
   /**
    * Set the methods.
    *
    * @param methods a Set<MethodInfo> 
    */
   void setMethods(Set methods);
   
   /**
    * Get the class
    * 
    * @return the classs
    */
   Class getType();
   
   // Inner classes -------------------------------------------------
}
