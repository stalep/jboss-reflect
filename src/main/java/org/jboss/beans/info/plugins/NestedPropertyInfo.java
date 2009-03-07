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

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.util.JBossObject;
import org.jboss.util.JBossStringBuilder;
import org.jboss.util.NotImplementedException;

/**
 * When bean has more than one property with the same name
 * we try to use this impl to look over all possible setters
 * in order to set the value.
 *
 * But for most of other methods there is insufficent information
 * to invoke the right method - e.g. just property name and parent bean. 
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class NestedPropertyInfo extends JBossObject implements PropertyInfo, Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 1L;

   private String name;
   private String upperName;
   private BeanInfo beanInfo;
   private List<PropertyInfo> propertys = new ArrayList<PropertyInfo>();

   @Deprecated
   public NestedPropertyInfo(String name, BeanInfo beanInfo)
   {
      this(name, name, beanInfo);
   }

   public NestedPropertyInfo(String name, String upperName, BeanInfo beanInfo)
   {
      this.name = name;
      this.upperName = upperName;
      this.beanInfo = beanInfo;
   }

   void addPropertyInfo(PropertyInfo propertyInfo)
   {
      propertys.add(propertyInfo);
   }

   // can be used

   public BeanInfo getBeanInfo()
   {
      return beanInfo;
   }

   public String getName()
   {
      return name;
   }

   public String getUpperName()
   {
      return upperName;
   }

   /**
    * In this case it is better to return null
    * then to throw an exception, since we might still have
    * enough information to use this class to set the value.
    *
    * @return null
    */
   public TypeInfo getType()
   {
      return null;
   }

   public void set(Object bean, Object value) throws Throwable
   {
      if (value != null)
      {
         for (PropertyInfo pi : propertys)
         {
            TypeInfo info = pi.getType();
            if (info != null && info.isInstance(value))
            {
               pi.set(bean, info.convertValue(value));
               return;
            }
         }
      }
      throw new IllegalArgumentException("Unable to determine setter on " + bean + " for property " + name + " with value " + value);
   }

   @Override
   public boolean equals(Object object)
   {
      if (object == null || object instanceof NestedPropertyInfo == false)
         return false;

      NestedPropertyInfo other = (NestedPropertyInfo) object;
      if (notEqual(name, other.name))
         return false;
      if (notEqual(beanInfo, other.beanInfo))
         return false;
      else if (notEqual(propertys.size(), other.propertys.size()))
         return false;
      return true;
   }

   @Override
   public void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(name);
   }

   @Override
   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(name);
   }

   @Override
   public int getHashCode()
   {
      return name.hashCode();
   }

   // ---- undeterminable

   public Object get(Object bean) throws Throwable
   {
      throw new IllegalArgumentException("Unable to determine getter on " + bean + " for property " + name);
   }

   public MethodInfo getGetter()
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + name);
   }

   public void setGetter(MethodInfo getter)
   {
      throw new NotImplementedException("setGetter");
   }

   public MethodInfo getSetter()
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + name);
   }

   public void setSetter(MethodInfo setter)
   {
      throw new NotImplementedException("setSetter");
   }

   public boolean isReadable()
   {
      return false;
   }

   public boolean isWritable()
   {
      return false;
   }

   public FieldInfo getFieldInfo()
   {
      return null;
   }

   public AnnotationValue[] getAnnotations()
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + name);
   }

   public AnnotationValue getAnnotation(String annotationName)
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + this.name);
   }

   public boolean isAnnotationPresent(String annotationName)
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + this.name);
   }

   public Annotation[] getUnderlyingAnnotations()
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + name);
   }

   public <T extends Annotation> T getUnderlyingAnnotation(Class<T> annotationType)
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + name);
   }

   public boolean isAnnotationPresent(Class<? extends Annotation> annotationType)
   {
      throw new IllegalArgumentException("Unable to determine right PropertyInfo on " + beanInfo + " by name: " + name);
   }

}
