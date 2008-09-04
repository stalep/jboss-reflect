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
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Parameter info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ParameterInfoImpl extends AnnotationHolder implements ParameterInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3256725082746664754L;

   /** The parameter name */
   protected String name;
   
   /** The parameter type */
   protected TypeInfo parameterType;

   /**
    * Create a new method info
    */
   public ParameterInfoImpl()
   {
   }

   /**
    * Create a new MethodInfo.
    * 
    * @param annotations the annotations
    * @param name the method name
    * @param parameterType the parameter type
    */
   public ParameterInfoImpl(AnnotationValue[] annotations, String name, TypeInfo parameterType)
   {
      super(annotations);
      this.name = name;
      this.parameterType = parameterType;
   }

   public String getName()
   {
      return name;
   }

   public TypeInfo getParameterType()
   {
      return parameterType;
   }

   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof ParameterInfo == false)
         return false;
      
      ParameterInfo other = (ParameterInfo) obj;
      return parameterType.equals(other.getParameterType());
   }
   
   public int hashCode()
   {
      return parameterType.hashCode();
   }
}
