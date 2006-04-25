package org.jboss.vfs.file;

import org.jboss.vfs.spi.VirtualFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Set;
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

   public static boolean isJar(String name)
   {
      int lastDot = name.lastIndexOf('.');
      String suffix = name;
      if( lastDot >= 0 )
         suffix = name.substring(lastDot);
      return jarSuffixes.contains(suffix);
   }


   public JarImpl(String path)
      throws IOException
   {
      file = new File(path);
      jar = new JarFile(file);
   }

   public String getName()
   {
      return file.getName();
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
            tmp.add(new NestedJarFromStream(jis, jarURL, entry));
         }
         else
            tmp.add(new JarFileEntry(jarURL, entry, jar));
      }
      VirtualFile[] children = new VirtualFile[tmp.size()];
      tmp.toArray(children);
      return children;
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
            child = new NestedJarFromStream(jis, jarURL, entry);
         }
         else
            child = new JarFileEntry(jarURL, entry, jar);
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
