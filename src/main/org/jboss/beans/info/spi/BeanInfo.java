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

import org.jboss.classadapter.spi.DependencyBuilderListItem;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.metadata.spi.MetaData;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
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
    * Get a property
    * 
    * @param name the property name
    * @return the property
    * @throws IllegalArgumentException for a null name or if there is no such property
    */
   PropertyInfo getProperty(String name);

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
   List<DependencyBuilderListItem> getDependencies(MetaData metaData);
   
   /**
    * Create a new instance
    * 
    * @return the new instance
    * @throws Throwable for any error
    */
   Object newInstance() throws Throwable;
   
   /**
    * Create a new instance
    *
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the new instance
    * @throws Throwable for any error
    */
   Object newInstance(String[] paramTypes, Object[] params) throws Throwable;
   
   /**
    * Create a new instance
    *
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the new instance
    * @throws Throwable for any error
    */
   Object newInstance(Class[] paramTypes, Object[] params) throws Throwable;
   
   /**
    * Create a new instance
    *
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the new instance
    * @throws Throwable for any error
    */
   Object newInstance(TypeInfo[] paramTypes, Object[] params) throws Throwable;
   
   /**
    * Get a property
    * 
    * @param bean the bean
    * @param name the property name
    * @return the property value
    * @throws Throwable for any error
    */
   Object getProperty(Object bean, String name) throws Throwable;
   
   /**
    * Get a property
    * 
    * @param bean the bean
    * @param name the property name
    * @param value the property value
    * @throws Throwable for any error
    */
   void setProperty(Object bean, String name, Object value) throws Throwable;
   
   /**
    * Invoke a method with no parameters
    *
    * @param bean the bean
    * @param name the method name
    * @return the result
    * @throws Throwable for any error
    */
   Object invoke(Object bean, String name) throws Throwable;
   
   /**
    * Invoke a method
    *
    * @param bean the bean
    * @param name the method name
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result
    * @throws Throwable for any error
    */
   Object invoke(Object bean, String name, String[] paramTypes, Object[] params) throws Throwable;
   
   /**
    * Invoke a method
    *
    * @param bean the bean
    * @param name the method name
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result
    * @throws Throwable for any error
    */
   Object invoke(Object bean, String name, Class[] paramTypes, Object[] params) throws Throwable;
   
   /**
    * Invoke a method
    *
    * @param bean the bean
    * @param name the method name
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result
    * @throws Throwable for any error
    */
   Object invoke(Object bean, String name, TypeInfo[] paramTypes, Object[] params) throws Throwable;

   /**
    * Invoke a static method with no parameters
    *
    * @param name the method name
    * @return the result
    * @throws Throwable for any error
    */
   Object invokeStatic(String name) throws Throwable;

   /**
    * Invoke a static method
    *
    * @param name the method name
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result
    * @throws Throwable for any error
    */
   Object invokeStatic(String name, String[] paramTypes, Object[] params) throws Throwable;

   /**
    * Invoke a static method
    *
    * @param name the method name
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result
    * @throws Throwable for any error
    */
   Object invokeStatic(String name, Class[] paramTypes, Object[] params) throws Throwable;

   /**
    * Invoke a static method
    *
    * @param name the method name
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result
    * @throws Throwable for any error
    */
   Object invokeStatic(String name, TypeInfo[] paramTypes, Object[] params) throws Throwable;
}
