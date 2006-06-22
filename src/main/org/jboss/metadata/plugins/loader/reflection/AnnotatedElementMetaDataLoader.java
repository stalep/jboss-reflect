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
package org.jboss.metadata.plugins.loader.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

import org.jboss.metadata.plugins.loader.BasicMetaDataLoader;
import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationItem;
import org.jboss.metadata.spi.retrieval.simple.SimpleAnnotationsItem;
import org.jboss.util.JBossStringBuilder;
import org.jboss.util.Strings;

/**
 * AnnotatedElementMetaDataLoader.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AnnotatedElementMetaDataLoader extends BasicMetaDataLoader 
{
   /** The annotated element */
   private AnnotatedElement annotated;
   
   /**
    * Create a new AnnotatedElementMetaDataContext.
    * 
    * @param annotated the annotated element
    */
   public AnnotatedElementMetaDataLoader(AnnotatedElement annotated)
   {
      if (annotated == null)
         throw new IllegalArgumentException("Null annotated element");
      this.annotated = annotated;
   }

   @SuppressWarnings("unchecked")
   public AnnotationsItem retrieveAnnotations()
   {
      Annotation[] annotations = annotated.getAnnotations();
      if (annotations.length == 0)
         return SimpleAnnotationsItem.NO_ANNOTATIONS;
      
      AnnotationItem[] items = new AnnotationItem[annotations.length];
      for (int i = 0; i < items.length; ++i)
         items[i] = new SimpleAnnotationItem(annotations[i]);
      return new SimpleAnnotationsItem(items);
   }
   
   public <T extends Annotation> AnnotationItem<T> retrieveAnnotation(Class<T> annotationType)
   {
      T annotation = annotated.getAnnotation(annotationType);
      if (annotation == null)
         return null;
      return new SimpleAnnotationItem<T>(annotation);
   }

   public String toString()
   {
      JBossStringBuilder buffer = new JBossStringBuilder();
      Strings.defaultToString(buffer, this);
      buffer.append("[");
      buffer.append(annotated);
      buffer.append("]");
      return buffer.toString();
   }
}
