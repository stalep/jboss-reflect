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
package org.jboss.virtual ;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.util.collection.WeakSet;
import org.jboss.virtual .plugins.vfs.helpers.FilterVirtualFileVisitor;
import org.jboss.virtual .plugins.vfs.helpers.MatchAllVirtualFileFilter;
import org.jboss.virtual .spi.VFSContext;
import org.jboss.virtual .spi.VirtualFileHandler;

/**
 * A virtual file as seen by the user
 * 
 * @author Scott.Stark@jboss.org
 * @author adrian@jboss.org
 * @version $Revision: 44334 $
 */
public class VirtualFile
   implements Serializable
{
   private static final long serialVersionUID = 1L;

   /** The virtual file handler */
   private final VirtualFileHandler handler;

   /** Whether we are closed */
   private AtomicBoolean closed = new AtomicBoolean(false); 
   
   /** The open streams */
   private transient final Set<InputStream> streams = Collections.synchronizedSet(new WeakSet());
   
   /**
    * Create a new VirtualFile.
    * 
    * @param handler the handler
    * @throws IllegalArgumentException if the handler is null
    */
   public VirtualFile(VirtualFileHandler handler)
   {
      if (handler == null)
         throw new IllegalArgumentException("Null handler");
      this.handler = handler;
   }

   /**
    * Get the virtual file handler
    * 
    * @return the handler
    * @throws IllegalStateException if the file is closed
    */
   protected VirtualFileHandler getHandler()
   {
      if (closed.get())
         throw new IllegalStateException("The virtual file is closed");
      return handler;
   }
   
   /**
    * Get the simple VF name (X.java)
    * 
    * @return the simple file name
    * @throws IllegalStateException if the file is closed
    */
   public String getName()
   {
      return getHandler().getName();
   }

   /**
    * Get the VFS relative path name (org/jboss/X.java)
    * 
    * @return the VFS relative path name
    * @throws IllegalStateException if the file is closed
    */
   public String getPathName()
   {
      return getHandler().getPathName();
   }

   /**
    * Get the VF URL (file://root/org/jboss/X.java)
    * 
    * @return the full URL to the VF in the VFS.
    * @throws MalformedURLException if a url cannot be parsed
    * @throws IllegalStateException if the file is closed
    */
   public URL toURL() throws MalformedURLException
   {
      return getHandler().toURL();
   }

   /**
    * When the file was last modified
    * 
    * @return the last modified time
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public long getLastModified() throws IOException
   {
      return getHandler().getLastModified();
   }
   
   /**
    * Get the size
    * 
    * @return the size
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public long getSize() throws IOException
   {
      return getHandler().getSize();
   }

   /**
    * Whether it is a directory
    * 
    * @return true if a directory.
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public boolean isDirectory() throws IOException
   {
      return getHandler().isDirectory();
   }

   /**
    * Whether it is a simple file
    * 
    * @return true if a simple file.
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public boolean isFile() throws IOException
   {
      return getHandler().isFile();
   }
   
   /**
    * Whether it is an archive
    * 
    * @return true when an archive
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public boolean isArchive() throws IOException
   {
      return getHandler().isArchive();
   }
   
   /**
    * Whether it is hidden
    * 
    * @return true when hidden
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public boolean isHidden() throws IOException
   {
      return getHandler().isHidden();
   }

   /**
    * Access the file contents.
    * 
    * @return an InputStream for the file contents.
    * @throws IOException for any error accessing the file system 
    * @throws IllegalStateException if the file is closed
    */
   public InputStream openStream() throws IOException
   {
      InputStream result = getHandler().openStream();
      streams.add(result);
      return result;
   }

   /**
    * Close the streams
    */
   public void closeStreams()
   {
      // Close the streams
      for (InputStream stream : streams)
      {
         if (stream != null)
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
      streams.clear();
   }

   /**
    * Close the file resources (stream, etc.)
    */
   public void close()
   {
      if (closed.getAndSet(true) == false)
      {
         closeStreams();
         handler.close();
      }
   }
   
   /**
    * Get the VFS instance for this virtual file
    * 
    * @return the VFS
    * @throws IllegalStateException if the file is closed
    */
   public VFS getVFS()
   {
      VFSContext context = getHandler().getVFSContext();
      return context.getVFS();
   }
   
   /**
    * Get the parent
    * 
    * @return the parent
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public VirtualFile getParent() throws IOException
   {
      VirtualFileHandler parent = getHandler().getParent();
      return parent.getVirtualFile();
   }
   
   /**
    * Get the children
    * 
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public List<VirtualFile> getChildren() throws IOException
   {
      return getChildren(null);
   }

   /**
    * Get the children
    * 
    * @param filter to filter the children
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed or it is not a directory
    */
   public List<VirtualFile> getChildren(VirtualFileFilter filter) throws IOException
   {
      if (isDirectory() == false)
         throw new IllegalStateException("Not a directory: " + this);

      if (filter == null)
         filter = MatchAllVirtualFileFilter.INSTANCE;
      FilterVirtualFileVisitor visitor = new FilterVirtualFileVisitor(filter, null);
      visit(visitor);
      return visitor.getMatched();
   }
   
   /**
    * Get all the children recursively<p>
    * 
    * This always uses {@link VisitorAttributes#RECURSE_DIRECTORIES}
    * 
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed
    */
   public List<VirtualFile> getChildrenRecursively() throws IOException
   {
      return getChildrenRecursively(null);
   }
   
   /**
    * Get all the children recursively<p>
    * 
    * This always uses {@link VisitorAttributes#RECURSE_DIRECTORIES}
    * 
    * @param filter to filter the children
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the file is closed or it is not a directory
    */
   public List<VirtualFile> getChildrenRecursively(VirtualFileFilter filter) throws IOException
   {
      if (isDirectory() == false)
         throw new IllegalStateException("Not a directory: " + this);

      if (filter == null)
         filter = MatchAllVirtualFileFilter.INSTANCE;
      FilterVirtualFileVisitor visitor = new FilterVirtualFileVisitor(filter, VisitorAttributes.RECURSE_DIRECTORIES);
      visit(visitor);
      return visitor.getMatched();
   }
   
   /**
    * Visit the virtual file system
    * 
    * @param visitor the visitor
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalArgumentException if the visitor is null
    * @throws IllegalStateException if the file is closed or it is not a directory
    */
   public void visit(VirtualFileVisitor visitor) throws IOException
   {
      if (isDirectory() == false)
         throw new IllegalStateException("Not a directory: " + this);
      getVFS().visit(this, visitor);
   }

   /**
    * Find a child
    * 
    * @param path the path 
    * @return the child
    * @throws IOException for any problem accessing the VFS (including the child does not exist)
    * @throws IllegalArgumentException if the path is null
    * @throws IllegalStateException if the file is closed or it is not a directory
    */
   public VirtualFile findChild(String path) throws IOException
   {
      VirtualFileHandler handler = getHandler();
      if (handler.isDirectory() == false)
         throw new IllegalStateException("Not a directory: " + this);

      path = VFSUtils.fixName(path);
      VirtualFileHandler child = handler.findChild(path);
      return child.getVirtualFile();
   }

   @Override
   public String toString()
   {
      return handler.toString();
   }

   @Override
   public int hashCode()
   {
      return handler.hashCode();
   }
   
   @Override
   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof VirtualFile == false)
         return false;
      VirtualFile other = (VirtualFile) obj;
      return handler.equals(other.handler);
   }
   
   @Override
   protected void finalize() throws Throwable
   {
      close();
   }
}
