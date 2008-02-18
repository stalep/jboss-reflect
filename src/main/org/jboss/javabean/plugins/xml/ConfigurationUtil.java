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
package org.jboss.javabean.plugins.xml;

import java.security.AccessController;
import java.security.PrivilegedAction;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.config.plugins.property.PropertyConfiguration;
import org.jboss.config.spi.Configuration;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.propertyeditor.PropertyEditors;

/**
 * Initialize the Configuration
 *
 * @author ales.justin@jboss.com
 */
public class ConfigurationUtil
{
   /** The kernel config */
   private static Configuration config;

   static synchronized void init()
   {
      if(config == null)
      {
         config = AccessController.doPrivileged(new PrivilegedAction<Configuration>()
         {
            public Configuration run()
            {
               return new PropertyConfiguration(System.getProperties());
            }
         });
         PropertyEditors.init();
      }
   }

   // ----- utils

   static BeanInfo getBeanInfo(Object object) throws Throwable
   {
      return getBeanInfo(object.getClass());
   }

   static BeanInfo getBeanInfo(Class<?> clazz) throws Throwable
   {
      return config.getBeanInfo(clazz);
   }

   static BeanInfo getBeanInfo(String className) throws Throwable
   {
      return config.getBeanInfo(className, Thread.currentThread().getContextClassLoader());
   }

   static PropertyInfo getPropertyInfo(Object parent, String name) throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(parent);
      return beanInfo.getProperty(name);
   }

   static Object newInstance(String className, String[] params, Object[] args)
      throws Throwable
   {
      BeanInfo info = getBeanInfo(className);
      return info.newInstance(params, args);
   }

   /**
    * Convert a value
    *
    * @param parent parent object
    * @param name the property name
    * @param override the override class
    * @param value the value
    * @return the converted value
    * @throws Throwable for any error
    */
   static Object convertValue(Object parent, String name, String override, Object value) throws Throwable
   {
      if (parent == null)
         throw new IllegalArgumentException("Null parent!");

      PropertyInfo property = getPropertyInfo(parent, name);
      return convertValue(property, override, value);
   }

   static Object convertValue(PropertyInfo property, String override, Object value)
         throws Throwable
   {
      if (property == null)
         throw new IllegalArgumentException("Null property!");

      TypeInfo type = property.getType();
      if (override != null)
         type = config.getTypeInfoFactory().getTypeInfo(override, null);

      return type != null ? type.convertValue(value) : value;
   }

}
