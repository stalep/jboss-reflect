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
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AbstractJavassistBody;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.Body;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.MutableConstructorInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * JavassistConstructor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistConstructorInfo extends JavassistAnnotatedParameterInfo implements MutableConstructorInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -2255405601790592604L;

   /** The reflection factory */
   private static final JavassistReflectionFactory reflectionFactory = new JavassistReflectionFactory(true);
 
   /** The constructor */
   private CtConstructor ctConstructor;
   
   /** The constructor implementation */
   private transient JavassistConstructor constructor;

   /**
    * Create a new JavassistConstructor.
    * 
    * @param annotationHelper the annotation helper
    * @param typeInfo the type ifo
    * @param ctConstructor the constructor
    */
   public JavassistConstructorInfo(AnnotationHelper annotationHelper, JavassistTypeInfo typeInfo, CtConstructor ctConstructor)
   {
      super(annotationHelper);
      this.typeInfo = typeInfo;
      this.ctConstructor = ctConstructor;
      
   }

   public ModifierInfo getModifiers()
   {
      return ModifierInfo.getNewModifier(ctConstructor.getModifiers());
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

   @Override
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

   @Override
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


   @Override
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
            parameters[i] = new JavassistParameterInfo(annotationHelper, this, i, parameterTypes[i]);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for parameters of constructor", e);
      }
   }

   public AnnotationValue[] getAnnotations()
   {
      return getAnnotations(ctConstructor);
   }

   @Override
   protected void createParameterAnnotations()
   {
      try
      {
         Object[][] parameterAnnotations = ctConstructor.getParameterAnnotations();
         super.setupParameterAnnotations(parameterAnnotations);
      }
      catch (ClassNotFoundException e)
      {
         // AutoGenerated
         throw new RuntimeException(e);
      }
   }
   
   protected CtConstructor getCtConstructor()
   {
      return ctConstructor;
   }

   public void setBody(Body body)
   {
      if (body instanceof AbstractJavassistBody == false)
      {
         throw new IllegalArgumentException("Body is not an instance of AbstractJavassistBody");
      }
      ((AbstractJavassistBody)body).createBody(ctConstructor);
      typeInfo.clearConstructorCache();
   }

   public void setExceptions(String[] exceptions)
   {
      try
      {
         ctConstructor.setExceptionTypes(JavassistUtil.toCtClass(exceptions));
      }
      catch (NotFoundException e)
      {
         throw new org.jboss.reflect.spi.NotFoundException(e.toString());
      }
      typeInfo.clearConstructorCache();
   }

   public void setExceptions(ClassInfo[] exceptions)
   {
      try
      {
         ctConstructor.setExceptionTypes(JavassistUtil.toCtClass(exceptions));
      }
      catch (NotFoundException e)
      {
         throw new org.jboss.reflect.spi.NotFoundException(e.toString());
      }
      typeInfo.clearConstructorCache();
   }

   public void setModifier(ModifierInfo mi)
   {
      typeInfo.clearMethodCache();
      ctConstructor.setModifiers(mi.getModifiers());
   }

   public void setParameters(String[] parameters)
   {
      for(String p : parameters)
      {
         try
         {
            ctConstructor.addParameter(JavassistUtil.toCtClass(p));
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
      typeInfo.clearConstructorCache();
   }

   public void setParameters(ClassInfo[] parameters)
   {
      for(ClassInfo clazz : parameters)
      {
         try
         {
            ctConstructor.addParameter(JavassistUtil.toCtClass(clazz));
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
      typeInfo.clearConstructorCache();
   }
}
