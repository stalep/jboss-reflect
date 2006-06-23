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
package org.jboss.config.plugins.property;

import java.util.Properties;
import java.util.StringTokenizer;

import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.classadapter.spi.DependencyBuilder;
import org.jboss.config.plugins.AbstractConfiguration;
import org.jboss.joinpoint.spi.JoinpointFactoryBuilder;
import org.jboss.logging.Logger;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.repository.spi.MetaDataContextFactory;

/**
 * PropertyConfiguration.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class PropertyConfiguration extends AbstractConfiguration
{
   /** The log */
   private static final Logger log = Logger.getLogger(PropertyConfiguration.class);

   /** The properties */
   protected Properties properties;

   /**
    * Create a configuration
    */
   public PropertyConfiguration()
   {
      this(null);
   }

   /**
    * Create a configuration
    * 
    * @param properties the properties
    */
   public PropertyConfiguration(Properties properties)
   {
      if (properties == null)
         properties = System.getProperties();
      this.properties = properties;
   }

   /**
    * Get the properties
    * 
    * @return the properties
    */
   public Properties getProperties()
   {
      return properties;
   }
   
   protected BeanInfoFactory createDefaultBeanInfoFactory() throws Throwable
   {
      return (BeanInfoFactory) loadFromProperties(PropertyConfigurationConstants.BEAN_INFO_FACTORY_NAME, PropertyConfigurationConstants.BEAN_INFO_FACTORY_DEFAULT, BeanInfoFactory.class);
   }
   
   protected ClassAdapterFactory createDefaultClassAdapterFactory() throws Throwable
   {
      ClassAdapterFactory result = (ClassAdapterFactory) loadFromProperties(PropertyConfigurationConstants.CLASS_ADAPTER_FACTORY_NAME, PropertyConfigurationConstants.CLASS_ADAPTER_FACTORY_DEFAULT, ClassAdapterFactory.class);
      result.setConfiguration(this);
      return result;
   }

   protected TypeInfoFactory createDefaultTypeInfoFactory() throws Throwable
   {
      return (TypeInfoFactory) loadFromProperties(PropertyConfigurationConstants.TYPE_INFO_FACTORY_NAME, PropertyConfigurationConstants.TYPE_INFO_FACTORY_DEFAULT, TypeInfoFactory.class);
   }

   protected JoinpointFactoryBuilder createDefaultJoinpointFactoryBuilder() throws Throwable
   {
      return (JoinpointFactoryBuilder) loadFromProperties(PropertyConfigurationConstants.JOIN_POINT_FACTORY_BUILDER_NAME, PropertyConfigurationConstants.JOIN_POINT_FACTORY_BUILDER_DEFAULT, JoinpointFactoryBuilder.class);
   }

   protected MetaDataContextFactory createDefaultMetaDataContextFactory() throws Throwable
   {
      return (MetaDataContextFactory) loadFromProperties(PropertyConfigurationConstants.META_DATA_CONTEXT_FACTORY_BUILDER_NAME, PropertyConfigurationConstants.META_DATA_CONTEXT_FACTORY_BUILDER_DEFAULT, MetaDataContextFactory.class);
   }

   protected DependencyBuilder createDefaultDependencyBuilder() throws Throwable
   {
      return (DependencyBuilder) loadFromProperties(PropertyConfigurationConstants.DEPENDENCY_BUILDER_NAME, PropertyConfigurationConstants.DEPENDENCY_BUILDER_DEFAULT, DependencyBuilder.class);
   }

   /**
    * Load an object from the specified properties
    * 
    * @param propertyName the property name
    * @param defaultValue the default value
    * @param targetClass the target class
    * @return the object
    * @throws Throwable for any error
    */
   protected Object loadFromProperties(String propertyName, String defaultValue, Class<? extends Object> targetClass) throws Throwable
   {
      String value = properties.getProperty(propertyName, defaultValue);
      StringTokenizer tokenizer = new StringTokenizer(value, ":");
      Class clazz = null;
      ClassNotFoundException error = null;
      while (tokenizer.hasMoreTokens())
      {
         String className = tokenizer.nextToken();
         try
         {
            clazz = getClass().getClassLoader().loadClass(className);
            break;
         }
         catch (ClassNotFoundException ignored)
         {
            log.trace(className + " not found: " + ignored.getMessage());
            error = ignored;
         }
      }
      if (clazz == null && error != null)
         throw error;
      if (clazz == null)
         throw new RuntimeException("Invalid configuration for property " + propertyName + " expected a class name that implements " + targetClass.getName());
      
      if (targetClass.isAssignableFrom(clazz) == false)
         throw new RuntimeException("Class " + clazz.getName() + " specified in property " + propertyName + " does not implement " + targetClass.getName());

      return clazz.newInstance();
   }
}
