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
package org.jboss.config.spi;

import java.lang.reflect.Type;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.beans.info.spi.BeanAccessMode;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.joinpoint.spi.JoinpointFactoryBuilder;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * Configuration.<p>
 * 
 * Provides configuration options. 
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface Configuration
{
   /**
    * Get the bean info
    * 
    * @param className the class name
    * @param cl the classloader
    * @return the bean info
    * @throws ClassNotFoundException when the class could not be loaded
    */
   BeanInfo getBeanInfo(String className, ClassLoader cl) throws ClassNotFoundException;

   /**
    * Get the bean info
    * 
    * @param clazz the class
    * @return the bean info
    */
   BeanInfo getBeanInfo(Class<?> clazz);

   /**
    * Get the bean info
    * 
    * @param type the type info
    * @return the bean info
    */
   BeanInfo getBeanInfo(TypeInfo type);
   
   /**
    * Get the bean info
    *
    * @param className the class name
    * @param cl the classloader
    * @param accessMode the bean access mode
    * @return the bean info
    * @throws ClassNotFoundException when the class could not be loaded
    */
   BeanInfo getBeanInfo(String className, ClassLoader cl, BeanAccessMode accessMode) throws ClassNotFoundException;

   /**
    * Get the bean info
    *
    * @param clazz the class
    * @param accessMode the bean access mode
    * @return the bean info
    */
   BeanInfo getBeanInfo(Class<?> clazz, BeanAccessMode accessMode);

   /**
    * Get the bean info
    *
    * @param type the type info
    * @param accessMode the bean access mode
    * @return the bean info
    */
   BeanInfo getBeanInfo(TypeInfo type, BeanAccessMode accessMode);

   /**
    * Get the class info for a class
    * 
    * @param className the class name
    * @param cl the classloader
    * @return the class info
    * @throws ClassNotFoundException when the class could not be loaded
    */
   ClassInfo getClassInfo(String className, ClassLoader cl) throws ClassNotFoundException;
   
   /**
    * Get the class info for a class
    * 
    * @param clazz the class
    * @return the class info
    */
   ClassInfo getClassInfo(Class<?> clazz);
   
   /**
    * Get the type info for a type
    * 
    * @param type the type
    * @return the type info
    * @throws IllegalArgumentException for a null type
    */
   TypeInfo getTypeInfo(Type type);

   /**
    * Get the type info for a type
    *
    * @param type the type
    * @return the type info
    * @param cl the classloader
    * @throws IllegalArgumentException for a null type
    * @throws ClassNotFoundException when the class could not be loaded
    */
   TypeInfo getTypeInfo(String type, ClassLoader cl) throws ClassNotFoundException;

   /**
    * Get the type info factory
    * 
    * @return the TypeInfoFactory
    */
   TypeInfoFactory getTypeInfoFactory();

   /**
    * Get the joinpoint factory builder
    * 
    * @return the JoinpointFactoryBuilder
    */
   JoinpointFactoryBuilder getJoinpointFactoryBuilder();

   /**
    * Set the beanInfoFactory.
    * 
    * @param beanInfoFactory the beanInfoFactory.
    * @throws SecurityException if you don't have the ConfigurationPermission
    */
   void setBeanInfoFactory(BeanInfoFactory beanInfoFactory);

   /**
    * Set the classAdapterFactory.
    * 
    * @param classAdapterFactory the classAdapterFactory.
    * @throws SecurityException if you don't have the ConfigurationPermission
    */
   void setClassAdapterFactory(ClassAdapterFactory classAdapterFactory);

   /**
    * Set the typeInfoFactory.
    * 
    * @param typeInfoFactory the typeInfoFactory.
    * @throws SecurityException if you don't have the ConfigurationPermission
    */
   void setTypeInfoFactory(TypeInfoFactory typeInfoFactory);

   /**
    * Set the joinpointFactoryBuilder.
    * 
    * @param joinpointFactoryBuilder the joinpointFactoryBuilder.
    * @throws SecurityException if you don't have the ConfigurationPermission
    */
   void setJoinpointFactoryBuilder(JoinpointFactoryBuilder joinpointFactoryBuilder);
}
