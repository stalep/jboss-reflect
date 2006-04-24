/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.vfs;

import java.net.URL;
import java.util.HashMap;
import java.util.Enumeration;
import java.util.ArrayList;
import java.io.IOException;
import java.io.InputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * A singleton factory for locating VFSFactory instances given VFS root URLs.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class VFSFactoryLocator
{
   /** The VSFactory mapped keyed by the VFS protocol string */
   private static HashMap<String, VFSFactory> factoryByProtocol = new HashMap<String, VFSFactory>();
   /** The default file protocol faactory */
   private static final String FILE_FACTORY = "org.jboss.vfs.file.DefaultVFSFactory";
   /** Has the default initialzation been performed */
   private static boolean initialized;

   public synchronized static void registerFactory(VFSFactory factory)
   {
      String[] protocols = factory.getProtocols();
      for(int n = 0; n < protocols.length; n ++)
      {
         String protocol = protocols[n];
         VFSFactory prevFactory = factoryByProtocol.put(protocol, factory);

      }
   }

   /**
    * Return the VFSFactory for the VFS mount point specified by the
    * rootURL.
    *  
    * @param rootURL - the URL to a VFS root
    * @return the VFSFactory capable of handling the rootURL. This will be null
    * if there is no factory registered for the rootURL protocol.
    */
   public synchronized static VFSFactory getFactory(URL rootURL)
      throws Exception
   {
      if( initialized == false )
         init();
      String protocol = rootURL.getProtocol();
      VFSFactory factory = factoryByProtocol.get(protocol);
      return factory;
   }

   /**
    * Load the default protocol VFS factories by searching for the
    * META-INF/services/org.jboss.vfs.VFSFactory service provider config files
    * using the thread context class loader. If that fails to locate a resource,
    * default to the file
    */
   private static synchronized void init()
      throws Exception
   {
      // Try to locate
      ClassLoader loader = Thread.currentThread().getContextClassLoader();
      Enumeration urls = loader.getResources("META-INF/services/org.jboss.vfs.VFSFactory");
      while( urls.hasMoreElements() )
      {
         URL url = (URL) urls.nextElement();
         VFSFactory[] factories = loadFactories(url, loader);
         for(int n = 0; n < factories.length; n ++)
         {
            VFSFactory factory = factories[n];
            registerFactory(factory);
         }
      }

      // If there are no factories load the FILE_FACTORY
      if( factoryByProtocol.size() == 0 )
      {
         Class<VFSFactory> c = (Class<VFSFactory>) loader.loadClass(FILE_FACTORY);
         VFSFactory factory = c.newInstance();
         registerFactory(factory);
      }
      initialized = true;
   }

   /**
    * Load the VFSFactory classes found in the service file
    * @param serviceURL
    * @param loader
    * @return A possibly zero length array of the VFSFactory instances
    *    loaded from the serviceURL
    */
   private static VFSFactory[] loadFactories(URL serviceURL, ClassLoader loader)
   {
      ArrayList tmp = new ArrayList();
      try
      {
         InputStream is = serviceURL.openStream();
         BufferedReader br = new BufferedReader(new InputStreamReader(is));
         String line;
         while( (line = br.readLine()) != null )
         {
            if( line.startsWith("#") == true )
               continue;
            String[] classes = line.split("\\s+|#.*");
            for(int n = 0; n < classes.length; n ++)
            {
               String name = classes[n];
               if( name.length() == 0 )
                  continue;
               Class factoryClass = loader.loadClass(name);
               VFSFactory factory = (VFSFactory) factoryClass.newInstance();
               tmp.add(factory);
            }
         }
      }
      catch(Exception e)
      {
         
      }
      VFSFactory[] factories = new VFSFactory[tmp.size()];
      tmp.toArray(factories);
      return factories;
   }
}
