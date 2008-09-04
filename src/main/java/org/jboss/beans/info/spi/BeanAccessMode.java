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

import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * Bean access mode.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public enum BeanAccessMode
{
   STANDARD(BeanInfoCreator.STANDARD), // Getters and Setters
   FIELDS(BeanInfoCreator.FIELDS), // Getters/Setters and fields without getters and setters
   ALL(BeanInfoCreator.ALL); // As above but with non public fields included

   /** The bean info creator */
   private BeanInfoCreator creator;

   BeanAccessMode(BeanInfoCreator creator)
   {
      this.creator = creator;
   }

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
   public BeanInfo create(
         BeanInfoFactory beanInfoFactory,
         ClassAdapter classAdapter,
         Set<PropertyInfo> properties,
         Set<ConstructorInfo> constructors,
         Set<MethodInfo> methods,
         Set<EventInfo> events)
   {
      return creator.create(beanInfoFactory, classAdapter, properties, constructors, methods, events);
   }
}