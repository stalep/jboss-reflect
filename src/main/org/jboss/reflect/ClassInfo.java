/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class ClassInfo extends InheritableAnnotationHolder implements TypeInfo
{
   protected String name;
   protected int modifiers;
   protected InterfaceInfo[] interfaces;
   protected MethodInfo[] methods;
   protected ConstructorInfo[] constructors;
   protected FieldInfo[] fields;
   protected HashMap fieldMap;
   protected ClassInfo superclass;

   public ClassInfo()
   {
   }

   public ClassInfo(String name, int modifiers, InterfaceInfo[] interfaces,
                    ClassInfo superclass, AnnotationValue[] annotations)
   {
      super(annotations);
      this.name = name;
      this.modifiers = modifiers;
      this.interfaces = interfaces;
      this.superclass = superclass;
   }

   public void setDeclaredMethods(MethodInfo[] methods)
   {
      this.methods = methods;
      if (methods != null)
      {
         for (int i = 0; i < methods.length; i++)
         {
            methods[i].declaringClass = this;
         }
      }
   }

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

   public void setDeclaredFields(FieldInfo[] fields)
   {
      this.fields = fields;
      if (fields != null)
      {
         for (int i = 0; i < fields.length; i++)
         {
            fields[i].declaringClass = this;
         }
      }
   }

   public String getName()
   {
      return name;
   }

   public ClassInfo getSuperclass()
   {
      return superclass;
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

   public static MethodInfo findMethod(MethodInfo[] methods1, String name, TypeInfo[] parameters)
   {
      if (methods1 == null) return null;
      for (int i = 0; i < methods1.length; i++)
      {
         if (methods1[i].getName().equals(name))
         {
            if (methods1[i].getParameterTypes().length == parameters.length)
            {
               boolean ok = true;
               for (int j = 0; j < parameters.length; j++)
               {
                  if (!parameters[j].equals(methods1[i].getParameterTypes()[j]))
                  {
                     ok = false;
                     break;
                  }
               }
               if (ok) return methods1[i];
            }
         }
      }
      return null;
   }

   public MethodInfo[] getDeclaredMethods()
   {
      return methods;
   }

   public FieldInfo[] getDeclaredFields()
   {
      return fields;
   }

   public FieldInfo getDeclaredField(String name)
   {
      return (FieldInfo) fieldMap.get(name);
   }

   public ConstructorInfo[] getDeclaredConstructors()
   {
      return constructors;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof ClassInfo)) return false;

      final ClassInfo classInfo = (ClassInfo) o;

      if (name != null ? !name.equals(classInfo.name) : classInfo.name != null) return false;

      return true;
   }

   public int hashCode()
   {
      return (name != null ? name.hashCode() : 0);
   }

}
