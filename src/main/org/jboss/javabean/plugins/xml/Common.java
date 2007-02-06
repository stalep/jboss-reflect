/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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

import java.util.ArrayList;
import java.util.List;

import org.jboss.beans.info.spi.BeanInfo;

/**
 * Common classes and static methods
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class Common
{
   public static class Holder
   {
      private Object object;      
      
      public Holder()
      {
      }

      public Object getValue()
      {
         return object;
      }
      
      public void setValue(Object object)
      {
         this.object = object;
      }
   }

   public static class Ctor extends Holder
   {
      private String className;
      private boolean ctorWasDeclared;
      private Constructor constructor = new Constructor();
      private List<String> paramTypes = new ArrayList<String>();
      private List<Object> argValues = new ArrayList<Object>();

      public Ctor(String className)
      {
         this.className = className;
      }

      public boolean isCtorWasDeclared()
      {
         return ctorWasDeclared;
      }

      public void setCtorWasDeclared(boolean ctorWasDeclared)
      {
         this.ctorWasDeclared = ctorWasDeclared;
      }

      public String getClassName()
      {
         return className;
      }
      
      public void addParam(String type, Object value)
      {
         paramTypes.add(type);
         argValues.add(value);
      }

      public String[] getParamTypes()
      {
         String[] types = new String[paramTypes.size()];
         paramTypes.toArray(types);
         return types;
      }

      public Object[] getArgs()
      {
         Object[] args = new Object[argValues.size()];
         argValues.toArray(args);
         return args;
      }

      public Constructor getConstructor()
      {
         return constructor;
      }

      public Object newInstance()
         throws Throwable
      {
         String factoryMethod = constructor.getFactoryMethod();
         if (factoryMethod != null)
         {
            BeanInfo beanInfo = ConfigurationUtil.getBeanInfo(constructor.getFactoryClass());
            return beanInfo.invokeStatic(factoryMethod, getParamTypes(), getArgs());
         }
         return ConfigurationUtil.newInstance(getClassName(), getParamTypes(), getArgs());
      }
   }

   public static class Constructor
   {
      private String factoryClass;
      private String factoryMethod;

      public String getFactoryClass()
      {
         return factoryClass;
      }

      public void setFactoryClass(String value)
      {
         factoryClass = value;
      }

      public String getFactoryMethod()
      {
         return factoryMethod;
      }

      public void setFactoryMethod(String value)
      {
         factoryMethod = value;
      }
   }

   public static class Property extends Holder
   {
      private String property;
      
      private String type;
      
      public Property()
      {
      }
      
      public String getProperty()
      {
         return property;
      }
      
      public void setProperty(String property)
      {
         this.property = property;
      }
      
      public String getType()
      {
         return type;
      }
      
      public void setType(String type)
      {
         this.type = type;
      }
   }

}
