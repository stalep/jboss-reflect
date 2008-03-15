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
package org.jboss.beans.info.plugins;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * Default property info.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class DefaultPropertyInfo extends AbstractPropertyInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1;

   /** The getter */
   private MethodInfo getter;

   /** The setter */
   private MethodInfo setter;

   /**
    * Create a new property info
    */
   public DefaultPropertyInfo()
   {
      this(null, null, null, null, null);
   }

   /**
    * Create a new property info
    *
    * @param name the name
    */
   public DefaultPropertyInfo(String name)
   {
      this(name, name, null, null, null);
   }

   /**
    * Create a new property info
    *
    * @param name the name
    * @param upperName the upper case version of the name
    * @param type the type
    * @param getter the getter
    * @param setter the setter
    */
   public DefaultPropertyInfo(String name, String upperName, TypeInfo type, MethodInfo getter, MethodInfo setter)
   {
      super(name, upperName, type);
      this.getter = getter;
      this.setter = setter;
   }

   /**
    * Create a new property info
    *
    * @param name the name
    * @param upperName the upper case version of the name
    * @param type the type
    * @param getter the getter
    * @param setter the setter
    * @param annotations the annotations
    */
   public DefaultPropertyInfo(String name, String upperName, TypeInfo type, MethodInfo getter, MethodInfo setter, AnnotationValue[] annotations)
   {
      super(name, upperName, type, annotations);
      this.getter = getter;
      this.setter = setter;
   }

   public MethodInfo getGetter()
   {
      return getter;
   }

   public void setGetter(MethodInfo getter)
   {
      this.getter = getter;
   }

   public MethodInfo getSetter()
   {
      return setter;
   }

   public void setSetter(MethodInfo setter)
   {
      this.setter = setter;
   }

   public boolean isReadable()
   {
      return getGetter() != null;
   }

   public boolean isWritable()
   {
      return getSetter() != null;
   }

   public Object get(Object bean) throws Throwable
   {
      if (bean == null)
         throw new IllegalArgumentException("Null bean");
      if (getter == null)
         throw new IllegalArgumentException("Property is not readable: " + getName() + " for " + getBeanInfo().getName());

      return getter.invoke(bean, null);
   }

   public void set(Object bean, Object value) throws Throwable
   {
      if (bean == null)
         throw new IllegalArgumentException("Null bean");
      if (setter == null)
         throw new IllegalArgumentException("Property is not writable: " + getName() + " for " + getBeanInfo().getName());

      setter.invoke(bean, new Object[] { value });
   }

   @Override
   public boolean equals(Object object)
   {
      if (super.equals(object) == false)
         return false;

      if (object == null || object instanceof DefaultPropertyInfo == false)
         return false;

      DefaultPropertyInfo other = (DefaultPropertyInfo) object;
      if (notEqual(getter, other.getter))
         return false;
      else if (notEqual(setter, other.setter))
         return false;
      return true;
   }

   @Override
   public void toString(JBossStringBuilder buffer)
   {
      super.toString(buffer);
      buffer.append(" getter=").append(getter);
      buffer.append(" setter=").append(setter);
   }
}
