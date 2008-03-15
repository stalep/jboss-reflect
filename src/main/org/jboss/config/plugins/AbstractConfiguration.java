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
package org.jboss.config.plugins;

import java.lang.reflect.Type;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.beans.info.spi.BeanAccessMode;
import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.config.spi.Configuration;
import org.jboss.config.spi.ConfigurationPermission;
import org.jboss.joinpoint.spi.JoinpointFactoryBuilder;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.NestedRuntimeException;

/**
 * Abstract configuration.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractConfiguration implements Configuration
{
   /** The default bean info factory */
   private BeanInfoFactory beanInfoFactory;
   
   /** The default class adaptor factory */
   private ClassAdapterFactory classAdapterFactory;
   
   /** The default type info factory */
   private TypeInfoFactory typeInfoFactory;
   
   /** The default type joinpoint factory builder */
   private JoinpointFactoryBuilder joinpointFactoryBuilder;

   /**
    * Create an abstract configuration
    */
   public AbstractConfiguration()
   {
   }
   
   public BeanInfo getBeanInfo(String className, ClassLoader cl) throws ClassNotFoundException
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(className, cl);
      return getBeanInfoFactory().getBeanInfo(classAdapter);
   }
   
   public BeanInfo getBeanInfo(Class<?> clazz)
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(clazz);
      return getBeanInfoFactory().getBeanInfo(classAdapter);
   }
   
   public BeanInfo getBeanInfo(TypeInfo typeInfo)
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(typeInfo);
      return getBeanInfoFactory().getBeanInfo(classAdapter);
   }
   
   public BeanInfo getBeanInfo(String className, ClassLoader cl, BeanAccessMode accessMode) throws ClassNotFoundException
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(className, cl);
      return getBeanInfoFactory().getBeanInfo(classAdapter, accessMode);
   }

   public BeanInfo getBeanInfo(Class<?> clazz, BeanAccessMode accessMode)
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(clazz);
      return getBeanInfoFactory().getBeanInfo(classAdapter, accessMode);
   }

   public BeanInfo getBeanInfo(TypeInfo typeInfo, BeanAccessMode accessMode)
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(typeInfo);
      return getBeanInfoFactory().getBeanInfo(classAdapter, accessMode);
   }

   public ClassInfo getClassInfo(String className, ClassLoader cl) throws ClassNotFoundException
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(className, cl);
      return classAdapter.getClassInfo();
   }
   
   public ClassInfo getClassInfo(Class<?> clazz)
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(clazz);
      return classAdapter.getClassInfo();
   }
   
   public TypeInfo getTypeInfo(Type type)
   {
      return getTypeInfoFactory().getTypeInfo(type);
   }

   public TypeInfo getTypeInfo(String type, ClassLoader cl) throws ClassNotFoundException
   {
      return getTypeInfoFactory().getTypeInfo(type, cl);
   }

   public TypeInfoFactory getTypeInfoFactory()
   {
      if (typeInfoFactory == null)
      {
         try
         {
            typeInfoFactory = createDefaultTypeInfoFactory();
         }
         catch (RuntimeException e)
         {
            throw e;
         }
         catch (Error e)
         {
            throw e;
         }
         catch (Throwable t)
         {
            throw new NestedRuntimeException("Cannot create TypeInfoFactory", t);
         }
      }
      return typeInfoFactory;
   }

   public JoinpointFactoryBuilder getJoinpointFactoryBuilder()
   {
      if (joinpointFactoryBuilder == null)
      {
         try
         {
            joinpointFactoryBuilder = createDefaultJoinpointFactoryBuilder();
         }
         catch (RuntimeException e)
         {
            throw e;
         }
         catch (Error e)
         {
            throw e;
         }
         catch (Throwable t)
         {
            throw new NestedRuntimeException("Cannot create JoinpointFactoryBuilder", t);
         }
      }
      return joinpointFactoryBuilder;
   }

   public void setBeanInfoFactory(BeanInfoFactory beanInfoFactory)
   {
      checkPermissionName("beanInfoFactory");
      this.beanInfoFactory = beanInfoFactory;
   }

   public void setClassAdapterFactory(ClassAdapterFactory classAdapterFactory)
   {
      checkPermissionName("classAdapterFactory");
      this.classAdapterFactory = classAdapterFactory;
   }

   public void setTypeInfoFactory(TypeInfoFactory typeInfoFactory)
   {
      checkPermissionName("typeInfoFactory");
      this.typeInfoFactory = typeInfoFactory;
   }

   public void setJoinpointFactoryBuilder(JoinpointFactoryBuilder joinpointFactoryBuilder)
   {
      checkPermissionName("joinpointFactoryBuilder");
      this.joinpointFactoryBuilder = joinpointFactoryBuilder;
   }

   /**
    * Get the BeanInfoFactory
    * 
    * @return the BeanInfoFactory
    */
   protected BeanInfoFactory getBeanInfoFactory()
   {
      if (beanInfoFactory == null)
      {
         try
         {
            beanInfoFactory = createDefaultBeanInfoFactory();
         }
         catch (RuntimeException e)
         {
            throw e;
         }
         catch (Error e)
         {
            throw e;
         }
         catch (Throwable t)
         {
            throw new RuntimeException("Error creating bean info factory");
         }
      }
      return beanInfoFactory;
   }

   /**
    * Get the class adapter factory
    * 
    * @return the ClassAdapterFactory
    */
   protected ClassAdapterFactory getClassAdapterFactory()
   {
      if (classAdapterFactory == null)
      {
         try
         {
            classAdapterFactory = createDefaultClassAdapterFactory();
         }
         catch (RuntimeException e)
         {
            throw e;
         }
         catch (Error e)
         {
            throw e;
         }
         catch (Throwable t)
         {
            throw new RuntimeException("Error creating class adapter");
         }
      }
      return classAdapterFactory;
   }
   
   /**
    * Create the default bean info factory
    * 
    * @return the bean info factory
    * @throws Throwable for any error
    */
   protected abstract BeanInfoFactory createDefaultBeanInfoFactory() throws Throwable;

   /**
    * Create the default class adapter factory
    * 
    * @return the class adapter factory
    * @throws Throwable for any error
    */
   protected abstract ClassAdapterFactory createDefaultClassAdapterFactory() throws Throwable;

   /**
    * Create the default type info factory
    * 
    * @return the type info factory
    * @throws Throwable for any error
    */
   protected abstract TypeInfoFactory createDefaultTypeInfoFactory() throws Throwable;

   /**
    * Create the default joinpoint factory builder
    * 
    * @return the joinpoint factory builder
    * @throws Throwable for any error
    */
   protected abstract JoinpointFactoryBuilder createDefaultJoinpointFactoryBuilder() throws Throwable;
   
   /**
    * Check a permission
    * 
    * @param name the name of the permission
    */
   private void checkPermissionName(String name)
   {
      SecurityManager sm = System.getSecurityManager();
      if (sm != null)
         sm.checkPermission(new ConfigurationPermission(name));
      
   }
}
