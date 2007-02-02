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
package org.jboss.reflect.plugins;

import java.lang.reflect.Array;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.DelegateClassInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.PackageInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.JBossStringBuilder;

/**
 * Class info
 *
 * TODO JBMICROCONT-118 fix the introspection assumption
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ClassInfoImpl extends InheritableAnnotationHolder implements ClassInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3545798779904340792L;

   /** Marker for generation */
   public static final ClassInfo UNKNOWN_CLASS = new DelegateClassInfo(null, true);

   /** Marker for generation */
   public static final ClassInfo[] UNKNOWN_CLASSES = new ClassInfo[0];

   /** Marker for generation */
   public static final TypeInfo UNKNOWN_TYPE = UNKNOWN_CLASS;

   /** Marker for generation */
   public static final TypeInfo[] UNKNOWN_TYPES = new TypeInfo[0];

   /** Marker for generation */
   public static final InterfaceInfo[] UNKNOWN_INTERFACES = new InterfaceInfo[0];

   /** Marker for generation */
   public static final ConstructorInfo[] UNKNOWN_CONSTRUCTORS = new ConstructorInfo[0];

   /** Marker for generation */
   public static final MethodInfo[] UNKNOWN_METHODS = new MethodInfo[0];

   /** Marker for generation */
   public static final FieldInfo[] UNKNOWN_FIELDS = new FieldInfo[0];
   
   /** The class name */
   protected String name;
   
   /** The class modifiers */
   protected int modifiers;
   
   /** The interfaces */
   protected InterfaceInfo[] interfaces = UNKNOWN_INTERFACES;
   
   /** The generic interfaces */
   protected InterfaceInfo[] genericInterfaces = UNKNOWN_INTERFACES;
   
   /** The methods */
   protected MethodInfo[] methods = UNKNOWN_METHODS;
   
   /** The fields */
   protected FieldInfo[] fields = UNKNOWN_FIELDS;
   
   /** Field map Map<String, FieldInfo> */
   protected HashMap<String, FieldInfo> fieldMap;

   /** The super class */
   protected ClassInfo superclass = UNKNOWN_CLASS;

   /** The generic super class */
   protected ClassInfo genericSuperclass = UNKNOWN_CLASS;

   /** The constructor info */
   protected ConstructorInfo[] constructors = UNKNOWN_CONSTRUCTORS; 

   /** The package info */
   protected PackageInfo packageInfo;
   
   /** The class info helper */
   protected ClassInfoHelper classInfoHelper;

   /** The type info factory */
   protected TypeInfoFactory typeInfoFactory;

   /** The attachments */
   private transient TypeInfoAttachments attachments;

   public TypeInfoFactory getTypeInfoFactory()
   {
      return typeInfoFactory;
   }

   public void setTypeInfoFactory(TypeInfoFactory typeInfoFactory)
   {
      this.typeInfoFactory = typeInfoFactory;
   }

   /**
    * Find a method
    * 
    * @param methods the methods
    * @param name the name
    * @param parameters the parameters
    * @return the method info
    */
   public static MethodInfo findMethod(MethodInfo[] methods, String name, TypeInfo[] parameters)
   {
      if (methods == null) return null;
      for (int i = 0; i < methods.length; i++)
      {
         if (methods[i].getName().equals(name))
         {
            final int length = (parameters != null) ? parameters.length : 0;
            if (methods[i].getParameterTypes().length == length)
            {
               boolean ok = true;
               for (int j = 0; j < length; j++)
               {
                  if (!parameters[j].equals(methods[i].getParameterTypes()[j]))
                  {
                     ok = false;
                     break;
                  }
               }
               if (ok) return methods[i];
            }
         }
      }
      return null;
   }
   
   /**
    * Find a constructor
    * 
    * @param constructors the constructors
    * @param parameters the parameters
    * @return the constructor info
    */
   public static ConstructorInfo findConstructor(ConstructorInfo[] constructors, TypeInfo[] parameters)
   {
      if (constructors == null) return null;
      for (int i = 0; i < constructors.length; i++)
      {
         final int length = (parameters != null) ? parameters.length : 0;
         if (constructors[i].getParameterTypes().length == length)
         {
            boolean ok = true;
            for (int j = 0; j < length; j++)
            {
               if (!parameters[j].equals(constructors[i].getParameterTypes()[j]))
               {
                  ok = false;
                  break;
               }
            }
            if (ok) return constructors[i];
         }
      }
      return null;
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
   
   /**
    * Create a new abstract ClassInfo.
    */
   public ClassInfoImpl()
   {
   }

   /**
    * Create a new class info
    * 
    * @param name the class name
    */
   public ClassInfoImpl(String name)
   {
      this.name = name;
   }

   /**
    * Create a new abstract ClassInfo.
    * 
    * @param name the class name
    * @param modifiers the class modifiers
    */
   public ClassInfoImpl(String name, int modifiers)
   {
      this.name = name;
      this.modifiers = modifiers;
   }

   /**
    * Create a new abstract ClassInfo.
    * 
    * @param name the class name
    * @param modifiers the class modifiers
    * @param interfaces the interfaces
    * @param superclass the super class
    */
   public ClassInfoImpl(String name, int modifiers, InterfaceInfo[] interfaces,
                        ClassInfoImpl superclass)
   {
      this.name = name;
      this.modifiers = modifiers;
      this.interfaces = interfaces;
      this.superclass = superclass;
   }

   /**
    * Set the class info helper
    * 
    * @param helper the helper
    */
   public void setClassInfoHelper(ClassInfoHelper helper)
   {
      this.classInfoHelper = helper;
   }
   
   /**
    * Set the type
    * 
    * @param type the class
    */
   public void setType(Class type)
   {
      setAnnotatedElement(type);
      if (type != null)
         modifiers = type.getModifiers();
   }
   
   /**
    * Set the interfaces
    * 
    * @param interfaces the interfaces
    */
   public void setInterfaces(InterfaceInfo[] interfaces)
   {
      this.interfaces = interfaces;
   }
   
   /**
    * Set the generic interfaces
    * 
    * @param interfaces the interfaces
    */
   public void setGenericInterfaces(InterfaceInfo[] interfaces)
   {
      this.genericInterfaces = interfaces;
   }

   /**
    * Set the declared methods
    * 
    * @param methods the methods
    */
   public void setDeclaredMethods(MethodInfoImpl[] methods)
   {
      this.methods = methods;
      if (methods != null)
      {
         for (int i = 0; i < methods.length; i++)
            methods[i].declaringClass = this;
      }
   }

   /**
    * Set the declared fields
    * 
    * @param fields the fields
    */
   public void setDeclaredFields(FieldInfoImpl[] fields)
   {
      this.fields = fields;
      if (fields != null)
      {
         fieldMap = new HashMap<String, FieldInfo>();
         for (int i = 0; i < fields.length; ++i)
         {
            fields[i].declaringClass = this;
            fieldMap.put(fields[i].getName(), fields[i]);
         }
      }
   }

   /**
    * Set the declared constructors
    * 
    * @param constructors the constructors
    */
   public void setDeclaredConstructors(ConstructorInfoImpl[] constructors)
   {
      this.constructors = constructors;
      if (constructors != null)
      {
         for (int i = 0; i < constructors.length; i++)
            constructors[i].declaringClass = this;
      }
   }

   /**
    * Set the super class
    * 
    * @param superInfo the super class
    */
   public void setSuperclass(ClassInfoImpl superInfo)
   {
      this.superclass = superInfo;
   }

   /**
    * Set the generic super class
    * 
    * @param superInfo the super class
    */
   public void setGenericSuperclass(ClassInfo superInfo)
   {
      this.genericSuperclass = superInfo;
   }

   public boolean isInterface()
   {
      return false;
   }

   public InterfaceInfo[] getInterfaces()
   {
      if (interfaces == UNKNOWN_INTERFACES)
         setInterfaces(classInfoHelper.getInterfaces(this));
      return interfaces;
   }

   public InterfaceInfo[] getGenericInterfaces()
   {
      if (genericInterfaces == UNKNOWN_INTERFACES)
         setGenericInterfaces(classInfoHelper.getGenericInterfaces(this));
      return genericInterfaces;
   }
   
   public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
   {
      if (methods == UNKNOWN_METHODS)
         setDeclaredMethods(classInfoHelper.getMethods(this));
      return findMethod(methods, name, parameters);
   }

   public MethodInfo[] getDeclaredMethods()
   {
      if (methods == UNKNOWN_METHODS)
         setDeclaredMethods(classInfoHelper.getMethods(this));
      return methods;
   }

   public FieldInfo getDeclaredField(String name)
   {
      if (fields == UNKNOWN_FIELDS)
         setDeclaredFields(classInfoHelper.getFields(this));
      if (fieldMap == null)
         return null;
      return fieldMap.get(name);
   }

   public FieldInfo[] getDeclaredFields()
   {
      if (fields == UNKNOWN_FIELDS)
         setDeclaredFields(classInfoHelper.getFields(this));
      return fields;
   }
   
   public ConstructorInfo getDeclaredConstructor(TypeInfo[] parameters)
   {
      if (methods == UNKNOWN_METHODS)
         setDeclaredConstructors(classInfoHelper.getConstructors(this));
      return findConstructor(constructors, parameters);
   }

   public ConstructorInfo[] getDeclaredConstructors()
   {
      if (constructors == UNKNOWN_CONSTRUCTORS)
         setDeclaredConstructors(classInfoHelper.getConstructors(this));
      return constructors;
   }

   public ClassInfo getSuperclass()
   {
      if (superclass == UNKNOWN_CLASS)
         setSuperclass(classInfoHelper.getSuperClass(this));
      return superclass;
   }

   public ClassInfo getGenericSuperclass()
   {
      if (genericSuperclass == UNKNOWN_CLASS)
         setGenericSuperclass(classInfoHelper.getGenericSuperClass(this));
      return genericSuperclass;
   }
   
   public int getModifiers()
   {
      return modifiers;
   }
   
   public boolean isStatic()
   {
      return Modifier.isStatic(modifiers);
   }
   
   public boolean isPublic()
   {
      return Modifier.isPublic(modifiers);
   }

   public String getName()
   {
      return name;
   }

   public String getSimpleName()
   {
      return getType().getSimpleName();
   }

   @Deprecated
   public Class<? extends Object> getType()
   {
      return (Class<? extends Object>) annotatedElement;
   }
   
   public Object convertValue(Object value) throws Throwable
   {
      return ValueConvertor.convertValue(getType(), value);
   }

   public Object convertValue(Object value, boolean replaceProperties) throws Throwable
   {
      return ValueConvertor.convertValue(getType(), value, replaceProperties);
   }

   public boolean isArray()
   {
      return getType().isArray();
   }

   public boolean isCollection()
   {
      return Collection.class.isAssignableFrom(getType());
   }

   public boolean isMap()
   {
      return Map.class.isAssignableFrom(getType());
   }

   public boolean isAnnotation()
   {
      return getType().isAnnotation();
   }

   public boolean isEnum()
   {
      return getType().isEnum();
   }

   public boolean isPrimitive()
   {
      return false;
   }

   public TypeInfo getArrayType(int depth)
   {
      Class arrayClass = getArrayClass(getType(), depth);
      return classInfoHelper.getTypeInfo(arrayClass);
   }

   public Object[] newArrayInstance(int size) throws Throwable
   {
      Class clazz = getType();
      if (clazz.isArray() == false)
         throw new ClassCastException(clazz + " is not an array.");
      return (Object[]) Array.newInstance(clazz.getComponentType(), size);
   }

   public boolean isAssignableFrom(TypeInfo info)
   {
      if (info == null)
      {
         throw new NullPointerException("Parameter info cannot be null!");
      }
      return getType().isAssignableFrom(info.getType());
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

   public PackageInfo getPackage()
   {
      if (packageInfo == null)
         packageInfo = classInfoHelper.getPackage(this);
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

   public Object getAttachment(String name)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      synchronized (this)
      {
         if (attachments == null)
            return null;
      }
      return attachments.getAttachment(name);
   }

   @Override
   protected InheritableAnnotationHolder getSuperHolder()
   {
      return (ClassInfoImpl) getSuperclass();
   }
   
   @Override
   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(getName());
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof ClassInfo == false)
         return false;

      final ClassInfo other = (ClassInfo) obj;

      String name = getName();
      if (name != null ? name.equals(other.getName()) == false : other.getName() != null)
         return false;
      return true;
   }

   @Override
   public int hashCode()
   {
      return (name != null ? name.hashCode() : 0);
   }
}
