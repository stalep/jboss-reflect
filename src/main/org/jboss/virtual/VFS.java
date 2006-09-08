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
import java.net.URISyntaxException;
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
    * Get the virtual file system for a root url
    * 
    * @param rootURL the root url
    * @return the virtual file system
    * @throws IOException if there is a problem accessing the VFS
    * @throws URISyntaxException if the URL is not a valid URI
    * @throws IllegalArgumentException if the rootURL is null
    */
   public static VFS getVFS(URL rootURL) throws IOException
   {
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(rootURL);
      VFSContext context = factory.getVFS(rootURL);
      return context.getVFS();
   }
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
      VFSContext context = factory.getVFS(rootURI);
      return context.getVFS();
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
      return vfs.findChildFromRoot(name);
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
    * Get a parent
    * 
    * @param child the child
    * @return the parent or null if there is no parent
    * @throws IOException for any problem accessing the VFS (including the child does not exist)
    * @throws IllegalArgumentException if the child is null
    */
   public VirtualFile getParent(VirtualFile child) throws IOException
   {
      if (child == null)
         throw new IllegalArgumentException("Null parent");
         
      VirtualFileHandler handler = child.getHandler();
      VirtualFileHandler parent = handler.getParent();
      return parent.getVirtualFile();
   }
   
   /**
    * Find a child
    * 
    * @param parent the context file
    * @param path the child path
    * @return the child
    * @throws IOException for any problem accessing the VFS (including the child does not exist)
    * @throws IllegalArgumentException if the parent or path is null
    */
   public VirtualFile findChild(VirtualFile parent, String path) throws IOException
   {
      if (parent == null)
         throw new IllegalArgumentException("Null parent");
      if (path == null)
         throw new IllegalArgumentException("Null path");
      
      VirtualFileHandler handler = parent.getHandler();
      VFSContext handlerContext = handler.getVFSContext();
      path = VFSUtils.fixName(path);
      VirtualFileHandler result = handlerContext.findChild(handler, path);
      return result.getVirtualFile();
   }
   
   /**
    * Find a child from the root
    * 
    * @param path the child path
    * @return the child
    * @throws IOException for any problem accessing the VFS (including the child does not exist)
    * @throws IllegalArgumentException if the path is null
    */
   public VirtualFile findChildFromRoot(String path) throws IOException
   {
      if (path == null)
         throw new IllegalArgumentException("Null path");
      
      VirtualFileHandler handler = context.getRoot();
      path = VFSUtils.fixName(path);
      VirtualFileHandler result = context.findChild(handler, path);
      return result.getVirtualFile();
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
      return getRoot().getChildren(null);
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
      return getRoot().getChildren(filter);
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
    * @throws IllegalStateException if the file is closed or it is not a directory
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
    */
   public void visit(VirtualFileVisitor visitor) throws IOException
   {
      VirtualFileHandler handler = context.getRoot();
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
    */
   public void visit(VirtualFile file, VirtualFileVisitor visitor) throws IOException
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
