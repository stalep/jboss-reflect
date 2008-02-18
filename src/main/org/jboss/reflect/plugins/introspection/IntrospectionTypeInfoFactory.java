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

import java.lang.reflect.Type;

import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * An introspection type factory that uses a static delegate.<p>
 * 
 * This avoids recalculating things everytime a factory is
 * constructed inside the same classloader
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class IntrospectionTypeInfoFactory implements TypeInfoFactory
{
   /** The delegate */
   protected static IntrospectionTypeInfoFactoryImpl delegate = new IntrospectionTypeInfoFactoryImpl();

   static TypeInfoFactory getDelegate()
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
}
