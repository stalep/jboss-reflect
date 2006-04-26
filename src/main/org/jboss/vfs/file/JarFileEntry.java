/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.vfs.file;

import org.jboss.vfs.spi.VirtualFile;

import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.net.URL;
import java.net.MalformedURLException;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class JarFileEntry
   implements VirtualFile
{
   private JarEntry entry;
   private JarFile jar;
   private URL jarURL;
   private String vfsPath;
   private InputStream entryIS;

   JarFileEntry(URL jarURL, String jarVfsPath, JarEntry entry, JarFile jar)
   {
      this.jarURL = jarURL;
      this.entry = entry;
      this.jar = jar;
      if( jarVfsPath.length() == 0 )
         this.vfsPath = entry.getName();
      else
         this.vfsPath = jarVfsPath + "/" + entry.getName();
   }

   public String getName()
   {
      return entry.getName();
   }
   public String getPathName()
   {
      return vfsPath;
   }

   public VirtualFile[] getChildren()
   {
      return new VirtualFile[0];
   }

   public VirtualFile findChild(String name)
      throws IOException
   {
      throw new FileNotFoundException("JarFileEntry("+entry.getName()+") has no children");
   }

   // Convience attribute accessors
   public long getLastModified()
   {
      return entry.getTime();
   }

   public long getSize()
   {
      return entry.getSize();
   }

   public boolean isDirectory()
   {
      return false;
   }

   public boolean isFile()
   {
      return true;
   }

   // Stream accessor
   public InputStream openStream() throws IOException
   {
      entryIS = jar.getInputStream(entry);
      return entryIS;
   }

   public void close() throws IOException
   {
      if( entryIS != null )
         entryIS.close();
   }

   public URL toURL() throws MalformedURLException
   {
      URL entryURL = new URL(jarURL, entry.getName());
      return entryURL;
   }

   public String toString()
   {
      StringBuffer tmp = new StringBuffer(super.toString());
      tmp.append('[');
      tmp.append("name=");
      tmp.append(entry.getName());
      tmp.append(",size=");
      tmp.append(entry.getSize());
      tmp.append(",time=");
      tmp.append(entry.getTime());
      tmp.append(",URL=");
      try
      {
         tmp.append(toURL());
      }
      catch(MalformedURLException e)
      {
      }
      tmp.append(']');
      return tmp.toString();
   }
}
