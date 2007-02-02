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
package org.jboss.reflect.spi;

/**
 * Class info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ClassInfo extends AnnotatedInfo, ModifierInfo, TypeInfo
{
   /**
    * Get the class name
    * 
    * @return the name
    */
   String getName();

   /**
    * Whether it is an interface
    * 
    * @return true when an interface
    */
   boolean isInterface();

   /**
    * Get the interfaces
    *
    * @return the interfaces
    */
   InterfaceInfo[] getInterfaces();
   
   /**
    * Get the generic interfaces
    *
    * @return the generic interfaces
    */
   InterfaceInfo[] getGenericInterfaces();
   
   /**
    * Get the declared method
    * 
    * @param name the method name
    * @param parameters the parameters
    * @return the method info
    */
   MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters);

   /**
    * Get the declared methods
    * 
    * @return the methods
    */
   MethodInfo[] getDeclaredMethods();

   /**
    * Get the declared field
    * 
    * @param name the field name
    * @return the field
    */
   FieldInfo getDeclaredField(String name);

   /**
    * Get the declared fields
    * 
    * @return the fields
    */
   FieldInfo[] getDeclaredFields();

   /**
    * Get the declared constructors
    * 
    * @return the constructors
    */
   ConstructorInfo[] getDeclaredConstructors();

   /**
    * Get a declared constructor
    * 
    * @param parameters the parameters
    * @return the constructor
    */
   ConstructorInfo getDeclaredConstructor(TypeInfo[] parameters);

   /**
    * Get the super class
    * 
    * @return the super class
    */
   ClassInfo getSuperclass();

   /**
    * Get the generic super class
    * 
    * @return the super class
    */
   ClassInfo getGenericSuperclass();
   
   /**
    * Get the actual type parameters
    * 
    * @return the type parameters
    */
   TypeInfo[] getActualTypeArguments();
   
   /**
    * Get the raw type
    * 
    * @return the raw type
    */
   ClassInfo getRawType();
   
   /**
    * Get the owner type
    * 
    * @return the owner type
    */
   TypeInfo getOwnerType();
   
   /**
    * Get the package
    * 
    * @return the package
    */
   PackageInfo getPackage();
}
