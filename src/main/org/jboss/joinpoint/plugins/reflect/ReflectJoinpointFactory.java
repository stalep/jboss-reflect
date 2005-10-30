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
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import org.jboss.joinpoint.spi.ConstructorJoinpoint;
import org.jboss.joinpoint.spi.FieldGetJoinpoint;
import org.jboss.joinpoint.spi.FieldSetJoinpoint;
import org.jboss.joinpoint.spi.JoinpointException;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.joinpoint.spi.MethodJoinpoint;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * A join point factory based on reflection
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectJoinpointFactory implements JoinpointFactory
{
   /** The class info */
   protected ClassInfo classInfo;

   public static void handleErrors(String context, Class[] parameters, Object[] arguments, Throwable t) throws Throwable
   {
      if (t instanceof IllegalArgumentException)
      {
         ArrayList expected = new ArrayList();
         Class[] parameterTypes = parameters;
         for (int i = 0; i < parameterTypes.length; ++i)
            expected.add(parameterTypes[i].getName());
         ArrayList actual = new ArrayList();
         if (arguments != null)
         {
            for (int i = 0; i < arguments.length; ++i)
            {
               if (arguments[i] == null)
                  actual.add(null);
               else
                  actual.add(arguments[i].getClass().getName());
            }
         }
         throw new IllegalArgumentException("Wrong arguments. " + context + " expected=" + expected + " actual=" + actual);
      }
      else if (t instanceof InvocationTargetException)
      {
         throw ((InvocationTargetException) t).getTargetException();
      }
      throw t;
   }
   
   public ReflectJoinpointFactory(ClassInfo classInfo)
   {
      this.classInfo = classInfo;
   }
   
   public ClassInfo getClassInfo()
   {
      return classInfo;
   }

   public ConstructorJoinpoint getConstructorJoinpoint(ConstructorInfo constructorInfo) throws JoinpointException
   {
      return new ReflectConstructorJoinPoint(constructorInfo);
   }

   public FieldGetJoinpoint getFieldGetJoinpoint(FieldInfo fieldInfo) throws JoinpointException
   {
      return new ReflectFieldGetJoinPoint(fieldInfo);
   }

   public FieldSetJoinpoint getFieldSetJoinpoint(FieldInfo fieldInfo) throws JoinpointException
   {
      return new ReflectFieldSetJoinPoint(fieldInfo);
   }

   public MethodJoinpoint getMethodJoinpoint(MethodInfo methodInfo) throws JoinpointException
   {
      return new ReflectMethodJoinPoint(methodInfo);
   }
}
