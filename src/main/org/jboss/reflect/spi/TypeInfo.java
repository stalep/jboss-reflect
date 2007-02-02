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

import java.io.Serializable;

/**
 * A type info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface TypeInfo extends Serializable
{
   /**
    * Get the type name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the simple name
    * 
    * @return the simple name
    */
   String getSimpleName();
   
   /**
    * Get the class
    *
    * @deprecated I'm not sure this should be here?
    * @return the class
    */
   Class getType();
   
   /**
    * Convert a value
    * 
    * @param value the original value
    * @return the converted value
    * @throws Throwable for any error
    */
   Object convertValue(Object value) throws Throwable;
   
   /**
    * Convert a value
    *
    * @param value the original value
    * @param replaceProperties whether to replace properties
    * @return the converted value
    * @throws Throwable for any error
    */
   Object convertValue(Object value, boolean replaceProperties) throws Throwable;

   /**
    * Whether this type is an array
    * 
    * @return true when an array
    */
   boolean isArray();

   /**
    * Whether this type is a collection
    * 
    * @return true when a collection
    */
   boolean isCollection();

   /**
    * Whether this type is a map
    * 
    * @return true when a map
    */
   boolean isMap();

   /**
    * Whether this type is an annotation
    * 
    * @return true when an annotation
    */
   boolean isAnnotation();
   
   /**
    * Whether this type is an enum
    * 
    * @return true when an enum
    */
   boolean isEnum();
   
   /**
    * Whether this type is a primitive
    * 
    * @return true when a primtive
    */
   boolean isPrimitive();
   
   /**
    * Whether this type is an array
    * 
    * @param depth the array depth
    * @return the array type
    */
   TypeInfo getArrayType(int depth);
   
   /**
    * Create a new array
    * 
    * @param size the size
    * @return the converted value
    * @throws Throwable for any error
    */
   Object[] newArrayInstance(int size) throws Throwable;

   /**
    * Mostly using
    * @see java.lang.Class#isAssignableFrom
    * NumberInfo tests for progression
    *
    * @param info
    * @return the boolean value indicating whether objects of the
    *         TypeInfo info can be assigned to objects of this TypeInfo
    * @exception NullPointerException if the specified TypeInfo parameter is
    *            null.
    */
   boolean isAssignableFrom(TypeInfo info);

   /**
    * Get the TypeInfoFactory that created this type info
    *
    * @return type info factory
    */
   TypeInfoFactory getTypeInfoFactory();
   
   /**
    * Get an attachment from the type
    * 
    * @param name the name
    * @return the attachment
    */
   Object getAttachment(String name);
   
   /**
    * Get an attachment from the type,
    * uses the expected type as both the name
    * and to cast the resulting object.
    * 
    * @param <T> the expected type
    * @param expectedType the expected type
    * @return the attachment
    * @throws ClassCastException when the object is not of the expected type
    */
   <T> T getAttachment(Class<T> expectedType);
   
   /**
    * Set an attachment against the type.
    * This is useful for caching information against a type.<p>
    *
    * If you add a future object, subsequent gets will wait for the result<p>
    * 
    * WARNING: Be careful about what you put in here. Don't create
    * references across classloaders, if you are not sure add a WeakReference
    * to the information.
    * 
    * @param name the name
    * @param attachment the attachment, pass null to remove an attachment
    * @throws IllegalArgumentException for a null name
    */
   void setAttachment(String name, Object attachment);
}
