/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.util.HashMap;

import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.ConstructorInfo;
import org.jboss.reflect.FieldInfo;
import org.jboss.reflect.InterfaceInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.TypeInfo;

/**
 * Class info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ClassInfoImpl extends InheritableAnnotationHolder implements ClassInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The class name */
   protected String name;
   
   /** The class modifiers */
   protected int modifiers;
   
   /** The interfaces */
   protected InterfaceInfo[] interfaces;
   
   /** The methods */
   protected MethodInfo[] methods;
   
   /** The fields */
   protected FieldInfo[] fields;
   
   /** Field map Map<String, FieldInfo> */
   protected HashMap fieldMap;

   /** The super class */
   protected ClassInfo superclass;

   /** The constructor info */
   protected ConstructorInfo[] constructors; 

   // Static --------------------------------------------------------

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
   
   // Constructors --------------------------------------------------

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
    * @param annotations the annotations
    */
   public ClassInfoImpl(String name, int modifiers, InterfaceInfo[] interfaces, 
                    ClassInfoImpl superclass, AnnotationValue[] annotations)
   {
      super(annotations);
      this.name = name;
      this.modifiers = modifiers;
      this.interfaces = interfaces;
      this.superclass = superclass;
   }

   // Public --------------------------------------------------------

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

   // ClassInfo implementation --------------------------------------

   public String getName()
   {
      return name;
   }

   public boolean isInterface()
   {
      return false;
   }
   
   public int getModifiers()
   {
      return modifiers;
   }

   public InterfaceInfo[] getInterfaces()
   {
      return interfaces;
   }
   
   public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
   {
      return findMethod(methods, name, parameters);
   }

   public MethodInfo[] getDeclaredMethods()
   {
      return methods;
   }

   public FieldInfo getDeclaredField(String name)
   {
      return (FieldInfo) fieldMap.get(name);
   }

   public FieldInfo[] getDeclaredFields()
   {
      return fields;
   }

   public ConstructorInfo[] getDeclaredConstructors()
   {
      return constructors;
   }

   public ClassInfo getSuperclass()
   {
      return superclass;
   }
   
   // Object overrides ----------------------------------------------

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

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
