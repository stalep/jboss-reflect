/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.spi.scope;

/**
 * CommonLevels.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface CommonLevels
{
   /** The domain level name */
   String DOMAIN_NAME = "DOMAIN";

   /** The domain level */
   ScopeLevel DOMAIN = new ScopeLevel(100, DOMAIN_NAME);

   /** The cluster level name */
   String CLUSTER_NAME = "CLUSTER";

   /** The cluster level */
   ScopeLevel CLUSTER = new ScopeLevel(DOMAIN.getLevel() + 100, CLUSTER_NAME);

   /** The machine level name */
   String MACHINE_NAME = "MACHINE";

   /** The machine level */
   ScopeLevel MACHINE = new ScopeLevel(CLUSTER.getLevel() + 100, MACHINE_NAME);

   /** The node level name */
   String NODE_NAME = "NODE";

   /** The node level */
   ScopeLevel NODE = new ScopeLevel(MACHINE.getLevel() + 100, NODE_NAME);

   /** The jvm level name */
   String JVM_NAME = "JVM";

   /** The jvm level */
   ScopeLevel JVM = new ScopeLevel(NODE.getLevel() + 100, JVM_NAME);

   /** The server level name */
   String SERVER_NAME = "SERVER";

   /** The server level */
   ScopeLevel SERVER = new ScopeLevel(JVM.getLevel() + 100, SERVER_NAME);

   /** The subsystem level name */
   String SUBSYSTEM_NAME = "SUBSYSTEM";

   /** The subsystem level */
   ScopeLevel SUBSYSTEM = new ScopeLevel(SERVER.getLevel() + 100, SUBSYSTEM_NAME);

   /** The application level name */
   String APPLICATION_NAME = "APPLICATION";

   /** The application level */
   ScopeLevel APPLICATION = new ScopeLevel(SUBSYSTEM.getLevel() + 100, APPLICATION_NAME);

   /** The deployment level name */
   String DEPLOYMENT_NAME = "DEPLOYMENT";

   /** The deployment level */
   ScopeLevel DEPLOYMENT = new ScopeLevel(APPLICATION.getLevel() + 100, DEPLOYMENT_NAME);

   /** The class level name */
   String CLASS_NAME = "CLASS";

   /** The class level */
   ScopeLevel CLASS = new ScopeLevel(DEPLOYMENT.getLevel() + 100, CLASS_NAME);

   /** The instance level name */
   String INSTANCE_NAME = "INSTANCE";

   /** The instance level */
   ScopeLevel INSTANCE = new ScopeLevel(CLASS.getLevel() + 100, INSTANCE_NAME);

   /** The join point level name */
   String JOINPOINT_NAME = "JOINPOINT";

   /** The join point level */
   ScopeLevel JOINPOINT = new ScopeLevel(INSTANCE.getLevel() + 100, JOINPOINT_NAME);

   /** The join point override level name */
   String JOINPOINT_OVERRIDE_NAME = "JOINPOINT_OVERRIDE";

   /** The join point override level */
   ScopeLevel JOINPOINT_OVERRIDE = new ScopeLevel(JOINPOINT.getLevel() + 100, JOINPOINT_OVERRIDE_NAME);

   /** The thread level name */
   String THREAD_NAME = "THREAD";

   /** The thread level */
   ScopeLevel THREAD = new ScopeLevel(JOINPOINT_OVERRIDE.getLevel() + 100, THREAD_NAME);

   /** The unit of work level name */
   String WORK_NAME = "WORK";

   /** The unit of work level */
   ScopeLevel WORK = new ScopeLevel(THREAD.getLevel() + 100, WORK_NAME);

   /** The request level name */
   String REQUEST_NAME = "REQUEST";

   /** The request level */
   ScopeLevel REQUEST = new ScopeLevel(WORK.getLevel() + 100, REQUEST_NAME);
}
