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
package org.jboss.virtual;

import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.jboss.logging.Logger;

/**
 * VFS Utilities
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class VFSUtils
{
   /** The log */
   private static final Logger log = Logger.getLogger(VFSUtils.class);
   
   /**
    * Get a manifest from a virtual file,
    * assuming the virtual file is the root of an archive
    * 
    * @param archive the root the archive
    * @return the manifest or null if not found
    * @throws IOException if there is an error reading the manifest or the
    *         virtual file is closed
    * @throws IllegalArgumentException for a null archive or it is not an archive
    */
   public Manifest getManifest(VirtualFile archive) throws IOException
   {
      if (archive == null)
         throw new IllegalArgumentException("Null archive");
      if (archive.isArchive() == false)
         throw new IllegalArgumentException("Not an archive: " + archive);
      
      VirtualFile manifest;
      try
      {
         manifest = archive.findChild(JarFile.MANIFEST_NAME); 
      }
      catch (IOException ignored)
      {
         log.debug("Can't find manifest for " + archive);
         return null;
      }

      InputStream stream = manifest.openStream();
      try
      {
         return new Manifest(stream);
      }
      finally
      {
         try
         {
            stream.close();
         }
         catch (IOException ignored)
         {
         }
      }
   }
   
   /**
    * Get a manifest from a virtual file system,
    * assuming the root of the VFS is the root of an archive
    * 
    * @param archive the vfs
    * @return the manifest or null if not found
    * @throws IOException if there is an error reading the manifest
    * @throws IllegalArgumentException for a null archive
    */
   public Manifest getManifest(VFS archive) throws IOException
   {
      VirtualFile root = archive.getRoot();
      return getManifest(root);
   }
   
   /**
    * Fix a name (removes any trailing slash)
    * 
    * @param name the name to fix
    * @return the fixed name
    */
   public static String fixName(String name)
   {
      int length = name.length();
      if (length <= 1)
         return name;
      if (name.charAt(length-1) == '/')
         return name.substring(0, length-1);
      return name;
   }
}
