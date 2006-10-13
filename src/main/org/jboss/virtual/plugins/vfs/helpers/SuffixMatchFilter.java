/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jboss.virtual.VirtualFile;
import org.jboss.virtual.VisitorAttributes;

/**
 * Matches a file name against a list of suffixes. 
 * 
 * @author Scott.Stark@jboss.org
 * @author adrian@jboss.org
 * @version $Revision: 44223 $
 */
public class SuffixMatchFilter extends AbstractVirtualFileFilterWithAttributes
{
   /** The suffixes */
   private List<String> suffixes;
   
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
      this(Collections.EMPTY_LIST, attributes);
      suffixes.add(suffix);
   }
   /**
    * Create a new SuffixMatchFilter.
    * @param suffixes - the list of file suffixes to accept.
    * @throws IllegalArgumentException for a null suffixes
    */
   public SuffixMatchFilter(List<String> suffixes)
   {
      this(suffixes, null);
   }
   /**
    * Create a new SuffixMatchFilter.
    * @param suffixes - the list of file suffixes to accept.
    * @param attributes the attributes, pass null to use {@link VisitorAttributes#RECURSE_LEAVES_ONLY}
    * @throws IllegalArgumentException for a null suffixes
    */
   public SuffixMatchFilter(List<String> suffixes, VisitorAttributes attributes)
   {
      super(attributes == null ? VisitorAttributes.RECURSE_LEAVES_ONLY : attributes);
      if (suffixes == null)
         throw new IllegalArgumentException("Null suffixes");
      this.suffixes = new ArrayList<String>();
      this.suffixes.addAll(suffixes);
   }

   /**
    * Accept any file that ends with one of the filter suffixes. This checks
    * that the file.getName() endsWith a suffix.
    * @return true if the file matches a suffix, false otherwise.
    */
   public boolean accepts(VirtualFile file)
   {
      String name = file.getName();
      for (int i = 0; i < suffixes.size(); ++i)
      {
         if (name.endsWith(suffixes.get(0)))
            return true;
      }
      return false;
   }
}
