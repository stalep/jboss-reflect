/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.util.HashMap;

import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.InterfaceInfo;

/**
 * Interface info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class InterfaceInfoImpl extends ClassInfoImpl implements InterfaceInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new interface info
    */
   public InterfaceInfoImpl()
   {
   }

   /**
    * Create a new interface info
    * 
    * @param name the interface name
    */
   public InterfaceInfoImpl(String name)
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
   public InterfaceInfoImpl(String name, int modifiers, InterfaceInfo[] interfaces, AnnotationValue[] annotations)
   {
      super(name, modifiers, interfaces, null, annotations);
   }

   // Public --------------------------------------------------------

   // ClassInfoImpl overrides ---------------------------------------
   
   public boolean isInterface()
   {
      return true;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
