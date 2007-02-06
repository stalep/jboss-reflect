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

import java.util.ArrayList;
import java.util.List;

import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * When bean has more than one property with the same name
 * we try to use this impl to look over all possible setters.
 * In case of getter we cannot determine which one to use
 * since there is insufficient information - only parent bean.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class NestedPropertyInfo extends AbstractPropertyInfo
{
   private List<PropertyInfo> propertys = new ArrayList<PropertyInfo>();

   public NestedPropertyInfo(String name)
   {
      super(name);
   }

   void addPropertyInfo(PropertyInfo propertyInfo)
   {
      propertys.add(propertyInfo);
   }

   public Object get(Object bean) throws Throwable
   {
      throw new IllegalArgumentException("Unable to determine getter on " + bean + " for property " + name);
   }

   public void set(Object bean, Object value) throws Throwable
   {
      if (value != null)
      {
         for (PropertyInfo pi : propertys)
         {
            TypeInfo info = pi.getType();
            if (info != null)
            {
               TypeInfo valueTypeInfo = info.getTypeInfoFactory().getTypeInfo(value.getClass());
               if (info.isAssignableFrom(valueTypeInfo))
               {
                  pi.set(bean, value);
                  return;
               }
            }
         }
      }
      throw new IllegalArgumentException("Unable to determine setter on " + bean + " for property " + name + " with value " + value);
   }

}
