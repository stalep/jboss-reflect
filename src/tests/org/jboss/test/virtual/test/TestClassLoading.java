package org.jboss.test.virtual.test;

import java.io.File;
import java.net.URL;

import org.jboss.test.BaseTestCase;
import org.jboss.virtual.VFS;
import org.jboss.virtual.classloading.VFSClassLoader;

public class TestClassLoading extends BaseTestCase
{
   public TestClassLoading(String name)
   {
      super(name);
   }

   public void testJarClasses()
      throws Exception
   {
      super.enableTrace("org.jboss");
      File libDir = new File("output/lib");
      URL libURL = libDir.toURL();
      VFS vfs = VFS.getVFS(libURL);
   
      String[] searchCtxts = {"jar1.jar"};
      ClassLoader parent = null;
      VFSClassLoader cl = new VFSClassLoader(searchCtxts, vfs, parent);
      URL mf = cl.findResource("META-INF/MANIFEST.MF");
      assertTrue("META-INF/application.xml != null", mf != null);
      log.info(mf);
      assertEquals("jar:file:/C:/svn/JBossMC/jbossmc/container/output/lib/jar1.jar!/META-INF/MANIFEST.MF", mf.toString());

      Class c = cl.loadClass("org.jboss.test.vfs.support.jar1.ClassInJar1");
      assertEquals("org.jboss.test.vfs.support.jar1.ClassInJar1", c.getName());
      URL csURL = c.getProtectionDomain().getCodeSource().getLocation();
      log.info(csURL);
      assertTrue("jar1.jar is in codesource", csURL.getPath().indexOf("jar1.jar") > 0);
   }
}
