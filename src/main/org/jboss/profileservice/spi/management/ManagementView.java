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

import org.jboss.profileservice.spi.ProfileKey;
import org.jboss.profileservice.spi.NoSuchProfileException;

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
    */
   public Object getView(ProfileKey key, String deploymentName)
      throws NoSuchProfileException;

   /**
    * Set/update the management view of a deployment. 
    * @param key - the profile containing the deployment
    * @param deploymentName - the name of deployment
    * @param view - the populated management view
    * @throws NoSuchProfileException
    * @throws IOException
    */
   public void setView(ProfileKey key, String deploymentName, Object view)
      throws NoSuchProfileException, IOException;
}
