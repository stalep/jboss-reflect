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

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;

/**
 * BasicMetaDatasItem.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BasicMetaDatasItem extends BasicItem<Object[]> implements MetaDatasItem
{
   /** The meta data items */
   private MetaDataItem<?>[] metaDataItems;
   
   /** The meta data */
   private Object[] metaDatas;
   
   /**
    * Create a new BasicMetaDatasItem.
    *
    * @param loader the loader
    * @param metaDataItems the meta data items
    */
   public BasicMetaDatasItem(MetaDataLoader loader, MetaDataItem<?>[] metaDataItems)
   {
      super(loader);
      
      if (metaDataItems == null)
         throw new IllegalArgumentException("Null metaDataItems");
      
      this.metaDataItems = metaDataItems;
      
      if (metaDataItems.length == 0)
         metaDatas = MetaData.NO_METADATA;
   }

   public Object[] getValue()
   {
      if (metaDatas == null)
      {
         Object[] temp = new Object[metaDataItems.length];
         for (int i = 0; i < temp.length; ++i)
            temp[i] = metaDataItems[i].getValue();
         metaDatas = temp;
      }
      return metaDatas;
   }

   public MetaDataItem<?>[] getMetaDatas()
   {
      return metaDataItems;
   }

   public boolean isCachable()
   {
      if (super.isCachable() == false)
         return false;
      
      for (MetaDataItem<?> item : metaDataItems)
      {
         if (item.isCachable() == false)
            return false;
      }
      
      return true;
   }

   public boolean isValid()
   {
      if (super.isValid() == false)
         return false;
      
      for (MetaDataItem<?> item : metaDataItems)
      {
         if (item.isValid() == false)
            return false;
      }
      
      return true;
   }
}
