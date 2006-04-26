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

package org.jboss.vfs.spi;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.util.Iterator;
import java.io.FileNotFoundException;
import java.io.IOException;


/** Prototype for a read-only VFS where virtual files are represented by URLs.
 * A VFS is a tree of URLs segmented into paths by URL protocol.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface ReadOnlyVFS
{
   /**
    * Locate a file in the VFS given its URL path.
    * 
    * @param path - the absolute path to the virtual file (file:/root/deploy/x.ear)
    * @return the matching VirtualFile
    * @throws FileNotFoundException throw if the path could not be resolved 
    */
   public VirtualFile resolveFile(String path)
      throws FileNotFoundException, MalformedURLException;

   /**
    * Locate a file in the VFS given a relative URL path and contexts in
    * the VFS to search from.
    * 
    * @param path - a relative URL path (x.ear)
    * @param searchContexts - the absolute paths in the VFS of the contexts to search from
    * @return the matching VirtualFile
    * @throws FileNotFoundException throw if the path could not be resolved 
    */
   public VirtualFile resolveFile(String path, List<String> searchContexts)
      throws IOException;

   /**
    * Find all files in the VFS matching the relative URL path.
    * @param path - a relative URL path (x.ear)
    * @return A possibly empty list of matching files
    */
   public List<VirtualFile> resolveFiles(String path);
   /**
    * Locate all files in the VFS given a relative URL path and contexts in
    * the VFS to search from.
    * 
    * @param path - a relative URL path (x.ear)
    * @param searchContexts - the absolute paths in the VFS of the contexts to search from
    * @return A possibly empty list of matching files
    * @return A possibly empty list of matching files
    */
   public List<VirtualFile> resolveFiles(String path, List<URL> searchContexts);

   /**
    * Clear any caches associated with the VFS
    */
   public void clear();

   /**
    * Scan the VFS for files accepted by the visitor.
    * 
    * @param acceptVisitor - the visitor that defines which files to accept
    * @return Iterator<VirtualFile> of the matches
    */
   public Iterator<VirtualFile> scan(VFSVisitor acceptVisitor);
}
