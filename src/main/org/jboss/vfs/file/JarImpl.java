package org.jboss.vfs.file;

import org.jboss.vfs.spi.VirtualFile;
import org.jboss.vfs.spi.VirtualFileFilter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.zip.ZipInputStream;

/**
 * A top level jar implementation of VirtualFile.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class JarImpl
   implements VirtualFile
{
   private static Set<String> jarSuffixes = new CopyOnWriteArraySet<String>();
   static
   {
      jarSuffixes.add(".ear");
      jarSuffixes.add(".jar");
      jarSuffixes.add(".rar");
      jarSuffixes.add(".war");
   }
   private JarFile jar;
   private File file;
   private String vfsPath;

   public static boolean addJarSuffix(String suffix)
   {
      boolean added = jarSuffixes.add(suffix);
      return added;
   }
   public static boolean removeJarSuffix(String suffix)
   {
      boolean removed = jarSuffixes.remove(suffix);
      return removed;
   }

   /**
    * Utility method for validating a jar file.
    * @todo need to deal with unpacked jars
    * @param name a path name
    * @return true if name ends in one of the registered jar suffixes,
    *    false otherwise.
    */
   public static boolean isJar(String name)
   {
      int lastDot = name.lastIndexOf('.');
      String suffix = name;
      if( lastDot >= 0 )
         suffix = name.substring(lastDot);
      return jarSuffixes.contains(suffix);
   }


   public JarImpl(String path, String vfsPath)
      throws IOException
   {
      file = new File(path);
      this.vfsPath = vfsPath;
      try
      {
         jar = new JarFile(file);
      }
      catch(IOException e)
      {
         IOException ioe = new IOException("Error on jar: "+path);
         ioe.initCause(e);
         throw ioe;
      }
   }

   public String getName()
   {
      return file.getName();
   }
   public String getPathName()
   {
      return vfsPath;
   }

   public boolean isArchive()
   {
      return true;
   }

   public VirtualFile[] getChildren() throws IOException
   {
      Enumeration<JarEntry> entries = jar.entries();
      ArrayList<VirtualFile> tmp = new ArrayList<VirtualFile>();
      URL jarURL = toURL();
      while( entries.hasMoreElements() )
      {
         JarEntry entry = entries.nextElement();
         if( isJar(entry.getName()) )
         {
            InputStream is = jar.getInputStream(entry);
            ZipInputStream jis;
            if( (is instanceof ZipInputStream) )
            {
               jis = (ZipInputStream) is;
            }
            else
            {
               jis = new ZipInputStream(is);
            }
            tmp.add(new NestedJarFromStream(jis, jarURL, vfsPath, entry));
         }
         else
            tmp.add(new JarFileEntry(jarURL, vfsPath, entry, jar));
      }
      VirtualFile[] children = new VirtualFile[tmp.size()];
      tmp.toArray(children);
      return children;
   }

   public List<VirtualFile> getChildrenRecursively() throws IOException
   {
      List<VirtualFile> rtn = new ArrayList<VirtualFile>();
      for (VirtualFile vf : getChildren())
      {
         rtn.add(vf);
      }
      return rtn;
   }

   public List<VirtualFile> getChildrenRecursively(VirtualFileFilter filter) throws IOException
   {
      List<VirtualFile> filtered = new ArrayList<VirtualFile>();
      for (VirtualFile vf : getChildrenRecursively())
      {
         if (filter.accepts(vf)) filtered.add(vf);
      }
      return filtered;
   }

   public VirtualFile findChild(String name) throws IOException
   {
      VirtualFile child = null;
      JarEntry entry = jar.getJarEntry(name);
      if( entry != null )
      {
         URL jarURL = toURL();
         if( isJar(entry.getName()) )
         {
            InputStream is = jar.getInputStream(entry);
            ZipInputStream jis;
            if( (is instanceof ZipInputStream) )
            {
               jis = (ZipInputStream) is;
            }
            else
            {
               jis = new ZipInputStream(is);
            }
            child = new NestedJarFromStream(jis, jarURL, vfsPath, entry);
         }
         else
            child = new JarFileEntry(jarURL, vfsPath, entry, jar);
      }
      return child;
   }

   // Convience attribute accessors
   public long getLastModified()
   {
      return file.lastModified();
   }

   public long getSize()
   {
      return file.length();
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
      return null;
   }

   public void close() throws IOException
   {
      jar.close();
   }

   public URL toURL() throws MalformedURLException
   {
      return file.toURL();
   }
}
