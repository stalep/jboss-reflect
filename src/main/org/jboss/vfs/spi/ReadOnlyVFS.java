package org.jboss.vfs.spi;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.List;
import java.io.FileNotFoundException;
import java.io.IOException;


/** Prototype for a read-only VFS where virtual files are represented by URLs.
 * A VFS is a tree of URLs segmented into paths by URL protocol.
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface ReadOnlyVFS
{
   /**
    * Locate a file in the VFS given its URL path.
    * 
    * @param path - the absolute path to the virtual file (file:/root/deploy/x.ear)
    * @return the matching VirtualFile
    * @throws FileNotFoundException throw if the path could not be resolved 
    */
   public VirtualFile resolveFile(String path)
      throws FileNotFoundException, MalformedURLException;

   /**
    * Locate a file in the VFS given a relative URL path and contexts in
    * the VFS to search from.
    * 
    * @param path - a relative URL path (x.ear)
    * @param searchContexts - the absolute paths in the VFS of the contexts to search from
    * @return the matching VirtualFile
    * @throws FileNotFoundException throw if the path could not be resolved 
    */
   public VirtualFile resolveFile(String path, List<String> searchContexts)
      throws IOException;

   /**
    * Find all files in the VFS matching the relative URL path.
    * @param path - a relative URL path (x.ear)
    * @return A possibly empty list of matching files
    */
   public List<VirtualFile> resolveFiles(String path);
   /**
    * Locate all files in the VFS given a relative URL path and contexts in
    * the VFS to search from.
    * 
    * @param path - a relative URL path (x.ear)
    * @param searchContexts - the absolute paths in the VFS of the contexts to search from
    * @return A possibly empty list of matching files
    * @return A possibly empty list of matching files
    */
   public List<VirtualFile> resolveFiles(String path, List<URL> searchContexts);

   /**
    * Clear any caches associated with the VFS
    */
   public void clear();
}
