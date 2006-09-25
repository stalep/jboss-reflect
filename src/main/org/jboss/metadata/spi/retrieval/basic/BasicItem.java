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

import java.util.concurrent.atomic.AtomicBoolean;

import org.jboss.metadata.spi.loader.MetaDataLoader;
import org.jboss.metadata.spi.retrieval.Item;

/**
 * BasicItem.
 * 
 * @param <T> The item type
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class BasicItem<T> implements Item<T>
{
   /** The loader */
   private MetaDataLoader loader;

   /** Whether this is valid */
   private AtomicBoolean valid = new AtomicBoolean(true);

   /**
    * Create a new BasicItem.
    * 
    * @param loader the loader
    */
   public BasicItem(MetaDataLoader loader)
   {
      this.loader = loader;
   }
   
   public boolean isCachable()
   {
      return loader.isCachable(this);
   }

   public boolean isValid()
   {
      return valid.get();
   }
   
   public void invalidate()
   {
      this.valid.set(false);
   }
}
