/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * Interface info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class InterfaceInfo extends ClassInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new interface info
    */
   public InterfaceInfo()
   {
   }

   /**
    * Create a new interface info
    * 
    * @param name the interface name
    */
   public InterfaceInfo(String name)
   {
      super(name);
   }

   /**
    * Create a new InterfaceInfo.
    * 
    * @param name the interface name
    * @param modifiers the interface modifier
    * @param interfaces the interfaces
    * @param annotations the annotations
    */
   public InterfaceInfo(String name, int modifiers, InterfaceInfo[] interfaces, AnnotationValue[] annotations)
   {
      super(name, modifiers, interfaces, null, annotations);
   }

   // Public --------------------------------------------------------

   // ClassInfo overrides -------------------------------------------
   
   public boolean isInterface()
   {
      return true;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
