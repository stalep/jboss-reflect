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

import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

public class JavassistMethodInfo extends JavassistAnnotatedParameterInfo implements MethodInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 101183748227690112L;

   /** The reflection factory */
   private static final JavassistReflectionFactory reflectionFactory = new JavassistReflectionFactory(true);
   
   /** The key */
   private SignatureKey key;
   
   /** The method */
   private CtMethod ctMethod;
   
   /** The method implementation */
   private transient JavassistMethod method;
   
   /** The return type */
   private transient TypeInfo returnType;
   
   /**
    * Create a new JavassistMethodInfo.
    * 
    * @param annotationHelper the annotation helper
    * @param typeInfo the type info
    * @param key the key
    * @param ctMethod the method
    */
   public JavassistMethodInfo(AnnotationHelper annotationHelper, JavassistTypeInfo typeInfo, SignatureKey key, CtMethod ctMethod)
   {
      super(annotationHelper);
      this.typeInfo = typeInfo;
      this.key = key;
      this.ctMethod = ctMethod;
   }

   public String getName()
   {
      return key.name;
   }

   public ClassInfo getDeclaringClass()
   {
      return typeInfo;
   }

   public int getModifiers()
   {
      return ctMethod.getModifiers();
   }

   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   public ClassInfo[] getExceptionTypes()
   {
      if (exceptionTypes == null)
      {
         try
         {
            CtClass[] types = ctMethod.getExceptionTypes();
            exceptionTypes = new ClassInfo[types.length];
            for (int i = 0; i < types.length; ++i)
               exceptionTypes[i] = (ClassInfo) typeInfo.getFactory().getTypeInfo(types[i]);
         }
         catch (NotFoundException e)
         {
            throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for exception types of method " + getName(), e);
         }
      }
      return exceptionTypes;
   }

   public TypeInfo getReturnType()
   {
      if (returnType != null)
         return returnType;
      try
      {
         CtClass clazz = ctMethod.getReturnType();
         returnType = typeInfo.getFactory().getTypeInfo(clazz);
         return returnType;
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for return type of method " + getName(), e);
      }
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

   public Object invoke(Object target, Object[] args) throws Throwable
   {
      if (method == null)
         method = reflectionFactory.createMethod(ctMethod);
      return method.invoke(target, args);
   }

   protected int getHashCode()
   {
      return getName().hashCode();
   }

   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof MethodInfo == false)
         return false;

      final MethodInfo other = (MethodInfo) obj;

      if (getName().equals(other.getName()) == false)
         return false;
      if (getDeclaringClass().equals(other.getDeclaringClass()) == false)
         return false;
      if (getReturnType().equals(other.getReturnType()) == false)
         return false;
      return Arrays.equals(getParameterTypes(), other.getParameterTypes());
   }

   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(getName());
   }

   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(getName());
      super.toString(buffer);
   }
   
   /**
    * Generate parameters
    */
   protected void generateParameters()
   {
      try
      {
         CtClass[] types = ctMethod.getParameterTypes();
         parameterTypes = new TypeInfo[types.length];
         for (int i = 0; i < types.length; ++i)
            parameterTypes[i] = typeInfo.getFactory().getTypeInfo(types[i]);
         parameters = new ParameterInfo[types.length];
         for (int i = 0; i < types.length; ++i)
            parameters[i] = new JavassistParameterInfo(annotationHelper, this, i, parameterTypes[i]);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for parameters of " + getName(), e);
      }
   }
   
   public AnnotationValue[] getAnnotations()
   {
      return getAnnotations(ctMethod);
   }

   protected CtBehavior getParameterizedObject()
   {
      return ctMethod;
   }

   protected void createParameterAnnotations()
   {
      try
      {
         Object[][] parameterAnnotations = ctMethod.getParameterAnnotations();
         super.setupParameterAnnotations(parameterAnnotations);
      }
      catch (ClassNotFoundException e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }

}
