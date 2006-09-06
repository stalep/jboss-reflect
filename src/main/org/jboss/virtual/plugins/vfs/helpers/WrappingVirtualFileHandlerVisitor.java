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
package org.jboss.virtual.plugins.vfs.helpers;

import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.VirtualFileVisitor;
import org.jboss.virtual.VisitorAttributes;
import org.jboss.virtual.spi.VirtualFileHandler;
import org.jboss.virtual.spi.VirtualFileHandlerVisitor;

/**
 * A handler visitor that wraps a normal file visitor
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class WrappingVirtualFileHandlerVisitor implements VirtualFileHandlerVisitor
{
   /** The wrapped visitor */
   private final VirtualFileVisitor visitor;
   
   /**
    * Create a new WrappingVirtualFileHandlerVisitor.
    * 
    * @param visitor the visitor
    * @throws IllegalArgumentException if the visitor is null
    */
   public WrappingVirtualFileHandlerVisitor(VirtualFileVisitor visitor)
   {
      if (visitor == null)
         throw new IllegalArgumentException("Null visitor");
      this.visitor = visitor;
   }
   
   public VisitorAttributes getAttributes()
   {
      return visitor.getAttributes();
   }

   public void visit(VirtualFileHandler handler)
   {
      VirtualFile file  = handler.getVirtualFile();
      visitor.visit(file);
   }
}
