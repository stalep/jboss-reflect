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
import java.net.MalformedURLException;
import java.net.URL;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.SecureClassLoader;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

import org.jboss.classloading.spi.ClassLoadingDomain;
import org.jboss.classloading.spi.DomainClassLoader;
import org.jboss.logging.Logger;
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
   private static Logger log = Logger.getLogger(VFSClassLoader.class);

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
    * Create a class loader given a search path VFS, and default parent class
    * loader.
    * @param searchCtxs - the paths from the VFS that make up the class loader path
    * @param vfs - the VFS used to resolve and load classes and resources
    */
   public VFSClassLoader(String[] searchCtxs, ReadOnlyVFS vfs)
   {
      ClassPathVFS cp  = new ClassPathVFS(searchCtxs, vfs);
      classpath.add(cp);
   }
   /**
    * Create a class loader given a search path VFS, and given parent class
    * loader.
    * @param searchCtxs - the paths from the VFS that make up the class loader path
    * @param vfs - the VFS used to resolve and load classes and resources
    * @param parent - the parent class loader to use
    */
   public VFSClassLoader(String[] searchCtxs, ReadOnlyVFS vfs, ClassLoader parent)
   {
      super(parent);
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
      VirtualFile classFile = findResourceFile(resName+".class");
      if( classFile == null )
         throw new ClassNotFoundException(name);
      try
      {
         byte[] tmp = new byte[128];
         ByteArrayOutputStream baos = new ByteArrayOutputStream();
         InputStream is = classFile.openStream();
         int length;
         while ((length = is.read(tmp)) > 0)
         {
            baos.write(tmp, 0, length);
         }
         is.close();
         tmp = baos.toByteArray();
         ProtectionDomain pd = getProtectionDomain(classFile);
         Class c = super.defineClass(name, tmp, 0, tmp.length, pd);
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
      VirtualFile vf = findResourceFile(name);
      if( vf != null )
      {
         try
         {
            res = vf.toURL();
         }
         catch(IOException e)
         {
            if( log.isTraceEnabled() )
               log.trace("Failed to obtain vf URL: "+vf, e);
         }
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

   protected VirtualFile findResourceFile(String name)
   {
      VirtualFile vf = null;
      try
      {
         for(ClassPathVFS cp : classpath)
         {
            vf = cp.vfs.resolveFile(name, cp.searchCtxs);
            if( vf != null )
            {
               break;
            }
         }
      }
      catch (IOException e)
      {
         if( log.isTraceEnabled() )
            log.trace("Failed to find resource: "+name, e);
      }
      return vf;
   }

   /**
    * Determine the protection domain. If we are a copy of the original
    * deployment, use the original url as the codebase.
    * @return the protection domain
    * @throws MalformedURLException 
    * @todo certificates and principles?
    */
   protected ProtectionDomain getProtectionDomain(VirtualFile classFile)
      throws MalformedURLException
   {
      Certificate certs[] = null;
      URL codesourceUrl = classFile.toURL();
      CodeSource cs = new CodeSource(codesourceUrl, certs);
      PermissionCollection permissions = SecurityActions.getPolicy().getPermissions(cs);
      if (log.isTraceEnabled())
         log.trace("getProtectionDomain, url=" + codesourceUrl +
                   " codeSource=" + cs + " permissions=" + permissions);
      return new ProtectionDomain(cs, permissions);
   }
}
