/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.test.vfs;

import java.net.URL;
import junit.framework.TestCase;
import org.jboss.vfs.VFSClassLoader;
import org.jboss.vfs.VFSFactory;
import org.jboss.vfs.VFSFactoryLocator;
import org.jboss.vfs.spi.ReadOnlyVFS;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class TestClassLoading  extends TestCase
{
   public void testEarClasses()
      throws Exception
   {
      URL root = new URL("file://usr/local/java5");
      VFSFactory factory = VFSFactoryLocator.getFactory(root);
      assertTrue("VFSFactory(file://usr/local/java5) != null", factory != null);

      ReadOnlyVFS vfs = factory.getVFS(root);
      String[] searchCtxts = {"jbosstest-web.ear"};
      VFSClassLoader cl = new VFSClassLoader(searchCtxts, vfs);
      URL appXml = cl.findResource("META-INF/application.xml");
      assertTrue("META-INF/application.xml != null", appXml != null);
   }
}
