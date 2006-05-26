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

import org.jboss.profileservice.spi.ProfileService;
import org.jboss.profileservice.spi.ProfileKey;
import org.jboss.profileservice.spi.Profile;
import org.jboss.profileservice.spi.NoSuchProfileException;
import org.jboss.profileservice.spi.management.ManagementView;

/**
 * A simple read-only profile service for testing the basic kernel bootstrap
 * and management view usecases.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ProfileServiceImpl
   implements ProfileService
{
   private Profile defatulImpl;
   private ManagementViewImpl mgtView;

   public ProfileServiceImpl() throws IOException
   {
      defatulImpl = new ProfileImpl();
   }
   public String[] getDomains()
   {
      String[] domains = {ProfileKey.DEFAULT};
      return domains;
   }

   public ProfileKey[] getProfileKeys()
   {
      ProfileKey[] keys = {new ProfileKey(null)};
      return keys;
   }

   /**
    * Always returns the default profile.
    */
   public Profile getProfile(ProfileKey key)
      throws NoSuchProfileException
   {
      return defatulImpl;
   }

   public String[] getProfileDeploymentNames(ProfileKey key)
      throws NoSuchProfileException
   {
      String[] names = {"default"};
      return names;
   }

   public ManagementView getViewManager()
   {
      return mgtView;
   }

   // Admin of profiles @todo could be an option plugin
   public Profile newProfile(ProfileKey key)
   {
      return null;
   }

   public void removeProfile(ProfileKey key)
      throws NoSuchProfileException
   {
   }
}
