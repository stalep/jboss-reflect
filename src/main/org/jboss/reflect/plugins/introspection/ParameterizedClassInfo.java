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
package org.jboss.reflect.plugins.introspection;

import java.lang.reflect.ParameterizedType;

import org.jboss.reflect.plugins.ClassInfoHelper;
import org.jboss.reflect.plugins.ClassInfoImpl;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.InterfaceInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossObject;
import org.jboss.util.JBossStringBuilder;

/**
 * ParameterizedClassInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ParameterizedClassInfo extends JBossObject implements ClassInfo, InterfaceInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -8739806147734002603L;
   
   /** The helper */
   protected ClassInfoHelper helper;
   
   /** The raw class info */
   protected ClassInfo delegate;
   
   /** The parameterized type */
   ParameterizedType parameterizedType;
   
   /** The owner type */
   private TypeInfo ownerType = ClassInfoImpl.UNKNOWN_TYPE;
   
   /** The type arguments */
   private TypeInfo[] typeArguments = ClassInfoImpl.UNKNOWN_TYPES;
   
   /**
    * Create a new ParameterizedClassInfo.
    *
    * @param helper the helper
    * @param delegate the raw array info
    * @param parameterizedType the parameterized  type
    */
   public ParameterizedClassInfo(ClassInfoHelper helper, ClassInfo delegate, ParameterizedType parameterizedType)
   {
      this.helper = helper;
      this.delegate = delegate;
      this.parameterizedType = parameterizedType;
   }

   public ConstructorInfo getDeclaredConstructor(TypeInfo[] parameters)
   {
      return delegate.getDeclaredConstructor(parameters);
   }

   public ConstructorInfo[] getDeclaredConstructors()
   {
      return delegate.getDeclaredConstructors();
   }

   public FieldInfo getDeclaredField(String name)
   {
      return delegate.getDeclaredField(name);
   }

   public FieldInfo[] getDeclaredFields()
   {
      return delegate.getDeclaredFields();
   }

   public MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters)
   {
      return delegate.getDeclaredMethod(name, parameters);
   }

   public MethodInfo[] getDeclaredMethods()
   {
      return delegate.getDeclaredMethods();
   }

   public InterfaceInfo[] getGenericInterfaces()
   {
      return delegate.getGenericInterfaces();
   }

   public ClassInfo getGenericSuperclass()
   {
      return delegate.getGenericSuperclass();
   }

   public InterfaceInfo[] getInterfaces()
   {
      return delegate.getInterfaces();
   }

   public String getName()
   {
      return delegate.getName();
   }

   public ClassInfo getSuperclass()
   {
      return delegate.getSuperclass();
   }

   public boolean isInterface()
   {
      return delegate.isInterface();
   }

   public AnnotationValue getAnnotation(String name)
   {
      return delegate.getAnnotation(name);
   }

   public AnnotationValue[] getAnnotations()
   {
      return delegate.getAnnotations();
   }

   public boolean isAnnotationPresent(String name)
   {
      return delegate.isAnnotationPresent(name);
   }

   public int getModifiers()
   {
      return delegate.getModifiers();
   }

   public boolean isPublic()
   {
      return delegate.isPublic();
   }

   public boolean isStatic()
   {
      return delegate.isStatic();
   }

   public Object convertValue(Object value) throws Throwable
   {
      return delegate.convertValue(value);
   }

   public TypeInfo getArrayType(int depth)
   {
      return delegate.getArrayType(depth);
   }
   
   public Class getType()
   {
      return delegate.getType();
   }

   public boolean isArray()
   {
      return delegate.isArray();
   }

   public boolean isEnum()
   {
      return delegate.isEnum();
   }

   public boolean isPrimitive()
   {
      return delegate.isPrimitive();
   }

   public Object[] newArrayInstance(int size) throws Throwable
   {
      return delegate.newArrayInstance(size);
   }

   public TypeInfo[] getActualTypeArguments()
   {
      if (typeArguments == ClassInfoImpl.UNKNOWN_TYPES)
         typeArguments = helper.getActualTypeArguments(this);
      return typeArguments;
   }

   public TypeInfo getOwnerType()
   {
      if (ownerType == ClassInfoImpl.UNKNOWN_TYPE)
         ownerType = helper.getOwnerType(this);
      return ownerType;
   }

   public ClassInfo getRawType()
   {
      return delegate;
   }

   protected int getHashCode()
   {
      return delegate.hashCode();
   }

   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      
      if (obj == null || obj instanceof ClassInfo == false)
         return false;
      
      ClassInfo other = (ClassInfo) obj;
      ClassInfo otherDelegate = other;
      if (other instanceof ParameterizedClassInfo)
         otherDelegate = ((ParameterizedClassInfo) other).delegate;
      
      if (delegate.equals(otherDelegate) == false)
         return false;
      
      // We are equal to the raw type (seems hacky?)
      if (other instanceof ParameterizedClassInfo == false)
         return true;
      
      TypeInfo[] typeArguments = getActualTypeArguments();
      TypeInfo[] otherTypeArguments = other.getActualTypeArguments();
      if (typeArguments.length != otherTypeArguments.length)
         return false;
      
      for (int i = 0; i < typeArguments.length; ++i)
      {
         if (typeArguments[i].equals(otherTypeArguments[i]) == false)
            return false;
      }
      return true;
   }

   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(parameterizedType);
   }

   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append(parameterizedType);
   }
}
