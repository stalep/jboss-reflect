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

import java.util.HashMap;

import org.jboss.reflect.plugins.AbstractAnnotatedInfo;
import org.jboss.reflect.plugins.AnnotationHelper;
import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;

/**
 * JavassistAnnotatedInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class JavassistAnnotatedInfo extends AbstractAnnotatedInfo
{
   final static AnnotationValue[] NOT_CONFIGURED = new AnnotationValue[0];

   /** The annotations */
   protected AnnotationValue[] annotationsArray = NOT_CONFIGURED;

   /** Annotations map Map<String, AnnotationValue> */
   protected HashMap<String, AnnotationValue> annotationMap;

   protected AnnotationHelper annotationHelper;
   
   public JavassistAnnotatedInfo(AnnotationHelper annotationHelper)
   {
      this.annotationHelper = annotationHelper;
   }

   protected AnnotationValue[] getAnnotations(Object obj)
   {
      synchronized (this)
      {
         if (annotationsArray == NOT_CONFIGURED)
         {
            annotationsArray = null;
            setupAnnotations(annotationHelper.getAnnotations(obj));
         }
      }      
      return annotationsArray;
   }

   public AnnotationValue getAnnotation(String name)
   {
      getAnnotations();
      return annotationMap.get(name);
   }

   public boolean isAnnotationPresent(String name)
   {
      getAnnotations();
      return annotationMap.containsKey(name);
   }
   
   /**
    * Set up the annotations
    * 
    * @param annotations the annotations
    */
   protected void setupAnnotations(AnnotationValue[] annotations)
   {
      if (annotations != null && annotations.length > 0)
      {
         this.annotationsArray = annotations;
         annotationMap = new HashMap<String, AnnotationValue>();
         for (int i = 0; i < annotations.length; i++)
         {
            AnnotationInfo type = annotations[i].getAnnotationType();
            annotationMap.put(type.getName(), annotations[i]);
         }
      }
   }
   
}
