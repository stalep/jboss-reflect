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

import java.util.Arrays;

import javassist.CannotCompileException;
import javassist.CtBehavior;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AbstractJavassistBody;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.Body;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.MutableMethodInfo;
import org.jboss.reflect.spi.MutableMethodInfoCommand;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

public class JavassistMethodInfo extends JavassistAnnotatedParameterInfo implements MutableMethodInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 101183748227690112L;

   /** The reflection factory */
   private static final JavassistReflectionFactory reflectionFactory = new JavassistReflectionFactory(true);
   
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
    * @param ctMethod the method
    */
   public JavassistMethodInfo(AnnotationHelper annotationHelper, JavassistTypeInfo typeInfo, CtMethod ctMethod)
   {
      super(annotationHelper);
      this.typeInfo = typeInfo;
      this.ctMethod = ctMethod;
   }

   public String getName()
   {
      return ctMethod.getName();
   }

   public ClassInfo getDeclaringClass()
   {
      return typeInfo;
   }

   public ModifierInfo getModifiers()
   {
      return ModifierInfo.getNewModifier(ctMethod.getModifiers());
   }

   public boolean isPublic()
   {
      return getModifiers().isPublic();
   }

   public boolean isStatic()
   {
      return getModifiers().isStatic();
   }

   public boolean isVolatile()
   {
      return getModifiers().isVolatile();
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

   @Override
   protected int getHashCode()
   {
      return getName().hashCode();
   }

   @Override
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

   @Override
   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(getName());
   }

   @Override
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

   @Override
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

   //TODO: need to be implemented...
   public void executeCommand(MutableMethodInfoCommand mmc)
   {
   }
   
   public CtMethod getCtMethod()
   {
      return ctMethod;
   }

   public void setBody(Body body)
   {
      typeInfo.clearMethodCache();
      if (body instanceof AbstractJavassistBody == false)
      {
         throw new IllegalArgumentException("Body is not an instance of AbstractJavassistBody");
      }
      ((AbstractJavassistBody)body).createBody(ctMethod);
   }

   public void setExceptions(String[] exceptions)
   {
      typeInfo.clearMethodCache();
      try
      {
         ctMethod.setExceptionTypes(JavassistUtil.toCtClass(exceptions));
      }
      catch (NotFoundException e)
      {
         throw new org.jboss.reflect.spi.NotFoundException(e.toString());
      }
   }

   public void setExceptions(ClassInfo[] exceptions)
   {
      try
      {
         ctMethod.setExceptionTypes(JavassistUtil.toCtClass(exceptions));
      }
      catch (NotFoundException e)
      {
         throw new org.jboss.reflect.spi.NotFoundException(e.toString());
      }
      typeInfo.clearMethodCache();
   }

   public void setModifier(ModifierInfo mi)
   {
     ctMethod.setModifiers(mi.getModifiers());
     typeInfo.clearMethodCache();
   }

   public void setName(String name)
   {
      ctMethod.setName(name);
      typeInfo.clearMethodCache();
   }

   public void setParameters(String[] parameters)
   {
      for(String p : parameters)
      {
         try
         {
            ctMethod.addParameter(JavassistUtil.toCtClass(p));
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
      typeInfo.clearMethodCache();
   }

   public void setParameters(ClassInfo[] parameters)
   {
      for(ClassInfo clazz : parameters)
      {
         try
         {
            ctMethod.addParameter(JavassistUtil.toCtClass(clazz));
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
      typeInfo.clearMethodCache();
   }

   public void setReturnType(String returnType)
   {
      throw new RuntimeException("Method not supported by Javassist");
   }

   public void setReturnType(ClassInfo returnType)
   {
      throw new RuntimeException("Method not supported by Javassist");
   }
}
