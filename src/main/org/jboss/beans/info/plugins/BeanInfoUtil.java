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

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.Arrays;

import org.jboss.config.spi.Configuration;
import org.jboss.config.plugins.property.PropertyConfiguration;
import org.jboss.util.propertyeditor.PropertyEditors;
import org.jboss.beans.info.spi.BeanInfo;

/**
 * Bean info helper.
 * Handles nested property names.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class BeanInfoUtil
{
   /** The bean configurator */
   private static Configuration configuration;

   static
   {
      configuration = AccessController.doPrivileged(new PrivilegedAction<Configuration>()
      {
         public Configuration run()
         {
            return new PropertyConfiguration(System.getProperties());
         }
      });
      PropertyEditors.init();
   }

   /**
    * Get the value from target.
    *
    * @param beanInfo the bean info
    * @param target the target
    * @param propertys the property names
    * @return getter value
    * @throws Throwable for any error
    */
   protected static Object getNestedTarget(BeanInfo beanInfo, Object target, String[] propertys)
         throws Throwable
   {
      for(int i = 0; i < propertys.length; i++)
      {
         if (beanInfo == null)
            throw new IllegalArgumentException("Null bean info");

         Object result = beanInfo.getProperty(target, propertys[i]);
         if (i < propertys.length - 1)
         {
            if (result == null)
               throw new IllegalArgumentException("Null target in nested property (" + Arrays.asList(propertys) + "): " + target + "." + propertys[i]);
            beanInfo = configuration.getBeanInfo(result.getClass());
         }
         target = result;
      }
      return target;
   }

   /**
    * Get the value from target.
    *
    * @param beanInfo the bean info
    * @param target the target
    * @param name the property name, can be nested
    * @return getter value
    * @throws Throwable for any error
    */
   public static Object get(BeanInfo beanInfo, Object target, String name) throws Throwable
   {
      if (target == null)
         throw new IllegalArgumentException("Null target");
      if (name == null)
         throw new IllegalArgumentException("Null property name");

      String[] propertys = name.split("\\.");
      return getNestedTarget(beanInfo, target, propertys);
   }

   /**
    * Set the value on target.
    *
    * @param beanInfo the bean info
    * @param target the target
    * @param name the property name, can be nested
    * @param value the value
    * @throws Throwable for any error
    */
   public static void set(BeanInfo beanInfo, Object target, String name, Object value) throws Throwable
   {
      if (target == null)
         throw new IllegalArgumentException("Null target");
      if (name == null)
         throw new IllegalArgumentException("Null property name");

      String[] propertys = name.split("\\.");
      int size = propertys.length - 1;
      if (size > 0)
      {
         String[] allButLast = new String[size];
         System.arraycopy(propertys, 0, allButLast, 0, size);
         Object result = getNestedTarget(beanInfo, target, allButLast);
         if (result == null)
            throw new IllegalArgumentException("Cannot set value on null target: " + target + "." + name);
         target = result;
         beanInfo = configuration.getBeanInfo(target.getClass());
      }
      else if (beanInfo == null)
         throw new IllegalArgumentException("Null bean info.");
      
      beanInfo.setProperty(target, propertys[size], value);
   }
}