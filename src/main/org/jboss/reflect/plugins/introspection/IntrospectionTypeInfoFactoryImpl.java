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
package org.jboss.reflect.plugins.introspection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.security.AccessController;
import java.security.PrivilegedAction;

import org.jboss.reflect.plugins.AnnotationAttributeImpl;
import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.plugins.AnnotationInfoImpl;
import org.jboss.reflect.plugins.AnnotationValueFactory;
import org.jboss.reflect.plugins.ArrayInfoImpl;
import org.jboss.reflect.plugins.ClassInfoHelper;
import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.plugins.ConstructorInfoImpl;
import org.jboss.reflect.plugins.EnumConstantInfoImpl;
import org.jboss.reflect.plugins.EnumInfoImpl;
import org.jboss.reflect.plugins.FieldInfoImpl;
import org.jboss.reflect.plugins.MethodInfoImpl;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.NumberInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.collection.temp.WeakTypeCache;

/**
 * An introspection type factory.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class IntrospectionTypeInfoFactoryImpl extends WeakTypeCache<TypeInfo> implements TypeInfoFactory, AnnotationHelper, ClassInfoHelper
{
   final static AnnotationValue[] NO_ANNOTATIONS = new AnnotationValue[0];

   /**
    * Generate the type info for a class
    *
    * @param clazz the class
    * @param info the class info
    */
   public void generateTypeInfo(Class clazz, ClassInfoImpl info)
   {
      // Everything is done lazily
   }

   public ClassInfoImpl getSuperClass(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      ClassInfoImpl superType = null;
      if (clazz.isInterface() == false)
      {
         Class superClazz = clazz.getSuperclass();
         if (superClazz != null)
            superType = (ClassInfoImpl) getTypeInfo(superClazz);
      }
      return superType;
   }

   public ClassInfo getGenericSuperClass(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      ClassInfo superType = null;
      if (clazz.isInterface() == false)
      {
         Type superClazz = clazz.getGenericSuperclass();
         if (superClazz != null)
            superType = (ClassInfo) getTypeInfo(superClazz);
      }
      return superType;
   }

   public AnnotationValue[] getAnnotations(Object obj)
   {
      Annotation[] annotations;
      if (obj instanceof AccessibleObject)
      {
         annotations = readAnnotations((AccessibleObject)obj);
      }
      else if (obj instanceof Class)
      {
         annotations = readAnnotations((Class)obj);
      }
      else
      {
         throw new RuntimeException("Attempt was made to read annotations from unsupported type " + obj.getClass().getName() + ": " + obj);
      }

      if (annotations.length == 0)
      {
         return NO_ANNOTATIONS;
      }

      AnnotationValue[] annotationValues = new AnnotationValue[annotations.length];
      for (int i = 0 ; i < annotations.length ; i++)
      {
         AnnotationInfo info = (AnnotationInfo)getTypeInfo(annotations[i].annotationType());
         annotationValues[i] = createAnnotationValue(info, annotations[i]);
      }
      return annotationValues;
   }

   public AnnotationValue createAnnotationValue(AnnotationInfo info, Object ann)
   {
      return AnnotationValueFactory.createAnnotationValue(this, this, info, ann);
   }

   public ConstructorInfoImpl[] getConstructors(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      ReflectConstructorInfoImpl[] infos = null;
      if (clazz.isInterface() == false)
      {
         Constructor[] constructors = getDeclaredConstructors(clazz);
         if (constructors != null && constructors.length > 0)
         {
            infos = new ReflectConstructorInfoImpl[constructors.length];
            for (int i = 0; i < constructors.length; ++i)
            {
               AnnotationValue[] annotations = getAnnotations(constructors[i]);
               
               Type[] genericParameterTypes = constructors[i].getGenericParameterTypes();

               // HACK: This is to workaround a bug in Sun's compiler related to enum constructors
               //       having no generic parameters?
               Type[] parameterTypes = constructors[i].getParameterTypes(); 
               if (genericParameterTypes.length != parameterTypes.length)
                  genericParameterTypes = parameterTypes;

               infos[i] = new ReflectConstructorInfoImpl(annotations, getTypeInfos(genericParameterTypes), getParameterAnnotations(constructors[i].getParameterAnnotations()), getClassInfos(constructors[i].getGenericExceptionTypes()), constructors[i].getModifiers(), (ClassInfo) getTypeInfo(constructors[i].getDeclaringClass()));
               infos[i].setConstructor(constructors[i]);
            }
         }
      }
      return infos;
   }

   public FieldInfoImpl[] getFields(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Field[] fields = getDeclaredFields(clazz);
      if (fields == null || fields.length == 0)
         return null;

      ReflectFieldInfoImpl[] infos = new ReflectFieldInfoImpl[fields.length];
      for (int i = 0; i < fields.length; ++i)
      {
         AnnotationValue[] annotations = getAnnotations(fields[i]);
         infos[i] = new ReflectFieldInfoImpl(annotations, fields[i].getName(), getTypeInfo(fields[i].getGenericType()), fields[i].getModifiers(), (ClassInfo) getTypeInfo(fields[i].getDeclaringClass()));
         infos[i].setField(fields[i]);
      }

      return infos;
   }

   public MethodInfoImpl[] getMethods(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Method[] methods = getDeclaredMethods(clazz);
      if (methods == null || methods.length == 0)
         return null;

      ReflectMethodInfoImpl[] infos = new ReflectMethodInfoImpl[methods.length];
      for (int i = 0; i < methods.length; ++i)
      {
         AnnotationValue[] annotations = getAnnotations(methods[i]);
         infos[i] = new ReflectMethodInfoImpl(annotations, methods[i].getName(), getTypeInfo(methods[i].getGenericReturnType()), getTypeInfos(methods[i].getGenericParameterTypes()), getParameterAnnotations(methods[i].getParameterAnnotations()), getClassInfos(methods[i].getGenericExceptionTypes()), methods[i].getModifiers(), (ClassInfo) getTypeInfo(methods[i].getDeclaringClass()));
         infos[i].setMethod(methods[i]);
      }
      return infos;
   }

   public InterfaceInfo[] getInterfaces(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Class[] interfaces = clazz.getInterfaces();
      if (interfaces == null || interfaces.length == 0)
         return null;

      InterfaceInfo[] infos = new InterfaceInfo[interfaces.length];
      for (int i = 0; i < interfaces.length; ++i)
         infos[i] = (InterfaceInfo) getTypeInfo(interfaces[i]);

      return infos;
   }

   public InterfaceInfo[] getGenericInterfaces(ClassInfoImpl classInfo)
   {
      Class clazz = classInfo.getType();
      Type[] interfaces = clazz.getGenericInterfaces();
      if (interfaces == null || interfaces.length == 0)
         return null;

      InterfaceInfo[] infos = new InterfaceInfo[interfaces.length];
      for (int i = 0; i < interfaces.length; ++i)
         infos[i] = (InterfaceInfo) getTypeInfo(interfaces[i]);

      return infos;
   }

   /**
    * Get the type infos for some classes
    *
    * @param classes the classes
    * @return the type infos
    */
   public TypeInfo[] getTypeInfos(Type[] classes)
   {
      if (classes == null || classes.length == 0)
         return null;

      TypeInfo[] result = new TypeInfo[classes.length];
      for (int i = 0; i < classes.length; ++i)
         result[i] = getTypeInfo(classes[i]);
      return result;
   }

   /**
    * Get the class infos for some classes
    *
    * @param classes the classes
    * @return the class infos
    */
   public ClassInfo[] getClassInfos(Type[] classes)
   {
      if (classes == null || classes.length == 0)
         return null;

      ClassInfo[] result = new ClassInfo[classes.length];
      for (int i = 0; i < classes.length; ++i)
         result[i] = (ClassInfo) getTypeInfo(classes[i]);
      return result;
   }

   public TypeInfo getTypeInfo(Class clazz)
   {
      if (clazz == null)
         throw new IllegalArgumentException("Null class");

      TypeInfo primitive = PrimitiveInfo.valueOf(clazz.getName());
      if (primitive != null)
         return primitive;

      NumberInfo number = NumberInfo.valueOf(clazz.getName());
      if (number != null)
      {
         if (number.isInitialized() == false)
         {
            number.setDelegate(get(clazz));
         }
         return number;
      }

      return get(clazz);
   }

   public TypeInfo getTypeInfo(Type type)
   {
      if (type == null)
         throw new IllegalArgumentException("Null type");

      String name = null;
      if (type instanceof Class)
         name = ((Class) type).getName();
      if (name != null)
      {
         TypeInfo primitive = PrimitiveInfo.valueOf(((Class) type).getName());
         if (primitive != null)
            return primitive;

         NumberInfo number = NumberInfo.valueOf(((Class) type).getName());
         if (number != null)
         {
            if (number.isInitialized() == false)
            {
               number.setDelegate(get(type));
            }
            return number;
         }
      }

      return get(type);
   }

   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      if (name == null)
         throw new IllegalArgumentException("Null class name");

      TypeInfo primitive = PrimitiveInfo.valueOf(name);
      if (primitive != null)
         return primitive;

      NumberInfo number = NumberInfo.valueOf(name);
      if (number != null)
      {
         if (number.isInitialized() == false)
         {
            number.setDelegate(resolveComplexTypeInfo(cl, name));
         }
         return number;
      }

      return resolveComplexTypeInfo(cl, name);
   }

   private TypeInfo resolveComplexTypeInfo(ClassLoader cl, String name)
         throws ClassNotFoundException
   {
      if (cl == null)
         cl = Thread.currentThread().getContextClassLoader();

      Class clazz = cl.loadClass(name);
      return getTypeInfo(clazz);
   }

   protected TypeInfo instantiate(Class<?> clazz)
   {
      ClassInfoImpl result;
      if (clazz.isArray())
      {
         TypeInfo componentType = getTypeInfo(clazz.getComponentType());
         result = new ArrayInfoImpl(componentType);
      }
      else if (clazz.isEnum())
      {
         EnumInfoImpl enumInfoImpl = new EnumInfoImpl(clazz.getName(), clazz.getModifiers());
         result = enumInfoImpl;
         Field[] fields = clazz.getFields();
         EnumConstantInfoImpl[] constants = new EnumConstantInfoImpl[fields.length];
         int i = 0;
         for (Field field : fields)
            constants[i++] = new EnumConstantInfoImpl(field.getName(), enumInfoImpl);
         enumInfoImpl.setEnumConstants(constants);
      }
      else if (clazz.isAnnotation())
      {
         result = new AnnotationInfoImpl(clazz.getName(), clazz.getModifiers());
         Method[] methods = getDeclaredMethods(clazz);
         AnnotationAttributeImpl[] atttributes = new AnnotationAttributeImpl[methods.length];
         for (int i = 0 ; i < methods.length ; i++)
         {
            atttributes[i] = new AnnotationAttributeImpl(methods[i].getName(), getTypeInfo(methods[i].getReturnType()), null);
         }
         ((AnnotationInfoImpl)result).setAttributes(atttributes);
      }
      else if (clazz.isInterface())
      {
         result = new ReflectClassInfoImpl(clazz.getName());
      }
      else
      {
         result = new ReflectClassInfoImpl(clazz.getName());
      }
      result.setType(clazz);
      result.setTypeInfoFactory(this);
      result.setClassInfoHelper(this);
      result.setAnnotationHelper(this);
      return result;
   }

   protected TypeInfo instantiate(ParameterizedType type)
   {
      Class rawType = (Class) type.getRawType();
      ClassInfo rawTypeInfo = (ClassInfo) getTypeInfo(rawType);
      if (rawTypeInfo instanceof ArrayInfo)
         return new ParameterizedArrayInfo(this, (ArrayInfo) rawTypeInfo, type);
      return new ParameterizedClassInfo(this, rawTypeInfo, type);
   }

   protected void generate(Class<?> clazz, TypeInfo result)
   {
      generateTypeInfo(clazz, (ClassInfoImpl) result);
   }

   protected void generate(ParameterizedType type, TypeInfo result)
   {
      // Everything is lazy
   }

   protected Constructor[] getDeclaredConstructors(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getDeclaredConstructors();
      else
      {
         PrivilegedAction<Constructor[]> action = new PrivilegedAction<Constructor[]>()
         {
            public Constructor[] run()
            {
               return clazz.getDeclaredConstructors();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }

   protected Field[] getDeclaredFields(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getDeclaredFields();
      else
      {
         PrivilegedAction<Field[]> action = new PrivilegedAction<Field[]>()
         {
            public Field[] run()
            {
               return clazz.getDeclaredFields();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }

   protected Method[] getDeclaredMethods(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getDeclaredMethods();
      else
      {
         PrivilegedAction<Method[]> action = new PrivilegedAction<Method[]>()
         {
            public Method[] run()
            {
               return clazz.getDeclaredMethods();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }

   private Annotation[] readAnnotations(final AccessibleObject ao)
   {
      if (System.getSecurityManager() == null)
         return ao.getAnnotations();
      else
      {
         PrivilegedAction<Annotation[]> action = new PrivilegedAction<Annotation[]>()
         {
            public Annotation[] run()
            {
               return ao.getAnnotations();
            }
         };
         
         return AccessController.doPrivileged(action);
      }
   }
   
   private Annotation[] readAnnotations(final Class clazz)
   {
      if (System.getSecurityManager() == null)
         return clazz.getAnnotations();
      else
      {
         PrivilegedAction<Annotation[]> action = new PrivilegedAction<Annotation[]>()
         {
            public Annotation[] run()
            {
               return clazz.getAnnotations();
            }
         };
         return AccessController.doPrivileged(action);
      }
   }
   

   protected AnnotationValue[][] getParameterAnnotations(Annotation[][] annotations)
   {
      AnnotationValue[][] annotationValues = new AnnotationValue[annotations.length][];
      for (int param = 0 ; param < annotations.length ; param++)
      {
         annotationValues[param] = new AnnotationValue[annotations[param].length];
         for (int ann = 0 ; ann < annotations[param].length ; ann++)
         {
            AnnotationInfo info = (AnnotationInfo)getTypeInfo(annotations[param][ann].annotationType());
            annotationValues[param][ann] = createAnnotationValue(info, annotations[param][ann]);
         }
      }
      return annotationValues;
   }

   public TypeInfo[] getActualTypeArguments(ParameterizedClassInfo classInfo)
   {
      ParameterizedType type = classInfo.parameterizedType;
      Type[] types = type.getActualTypeArguments();
      if (types == null)
         return null;
      
      TypeInfo[] result = new TypeInfo[types.length];
      for (int i = 0; i < types.length; ++i)
         result[i] = getTypeInfo(types[i]);
      
      return result;
   }
   
   public TypeInfo getOwnerType(ParameterizedClassInfo classInfo)
   {
      ParameterizedType type = classInfo.parameterizedType;
      Type owner = type.getOwnerType();
      if (owner == null)
         return null;
      
      return getTypeInfo(owner);
   }
}
