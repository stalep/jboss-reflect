/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.test.vfs;

import java.net.URL;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;
import java.util.Iterator;
import java.util.HashMap;
import java.util.ArrayList;

import junit.framework.TestCase;
import org.jboss.vfs.VFSFactoryLocator;
import org.jboss.vfs.VFSFactory;
import org.jboss.vfs.spi.ReadOnlyVFS;
import org.jboss.vfs.spi.VirtualFile;
import org.jboss.vfs.file.JarImpl;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class TestFileVFS extends TestCase
{
   public void testVFSFactory()
      throws Exception
   {
      URL root = new URL("file://usr/local/java5");
      VFSFactory factory = VFSFactoryLocator.getFactory(root);
      assertTrue("VFSFactory(file://usr/local/java5) != null", factory != null);
   }
   public void testInnerJarFile()
      throws Exception
   {
      MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
      System.out.println("Starting heap usage: "+mem.getHeapMemoryUsage());
      JarFile jf = new JarFile("/usr/local/java5/outer.jar");
      ZipEntry entry = jf.getEntry("resolver.jar");
      JarInputStream jis1 = new JarInputStream(jf.getInputStream(entry));
      NestedJarFromStream njfs = new NestedJarFromStream(jis1);
      NestedJarFromStream.JarEntryContents e1 = njfs.getEntry("org/apache/xml/resolver/Resolver.class");
      System.out.println("org/apache/xml/resolver/Resolver.class: "+e1);
      System.out.println("Ending heap usage: "+mem.getHeapMemoryUsage());
   }
   public void test403DefaultJarsSize()
      throws Exception
   {
      MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
      System.out.println("Starting heap usage: "+mem.getHeapMemoryUsage());
      File jboss403 = new File("/cvs/Releases/jboss-4.0.3");
      File lib = new File(jboss403, "lib");
      File defaultLib = new File(jboss403, "server/default/lib");

      long size = 0;
      File[] jars = lib.listFiles();
      for(int n = 0; n < jars.length; n ++)
      {
         if( jars[n].isFile() == false )
            continue;
         FileInputStream fis = new FileInputStream(jars[n]);
         JarInputStream jis = new JarInputStream(fis);
         NestedJarFromStream njfs = new NestedJarFromStream(jis);
         Iterator<NestedJarFromStream.JarEntryContents> iter = njfs.getEntries();
         while( iter.hasNext() )
         {
            NestedJarFromStream.JarEntryContents jec = iter.next();
            size += jec.getEntry().getSize();
         }
      }
      jars = defaultLib.listFiles();
      for(int n = 0; n < jars.length; n ++)
      {
         if( jars[n].isFile() == false )
            continue;
         FileInputStream fis = new FileInputStream(jars[n]);
         JarInputStream jis = new JarInputStream(fis);
         NestedJarFromStream njfs = new NestedJarFromStream(jis);
         Iterator<NestedJarFromStream.JarEntryContents> iter = njfs.getEntries();
         while( iter.hasNext() )
         {
            NestedJarFromStream.JarEntryContents jec = iter.next();
            size += jec.getEntry().getSize();
         }
      }
      System.out.println("Jar contents size: "+(size/(1024*1024))+"Mb");
      System.out.println("Ending heap usage: "+mem.getHeapMemoryUsage());
   }
   public void test403AllJarsSize()
      throws Exception
   {
      FileWriter out = new FileWriter("/usr/local/java5/test403AllJarsSize.txt");
      MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
      out.write("Starting heap usage: "+mem.getHeapMemoryUsage()+"\n");
      File jboss403 = new File("/cvs/Releases/jboss-4.0.3");
      File lib = new File(jboss403, "lib");
      File serverLib = new File(jboss403, "server/all/lib");

      long size = 0;
      File[] jars = lib.listFiles();
      for(int n = 0; n < jars.length; n ++)
      {
         if( jars[n].isFile() == false )
            continue;
         FileInputStream fis = new FileInputStream(jars[n]);
         JarInputStream jis = new JarInputStream(fis);
         NestedJarFromStream njfs = new NestedJarFromStream(jis);
         Iterator<NestedJarFromStream.JarEntryContents> iter = njfs.getEntries();
         while( iter.hasNext() )
         {
            NestedJarFromStream.JarEntryContents jec = iter.next();
            size += jec.getEntry().getSize();
         }
      }
      jars = serverLib.listFiles();
      for(int n = 0; n < jars.length; n ++)
      {
         if( jars[n].isFile() == false )
            continue;
         FileInputStream fis = new FileInputStream(jars[n]);
         JarInputStream jis = new JarInputStream(fis);
         NestedJarFromStream njfs = new NestedJarFromStream(jis);
         Iterator<NestedJarFromStream.JarEntryContents> iter = njfs.getEntries();
         while( iter.hasNext() )
         {
            NestedJarFromStream.JarEntryContents jec = iter.next();
            size += jec.getEntry().getSize();
         }
      }
      out.write("Jar contents size: "+(size/(1024*1024))+"Mb\n");
      out.write("Ending heap usage: "+mem.getHeapMemoryUsage()+"\n");
      out.close();
   }

   public void testEarNavigation()
      throws Exception
   {
      VirtualFile ear = new JarImpl("/usr/local/java5/jbosstest-web.ear");
      System.out.println("Size: "+ear.getSize());
      VirtualFile[] files = ear.getChildren();
      HashMap<String, VirtualFile> classes = new HashMap<String, VirtualFile>();
      for(int n = 0; n < files.length; n ++)
      {
         VirtualFile vf = files[n];
         System.out.println(vf);
         if( vf.isDirectory() )
            findClasses(vf, classes);
      }

      assertTrue("classes count > 0 ", classes.size() > 0);
   }

   public void testInnerJar()
      throws Exception
   {
      File root = new File("/usr/local/java5");
      URL rootURL = root.toURL();
      VFSFactory factory = VFSFactoryLocator.getFactory(rootURL);
      ReadOnlyVFS vfs = factory.getVFS(rootURL);
      VirtualFile inner = vfs.resolveFile("outer.jar/resolver.jar");
      System.out.println("IsFile: "+inner.isFile());
      System.out.println(inner.getLastModified());
      VirtualFile[] contents = inner.getChildren();
      for(int n = 0; n < contents.length; n ++)
      {
         VirtualFile vf = contents[n];
         System.out.println("  "+vf.getName());
      }
   }

   public void testFindResource()
      throws Exception
   {
      File root = new File("/usr/local/java5");
      URL rootURL = root.toURL();
      VFSFactory factory = VFSFactoryLocator.getFactory(rootURL);
      ReadOnlyVFS vfs = factory.getVFS(rootURL);
      VirtualFile jar = vfs.resolveFile("resources.jar");
      assertTrue("resources.jar != null", jar != null);

      ArrayList<String> searchCtx = new ArrayList<String>();
      searchCtx.add("resources.jar");
      VirtualFile metaInf = vfs.resolveFile("META-INF/MANIFEST.MF", searchCtx);
      assertTrue("META-INF/MANIFEST.MF != null", metaInf != null);
      InputStream mfIS = metaInf.openStream();
      assertTrue("META-INF/MANIFEST.MF.openStream != null", mfIS != null);
      Manifest mf = new Manifest(mfIS);
      mfIS.close();
      mfIS.close();
   }

   private void findClasses(VirtualFile jar, 
      HashMap<String, VirtualFile> classes) throws IOException
   {
      VirtualFile[] contents = jar.getChildren();
      for(int n = 0; n < contents.length; n ++)
      {
         VirtualFile vf = contents[n];
         if( vf.isDirectory() )
            findClasses(vf, classes);
         if( vf.getName().endsWith(".class") )
            classes.put(vf.getName(), vf);
      }
   }
}
