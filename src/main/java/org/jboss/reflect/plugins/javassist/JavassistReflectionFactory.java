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

import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.concurrent.atomic.AtomicInteger;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.Modifier;
import javassist.NotFoundException;

import org.jboss.util.JBossStringBuilder;
import org.jboss.util.UnreachableStatementException;

/**
 * JavassistReflectionFactory.
 * 
 * TODO JBMICROCONT-122 proper classpool with pruning
 * TODO JBMICROCONT-121 non compiler based implementation
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistReflectionFactory
{
   /** The method class counter */
   private static final AtomicInteger counter = new AtomicInteger(0);
   
   /** Whether to check arguments */
   private final boolean check;
   
   /**
    * Create a new JavassistReflectionFactory.
    * 
    * @param check whether to check arguments
    */
   public JavassistReflectionFactory(boolean check)
   {
      this.check = check;
   }
   
   /**
    * Create a javassist method
    * 
    * @param ctMethod the method
    * @return the method
    * @throws Throwable for any error
    */
   public JavassistMethod createMethod(CtMethod ctMethod) throws Throwable
   {
      //TODO: Fix this to use a more reasonable  factory
      ClassPool pool = JavassistTypeInfoFactoryImpl.poolFactory.getPoolForLoader(null);
      final CtClass result = pool.makeClass(JavassistMethod.class.getName() + counter.incrementAndGet());
      /* TODO JBMICROCONT-133 figure out how to do this on all JDKs
      try
      {
         CtClass magic = pool.get("sun.reflect.MagicAccessorImpl");
         result.setSuperclass(magic);
      }
      catch (NotFoundException ignored)
      {
      }*/
      result.addInterface(pool.get(JavassistMethod.class.getName()));
      
      CtConstructor constructor = new CtConstructor(null, result);
      constructor.setBody("super();");
      result.addConstructor(constructor);
      
      CtClass object = pool.get(Object.class.getName());
      
      JBossStringBuilder buffer = new JBossStringBuilder();
      
      // Signature
      buffer.append("public Object invoke(Object target, Object[] args) throws Throwable {");

      boolean isInstanceMethod = Modifier.isStatic(ctMethod.getModifiers()) == false;

      // Check for null target
      if (check && isInstanceMethod)
      {
         buffer.append("if (target == null) throw new IllegalArgumentException(\"Null target for ");
         buffer.append(ctMethod.getName()).append(ctMethod.getSignature()).append("\");");
      }

      // Check the target
      CtClass declaring = ctMethod.getDeclaringClass();
      boolean needsCast = isInstanceMethod && object.equals(declaring) == false;
      if (check && needsCast)
      {
         buffer.append("if (target instanceof ").append(declaring.getName()).append(" == false) ");
         buffer.append("throw new IllegalArgumentException(\"Target \" + target + \"");
         buffer.append(" is not an instance of ").append(declaring.getName());
         buffer.append(" for ").append(ctMethod.getName()).append(ctMethod.getSignature()).append("\");");
      }

      // Check the parameters 
      CtClass[] params = ctMethod.getParameterTypes();
      if (check)
      {
         // Wrong number of args?
         if (params.length != 0)
         {
            buffer.append("if (args == null || args.length != ").append(params.length).append(") ");
            buffer.append("throw new IllegalArgumentException(\"Expected ").append(params.length).append(" parameter(s)");
            buffer.append(" for ").append(ctMethod.getName()).append(ctMethod.getSignature()).append("\");");
         }
         else
         {
            // Didn't expect args
            buffer.append("if (args != null && args.length != 0)");
            buffer.append("throw new IllegalArgumentException(\"Expected no parameters");
            buffer.append(" for ").append(ctMethod.getName()).append(ctMethod.getSignature()).append("\");");
         }
         for (int i = 0; i < params.length; ++i)
         {
            if (object.equals(params[i]) == false)
            {
               String paramType = getBoxedType(params[i]);
               // Primitives can't be null
               if (params[i].isPrimitive())
               {
                  buffer.append("if (args[").append(i).append("] == null) ");
                  buffer.append("throw new IllegalArgumentException(\"Parameter ").append(i);
                  buffer.append(" cannot be null for ").append(params[i].getName());
                  buffer.append(" for ").append(ctMethod.getName()).append(ctMethod.getSignature()).append("\");");
               }
               // Check the parameter types
               buffer.append("if (args[").append(i).append("] != null && ");
               buffer.append("args[").append(i).append("] instanceof ").append(paramType).append(" == false) ");
               buffer.append("throw new IllegalArgumentException(\"Parameter ").append(i).append(" \" + args[").append(i).append("] + \"");
               buffer.append(" is not an instance of ").append(paramType);
               buffer.append(" for ").append(ctMethod.getName()).append(ctMethod.getSignature()).append("\");");
            }
         }
      }
      
      // Add a return and box the return type if necessary
      CtClass returnType = ctMethod.getReturnType();
      boolean isVoid = CtClass.voidType.equals(returnType);
      boolean isPrimitive = returnType.isPrimitive();
      if (isVoid == false)
      {
         buffer.append("return ");
         if (isPrimitive)
            buffer.append("new ").append(getBoxedType(returnType)).append('(');
      }

      // Instance method
      if (isInstanceMethod)
      {
         buffer.append('(');
         if (needsCast)
            buffer.append("(").append(declaring.getName()).append(')');
         
         buffer.append("target).");
      }
      else
      {
         // Static method
         buffer.append(declaring.getName()).append('.');
      }

      // Add the method name
      buffer.append(ctMethod.getName()).append('(');

      // Add the parameters
      for (int i = 0; i < params.length; ++i)
      {
         buffer.append('(');
         // Cast the parameters
         if (object.equals(params[i]) == false)
             buffer.append("(").append(getBoxedType(params[i])).append(')');
         buffer.append("args[").append(i).append("])");
         // Unbox primitive parameters
         if (params[i].isPrimitive())
            unbox(buffer, params[i]);
         
         if (i < params.length - 1)
            buffer.append(", ");
      }
      buffer.append(')');

      // Complete the boxing of the return value
      if (isVoid == false && isPrimitive)
         buffer.append(')');
      
      buffer.append(';');
      
      // Add a return null if there is no return value
      if (isVoid)
         buffer.append("return null;");
      buffer.append('}');

      // Compile it
      String code = buffer.toString();
      try
      {
         CtMethod invoke = CtNewMethod.make(code, result);
         result.addMethod(invoke);
      }
      catch (Exception e)
      {
         throw new RuntimeException("Cannot compile " + code, e);
      }
      
      // Create it
      try
      {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<JavassistMethod>()
         {
            public JavassistMethod run() throws Exception
            {
               Class<?> clazz = result.toClass();
               return (JavassistMethod) clazz.newInstance();
            }
         });
      }
      catch (PrivilegedActionException e)
      {
         throw e.getCause();
      }
   }
   
   /**
    * Create a javassist constructor
    * 
    * @param ctConstructor the constructor
    * @return the constructor
    * @throws Throwable for any error
    */
   public JavassistConstructor createConstructor(CtConstructor ctConstructor) throws Throwable
   {
      //TODO: FIx this to use a more reasonable factory
      ClassPool pool = JavassistTypeInfoFactoryImpl.poolFactory.getPoolForLoader(null);
      final CtClass result = pool.makeClass(JavassistConstructor.class.getName() + counter.incrementAndGet());
      try
      {
         CtClass magic = pool.get("sun.reflect.MagicAccessorImpl");
         result.setSuperclass(magic);
      }
      catch (NotFoundException ignored)
      {
      }
      result.addInterface(pool.get(JavassistConstructor.class.getName()));
      
      CtConstructor constructor = new CtConstructor(null, result);
      constructor.setBody("super();");
      result.addConstructor(constructor);
      
      CtClass object = pool.get(Object.class.getName());
      
      JBossStringBuilder buffer = new JBossStringBuilder();
      
      // Signature
      buffer.append("public Object newInstance(Object[] args) throws Throwable {");

      String declaring = ctConstructor.getDeclaringClass().getName();
      
      // Check the parameters 
      CtClass[] params = ctConstructor.getParameterTypes();
      if (check)
      {
         // Wrong number of args?
         if (params.length != 0)
         {
            buffer.append("if (args == null || args.length != ").append(params.length).append(") ");
            buffer.append("throw new IllegalArgumentException(\"Expected ").append(params.length).append(" parameter(s)");
            buffer.append(" for ").append("new ").append(declaring).append(ctConstructor.getSignature()).append("\");");
         }
         else
         {
            // Didn't expect args
            buffer.append("if (args != null && args.length != 0)");
            buffer.append("throw new IllegalArgumentException(\"Expected no parameters");
            buffer.append(" for ").append("new ").append(declaring).append(ctConstructor.getSignature()).append("\");");
         }
         for (int i = 0; i < params.length; ++i)
         {
            if (object.equals(params[i]) == false)
            {
               String paramType = getBoxedType(params[i]);
               // Primitives can't be null
               if (params[i].isPrimitive())
               {
                  buffer.append("if (args[").append(i).append("] == null) ");
                  buffer.append("throw new IllegalArgumentException(\"Parameter ").append(i);
                  buffer.append(" cannot be null for ").append(params[i].getName());
                  buffer.append(" for ").append("new ").append(declaring).append(ctConstructor.getSignature()).append("\");");
               }
               // Check the parameter types
               buffer.append("if (args[").append(i).append("] != null && ");
               buffer.append("args[").append(i).append("] instanceof ").append(paramType).append(" == false) ");
               buffer.append("throw new IllegalArgumentException(\"Parameter ").append(i).append(" \" + args[").append(i).append("] + \"");
               buffer.append(" is not an instance of ").append(paramType);
               buffer.append(" for ").append("new ").append(declaring).append(ctConstructor.getSignature()).append("\");");
            }
         }
      }
      
      // Add the return new 
      buffer.append("return new ").append(declaring).append('(');

      // Add the parameters
      for (int i = 0; i < params.length; ++i)
      {
         buffer.append('(');
         // Cast the parameters
         if (object.equals(params[i]) == false)
             buffer.append("(").append(getBoxedType(params[i])).append(')');
         buffer.append("args[").append(i).append("])");
         // Unbox primitive parameters
         if (params[i].isPrimitive())
            unbox(buffer, params[i]);
         
         if (i < params.length - 1)
            buffer.append(", ");
      }
      buffer.append(");}");

      // Compile it
      String code = buffer.toString();
      try
      {
         CtMethod newInstance = CtNewMethod.make(code, result);
         result.addMethod(newInstance);
      }
      catch (Exception e)
      {
         throw new RuntimeException("Cannot compile " + code, e);
      }
      
      // Create it
      try
      {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<JavassistConstructor>()
         {
            public JavassistConstructor run() throws Exception
            {
               Class<?> clazz = result.toClass();
               return (JavassistConstructor) clazz.newInstance();
            }
         });
      }
      catch (PrivilegedActionException e)
      {
         throw e.getCause();
      }
   }
   
   /**
    * Create a javassist field
    * 
    * @param ctField the field
    * @return the field
    * @throws Throwable for any error
    */
   public JavassistField createField(CtField ctField) throws Throwable
   {
      // Fix this to use a better pool factory
      ClassPool pool = JavassistTypeInfoFactoryImpl.poolFactory.getPoolForLoader(null);
      final CtClass result = pool.makeClass(JavassistField.class.getName() + counter.incrementAndGet());
      try
      {
         CtClass magic = pool.get("sun.reflect.MagicAccessorImpl");
         result.setSuperclass(magic);
      }
      catch (NotFoundException ignored)
      {
      }
      result.addInterface(pool.get(JavassistField.class.getName()));
      
      CtConstructor constructor = new CtConstructor(null, result);
      constructor.setBody("super();");
      result.addConstructor(constructor);
      
      CtClass object = pool.get(Object.class.getName());
      
      // GET
      JBossStringBuilder buffer = new JBossStringBuilder();
      
      // Signature
      buffer.append("public Object get(Object target) throws Throwable {");

      boolean isInstanceField= Modifier.isStatic(ctField.getModifiers()) == false;

      // Check for null target
      if (check && isInstanceField)
      {
         buffer.append("if (target == null) throw new IllegalArgumentException(\"Null target");
         buffer.append(" for ").append(ctField.getName()).append("\");");
      }

      // Check the target
      CtClass declaring = ctField.getDeclaringClass();
      boolean needsCast = isInstanceField && object.equals(declaring) == false;
      if (check && needsCast)
      {
         buffer.append("if (target instanceof ").append(declaring.getName()).append(" == false) ");
         buffer.append("throw new IllegalArgumentException(\"Target \" + target + \"");
         buffer.append(" is not an instance of ").append(declaring.getName());
         buffer.append(" for ").append(ctField.getName()).append("\");");
      }
      
      // Add a return and box the return type if necessary
      CtClass type = ctField.getType();
      boolean isPrimitive = type.isPrimitive();
      buffer.append("return ");
      if (isPrimitive)
         buffer.append("new ").append(getBoxedType(type)).append('(');

      // Instance field
      if (isInstanceField)
      {
         buffer.append('(');
         if (needsCast)
            buffer.append("(").append(declaring.getName()).append(')');
         
         buffer.append("target).");
      }
      else
      {
         // Static field
         buffer.append(declaring.getName()).append('.');
      }

      // Add the field name
      buffer.append(ctField.getName());

      // Complete the boxing of the return value
      if (isPrimitive)
         buffer.append(')');
      
      buffer.append(";}");

      // Compile it
      String code = buffer.toString();
      CtMethod get = CtNewMethod.make(code, result);
      try
      {
         result.addMethod(get);
      }
      catch (Exception e)
      {
         throw new RuntimeException("Cannot compile " + code, e);
      }
      
      // SET
      buffer = new JBossStringBuilder();
      
      // Signature
      buffer.append("public void set(Object target, Object value) throws Throwable {");

      // Check for null target
      if (check && isInstanceField)
      {
         buffer.append("if (target == null) throw new IllegalArgumentException(\"Null target");
         buffer.append(" for ").append(ctField.getName()).append("\");");
      }

      // Check the target
      if (check && needsCast)
      {
         buffer.append("if (target instanceof ").append(declaring.getName()).append(" == false) ");
         buffer.append("throw new IllegalArgumentException(\"Target \" + target + \"");
         buffer.append(" is not an instance of ").append(declaring.getName());
         buffer.append(" for ").append(ctField.getName()).append("\");");
      }

      // Check the parameter 
      if (check)
      {
         if (object.equals(type) == false)
         {
            String paramType = getBoxedType(type);
            // Primitives can't be null
            if (type.isPrimitive())
            {
               buffer.append("if (type == null) ");
               buffer.append("throw new IllegalArgumentException(\"Value ");
               buffer.append(" cannot be null for ").append(type.getName());
               buffer.append(" for ").append(ctField.getName()).append("\");");
            }
            // Check the parameter types
            buffer.append("if (value != null && ");
            buffer.append("value instanceof ").append(paramType).append(" == false) ");
            buffer.append("throw new IllegalArgumentException(\"Value \" + value + \"");
            buffer.append(" is not an instance of ").append(paramType);
            buffer.append(" for ").append(ctField.getName()).append("\");");
         }
      }

      // Instance Field
      if (isInstanceField)
      {
         buffer.append('(');
         if (needsCast)
            buffer.append("(").append(declaring.getName()).append(')');
         
         buffer.append("target).");
      }
      else
      {
         // Static field
         buffer.append(declaring.getName()).append('.');
      }

      // Add the field name
      buffer.append(ctField.getName()).append("=");

      // Add the value
      buffer.append('(');
      // Cast the value
      if (object.equals(type) == false)
          buffer.append("(").append(getBoxedType(type)).append(')');
      buffer.append("value)");
      // Unbox primitive parameters
      if (type.isPrimitive())
         unbox(buffer, type);
      
      buffer.append(";}");

      // Compile it
      code = buffer.toString();
      try
      {
         CtMethod set = CtNewMethod.make(code, result);
         result.addMethod(set);
      }
      catch (Exception e)
      {
         throw new RuntimeException("Cannot compile " + code, e);
      }
      
      // Create it
      try
      {
         return AccessController.doPrivileged(new PrivilegedExceptionAction<JavassistField>()
         {
            public JavassistField run() throws Exception
            {
               Class<?> clazz = result.toClass();
               return (JavassistField) clazz.newInstance();
            }
         });
      }
      catch (PrivilegedActionException e)
      {
         throw e.getCause();
      }
   }
   
   /**
    * Unbox a primitive
    * 
    * @param buffer the buffer
    * @param primitive the primitive
    */
   protected void unbox(JBossStringBuilder buffer, CtClass primitive)
   {
      if (CtClass.booleanType.equals(primitive))
         buffer.append(".booleanValue()");
      else if (CtClass.byteType.equals(primitive))
         buffer.append(".byteValue()");
      else if (CtClass.charType.equals(primitive))
         buffer.append(".charValue()");
      else if (CtClass.doubleType.equals(primitive))
         buffer.append(".doubleValue()");
      else if (CtClass.floatType.equals(primitive))
         buffer.append(".floatValue()");
      else if (CtClass.intType.equals(primitive))
         buffer.append(".intValue()");
      else if (CtClass.longType.equals(primitive))
         buffer.append(".longValue()");
      else if (CtClass.shortType.equals(primitive))
         buffer.append(".shortValue()");
      else
      {
         throw new UnreachableStatementException();
      }
   }

   /**
    * Get the boxed type
    * 
    * TODO JBMICROCONT-119 integer progression?
    * @param type the type to box
    * @return the boxed type name
    */
   protected String getBoxedType(CtClass type)
   {
      if (type.isPrimitive())
      {
         if (CtClass.booleanType.equals(type))
            return Boolean.class.getName();
         else if (CtClass.byteType.equals(type))
            return Byte.class.getName();
         else if (CtClass.charType.equals(type))
            return Character.class.getName();
         else if (CtClass.doubleType.equals(type))
            return Double.class.getName();
         else if (CtClass.floatType.equals(type))
            return Float.class.getName();
         else if (CtClass.intType.equals(type))
            return Integer.class.getName();
         else if (CtClass.longType.equals(type))
            return Long.class.getName();
         else if (CtClass.shortType.equals(type))
            return Short.class.getName();
         throw new UnreachableStatementException();
      }
      return type.getName();
   }
}
