/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.vfs.file;

import java.net.URL;
import java.net.MalformedURLException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.concurrent.ConcurrentHashMap;

import org.jboss.vfs.spi.ReadOnlyVFS;
import org.jboss.vfs.spi.VirtualFile;

/** A simple implementation of ReadOnlyVFS that only understands file URLs
 *  
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class FileSystemVFS
   implements ReadOnlyVFS
{
   private static Logger log = Logger.getLogger("org.jboss.vfs.file.FileSystemVFS");
   private URL rootURL;
   private File vfsRoot;
   private ConcurrentHashMap<String, VirtualFile> fileCache;

   FileSystemVFS(URL rootURL)
   {
      this.rootURL = rootURL;
      vfsRoot = new File(rootURL.getFile());
      fileCache = new ConcurrentHashMap<String, VirtualFile>();
   }

   public VirtualFile resolveFile(String path)
      throws FileNotFoundException, MalformedURLException
   {
      URL filePath = new URL(rootURL, path);
      FileImpl file = new FileImpl(filePath);
      return file;
   }

   public VirtualFile resolveFile(String path, List<String> searchContexts)
      throws IOException
   {
      VirtualFile match = null;
      // Parse the path into its components
      String[] atoms = path.split("!/");
      for(String ctx : searchContexts)
      {
         // Check the cache
         match = fileCache.get(path);
         if( match == null )
         {
            // See if we can create a virtual file from the context
            match = resolveFile(path, ctx);
         }
      }
      return match;
   }

   public List<VirtualFile> resolveFiles(String path)
   {
      return null;
   }

   public List<VirtualFile> resolveFiles(String path, List<URL> searchContexts)
   {
      return null;
   }

   public void clear()
   {
      fileCache.clear();
   }

   private VirtualFile resolveFile(String path, String searchContext)
      throws IOException
   {
      VirtualFile match = null;
      // Parse the search context into its components
      String[] ctxAtoms = searchContext.split("!/");
      // Look for a
      boolean inJar = false;
      StringBuffer activePath = new StringBuffer(rootURL.getPath());
      VirtualFile prevVF = null;
      for(String atom : ctxAtoms)
      {
         activePath.append('/');
         activePath.append(atom);
         VirtualFile atomVF = fileCache.get(atom);
         JarImpl jar = null;
         if( atomVF == null )
         {
            try
            {
               // Create the atom file and cache it
               if( inJar == true )
               {
                  atomVF = prevVF.findChild(atom);
               }
               else
               {
                  if( JarImpl.isJar(atom) )
                  {
                     jar = new JarImpl(activePath.toString());
                     atomVF = jar;
                     inJar = true;
                  }
               }
               fileCache.put(activePath.toString(), atomVF);
               prevVF = atomVF;
            }
            catch(IOException e)
            {
               log.log(Level.FINE,
                  "Failed to create virtual file for atom up to: "+activePath, e);
            }
         }
      }
      match = prevVF.findChild(path);
      return match;
   }

   private void validateURL(URL path)
      throws FileNotFoundException
   {
      String scheme = path.getProtocol();
      if( scheme.equalsIgnoreCase("file") == false )
         throw new FileNotFoundException("Only file schemes are supported, invalid path: "+path);
   }
}
