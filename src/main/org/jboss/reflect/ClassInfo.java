/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * Class info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ClassInfo extends InheritableAnnotationHolder implements TypeInfo
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
   public ClassInfo()
   {
   }

   /**
    * Create a new class info
    * 
    * @param name the class name
    */
   public ClassInfo(String name)
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
   public ClassInfo(String name, int modifiers, InterfaceInfo[] interfaces, 
                    ClassInfo superclass, AnnotationValue[] annotations)
   {
      super(annotations);
      this.name = name;
      this.modifiers = modifiers;
      this.interfaces = interfaces;
      this.superclass = superclass;
   }

   // Public --------------------------------------------------------

   /**
    * Get the class name
    * 
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Whether it is an interface
    * 
    * @return true when an interface
    */
   public boolean isInterface()
   {
      return false;
   }
   
   /**
    * Get the modifiers
    * 
    * @return the modifiers
    */
   public int getModifiers()
   {
      return modifiers;
   }

   /**
    * Get the interfaces
    *
    * @return the interfaces
    */
   public InterfaceInfo[] getInterfaces()
   {
      return interfaces;
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
    * Get the declared method
    * 
    * @param name the method name
    * @param parameters the parameters
    * @return the method info
    */
   public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
   {
      return findMethod(methods, name, parameters);
   }

   /**
    * Get the declared methods
    * 
    * @return the methods
    */
   public MethodInfo[] getDeclaredMethods()
   {
      return methods;
   }

   /**
    * Set the declared methods
    * 
    * @param methods the methods
    */
   public void setDeclaredMethods(MethodInfo[] methods)
   {
      this.methods = methods;
      if (methods != null)
      {
         for (int i = 0; i < methods.length; i++)
            methods[i].declaringClass = this;
      }
   }

   /**
    * Get the declared field
    * 
    * @param name the field name
    * @return the field
    */
   public FieldInfo getDeclaredField(String name)
   {
      return (FieldInfo) fieldMap.get(name);
   }

   /**
    * Get the declared fields
    * 
    * @return the fields
    */
   public FieldInfo[] getDeclaredFields()
   {
      return fields;
   }

   /**
    * Set the declared fields
    * 
    * @param fields the fields
    */
   public void setDeclaredFields(FieldInfo[] fields)
   {
      this.fields = fields;
      if (fields != null)
      {
         for (int i = 0; i < fields.length; i++)
            fields[i].declaringClass = this;
      }
   }

   /**
    * Get the declared constructors
    * 
    * @return the constructors
    */
   public ConstructorInfo[] getDeclaredConstructors()
   {
      return constructors;
   }

   /**
    * Set the declared constructors
    * 
    * @param constructors the constructors
    */
   public void setDeclaredConstructors(ConstructorInfo[] constructors)
   {
      this.constructors = constructors;
      if (constructors != null)
      {
         for (int i = 0; i < constructors.length; i++)
         {
            constructors[i].declaringClass = this;
         }
      }
   }

   /**
    * Get the super class
    * 
    * @return the super class
    */
   public ClassInfo getSuperclass()
   {
      return superclass;
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
   
   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof ClassInfo == false)
         return false;

      final ClassInfo other = (ClassInfo) obj;

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
