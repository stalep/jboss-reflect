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
package org.jboss.beans.info.plugins;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.reflect.plugins.AnnotationHolder;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * Property info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractPropertyInfo extends AnnotationHolder implements PropertyInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 6558910165211748079L;

   /** The bean info */
   protected BeanInfo beanInfo;
   
   /** The property name */
   protected String name;

   /** The upper property name */
   protected String upperName;
   
   /** The type */
   protected TypeInfo type;
   
   /** The getter */
   protected MethodInfo getter;
   
   /** The setter */
   protected MethodInfo setter;

   /**
    * Create a new property info
    */
   public AbstractPropertyInfo()
   {
      this(null, null, null, null, null);
   }

   /**
    * Create a new property info
    * 
    * @param name the name
    */
   public AbstractPropertyInfo(String name)
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
   public AbstractPropertyInfo(String name, String upperName, TypeInfo type, MethodInfo getter, MethodInfo setter)
   {
      this.name = name;
      this.upperName = upperName;
      this.type = type;
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
   public AbstractPropertyInfo(String name, String upperName, TypeInfo type, MethodInfo getter, MethodInfo setter, AnnotationValue[] annotations)
   {
      super(annotations);
      this.name = name;
      this.upperName = upperName;
      this.type = type;
      this.getter = getter;
      this.setter = setter;
   }
   
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
   
   public TypeInfo getType()
   {
      return type;
   }

   public void setType(TypeInfo type)
   {
      this.type = type;
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
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractPropertyInfo == false)
         return false;
      
      AbstractPropertyInfo other = (AbstractPropertyInfo) object;
      if (notEqual(name, other.name))
         return false;
      else if (notEqual(getter, other.getter))
         return false;
      else if (notEqual(setter, other.setter))
         return false;
      return true;
   }
   
   public void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" getter=").append(getter);
      buffer.append(" setter=").append(setter);
   }
   
   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(name);
   }

   public int getHashCode()
   {
      return name.hashCode();
   }
}
