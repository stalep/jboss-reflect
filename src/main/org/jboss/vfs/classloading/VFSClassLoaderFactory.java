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
package org.jboss.vfs.classloading;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;

import org.jboss.logging.Logger;
import org.jboss.vfs.VFSFactory;
import org.jboss.vfs.spi.ReadOnlyVFS;

/**
 * A factory for creating VFSClassLoader instances.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class VFSClassLoaderFactory
{
   private static Logger log = Logger.getLogger(VFSClassLoaderFactory.class);

   public static VFSClassLoader newClassLoader(URL rootURL, String[] paths, VFSFactory factory)
      throws IOException
   {
      log.debug("newClassLoader, rootURL="+rootURL+", paths="+Arrays.asList(paths));
      ReadOnlyVFS vfs = factory.getVFS(rootURL);
      return SecurityActions.newClassLoader(paths, vfs);
   }
   public static VFSClassLoader newClassLoader(String[] paths, ReadOnlyVFS vfs)
   {
      log.debug("newClassLoader, vfs.rootURL="+vfs.getRootURL()+", paths="+Arrays.asList(paths));
      return SecurityActions.newClassLoader(paths, vfs);
   }
   public static VFSClassLoader newClassLoader(String[] paths, ReadOnlyVFS vfs, ClassLoader parent)
   {
      log.debug("newClassLoader, vfs.rootURL="+vfs.getRootURL()+", paths="+Arrays.asList(paths)
         +", parent="+parent);
      return SecurityActions.newClassLoader(paths, vfs, parent);
   }
}