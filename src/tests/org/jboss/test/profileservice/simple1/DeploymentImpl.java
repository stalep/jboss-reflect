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

import java.net.URL;

import org.jboss.profileservice.spi.Deployment;
import org.jboss.profileservice.spi.DeploymentBean;
import org.jboss.profileservice.spi.management.ManagedObject;

/**
 * A simple implementation of Deployment.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class DeploymentImpl
   implements Deployment
{
   private String type;
   private String name;
   private URL rootURL;
   private String[] files = {};
   private DeploymentBean[] beans = {};
   private ManagedObject managedObject;

   public String getType()
   {
      return type;
   }
   public void setType(String type)
   {
      this.type = type;
   }

   public String getName()
   {
      return name;
   }
   public void setName(String name)
   {
      this.name = name;
   }

   public URL getRootURL()
   {
      return rootURL;
   }
   public void setRootURL(URL rootURL)
   {
      this.rootURL = rootURL;
   }

   public String[] getFiles()
   {
      return files;
   }
   public void setFiles(String[] files)
   {
      this.files = files;
   }

   public DeploymentBean[] getBeans()
   {
      return beans;
   }
   public void setBeans(DeploymentBean[] beans)
   {
      this.beans = beans;
   }

   public void setManagedObject(ManagedObject managedObject)
   {
      this.managedObject = managedObject;
   }
   public ManagedObject getManagedObject()
   {
      return managedObject;
   }

}
