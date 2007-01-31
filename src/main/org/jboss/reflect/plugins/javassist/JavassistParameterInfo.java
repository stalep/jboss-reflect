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

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AnnotationValue;
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
   /** The serialVersionUID */
   private static final long serialVersionUID = 7388866103874412735L;

   /** The annotated info */
   private JavassistAnnotatedParameterInfo annotated;
   
   /** The name */
   private String name;
   
   /** The paramter type */
   private TypeInfo parameterType;
   
   /**
    * Create a new JavassistParameterInfo.
    * 
    * @param annotationHelper the annotation helper
    * @param annotated the annotated object
    * @param index the index
    * @param parameterType the type
    */
   public JavassistParameterInfo(AnnotationHelper annotationHelper, JavassistAnnotatedParameterInfo annotated, int index, TypeInfo parameterType)
   {
      super(annotationHelper);
      this.annotated = annotated;
      this.name = "arg" + index;
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
   
   public AnnotationValue[] getAnnotations()
   {
      if (annotationsArray == NOT_CONFIGURED)
      {
         annotated.createParameterAnnotations(); //Calls setAnnotations() so annotationsArray is created
      }
      return annotationsArray;
   }
   
   public void setAnnotations(AnnotationValue[] annotations)
   {
      annotationsArray = annotations;
      setupAnnotations(annotations);
   }

}
