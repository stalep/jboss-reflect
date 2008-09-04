/*
* JBoss, Home of Professional Open Source
* Copyright 2007, JBoss Inc., and individual contributors as indicated
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
package org.jboss.reflect.plugins;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * TypeInfoAttachments.
 *
 * TODO add some security on who can add attachments
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class TypeInfoAttachments
{
   /** The attachments */
   private transient Map<String, Object> attachments;

   /**
    * Set an attachment against the type.
    * This is useful for caching information against a type.<p>
    *
    * If you add a future object, subsequent gets will wait for the result<p>
    * 
    * WARNING: Be careful about what you put in here. Don't create
    * references across classloaders, if you are not sure add a WeakReference
    * to the information.
    * 
    * @param name the name
    * @param attachment the attachment, pass null to remove an attachment
    * @throws IllegalArgumentException for a null name
    */
   public void addAttachment(String name, Object attachment)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      if (attachment == null)
         return;
      synchronized (this)
      {
         if (attachments == null)
            attachments = new HashMap<String, Object>();
         attachments.put(name, attachment);
      }
   }

   /**
    * Remove an attachment
    * 
    * @param name the name
    * @throws IllegalArgumentException for a null name
    */
   public void removeAttachment(String name)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      synchronized (this)
      {
         if (attachments == null)
            return;
         attachments.remove(name);
      }
   }

   /**
    * Get an attachment from the type
    * 
    * @param name the name
    * @return the attachment
    */
   public Object getAttachment(String name)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      Object result = null;
      synchronized (this)
      {
         if (attachments == null)
            return null;
         result = attachments.get(name);
      }
      if (result == null)
         return null;
      
      // Special case if the attachment is a future object
      if (result instanceof Future)
      {
         try
         {
            return ((Future<?>) result).get();
         }
         catch (RuntimeException e)
         {
            throw e;
         }
         catch (Exception e)
         {
            throw new RuntimeException("Error getting attachment from future " + result, e);
         }
      }
      return result;
   }
}
