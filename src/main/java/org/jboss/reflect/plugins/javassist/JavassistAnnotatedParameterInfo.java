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
package org.jboss.reflect.plugins.javassist;

import java.lang.annotation.Annotation;

import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public abstract class JavassistAnnotatedParameterInfo extends JavassistAnnotatedInfo
{
   /** The serialVersionUID */
   private static final long serialVersionUID = -494071110672611729L;

   /** The parameters */
   protected transient ParameterInfo[] parameters;
   
   /** The parameter types */
   protected transient TypeInfo[] parameterTypes;
   
   /** The exception types */
   protected transient ClassInfo[] exceptionTypes;
   
   /** The type info */
   protected JavassistTypeInfo typeInfo;

   public JavassistAnnotatedParameterInfo(AnnotationHelper annotationHelper)
   {
      super(annotationHelper);
   }

   protected void setupParameterAnnotations(Object[][] annotations)
   {
      for (int param = 0 ; param < annotations.length ; param++)
      {
         AnnotationValue[] annotationValues = new AnnotationValue[annotations[param].length];
         for (int ann = 0 ; ann < annotationValues.length ; ann++)
         {
            Class<?> clazz = ((Annotation)annotations[param][ann]).annotationType();

            AnnotationInfo info = (AnnotationInfo)((JavassistTypeInfoFactoryImpl)annotationHelper).getTypeInfo(clazz);
            annotationValues[ann] = annotationHelper.createAnnotationValue(info, annotations[param][ann]);
         }
         ((JavassistParameterInfo)parameters[param]).setAnnotations(annotationValues);
      }
   }
   
   protected abstract void createParameterAnnotations();

}
