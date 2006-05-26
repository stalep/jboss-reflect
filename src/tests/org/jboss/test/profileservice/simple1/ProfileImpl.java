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
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jboss.profileservice.spi.DeploymentBean;
import org.jboss.profileservice.spi.NoSuchDeploymentException;
import org.jboss.profileservice.spi.Profile;
import org.jboss.profileservice.spi.DeploymentTemplate;
import org.jboss.profileservice.spi.Deployment;

/**
 * A test profile with a fixed default deployment. The contents of the
 * deployments are expected to be in the 
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ProfileImpl
   implements Profile
{
   private HashMap<String, Deployment> deployments;

   public ProfileImpl() throws IOException
   {
      deployments = new HashMap<String, Deployment>();
      String deployDir = SecurityActions.getSystemProperty("jbosstest.deploy.dir");
      URL libURL;
      if( deployDir == null )
      {
         // Used codesource + ../lib
         URL classes = getClass().getProtectionDomain().getCodeSource().getLocation();
         libURL = new URL(classes, "../lib/");
      }
      else
      {
         libURL = new URL(deployDir);
      }

      DeploymentImpl p0 = new DeploymentImpl();
      p0.setName("CoreServices");
      p0.setRootURL(libURL);
      String[] files = {"p0.jar"};
      p0.setFiles(files);
      ArrayList<DeploymentBean> beans = new ArrayList<DeploymentBean>();
      // NamingService
      String nsName = "TheNamingService";
      DeploymentBeanImpl ns = new DeploymentBeanImpl(nsName,
            "org.jboss.test.profileservice.profiles.p0.beans.NamingService");
      beans.add(ns);
      // JTA
      String txName = "TheTxMgr";
      DeploymentBeanImpl txMgr = new DeploymentBeanImpl(txName,
            "org.jboss.test.profileservice.profiles.p0.beans.TxMgr");
      String[] txDeps = {nsName};
      txMgr.setDependencies(txDeps);
      beans.add(txMgr);
      // JCA
      String jcaName = "TheJCAMgr";
      DeploymentBeanImpl jca = new DeploymentBeanImpl(jcaName,
            "org.jboss.test.profileservice.profiles.p0.beans.JCAMgr");
      String[] jcaDeps = {nsName, txName};
      jca.setDependencies(jcaDeps);
      beans.add(jca);

      DeploymentBean[] tmp = new DeploymentBean[beans.size()];
      p0.setBeans(beans.toArray(tmp));
      deployments.put("CoreServices", p0);
   }

   public String getVersion()
   {
      return "1.0.0";
   }

   public DeploymentTemplate getTemplate(String name)
   {
      return null;
   }

   public void addDeployment(Deployment d)
   {
      throw new UnsupportedOperationException("simple1 is read-only");
   }

   public void removeDeployment(String name)
   {
      throw new UnsupportedOperationException("simple1 is read-only");
   }

   public Deployment[] getDeployments()
   {
      Deployment[] tmp = new Deployment[deployments.size()];
      deployments.values().toArray(tmp);
      return tmp;
   }

   public Map<String, Object> getConfig()
   {
      return null;
   }

   public Deployment getDeployment(String name)
      throws NoSuchDeploymentException
   {
      Deployment d = deployments.get(name);
      if( d == null )
         throw new NoSuchDeploymentException(name);
      return d;
   }
}
