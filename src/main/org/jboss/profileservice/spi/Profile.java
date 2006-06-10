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

import java.util.Map;

/**
 * A profile represents a named collection of deployments on a server
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface Profile<TMetaData>
{
   /** The x.y.z version of the profile */
   public String getVersion();

   /**
    * Get a deployment template.
    * 
    * @param name - the deployment name to identify the template to retrieve
    * @return the named DeploymentTemplate
    * @throws NoSuchDeploymentException - if there is no such deployment
    */
   public DeploymentTemplate getTemplate(String name)
      throws NoSuchDeploymentException;
   public void addDeployment(Deployment<TMetaData> d);
   public void removeDeployment(String name);

   /**
    * Get a named deployment.
    * 
    * @param name - the deployment name to identify the template to retrieve
    * @return the named Deployment
    * @throws NoSuchDeploymentException - if there is no such deployment
    */
   public Deployment<TMetaData> getDeployment(String name)
      throws NoSuchDeploymentException;

   /**
    * Get all deployments defined in this profile
    * @return Array of the Deployment instances in this profile.
    */
   public Deployment<TMetaData>[] getDeployments();
   public Map<String, Object> getConfig();
}
