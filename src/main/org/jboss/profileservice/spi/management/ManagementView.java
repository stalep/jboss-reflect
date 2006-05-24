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

import java.io.IOException;
import java.util.HashMap;

import org.jboss.profileservice.spi.NoSuchDeploymentException;
import org.jboss.profileservice.spi.ProfileKey;
import org.jboss.profileservice.spi.NoSuchProfileException;
import org.jboss.annotation.management.ManagedObject;
import org.jboss.beans.info.spi.PropertyInfo;

/**
 * The management view plugin spi for querying profiles for the
 * deployemnt management object interface roots.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface ManagementView
{
   /**
    * Obtain the top-level management view for a deployment. This is
    * done by identifying all DeploymentBeans annotated with
    * @link{org.jboss.annotation.management.ManagedObject}
    * 
    * @param key - the profile containing the deployment
    * @param deploymentName - the name of deployment
    * @return the root management view
    * @throws NoSuchProfileException
    * @throws NoSuchDeploymentException 
    */
   public ManagedObject getView(ProfileKey key, String deploymentName)
      throws NoSuchProfileException, NoSuchDeploymentException;

   /**
    * Obtain a map of the PropertyInfo objects corresponding to the
    * top-level ManagedObject for the indicated deployment. 
    * 
    * @param key
    * @param deploymentName
    * @return
    * @throws NoSuchProfileException
    * @throws NoSuchDeploymentException 
    */
   public HashMap<String, PropertyInfo> getViewProperties(ProfileKey key, String deploymentName)
      throws NoSuchProfileException, NoSuchDeploymentException;

   /**
    * Set/update the management view of a deployment. 
    * @param key - the profile containing the deployment
    * @param deploymentName - the name of deployment
    * @param view - the populated management view
    * @throws NoSuchProfileException
    * @throws IOException
    */
   public void setView(ProfileKey key, String deploymentName, HashMap<String, PropertyInfo> view)
      throws NoSuchProfileException, IOException;
}
