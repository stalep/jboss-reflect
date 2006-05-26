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
package org.jboss.test.profileservice.simple1;

import java.util.Iterator;
import java.util.Map;

import org.jboss.profileservice.spi.DeploymentBean;
import org.jboss.profileservice.spi.Policy;
import org.jboss.profileservice.spi.PropertyInfo;

/**
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class DeploymentBeanImpl implements DeploymentBean
{
   private String name;
   private String bean;
   private String[] dependencies = {};

   public DeploymentBeanImpl(String name, String bean)
   {
      this.name = name;
      this.bean = bean;
   }

   public String getBean()
   {
      return bean;
   }

   public String getName()
   {
      return name;
   }

   public String[] getDependencies()
   {
      return dependencies;
   }
   public void setDependencies(String[] dependencies)
   {
      this.dependencies = dependencies;
   }

   public Iterator<Policy> getPolcies()
   {
      // TODO Auto-generated method stub
      return null;
   }

   public Iterator<PropertyInfo> getProperties()
   {
      // TODO Auto-generated method stub
      return null;
   }

   public Map<PropertyInfo, Object> getPropertyValues()
   {
      // TODO Auto-generated method stub
      return null;
   }

   public void setPropertyValues(Map<PropertyInfo, Object> values)
   {
      // TODO Auto-generated method stub

   }

   public PropertyInfo getProperty(String name)
   {
      // TODO Auto-generated method stub
      return null;
   }

   public Object getPropertyValue(String name)
   {
      // TODO Auto-generated method stub
      return null;
   }

   public void setPropertyValue(String name, Object value)
   {
      // TODO Auto-generated method stub

   }

}
