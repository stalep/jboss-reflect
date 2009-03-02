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

import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.CannotCompileException;
import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewConstructor;
import javassist.CtNewMethod;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.PackageInfoImpl;
import org.jboss.reflect.plugins.TypeInfoAttachments;
import org.jboss.reflect.plugins.ValueConvertor;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.Body;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.InsertBeforeJavassistBody;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableConstructorInfo;
import org.jboss.reflect.spi.MutableFieldInfo;
import org.jboss.reflect.spi.MutableMethodInfo;
import org.jboss.reflect.spi.PackageInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.JBossStringBuilder;

/**
 * JavassistTypeInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistTypeInfo extends JavassistInheritableAnnotationHolder implements MutableClassInfo, InterfaceInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -5072033691434335775L;

   /** The factory */
   private JavassistTypeInfoFactoryImpl factory;

   /** The name */
   private String name;
   
   /** The class */
   private Class<? extends Object> clazz;

   /** The constructors */
   private Map<SignatureKey, JavassistConstructorInfo> constructors = new ConcurrentHashMap<SignatureKey, JavassistConstructorInfo>();

   /** The constructors */
   private MutableConstructorInfo[] constructorArray;

   /** The fields */
   private Map<String, JavassistFieldInfo> fields = new ConcurrentHashMap<String, JavassistFieldInfo>();

   /** The fields */
   private MutableFieldInfo[] fieldArray;

   /** The methods */
   private Map<SignatureKey, JavassistMethodInfo> methods = new ConcurrentHashMap<SignatureKey, JavassistMethodInfo>();

   /** The methods */
   private MutableMethodInfo[] methodArray;

   /** The package info */
   private PackageInfo packageInfo;
   
   /** The attachments */
   private transient TypeInfoAttachments attachments;

   /**
    * Create a new JavassistTypeInfo.
    * 
    * @param factory the factory
    * @param ctClass the ctClass
    * @param clazz the class
    */
   JavassistTypeInfo(JavassistTypeInfoFactoryImpl factory, CtClass ctClass, Class<? extends Object> clazz)
   {
      this(factory, ctClass.getName(), ctClass, clazz);
   }

   /**
    * Create a new JavassistTypeInfo.
    * 
    * @param factory the factory
    * @param ctClass the ctClass
    * @param clazz the class
    * @param name the name
    */
   JavassistTypeInfo(JavassistTypeInfoFactoryImpl factory, String name, CtClass ctClass, Class<? extends Object> clazz)
   {
      super(ctClass, factory);
      this.factory = factory;
      this.clazz = clazz;
      this.name = name;
   }

   public String getName()
   {
      return name;
   }

   public boolean isInterface()
   {
      return ctClass.isInterface();
   }

   public String getSimpleName()
   {
      return getType().getSimpleName();
   }

   public ModifierInfo getModifiers()
   {
      return ModifierInfo.getNewModifier(ctClass.getModifiers());
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

   @Deprecated
   public Class<? extends Object> getType()
   {
      if(clazz == null)
         clazz = JavassistUtil.ctClassToClass(ctClass);
      
      return clazz;
   }

   public ClassInfo getSuperclass()
   {
      if (isInterface())
         return null;
      try
      {
         CtClass superclass = ctClass.getSuperclass();
         if (superclass == null)
            return null;
         return (ClassInfo) factory.getTypeInfo(superclass);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound(clazz.getSuperclass().getName(), e);
      }
   }

   public ClassInfo getGenericSuperclass()
   {
      // TODO JBMICROCONT-129 getGenericSuperclass
      throw new org.jboss.util.NotImplementedException("getGenericSuperclass");
   }

   public InterfaceInfo[] getInterfaces()
   {
      try
      {
         CtClass[] interfaces = ctClass.getInterfaces();
         if (interfaces == null || interfaces.length == 0)
            return null;
         InterfaceInfo[] result = new InterfaceInfo[interfaces.length];
         for (int i = 0; i < result.length; ++i)
            result[i] = (InterfaceInfo) factory.getTypeInfo(interfaces[i]);
         return result;
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for interfaces of " + getName(), e);
      }
   }

   public InterfaceInfo[] getGenericInterfaces()
   {
      // TODO JBMICROCONT-129 getGenericInterfaces
      throw new org.jboss.util.NotImplementedException("getGenericInterfaces");
   }

   public MutableConstructorInfo[] getDeclaredConstructors()
   {
      if (constructorArray == null)
      {
         CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
         if (declaredConstructors == null || declaredConstructors.length == 0)
            constructorArray = new MutableConstructorInfo[0];
         else
         {
            synchronized (constructors)
            {
               for (int i = 0; i < declaredConstructors.length; ++i)
                  generateConstructorInfo(declaredConstructors[i]);
               Collection<JavassistConstructorInfo> constructorCollection = constructors.values();
               constructorArray = constructorCollection.toArray(new MutableConstructorInfo[constructorCollection.size()]);
            }
         }
      }
      return constructorArray;
   }

   public MutableConstructorInfo getDeclaredConstructor(TypeInfo[] parameters)
   {
      SignatureKey key = new SignatureKey(null, parameters);
      synchronized (constructors)
      {
         MutableConstructorInfo constructor = constructors.get(key);
         if (constructor != null)
            return constructor;
      }
      if (constructorArray != null)
         return null;
      return generateConstructorInfo(key);
   }

   public MutableConstructorInfo getDeclaredConstructor(String[] parameters) throws ClassNotFoundException
   {
      TypeInfo[] typeParams = new TypeInfo[parameters.length];
      for(int i=0; i<parameters.length;i++)
      {
         typeParams[i] = factory.getTypeInfo(parameters[i], 
               JavassistTypeInfoFactoryImpl.poolFactory.getPoolForLoader(null).getClassLoader());
      }
      return getDeclaredConstructor(typeParams);
   }
   
   public MutableFieldInfo getDeclaredField(String fieldName)
   {
      synchronized (fields)
      {
         MutableFieldInfo field = fields.get(fieldName);
         if (field != null)
            return field;
      }
      if (fieldArray != null)
         return null;
      try
      {
         CtField field = ctClass.getDeclaredField(fieldName);
         if (field == null)
            return null;
         return generateFieldInfo(field);
      }
      catch (NotFoundException e)
      {
         return null;
      }
   }

   public MutableFieldInfo[] getDeclaredFields()
   {
      if (fieldArray == null)
      {
         CtField[] declaredFields = ctClass.getDeclaredFields();
         if (declaredFields == null || declaredFields.length == 0)
            fieldArray = new MutableFieldInfo[0];
         else
         {
            synchronized (fields)
            {
               for (int i = 0; i < declaredFields.length; ++i)
                  generateFieldInfo(declaredFields[i]);
               Collection<JavassistFieldInfo> fieldCollection = fields.values();
               fieldArray = fieldCollection.toArray(new MutableFieldInfo[fieldCollection.size()]);
            }
         }
      }
      return fieldArray;
   }

   public MutableMethodInfo getDeclaredMethod(String methodName, TypeInfo[] parameters)
   {
      SignatureKey key = new SignatureKey(methodName, parameters);
      synchronized (methods)
      {
         MutableMethodInfo method = methods.get(key);
         if (method != null)
            return method;
      }
      if (methodArray != null)
         return null;
      return generateMethodInfo(key);
   }

   public MutableMethodInfo getDeclaredMethod(String methodName, String[] parameters) throws ClassNotFoundException
   {
      TypeInfo[] typeParams = new TypeInfo[parameters.length];
      for(int i=0; i<parameters.length;i++)
      {
         typeParams[i] = factory.getTypeInfo(parameters[i], 
               JavassistTypeInfoFactoryImpl.poolFactory.getPoolForLoader(null).getClassLoader());
      }         
      return getDeclaredMethod(methodName, typeParams);
   }
   
   public MutableMethodInfo[] getDeclaredMethods()
   {
      if (methodArray == null)
      {
         CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
         if (declaredMethods == null || declaredMethods.length == 0)
            methodArray = new MutableMethodInfo[0];
         else
         {
            synchronized (methods)
            {
               for (int i = 0; i < declaredMethods.length; ++i)
                  generateMethodInfo(declaredMethods[i]);
               Collection<JavassistMethodInfo> methodCollection = methods.values();
               methodArray = methodCollection.toArray(new MutableMethodInfo[methodCollection.size()]);
            }
         }
      }
      return methodArray;
   }

   public boolean isArray()
   {
      return getCtClass().isArray();
   }

 //TODO: need to change the use of getType() here
   public boolean isCollection()
   {
      return Collection.class.isAssignableFrom(getType());
   }

   //TODO: need to change the use of getType() here
   public boolean isMap()
   {
      return Map.class.isAssignableFrom(getType());
   }

   public boolean isAnnotation()
   {
      return getCtClass().isAnnotation();
   }

   public boolean isEnum()
   {
      return getCtClass().isEnum();
   }

   public boolean isPrimitive()
   {
      return getCtClass().isPrimitive();
   }

   /**
    * Get an array class
    * 
    * @param clazz the class
    * @return the array class
    */
   public static Class<?> getArrayClass(Class<?> clazz)
   {
      return Array.newInstance(clazz, 0).getClass();
   }

   public TypeInfo getArrayType()
   {
      Class<?> arrayClass = getArrayClass(getType());
      return factory.getTypeInfo(arrayClass);
   }

   @SuppressWarnings("deprecation")
   public Object newArrayInstance(int size) throws Throwable
   {
      if (isArray() == false)
         throw new ClassCastException(this + " is not an array.");
      return Array.newInstance(getComponentType().getType(), size);
   }

   @SuppressWarnings("deprecation")
   public boolean isAssignableFrom(TypeInfo info)
   {
      if (info == null)
         throw new NullPointerException("Parameter info cannot be null!");

      return getType().isAssignableFrom(info.getType());
   }

   public boolean isInstance(Object object)
   {
      return getType().isInstance(object);
   }

   public TypeInfoFactory getTypeInfoFactory()
   {
      return factory;
   }

   public Object convertValue(Object value) throws Throwable
   {
      return ValueConvertor.convertValue(getType(), value);
   }

   public Object convertValue(Object value, boolean replaceProperties) throws Throwable
   {
      return ValueConvertor.convertValue(getType(), value, replaceProperties);
   }

   public Object convertValue(Object value, boolean replaceProperties, boolean trim) throws Throwable
   {
      return ValueConvertor.convertValue(getType(), value, replaceProperties, trim);
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
      if (obj == null || obj instanceof TypeInfo == false)
         return false;

      TypeInfo other = (TypeInfo) obj;
      return getName().equals(other.getName());
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
    * Get the factory
    * 
    * @return the factory
    */
   protected JavassistTypeInfoFactoryImpl getFactory()
   {
      return factory;
   }

   /**
    * Generate constructor info
    * 
    * @param constructor the constructor
    * @return the constructor info
    */
   protected MutableConstructorInfo generateConstructorInfo(CtConstructor constructor)
   {
      try
      {
         CtClass[] parameterTypes = constructor.getParameterTypes();
         String[] params = new String[parameterTypes.length];
         for (int i = 0; i < params.length; ++i)
            params[i] = parameterTypes[i].getName();
         SignatureKey key = new SignatureKey(null, params);
         JavassistConstructorInfo info = new JavassistConstructorInfo(factory, this, constructor);
         synchronized (constructors)
         {
            constructors.put(key, info);
         }
         return info;
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for constructor of " + getName(), e);
      }
   }

   /**
    * Generate constructor info
    * 
    * @param key the key
    * @return the constructor info
    */
   protected MutableConstructorInfo generateConstructorInfo(SignatureKey key)
   {
      CtClass[] params = getParameterTypes(key);
      try
      {
         CtConstructor ctConstructor = ctClass.getDeclaredConstructor(params);
         return generateConstructorInfo(ctConstructor);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseMethodNotFound("for constructor " + getName(), e);
      }
   }

   /**
    * Generate field info
    * 
    * @param field the field
    * @return the field info
    */
   protected MutableFieldInfo generateFieldInfo(CtField field)
   {
      JavassistFieldInfo info = new JavassistFieldInfo(factory, this, field);
      synchronized (fields)
      {
         fields.put(field.getName(), info);
      }
      return info;
   }

   /**
    * Generate method info
    * 
    * @param key the key
    * @return the method info
    */
   protected MutableMethodInfo generateMethodInfo(SignatureKey key)
   {
      CtClass[] params = getParameterTypes(key);
      try
      {
         CtMethod ctMethod = ctClass.getDeclaredMethod(key.name, params);
         return generateMethodInfo(key, ctMethod);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseMethodNotFound("for method " + key.name, e);
      }
   }

   /**
    * Generate method info
    * 
    * @param method the method
    * @return the method info
    */
   protected MutableMethodInfo generateMethodInfo(CtMethod method)
   {
      try
      {
         CtClass[] parameterTypes = method.getParameterTypes();
         String[] params = new String[parameterTypes.length];
         for (int i = 0; i < params.length; ++i)
            params[i] = parameterTypes[i].getName();
         SignatureKey key = new SignatureKey(method.getName(), params);
         return generateMethodInfo(key, method);
      }
      catch (NotFoundException e)
      {
         throw JavassistTypeInfoFactoryImpl.raiseClassNotFound("for method " + method.getName(), e);
      }
   }

   /**
    * Generate method info
    * 
    * @param key the key
    * @param method the method
    * @return the method info
    */
   protected MutableMethodInfo generateMethodInfo(SignatureKey key, CtMethod method)
   {
      JavassistMethodInfo info = new JavassistMethodInfo(factory, this, method);
      synchronized (methods)
      {
         methods.put(key, info);
      }
      return info;
   }

   /**
    * Get the parameter types
    * 
    * @param key the key
    * @return the parameter types
    */
   protected CtClass[] getParameterTypes(SignatureKey key)
   {
      if (key.params == null)
         return null;

      CtClass[] result = new CtClass[key.params.length];
      for (int i = 0; i < key.params.length; ++i)
         result[i] = factory.getCtClass(key.params[i]);

      return result;
   }

   protected Object getAnnotatedTarget()
   {
      return ctClass;
   }
   
   @Override
   public AnnotationValue[] getAnnotations()
   {
      return getAnnotations(ctClass);
   }

   @Override
   public JavassistInheritableAnnotationHolder getSuperHolder()
   {
      try
      {
         CtClass zuper = ctClass.getSuperclass();
         if (zuper == null)
         {
            return null;
         }
         return (JavassistTypeInfo)factory.getTypeInfo(zuper);
      }
      catch (NotFoundException e)
      {
         throw new RuntimeException(e);
      }
   }

   public TypeInfo[] getActualTypeArguments()
   {
      return null;
   }

   public TypeInfo getOwnerType()
   {
      return null;
   }

   public ClassInfo getRawType()
   {
      return this;
   }

   public TypeInfo getComponentType()
   {
      return null;
   }

   public TypeInfo getKeyType()
   {
      return null;
   }

   public TypeInfo getValueType()
   {
      return null;
   }

   public PackageInfo getPackage()
   {
      if (packageInfo == null)
      {
         String packageName = ctClass.getPackageName();
         if (packageName != null)
            packageInfo = new PackageInfoImpl(ctClass.getPackageName());
      }
      // TODO package annotations
      return packageInfo;
   }

   public void setAttachment(String name, Object attachment)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      synchronized (this)
      {
         if (attachments == null)
         {
            if (attachment == null)
               return;
            attachments = new TypeInfoAttachments();;
         }
      }
      if (attachment == null)
         attachments.removeAttachment(name);
      else
         attachments.addAttachment(name, attachment);
   }

   public <T> T getAttachment(Class<T> expectedType)
   {
      if (expectedType == null)
         throw new IllegalArgumentException("Null expectedType");
      Object result = getAttachment(expectedType.getName());
      if (result == null)
         return null;
      return expectedType.cast(result);
   }

   public Object getAttachment(String attachmentName)
   {
      if (attachmentName == null)
         throw new IllegalArgumentException("Null name");
      synchronized (this)
      {
         if (attachments == null)
            return null;
      }
      return attachments.getAttachment(attachmentName);
   }
   
    CtClass getCtClass()
   {
      return ctClass;
   }
   
   protected void clearMethodCache()
   {
      if(methodArray != null)
         methodArray = null;
      if(methods.size() > 0)
         methods.clear();
   }
   
   protected void clearConstructorCache()
   {
      if(constructorArray != null)
         constructorArray = null;
   }
   
   protected void clearFieldCache()
   {
      if(fieldArray != null)
         fieldArray = null;
   }
   
   public void addConstructor(MutableConstructorInfo mci)
   {
      if(mci instanceof JavassistConstructorInfo)
      {
         try
         {
            ctClass.addConstructor(((JavassistConstructorInfo) mci).getCtConstructor());
            clearConstructorCache();
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
   }

   public void addField(MutableFieldInfo mfi)
   {
      if(mfi instanceof JavassistFieldInfo)
      {
         try
         {
            ctClass.addField(((JavassistFieldInfo) mfi).getCtField());
            clearFieldCache();
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
      
   }

   public void addMethod(MutableMethodInfo mmi)
   {
      if(mmi instanceof JavassistMethodInfo)
      {
         try
         {
            ctClass.addMethod(((JavassistMethodInfo) mmi).getCtMethod());
            clearMethodCache();
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
   }

   public MutableConstructorInfo createMutableConstructor(Body body)
   {
      try
      {
         CtConstructor constructor = CtNewConstructor.make(body.getBody(), ctClass);
         return new JavassistConstructorInfo(factory, this, constructor);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, String[] parameters,
         String[] exceptions)
   {
      try
      {
         CtConstructor constructor = CtNewConstructor.make(JavassistUtil.toCtClass(parameters), 
               JavassistUtil.toCtClass(exceptions), ctClass);
         constructor.setModifiers(modifier.getModifiers());
         return new JavassistConstructorInfo(factory, this, constructor);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, ClassInfo[] parameters,
         ClassInfo[] exceptions)
   {
      try
      {
         CtConstructor constructor = CtNewConstructor.make(JavassistUtil.toCtClass(parameters), 
               JavassistUtil.toCtClass(exceptions), ctClass);
         constructor.setModifiers(modifier.getModifiers());
         return new JavassistConstructorInfo(factory, this, constructor);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, Body body, String[] parameters,
         String[] exceptions)
   {
      try
      {
         CtConstructor constructor = CtNewConstructor.make(JavassistUtil.toCtClass(parameters), 
               JavassistUtil.toCtClass(exceptions), body.getBody(), ctClass);
         constructor.setModifiers(modifier.getModifiers());
         return new JavassistConstructorInfo(factory, this, constructor);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, Body body, ClassInfo[] parameters,
         ClassInfo[] exceptions)
   {
      try
      {
         CtConstructor constructor = CtNewConstructor.make(JavassistUtil.toCtClass(parameters), 
               JavassistUtil.toCtClass(exceptions), body.getBody(), ctClass);
         constructor.setModifiers(modifier.getModifiers());
         return new JavassistConstructorInfo(factory, this, constructor);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableFieldInfo createMutableField(ModifierInfo modifier, String type, String fieldName)
   {
      try
      {
         CtField field = new CtField(JavassistUtil.toCtClass(type), fieldName, ctClass);
         field.setModifiers(modifier.getModifiers());
         return new JavassistFieldInfo(factory, this, field);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableFieldInfo createMutableField(ModifierInfo modifier, ClassInfo type, String fieldName)
   {
      try
      {
         CtField field = new CtField(JavassistUtil.toCtClass(type), fieldName, ctClass);
         field.setModifiers(modifier.getModifiers());
         return new JavassistFieldInfo(factory, this, field);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableMethodInfo createMutableMethod(Body body)
   {
      try
      {
         CtMethod method = CtNewMethod.make(body.getBody(), ctClass);
         return new JavassistMethodInfo(factory, this, method);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableMethodInfo createMutableMethod(ModifierInfo modifier, String returnType, String methodName,
         String[] parameters, String[] exceptions)
   {
      try
      {
         CtMethod method = CtNewMethod.make(modifier.getModifiers(), JavassistUtil.toCtClass(returnType),
               methodName, JavassistUtil.toCtClass(parameters), JavassistUtil.toCtClass(exceptions),
               new InsertBeforeJavassistBody("{}").getBody(), ctClass);
         return new JavassistMethodInfo(factory, this, method);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableMethodInfo createMutableMethod(ModifierInfo modifier, ClassInfo returnType, String methodName,
         ClassInfo[] parameters, ClassInfo[] exceptions)
   {
      try
      {
         CtMethod method = CtNewMethod.make(modifier.getModifiers(), JavassistUtil.toCtClass(returnType),
               methodName, JavassistUtil.toCtClass(parameters), JavassistUtil.toCtClass(exceptions),
               new InsertBeforeJavassistBody("{}").getBody(), ctClass);
         return new JavassistMethodInfo(factory, this, method);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableMethodInfo createMutableMethod(ModifierInfo modifier, String returnType, String methodName, Body body,
         String[] parameters, String[] exceptions)
   {
      try
      {
         CtMethod method = CtNewMethod.make(modifier.getModifiers(), JavassistUtil.toCtClass(returnType),
               methodName, JavassistUtil.toCtClass(parameters), JavassistUtil.toCtClass(exceptions),
               body.getBody(), ctClass);
         return new JavassistMethodInfo(factory, this, method);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public MutableMethodInfo createMutableMethod(ModifierInfo modifier, ClassInfo returnType, String methodName, Body body,
         ClassInfo[] parameters, ClassInfo[] exceptions)
   {
      try
      {
         CtMethod method = CtNewMethod.make(modifier.getModifiers(), JavassistUtil.toCtClass(returnType),
               methodName, JavassistUtil.toCtClass(parameters), JavassistUtil.toCtClass(exceptions),
               body.getBody(), ctClass);
         return new JavassistMethodInfo(factory, this, method);
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }

   public void removeConstructor(MutableConstructorInfo mci)
   {
      if(mci instanceof JavassistConstructorInfo)
         try
         {
            ctClass.removeConstructor(((JavassistConstructorInfo) mci).getCtConstructor());
            clearConstructorCache();
         }
         catch (NotFoundException e)
         {
            throw new org.jboss.reflect.spi.NotFoundException(e.toString());
         }
   }

   public void removeField(MutableFieldInfo mfi)
   {
      if(mfi instanceof JavassistFieldInfo)
         try
         {
            ctClass.removeField(((JavassistFieldInfo) mfi).getCtField());
            clearFieldCache();
         }
         catch (NotFoundException e)
         {
            throw new org.jboss.reflect.spi.NotFoundException(e.toString());
         }
   }

   public void removeMethod(MutableMethodInfo mmi)
   {
      if(mmi instanceof JavassistMethodInfo)
         try
         {
            ctClass.removeMethod(((JavassistMethodInfo) mmi).getCtMethod());
            clearMethodCache();
         }
         catch (NotFoundException e)
         {
            throw new org.jboss.reflect.spi.NotFoundException(e.toString());
         }
   }

   public byte[] toByteCode()
   {
      try
      {
         return ctClass.toBytecode();
      }
      catch (IOException e)
      {
         throw new RuntimeException(e.toString());
      }
      catch (CannotCompileException e)
      {
         throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
      }
   }
}
