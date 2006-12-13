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
package org.jboss.metadata.spi.stack;

import java.util.ArrayList;

import org.jboss.metadata.spi.MetaData;

/**
 * MetaDataStack.
 * 
 * TODO add some security to the mutable operations
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public final class MetaDataStack
{
   /** The stack */
   private static ThreadLocal<ArrayList<MetaData>> stack = new ThreadLocal<ArrayList<MetaData>>()
   {
      protected ArrayList<MetaData> initialValue()
      {
         return new ArrayList<MetaData>();
      }
   };
   
   /**
    * Push some meta data
    * 
    * @param metaData the meta data
    */
   public static void push(MetaData metaData)
   {
      /** TODO MetaDataStackPermission
      if (metaData == null)
         throw new IllegalArgumentException("Null meta data");
      */
      
      SecurityManager sm = System.getSecurityManager();
      if (sm != null)
         sm.checkPermission(MetaDataStackPermission.MODIFY);
      
      stack.get().add(metaData);
   }
   
   /**
    * Pop some meta data
    */
   public static void pop()
   {
      /** TODO MetaDataStackPermission
      SecurityManager sm = System.getSecurityManager();
      if (sm != null)
         sm.checkPermission(MetaDataStackPermission.MODIFY);
      */

      ArrayList<MetaData> list = stack.get();
      if (list.size() == 0)
         throw new IllegalArgumentException("Empty stack");

      list.remove(list.size()-1);
   }

   
   /**
    * Peek some meta data
    * 
    * @return the metadata or null if there isn't one
    */
   public static MetaData peek()
   {
      /** TODO MetaDataStackPermission
      SecurityManager sm = System.getSecurityManager();
      if (sm != null)
         sm.checkPermission(MetaDataStackPermission.PEEK);
      */

      ArrayList<MetaData> list = stack.get();
      if (list.size() == 0)
         return null;
      return list.get(list.size()-1);
   }
   
   /**
    * Mask the metadataa
    */
   public static void mask()
   {
      /** TODO MetaDataStackPermission
      SecurityManager sm = System.getSecurityManager();
      if (sm != null)
         sm.checkPermission(MetaDataStackPermission.MODIFY);
      */
      
      stack.get().add(null);
   }
   
   /**
    * Unmask the metadata
    */
   public static void unmask()
   {
      pop();
   }
}
