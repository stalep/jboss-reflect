/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.vfs.file;

import java.net.URL;
import org.jboss.vfs.VFSFactory;
import org.jboss.vfs.spi.ReadOnlyVFS;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class DefaultVFSFactory implements VFSFactory
{
   private static String[] PROTOCOLS = {"file"};

   public String[] getProtocols()
   {
      return PROTOCOLS;
   }

   /**
    * @todo return differnt implementations based on the type of root:
    * - a directory of static jars such as the server/x/lib
    * - an indexed deployment
    * - a standalone deployment
    * - a hot deployable directory
    * @param root
    * @return A ReadOnlyVFS for file URLs rooted at the root
    */
   public ReadOnlyVFS getVFS(URL root)
   {
      return new FileSystemVFS(root);
   }
}
