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
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jboss.virtual.plugins.context.AbstractURLHandler;
import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * JarEntryHandler.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JarEntryHandler extends AbstractURLHandler
{
   /** serialVersionUID */
   private static final long serialVersionUID = 1L;

   /** The jar file */
   private final JarFile jar;
   
   /** The jar entry */
   private final JarEntry entry;
   
   /**
    * Create a new JarHandler.
    * 
    * @param context the context
    * @param parent the parent
    * @param jar the jar file
    * @param entry the entry
    * @param url the url
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null context, url, jar or entry
    */
   public JarEntryHandler(VFSContext context, VirtualFileHandler parent, JarFile jar, JarEntry entry, URL url) throws IOException
   {
      super(context, parent, url, AbstractJarHandler.getEntryName(entry));
      if (jar == null)
         throw new IllegalArgumentException("Null jar");
      
      this.jar = jar;
      this.entry = entry;
   }
   
   /**
    * Get the entry
    * 
    * @return the file
    */
   protected JarEntry getEntry()
   {
      checkClosed();
      return entry;
   }
   
   @Override
   public long getLastModified()
   {
      return getEntry().getTime();
   }

   @Override
   public long getSize()
   {
      return getEntry().getSize();
   }

   public boolean isArchive()
   {
      checkClosed();
      return false;
   }

   public boolean isDirectory()
   {
      return getEntry().isDirectory();
   }

   public boolean isFile()
   {
      return isDirectory() == false;
   }

   public boolean isHidden()
   {
      checkClosed();
      return false;
   }

   @Override
   public InputStream openStream() throws IOException
   {
      return jar.getInputStream(getEntry());
   }

   public List<VirtualFileHandler> getChildren(boolean ignoreErrors) throws IOException
   {
      checkClosed();
      return Collections.emptyList();
   }

   public VirtualFileHandler findChild(String path) throws IOException
   {
      checkClosed();
      throw new IOException("A JarEntry has no children: " + path + " for " + this);
   }
}
