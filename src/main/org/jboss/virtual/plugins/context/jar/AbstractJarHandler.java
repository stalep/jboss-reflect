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
package org.jboss.virtual.plugins.context.jar;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.virtual.plugins.context.AbstractURLHandler;
import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * AbstractJarHandler.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AbstractJarHandler extends AbstractURLHandler
{
   /** The jar file */
   private JarFile jar;

   /** The jar entries */
   private List<VirtualFileHandler> entries;
   
   /**
    * Get a jar entry name
    * 
    * @param entry the entry
    * @return the name
    * @throws IllegalArgumentException for a null entry
    */
   protected static String getEntryName(JarEntry entry)
   {
      if (entry == null)
         throw new IllegalArgumentException("Null entry");
      return entry.getName();
   }
   
   /**
    * Create a new JarHandler.
    * 
    * @param context the context
    * @param parent the parent
    * @param url the url
    * @param name the name
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null context, url or vfsPath
    */
   protected AbstractJarHandler(VFSContext context, VirtualFileHandler parent, URL url, String name) throws IOException
   {
      super(context, parent, url, name);
   }

   /**
    * Get the jar.
    * 
    * @return the jar.
    */
   public JarFile getJar()
   {
      return jar;
   }

   /**
    * Initialise the jar file
    * 
    * @param jarFile the jar file
    * @throws IOException for any error reading the jar file
    * @throws IllegalArgumentException for a null jarFile
    */
   protected void initJarFile(JarFile jarFile) throws IOException
   {
      if (this.jar != null)
         throw new IllegalStateException("jarFile has already been set");

      this.jar = jarFile;

      Enumeration<JarEntry> enumeration = jar.entries();
      if (enumeration.hasMoreElements() == false)
      {
         entries = Collections.emptyList();
         return;
      }
      
      entries = new ArrayList<VirtualFileHandler>();
      while (enumeration.hasMoreElements())
      {
         JarEntry entry = enumeration.nextElement();
         VirtualFileHandler handler = createVirtualFileHandler(this, entry);
         entries.add(handler);
      }
   }
   
   protected void doClose()
   {
      /* TODO Figure out why this breaks things randomly
      try
      {
         if (jar != null)
            jar.close();
      }
      catch (IOException ignored)
      {
      }
      */
   }

   public boolean isArchive()
   {
      checkClosed();
      return true;
   }

   public boolean isDirectory()
   {
      checkClosed();
      return true;
   }

   public List<VirtualFileHandler> getChildren(boolean ignoreErrors) throws IOException
   {
      checkClosed();
      return entries;
   }

   public VirtualFileHandler findChild(String path) throws IOException
   {
      if (path == null)
         throw new IllegalArgumentException("Null path");

      if (path.length() == 0)
         return this;
      
      List<VirtualFileHandler> children = getChildren(false);
      for (VirtualFileHandler child : children)
      {
         // Try a simple match
         String name = child.getName();
         if (name.equals(path))
            return child;
         
         // Try a partial match on a nested jar
         if (child.isArchive() && path.startsWith(name))
         {
            String remainingPath = path.substring(name.length()+1);
            return child.findChild(remainingPath);
         }
      }
      throw new IOException("Child not found " + path + " for " + this);
   }

   /**
    * Create a new virtual file handler
    * 
    * @param parent the parent
    * @param entry the entry
    * @return the handler
    * @throws IOException for any error accessing the file system
    * @throws IllegalArgumentException for a null parent or entry
    */
   public VirtualFileHandler createVirtualFileHandler(VirtualFileHandler parent, JarEntry entry) throws IOException
   {
      if (parent == null)
         throw new IllegalArgumentException("Null parent");
      if (entry == null)
         throw new IllegalArgumentException("Null entry");

      // Question: Why doesn't this work properly?
      // URL url = new URL(parent.toURL(), entry.getName());
      StringBuilder buffer = new StringBuilder();
      buffer.append(parent.toURL());
      if (buffer.charAt(buffer.length()-1) != '/')
         buffer.append('/');
      buffer.append(entry.getName());
      URL url = new URL(buffer.toString());

      VFSContext context = parent.getVFSContext();

      VirtualFileHandler vfh;
      if (JarUtils.isArchive(entry.getName()))
      {
         String flag = context.getOptions().get("useNoCopyJarHandler");
         boolean useNoCopyJarHandler = Boolean.valueOf(flag);

         if( useNoCopyJarHandler )
            vfh = new NoCopyNestedJarHandler(context, parent, jar, entry, url);
         else
            vfh = new NestedJarHandler(context, parent, jar, entry, url);
      }
      else
      {
         vfh = new JarEntryHandler(context, parent, jar, entry, url);         
      }
      return vfh;
   }
}
