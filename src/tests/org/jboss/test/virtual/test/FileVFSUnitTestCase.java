/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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
package org.jboss.test.virtual.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URI;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.jar.Attributes;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
import java.util.zip.ZipInputStream;

import junit.framework.Test;
import junit.framework.TestSuite;

import org.jboss.test.BaseTestCase;
import org.jboss.test.virtual.support.MetaDataMatchFilter;
import org.jboss.virtual.VFS;
import org.jboss.virtual.VFSUtils;
import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.VisitorAttributes;
import org.jboss.virtual.plugins.context.jar.JarUtils;
import org.jboss.virtual.plugins.context.jar.NestedJarFromStream;
import org.jboss.virtual.plugins.vfs.helpers.SuffixMatchFilter;
import org.jboss.virtual.spi.LinkInfo;
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
public class FileVFSUnitTestCase extends BaseTestCase
{
   public FileVFSUnitTestCase(String name)
   {
      super(name);
   }
   
   public static Test suite()
   {
      return new TestSuite(FileVFSUnitTestCase.class);
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
   public void testNestedJarFromStream()
      throws Exception
   {
      URL outer = getResource("/vfs/test/outer.jar");
      String path = outer.getPath();
      File outerJar = new File(path);
      assertTrue(outerJar.getAbsolutePath()+" exists", outerJar.exists());
      JarFile jf = new JarFile(outerJar);

      URL rootURL = outerJar.getParentFile().toURL();
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
      String title1 = mainAttrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar1", title1);
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
      String title2 = mainAttrs2.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar2", title2);
      mf2IS.close();
      njfs2.close();
   }

   /**
    * Test reading the contents of nested jar entries.
    * @throws Exception
    */
   public void testInnerJarFile()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile outerjar = vfs.findChild("outer.jar");
      assertTrue("outer.jar != null", outerjar != null);
      VirtualFile jar1 = outerjar.findChild("jar1.jar");
      assertTrue("outer.jar/jar1.jar != null", jar1 != null);
      VirtualFile jar2 = outerjar.findChild("jar2.jar");
      assertTrue("outer.jar/jar2.jar != null", jar2 != null);

      VirtualFile jar1MF = jar1.findChild("META-INF/MANIFEST.MF");
      assertNotNull("jar1!/META-INF/MANIFEST.MF", jar1MF);
      InputStream mfIS = jar1MF.openStream();
      Manifest mf1 = new Manifest(mfIS);
      Attributes mainAttrs1 = mf1.getMainAttributes();
      String title1 = mainAttrs1.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar1", title1);
      jar1MF.close();

      VirtualFile jar2MF = jar2.findChild("META-INF/MANIFEST.MF");
      assertNotNull("jar2!/META-INF/MANIFEST.MF", jar2MF);
      InputStream mfIS2 = jar2MF.openStream();
      Manifest mf2 = new Manifest(mfIS2);
      Attributes mainAttrs2 = mf2.getMainAttributes();
      String title2 = mainAttrs2.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals("jar2", title2);
      jar2MF.close();
   }

   /**
    * Basic tests of accessing resources in a jar
    * @throws Exception
    */
   public void testFindResource()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile jar = vfs.findChild("outer.jar");
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
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile jar = vfs.findChild("unpacked-outer.jar");
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
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);

      // Check resolving the root file
      VirtualFile root = vfs.findChild("");
      assertEquals("root name", "test", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertFalse("root isDirectory", root.isLeaf());

      // Find the outer.jar
      VirtualFile outerJar = vfs.findChild("outer.jar");
      assertNotNull("outer.jar", outerJar);
      assertEquals("outer.jar name", "outer.jar", outerJar.getName());
      assertEquals("outer.jar path", "outer.jar", outerJar.getPathName());
      
      VirtualFile outerJarMF = vfs.findChild("outer.jar/META-INF/MANIFEST.MF");
      assertNotNull("outer.jar/META-INF/MANIFEST.MF", outerJarMF);

      // Test a non-canonical path
      rootURL = getResource("/vfs/sundry/../test");
      // Check resolving the root file
      root = vfs.findChild("");
      assertEquals("root name", "test", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertFalse("root isDirectory", root.isLeaf());
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
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      
      // Find ClassInJar1.class
      VirtualFile vf = vfs.findChild("jar1.jar"); 
      VirtualFile c1 = vf.findChild("org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      assertNotNull("ClassInJar1.class VF", c1);
      log.debug("Found ClassInJar1.class: "+c1);

      // Find ClassInJar1$InnerClass.class
      VirtualFile c1i = vf.findChild("org/jboss/test/vfs/support/jar1/ClassInJar1$InnerClass.class");
      assertNotNull("ClassInJar1$InnerClass.class VF", c1i);
      log.debug("Found ClassInJar1$InnerClass.class: "+c1i);

      // Find ClassInJar2.class
      vf = vfs.findChild("jar2.jar");
      VirtualFile c2 = vf.findChild("org/jboss/test/vfs/support/jar2/ClassInJar2.class");
      assertNotNull("ClassInJar2.class VF", c2);
      log.debug("Found ClassInJar2.class: "+c2);
   }

   public void testResolveFileInUnpackedJar()
      throws Exception
   {
      log.info("+++ testResolveFileInUnpackedJar, cwd="+(new File(".").getCanonicalPath()));
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);

      // Check resolving the root file
      VirtualFile root = vfs.findChild("");
      assertEquals("root name", "test", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertFalse("root isDirectory", root.isLeaf());

      // Find the outer.jar
      VirtualFile outerJar = vfs.findChild("unpacked-outer.jar");
      assertNotNull("unpacked-outer.jar", outerJar);
      assertEquals("unpacked-outer.jar name", "unpacked-outer.jar", outerJar.getName());
      assertEquals("unpacked-outer.jar path", "unpacked-outer.jar", outerJar.getPathName());
      
      VirtualFile outerJarMF = vfs.findChild("unpacked-outer.jar/META-INF/MANIFEST.MF");
      assertNotNull("unpacked-outer.jar/META-INF/MANIFEST.MF", outerJarMF);

      // Test a non-canonical path
      rootURL = getResource("/test/sundry/../test");
      // Check resolving the root file
      root = vfs.findChild("");
      assertEquals("root name", "test", root.getName());
      assertEquals("root path", "", root.getPathName());
      assertFalse("root isDirectory", root.isLeaf());
   }

   /**
    * Test file resolution with nested jars
    * @throws Exception
    */
   public void testInnerJar()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile inner = vfs.findChild("outer.jar/jar1.jar");
      log.info("IsFile: "+inner.isLeaf());
      log.info(inner.getLastModified());
      List<VirtualFile> contents = inner.getChildren();
      // META-INF/*, org/jboss/test/vfs/support/jar1/* at least
      assertTrue("jar1.jar children.length("+contents.size()+") >= 2", contents.size() >= 2);
      for(VirtualFile vf : contents)
      {
         log.info("  "+vf.getName());
      }
      VirtualFile vf = vfs.findChild("outer.jar/jar1.jar");
      VirtualFile jar1MF = vf.findChild("META-INF/MANIFEST.MF");
      InputStream mfIS = jar1MF.openStream();
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals(Attributes.Name.SPECIFICATION_TITLE.toString(), "jar1", version);
      mfIS.close();
   }

   /**
    * Test a scan of the outer.jar vfs to locate all .class files
    * @throws Exception
    */
   public void testClassScan()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test/outer.jar");
      VFS vfs = VFS.getVFS(rootURL);

      HashSet<String> expectedClasses = new HashSet<String>();
      expectedClasses.add("jar1.jar/org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      expectedClasses.add("jar1.jar/org/jboss/test/vfs/support/jar1/ClassInJar1$InnerClass.class");
      expectedClasses.add("jar2.jar/org/jboss/test/vfs/support/jar2/ClassInJar2.class");
      expectedClasses.add("org/jboss/test/vfs/support/CommonClass.class");
      super.enableTrace("org.jboss.virtual.plugins.vfs.helpers.SuffixMatchFilter");
      SuffixMatchFilter classVisitor = new SuffixMatchFilter(".class", VisitorAttributes.RECURSE);
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
    * Test a scan of the unpacked-outer.jar vfs to locate all .class files
    * @throws Exception
    */
   public void testClassScanUnpacked()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test/unpacked-outer.jar");
      VFS vfs = VFS.getVFS(rootURL);
   
      HashSet<String> expectedClasses = new HashSet<String>();
      expectedClasses.add("jar1.jar/org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      expectedClasses.add("jar1.jar/org/jboss/test/vfs/support/jar1/ClassInJar1$InnerClass.class");
      expectedClasses.add("jar2.jar/org/jboss/test/vfs/support/jar2/ClassInJar2.class");
      expectedClasses.add("org/jboss/test/vfs/support/CommonClass.class");
      super.enableTrace("org.jboss.virtual.plugins.vfs.helpers.SuffixMatchFilter");
      SuffixMatchFilter classVisitor = new SuffixMatchFilter(".class", VisitorAttributes.RECURSE);
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
    * Test a scan of the jar1-filesonly.jar vfs to locate all .class files
    * @throws Exception
    */
   public void testClassScanFilesonly()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test/jar1-filesonly.jar");
      VFS vfs = VFS.getVFS(rootURL);
   
      HashSet<String> expectedClasses = new HashSet<String>();
      expectedClasses.add("org/jboss/test/vfs/support/jar1/ClassInJar1.class");
      expectedClasses.add("org/jboss/test/vfs/support/jar1/ClassInJar1$InnerClass.class");
      super.enableTrace("org.jboss.virtual.plugins.vfs.helpers.SuffixMatchFilter");
      SuffixMatchFilter classVisitor = new SuffixMatchFilter(".class", VisitorAttributes.RECURSE);
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
      assertEquals("There were 2 classes", 2, count);

      // Make sure we can walk path-wise to the class
      VirtualFile jar1 = vfs.getRoot();
      VirtualFile parent = jar1;
      String className = "org/jboss/test/vfs/support/jar1/ClassInJar1.class";
      VirtualFile ClassInJar1 = vfs.findChild(className);
      String[] paths = className.split("/");
      StringBuilder vfsPath = new StringBuilder();
      for(String path : paths)
      {
         vfsPath.append(path);
         VirtualFile vf = parent.findChild(path);
         if( path.equals("ClassInJar1.class") )
            assertEquals("ClassInJar1.class", ClassInJar1, vf);
         else
         {
            assertEquals("vfsPath", vfsPath.toString(), vf.getPathName());
            assertEquals("lastModified", ClassInJar1.getLastModified(), vf.getLastModified());
         }
         vfsPath.append('/');
         parent = vf;
      }
   }

   /**
    * Test access of directories in a jar that only stores files
    * @throws Exception
    */
   public void testFilesOnlyJar()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);

      VirtualFile jar = vfs.findChild("jar1-filesonly.jar");
      VirtualFile metadataLocation = jar.findChild("META-INF");
      assertNotNull(metadataLocation);
      VirtualFile mfFile = metadataLocation.findChild("MANIFEST.MF");
      assertNotNull(mfFile);
      InputStream is = mfFile.openStream();
      Manifest mf = new Manifest(is);
      mfFile.close();
      String title = mf.getMainAttributes().getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals(Attributes.Name.SPECIFICATION_TITLE.toString(), "jar1-filesonly", title);

      // Retry starting from the jar root
      mfFile = jar.findChild("META-INF/MANIFEST.MF");
      is = mfFile.openStream();
      mf = new Manifest(is);
      mfFile.close();
      title = mf.getMainAttributes().getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals(Attributes.Name.SPECIFICATION_TITLE.toString(), "jar1-filesonly", title);
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
      VirtualFile tmpVF = vfs.findChild("vfs.ser");
      FileOutputStream fos = new FileOutputStream(tmp);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(tmpVF);
      oos.close();

      // Check the tmpVF attributes against the tmp file
      long lastModified = tmp.lastModified();
      long size = tmp.length();
      String name = tmp.getName();
      String vfsPath = tmp.getPath();
      vfsPath = vfsPath.substring(tmpRoot.getPath().length()+1);
      URL url = tmp.toURL();
      log.debug("name: "+name);
      log.debug("vfsPath: "+vfsPath);
      log.debug("url: "+url);
      log.debug("lastModified: "+lastModified);
      log.debug("size: "+size);
      assertEquals("name", name, tmpVF.getName());
      assertEquals("pathName", vfsPath, tmpVF.getPathName());
      assertEquals("lastModified", lastModified, tmpVF.getLastModified());
      assertEquals("size", size, tmpVF.getSize());
      assertEquals("url", url, tmpVF.toURL());
      assertEquals("isLeaf", true, tmpVF.isLeaf());
      assertEquals("isHidden", false, tmpVF.isHidden());

      // Read in the VF from the serialized file
      FileInputStream fis = new FileInputStream(tmp);
      ObjectInputStream ois = new ObjectInputStream(fis);
      VirtualFile tmpVF2 = (VirtualFile) ois.readObject();
      ois.close();
      // Validated the deserialized attribtes against the tmp file
      assertEquals("name", name, tmpVF2.getName());
      assertEquals("pathName", vfsPath, tmpVF2.getPathName());
      assertEquals("lastModified", lastModified, tmpVF2.getLastModified());
      assertEquals("size", size, tmpVF2.getSize());
      assertEquals("url", url, tmpVF2.toURL());
      assertEquals("isLeaf", true, tmpVF2.isLeaf());
      assertEquals("isHidden", false, tmpVF2.isHidden());
   }

   /**
    * Test the serialization of VirtualFiles representing a jar
    * @throws Exception
    */
   public void testVFJarSerialization()
      throws Exception
   {
      File tmpRoot = File.createTempFile("vfs", ".root");
      tmpRoot.delete();
      tmpRoot.mkdir();
      tmpRoot.deleteOnExit();
      // Create a test jar containing a txt file
      File tmpJar = new File(tmpRoot, "tst.jar");
      tmpJar.createNewFile();
      tmpJar.deleteOnExit();
      FileOutputStream fos = new FileOutputStream(tmpJar);
      JarOutputStream jos = new JarOutputStream(fos);
      // Write a text file to include in a test jar
      JarEntry txtEntry = new JarEntry("tst.txt");
      jos.putNextEntry(txtEntry);
      txtEntry.setSize("testVFJarSerialization".length());
      txtEntry.setTime(System.currentTimeMillis());
      jos.write("testVFJarSerialization".getBytes());
      jos.close();
      log.info("+++ testVFJarSerialization, tmp="+tmpJar.getCanonicalPath());

      URI rootURI = tmpRoot.toURI();
      VFS vfs = VFS.getVFS(rootURI);
      File vfsSer = new File(tmpRoot, "vfs.ser");
      vfsSer.createNewFile();
      vfsSer.deleteOnExit();

      VirtualFile tmpVF = vfs.findChild("tst.jar");
      // Validate the vf jar against the tmp file attributes
      long lastModified = tmpJar.lastModified();
      long size = tmpJar.length();
      String name = tmpJar.getName();
      String vfsPath = tmpJar.getPath();
      vfsPath = vfsPath.substring(tmpRoot.getPath().length()+1);
      URL url = tmpJar.toURL();
      url = JarUtils.createJarURL(url);
      log.debug("name: "+name);
      log.debug("vfsPath: "+vfsPath);
      log.debug("url: "+url);
      log.debug("lastModified: "+lastModified);
      log.debug("size: "+size);
      assertEquals("name", name, tmpVF.getName());
      assertEquals("pathName", vfsPath, tmpVF.getPathName());
      assertEquals("lastModified", lastModified, tmpVF.getLastModified());
      assertEquals("size", size, tmpVF.getSize());
      assertEquals("url", url, tmpVF.toURL());
      // TODO: these should pass
      //assertEquals("isFile", true, tmpVF.isFile());
      //assertEquals("isDirectory", false, tmpVF.isDirectory());
      assertEquals("isHidden", false, tmpVF.isHidden());
      // Write out the vfs jar file
      fos = new FileOutputStream(vfsSer);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(tmpVF);
      oos.close();

      // Read in the VF from the serialized file
      FileInputStream fis = new FileInputStream(vfsSer);
      ObjectInputStream ois = new ObjectInputStream(fis);
      VirtualFile tmpVF2 = (VirtualFile) ois.readObject();
      ois.close();
      // Validate the vf jar against the tmp file attributes
      assertEquals("name", name, tmpVF2.getName());
      assertEquals("pathName", vfsPath, tmpVF2.getPathName());
      assertEquals("lastModified", lastModified, tmpVF2.getLastModified());
      assertEquals("size", size, tmpVF2.getSize());
      assertEquals("url", url, tmpVF2.toURL());
      // TODO: these should pass
      //assertEquals("isFile", true, tmpVF2.isFile());
      //assertEquals("isDirectory", false, tmpVF2.isDirectory());
      assertEquals("isHidden", false, tmpVF2.isHidden());
   }

   /**
    * Test the serialization of VirtualFiles representing a jar
    * @throws Exception
    */
   public void testVFNestedJarSerialization()
      throws Exception
   {
      // this expects to be run with a working dir of the container root
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile inner = vfs.findChild("outer.jar/jar1.jar");

      File vfsSer = File.createTempFile("testVFNestedJarSerialization", ".ser");
      vfsSer.deleteOnExit();
      // Write out the vfs inner jar file
      FileOutputStream fos = new FileOutputStream(vfsSer);
      ObjectOutputStream oos = new ObjectOutputStream(fos);
      oos.writeObject(inner);
      oos.close();

      
      // Read in the VF from the serialized file
      FileInputStream fis = new FileInputStream(vfsSer);
      ObjectInputStream ois = new ObjectInputStream(fis);
      inner = (VirtualFile) ois.readObject();
      ois.close();
      List<VirtualFile> contents = inner.getChildren();
      // META-INF/*, org/jboss/test/vfs/support/jar1/* at least
      assertTrue("jar1.jar children.length("+contents.size()+") >= 2", contents.size() >= 2);
      for(VirtualFile vf : contents)
      {
         log.info("  "+vf.getName());
      }
      VirtualFile vf = vfs.findChild("outer.jar/jar1.jar");
      VirtualFile jar1MF = vf.findChild("META-INF/MANIFEST.MF");
      InputStream mfIS = jar1MF.openStream();
      Manifest mf = new Manifest(mfIS);
      Attributes mainAttrs = mf.getMainAttributes();
      String version = mainAttrs.getValue(Attributes.Name.SPECIFICATION_TITLE);
      assertEquals(Attributes.Name.SPECIFICATION_TITLE.toString(), "jar1", version);
      mfIS.close();
   }

   /**
    * Test parsing of a vfs link properties file. It contains test.classes.url
    * and test.lib.url system property references that are configured to
    * point to the CodeSource location of this class and /vfs/sundry/jar/
    * respectively.
    * 
    * @throws Exception
    */
   public void testVfsLinkProperties()
      throws Exception
   {
      URL linkURL = super.getResource("/vfs/links/war1.vfslink.properties");
      assertNotNull("vfs/links/war1.vfslink.properties", linkURL);
      // Find resources to use as the WEB-INF/{classes,lib} link targets
      URL classesURL = getClass().getProtectionDomain().getCodeSource().getLocation();
      assertNotNull("classesURL", classesURL);
      System.setProperty("test.classes.url", classesURL.toString());
      URL libURL = super.getResource("/vfs/sundry/jar");
      assertNotNull("libURL", libURL);      
      System.setProperty("test.lib.url", libURL.toString());

      assertTrue("isLink", VFSUtils.isLink(linkURL.getPath()));
      Properties props = new Properties();
      InputStream linkIS = linkURL.openStream();
      List<LinkInfo> infos = VFSUtils.readLinkInfo(linkIS, linkURL.getPath(), props);
      assertEquals("LinkInfo count", 2, infos.size());
      LinkInfo classesInfo = null;
      LinkInfo libInfo = null;
      for(LinkInfo info :infos)
      {
         if( info.getName().equals("WEB-INF/classes") )
            classesInfo = info;
         else if(info.getName().equals("WEB-INF/lib") )
            libInfo = info;
      }
      assertNotNull("classesInfo", classesInfo);
      assertEquals("classesInfo.target", classesURL.toURI(), classesInfo.getLinkTarget());
      assertNotNull("libInfo", libInfo);
      assertEquals("libInfo.target", libURL.toURI(), libInfo.getLinkTarget());
   }

   /**
    * Test the test-link.war link
    * @throws Exception
    */
   public void testWarLink()
      throws Exception
   {
      // Find resources to use as the WEB-INF/{classes,lib} link targets
      URL classesURL = getClass().getProtectionDomain().getCodeSource().getLocation();
      assertNotNull("classesURL", classesURL);
      System.setProperty("test.classes.url", classesURL.toString());
      URL libURL = super.getResource("/vfs/sundry/jar");
      assertNotNull("libURL", libURL);      
      System.setProperty("test.lib.url", libURL.toString());

      // Root the vfs at the link file parent directory
      URL linkURL = super.getResource("/vfs/links/war1.vfslink.properties");
      File linkFile = new File(linkURL.toURI());
      File vfsRoot = linkFile.getParentFile();
      assertNotNull("vfs/links/war1.vfslink.properties", linkURL);
      VFS vfs = VFS.getVFS(vfsRoot.toURI());

      // We should find the test-link.war the link represents
      VirtualFile war = vfs.findChild("test-link.war");
      assertNotNull("war", war);

      // Validate the WEB-INF/classes child link
      VirtualFile classes = war.findChild("WEB-INF/classes");
      String classesName = classes.getName();
      String classesPathName = classes.getPathName();
      boolean classesIsDirectory = classes.isLeaf() == false;
      assertEquals("classes.name", "classes", classesName);
      assertEquals("classes.pathName", "test-link.war/WEB-INF/classes", classesPathName);
      assertEquals("classes.isDirectory", true, classesIsDirectory);
      // Should be able to find this class since classes points to out codesource
      VirtualFile thisClass = classes.findChild("org/jboss/test/virtual/test/FileVFSUnitTestCase.class");
      assertEquals("FileVFSUnitTestCase.class", thisClass.getName());

      // Validate the WEB-INF/lib child link
      VirtualFile lib = war.findChild("WEB-INF/lib");
      String libName = lib.getName();
      String libPathName = lib.getPathName();
      boolean libIsDirectory = lib.isLeaf() == false;
      assertEquals("lib.name", "lib", libName);
      assertEquals("lib.pathName", "test-link.war/WEB-INF/lib", libPathName);
      assertEquals("lib.isDirectory", true, libIsDirectory);
      // Should be able to find archive.jar under lib
      VirtualFile archiveJar = lib.findChild("archive.jar");
      assertEquals("archive.jar", archiveJar.getName());
   }

  /**
    * Test that the URL of a VFS corresponding to a directory ends in '/' so that
    * URLs created relative to it are under the directory. This requires that
    * build-test.xml artifacts exist.
    * 
    * @throws Exception
    */
   public void testDirURLs() throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);

      VirtualFile outerJar = vfs.findChild("unpacked-outer.jar");
      URL outerURL = outerJar.toURL();
      log.debug("outerURL: "+outerURL);
      assertTrue(outerURL+" ends in '/'", outerURL.getPath().endsWith("/"));
      // Validate that jar1 is under unpacked-outer.jar
      URL jar1URL = JarUtils.createJarURL(new URL(outerURL, "jar1.jar"));
      log.debug("jar1URL: "+jar1URL+", path="+jar1URL.getPath());
      assertTrue("jar1URL path ends in unpacked-outer.jar/jar1.jar!/",
            jar1URL.getPath().endsWith("unpacked-outer.jar/jar1.jar!/"));
      VirtualFile jar1 = outerJar.findChild("jar1.jar");
      assertEquals(jar1URL, jar1.toURL());

      VirtualFile packedJar = vfs.findChild("jar1.jar");
      jar1URL = packedJar.findChild("org/jboss/test/vfs/support").toURL();
      assertTrue("Jar directory entry URLs must end in /: " + jar1URL.toString(), jar1URL.toString().endsWith("/"));
   }

   /**
    * Test copying a jar
    * 
    * @throws Exception
    */
   public void testCopyJar()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile jar = vfs.findChild("outer.jar");
      assertTrue("outer.jar != null", jar != null);
      File tmpJar = File.createTempFile("testCopyJar", ".jar");
      tmpJar.deleteOnExit();

      try
      {
         InputStream is = jar.openStream();
         FileOutputStream fos = new FileOutputStream(tmpJar);
         byte[] buffer = new byte[1024];
         int read;
         while( (read = is.read(buffer)) > 0 )
         {
            fos.write(buffer, 0, read);
         }
         fos.close();
         log.debug("outer.jar size is: "+jar.getSize());
         log.debug(tmpJar.getAbsolutePath()+" size is: "+tmpJar.length());
         assertTrue("outer.jar > 0", jar.getSize() > 0);
         assertEquals("copy jar size", jar.getSize(), tmpJar.length());
         jar.close();
      }
      finally
      {
         try
         {
            tmpJar.delete();
         }
         catch(Exception ignore)
         {
         }
      }
   }

   /**
    * Test copying a jar that is nested in another jar.
    * 
    * @throws Exception
    */
   public void testCopyInnerJar()
      throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      VirtualFile outerjar = vfs.findChild("outer.jar");
      assertTrue("outer.jar != null", outerjar != null);
      VirtualFile jar = outerjar.findChild("jar1.jar");
      assertTrue("outer.jar/jar1.jar != null", jar != null);

      File tmpJar = File.createTempFile("testCopyInnerJar", ".jar");
      tmpJar.deleteOnExit();

      try
      {
         InputStream is = jar.openStream();
         FileOutputStream fos = new FileOutputStream(tmpJar);
         byte[] buffer = new byte[1024];
         int read;
         while( (read = is.read(buffer)) > 0 )
         {
            fos.write(buffer, 0, read);
         }
         fos.close();
         log.debug("outer.jar/jar1.jar size is: "+jar.getSize());
         log.debug(tmpJar.getAbsolutePath()+" size is: "+tmpJar.length());
         assertTrue("outer.jar > 0", jar.getSize() > 0);
         assertEquals("copy jar size", jar.getSize(), tmpJar.length());
         jar.close();
      }
      finally
      {
         try
         {
            tmpJar.delete();
         }
         catch(Exception ignore)
         {
         }
      }
   }

   /**
    * Tests that we can find the META-INF/some-data.xml in an unpacked deployment
    */
   public void testGetMetaDataUnpackedJar() throws Exception
   {
      testGetMetaDataFromJar("unpacked-with-metadata.jar");
   }
   
   /**
    * Tests that we can find the META-INF/some-data.xml in a packed deployment
    */
   public void testGetMetaDataPackedJar() throws Exception
   {
      testGetMetaDataFromJar("with-metadata.jar");
   }
   
   private void testGetMetaDataFromJar(String name) throws Exception
   {
      URL rootURL = getResource("/vfs/test");
      VFS vfs = VFS.getVFS(rootURL);
      
      VirtualFile jar = vfs.findChild(name);
      assertNotNull(jar);
      VirtualFile metadataLocation = jar.findChild("META-INF");
      assertNotNull(metadataLocation);

      VirtualFile metadataByName = metadataLocation.findChild("some-data.xml");
      assertNotNull(metadataByName);
      
      //This is the same code as is called by AbstractDeploymentContext.getMetaDataFiles(String name, String suffix). 
      //The MetaDataMatchFilter is a copy of the one used there
      List<VirtualFile> metaDataList = metadataLocation.getChildren(new MetaDataMatchFilter(null, "-data.xml"));
      assertNotNull(metaDataList);
      assertEquals("Wrong size", 1, metaDataList.size());
   }
}
