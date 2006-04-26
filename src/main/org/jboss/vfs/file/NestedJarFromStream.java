/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.vfs.file;

import org.jboss.vfs.spi.VirtualFile;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * A nested jar implementation used to represent a jar within a jar.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class NestedJarFromStream
   implements VirtualFile
{
   private ZipInputStream zis;
   private HashMap<String, JarEntryContents> entries = new HashMap<String, JarEntryContents>();
   private URL jarURL;
   private URL entryURL;
   private String vfsPath;
   private String name;
   private long lastModified;
   private long size;
   private boolean inited;

   /**
    * Create a nested jar from the parent zip inputstream/zip entry.
    * @param zis - the jar zip input stream
    * @param jarURL - the URL to use as the jar URL
    * @param entry - the parent jar ZipEntry for the nested jar
    */
   public NestedJarFromStream(ZipInputStream zis, URL jarURL,
      String jarVfsPath, ZipEntry entry)
   {
      this.jarURL = jarURL;
      this.name = entry.getName();
      if( jarVfsPath.length() > 0 )
         this.vfsPath = jarVfsPath + "/" + name;
      else
         this.vfsPath = name;
      this.lastModified = entry.getTime();
      this.size = entry.getSize();
      this.zis = zis;
   }

   public int size()
   {
      return entries.size();
   }

   public Iterator<JarEntryContents> getEntries()
      throws IOException
   {
      if( inited == false )
         init();
      return entries.values().iterator();
   }
   public JarEntryContents getEntry(String name)
      throws IOException
   {
      if( inited == false )
         init();
      JarEntryContents jec = entries.get(name);
      return jec;
   }
   public ZipEntry getJarEntry(String name)
      throws IOException
   {
      if( inited == false )
         init();
      JarEntryContents jec = entries.get(name);
      ZipEntry entry = (jec != null ? jec.getEntry() : null);
      return entry;
   }
   public byte[] getContents(String name)
      throws IOException
   {
      if( inited == false )
         init();
      JarEntryContents jec = entries.get(name);
      byte[] contents = (jec != null ? jec.getContents() : null);
      return contents;
   }

   public String getName()
   {
      return name;
   }
   public String getPathName()
   {
      return vfsPath;
   }

   public VirtualFile[] getChildren()
      throws IOException
   {
      if( inited == false )
         init();
      VirtualFile[] children = new VirtualFile[entries.size()];
      entries.values().toArray(children);
      return children;
   }

   public VirtualFile findChild(String name)
      throws IOException
   {
      if( inited == false )
         init();
      VirtualFile child = entries.get(name);
      return child;
   }

   public long getLastModified()
   {
      return lastModified;
   }

   public long getSize()
   {
      return size;
   }

   public boolean isDirectory()
   {
      return true;
   }

   public boolean isFile()
   {
      return false;
   }

   // Stream accessor
   public InputStream openStream() throws IOException
   {
      return zis;
   }

   public void close()
      throws IOException
   {
      entries.clear();
      if( zis != null )
         zis.close();
   }

   public URL toURL() throws MalformedURLException
   {
      if( entryURL == null )
         entryURL = new URL(jarURL, getName());
      return entryURL;
   }

   public String toString()
   {
      StringBuffer tmp = new StringBuffer(super.toString());
      tmp.append('[');
      tmp.append("name=");
      tmp.append(getName());
      tmp.append(",size=");
      tmp.append(getSize());
      tmp.append(",lastModified=");
      tmp.append(getLastModified());
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

   protected void init()
      throws IOException
   {
      inited = true;
      ZipEntry entry = zis.getNextEntry();
      while( entry != null )
      {
         try
         {
            String url = toURL().toExternalForm() + "!/" +  entry.getName();
            URL jecURL = new URL(url);
            JarEntryContents jec = new JarEntryContents(entry, jecURL, zis, getPathName());
            entries.put(entry.getName(), jec);
            entry = zis.getNextEntry();
         }
         catch(MalformedURLException e)
         {
            e.printStackTrace();
         }
      }
      zis.close();
      zis = null;
   }

   public static class JarEntryContents
      implements VirtualFile
   {
      private ZipEntry entry;
      private URL entryURL;
      private String vfsPath;
      private byte[] contents;
      private boolean isJar;
      private NestedJarFromStream njar;
      private InputStream openStream;

      JarEntryContents(ZipEntry entry, URL entryURL, InputStream zis,
         String parentVfsPath)
         throws IOException
      {
         this.entry = entry;
         this.entryURL = entryURL;
         this.vfsPath = parentVfsPath + "/" + entry.getName();
         this.isJar = JarImpl.isJar(entry.getName());
         int size = (int) entry.getSize();
         if( size <= 0 )
            return;

         ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
         byte[] tmp = new byte[1024];
         while( zis.available() > 0 )
         {
            int length = zis.read(tmp);
            if( length > 0 )
               baos.write(tmp, 0, length);
         }
         contents = baos.toByteArray();
      }
      public ZipEntry getEntry()
      {
         return entry;
      }
      public byte[] getContents()
      {
         return contents;
      }

      public String getName()
      {
         return entry.getName();
      }
      public String getPathName()
      {
         return vfsPath;
      }

      public VirtualFile[] getChildren() throws IOException
      {
         VirtualFile[] children = {};
         if( isJar )
         {
            initNestedJar();
            children = njar.getChildren();
         }
         return children;
      }
      public VirtualFile findChild(String name)
         throws IOException
      {
         VirtualFile child = null;
         if( isJar )
         {
            initNestedJar();
            child = njar.findChild(name);
         }
         else
         {
            throw new FileNotFoundException("JarEntryContents("+entry.getName()+") has no children");
         }
         return child;
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
         return isJar;
      }

      public boolean isFile()
      {
         return isJar == false;
      }

      // Stream accessor
      public synchronized InputStream openStream()
         throws IOException
      {
         initNestedJar();
         if( njar != null )
            openStream = njar.openStream();
         else
            openStream = new ByteArrayInputStream(contents);
         return openStream;
      }

      public synchronized void close()
         throws IOException
      {
         if( openStream != null )
         {
            openStream.close();
            openStream = null;
         }
      }

      public URL toURL() throws MalformedURLException
      {
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

      private synchronized void initNestedJar()
         throws IOException
      {
         if( isJar && njar == null )
         {
            ByteArrayInputStream bais = new ByteArrayInputStream(contents);
            ZipInputStream zis = new ZipInputStream(bais);
            njar = new NestedJarFromStream(zis, entryURL, getPathName(), entry);
         }
      }
   }
}
