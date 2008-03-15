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
package org.jboss.beans.info.spi;

import java.util.Set;

import org.jboss.beans.info.plugins.AbstractBeanInfo;
import org.jboss.beans.info.plugins.AllBeanInfo;
import org.jboss.beans.info.plugins.FieldBeanInfo;
import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * Create the bean info from bean access mode.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
interface BeanInfoCreator
{
   static final BeanInfoCreator STANDARD = new StandardBeanInfoCreator();
   static final BeanInfoCreator FIELDS = new FieldBeanInfoCreator();
   static final BeanInfoCreator ALL = new AllBeanInfoCreator();

   /**
    * Create the bean info
    *
    * @param beanInfoFactory the bean info factory
    * @param classAdapter the class adapter
    * @param properties the properties
    * @param constructors the constructors
    * @param methods the methods
    * @param events the events
    * @return the bean info
    */
   BeanInfo create(
         BeanInfoFactory beanInfoFactory,
         ClassAdapter classAdapter,
         Set<PropertyInfo> properties,
         Set<ConstructorInfo> constructors,
         Set<MethodInfo> methods,
         Set<EventInfo> events);

   class StandardBeanInfoCreator implements BeanInfoCreator
   {
      public BeanInfo create(
            BeanInfoFactory beanInfoFactory,
            ClassAdapter classAdapter,
            Set<PropertyInfo> properties,
            Set<ConstructorInfo> constructors,
            Set<MethodInfo> methods,
            Set<EventInfo> events)
      {
         return new AbstractBeanInfo(beanInfoFactory, classAdapter, properties, constructors, methods, events);
      }
   }

   class FieldBeanInfoCreator implements BeanInfoCreator
   {
      public BeanInfo create(
            BeanInfoFactory beanInfoFactory,
            ClassAdapter classAdapter,
            Set<PropertyInfo> properties,
            Set<ConstructorInfo> constructors,
            Set<MethodInfo> methods,
            Set<EventInfo> events)
      {
         return new FieldBeanInfo(beanInfoFactory, classAdapter, properties, constructors, methods, events);
      }
   }

   class AllBeanInfoCreator implements BeanInfoCreator
   {
      public BeanInfo create(
            BeanInfoFactory beanInfoFactory,
            ClassAdapter classAdapter,
            Set<PropertyInfo> properties,
            Set<ConstructorInfo> constructors,
            Set<MethodInfo> methods,
            Set<EventInfo> events)
      {
         return new AllBeanInfo(beanInfoFactory, classAdapter, properties, constructors, methods, events);
      }
   }
}
