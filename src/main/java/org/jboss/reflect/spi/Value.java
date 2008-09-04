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
 * A value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public interface Value
{
   /**
    * Get the type of the value
    * 
    * @return the type
    */
   TypeInfo getType();
   
   /**
    * Is this value a primitive
    * 
    * @return true when a primitive
    */
   boolean isPrimitive();
   
   /**
    * Get the value as a primitive
    * 
    * @return the value
    * @throws IllegalStateException when not a primitive value
    */
   PrimitiveValue asPrimitive();
   
   /**
    * Is this value a class
    * 
    * @return true when a class
    */
   boolean isClass();
   
   /**
    * Get the value as a class
    * 
    * @return the value
    * @throws IllegalStateException when not a class value
    */
   ClassValue asClass();
   
   /**
    * Is this value a string
    * 
    * @return true when a string
    */
   boolean isString();
   
   /**
    * Get the value as a string
    * 
    * @return the value
    * @throws IllegalStateException when not a string value
    */
   StringValue asString();
   
   /**
    * Is this value an enum
    * 
    * @return true when an enum
    */
   boolean isEnum();
   
   /**
    * Get the value as an enum
    * 
    * @return the value
    * @throws IllegalStateException when not an enum
    */
   EnumValue asEnum();
   
   /**
    * Is this value an annotation
    * 
    * @return true when an annotation
    */
   boolean isAnnotation();
   
   /**
    * Get the value as an annotation
    * 
    * @return the value
    * @throws IllegalStateException when not an annotation value
    */
   AnnotationValue asAnnotation();
   
   /**
    * Is this value an array
    * 
    * @return true when an array
    */
   boolean isArray();
   
   /**
    * Get the value as an annotation
    * 
    * @return the value
    * @throws IllegalStateException when not an array value
    */
   ArrayValue asArray();
}
