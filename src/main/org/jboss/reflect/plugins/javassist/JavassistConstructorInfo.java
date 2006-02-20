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
package org.jboss.reflect.plugins.javassist;

import java.lang.reflect.Modifier;
import java.util.Arrays;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * JavassistConstructor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistConstructorInfo extends JavassistAnnotatedInfo implements ConstructorInfo
{
   /** The reflection factory */
   private static final JavassistReflectionFactory reflectionFactory = new JavassistReflectionFactory(true);
 
   /** The type info */
   private JavassistTypeInfo typeInfo;
   
   /** The constructor */
   private CtConstructor ctConstructor;
   
   /** The constructor implementation */
   private transient JavassistConstructor constructor;

   /** The parameters */
   private transient ParameterInfo[] parameters;
   
   /** The parameter types */
   private transient TypeInfo[] parameterTypes;
   
   /** The exception types */
   private transient ClassInfo[] exceptionTypes;
   
   /**
    * Create a new JavassistConstructor.
    * 
    * @param typeInfo the type ifo
    * @param ctConstructor the constructor
    */
   public JavassistConstructorInfo(JavassistTypeInfo typeInfo, CtConstructor ctConstructor)
   {
      this.typeInfo = typeInfo;
      this.ctConstructor = ctConstructor;
   }

   public int getModifiers()
   {
      return ctConstructor.getModifiers();
   }

   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   public ClassInfo getDeclaringClass()
   {
      return typeInfo;
   }

   public ClassInfo[] getExceptionTypes()
   {
      if (exceptionTypes == null)
      {
         try
         {
            CtClass[] types = ctConstructor.getExceptionTypes();
            exceptionTypes = new ClassInfo[types.length];
            for (int i = 0; i < types.length; ++i)
               exceptionTypes[i] = (ClassInfo) typeInfo.getFactory().getTypeInfo(types[i]);
         }
         catch (NotFoundException e)
         {
            throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for exception types of constructor", e);
         }
      }
      return exceptionTypes;
   }

   public ParameterInfo[] getParameters()
   {
      if (parameters == null)
         generateParameters();
      return parameters;
   }

   public TypeInfo[] getParameterTypes()
   {
      if (parameterTypes == null)
         generateParameters();
      return parameterTypes;
   }

   public Object newInstance(Object[] args) throws Throwable
   {
      if (constructor == null)
         constructor = reflectionFactory.createConstructor(ctConstructor);
      return constructor.newInstance(args);
   }

   protected int getHashCode()
   {
      int result = getDeclaringClass().hashCode();
      generateParameters();
      if (parameterTypes != null)
      {
         for (int i = 0; i < parameterTypes.length; i++)
            result = 29 * result + parameterTypes[i].hashCode();
      }
      return result;
   }

   public boolean equals(Object obj)
   {
      if (this == obj) 
         return true;
      if (obj == null || obj instanceof ConstructorInfo == false)
         return false;

      final ConstructorInfo other = (ConstructorInfo) obj;
      
      if (getDeclaringClass().equals(other.getDeclaringClass()) == false)
         return false;
      return (Arrays.equals(getParameterTypes(), other.getParameterTypes()));
   }


   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append(Arrays.asList(getParameterTypes()));
      super.toString(buffer);
   }
   
   /**
    * Generate parameters
    */
   protected void generateParameters()
   {
      try
      {
         CtClass[] types = ctConstructor.getParameterTypes();
         parameterTypes = new TypeInfo[types.length];
         for (int i = 0; i < types.length; ++i)
            parameterTypes[i] = typeInfo.getFactory().getTypeInfo(types[i]);
         parameters = new ParameterInfo[types.length];
         for (int i = 0; i < types.length; ++i)
            parameters[i] = new JavassistParameterInfo(this, "arg" + i, parameterTypes[i]);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for parameters of constructor", e);
      }
   }
}
