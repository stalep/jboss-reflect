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

import java.io.IOException;
import java.util.HashMap;

import org.jboss.profileservice.spi.management.ManagementView;
import org.jboss.profileservice.spi.Deployment;
import org.jboss.profileservice.spi.NoSuchDeploymentException;
import org.jboss.profileservice.spi.Profile;
import org.jboss.profileservice.spi.ProfileKey;
import org.jboss.profileservice.spi.NoSuchProfileException;
import org.jboss.profileservice.spi.ProfileService;
import org.jboss.profileservice.spi.management.ManagedObject;
import org.jboss.beans.info.spi.PropertyInfo;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ManagementViewImpl
   implements ManagementView
{
   private ProfileService ps;

   ManagementViewImpl(ProfileService ps)
   {
      this.ps = ps;
   }

   public ManagedObject getView(ProfileKey key, String deploymentName)
      throws NoSuchProfileException, NoSuchDeploymentException
   {
      Profile profile = ps.getProfile(key);
      Deployment d = profile.getDeployment(deploymentName);
      ManagedObject mo = d.getManagedObject();
      return mo;
   }

   public void setView(ProfileKey key, String deploymentName, HashMap<String, PropertyInfo> view)
      throws NoSuchProfileException, IOException
   {
   }
}
