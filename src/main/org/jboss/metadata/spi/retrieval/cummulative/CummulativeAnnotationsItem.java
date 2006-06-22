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

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationsItem;

/**
 * CummulativeAnnotationsItem.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class CummulativeAnnotationsItem extends SimpleAnnotationsItem
{
   /** The context */
   private MetaDataContext context;
   
   /** The valid time */
   private long validTime;
   
   /**
    * Create a new CummulativeAnnotationsItem.
    * 
    * @param context the context
    */
   public CummulativeAnnotationsItem(MetaDataContext context)
   {
      if (context == null)
         throw new IllegalArgumentException("Null context");

      this.context = context;
      init(context.getValidTime().getValidTime());
   }

   public Annotation[] getValue()
   {
      checkValid();
      return super.getValue();
   }
   
   public AnnotationItem[] getAnnotations()
   {
      checkValid();
      return super.getAnnotations();
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
      AnnotationItem[] items = super.getAnnotations();
      boolean valid = (items != null);
      
      long newValidTime = context.getValidTime().getValidTime();
      if (validTime < newValidTime)
         valid = false;
      
      if (valid && items != null)
      {
         for (AnnotationItem item : items)
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
      Set<AnnotationItem> temp = null;

      List<MetaDataRetrieval> retrievals = context.getRetrievals();
      for (MetaDataRetrieval retrieval : retrievals)
      {
         AnnotationsItem item = retrieval.retrieveAnnotations();
         if (item != null)
         {
            AnnotationItem[] items = item.getAnnotations();
            for (AnnotationItem it : items)
            {
               if (temp == null)
                  temp = new HashSet<AnnotationItem>();
               temp.add(it);
            }
         }
      }
      
      AnnotationItem[] items = NO_ANNOTATION_ITEMS;
      if (temp != null)
         items = temp.toArray(new AnnotationItem[temp.size()]);
      setAnnotationItems(items);
      this.validTime = validTime;
   }
}
