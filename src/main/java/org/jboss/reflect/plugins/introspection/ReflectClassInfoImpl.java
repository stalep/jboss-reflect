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
package org.jboss.reflect.plugins.introspection;

import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * Class info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectClassInfoImpl extends ClassInfoImpl implements InterfaceInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 2719363625297177304L;

   /**
    * Create a new abstract ClassInfo.
    */
   public ReflectClassInfoImpl()
   {
   }

   /**
    * Create a new class info
    * 
    * @param name the class name
    */
   public ReflectClassInfoImpl(String name)
   {
      super(name);
   }

   /**
    * Create a new abstract ClassInfo.
    * 
    * @param name the class name
    * @param modifiers the class modifiers
    * @param interfaces the interfaces
    * @param superclass the super class
    */
   public ReflectClassInfoImpl(String name, int modifiers, InterfaceInfo[] interfaces,
                        ReflectClassInfoImpl superclass)
   {
      super(name, modifiers, interfaces, superclass);
   }

   @SuppressWarnings("deprecation")
   public boolean isInterface()
   {
      return getType().isInterface();
   }

   @SuppressWarnings("deprecation")
   Object readResolve()
   {
      TypeInfoFactory typeInfoFactory = IntrospectionTypeInfoFactory.getDelegate();
      return typeInfoFactory.getTypeInfo(getType());
   }
}
