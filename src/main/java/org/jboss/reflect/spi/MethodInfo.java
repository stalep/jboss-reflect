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
 * Method info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface MethodInfo extends AnnotatedInfo, MemberInfo
{
   /** No parameters */
   public static final TypeInfo[] NO_PARAMS_TYPES = {};
   
   /** No parameters */
   public static final ParameterInfo[] NO_PARAMS = {};
   
   /** No Exceptions */
   public static final ClassInfo[] NO_EXCEPTIONS = {};

   /**
    * Get the method name
    * 
    * @return the method name
    */
   String getName();

   /**
    * Get the parameter types
    * 
    * @return the parameter types
    */
   TypeInfo[] getParameterTypes();

   /**
    * Get the parameters
    * 
    * @return the parameters
    */
   ParameterInfo[] getParameters();

   /**
    * Get the exception types
    * 
    * @return the exception types
    */
   ClassInfo[] getExceptionTypes();
   
   /**
    * Get the return type
    * 
    * @return the return type
    */
   TypeInfo getReturnType();

   /**
    * Invoke the method
    * 
    * @param target the target
    * @param args the arguments
    * @return the result of the invocation
    * @throws Throwable for any error
    */
   Object invoke(Object target, Object[] args) throws Throwable;
}
