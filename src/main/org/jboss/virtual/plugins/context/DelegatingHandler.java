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

package org.jboss.virtual.plugins.context;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * A delegating VirtualFileHandler that allows for overriding the delegate
 * parent and name. One usecase is a link which roots another VFSContext
 * under a different parent and name.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class DelegatingHandler extends AbstractVirtualFileHandler
{
   private static final long serialVersionUID = 1;
   private VirtualFileHandler delegate;

   /**
    * Create a DelegatingHandler
    * @param context - the context for the parent
    * @param parent - the parent of the delegate in this VFS
    * @param name - the name of the delegate in this VFS
    * @param delegate - the handler delegate
    */
   public DelegatingHandler(VFSContext context, VirtualFileHandler parent, String name,
         VirtualFileHandler delegate)
   {
      super(context, parent, name);
      this.delegate = delegate;
   }

   public VirtualFileHandler findChild(String path) throws IOException
   {
      return delegate.findChild(path);
   }

   public List<VirtualFileHandler> getChildren(boolean ignoreErrors) throws IOException
   {
      return delegate.getChildren(ignoreErrors);
   }

   public long getLastModified() throws IOException
   {
      return delegate.getLastModified();
   }

   public long getSize() throws IOException
   {
      return delegate.getSize();
   }

   public boolean isArchive() throws IOException
   {
      return delegate.isArchive();
   }

   public boolean isDirectory() throws IOException
   {
      return delegate.isDirectory();
   }

   public boolean isFile() throws IOException
   {
      return delegate.isFile();
   }

   public boolean isHidden() throws IOException
   {
      return delegate.isHidden();
   }

   public InputStream openStream() throws IOException
   {
      return delegate.openStream();
   }

   public URI toURI() throws URISyntaxException
   {
      return delegate.toURI();
   }

}
