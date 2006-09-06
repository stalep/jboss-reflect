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

import org.jboss.virtual.VisitorAttributes;
import org.jboss.virtual.spi.VirtualFileHandlerVisitor;

/**
 * AbstractVirtualFileVisitor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractVirtualFileHandlerVisitor implements VirtualFileHandlerVisitor
{
   private final VisitorAttributes attributes;

   /**
    * Create a new AbstractVirtualFileVisitor using the default visitor attributes
    */
   protected AbstractVirtualFileHandlerVisitor()
   {
      this(VisitorAttributes.DEFAULT);
   }

   /**
    * Create a new AbstractVirtualFileVisitor using the default visitor attributes
    * 
    * @param attributes the attributes
    * @throws IllegalArgumentException if the attributes are null
    */
   protected AbstractVirtualFileHandlerVisitor(VisitorAttributes attributes)
   {
      if (attributes == null)
         throw new IllegalArgumentException("Null attributes");
      this.attributes = attributes;
   }
   
   public VisitorAttributes getAttributes()
   {
      return attributes;
   }
}
