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
package org.jboss.virtual.plugins.vfs.helpers;

import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.VisitorAttributes;

/**
 * Matches a suffix recursively
 * 
 * @author Scott.Stark@jboss.org
 * @author adrian@jboss.org
 * @version $Revision: 44223 $
 */
public class SuffixMatchFilter extends AbstractVirtualFileFilterWithAttributes
{
   /** The suffix */
   private String suffix;
   
   /**
    * Create a new SuffixMatchFilter,
    * using {@link VisitorAttributes#RECURSE_LEAVES_ONLY}
    * 
    * @param suffix the suffix
    * @throws IllegalArgumentException for a null suffix
    */
   public SuffixMatchFilter(String suffix)
   {
      this(suffix, null);
   }
   
   /**
    * Create a new SuffixMatchFilter.
    * 
    * @param suffix the suffix
    * @param attributes the attributes, pass null to use {@link VisitorAttributes#RECURSE_LEAVES_ONLY}
    * @throws IllegalArgumentException for a null suffix
    */
   public SuffixMatchFilter(String suffix, VisitorAttributes attributes)
   {
      super(attributes == null ? VisitorAttributes.RECURSE_LEAVES_ONLY : attributes);
      if (suffix == null)
         throw new IllegalArgumentException("Null suffix");
      this.suffix = suffix;
   }

   public boolean accepts(VirtualFile file)
   {
      String name = file.getName();
      return name.endsWith(suffix);
   }
}
