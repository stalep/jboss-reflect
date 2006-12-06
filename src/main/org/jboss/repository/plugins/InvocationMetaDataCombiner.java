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
package org.jboss.repository.plugins;

import org.jboss.repository.spi.MetaDataCombiner;
import org.jboss.repository.spi.Key;
import org.jboss.repository.spi.MetaData;

/**
 @author Scott.Stark@jboss.org
 @version $Revision$ */
public class InvocationMetaDataCombiner
   implements MetaDataCombiner
{
   private MetaDataCombiner next;
   private Object context;

   public InvocationMetaDataCombiner(MetaDataCombiner next, Object context)
   {
      this.next = next;
      this.context = context;
   }

   protected Object getContext()
   {
      return context;
   }
   
   public MetaDataCombiner getNext()
   {
      return next;
   }
   public void setNext(MetaDataCombiner next)
   {
      this.next = next;
   }

   public Object combine(Key key, MetaData[] levelData)
   {
      Object value = null;
      if( value != null )
         return value;
      value = next.combine(key, levelData);
      return value;
   }

}
