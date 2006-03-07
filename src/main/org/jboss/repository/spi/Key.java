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

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Collections;
import java.util.TreeSet;
import java.util.Iterator;

import org.jboss.repository.spi.CommonNames;
import org.jboss.util.JBossStringBuilder;

/**
 @author Scott.Stark@jboss.org
 @version $Revision$
 */
public class Key implements Comparable
{
   private String name;
   private Map attributes;
   private int level;

   public Key(String nameExpr)
      throws ParseException
   {
      parseName(nameExpr);
      defineLevel();
   }
   public Key(String name, Map attributes)
   {
      this.name = name;
      this.attributes = attributes;
      defineLevel();
   }

   public String getName()
   {
      return name;
   }
   public int getLevel()
   {
      return level;
   }
   public Map getAttributes()
   {
      return Collections.unmodifiableMap(attributes);
   }


   public int compareTo(Object obj)
   {
      if( obj instanceof Key == false )
         throw new ClassCastException("Argument is not a Key, type="+obj.getClass());

      Key key = (Key) obj;
      int compare = name.compareTo(key.name);
      if( compare == 0 )
      {
         Map keyAttrs = key.attributes;
         if( attributes == null )
            compare = keyAttrs == null ? 0 : keyAttrs.size();
         else if( keyAttrs == null )
            compare = attributes == null ? 0 : attributes.size();
         else
         {
            TreeSet set = new TreeSet(attributes.keySet());
            set.addAll(keyAttrs.keySet());
            Iterator keys = set.iterator();
            while( keys.hasNext() && compare == 0 )
            {
               String key1 = (String) keys.next();
               String value1 = (String) attributes.get(key1);
               String value2 = (String) keyAttrs.get(key1);
               
               if (value1 == null && value2 == null)
                  compare = 0;
               if( value1 == null )
                  compare = -1;
               else if( value2 == null )
                  compare = 1;
               else
                  compare = value1.compareTo(value2);
            }
         }
      }
      return compare;
   }
   public boolean equals(Object obj)
   {
      return compareTo(obj) == 0;
   }
   public int hashCode()
   {
      int hashCode = name.hashCode() + attributes.hashCode();
      return hashCode;
   }

   protected void defineLevel()
   {
      level = CommonNames.DOMAIN_LEVEL;
      if( attributes.containsKey(CommonNames.DOMAIN) )
         level = CommonNames.DOMAIN_LEVEL;
      if( attributes.containsKey(CommonNames.CLUSTER) )
         level = CommonNames.CLUSTER_LEVEL;
      if( attributes.containsKey(CommonNames.SERVER) )
         level = CommonNames.SERVER_LEVEL;
      if( attributes.containsKey(CommonNames.APPLICATION) )
         level = CommonNames.APPLICATION_LEVEL;
      if( attributes.containsKey(CommonNames.DEPLOYMENT) )
         level = CommonNames.DEPLOYMENT_LEVEL;
      if( attributes.containsKey(CommonNames.SESSION) )
         level = CommonNames.SESSION_LEVEL;
   }
   
   protected void parseName(String nameExpr)
      throws ParseException
   {
      int colon = nameExpr.indexOf(':');
      if( colon < 0 )
         name = nameExpr;
      else
      {
         name = nameExpr.substring(0, colon);
         // Parse the key=value pairs
         StringTokenizer tokenizer = new StringTokenizer(nameExpr.substring(colon + 1), ",=");
         while( tokenizer.hasMoreTokens() )
         {
            String key = tokenizer.nextToken();
            if( tokenizer.hasMoreTokens() == false )
            {
               throw new ParseException("No value for key: "+key, attributes.size());
            }
            String value = tokenizer.nextToken();
            
            if (attributes == null)
            {
               attributes = new HashMap();
            }
            attributes.put(key, value);
         }
      }
   }
   
   public String toString()
   {
      JBossStringBuilder sb = new JBossStringBuilder();
      sb.append("[Key[");
      sb.append(name);
      sb.append(":");
      
      boolean first = true;
      for (Iterator it = attributes.keySet().iterator() ; it.hasNext() ; )
      {
         Object key = it.next();
         
         if (first)
         {
            first = false;
         }
         else
         {
            sb.append(", ");
         }
         
         sb.append(key);
         sb.append("=");
         sb.append(attributes.get(key));
      }
      
      sb.append("]]");
      return sb.toString();
   }

}
