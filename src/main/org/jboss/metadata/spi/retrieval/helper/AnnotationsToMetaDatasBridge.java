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
package org.jboss.metadata.spi.retrieval.helper;

import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;

/**
 * AnnotationsToMetaDatasBridge.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AnnotationsToMetaDatasBridge implements MetaDatasItem
{
   /** The annotations */
   private AnnotationsItem annotations;

   /**
    * Create a new AnnotationsToMetaDatasBridge.
    * 
    * @param annotations the annotations
    */
   public AnnotationsToMetaDatasBridge(AnnotationsItem annotations)
   {
      if (annotations == null)
         throw new IllegalArgumentException("Null annotations");
      this.annotations = annotations;
   }

   public boolean isCachable()
   {
      return annotations.isCachable();
   }

   public boolean isValid()
   {
      return annotations.isValid();
   }

   @SuppressWarnings("unchecked")
   public Object[] getValue()
   {
      return annotations.getValue();
   }

   public MetaDataItem<?>[] getMetaDatas()
   {
      return annotations.getAnnotations();
   }
}
