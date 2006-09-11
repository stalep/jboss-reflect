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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.virtual.plugins.context.AbstractURLHandler;
import org.jboss.virtual.plugins.context.StructuredVirtualFileHandler;
import org.jboss.virtual.plugins.context.jar.JarUtils;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * FileHandler.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class FileHandler extends AbstractURLHandler
   implements StructuredVirtualFileHandler
{
   private static final long serialVersionUID = 1;
   /** The file */
   private transient File file;
   
   /**
    * Create a new FileHandler.
    * 
    * @param context the context
    * @param parent the parent
    * @param file the file
    * @param url the url
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null context, url
    */
   public FileHandler(FileSystemContext context, VirtualFileHandler parent, File file, URL url) throws IOException
   {
      super(context, parent, url, file.getName());

      this.file = file;
      if (file.exists() == false)
         throw new FileNotFoundException("File does not exist: " + file.getCanonicalPath());
   }
   /**
    * Create a new FileHandler
    *  
    * @param context the context
    * @param parent the parent
    * @param file the file
    * @param uri the uri
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null context, uri
    */
   public FileHandler(FileSystemContext context, VirtualFileHandler parent, File file, URI uri) throws IOException
   {
      this(context, parent, file, uri.toURL());
   }

   @Override
   public FileSystemContext getVFSContext()
   {
      return (FileSystemContext) super.getVFSContext();
   }
   
   /**
    * Get the file for this file handler
    * 
    * @return the file
    */
   protected File getFile()
   {
      checkClosed();
      return file;
   }
   
   @Override
   public long getLastModified()
   {
      return getFile().lastModified();
   }

   @Override
   public long getSize()
   {
      return getFile().length();
   }

   public boolean isArchive()
   {
      if (isDirectory() == false)
         return false;
      return JarUtils.isArchive(getName());
   }

   public boolean isDirectory()
   {
      return getFile().isDirectory();
   }

   public boolean isFile()
   {
      return getFile().isFile();
   }

   public boolean isHidden()
   {
      return getFile().isHidden();
   }

   public List<VirtualFileHandler> getChildren(boolean ignoreErrors) throws IOException
   {
      File parent = getFile();
      File[] files = parent.listFiles();
      if (files == null)
         throw new IOException("Error listing files: " + parent.getCanonicalPath());
      if (files.length == 0)
         return Collections.emptyList();

      FileSystemContext context = getVFSContext();
      
      List<VirtualFileHandler> result = new ArrayList<VirtualFileHandler>();
      for (File file : files)
      {
         try
         {
            VirtualFileHandler handler = context.createVirtualFileHandler(this, file);
            result.add(handler);
         }
         catch (IOException e)
         {
            if (ignoreErrors)
               log.trace("Ignored: " + e);
            else
               throw e;
         }
      }
      return result;
   }

   public VirtualFileHandler findChild(String path) throws IOException
   {
      return structuredFindChild(path);
   }

   public VirtualFileHandler createChildHandler(String name) throws IOException
   {
      FileSystemContext context = getVFSContext();
      File parentFile = getFile();
      File child = new File(parentFile, name);
      return context.createVirtualFileHandler(this, child);
   }

   private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException
   {
      in.defaultReadObject();
      // Initialize the transient values
      this.file = new File(getURL().getPath());
   }

}
