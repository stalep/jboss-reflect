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
package org.jboss.reflect.plugins;

import org.jboss.reflect.plugins.introspection.ParameterizedClassInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.PackageInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * ClassInfoHelper.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface ClassInfoHelper
{
   /**
    * Get the super class
    * 
    * @param classInfo the class info
    * @return the super class info
    */
   ClassInfoImpl getSuperClass(ClassInfoImpl classInfo);

   /**
    * Get the generic super class
    * 
    * @param classInfo the class info
    * @return the super class info
    */
   ClassInfo getGenericSuperClass(ClassInfoImpl classInfo);

   /**
    * Get the interfaces
    * 
    * @param classInfo the class info
    * @return the interface info
    */
   InterfaceInfo[] getInterfaces(ClassInfoImpl classInfo);

   /**
    * Get the generic interfaces
    * 
    * @param classInfo the class info
    * @return the interface info
    */
   InterfaceInfo[] getGenericInterfaces(ClassInfoImpl classInfo);

   /**
    * Get the constructors
    * 
    * @param classInfo the class info
    * @return the constructor info
    */
   ConstructorInfoImpl[] getConstructors(ClassInfoImpl classInfo);
   
   /**
    * Get the fields
    * 
    * @param classInfo the class info
    * @return the field info
    */
   FieldInfoImpl[] getFields(ClassInfoImpl classInfo);

   /**
    * Get the methods
    * 
    * @param classInfo the class info
    * @return the method info
    */
   MethodInfoImpl[] getMethods(ClassInfoImpl classInfo);

   /**
    * Get a type info
    * 
    * @param clazz the class
    * @return the type info
    */
   TypeInfo getTypeInfo(Class clazz);
   
   /**
    * Get the actual type parameters
    * 
    * @param classInfo the parameterized class info
    * @return the type parameters
    */
   TypeInfo[] getActualTypeArguments(ParameterizedClassInfo classInfo);
   
   /**
    * Get the owner type
    * 
    * @param classInfo the parameterized class info
    * @return the owner type
    */
   TypeInfo getOwnerType(ParameterizedClassInfo classInfo);
   
   /**
    * Get the component type for a collection
    * 
    * @param classInfo the class info
    * @return the component type or null if not a collection
    */
   TypeInfo getComponentType(ClassInfo classInfo);
   
   /**
    * Get the key type for a map
    * 
    * @param classInfo the class info
    * @return the key type or null if not a map
    */
   TypeInfo getKeyType(ClassInfo classInfo);
   
   /**
    * Get the value type for a map
    * 
    * @param classInfo the class info
    * @return the value type or null if not a map
    */
   TypeInfo getValueType(ClassInfo classInfo);
   
   /**
    * Get the package for class
    * 
    * @param classInfo the class info
    * @return the package
    */
   PackageInfo getPackage(ClassInfoImpl classInfo);
}
