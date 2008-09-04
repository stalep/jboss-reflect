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

import java.io.Serializable;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.reflect.plugins.AnnotationHolder;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * Property info.
 * 
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractPropertyInfo extends AnnotationHolder
   implements PropertyInfo, Serializable
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 2;

   /** The bean info */
   private transient BeanInfo beanInfo;
   
   /** The property name */
   private String name;

   /** The upper property name */
   private String upperName;
   
   /** The type */
   private TypeInfo type;

   /**
    * Create a new property info
    */
   public AbstractPropertyInfo()
   {
      this(null, null, null);
   }

   /**
    * Create a new property info
    * 
    * @param name the name
    */
   public AbstractPropertyInfo(String name)
   {
      this(name, name, null);
   }

   /**
    * Create a new property info
    * 
    * @param name the name
    * @param upperName the upper case version of the name
    * @param type the type
    */
   public AbstractPropertyInfo(String name, String upperName, TypeInfo type)
   {
      init(name, upperName, type);
   }

   /**
    * Create a new property info
    * 
    * @param name the name
    * @param upperName the upper case version of the name
    * @param type the type
    * @param annotations the annotations
    */
   public AbstractPropertyInfo(String name, String upperName, TypeInfo type, AnnotationValue[] annotations)
   {
      super(annotations);
      init(name, upperName, type);
   }

   /**
    * Initialize fields.
    *
    * @param name the name
    * @param upperName the upper name
    * @param type the type
    */
   protected void init(String name, String upperName, TypeInfo type)
   {
      this.name = name;
      this.upperName = upperName;
      this.type = type;
   }

   public BeanInfo getBeanInfo()
   {
      return beanInfo;
   }

   void setBeanInfo(BeanInfo beanInfo)
   {
      this.beanInfo = beanInfo;
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

   public FieldInfo getFieldInfo()
   {
      return null;
   }

   @Override
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractPropertyInfo == false)
         return false;
      
      AbstractPropertyInfo other = (AbstractPropertyInfo) object;
      if (notEqual(name, other.name))
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
}
