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

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */

public interface VirtualFile
{
   /**
    * Get the simple VF name (X.java)
    * @return the simple file name
    */
   public String getName();

   /**
    * Get the VFS relative path name (org/jboss/X.java)
    * @return the VFS relative path name
    */
   public String getPathName();

   public VirtualFile[] getChildren() throws IOException;
   public List<VirtualFile> getChildrenRecursively() throws IOException;
   public List<VirtualFile> getChildrenRecursively(VirtualFileFilter filter) throws IOException;
   public VirtualFile findChild(String name) throws IOException;


   // Convience attribute accessors
   public long getLastModified();
   public long getSize();

   /**
    * Is this VF a directory that can contain children
    * @return true if a directory.
    */
   public boolean isDirectory();

   /**
    * Is this VF a simple file that cannot contain children.
    * @return  true if a regular file.
    */
   public boolean isFile();
   public boolean isArchive();

   /**
    * Access the file contents.
    * @return An InputStream for the file contents.
    * @throws IOException
    */
   public InputStream openStream() throws IOException;

   /**
    * Close the file resources (stream, etc.)
    * @throws IOException
    */
   public void close() throws IOException;

   /**
    * Get the VF URL (file://root/org/jboss/X.java)
    * @return the full URL to the VF in the VFS.
    * @throws MalformedURLException
    */
   public URL toURL() throws MalformedURLException;
}
