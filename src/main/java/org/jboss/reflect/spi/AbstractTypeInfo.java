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
package org.jboss.reflect.spi;

import org.jboss.reflect.plugins.TypeInfoAttachments;
import org.jboss.util.JBossObject;

/**
 * AbstractTypeInfo.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class AbstractTypeInfo extends JBossObject implements TypeInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -3395908398225434453L;
   
   /** The attachments */
   private transient TypeInfoAttachments attachments;

   public boolean isArray()
   {
      return false;
   }

   public boolean isCollection()
   {
      return false;
   }

   public boolean isMap()
   {
      return false;
   }

   public boolean isAnnotation()
   {
      return false;
   }

   public boolean isEnum()
   {
      return false;
   }

   public boolean isPrimitive()
   {
      return false;
   }

   public void setAttachment(String name, Object attachment)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      synchronized (this)
      {
         if (attachments == null)
         {
            if (attachment == null)
               return;
            attachments = new TypeInfoAttachments();;
         }
      }
      if (attachment == null)
         attachments.removeAttachment(name);
      else
         attachments.addAttachment(name, attachment);
   }

   public <T> T getAttachment(Class<T> expectedType)
   {
      if (expectedType == null)
         throw new IllegalArgumentException("Null expectedType");
      Object result = getAttachment(expectedType.getName());
      if (result == null)
         return null;
      return expectedType.cast(result);
   }

   public Object getAttachment(String name)
   {
      if (name == null)
         throw new IllegalArgumentException("Null name");
      synchronized (this)
      {
         if (attachments == null)
            return null;
      }
      return attachments.getAttachment(name);
   }
}
