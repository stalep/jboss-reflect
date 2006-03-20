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
package org.jboss.beans.info.plugins;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.repository.spi.MetaDataContext;
import org.jboss.repository.spi.MetaDataContextFactory;
import org.jboss.util.JBossObject;
import org.jboss.util.JBossStringBuilder;

/**
 * BeanInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractBeanInfo extends JBossObject implements BeanInfo
{
   /** The class name */
   protected String name;
   
   /** The class adapter */
   protected ClassAdapter classAdapter;
   
   /** The properties */
   protected Set properties;
   
   /** The constructors */
   protected Set constructors;
   
   /** The methods */
   protected Set methods;
   
   /** The events */
   protected Set events;
   
   /** The BeanInfoFactory */
   protected BeanInfoFactory beanInfoFactory;


   /**
    * Create a new bean info
    * 
    * @param beanInfoFactory the bean info factory
    * @param classAdapter the class adapter
    * @param properties the properties
    * @param constructors the constructors
    * @param methods the methods
    * @param events the events
    */
   public AbstractBeanInfo(BeanInfoFactory beanInfoFactory, ClassAdapter classAdapter, Set properties, Set constructors,
         Set methods, Set events)
   {
      this.beanInfoFactory = beanInfoFactory;
      this.name = classAdapter.getClassInfo().getName();
      this.classAdapter = classAdapter;
      this.properties = properties;
      if (properties != null && properties.isEmpty() == false)
      {
         for (Iterator i = properties.iterator(); i.hasNext();)
         {
            AbstractPropertyInfo ainfo = (AbstractPropertyInfo) i.next();
            ainfo.beanInfo = this;
         }
      }
      this.constructors = constructors;
      this.methods = methods;
      this.events = events;
   }
   
   protected AbstractBeanInfo(AbstractBeanInfo template)
   {
      this.name = template.name;
      this.classAdapter = template.classAdapter.getInstanceAdapter(template.classAdapter.getClassInfo());
      this.properties = template.properties;
      this.constructors = template.constructors;
      this.methods = template.methods;
      this.events = template.events;
      this.beanInfoFactory = template.beanInfoFactory;
   }

   public String getName()
   {
      return name;
   }
   
   public Set getProperties()
   {
      return properties;
   }
   
   public void setProperties(Set properties)
   {
      this.properties = properties;
   }
   
   public ClassInfo getClassInfo()
   {
      return classAdapter.getClassInfo();
   }

   public List getDependencies()
   {
      return classAdapter.getDependencies();
   }

   public JoinpointFactory getJoinpointFactory()
   {
      return classAdapter.getJoinpointFactory();
   }
   
   public MetaDataContextFactory getMetaDataContextFactory()
   {
      return classAdapter.getMetaDataContextFactory();
   }

   public Set getConstructors()
   {
      return constructors;
   }

   public void setConstructors(Set constructors)
   {
      this.constructors = constructors;
   }
   
   public Set getEvents()
   {
      return events;
   }

   public void setEvents(Set events)
   {
      this.events = events;
   }
   
   public Set getMethods()
   {
      return methods;
   }

   public void setMethods(Set methods)
   {
      this.methods = methods;
   }
   
   public BeanInfoFactory getBeanInfoFactory()
   {
      return beanInfoFactory;
   }
   
   public MetaDataContext getMetaDataContext()
   {
      return classAdapter.getMetaDataContext();
   }

   public void setMetaDataContext(MetaDataContext metaCtx)
   {
      classAdapter.setMetaDataContext(metaCtx);
   }

   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractBeanInfo == false)
         return false;
      
      AbstractBeanInfo other = (AbstractBeanInfo) object;
      if (notEqual(name, other.name))
         return false;
      else if (notEqual(classAdapter, other.classAdapter))
         return false;
      else if (notEqual(properties, other.properties))
         return false;
      else if (notEqual(methods, other.methods))
         return false;
      else if (notEqual(constructors, other.constructors))
         return false;
      else if (notEqual(events, other.events))
         return false;
      return true;
   }
   
   public void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" classInfo=");
      classAdapter.toShortString(buffer);
      buffer.append(" properties=");
      JBossObject.list(buffer, properties);
      buffer.append(" methods=");
      JBossObject.list(buffer, methods);
      buffer.append(" constructors=");
      JBossObject.list(buffer, constructors);
      buffer.append(" events=");
      JBossObject.list(buffer, events);
   }
   
   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(name);
   }
   
   public int getHashCode()
   {
      return name.hashCode();
   }


   public BeanInfo getInstanceInfo()
   {
      return new AbstractInstanceBeanInfo(this);
   }
}
