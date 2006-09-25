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

import java.lang.reflect.Method;

import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Method info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectMethodInfoImpl extends MethodInfoImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -7215834088396065347L;
   
   /** The method */
   protected Method method;

   /**
    * Create a new method info
    */
   public ReflectMethodInfoImpl()
   {
   }

   /**
    * Create a new MethodInfo.
    * 
    * @param annotations the annotations
    * @param name the method name
    * @param returnType the return type
    * @param parameterTypes the parameter types
    * @param parameterAnnotations the parameter annotations
    * @param exceptionTypes the exception types
    * @param modifiers the modifiers
    * @param declaring the declaring class
    */
   public ReflectMethodInfoImpl(AnnotationValue[] annotations, String name, TypeInfo returnType, TypeInfo[] parameterTypes, AnnotationValue[][] parameterAnnotations, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations, name, returnType, parameterTypes, parameterAnnotations, exceptionTypes, modifiers, declaring);
   }

   /**
    * Create a new MethodInfo.
    * 
    * @param annotations the annotations
    * @param name the method name
    * @param returnType the return type
    * @param parameters the parameters
    * @param exceptionTypes the exception types
    * @param modifiers the modifiers
    * @param declaring the declaring class
    */
   public ReflectMethodInfoImpl(AnnotationValue[] annotations, String name, TypeInfo returnType, ParameterInfo[] parameters, ClassInfo[] exceptionTypes, int modifiers, ClassInfo declaring)
   {
      super(annotations, name, returnType, parameters, exceptionTypes, modifiers, declaring);
   }

   /**
    * Set the method
    * 
    * @param method the method
    */
   public void setMethod(Method method)
   {
      this.method = method;
   }

   /**
    * Get the method
    * 
    * @return the method
    */
   public Method getMethod()
   {
      return method;
   }
   
   public Object invoke(Object target, Object[] args) throws Throwable
   {
      return ReflectionUtils.invoke(method, target, args);
   }
}
