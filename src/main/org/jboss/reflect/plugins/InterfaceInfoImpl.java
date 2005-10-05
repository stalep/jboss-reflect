/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.InterfaceInfo;

/**
 * Interface info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class InterfaceInfoImpl extends ClassInfoImpl implements InterfaceInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3258690987944522291L;

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
    */
   public InterfaceInfoImpl(String name, int modifiers, InterfaceInfo[] interfaces)
   {
      super(name, modifiers, interfaces, null);
   }
   
   public boolean isInterface()
   {
      return true;
   }
}
