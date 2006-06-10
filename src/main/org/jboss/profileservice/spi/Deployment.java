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

import java.net.URL;

import org.jboss.profileservice.spi.management.ManagedObject;

/**
 * A deployment is an encapsulation of the deployment beans and resources.
 * 
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface Deployment<TMetaData>
{
   /** The deployment type */
   public String getType();
   /** The deployment name */
   public String getName();
   /**
    * The root of the deployment contents
    * @return the root URL of the deployment contents.
    */
   public URL getRootURL();
   /** The deployment files, jars, resources paths relative to the rootURL */
   public String[] getFiles();
   /** The MC bean information for the deployment */
   public DeploymentBean<TMetaData>[] getBeans();
   /**
    * Get the top-level managed object view for the deployment beans.
    * 
    * @return 
    */
   public ManagedObject getManagedObject();

}
