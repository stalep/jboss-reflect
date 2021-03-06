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
package org.jboss.beans.info.spi;

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.FieldInfo;

/**
 * Description of a property.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface PropertyInfo extends AnnotatedInfo
{
   /**
    * Get the BeanInfo
    * 
    * @return the bean info
    */
   BeanInfo getBeanInfo();
   
   /**
    * Get the property name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the uppercase version of the property name
    * 
    * @return the name
    */
   String getUpperName();

   /**
    * Get the type
    * 
    * @return the type
    */
   TypeInfo getType();

   /**
    * Get the getter
    * 
    * @return the getter
    */
   MethodInfo getGetter();

   /**
    * Set the getter
    * 
    * @param getter the getter
    */
   void setGetter(MethodInfo getter);

   /**
    * Get the setter
    * 
    * @return the setter
    */
   MethodInfo getSetter();

   /**
    * Set the setter
    * 
    * @param setter the setter
    */
   void setSetter(MethodInfo setter);

   /**
    * Is property readable.
    *
    * @return true if the property is readable
    */
   boolean isReadable();

   /**
    * Is property writable.
    *
    * @return true if the property is writable
    */
   boolean isWritable();

   /**
    * Get the property value
    * 
    * @param bean the bean
    * @return the property value
    * @throws Throwable for any error
    */
   Object get(Object bean) throws Throwable;
   
   /**
    * Set the property value
    * 
    * @param bean the bean
    * @param value the property value
    * @throws Throwable for any error
    */
   void set(Object bean, Object value) throws Throwable;

   /**
    * Get the field info
    * in case impl uses field to
    * handle property ops.
    *
    * @return the field info or null if no such info available
    */
   FieldInfo getFieldInfo();
}
