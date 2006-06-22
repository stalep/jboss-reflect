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
package org.jboss.metadata.spi.retrieval.cummulative;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleMetaDatasItem;

/**
 * CummulativeMetaDatasItem.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class CummulativeMetaDatasItem extends SimpleMetaDatasItem
{
   /** The context */
   private MetaDataContext context;
   
   /** The valid time */
   private long validTime;
   
   /**
    * Create a new CummulativeMetaDatasItem.
    * 
    * @param context the context
    */
   public CummulativeMetaDatasItem(MetaDataContext context)
   {
      if (context == null)
         throw new IllegalArgumentException("Null context");

      this.context = context;
      init(context.getValidTime().getValidTime());
   }

   public Object[] getValue()
   {
      checkValid();
      return super.getValue();
   }
   
   public MetaDataItem[] getMetaDatas()
   {
      checkValid();
      return super.getMetaDatas();
   }

   public boolean isCachable()
   {
      return true;
   }

   public boolean isValid()
   {
      return true;
   }

   /**
    * Check whether we are valid
    */
   protected void checkValid()
   {
      MetaDataItem[] items = super.getMetaDatas();
      boolean valid = (items != null);
      
      long newValidTime = context.getValidTime().getValidTime();
      if (validTime < newValidTime)
         valid = false;
      
      if (items != null)
      {
         for (MetaDataItem item : items)
         {
            if (item.isValid() == false)
               valid = false;
         }
      }
      
      if (valid == false)
         init(newValidTime);
   }

   /**
    * Initialise
    * 
    * @param validTime the valid time
    */
   protected void init(long validTime)
   {
      Set<MetaDataItem> temp = null;

      List<MetaDataRetrieval> retrievals = context.getRetrievals();
      for (MetaDataRetrieval retrieval : retrievals)
      {
         MetaDatasItem item = retrieval.retrieveMetaData();
         if (item != null)
         {
            MetaDataItem[] items = item.getMetaDatas();
            for (MetaDataItem it : items)
            {
               if (temp == null)
                  temp = new HashSet<MetaDataItem>();
               temp.add(it);
            }
         }
      }
      
      MetaDataItem[] items = NO_META_DATA_ITEMS;
      if (temp != null)
         items = temp.toArray(new MetaDataItem[temp.size()]);
      setMetaDataItems(items);
      this.validTime = validTime;
   }
}
