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
package org.jboss.profileservice.spi.management;

import java.util.ArrayList;
import java.util.List;

import org.jboss.profileservice.spi.PropertyInfo;

/**
 * A collection of the properties making up a deployment managed object
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ManagedObject
{
   private ArrayList<PropertyInfo> properties = new ArrayList<PropertyInfo>();

   public ManagedObject()
   {
   }

   public List<PropertyInfo> getProperties()
   {
      return properties;
   }

   public boolean addPropertyRef(PropertyInfo ref)
   {
      return properties.add(ref);
   }
   public boolean removePropertyRef(PropertyInfo ref)
   {
      return properties.remove(ref);
   }
   public int getSize()
   {
      return properties.size();
   }
   public void clear()
   {
      properties.clear();
   }
}
