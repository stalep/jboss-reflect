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
package org.jboss.beans.info.plugins;

import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * Field property info.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class FieldPropertyInfo extends AbstractPropertyInfo
{
   private static final long serialVersionUID = 1L;

   /** The field info */
   private FieldInfo field;

   public FieldPropertyInfo(FieldInfo field)
   {
      this(field, true);
   }

   protected FieldPropertyInfo(FieldInfo field, boolean setAnnotations)
   {
      if (field == null)
         throw new IllegalArgumentException("Null field");

      this.field = field;
      init(field.getName(), field.getName(), field.getType());
      if (setAnnotations)
         setupAnnotations(field.getAnnotations());
   }

   public MethodInfo getGetter()
   {
      return null;
   }

   public void setGetter(MethodInfo getter)
   {
      throw new UnsupportedOperationException("Cannot set getter on field property info.");
   }

   public MethodInfo getSetter()
   {
      return null;
   }

   public void setSetter(MethodInfo setter)
   {
      throw new UnsupportedOperationException("Cannot set getter on field property info.");
   }

   public boolean isReadable()
   {
      return true;
   }

   public boolean isWritable()
   {
      return true;
   }

   public Object get(Object bean) throws Throwable
   {
      if (bean == null)
         throw new IllegalArgumentException("Null bean");

      return field.get(bean);
   }

   public void set(Object bean, Object value) throws Throwable
   {
      if (bean == null)
         throw new IllegalArgumentException("Null bean");

      field.set(bean, value);
   }

   @Override
   public FieldInfo getFieldInfo()
   {
      return field;
   }

   @Override
   public boolean equals(Object object)
   {
      if (object == null || object instanceof FieldPropertyInfo == false)
         return false;

      FieldPropertyInfo other = (FieldPropertyInfo) object;
      return field.equals(other.field);
   }

   @Override
   public void toString(JBossStringBuilder buffer)
   {
      buffer.append(" field=").append(field);
   }
}
