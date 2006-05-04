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
package org.jboss.config.plugins.property;

import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.joinpoint.spi.JoinpointFactoryBuilder;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.repository.spi.MetaDataContextFactory;

/**
 * Constants.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface PropertyConfigurationConstants
{
   /** The BeanInfoFactory property name */
   static final String BEAN_INFO_FACTORY_NAME = BeanInfoFactory.class.getName();
   
   /** The BeanInfoFactory default value */
   static final String BEAN_INFO_FACTORY_DEFAULT="org.jboss.beans.info.plugins.AbstractBeanInfoFactory";

   /** The ClassAdapterFactory property name */
   static final String CLASS_ADAPTER_FACTORY_NAME = ClassAdapterFactory.class.getName();
   
   /** The ClassAdapterFactory default value */
   static final String CLASS_ADAPTER_FACTORY_DEFAULT="org.jboss.aop.microcontainer.integration.AOPClassAdapterFactory:org.jboss.classadapter.plugins.BasicClassAdapterFactory";

   /** The JoinpointFactoryBuilder property name */
   static final String JOIN_POINT_FACTORY_BUILDER_NAME = JoinpointFactoryBuilder.class.getName();
   
   /** The JoinpointFactoryBuiylder default value */
   static final String JOIN_POINT_FACTORY_BUILDER_DEFAULT="org.jboss.aop.microcontainer.integration.AOPJoinpointFactoryBuilder:org.jboss.joinpoint.plugins.BasicJoinpointFactoryBuilder";

   /** The MetaDataContextFactory property name */
   static final String META_DATA_CONTEXT_FACTORY_BUILDER_NAME = MetaDataContextFactory.class.getName();
   
   /** The MetaDataContextFactory default value */
   static final String META_DATA_CONTEXT_FACTORY_BUILDER_DEFAULT="org.jboss.aop.microcontainer.integration.AOPMetaDataContextFactory:org.jboss.repository.plugins.basic.BasicMetaDataContextFactory";

   /** The TypeInfoFactory property name */
   static final String TYPE_INFO_FACTORY_NAME = TypeInfoFactory.class.getName();
   
   /** The TypeInfoFactory default value */
   static final String TYPE_INFO_FACTORY_DEFAULT="org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory";
}
