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
package org.jboss.beans.info.spi.helpers;

import java.util.Set;
import java.util.List;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.beans.info.spi.EventInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.classadapter.spi.DependencyBuilderListItem;
import org.jboss.metadata.spi.MetaData;
import org.jboss.util.JBossStringBuilder;
import org.jboss.util.JBossObject;

/**
 * An unmodifiable view of the specified bean info instance.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class UnmodifiableBeanInfo extends JBossObject implements BeanInfo
{
   private BeanInfo delegate;

   public UnmodifiableBeanInfo(BeanInfo delegate)
   {
      if (delegate == null)
         throw new IllegalArgumentException("Null bean info.");
      this.delegate = delegate;
   }

   public String getName()
   {
      return delegate.getName();
   }

   public ClassInfo getClassInfo()
   {
      return delegate.getClassInfo();
   }

   public JoinpointFactory getJoinpointFactory()
   {
      return delegate.getJoinpointFactory();
   }

   public Set<PropertyInfo> getProperties()
   {
      return delegate.getProperties();
   }

   public void setProperties(Set<PropertyInfo> properties)
   {
      throw new UnsupportedOperationException();
   }

   public PropertyInfo getProperty(String name)
   {
      return delegate.getProperty(name);
   }

   public Set<ConstructorInfo> getConstructors()
   {
      return delegate.getConstructors();
   }

   public void setConstructors(Set<ConstructorInfo> constructors)
   {
      throw new UnsupportedOperationException();
   }

   public Set<MethodInfo> getMethods()
   {
      return delegate.getMethods();
   }

   public void setMethods(Set<MethodInfo> methods)
   {
      throw new UnsupportedOperationException();
   }

   public Set<EventInfo> getEvents()
   {
      return delegate.getEvents();
   }

   public void setEvents(Set<EventInfo> events)
   {
      throw new UnsupportedOperationException();
   }

   public BeanInfoFactory getBeanInfoFactory()
   {
      return delegate.getBeanInfoFactory();
   }

   public List<DependencyBuilderListItem> getDependencies(MetaData metaData)
   {
      return delegate.getDependencies(metaData);
   }

   public Object newInstance() throws Throwable
   {
      return delegate.newInstance();
   }

   public Object newInstance(String[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.newInstance(paramTypes, params);
   }

   public Object newInstance(Class[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.newInstance(paramTypes, params);
   }

   public Object newInstance(TypeInfo[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.newInstance(paramTypes, params);
   }

   public Object getProperty(Object bean, String name) throws Throwable
   {
      return delegate.getProperty(bean, name);
   }

   public void setProperty(Object bean, String name, Object value) throws Throwable
   {
      delegate.setProperty(bean, name, value);
   }

   public Object invoke(Object bean, String name) throws Throwable
   {
      return delegate.invoke(bean, name);
   }

   public Object invoke(Object bean, String name, String[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.invoke(bean, name, paramTypes, params);
   }

   public Object invoke(Object bean, String name, Class[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.invoke(bean, name, paramTypes, params);
   }

   public Object invoke(Object bean, String name, TypeInfo[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.invoke(bean, name, paramTypes, params);
   }

   public Object invokeStatic(String name) throws Throwable
   {
      return delegate.invokeStatic(name);
   }

   public Object invokeStatic(String name, String[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.invokeStatic(name, paramTypes, params);
   }

   public Object invokeStatic(String name, Class[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.invokeStatic(name, paramTypes, params);
   }

   public Object invokeStatic(String name, TypeInfo[] paramTypes, Object[] params) throws Throwable
   {
      return delegate.invokeStatic(name, paramTypes, params);
   }

   public String toShortString()
   {
      return delegate.toShortString();
   }

   public void toShortString(JBossStringBuilder buffer)
   {
      delegate.toShortString(buffer);
   }
}
