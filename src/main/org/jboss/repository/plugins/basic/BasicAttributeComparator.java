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

/*
 * JBoss, Home of Professional Open Source
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */

package org.jboss.repository.plugins.basic;

import java.util.Comparator;
import java.util.HashMap;

import org.jboss.repository.spi.CommonNames;

/**
 @author Scott.Stark@jboss.org
 @version $Revision$
 */
public class BasicAttributeComparator
   implements Comparator
{
   private static String[] BASIC_ORDERING = {
      CommonNames.THREAD,
      CommonNames.REQUEST,
      CommonNames.SESSION,
      CommonNames.DEPLOYMENT,
      CommonNames.APPLICATION,
      CommonNames.SERVER,
      CommonNames.CLUSTER,
      CommonNames.DOMAIN
   };
   private static HashMap BASIC_ORDER_INDEXES = new HashMap();
   static
   {
      for(int n = 0; n < BASIC_ORDERING.length; n ++)
         BASIC_ORDER_INDEXES.put(BASIC_ORDERING[n], new Integer(n));
   }

   public int compare(Object obj1, Object obj2)
   {
      String name1 = (String) obj1;
      String name2 = (String) obj2;
      Integer index1 = (Integer) BASIC_ORDER_INDEXES.get(name1);
      Integer index2 = (Integer) BASIC_ORDER_INDEXES.get(name2);
      int compare = 0;
      if( index1 != null )
      {
         if( index2 != null )
            compare = index1.compareTo(index2);
         else
            compare = -1;
      }
      else if( index2 != null )
      {
         // 
         compare = 1;
      }
      else
      {
         // Neither attribute is a standard one
         compare = name1.compareTo(name2);
      }
      return compare;
   }

}
