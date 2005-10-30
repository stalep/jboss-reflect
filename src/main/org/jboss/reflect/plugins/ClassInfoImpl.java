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

import java.lang.reflect.Modifier;
import java.util.HashMap;

import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactoryImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * Class info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ClassInfoImpl extends InheritableAnnotationHolder implements ClassInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3545798779904340792L;

   /** Marker for generation */
   static final ClassInfo UNKNOWN_CLASS = new UnknownClassInfo();

   /** Marker for generation */
   static final InterfaceInfo[] UNKNOWN_INTERFACES = new InterfaceInfo[0];

   /** Marker for generation */
   static final ConstructorInfo[] UNKNOWN_CONSTRUCTORS = new ConstructorInfo[0];

   /** Marker for generation */
   static final MethodInfo[] UNKNOWN_METHODS = new MethodInfo[0];

   /** Marker for generation */
   private static final FieldInfo[] UNKNOWN_FIELDS = new FieldInfo[0];
   
   /** The typeinfo factory */
   protected IntrospectionTypeInfoFactoryImpl typeInfoFactory;
   
   /** The class name */
   protected String name;
   
   /** The type */
   protected Class type;
   
   /** The class modifiers */
   protected int modifiers;
   
   /** The interfaces */
   protected InterfaceInfo[] interfaces = UNKNOWN_INTERFACES;
   
   /** The methods */
   protected MethodInfo[] methods = UNKNOWN_METHODS;
   
   /** The fields */
   protected FieldInfo[] fields = UNKNOWN_FIELDS;
   
   /** Field map Map<String, FieldInfo> */
   protected HashMap fieldMap;

   /** The super class */
   protected ClassInfo superclass = UNKNOWN_CLASS;

   /** The constructor info */
   protected ConstructorInfo[] constructors = UNKNOWN_CONSTRUCTORS; 

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
            if (methods[i].getParameterTypes().length == parameters.length)
            {
               boolean ok = true;
               for (int j = 0; j < parameters.length; j++)
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
    * Set the type
    * 
    * @param type the class
    */
   public void setType(Class type)
   {
      this.type = type;
   }

   /**
    * Set the typeinfo factory
    * 
    * @param the typeinfo factory
    */
   public void setTypeInfoFactory(IntrospectionTypeInfoFactoryImpl typeInfoFactory)
   {
      this.typeInfoFactory = typeInfoFactory;
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
         for (int i = 0; i < fields.length; i++)
            fields[i].declaringClass = this;
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
   public void setSuperclass(ClassInfo superInfo)
   {
      this.superclass = superInfo;
   }


   public boolean isInterface()
   {
      return false;
   }

   public InterfaceInfo[] getInterfaces()
   {
      if (interfaces == UNKNOWN_INTERFACES)
         setInterfaces(typeInfoFactory.getInterfaces(type));
      return interfaces;
   }
   
   public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
   {
      return findMethod(methods, name, parameters);
   }

   public MethodInfo[] getDeclaredMethods()
   {
      if (methods == UNKNOWN_METHODS)
         setDeclaredMethods(typeInfoFactory.getMethods(type, this));
      return methods;
   }

   public FieldInfo getDeclaredField(String name)
   {
      if (fields == UNKNOWN_FIELDS)
         setDeclaredFields(typeInfoFactory.getFields(type, this));
      return (FieldInfo) fieldMap.get(name);
   }

   public FieldInfo[] getDeclaredFields()
   {
      if (fields == UNKNOWN_FIELDS)
         setDeclaredFields(typeInfoFactory.getFields(type, this));
      return fields;
   }

   public ConstructorInfo[] getDeclaredConstructors()
   {
      if (constructors == UNKNOWN_CONSTRUCTORS)
         setDeclaredConstructors(typeInfoFactory.getConstructors(type, this));
      return constructors;
   }

   public ClassInfo getSuperclass()
   {
      if (superclass == UNKNOWN_CLASS)
         setSuperclass(typeInfoFactory.getSuperClass(type));
      return superclass;
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

   public Class getType()
   {
      return type;
   }
   
   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(name);
   }

   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof ClassInfoImpl == false)
         return false;

      final ClassInfoImpl other = (ClassInfoImpl) obj;

      if (name != null ? name.equals(other.name) == false : other.name != null)
         return false;
      return true;
   }

   public int hashCode()
   {
      return (name != null ? name.hashCode() : 0);
   }
   
   static class UnknownClassInfo implements ClassInfo
   {
      public ConstructorInfo[] getDeclaredConstructors()
      {
         return null;
      }

      public FieldInfo getDeclaredField(String name)
      {
         return null;
      }

      public FieldInfo[] getDeclaredFields()
      {
         return null;
      }

      public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
      {
         return null;
      }

      public MethodInfo[] getDeclaredMethods()
      {
         return null;
      }

      public InterfaceInfo[] getInterfaces()
      {
         return null;
      }

      public String getName()
      {
         return null;
      }

      public ClassInfo getSuperclass()
      {
         return null;
      }

      public boolean isInterface()
      {
         return false;
      }

      public AnnotationValue getAnnotation(String name)
      {
         return null;
      }

      public AnnotationValue[] getAnnotations()
      {
         return null;
      }

      public boolean isAnnotationPresent(String name)
      {
         return false;
      }

      public String toShortString()
      {
         return null;
      }

      public void toShortString(JBossStringBuilder buffer)
      {
      }

      public Class getType()
      {
         return null;
      }

      public int getModifiers()
      {
         return 0;
      }

      public boolean isPublic()
      {
         return false;
      }

      public boolean isStatic()
      {
         return false;
      }
      
      public Object clone()
      {
         return null;
      }
   }
}
