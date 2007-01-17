/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import java.lang.reflect.ParameterizedType;

import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * ParameterizedArrayInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ParameterizedArrayInfo extends ParameterizedClassInfo implements ArrayInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -2126867826240682161L;

   /**
    * Create a new ParameterizedArrayInfo.
    * 
    * @param factory the factory
    * @param delegate the raw array info
    * @param parameterizedType the parameterized  type
    */
   public ParameterizedArrayInfo(IntrospectionTypeInfoFactoryImpl factory, ArrayInfo delegate, ParameterizedType parameterizedType)
   {
      super(factory, delegate, parameterizedType);
   }

   public TypeInfo getComponentType()
   {
      return ((ArrayInfo) delegate).getComponentType();
   }
}
