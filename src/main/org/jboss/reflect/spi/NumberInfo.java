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
package org.jboss.reflect.spi;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.jboss.util.JBossStringBuilder;

/**
 * Number info
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class NumberInfo extends PrimitiveInfo implements ClassInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 1L;

   /** The byte info */
   public static final NumberInfo BYTE_OBJECT = new NumberInfo(0, Byte.class);

   /** The double info */
   public static final NumberInfo DOUBLE_OBJECT = new NumberInfo(1, Double.class);

   /** The float info */
   public static final NumberInfo FLOAT_OBJECT = new NumberInfo(2, Float.class);

   /** The int info */
   public static final NumberInfo INT_OBJECT = new NumberInfo(3, Integer.class);

   /** The long info */
   public static final NumberInfo LONG_OBJECT = new NumberInfo(4, Long.class);

   /** The short info */
   public static final NumberInfo SHORT_OBJECT = new NumberInfo(5, Short.class);

   /** The atomic int info */
   public static final NumberInfo ATOMIC_INT = new NumberInfo(6, AtomicInteger.class);

   /** The atomic long info */
   public static final NumberInfo ATOMIC_LONG = new NumberInfo(7, AtomicLong.class);

   /** The primitives */
   private static final NumberInfo[] values = {
         BYTE_OBJECT,
         DOUBLE_OBJECT,
         FLOAT_OBJECT,
         INT_OBJECT,
         LONG_OBJECT,
         SHORT_OBJECT,
         ATOMIC_INT,
         ATOMIC_LONG
   };

   /** The primitives */
   private static final HashMap<String, NumberInfo> map = new HashMap<String, NumberInfo>();

   static
   {
      map.put(Byte.class.getName(), NumberInfo.BYTE_OBJECT);
      map.put(Double.class.getName(), NumberInfo.DOUBLE_OBJECT);
      map.put(Float.class.getName(), NumberInfo.FLOAT_OBJECT);
      map.put(Integer.class.getName(), NumberInfo.INT_OBJECT);
      map.put(Long.class.getName(), NumberInfo.LONG_OBJECT);
      map.put(Short.class.getName(), NumberInfo.SHORT_OBJECT);
      map.put(AtomicInteger.class.getName(), NumberInfo.ATOMIC_INT);
      map.put(AtomicLong.class.getName(), NumberInfo.ATOMIC_LONG);
   }

   private transient ClassInfo delegate;

   /**
    * Get the primitive info for a type
    *
    * @param name the name
    * @return the info
    */
   public static NumberInfo valueOf(String name)
   {
      return map.get(name);
   }

   /**
    * Create a new number info
    *
    * @param ordinal the oridinal
    * @param type the class
    */
   protected NumberInfo(int ordinal, Class<? extends Object> type)
   {
      super(type.getName(), ordinal, type);
   }

   public void setDelegate(TypeInfo info)
   {
      if (info instanceof ClassInfo == false)
      {
         throw new IllegalArgumentException("Should be of classType instance: " + info);
      }
      delegate = (ClassInfo) info;
   }

   public boolean isInitialized()
   {
      return (delegate != null);
   }

   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null)
         return false;
      if (!(obj instanceof NumberInfo))
         return false;
      if (!obj.getClass().equals(this.getClass()))
         return false;
      NumberInfo other = (NumberInfo) obj;
      return other.ordinal == this.ordinal;
   }

   Object readResolve() throws ObjectStreamException
   {
      return values[ordinal];
   }

   // --- delegate

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

   public boolean isPrimitive()
   {
      return delegate.isPrimitive();
   }

   public TypeInfo[] getActualTypeArguments()
   {
      return delegate.getActualTypeArguments();
   }

   public TypeInfo getOwnerType()
   {
      return delegate.getOwnerType();
   }

   public ClassInfo getRawType()
   {
      return delegate;
   }

   protected int getHashCode()
   {
      return delegate.hashCode();
   }

   public String toShortString()
   {
      return name;
   }

   public void toShortString(JBossStringBuilder builder)
   {
      builder.append(name);
   }
  
   public Object clone()
   {
      return this;
   }

}
