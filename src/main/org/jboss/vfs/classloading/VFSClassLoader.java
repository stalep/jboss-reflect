/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.vfs.classloading;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.security.SecureClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import org.jboss.classloading.spi.ClassLoadingDomain;
import org.jboss.classloading.spi.DomainClassLoader;
import org.jboss.vfs.spi.ReadOnlyVFS;
import org.jboss.vfs.spi.VirtualFile;

/** A class loader that obtains classes and resources from a VFS.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class VFSClassLoader extends SecureClassLoader
   implements DomainClassLoader
{
   protected static class ClassPathVFS
   {
      private ArrayList<String> searchCtxs = new ArrayList<String>();
      private ReadOnlyVFS vfs;
      protected ClassPathVFS(String[] searchCtxs, ReadOnlyVFS vfs)
      {
         this.searchCtxs.addAll(Arrays.asList(searchCtxs));
         this.vfs = vfs;
      }
   }
   protected ArrayList<ClassPathVFS> classpath = new ArrayList<ClassPathVFS>();

   /**
    * Create a class loader given a search path and VFS
    * @param searchCtxs - the paths from the VFS that make up the class loader path
    * @param vfs - the VFS used to resolve and load classes and resources
    */
   public VFSClassLoader(String[] searchCtxs, ReadOnlyVFS vfs)
   {
     ClassPathVFS cp  = new ClassPathVFS(searchCtxs, vfs);
      classpath.add(cp);
   }

   /**
    * Find and define the given java class
    * 
    * @param name - the binary class name
    * @return the defined Class object
    * @throws ClassNotFoundException thrown if the class could not be found
    *    or defined
    */
   protected Class<?> findClass(String name) throws ClassNotFoundException
   {
      String resName = name.replace('.', '/');
      URL classRes = findResource(resName+".class");
      if( classRes == null )
         throw new ClassNotFoundException(name);
      try
      {
         byte[] tmp = new byte[128];
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         InputStream is = classRes.openStream();
         int length;
         while ((length = is.read(tmp)) > 0)
         {
            baos.write(tmp, 0, length);
         }
         is.close();
         tmp = baos.toByteArray();
         Class c = super.defineClass(name, tmp, 0, tmp.length);
         return c;
      }
      catch (IOException e)
      {
         throw new ClassNotFoundException(name, e);
      }
   }

   /**
    * Search for the resource in the VFS contraining the search to the
    * class loader paths.
    * @param name - the resource name
    * @return the resource URL if found, null otherwise
    */
   public URL findResource(String name)
   {
      URL res = null;
      try
      {
         for(ClassPathVFS cp : classpath)
         {
            VirtualFile vf = cp.vfs.resolveFile(name, cp.searchCtxs);
            if( vf != null )
            {
               res = vf.toURL();
               break;
            }
         }
      }
      catch (IOException e)
      {
         e.printStackTrace();
      }
      return res;
   }

   /**
    * Search for the resource in the VFS contraining the search to the
    * class loader paths.
    * @param name - the resource name
    * @return A possibly empty enumeration of the matching resources
    */
   public Enumeration<URL> findResources(String name) throws IOException
   {
      Vector<URL> resources = new Vector<URL>();
      /*for(ClassPathVFS cp : classpath)
      {
         List<VirtualFile> matches = null;//cp.vfs.resolveFiles(name, cp.searchCtxs);
         for(VirtualFile vf : matches)
         {
            URL resURL = vf.toURL();
            resources.add(resURL);
         }
      }*/
      return resources.elements();
   }

   public ClassLoadingDomain getDomain()
   {
      return null;
   }
   public void setDomain(ClassLoadingDomain domain)
   {
   }

   public Class loadClassLocally(String name, boolean resolve)
      throws ClassNotFoundException
   {
      return findClass(name);
   }

   public URL loadResourceLocally(String name)
   {
      return this.findResource(name);
   }

   /**
    * Get the packages defined by the classloader
    * 
    * @return the packages
    */
   public Package[] getPackages()
   {
      return super.getPackages();
   }

   /**
    * Get a package defined by the classloader
    * 
    * @param name the name of the package
    * @return the package
    */
   public Package getPackage(String name)
   {
      return super.getPackage(name);
   }
}
