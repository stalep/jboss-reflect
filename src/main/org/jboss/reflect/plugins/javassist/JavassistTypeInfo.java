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

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javassist.CtClass;
import javassist.CtConstructor;
import javassist.CtField;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.jboss.reflect.plugins.ValueConvertor;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * JavassistTypeInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistTypeInfo extends JavassistInheritableAnnotationHolder implements ClassInfo, InterfaceInfo
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
   private ConstructorInfo[] constructorArray;

   /** The fields */
   private Map<String, JavassistFieldInfo> fields = new ConcurrentHashMap<String, JavassistFieldInfo>();

   /** The fields */
   private FieldInfo[] fieldArray;

   /** The methods */
   private Map<SignatureKey, JavassistMethodInfo> methods = new ConcurrentHashMap<SignatureKey, JavassistMethodInfo>();

   /** The methods */
   private MethodInfo[] methodArray;

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

   public int getModifiers()
   {
      return ctClass.getModifiers();
   }

   public boolean isPublic()
   {
      return Modifier.isPublic(getModifiers());
   }

   public boolean isStatic()
   {
      return Modifier.isStatic(getModifiers());
   }

   public Class<? extends Object> getType()
   {
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

   public ConstructorInfo[] getDeclaredConstructors()
   {
      if (constructorArray == null)
      {
         CtConstructor[] declaredConstructors = ctClass.getDeclaredConstructors();
         if (declaredConstructors == null || declaredConstructors.length == 0)
            constructorArray = new ConstructorInfo[0];
         else
         {
            synchronized (constructors)
            {
               for (int i = 0; i < declaredConstructors.length; ++i)
                  generateConstructorInfo(declaredConstructors[i]);
               Collection<JavassistConstructorInfo> constructorCollection = constructors.values();
               constructorArray = constructorCollection.toArray(new ConstructorInfo[constructorCollection.size()]);
            }
         }
      }
      return constructorArray;
   }

   public ConstructorInfo getDeclaredConstructor(TypeInfo[] parameters)
   {
      SignatureKey key = new SignatureKey(null, parameters);
      synchronized (constructors)
      {
         ConstructorInfo constructor = constructors.get(key);
         if (constructor != null)
            return constructor;
      }
      if (constructorArray != null)
         return null;
      return generateConstructorInfo(key);
   }

   public FieldInfo getDeclaredField(String name)
   {
      synchronized (fields)
      {
         FieldInfo field = fields.get(name);
         if (field != null)
            return field;
      }
      if (fieldArray != null)
         return null;
      try
      {
         CtField field = ctClass.getDeclaredField(name);
         if (field == null)
            return null;
         return generateFieldInfo(field);
      }
      catch (NotFoundException e)
      {
         return null;
      }
   }

   public FieldInfo[] getDeclaredFields()
   {
      if (fieldArray == null)
      {
         CtField[] declaredFields = ctClass.getDeclaredFields();
         if (declaredFields == null || declaredFields.length == 0)
            fieldArray = new FieldInfo[0];
         else
         {
            synchronized (fields)
            {
               for (int i = 0; i < declaredFields.length; ++i)
                  generateFieldInfo(declaredFields[i]);
               Collection<JavassistFieldInfo> fieldCollection = fields.values();
               fieldArray = fieldCollection.toArray(new FieldInfo[fieldCollection.size()]);
            }
         }
      }
      return fieldArray;
   }

   public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
   {
      SignatureKey key = new SignatureKey(name, parameters);
      synchronized (methods)
      {
         MethodInfo method = methods.get(key);
         if (method != null)
            return method;
      }
      if (methodArray != null)
         return null;
      return generateMethodInfo(key);
   }

   public MethodInfo[] getDeclaredMethods()
   {
      if (methodArray == null)
      {
         CtMethod[] declaredMethods = ctClass.getDeclaredMethods();
         if (declaredMethods == null || declaredMethods.length == 0)
            methodArray = new MethodInfo[0];
         else
         {
            synchronized (methods)
            {
               for (int i = 0; i < declaredMethods.length; ++i)
                  generateMethodInfo(declaredMethods[i]);
               Collection<JavassistMethodInfo> methodCollection = methods.values();
               methodArray = methodCollection.toArray(new MethodInfo[methodCollection.size()]);
            }
         }
      }
      return methodArray;
   }

   public boolean isArray()
   {
      return getType().isArray();
   }

   public boolean isEnum()
   {
      return getType().isEnum();
   }

   public boolean isPrimitive()
   {
      return getType().isPrimitive();
   }

   /**
    * Get an array class
    * 
    * TODO JBMICROCONT-123 there must be a better way to do this!
    * @param clazz the class
    * @param depth the depth
    * @return the array class
    */
   public static Class getArrayClass(Class clazz, int depth)
   {
      return Array.newInstance(clazz, depth).getClass();
   }

   public TypeInfo getArrayType(int depth)
   {
      Class arrayClass = getArrayClass(getType(), depth);
      return factory.getTypeInfo(arrayClass);
   }

   public Object[] newArrayInstance(int size) throws Throwable
   {
      Class clazz = getType();
      if (clazz.isArray() == false)
         throw new ClassCastException(clazz + " is not an array.");
      return (Object[]) Array.newInstance(clazz.getComponentType(), size);
   }

   public Object convertValue(Object value) throws Throwable
   {
      return ValueConvertor.convertValue(getType(), value);
   }

   protected int getHashCode()
   {
      return getName().hashCode();
   }

   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof TypeInfo == false)
         return false;

      TypeInfo other = (TypeInfo) obj;
      return getName().equals(other.getName());
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
   protected ConstructorInfo generateConstructorInfo(CtConstructor constructor)
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
   protected ConstructorInfo generateConstructorInfo(SignatureKey key)
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
   protected FieldInfo generateFieldInfo(CtField field)
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
   protected MethodInfo generateMethodInfo(SignatureKey key)
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
   protected MethodInfo generateMethodInfo(CtMethod method)
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
   protected MethodInfo generateMethodInfo(SignatureKey key, CtMethod method)
   {
      JavassistMethodInfo info = new JavassistMethodInfo(factory, this, key, method);
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

}
