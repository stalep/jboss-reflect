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
package org.jboss.reflect.plugins.javassist;

import java.lang.reflect.Type;

import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.MutableTypeInfoFactory;

/**
 * An javassist type factory that uses a static delegate.<p>
 * 
 * This avoids recalculating things everytime a factory is
 * constructed inside the same classloader
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class JavassistTypeInfoFactory implements MutableTypeInfoFactory
{
   /** The delegate */
   protected static JavassistTypeInfoFactoryImpl delegate = new JavassistTypeInfoFactoryImpl();

   static MutableTypeInfoFactory getDelegate()
   {
      return delegate;
   }

   public TypeInfo getTypeInfo(Class<?> clazz)
   {
      return delegate.getTypeInfo(clazz);
   }
   
   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      return delegate.getTypeInfo(name, cl);
   }
   
   public TypeInfo getTypeInfo(Type type)
   {
      return delegate.getTypeInfo(type);
   }

   public MutableClassInfo createNewMutableClass(String name)
   {
      return delegate.createNewMutableClass(name);
   }

   public MutableClassInfo createNewMutableClass(String name, ClassInfo superClass)
   {
      return delegate.createNewMutableClass(name, superClass);
   }

   public MutableClassInfo createNewMutableInterface(String name)
   {
      return delegate.createNewMutableInterface(name);
   }

   public MutableClassInfo createNewMutableInterface(String name, ClassInfo superClass)
   {
      return delegate.createNewMutableInterface(name, superClass);
   }

   public MutableClassInfo getMutable(String name, ClassLoader cl)
   {
      return delegate.getMutable(name, cl);
   }

}
