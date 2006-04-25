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
package org.jboss.vfs;

import org.jboss.vfs.spi.ReadOnlyVFS;

import java.net.URL;
import java.io.IOException;

/**
 * The entry point to obtaining a VFS for a given URL root mount point
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface VFSFactory
{
   /**
    * Get the URL protocols for which vfs factories exist
    * @return list of supported protocols.
    */
   public String[] getProtocols();

   /**
    * Obtain a vfs for the given root url.
    * 
    * @param rootURL - the URL for the root of the virtual file system
    * @return 
    * @throws IOException - thrown if the root cannot be opened/accessed
    */
   public ReadOnlyVFS getVFS(URL rootURL) throws IOException;
}
