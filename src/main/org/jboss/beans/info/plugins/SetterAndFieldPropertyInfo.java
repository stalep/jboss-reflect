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

import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * Combined setter and field property info.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class SetterAndFieldPropertyInfo extends FieldPropertyInfo
{
   private static final long serialVersionUID = 1L;

   /** Previous property info */
   private PropertyInfo previous;

   public SetterAndFieldPropertyInfo(PropertyInfo previous, FieldInfo field)
   {
      // TODO - what to do with annotations, merge?
      super(field);

      if (previous == null)
         throw new IllegalArgumentException("Null previous");
      this.previous = previous;
   }

   public void set(Object bean, Object value) throws Throwable
   {
      previous.set(bean, value);
   }

   public MethodInfo getSetter()
   {
      return previous.getSetter();
   }

   public void setSetter(MethodInfo setter)
   {
      previous.setSetter(setter);
   }
}
