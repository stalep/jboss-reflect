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

import org.jboss.profileservice.spi.management.ManagementView;

/**
 * The entry point service for accessing/administiring server profiles
 *  
 * @todo this should be broken up into different feature plugin services
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface ProfileService
{
   // Querying profiles

   /**
    * Get the unique domains for which profiles exist
    * @return array of profile domains
    */
   public String[] getDomains();

   /**
    * Get the keys for all known profiles
    * @return keys for all known profiles
    */
   public ProfileKey[] getProfileKeys();

   /**
    * Obtain the profile for the key.
    * 
    * @param key - the key for the profile
    * @return the matching profile.
    * @throws NoSuchProfileException
    */
   public Profile getProfile(ProfileKey key)
      throws NoSuchProfileException;

   /**
    * Get a list of the deployment names associated with a profile.
    * @param key - the key for the profile
    * @return the list of deployment names.
    * @throws NoSuchProfileException
    */
   public String[] getProfileDeploymentNames(ProfileKey key)
      throws NoSuchProfileException;

   /**
    * Obtain the ManagementView plugin
    * @return the ManagementView plugin if supported
    */
   public ManagementView getViewManager();

   // Admin of profiles @todo could be an option plugin
   public Profile newProfile(ProfileKey key);
   public void removeProfile(ProfileKey key)
      throws NoSuchProfileException;

}