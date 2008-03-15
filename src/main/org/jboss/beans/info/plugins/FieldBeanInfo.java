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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.beans.info.spi.EventInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * Field bean info.
 * @see org.jboss.beans.info.spi.BeanAccessMode#FIELDS
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class FieldBeanInfo extends AbstractBeanInfo
{
   /** The fields */
   protected Map<String, FieldInfo> fieldsByName;

   public FieldBeanInfo(
         BeanInfoFactory beanInfoFactory,
         ClassAdapter classAdapter,
         Set<PropertyInfo> properties,
         Set<ConstructorInfo> constructors,
         Set<MethodInfo> methods,
         Set<EventInfo> events)
   {
      super(beanInfoFactory, classAdapter, properties, constructors, methods, events);
   }

   public void setProperties(Set<PropertyInfo> properties)
   {
      setFields(getFields(classAdapter.getClassInfo(), getFieldFilter()));
      super.setProperties(properties);
      for(FieldInfo field : fieldsByName.values())
      {
         PropertyInfo previous = findPropertyInfo(field.getName());
         if (previous == null)
            addProperty(new FieldPropertyInfo(field));
      }
   }

   protected PropertyInfo replaceProperty(PropertyInfo original)
   {
      String name = original.getName();

      if (original.isReadable() == false)
      {
         FieldInfo field = getField(name);
         if (field != null) // TODO - match type?
            return new SetterAndFieldPropertyInfo(original, field);
      }
      else if (original.isWritable() == false)
      {
         FieldInfo field = getField(name);
         if (field != null) // TODO - match type?
            return new GetterAndFieldPropertyInfo(original, field);
      }
      return original;
   }

   /**
    * Set fields
    *
    * @param fields the fields
    */
   protected void setFields(Set<FieldInfo> fields)
   {
      if (fields != null && fields.isEmpty() == false)
      {
         fieldsByName = new HashMap<String, FieldInfo>(fields.size());
         for (FieldInfo field : fields)
         {
            fieldsByName.put(field.getName(), field);
         }
      }
      else
      {
         fieldsByName = Collections.emptyMap();
      }
   }

   /**
    * Get a property
    *
    * @param name the property name
    * @return the property
    * @throws IllegalArgumentException if there is no such property
    */
   protected FieldInfo getField(String name)
   {
      return fieldsByName.get(name);
   }

   /**
    * Get the fields
    *
    * @param classInfo the class info
    * @param filter the field filter
    * @return the fields
    */
   protected static Set<FieldInfo> getFields(ClassInfo classInfo, FieldFilter filter)
   {
      HashSet<FieldInfo> fields = new HashSet<FieldInfo>();
      while (classInfo != null)
      {
         FieldInfo[] finfos = classInfo.getDeclaredFields();
         if (finfos != null && finfos.length > 0)
         {
            for (int i = 0; i < finfos.length; ++i)
               if (filter.useField(finfos[i]))
                  fields.add(finfos[i]);
         }
         classInfo = classInfo.getSuperclass();
      }
      return fields;
   }

   /**
    * Get the field filter.
    *
    * @return the field filter
    */
   protected FieldFilter getFieldFilter()
   {
      return FieldFilter.PUBLIC;
   }
}
