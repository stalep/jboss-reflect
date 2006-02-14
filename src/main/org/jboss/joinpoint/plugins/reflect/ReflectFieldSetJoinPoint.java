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
package org.jboss.joinpoint.plugins.reflect;

import org.jboss.joinpoint.spi.FieldSetJoinpoint;
import org.jboss.reflect.plugins.introspection.ReflectionUtils;
import org.jboss.reflect.spi.FieldInfo;

/**
 * A field set joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldSetJoinPoint extends ReflectTargettedJoinPoint implements FieldSetJoinpoint
{
   /** The field info */
   protected FieldInfo fieldInfo;

   /** The value */
   protected Object value;

   /**
    * Create a new field set join point
    * 
    * @param fieldInfo the field info
    */
   public ReflectFieldSetJoinPoint(FieldInfo fieldInfo)
   {
      this.fieldInfo = fieldInfo;
   }

   public FieldInfo getFieldInfo()
   {
      return fieldInfo;
   }
   
   /**
    * Get the value
    * 
    * @return the value
    */
   public Object getValue()
   {
      return value;
   }

   public void setValue(Object value)
   {
      this.value = value;
   }

   public Object dispatch() throws Throwable
   {
      return ReflectionUtils.setField(fieldInfo.getField(), target, value);
   }
   
   public String toHumanReadableString()
   {
      return "SET " + fieldInfo.toString();
   }
}
