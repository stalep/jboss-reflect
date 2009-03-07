/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.ModifierInfo;

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
    */
   public InterfaceInfoImpl(String name, ModifierInfo modifiers)
   {
      super(name, modifiers);
   }

   /**
    * Create a new InterfaceInfo.
    * 
    * @param name the interface name
    * @param modifiers the interface modifier
    * @param interfaces the interfaces
    */
   public InterfaceInfoImpl(String name, ModifierInfo modifiers, InterfaceInfo[] interfaces)
   {
      super(name, modifiers, interfaces, null);
   }
   
   @Override
   public boolean isInterface()
   {
      return true;
   }
}
