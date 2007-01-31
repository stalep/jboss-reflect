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

import java.io.Serializable;
import java.lang.annotation.Annotation;

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.util.JBossObject;

/**
 * Abstract annotated info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public abstract class AbstractAnnotatedInfo extends JBossObject implements AnnotatedInfo, Serializable
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3546645408219542832L;

   /** No annotations */
   private static final Annotation[] NO_ANNOTATIONS = new Annotation[0];

   /**
    * Create a new annotated info
    */
   public AbstractAnnotatedInfo()
   {
   }

   public <T extends Annotation> T getUnderlyingAnnotation(Class<T> annotationType)
   {
      if (annotationType == null)
         throw new IllegalArgumentException("Null annotationType");
      AnnotationValue value = getAnnotation(annotationType.getName());
      if (value == null)
         return null;
      return value.getUnderlyingAnnotation(annotationType);
   }

   public Annotation[] getUnderlyingAnnotations()
   {
      AnnotationValue[] values = getAnnotations();
      if (values == null)
         return NO_ANNOTATIONS;
      Annotation[] result = new Annotation[values.length];
      for (int i = 0; i < values.length; ++i)
         result[i] = values[i].getUnderlyingAnnotation();
      return result;
   }

   public boolean isAnnotationPresent(Class<? extends Annotation> annotationType)
   {
      if (annotationType == null)
         throw new IllegalArgumentException("Null annotationType");
      return isAnnotationPresent(annotationType.getName());
   }
}
