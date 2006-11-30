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

import java.util.Iterator;
import java.util.Map;
import java.util.HashMap;

import org.jboss.repository.spi.KernelRepository;
import org.jboss.repository.spi.MetaDataCombiner;
import org.jboss.repository.spi.Key;
import org.jboss.repository.spi.MetaData;
import org.jboss.repository.spi.MetaDataLoader;
import org.jboss.repository.spi.CommonNames;

/**
 @todo update for synchronization and concurrent reader maps

 @author Scott.Stark@jboss.org
 @version $Revision$
 */
public class AbstractKernelRepository
   implements KernelRepository
{
   private Map<Key, MetaData> repository = new HashMap<Key, MetaData>(103);
   private Map<Key, MetaData> domainMap = new HashMap<Key, MetaData>(103);
   private Map<Key, MetaData> clusterMap = new HashMap<Key, MetaData>(103);
   private Map<Key, MetaData> serverMap = new HashMap<Key, MetaData>(103);
   private Map<Key, MetaData> appMap = new HashMap<Key, MetaData>(103);
   private Map<Key, MetaData> deployMap = new HashMap<Key, MetaData>(103);
   private Map<Key, MetaData> sessionMap = new HashMap<Key, MetaData>(103);
   private Map maps[] = {
      domainMap,
      clusterMap,
      serverMap,
      appMap,
      deployMap,
      sessionMap
   };

   private MetaDataCombiner combiner;

   public AbstractKernelRepository(MetaDataCombiner combiner)
      throws Exception
   {
      super();
      this.combiner = combiner;
   }

   public Object getMetaData(Key key)
   {
      return getMetaData(key, combiner);
   }

   /**
    @todo should the loader be used to attempt to load data if the requested
      name does not currently exist?

    @param key
    @param combiner
    @return the metadata object for name if found, null otherwise
    */
   public Object getMetaData(Key key, MetaDataCombiner combiner)
   {
      // Should the loader be used here?
      Map attributes = key.getAttributes();
      HashMap<String, String> tmp = new HashMap<String, String>();
      MetaData[] levelData = new MetaData[CommonNames.N_LEVELS];
      int level = key.getLevel();
      for(int n = 0; n <= level; n ++)
      {
         String levelKey = CommonNames.LEVELS[n];
         String value = (String) attributes.get(levelKey);
         tmp.put(levelKey, value);
         Key tmpKey = new Key(key.getName(), tmp);
         levelData[n] = (MetaData) maps[n].get(tmpKey);
      }

      return combiner.combine(key, levelData);
   }

   /**
    Get the MetaData for the key and all its subkeys
    @param key
    @return Map<Key, MetaData> for the key and matching subkeys
    */
   public Map getAllMetaData(Key key)
   {
       HashMap<Key, MetaData> levelData = new HashMap<Key, MetaData>();
       Map attributes = key.getAttributes();
       HashMap<String, String> tmp = new HashMap<String, String>();
       int level = key.getLevel();
       for(int n = 0; n <= level; n ++)
       {
          String levelKey = CommonNames.LEVELS[n];
          String value = (String) attributes.get(levelKey);
          tmp.put(levelKey, value);
          Key tmpKey = new Key(key.getName(), tmp);
          MetaData data = (MetaData) maps[n].get(tmpKey);
          if( data != null )
            levelData.put(tmpKey, data);
       }

       return levelData;
   }

   @SuppressWarnings("unchecked")
   public synchronized MetaData addMetaData(Key key, MetaData data)
   {
      MetaData prev = repository.put(key, data);
      // Add 
      int level = key.getLevel();
      maps[level].put(key, data);
      return prev;
   }
   public MetaData removeMetaData(Key key)
   {
      int level = key.getLevel();
      MetaData prev = (MetaData) maps[level].remove(key);
      repository.remove(key);
      return prev;
   }

   @SuppressWarnings("unchecked")
   public void loadMetaData(MetaDataLoader loader)
   {
      Iterator keys = loader.getKeys();
      while( keys.hasNext() )
      {
         Key key = (Key) keys.next();
         MetaData data = loader.load(key);
         int level = key.getLevel();
         maps[level].put(key, data);
      }
   }

   public Iterator getKeyNames()
   {
      return repository.keySet().iterator();
   }

   /**
    @todo Not implemented.
    @param nameRE
    @param attributes
    @return null currently
    */
   public Iterator findKeys(String nameRE, Map attributes)
   {
      return null;
   }

}
