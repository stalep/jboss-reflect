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
import java.net.URI;
import java.net.URL;
import java.util.List;

import org.jboss.virtual.plugins.vfs.helpers.WrappingVirtualFileHandlerVisitor;
import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VFSContextFactory;
import org.jboss.virtual.spi.VFSContextFactoryLocator;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * Virtual File System
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class VFS
{
   /** The VFS Context */
   private final VFSContext context;

   /**
    * Get the virtual file system for a root uri
    * 
    * @param rootURI the root URI
    * @return the virtual file system
    * @throws IOException if there is a problem accessing the VFS
    * @throws IllegalArgumentException if the rootURL is null
    */
   public static VFS getVFS(URI rootURI) throws IOException
   {
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(rootURI);
      if (factory == null)
         throw new IOException("No context factory for " + rootURI);
      VFSContext context = factory.getVFS(rootURI);
      return context.getVFS();
   }

   /**
    * Get the root virtual file
    * 
    * @param rootURI the root uri
    * @return the virtual file
    * @throws IOException if there is a problem accessing the VFS
    * @throws IllegalArgumentException if the rootURL
    */
   public static VirtualFile getRoot(URI rootURI) throws IOException
   {
      VFS vfs = getVFS(rootURI);
      return vfs.getRoot();
   }

   /**
    * Get a virtual file
    * 
    * @param rootURI the root uri
    * @param name the path name
    * @return the virtual file
    * @throws IOException if there is a problem accessing the VFS
    * @throws IllegalArgumentException if the rootURL or name is null
    */
   public static VirtualFile getVirtualFile(URI rootURI, String name) throws IOException
   {
      VFS vfs = getVFS(rootURI);
      return vfs.findChild(name);
   }

   /**
    * Get the virtual file system for a root url
    * 
    * @param rootURL the root url
    * @return the virtual file system
    * @throws IOException if there is a problem accessing the VFS
    * @throws IllegalArgumentException if the rootURL is null
    */
   public static VFS getVFS(URL rootURL) throws IOException
   {
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(rootURL);
      if (factory == null)
         throw new IOException("No context factory for " + rootURL);
      VFSContext context = factory.getVFS(rootURL);
      return context.getVFS();
   }

   /**
    * Get the root virtual file
    * 
    * @param rootURL the root url
    * @return the virtual file
    * @throws IOException if there is a problem accessing the VFS
    * @throws IllegalArgumentException if the rootURL
    */
   public static VirtualFile getRoot(URL rootURL) throws IOException
   {
      VFS vfs = getVFS(rootURL);
      return vfs.getRoot();
   }

   /**
    * Get a virtual file
    * 
    * @param rootURL the root url
    * @param name the path name
    * @return the virtual file
    * @throws IOException if there is a problem accessing the VFS
    * @throws IllegalArgumentException if the rootURL or name is null
    */
   public static VirtualFile getVirtualFile(URL rootURL, String name) throws IOException
   {
      VFS vfs = getVFS(rootURL);
      return vfs.findChild(name);
   }

   /**
    * Create a new VFS.
    * 
    * @param context the context
    * @throws IllegalArgumentException for a null context
    */
   public VFS(VFSContext context)
   {
      if (context == null)
         throw new IllegalArgumentException("Null name");
      this.context = context;
   }
   
   /**
    * Get the root file of this VFS
    * 
    * @return the root
    * @throws IOException for any problem accessing the VFS
    */
   public VirtualFile getRoot() throws IOException
   {
      VirtualFileHandler handler = context.getRoot();
      return handler.getVirtualFile();
   }
   
   /**
    * Find a child from the root
    * 
    * @param path the child path
    * @return the child
    * @throws IOException for any problem accessing the VFS (including the child does not exist)
    * @throws IllegalArgumentException if the path is null
    */
   public VirtualFile findChild(String path) throws IOException
   {
      if (path == null)
         throw new IllegalArgumentException("Null path");
      
      VirtualFileHandler handler = context.getRoot();
      path = VFSUtils.fixName(path);
      VirtualFileHandler result = context.findChild(handler, path);
      return result.getVirtualFile();
   }
   
   /**
    * Find a child from the root
    * 
    * @Deprecated use {@link #findChild(String)}
    * @param path the child path
    * @return the child
    * @throws IOException for any problem accessing the VFS (including the child does not exist)
    * @throws IllegalArgumentException if the path is null
    */
   @Deprecated
   public VirtualFile findChildFromRoot(String path) throws IOException
   {
      return findChild(path);
   }
   
   /**
    * Get the children
    * 
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the root is not a directory
    */
   public List<VirtualFile> getChildren() throws IOException
   {
      return getRoot().getChildren(null);
   }

   /**
    * Get the children
    * 
    * @param filter to filter the children
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the root is not a directory
    */
   public List<VirtualFile> getChildren(VirtualFileFilter filter) throws IOException
   {
      return getRoot().getChildren(filter);
   }
   
   /**
    * Get all the children recursively<p>
    * 
    * This always uses {@link VisitorAttributes#RECURSE_DIRECTORIES}
    * 
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the root is not a directory
    */
   public List<VirtualFile> getChildrenRecursively() throws IOException
   {
      return getRoot().getChildrenRecursively(null);
   }
   
   /**
    * Get all the children recursively<p>
    * 
    * This always uses {@link VisitorAttributes#RECURSE_DIRECTORIES}
    * 
    * @param filter to filter the children
    * @return the children
    * @throws IOException for any problem accessing the virtual file system
    * @throws IllegalStateException if the root is not a directory
    */
   public List<VirtualFile> getChildrenRecursively(VirtualFileFilter filter) throws IOException
   {
      return getRoot().getChildrenRecursively(filter);
   }
   
   /**
    * Visit the virtual file system from the root
    * 
    * @param visitor the visitor
    * @throws IOException for any problem accessing the VFS
    * @throws IllegalArgumentException if the visitor is null
    * @throws IllegalStateException if the root is not a directory
    */
   public void visit(VirtualFileVisitor visitor) throws IOException
   {
      VirtualFileHandler handler = context.getRoot();
      if (handler.isDirectory() == false)
         throw new IllegalStateException("Not a directory");
      WrappingVirtualFileHandlerVisitor wrapper = new WrappingVirtualFileHandlerVisitor(visitor);
      context.visit(handler, wrapper);
   }

   /**
    * Visit the virtual file system
    * 
    * @param file the file
    * @param visitor the visitor
    * @throws IOException for any problem accessing the VFS
    * @throws IllegalArgumentException if the file or visitor is null
    * @throws IllegalStateException if the root is not a directory
    */
   protected void visit(VirtualFile file, VirtualFileVisitor visitor) throws IOException
   {
      if (file == null)
         throw new IllegalArgumentException("Null file");

      VirtualFileHandler handler = file.getHandler();
      WrappingVirtualFileHandlerVisitor wrapper = new WrappingVirtualFileHandlerVisitor(visitor);
      VFSContext handlerContext = handler.getVFSContext();
      handlerContext.visit(handler, wrapper);
   }

   @Override
   public String toString()
   {
      return context.toString();
   }

   @Override
   public int hashCode()
   {
      return context.hashCode();
   }
   
   @Override
   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof VFS == false)
         return false;
      VFS other = (VFS) obj;
      return context.equals(other.context);
   }
}
