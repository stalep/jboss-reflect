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
package org.jboss.metadata.generic;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.retrieval.MetaDataItem;

/**
 * GenericMatcher.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public final class GenericMatcher
{
   /**
    * Match an object class
    * 
    * @param objects the objects
    * @param name the type name
    * @return the matched object or null if no match
    */
   @SuppressWarnings("unchecked")
   public static final Object matchObject(Object[] objects, String name)
   {
      if (objects != null)
      {
         for (Object o : objects)
         {
            if (o instanceof Annotation)
            {
               Annotation a = (Annotation) o;
               if (name.equals(a.annotationType().getName()))
                  return o;
            }
            else if (name.equals(o.getClass().getName()))
               return o;
         }
      }
      
      return null;
   }

   /**
    * Match a meta data item
    * 
    * @param metaDatas the meta data items
    * @param name the type name
    * @return the matched meta data item or null if no match
    */
   public static final MetaDataItem matchMetaDataItem(MetaDataItem[] metaDatas, String name)
   {
      if (metaDatas != null)
      {
         for (MetaDataItem item : metaDatas)
         {
            Object o = item.getValue();
            if (o instanceof Annotation)
            {
               Annotation a = (Annotation) o;
               if (name.equals(a.annotationType().getName()))
                  return item;
            }
            else if (name.equals(item.getName()))
               return item;
         }
      }
      
      return null;
   }
}
