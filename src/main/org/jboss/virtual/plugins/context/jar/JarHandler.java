/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.virtual.plugins.context.jar;

import java.io.IOException;
import java.io.InputStream;
import java.net.JarURLConnection;
import java.net.URL;

import org.jboss.virtual.spi.VFSContext;
import org.jboss.virtual.spi.VirtualFileHandler;

/**
 * JarHandler.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class JarHandler extends AbstractJarHandler
{
   /** serialVersionUID */
   private static final long serialVersionUID = 1L;

   /**
    * Create a new JarHandler.
    * 
    * @param context the context
    * @param parent the parent
    * @param url the url
    * @param name the name
    * @throws IOException for an error accessing the file system
    * @throws IllegalArgumentException for a null context, url or vfsPath
    */
   public JarHandler(VFSContext context, VirtualFileHandler parent, URL url, String name) throws IOException
   {
      super(context, parent, url, name);

      try
      {
         JarURLConnection connection =  (JarURLConnection) url.openConnection();
         initJarFile(connection.getJarFile());
      }
      catch (IOException original)
      {
         // Fix the context of the error message
         IOException e = new IOException("Error opening jar file: " + url + " reason=" + original.getMessage());
         e.setStackTrace(original.getStackTrace());
         throw e;
      }
   }

   /**
    * Override to return the input stream of the underlying url rather than
    * a JarInputStream for the jar as this is not usable for copying the
    * jar.
    */
   @Override
   public InputStream openStream() throws IOException
   {
      checkClosed();
      String jarURL = getURL().toString();
      String underlyingURL = jarURL.substring(4, jarURL.length()-2);
      URL realURL = new URL(underlyingURL);
      InputStream is = realURL.openStream();
      return is;
   }
}
