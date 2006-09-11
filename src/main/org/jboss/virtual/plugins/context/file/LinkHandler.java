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

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.jboss.virtual.VFSUtils;
import org.jboss.virtual.plugins.context.AbstractURLHandler;
import org.jboss.virtual.plugins.context.StructuredVirtualFileHandler;
import org.jboss.virtual.spi.LinkInfo;
import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VFSContextFactory;
import org.jboss.virtual.spi.VFSContextFactoryLocator;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * A handler for link directories.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class LinkHandler extends AbstractURLHandler
   implements StructuredVirtualFileHandler
{
   private static final long serialVersionUID = 1;
   /** The link information */
   private List<LinkInfo> links;
   private HashMap<String, VirtualFileHandler> linkTargets =
      new HashMap<String, VirtualFileHandler>();
   
   /**
    * Create a new LinkHandler.
    * 
    * @param context the context
    * @param parent the parent
    * @param file the file
    * @param uri the uri
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null context, url
    */
   public LinkHandler(FileSystemContext context, VirtualFileHandler parent, URI uri, String name,
         List<LinkInfo> links)
      throws IOException
   {
      super(context, parent, uri.toURL(), name);
      this.links = links;
   }

   @Override
   public boolean isArchive()
   {
      return false;
   }

   public boolean isDirectory()
   {
      return true;
   }

   @Override
   public boolean isFile()
   {
      return false;
   }

   public List<VirtualFileHandler> getChildren(boolean ignoreErrors) throws IOException
   {
      List<VirtualFileHandler> result = new ArrayList<VirtualFileHandler>();
      for (LinkInfo link : links)
      {
         try
         {
            String name = link.getName();
            URI linkURI = link.getLinkTarget();
            if( name == null )
               name = VFSUtils.getName(linkURI);
            VirtualFileHandler handler = linkTargets.get(name);
            if( handler == null )
            {
               handler = createLinkHandler(link);
               linkTargets.put(name, handler);
            }
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
      VirtualFileHandler handler = linkTargets.get(name);
      if( handler == null )
      {
         for (LinkInfo link : links)
         {
            String infoName = link.getName();
            if( infoName == null )
            {
               infoName = VFSUtils.getName(link.getLinkTarget());
            }
            
            if( name.equals(infoName) )
            {
               handler = createLinkHandler(link);
               linkTargets.put(name, handler);
            }
         }
      }
      return handler;
   }

   @Override
   protected void doClose()
   {
      super.doClose();
      links.clear();
   }
   
   protected VirtualFileHandler createLinkHandler(LinkInfo info)
      throws IOException
   {
      URI linkURI = info.getLinkTarget();
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(linkURI);
      VFSContext context = factory.getVFS(linkURI);
      VirtualFileHandler handler = context.getRoot();
      // TODO: if the factory caches contexts the root handler may not point to the link
      return handler;
   }
}
