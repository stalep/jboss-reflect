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
package org.jboss.joinpoint.spi;

import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * A join point factory.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface JoinpointFactory
{
   /**
    * Get the class info for this join point factory
    * 
    * @return the class info
    */
   ClassInfo getClassInfo();
   
   /**
    * Get a constructor join point
    * 
    * @param constructorInfo the constructor info
    * @return the constructor join point
    * @throws JoinpointException when no such constructor
    */
   ConstructorJoinpoint getConstructorJoinpoint(ConstructorInfo constructorInfo) throws JoinpointException;

   /**
    * Get a field get join point
    * 
    * @param fieldInfo the field info
    * @return the field get join point
    * @throws JoinpointException when no such field
    */
   FieldGetJoinpoint getFieldGetJoinpoint(FieldInfo fieldInfo) throws JoinpointException;

   /**
    * Get a field set join point
    * 
    * @param fieldInfo the field info
    * @return the field set join point
    * @throws JoinpointException when no such field
    */
   FieldSetJoinpoint getFieldSetJoinpoint(FieldInfo fieldInfo) throws JoinpointException;

   /**
    * Get a method join point
    * 
    * @param methodInfo the method info
    * @return the method join point
    * @throws JoinpointException when no such method
    */
   MethodJoinpoint getMethodJoinpoint(MethodInfo methodInfo) throws JoinpointException;
}
