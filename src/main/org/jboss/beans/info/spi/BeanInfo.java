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
 * Description of a bean.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface BeanInfo extends JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the bean name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the class information
    * 
    * @return the class information
    */
   ClassInfo getClassInfo();

   /**
    * Set the class information
    * 
    * @param classInfo the class information
    */
   void setClassInfo(ClassInfo classInfo);
   
   /**
    * Get the attribute information.
    *
    * @return a Set<AttributeInfo> 
    */
   Set getAttributes();
   
   /**
    * Set the attribute information.
    *
    * @param attributes a Set<AttributeInfo> 
    */
   void setAttributes(Set attributes);
   
   /**
    * Get the constructor info.
    *
    * @return a Set<ConstructorInfo> 
    */
   Set getConstructors();
   
   /**
    * Set the constructor info.
    *
    * @param constructors a Set<ConstructorInfo> 
    */
   void setConstructors(Set constructors);
   
   /**
    * Get the method information.
    *
    * @return a Set<MethodInfo> 
    */
   Set getMethods();
   
   /**
    * Set the method information.
    *
    * @param methods a Set<MethodInfo> 
    */
   void setMethods(Set methods);
   
   /**
    * Get the event information.
    *
    * @return a Set<EventInfo> 
    */
   Set getEvents();
   
   /**
    * set the event information.
    *
    * @param events a Set<EventInfo> 
    */
   void setEvents(Set events);
   
   // Inner classes -------------------------------------------------
}
