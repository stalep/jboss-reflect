/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.vfs;

import org.jboss.vfs.spi.ReadOnlyVFS;

import java.net.URL;

/**
 * The entry point to obtaining a VFS for a given URL root mount point
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface VFSFactory
{
   public String[] getProtocols();
   public ReadOnlyVFS getVFS(URL url);
}
