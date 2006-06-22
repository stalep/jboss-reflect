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
package org.jboss.metadata.spi.retrieval.basic;

import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.retrieval.MetaDataItem;

/**
 * BasicMetaDataItem.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicMetaDataItem<T> extends BasicItem<T> implements MetaDataItem<T>
{
   /** The meta data */
   private T metaData;
   
   /** The name */
   private String name;
   
   /**
    * Create a new BaiscMetaDataItem.
    * 
    * @param loader the loader
    * @param name the name
    * @param metaData the metadata
    */
   public BasicMetaDataItem(MetaDataLoader loader, String name, T metaData)
   {
      super(loader);

      if (name == null)
         throw new IllegalArgumentException("Null name");
      
      this.name = name;
      this.metaData = metaData;
   }

   public T getValue()
   {
      return metaData;
   }
   
   public String getName()
   {
      return name;
   }

   public boolean equals(Object object)
   {
      if (object == this)
         return true;
      if (object == null || object instanceof MetaDataItem == false)
         return false;
      
      MetaDataItem other = (MetaDataItem) object;
      return getName().equals(other.getName());
   }
   
   public int hashCode()
   {
      return getName().hashCode();
   }
   
   public String toString()
   {
      return getValue().toString();
   }
}
