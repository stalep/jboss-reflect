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
package org.jboss.test.repository.test;

import java.util.HashMap;

import junit.framework.TestCase;

import org.jboss.repository.spi.Key;
import org.jboss.repository.spi.BasicMetaData;
import org.jboss.repository.spi.CommonNames;
import org.jboss.repository.plugins.basic.BasicKernelRepository;

/**
 * Registry Test Case.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class RepositoryTestCase extends TestCase
{
   public RepositoryTestCase(String name)
   {
      super(name);
   }

   public void testBasictRegistry() throws Throwable
   {
      BasicKernelRepository bkp = new BasicKernelRepository();
      HashMap baseAttrs = new HashMap();
      baseAttrs.put(CommonNames.DOMAIN, "domain0");
      // Keys from the bottom up (thread - domain)
      Key baseKey = new Key("permissions", baseAttrs);
      BasicMetaData baseData = new BasicMetaData(0, "domain0");
      bkp.addMetaData(baseKey, baseData);

      HashMap clusterAttrs = new HashMap(baseAttrs);
      clusterAttrs.put(CommonNames.CLUSTER, "cluster0");
      BasicMetaData clusterData = new BasicMetaData(0, "cluster0");
      Key clusterKey = new Key("permissions", clusterAttrs);
      bkp.addMetaData(clusterKey, clusterData);

      HashMap serverAttrs = new HashMap(clusterAttrs);
      serverAttrs.put(CommonNames.SERVER, "server0");
      BasicMetaData serverData = new BasicMetaData(0, "server0");
      Key serverKey = new Key("permissions", serverAttrs);
      bkp.addMetaData(serverKey, serverData);

      HashMap appAttrs = new HashMap(serverAttrs);
      appAttrs.put(CommonNames.APPLICATION, "app0");
      BasicMetaData appData = new BasicMetaData(0, "app0");
      Key appKey = new Key("permissions", appAttrs);
      bkp.addMetaData(appKey, appData);

      HashMap deployAttrs = new HashMap(appAttrs);
      deployAttrs.put(CommonNames.DEPLOYMENT, "deploy0");
      BasicMetaData deployData = new BasicMetaData(0, "deploy0");
      Key deployKey = new Key("permissions", deployAttrs);
      bkp.addMetaData(deployKey, deployData);

      HashMap sessionAttrs = new HashMap(deployAttrs);
      sessionAttrs.put(CommonNames.SESSION, "session0");
      BasicMetaData sessionData = new BasicMetaData(0, "session0");
      Key sessionKey = new Key("permissions", sessionAttrs);
      bkp.addMetaData(sessionKey, sessionData);

      Object base = bkp.getMetaData(baseKey);
      assertTrue(base.equals("domain0"));

      Object cluster = bkp.getMetaData(clusterKey);
      assertTrue(cluster.equals("cluster0"));

      Object server = bkp.getMetaData(serverKey);
      assertTrue(server.equals("server0"));

      Object app = bkp.getMetaData(appKey);
      assertTrue(app.equals("app0"));

      Object deploy = bkp.getMetaData(deployKey);
      assertTrue(deploy.equals("deploy0"));

      Object session = bkp.getMetaData(sessionKey);
      assertTrue(session.equals("session0"));
   }

}
