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
package org.jboss.reflect.plugins.javassist;

import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;


/**
 * SignatureKey.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
class SignatureKey
{
   /** The name */
   String name;
   
   /** The parameter names */
   String[] params;

   /** The cached hashcode */
   private transient int cachedHashCode = Integer.MIN_VALUE;
   
   /**
    * Create a new SignatureKey.
    * 
    * @param name the name
    * @param typeInfos the type infos
    */
   public SignatureKey(String name, TypeInfo[] typeInfos)
   {
      this.name = name;
      if (typeInfos != null && typeInfos.length > 0)
      {
         params = new String[typeInfos.length];
         for (int i = 0; i < typeInfos.length; ++i)
            params[i] = typeInfos[i].getName();
      }
   }
   
   /**
    * Create a new SignatureKey.
    * 
    * @param name the name
    * @param params the params
    */
   public SignatureKey(String name, String[] params)
   {
      this.name = name;
      this.params = params;
   }
   
   public boolean equals(Object obj)
   {
      if (obj == this)
         return true;
      if (obj == null || obj instanceof SignatureKey == false)
         return false;
      
      SignatureKey other = (SignatureKey) obj;
      
      if (name == null && other.name != null)
         return false;
      if (name != null && other.name == null)
         return false;
      if (name != null && name.equals(other.name) == false)
         return false;
      
      if (params == null && other.params == null)
         return true;
      if (params == null && other.params != null)
         return false;
      if (params != null && other.params == null)
         return false;
      
      if (params.length != other.params.length)
         return false;
      
      for (int i = 0; i < params.length; ++i)
      {
         if (params[i].equals(other.params[i]) == false)
            return false;
      }
      return true;
   }
   
   public int hashCode()
   {
      if (cachedHashCode == Integer.MIN_VALUE)
      {
         JBossStringBuilder builder = new JBossStringBuilder();
         if (name != null)
            builder.append(name);
         if (params != null)
         {
            for (int i = 0; i < params.length; ++i)
               builder.append(params[i]);
         }
         cachedHashCode = builder.toString().hashCode();
      }
      return cachedHashCode;
   }
}
