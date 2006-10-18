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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.JarURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.virtual.plugins.context.AbstractURLHandler;
import org.jboss.virtual.plugins.context.StructuredVirtualFileHandler;
import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * AbstractJarHandler.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 1.1 $
 */
public class AbstractJarHandler extends AbstractURLHandler
   implements StructuredVirtualFileHandler
{
   /** serialVersionUID */
   private static final long serialVersionUID = 1;

   /** The jar file */
   private transient JarFile jar;

   /** The jar entries */
   private transient List<VirtualFileHandler> entries;
   private transient Map<String, VirtualFileHandler> entryMap;

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
         entryMap = Collections.emptyMap();
         return;
      }

      // Go through and create a structured representation of the jar
      Map<String, VirtualFileHandler> parentMap = new HashMap<String, VirtualFileHandler>();
      ArrayList<LinkedHashMap<String,JarEntry>> levelMapList = new ArrayList<LinkedHashMap<String,JarEntry>>();
      entries = new ArrayList<VirtualFileHandler>();
      entryMap = new HashMap<String, VirtualFileHandler>();
      boolean trace = log.isTraceEnabled();
      while (enumeration.hasMoreElements())
      {
         JarEntry entry = enumeration.nextElement();
         String[] paths = entry.getName().split("/");
         int depth = paths.length;
         if( depth >= levelMapList.size() )
         {
            for(int n = levelMapList.size(); n <= depth; n ++)
               levelMapList.add(new LinkedHashMap<String,JarEntry>());
         }
         LinkedHashMap<String,JarEntry> levelMap = levelMapList.get(depth);
         levelMap.put(paths[depth-1], entry);
         if( trace )
            log.trace("added "+entry.getName()+" at depth "+depth);
      }
      // Process each level to build the handlers in parent first order
      for(LinkedHashMap<String,JarEntry> levelMap : levelMapList)
      {
         for(JarEntry entry : levelMap.values())
         {
            String name = entry.getName();
            int slash = entry.isDirectory() ? name.lastIndexOf('/', name.length()-2) :
               name.lastIndexOf('/', name.length()-1);
            VirtualFileHandler parent = this;
            String entryName = name;
            if( slash >= 0 )
            {
               // Need to include the slash in the name to match the JarEntry.name
               String parentName = name.substring(0, slash+1);
               parent = parentMap.get(parentName);
            }
            // Get the entry name without any directory '/' ending
            int start = slash+1;
            int end = entry.isDirectory() ? name.length()-1 : name.length();
            entryName = name.substring(start, end);
            VirtualFileHandler handler = this.createVirtualFileHandler(parent, entry, entryName);
            if( entry.isDirectory() )
               parentMap.put(name, handler);
            if( parent == this )
            {
               // This is an immeadiate child of the jar handler
               entries.add(handler);
               entryMap.put(entryName, handler);
            }
            else if( parent instanceof JarEntryHandler )
            {
               // This is a child of the jar entry handler
               JarEntryHandler ehandler = (JarEntryHandler) parent;
               ehandler.addChild(handler);
            }
         }
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

   public boolean isLeaf()
   {
      checkClosed();
      return false;
   }

   public List<VirtualFileHandler> getChildren(boolean ignoreErrors) throws IOException
   {
      checkClosed();
      return entries;
   }

   public VirtualFileHandler findChild(String path) throws IOException
   {
      return super.structuredFindChild(path);
   }

   public VirtualFileHandler createChildHandler(String name) throws IOException
   {
      VirtualFileHandler child = entryMap.get(name);
      if( child == null )
         throw new FileNotFoundException(this+" has no child: "+name);
      return child;
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
   protected VirtualFileHandler createVirtualFileHandler(VirtualFileHandler parent, JarEntry entry,
         String entryName)
      throws IOException
   {
      if (parent == null)
         throw new IllegalArgumentException("Null parent");
      if (entry == null)
         throw new IllegalArgumentException("Null entry");

      // Question: Why doesn't this work properly?
      // URL url = new URL(parent.toURL(), entry.getName());
      StringBuilder buffer = new StringBuilder();
      try
      {
         buffer.append(parent.toURI());
      }
      catch(URISyntaxException e)
      {
         // Should not happen
         throw new MalformedURLException(e.getMessage());
      }
      if (buffer.charAt(buffer.length()-1) != '/')
         buffer.append('/');
      buffer.append(entryName);
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
         vfh = new JarEntryHandler(context, parent, jar, entry, entryName, url);
      }

      return vfh;
   }

   /**
    * Restore the jar file from the jar URL
    * 
    * @param in
    * @throws IOException
    * @throws ClassNotFoundException
    */
   private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException
   {
      in.defaultReadObject();
      // Initialize the transient values
      URL jarURL = super.getURL();
      URLConnection conn = jarURL.openConnection();
      if( conn instanceof JarURLConnection )
      {
         JarURLConnection jconn = (JarURLConnection) conn;
         JarFile jarFile = jconn.getJarFile();
         initJarFile(jarFile);
      }
      else
      {
         throw new IOException("Cannot restore from non-JarURLConnection, url: "+jarURL);
      }
   }

}
