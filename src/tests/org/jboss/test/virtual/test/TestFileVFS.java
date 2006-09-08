/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.test.virtual.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipInputStream;

import org.jboss.test.BaseTestCase;
import org.jboss.virtual.VFS;
import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.plugins.context.jar.NestedJarFromStream;
import org.jboss.virtual.plugins.vfs.helpers.SuffixMatchFilter;
import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VFSContextFactory;
import org.jboss.virtual.spi.VFSContextFactoryLocator;

/**
 * Tests of the VFS implementation
 * 
 * @author Scott.Stark@jboss.org
 * @author adrian@jboss.org
 * @version $Revision: 55523 $
 */
public class TestFileVFS extends BaseTestCase
{
   public TestFileVFS(String name)
   {
      super(name);
   }

   /**
    * Test that a VFSContextFactory can be created from the testcase CodeSource url
    * @throws Exception
    */
   public void testVFSContextFactory()
      throws Exception
   {
      URL root = getClass().getProtectionDomain().getCodeSource().getLocation();
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(root);
      assertTrue("VFSContextFactory(CodeSource.Location) != null", factory != null);
   }

   /**
    * Test that NestedJarFromStream can provide access to nested jar content
    * @throws Exception
    */
   public void testInnerJarFile()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJar = new File("output/lib/outer.jar");
      assertTrue(outerJar.getAbsolutePath()+" exists", outerJar.exists());
      JarFile jf = new JarFile(outerJar);

      URL rootURL = outerJar.getParentFile().toURL();
      VFS vfs = VFS.getVFS(rootURL);
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(rootURL);
      VFSContext context = factory.getVFS(rootURL);

      JarEntry jar1 = jf.getJarEntry("jar1.jar");
      URL jar1URL = new URL(outerJar.toURL(), "jar1.jar");
      ZipInputStream jis1 = new ZipInputStream(jf.getInputStream(jar1));
      NestedJarFromStream njfs = new NestedJarFromStream(context, null, jis1, jar1URL, jar1);
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
      NestedJarFromStream njfs2 = new NestedJarFromStream(context, null, jis2, jar2URL, jar2);
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
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile jar = vfs.findChildFromRoot("outer.jar");
      assertTrue("outer.jar != null", jar != null);

      /*
      ArrayList<String> searchCtx = new ArrayList<String>();
      searchCtx.add("outer.jar");
      VirtualFile metaInf = vfs.resolveFile("META-INF/MANIFEST.MF", searchCtx);
      */
      VirtualFile metaInf = jar.findChild("META-INF/MANIFEST.MF");
      assertTrue("META-INF/MANIFEST.MF != null", metaInf != null);
      InputStream mfIS = metaInf.openStream();
      assertTrue("META-INF/MANIFEST.MF.openStream != null", mfIS != null);
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_VERSION);
      assertEquals("1.0.0.GA", version);
      mfIS.close();
   }

   public void testFindResourceUnpackedJar()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJar = new File("output/lib/unpacked-outer.jar");
      URL rootURL = outerJar.getParentFile().toURL();
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile jar = vfs.findChildFromRoot("unpacked-outer.jar");
      assertTrue("unpacked-outer.jar != null", jar != null);

      /**
      ArrayList<String> searchCtx = new ArrayList<String>();
      searchCtx.add("unpacked-outer.jar");
      VirtualFile metaInf = vfs.resolveFile("META-INF/MANIFEST.MF", searchCtx);
      */
      VirtualFile metaInf = jar.findChild("META-INF/MANIFEST.MF");
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
      log.info("+++ testResolveFile, cwd="+(new File(".").getCanonicalPath()));
      // this expects to be run with a working dir of the container root
      File outerJarFile = new File("output/lib/outer.jar");
      URL rootURL = outerJarFile.getParentFile().toURL();
      VFS vfs = VFS.getVFS(rootURL);

      // Check resolving the root file
      VirtualFile root = vfs.findChildFromRoot("");
      assertEquals("root name", "lib", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertTrue("root isDirectory", root.isDirectory());

      // Find the outer.jar
      VirtualFile outerJar = vfs.findChildFromRoot("outer.jar");
      assertNotNull("outer.jar", outerJar);
      assertEquals("outer.jar name", "outer.jar", outerJar.getName());
      assertEquals("outer.jar path", "outer.jar", outerJar.getPathName());
      
      VirtualFile outerJarMF = vfs.findChildFromRoot("outer.jar/META-INF/MANIFEST.MF");
      assertNotNull("outer.jar/META-INF/MANIFEST.MF", outerJarMF);

      // Test a non-canonical path
      outerJarFile = new File("output/resources/../lib/outer.jar");
      rootURL = outerJarFile.getParentFile().toURL();
      // Check resolving the root file
      root = vfs.findChildFromRoot("");
      assertEquals("root name", "lib", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertTrue("root isDirectory", root.isDirectory());
   }

   /**
    * Validate resolving a .class file given a set of search contexts in the
    * vfs that make up a classpath.
    * 
    * @throws Exception
    */
   public void testResolveClassFileInClassPath()
      throws Exception
   {
      log.info("+++ testResolveFile, cwd="+(new File(".").getCanonicalPath()));
      // this expects to be run with a working dir of the container root
      File libFile = new File("output/lib");
      URL rootURL = libFile.toURL();
      VFS vfs = VFS.getVFS(rootURL);
      
      // Find ClassInJar1.class
      VirtualFile vf = vfs.findChildFromRoot("jar1.jar"); 
      VirtualFile c1 = vf.findChild("org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      assertNotNull("ClassInJar1.class VF", c1);
      log.debug("Found ClassInJar1.class: "+c1);

      // Find ClassInJar1$InnerClass.class
      VirtualFile c1i = vf.findChild("org/jboss/test/vfs/support/jar1/ClassInJar1$InnerClass.class");
      assertNotNull("ClassInJar1$InnerClass.class VF", c1i);
      log.debug("Found ClassInJar1$InnerClass.class: "+c1i);

      // Find ClassInJar2.class
      vf = vfs.findChildFromRoot("jar2.jar");
      VirtualFile c2 = vf.findChild("org/jboss/test/vfs/support/jar2/ClassInJar2.class");
      assertNotNull("ClassInJar2.class VF", c2);
      log.debug("Found ClassInJar2.class: "+c2);
   }

   public void testResolveFileInUnpackedJar()
      throws Exception
   {
      log.info("+++ testResolveFileInUnpackedJar, cwd="+(new File(".").getCanonicalPath()));
      // this expects to be run with a working dir of the container root
      File outerJarFile = new File("output/lib/unpacked-outer.jar");
      URL rootURL = outerJarFile.getParentFile().toURL();
      VFS vfs = VFS.getVFS(rootURL);

      // Check resolving the root file
      VirtualFile root = vfs.findChildFromRoot("");
      assertEquals("root name", "lib", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertTrue("root isDirectory", root.isDirectory());

      // Find the outer.jar
      VirtualFile outerJar = vfs.findChildFromRoot("unpacked-outer.jar");
      assertNotNull("unpacked-outer.jar", outerJar);
      assertEquals("unpacked-outer.jar name", "unpacked-outer.jar", outerJar.getName());
      assertEquals("unpacked-outer.jar path", "unpacked-outer.jar", outerJar.getPathName());
      
      VirtualFile outerJarMF = vfs.findChildFromRoot("unpacked-outer.jar/META-INF/MANIFEST.MF");
      assertNotNull("unpacked-outer.jar/META-INF/MANIFEST.MF", outerJarMF);

      // Test a non-canonical path
      outerJarFile = new File("output/resources/../lib/unpacked-outer.jar");
      rootURL = outerJarFile.getParentFile().toURL();
      // Check resolving the root file
      root = vfs.findChildFromRoot("");
      assertEquals("root name", "lib", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertTrue("root isDirectory", root.isDirectory());
   }

   /**
    * Test file resolution with nested jars
    * @throws Exception
    */
   public void testInnerJar()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJar = new File("output/lib/outer.jar");
      URL rootURL = outerJar.getParentFile().toURL();
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile inner = vfs.findChildFromRoot("outer.jar/jar1.jar");
      log.info("IsFile: "+inner.isFile());
      log.info(inner.getLastModified());
      List<VirtualFile> contents = inner.getChildren();
      // META-INF/*, org/jboss/test/vfs/support/jar1/* at least
      assertTrue("jar1.jar children.length("+contents.size()+") >= 2", contents.size() >= 2);
      for(VirtualFile vf : contents)
      {
         log.info("  "+vf.getName());
      }
      VirtualFile vf = vfs.findChildFromRoot("outer.jar/jar1.jar");
      VirtualFile jar1MF = vf.findChild("META-INF/MANIFEST.MF");
      InputStream mfIS = jar1MF.openStream();
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar1", version);
      mfIS.close();
   }

   /**
    * Test a scan of the outer.jar vfs to locate all .class files
    * @throws Exception
    */
   public void testClassScan()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File outerJarFile = new File("output/lib/outer.jar");
      URL rootURL = outerJarFile.toURL();
      VFS vfs = VFS.getVFS(rootURL);

      HashSet<String> expectedClasses = new HashSet<String>();
      expectedClasses.add("jar1.jar/org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      expectedClasses.add("jar1.jar/org/jboss/test/vfs/support/jar1/ClassInJar1$InnerClass.class");
      expectedClasses.add("jar2.jar/org/jboss/test/vfs/support/jar2/ClassInJar2.class");
      expectedClasses.add("org/jboss/test/vfs/support/CommonClass.class");
      SuffixMatchFilter classVisitor = new SuffixMatchFilter(".class");
      List<VirtualFile> classes = vfs.getChildren(classVisitor);
      int count = 0;
      for (VirtualFile cf : classes)
      {
         String path = cf.getPathName();
         if( path.endsWith(".class") )
         {
            assertTrue(path, expectedClasses.contains(path));
            count ++;
         }
      }
      assertEquals("There were 4 classes", 4, count);
   }

   /**
    * Test the serialization of VirtualFiles
    * @throws Exception
    */
   public void testVFSerialization()
      throws Exception
   {
      File tmpRoot = File.createTempFile("vfs", ".root");
      tmpRoot.delete();
      tmpRoot.mkdir();
      tmpRoot.deleteOnExit();
      File tmp = new File(tmpRoot, "vfs.ser");
      tmp.createNewFile();
      tmp.deleteOnExit();
      log.info("+++ testVFSerialization, tmp="+tmp.getCanonicalPath());
      URL rootURL = tmpRoot.toURL();
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile tmpVF = vfs.findChildFromRoot("vfs.ser");
      FileOutputStream fos = new FileOutputStream(tmp);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(tmpVF);
      oos.close();

      FileInputStream fis = new FileInputStream(tmp);
      ObjectInputStream ois = new ObjectInputStream(fis);
      VirtualFile tmpVF2 = (VirtualFile) ois.readObject();
      ois.close();
      long lastModified = tmpVF.getLastModified();
      long size = tmpVF.getSize();
      String name = tmpVF.getName();
      String pathName = tmpVF.getPathName();
      URL url = tmpVF.toURL();

      assertEquals("name", name, tmpVF2.getName());
      assertEquals("pathName", pathName, tmpVF2.getPathName());
      assertEquals("lastModified", lastModified, tmpVF2.getLastModified());
      assertEquals("size", size, tmpVF2.getSize());
      assertEquals("url", url, tmpVF2.toURL());
   }

   /**
    * Test that the URL of a VFS corresponding to a directory ends in '/' so that
    * URLs created relative to it are under the directory.
    * 
    * @throws Exception
    */
   /*
   public void testDirURLs()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      File libFile = new File("output/lib");
      URL rootURL = libFile.toURL();
      VFSContextFactory factory = VFSContextFactoryLocator.getFactory(rootURL);
      ReadOnlyVFS vfs = factory.getVFS(rootURL);

      // Use the unpacked-outer.jar in output/lib
      VirtualFile outerJar = vfs.resolveFile("unpacked-outer.jar");
      URL outerURL = outerJar.toURL();
      log.debug("outerURL: "+outerURL);
      assertTrue(outerURL+" ends in '/'", outerURL.getPath().endsWith("/"));
      // Validate that jar1 is under unpacked-outer.jar
      URL jar1URL = new URL(outerURL, "jar1.jar");
      log.debug("jar1URL: "+jar1URL+", path="+jar1URL.getPath());
      assertTrue("jar1URL path ends in unpacked-outer.jar/jar1.jar",
            jar1URL.getPath().endsWith("unpacked-outer.jar/jar1.jar"));
      VirtualFile jar1 = outerJar.findChild("jar1.jar");
      assertEquals("jar1URL == VF.toURL()", jar1URL, jar1.toURL());
   }
   */
}
