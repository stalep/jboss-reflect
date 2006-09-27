package org.jboss.test.virtual.test;

import java.net.URL;

import org.jboss.test.BaseTestCase;
import org.jboss.virtual.VFS;
import org.jboss.virtual.classloading.VFSClassLoader;
import org.jboss.virtual.plugins.context.jar.JarUtils;

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
      URL url = getResource("/vfs/test/");
      VFS vfs = VFS.getVFS(url);
   
      String[] searchCtxts = {"jar1.jar"};
      ClassLoader parent = null;
      VFSClassLoader cl = new VFSClassLoader(searchCtxts, vfs, parent);
      URL mf = cl.findResource("META-INF/MANIFEST.MF");
      assertTrue("META-INF/application.xml != null", mf != null);
      log.info(mf);
      
      URL expected = new URL(url, "jar1.jar");
      expected = JarUtils.createJarURL(expected);
      expected = new URL(expected, "META-INF/MANIFEST.MF");
      assertEquals(expected, mf);

      Class c = cl.loadClass("org.jboss.test.vfs.support.jar1.ClassInJar1");
      assertEquals("org.jboss.test.vfs.support.jar1.ClassInJar1", c.getName());
      URL csURL = c.getProtectionDomain().getCodeSource().getLocation();
      log.info(csURL);
      assertTrue("jar1.jar is in codesource", csURL.getPath().indexOf("jar1.jar") > 0);
   }
}
