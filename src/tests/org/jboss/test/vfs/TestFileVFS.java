/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.test.vfs;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipInputStream;

import org.jboss.test.BaseTestCase;
import org.jboss.vfs.VFSFactory;
import org.jboss.vfs.VFSFactoryLocator;
import org.jboss.vfs.file.JarImpl;
import org.jboss.vfs.file.NestedJarFromStream;
import org.jboss.vfs.spi.ReadOnlyVFS;
import org.jboss.vfs.spi.VirtualFile;

/**
 * Tests of the VFS implementation
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class TestFileVFS extends BaseTestCase
{
   public TestFileVFS(String name)
   {
      super(name);
   }

   /**
    * Test that a VFSFactory can be created from the testcase CodeSource url
    * @throws Exception
    */
   public void testVFSFactory()
      throws Exception
   {
      URL root = getClass().getProtectionDomain().getCodeSource().getLocation();
      VFSFactory factory = VFSFactoryLocator.getFactory(root);
      assertTrue("VFSFactory(CodeSource.Location) != null", factory != null);
   }

   /**
    * Test that NestedJarFromStream can provide access to nested jar content
    * @throws Exception
    */
   public void testInnerJarFile()
      throws Exception
   {
      MemoryMXBean mem = ManagementFactory.getMemoryMXBean();
      log.info("Starting heap usage: "+mem.getHeapMemoryUsage());

      // this expects to be run with a working dir of the container root
      File outerJar = new File("output/lib/outer.jar");
      assertTrue(outerJar.getAbsolutePath()+" exists", outerJar.exists());
      JarFile jf = new JarFile(outerJar);

      JarEntry jar1 = jf.getJarEntry("jar1.jar");
      URL jar1URL = new URL(outerJar.toURL(), "jar1.jar");
      ZipInputStream jis1 = new ZipInputStream(jf.getInputStream(jar1));
      NestedJarFromStream njfs = new NestedJarFromStream(jis1, jar1URL, jar1);
      NestedJarFromStream.JarEntryContents e1 = njfs.getEntry("org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      assertNotNull(e1);
      log.info("org/jboss/test/vfs/support/CommonClass.class: "+e1);
      NestedJarFromStream.JarEntryContents mfe1 = njfs.getEntry("META-INF/MANIFEST.MF");
      assertNotNull("jar1!/META-INF/MANIFEST.MF", mfe1);
      InputStream mfIS = mfe1.openStream();
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar1", version);
      mfIS.close();
      njfs.close();

      JarEntry jar2 = jf.getJarEntry("jar2.jar");
      URL jar2URL = new URL(outerJar.toURL(), "jar2.jar");
      ZipInputStream jis2 = new ZipInputStream(jf.getInputStream(jar2));
      NestedJarFromStream njfs2 = new NestedJarFromStream(jis2, jar2URL, jar2);
      NestedJarFromStream.JarEntryContents e2 = njfs2.getEntry("org/jboss/test/vfs/support/jar2/ClassInJar2.class");
      assertNotNull(e2);
      log.info("org/jboss/test/vfs/support/CommonClass.class: "+e2);
      NestedJarFromStream.JarEntryContents mfe2 = njfs2.getEntry("META-INF/MANIFEST.MF");
      assertNotNull("jar2!/META-INF/MANIFEST.MF", mfe2);
      InputStream mf2IS = mfe2.openStream();
      Manifest mf2 = new Manifest(mf2IS);
      Attributes mainAttrs2 = mf2.getMainAttributes();
      String version2 = mainAttrs2.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar2", version2);
      mf2IS.close();
      njfs2.close();
      log.info("Ending heap usage: "+mem.getHeapMemoryUsage());
   }

   /**
    * Basic tests of accessing resources in a jar
    * @throws Exception
    */
   public void testFindResource()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJar = new File("output/lib/outer.jar");
      URL rootURL = outerJar.getParentFile().toURL();
      VFSFactory factory = VFSFactoryLocator.getFactory(rootURL);
      ReadOnlyVFS vfs = factory.getVFS(rootURL);
      VirtualFile jar = vfs.resolveFile("outer.jar");
      assertTrue("outer.jar != null", jar != null);

      ArrayList<String> searchCtx = new ArrayList<String>();
      searchCtx.add("outer.jar");
      VirtualFile metaInf = vfs.resolveFile("META-INF/MANIFEST.MF", searchCtx);
      assertTrue("META-INF/MANIFEST.MF != null", metaInf != null);
      InputStream mfIS = metaInf.openStream();
      assertTrue("META-INF/MANIFEST.MF.openStream != null", mfIS != null);
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_VERSION);
      assertEquals("1.0.0.GA", version);
      mfIS.close();
   }

   /**
    * Test simple file resolution without search contexts
    * @throws Exception
    */
   public void testResolveFile()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJarFile = new File("output/lib/outer.jar");
      URL rootURL = outerJarFile.getParentFile().toURL();
      VFSFactory factory = VFSFactoryLocator.getFactory(rootURL);
      ReadOnlyVFS vfs = factory.getVFS(rootURL);

      // Find the outer.jar
      VirtualFile outerJar = vfs.resolveFile("outer.jar");
      assertNotNull("outer.jar", outerJar);
      VirtualFile outerJarMF = vfs.resolveFile("outer.jar/META-INF/MANIFEST.MF");
      assertNotNull("outer.jar/META-INF/MANIFEST.MF", outerJarMF);
      
   }

   /**
    * Test vile resolution with nested jars
    * @throws Exception
    */
   public void testInnerJar()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJar = new File("output/lib/outer.jar");
      URL rootURL = outerJar.getParentFile().toURL();
      VFSFactory factory = VFSFactoryLocator.getFactory(rootURL);
      ReadOnlyVFS vfs = factory.getVFS(rootURL);
      VirtualFile inner = vfs.resolveFile("outer.jar/jar1.jar");
      log.info("IsFile: "+inner.isFile());
      log.info(inner.getLastModified());
      VirtualFile[] contents = inner.getChildren();
      // META-INF/*, org/jboss/test/vfs/support/jar1/* at least
      assertTrue("jar1.jar children.length("+contents.length+") >= 2", contents.length >= 2);
      for(VirtualFile vf : contents)
      {
         log.info("  "+vf.getName());
      }
      ArrayList<String> searchCtxts = new ArrayList<String>();
      searchCtxts.add("outer.jar/jar1.jar");
      VirtualFile jar1MF = vfs.resolveFile("META-INF/MANIFEST.MF", searchCtxts);
      InputStream mfIS = jar1MF.openStream();
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar1", version);
      mfIS.close();
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
      for(File jar : jars)
      {
         if( jar.isFile() == false )
            continue;
         JarImpl jf = new JarImpl(jar.getAbsolutePath());
         VirtualFile[] children = jf.getChildren();
         for(VirtualFile vf : children)
         {
            size += vf.getSize();
         }
      }
      jars = serverLib.listFiles();
      for(File jar : jars)
      {
         if( jar.isFile() == false )
            continue;
         JarImpl jf = new JarImpl(jar.getAbsolutePath());
         VirtualFile[] children = jf.getChildren();
         for(VirtualFile vf : children)
         {
            size += vf.getSize();
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
      log.info("Size: "+ear.getSize());
      VirtualFile[] files = ear.getChildren();
      HashMap<String, VirtualFile> classes = new HashMap<String, VirtualFile>();
      for(VirtualFile vf : files)
      {
         log.info(vf);
         if( vf.isDirectory() )
            findClasses(vf, classes);
      }

      assertTrue("classes count > 0 ", classes.size() > 0);
   }


   private void findClasses(VirtualFile jar,
                            HashMap<String, VirtualFile> classes) throws IOException
   {
      VirtualFile[] contents = jar.getChildren();
      for(VirtualFile vf : contents)
      {
         if( vf.isDirectory() )
            findClasses(vf, classes);
         if( vf.getName().endsWith(".class") )
            classes.put(vf.getName(), vf);
      }
   }

}
