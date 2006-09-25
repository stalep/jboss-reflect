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
 * A field info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface FieldInfo extends AnnotatedInfo, MemberInfo
{
   /**
    * Get the name
    * 
    * @return the field name
    */
   String getName();
   
   /**
    * Get the field type
    * 
    * @return the field type
    */
   TypeInfo getType();

   /**
    * Get the declaring class
    * 
    * @return the declaring class
    */
   ClassInfo getDeclaringClass();
   
   /**
    * Get the value of the field
    * 
    * @param target the target
    * @return the field value
    * @throws Throwable for any error
    */
   Object get(Object target) throws Throwable;
   
   /**
    * Set the value of the field
    * 
    * @param target the target
    * @param value the value
    * @return null
    * @throws Throwable for any error
    */
   Object set(Object target, Object value) throws Throwable;
}
