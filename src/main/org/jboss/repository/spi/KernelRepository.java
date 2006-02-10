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
package org.jboss.repository.spi;

import java.util.Iterator;
import java.util.Map;

/**
 @author Scott.Stark@jboss.org
 @version $Revision$
 */
public interface KernelRepository
{
   // Data access ------------------------------
   public Object getMetaData(Key key);

   public Object getMetaData(Key key, MetaDataCombiner combiner);

   /**
    Get the MetaData for the key and all its subkeys
    @param key
    @return Map<Key, MetaData> for the key and matching subkeys
    */
   public Map getAllMetaData(Key key);

   /**
    @param key
    @param data
    */
   public MetaData addMetaData(Key key, MetaData data);

   public MetaData removeMetaData(Key key);

   /**
    @param loader
    */
   public void loadMetaData(MetaDataLoader loader);

   // Key info ---------------------------------

   /**
    Get the current repository keys
    @return Iterator<Key> of the repository keys
    */
   public Iterator getKeyNames();

   /**
    Query the repository for keys matching the name regular expression and
    attribute value regular expressions.
    @param nameRE
    @param attributes
    @return Iterator<Key> of the matching keys
    */
   public Iterator findKeys(String nameRE, Map attributes);
}
