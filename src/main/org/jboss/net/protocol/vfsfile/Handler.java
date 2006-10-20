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
package org.jboss.net.protocol.vfsfile;

import org.jboss.virtual.VFS;
import org.jboss.virtual.VirtualFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLStreamHandler;

/**
 * URLStreamHandler for VFS
 *
 * @author <a href="bill@jboss.com">Bill Burke</a>
 * @version $Revision: 1.1 $
 */
public class Handler extends URLStreamHandler
{

   protected URLConnection openConnection(URL u) throws IOException
   {
      String file = u.toString().substring(8); // strip out vfsfile:
      File fp = new File(file);

      File parent = fp;
      File curr = fp;
      String relative = fp.getName();
      while ((curr = curr.getParentFile()) != null)
      {
         parent = curr;
         if (parent.getParentFile() != null) relative = parent.getName() + "/" + relative;
      }

      URL url = parent.toURL();

      VFS vfs = VFS.getVFS(url);
      VirtualFile vf = vfs.findChild(relative);


      return new VirtualFileURLConnection(url, vf);
   }


   public static void main(String[] args) throws Exception
   {
      System.setProperty("java.protocol.handler.pkgs", "org.jboss.net.protocol");
      //URL url = new URL("vfsfile:/c:/tmp/urlstream.java");
      //URL url = new URL("vfsfile:/C:\\jboss\\jboss-head\\build\\output\\jboss-5.0.0.Beta\\server\\default\\lib\\jboss.jar\\schema\\xml.xsd");
      URL url = new URL("vfsfile:/c:/tmp/parent.jar/foo.jar/urlstream.java");
      InputStream is = url.openStream();
      while (is.available() != 0)
      {
         System.out.print((char)is.read());
      }
      is.close();

   }
}
