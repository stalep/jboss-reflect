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
package org.jboss.beans.info.spi;

import java.util.List;
import java.util.Set;

import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.metadata.spi.MetaData;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.util.JBossInterface;

/**
 * Description of a bean.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface BeanInfo extends JBossInterface
{
   /**
    * Get the bean name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the class information
    * 
    * @return the class information
    */
   ClassInfo getClassInfo();

   /**
    * Get the joinpoint factory
    * 
    * @return the joinpoint factory
    */
   JoinpointFactory getJoinpointFactory();
   
   /**
    * Get the property information.
    *
    * @return a Set<PropertyInfo> 
    */
   Set<PropertyInfo> getProperties();
   
   /**
    * Set the property information.
    *
    * @param properties a Set<PropertyInfo> 
    */
   void setProperties(Set<PropertyInfo> properties);
   
   /**
    * Get the constructor info.
    *
    * @return a Set<ConstructorInfo> 
    */
   Set<ConstructorInfo> getConstructors();
   
   /**
    * Set the constructor info.
    *
    * @param constructors a Set<ConstructorInfo> 
    */
   void setConstructors(Set<ConstructorInfo> constructors);
   
   /**
    * Get the method information.
    *
    * @return a Set<MethodInfo> 
    */
   Set<MethodInfo> getMethods();
   
   /**
    * Set the method information.
    *
    * @param methods a Set<MethodInfo> 
    */
   void setMethods(Set<MethodInfo> methods);
   
   /**
    * Get the event information.
    *
    * @return a Set<EventInfo> 
    */
   Set<EventInfo> getEvents();
   
   /**
    * set the event information.
    *
    * @param events a Set<EventInfo> 
    */
   void setEvents(Set<EventInfo> events);
   
   /**
    * Get the bean info factory
    * 
    * @return the factory
    */
   BeanInfoFactory getBeanInfoFactory();

   /**
    * Bean may have additional dependencies
    * that the kernel cannot initially resolve. (currently defined by ClassAdapter)
    *
    * @param metaData the metadata
    * @return the list of dependencies
    */
   List<Object> getDependencies(MetaData metaData);
}
