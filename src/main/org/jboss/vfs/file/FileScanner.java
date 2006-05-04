/*
  * JBoss, Home of Professional Open Source
  * Copyright 2005, JBoss Inc., and individual contributors as indicated
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

package org.jboss.vfs.file;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.jboss.logging.Logger;
import org.jboss.vfs.spi.VFSVisitor;
import org.jboss.vfs.spi.VirtualFile;

/**
 * Depth first implementation of the iterator.
 * This is not thread-safe.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
class FileScanner implements Iterator<VirtualFile>
{
   private static Logger log = Logger.getLogger(FileScanner.class);
   private ArrayList<VirtualFile> acceptedFiles = new ArrayList<VirtualFile>();
   private Iterator<VirtualFile> iter;

   /**
    * Scan the vfs starting from root and include files accepted by
    * the visitor.
    * 
    * @param root - the virtual file to start the scan from
    * @param acceptVisitor - a visitor that defines acceptance
    */
   FileScanner(VirtualFile root, VFSVisitor acceptVisitor)
   {
      if( acceptVisitor.visit(root) )
         addFile(root, acceptVisitor);
      iter = acceptedFiles.iterator();
   }

   /**
    * @return true if additional scan file exist
    */
   public boolean hasNext()
   {
      return iter.hasNext();
   }

   /**
    * Get the next virtual file in the accepted list
    * @return next accepted virtual file
    */
   public VirtualFile next()
   {
      VirtualFile vf = iter.next();
      return vf;
   }

   /**
    * This is a no-op
    */
   public void remove()
   {
   }

   private void addFile(VirtualFile file, VFSVisitor acceptVisitor)
   {
      acceptedFiles.add(file);
      VirtualFile[] children;
      try
      {
         children = file.getChildren();
      }
      catch (IOException e)
      {
         log.warn("Failed to obtain children of: "+file.getName(), e);
         return;
      }

      for(VirtualFile vf : children)
      {
         if( acceptVisitor.visit(vf) )
            addFile(vf, acceptVisitor);
      }
   }
}
