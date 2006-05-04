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

import org.jboss.beans.info.plugins.AbstractBeanInfoFactory;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.classadapter.plugins.BasicClassAdapterFactory;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.joinpoint.plugins.BasicJoinpointFactoryBuilder;
import org.jboss.joinpoint.spi.JoinpointFactoryBuilder;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.repository.plugins.basic.BasicMetaDataContextFactory;
import org.jboss.repository.spi.MetaDataContextFactory;

/**
 * Basic configuration.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class BasicConfiguration extends AbstractConfiguration
{
   /**
    * Create an abstract configuration
    */
   public BasicConfiguration()
   {
   }
   
   /**
    * Create the default bean info factory
    * 
    * @return the bean info factory
    * @throws Throwable for any error
    */
   protected BeanInfoFactory createDefaultBeanInfoFactory() throws Throwable
   {
      return new AbstractBeanInfoFactory();
   }

   /**
    * Create the default class adapter factory
    * 
    * @return the class adapter factory
    * @throws Throwable for any error
    */
   protected ClassAdapterFactory createDefaultClassAdapterFactory() throws Throwable
   {
      return new BasicClassAdapterFactory();
   }

   /**
    * Create the default type info factory
    * 
    * @return the type info factory
    * @throws Throwable for any error
    */
   protected TypeInfoFactory createDefaultTypeInfoFactory() throws Throwable
   {
      return new IntrospectionTypeInfoFactory();
   }

   /**
    * Create the default joinpoint factory builder
    * 
    * @return the joinpoint factory builder
    * @throws Throwable for any error
    */
   protected JoinpointFactoryBuilder createDefaultJoinpointFactoryBuilder() throws Throwable
   {
      return new BasicJoinpointFactoryBuilder();
   }

   /**
    * Create the default metadata context factory
    * 
    * @return the metadata context factory
    * @throws Throwable for any error
    */
   protected MetaDataContextFactory createDefaultMetaDataContextFactory() throws Throwable
   {
      return new BasicMetaDataContextFactory();
   }
}
