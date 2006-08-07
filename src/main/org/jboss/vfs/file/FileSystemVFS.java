/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.vfs.file;

import org.jboss.vfs.spi.ReadOnlyVFS;
import org.jboss.vfs.spi.VirtualFile;
import org.jboss.vfs.spi.VFSVisitor;
import org.jboss.vfs.VFSFactory;
import org.jboss.vfs.VFSFactoryLocator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.Iterator;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/** A simple implementation of ReadOnlyVFS that only understands file URLs
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class FileSystemVFS
   implements ReadOnlyVFS
{
   // TODO: jboss logger
   private static Logger log = Logger.getLogger("org.jboss.vfs.file.FileSystemVFS");
   /** */
   private static WeakHashMap<URL, FileSystemVFS> fileSystems =
      new WeakHashMap<URL, FileSystemVFS>();
   /** The VFS root URL */
   private URL rootURL;
   /** The VirtualFile corresponding to the rootURL */
   private VirtualFile vfsRoot;
   /** Cache of rootURL absolute paths to previously resolved files */
   private ConcurrentHashMap<String, VirtualFile> fileCache;

   /**
    * get VirtualFile from filesystem path
    *
    * @param fileSystemPath path, i.e. "/home/wburke/foo.jar"
    * @return the VirtualFiel for fileSystemPath
    * @throws RuntimeException wrapper for any error  
    */
   public static VirtualFile getVirtualFile(String fileSystemPath)
   {
      try
      {
         File fp = new File(fileSystemPath);
         URL parent = fp.getParentFile().toURL();
         VFSFactory factory = VFSFactoryLocator.getFactory(parent);
         ReadOnlyVFS vfs = factory.getVFS(parent);
         return (vfs.resolveFile(fp.getName()));
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }

   }

   /**
    * Get the FileSystemVFS for the rootURL.
    * 
    * @param rootURL
    * @return
    * @throws IOException
    */
   public static synchronized FileSystemVFS getFileSystemVFS(URL rootURL)
      throws IOException
   {
      FileSystemVFS vfs = fileSystems.get(rootURL);
      if( vfs == null )
      {
         vfs = new FileSystemVFS(rootURL);
         fileSystems.put(rootURL, vfs);
      }
      return vfs;
   }

   FileSystemVFS(URL rootURL)
      throws IOException
   {
      this.rootURL = rootURL;
      String path = rootURL.getPath();
      if( JarImpl.isJar(path) )
         vfsRoot = new JarImpl(path, "");
      else
         vfsRoot = new FileImpl(rootURL, "", this);
      fileCache = new ConcurrentHashMap<String, VirtualFile>();
   }

   public VirtualFile resolveFile(String path)
      throws FileNotFoundException, MalformedURLException
   {
      try
      {
         VirtualFile vf = this.resolveFile(path, "");
         return vf;
      }
      catch(FileNotFoundException e)
      {
         throw e;
      }
      catch (IOException e)
      {
         FileNotFoundException fnfe = new FileNotFoundException(path);
         fnfe.initCause(e);
         throw fnfe;
      }
   }

   public VirtualFile resolveFile(String path, List<String> searchContexts)
      throws IOException
   {
      VirtualFile match = null;
      for(String ctx : searchContexts)
      {
         // Check the cache
         match = fileCache.get(path);
         if( match == null )
         {
            // See if we can create a virtual file from the context
            match = resolveFile(path, ctx);
         }
         if( match != null )
            break;
      }
      return match;
   }

   public List<VirtualFile> resolveFiles(String path)
   {
      return null;
   }

   /**
    * TODO
    */
   public List<VirtualFile> resolveFiles(String path, List<URL> searchContexts)
   {
      return null;
   }

   /**
    * Clear the Map<String, VirtualFile> of name to VF hits
    */
   public void clear()
   {
      fileCache.clear();
   }

   public Iterator<VirtualFile> scan(VFSVisitor acceptVisitor)
   {
      FileScanner scanner = new FileScanner(vfsRoot, acceptVisitor);
      return scanner;
   }

   /**
    * Get the rootURL for the VFS
    * @return rootURL for the VFS
    */
   public URL getRootURL()
   {
      return rootURL;
   }

   /**
    * Resolve the given path against the filesystem search context. This first
    * locates the VirtualFile corresponding to the searchContext, and then
    * resolves the file against it.
    *
    * @param path a VFS path
    * @param searchContext - the filesystem path to start the search from
    * @return the resolved VirtualFile
    * @throws IOException
    */
   private VirtualFile resolveFile(String path, String searchContext)
      throws IOException
   {
      VirtualFile match = null;
      // Parse the search context into its components
      String[] ctxAtoms = searchContext.split("!/|/");
      // Look for a
      boolean inJar = false;
      StringBuffer vfsPath = new StringBuffer();
      VirtualFile prevVF = null;
      for(String atom : ctxAtoms)
      {
         if( vfsPath.length() > 0 )
            vfsPath.append('/');
         vfsPath.append(atom);
         String atomPath = vfsPath.toString();
         String absPath = rootURL.getPath() + atomPath;
         VirtualFile atomVF = fileCache.get(atomPath);
         if( atomVF == null )
         {
            try
            {
               // Create the atom file and cache it
               if( inJar == true )
               {
                  atomVF = prevVF.findChild(atom);
               }
               else if( JarImpl.isJar(atom) )
               {
                  atomVF = new JarImpl(absPath, atomPath);
                  inJar = true;
               }
               else
               {
                  URL atomParentURL = prevVF == null ? rootURL : prevVF.toURL();
                  URL filePath = new URL(atomParentURL, atom);
                  atomVF = new FileImpl(filePath, atomPath, this);
               }
               fileCache.put(atomPath, atomVF);
               prevVF = atomVF;
            }
            catch(IOException e)
            {
               log.log(Level.FINE,
                  "Failed to create virtual file for atom up to: "+absPath, e);
            }
         }
         else
         {
            prevVF = atomVF;
         }
      }
      if( prevVF == null )
         throw new FileNotFoundException("Failed to find file for path: "+path);
      match = prevVF.findChild(path);
      return match;
   }

   VirtualFile getChild(URL parentURL, String path)
      throws IOException
   {
      // Parse the search context into its components
      String[] ctxAtoms = path.split("!/|/");
      // Look for a
      boolean inJar = false;
      StringBuffer vfsPath = new StringBuffer();
      VirtualFile childVF = null;
      for(String atom : ctxAtoms)
      {
         if( vfsPath.length() > 0 )
            vfsPath.append('/');
         vfsPath.append(atom);
         String atomPath = vfsPath.toString();
         String absPath = rootURL.getPath() + atomPath;
         VirtualFile atomVF = fileCache.get(atomPath);
         if( atomVF == null )
         {
            // Create the atom file and cache it
            if( inJar == true )
            {
               atomVF = childVF.findChild(atom);
            }
            else
            {
               // Need to validate that the jar is not unpacked dir
               StringBuilder tmp = new StringBuilder(atomPath);
               File test = new File(tmp.toString());
               if( test.isFile() && JarImpl.isJar(atom) )
               {
                  atomVF = new JarImpl(absPath, atomPath);
                  inJar = true;
               }
               else
               {
                  URL atomParentURL = childVF == null ? parentURL : childVF.toURL();
                  String parentString = atomParentURL.toString();
                  if (!parentString.endsWith("/"))
                     atomParentURL = new URL(parentString + "/");
                  URL filePath = new URL(atomParentURL, atom);
                  atomVF = new FileImpl(filePath, atomPath, this);
               }
            }
            fileCache.put(atomPath, atomVF);
            childVF = atomVF;
         }
         else
         {
            childVF = atomVF;
         }
      }
      return childVF;
   }

}
