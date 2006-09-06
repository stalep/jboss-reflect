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
package org.jboss.virtual.plugins.context.file;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.plugins.context.AbstractVFSContext;
import org.jboss.virtual.plugins.context.jar.JarHandler;
import org.jboss.virtual.plugins.context.jar.JarUtils;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * FileSystemContext.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class FileSystemContext extends AbstractVFSContext
{
   /** The root file */
   private final VirtualFileHandler root;
   
   /** A reference to the virtual file of the root to stop it getting closed */
   private final VirtualFile rootFile;
   
   /**
    * Get the file for a url
    * 
    * @param url the url
    * @return the file
    * @throws IOException for any error accessing the file system
    * @throws IllegalArgumentException for a null url
    */
   private static File getFile(URL url) throws IOException
   {
      if (url == null)
         throw new IllegalArgumentException("Null url");
      
      return new File(url.getPath());
   }
   
   /**
    * Get the url for a file
    * 
    * @param file the file
    * @return the url
    * @throws IOException for any error accessing the file system
    * @throws IllegalArgumentException for a null file
    */
   private static URL getFileURL(File file) throws IOException
   {
      if (file == null)
         throw new IllegalArgumentException("Null file");
      return file.toURL();
   }
   
   /**
    * Create a new FileSystemContext.
    * 
    * @param rootURL the root url
    * @throws IOException for an error accessing the file system
    */
   public FileSystemContext(URL rootURL) throws IOException
   {
      this(rootURL, getFile(rootURL));
   }
   
   /**
    * Create a new FileSystemContext.
    * 
    * @param file the root file
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null file
    */
   public FileSystemContext(File file) throws IOException
   {
      this(getFileURL(file), file);
   }
   
   /**
    * Create a new FileSystemContext.
    * 
    * @param rootURL the root url
    * @param file the file
    * @throws IOException for an error accessing the file system
    */
   private FileSystemContext(URL rootURL, File file) throws IOException
   {
      super(rootURL);
      root = createVirtualFileHandler(null, file);
      rootFile = root.getVirtualFile();
   }

   public VirtualFileHandler getRoot() throws IOException
   {
      return root;
   }

   /**
    * Create a new virtual file handler
    * 
    * @param parent the parent
    * @param file the file
    * @return the handler
    * @throws IOException for any error accessing the file system
    * @throws IllegalArgumentException for a null file
    */
   public VirtualFileHandler createVirtualFileHandler(VirtualFileHandler parent, File file) throws IOException
   {
      if (file == null)
         throw new IllegalArgumentException("Null file");
      
      URL fileURL = getFileURL(file);
      if (file.isFile() && JarUtils.isArchive(file.getName()))
      {
         URL url = JarUtils.createJarURL(fileURL);
         String name = file.getName();
         return new JarHandler(this, parent, url, name);
      }
      return createVirtualFileHandler(parent, file, fileURL);
   }

   /**
    * Create a new virtual file handler
    * 
    * @param parent the parent
    * @param file the file
    * @param url the url
    * @return the handler
    * @throws IOException for any error accessing the file system
    * @throws IllegalArgumentException for a null file
    */
   public VirtualFileHandler createVirtualFileHandler(VirtualFileHandler parent, File file, URL url) throws IOException
   {
      if (file == null)
         throw new IllegalArgumentException("Null file");
      if (url == null)
         throw new IllegalArgumentException("Null url");
      
      return new FileHandler(this, parent, file, url);
   }
   
   @Override
   protected void finalize() throws Throwable
   {
      if (rootFile != null)
         rootFile.close();
      super.finalize();
   }
}
