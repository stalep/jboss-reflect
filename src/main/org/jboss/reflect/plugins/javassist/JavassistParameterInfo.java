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

import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * JavassistParameterInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistParameterInfo extends JavassistAnnotatedInfo implements ParameterInfo
{
   /** The annotated info */
   private JavassistAnnotatedInfo annotated;
   
   /** The name */
   private String name;
   
   /** The paramter type */
   private TypeInfo parameterType;
   
   /**
    * Create a new JavassistParameterInfo.
    * 
    * @param annotated the annotated object
    * @param name the name
    * @param parameterType the type
    */
   public JavassistParameterInfo(JavassistAnnotatedInfo annotated, String name, TypeInfo parameterType)
   {
      this.annotated = annotated;
      this.name = name;
      this.parameterType = parameterType;
   }

   /**
    * Get the annotated info
    * 
    * @return the annotated
    */
   protected JavassistAnnotatedInfo getAnnotated()
   {
      return annotated;
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

   protected int getHashCode()
   {
      return getName().hashCode();
   }

   public void toShortString(JBossStringBuilder buffer)
   {
      buffer.append(getParameterType());
   }

   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("type=").append(getParameterType());
      super.toString(buffer);
   }
}
