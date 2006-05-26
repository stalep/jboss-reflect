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

package org.jboss.profileservice.spi;

import java.util.Iterator;
import java.util.Map;

/**
 * A deployment bean representation. This maps to a kernel bean instance.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface DeploymentBean
{
   /** The fully qualified class name of the bean */
   public String getBean();
   /** The name the bean is registered under. This would be used in bean
    * dependency statements.
    * @return the name the bean is registered under.
    */
   public String getName();
   /**
    * Obtain the list of dependencies for this bean.
    * @return a possibly empty list of mc dependencies.
    */
   public String[] getDependencies();
   /** */
   public Iterator<Policy> getPolcies();
   public Iterator<PropertyInfo> getProperties();
   public Map<PropertyInfo, Object> getPropertyValues();
   public void setPropertyValues(Map<PropertyInfo, Object> values);
   public PropertyInfo getProperty(String name);
   public Object getPropertyValue(String name);
   public void setPropertyValue(String name, Object value);
}
