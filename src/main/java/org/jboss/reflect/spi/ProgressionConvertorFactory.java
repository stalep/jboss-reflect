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

import java.security.AccessController;
import java.security.PrivilegedExceptionAction;

import org.jboss.reflect.plugins.SimpleProgressionConvertor;
import org.jboss.reflect.plugins.introspection.ReflectionUtils;

/**
 * Singleton progression instance factory.
 * We can change the progression convertor with system property
 * or setting the convertor class name at the singleton factory instance.
 * This way we can still change the convertor at runtime before actual usage -
 * in MC beans definition or other IoC - via XML.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class ProgressionConvertorFactory
{
   private static ProgressionConvertorFactory instance = new ProgressionConvertorFactory();

   private String convertorClassName = SimpleProgressionConvertor.class.getName();
   private ProgressionConvertor convertor;

   public static ProgressionConvertorFactory getInstance()
   {
      return instance;
   }

   public ProgressionConvertor getConvertor() throws Throwable
   {
      // TODO - some more config options; by deployment, app, server, cluster, ...
      if (convertor == null)
      {
         ConvertorLookup lookup = new ConvertorLookup();
         convertor = AccessController.doPrivileged(lookup);
      }
      return convertor;
   }

   public void setConvertorClassName(String convertorClassName)
   {
      this.convertorClassName = convertorClassName;
   }

   private class ConvertorLookup implements PrivilegedExceptionAction<ProgressionConvertor>
   {
      public ProgressionConvertor run() throws Exception
      {
         try
         {
            String convertorClass = System.getProperty("org.jboss.reflect.plugins.progressionConvertor", convertorClassName);
            Object result = ReflectionUtils.newInstance(convertorClass);
            return ProgressionConvertor.class.cast(result);
         }
         catch (Throwable t)
         {
            throw new Exception("Exception while creating convertor instance.", t);
         }
      }
   }

}
