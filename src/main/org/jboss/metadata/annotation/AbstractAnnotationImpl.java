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
package org.jboss.metadata.annotation;

import java.lang.annotation.Annotation;

/**
 * AbstractAnnotationImpl.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractAnnotationImpl implements Annotation
{
   /** The annotation type */
   private Class<? extends Annotation> annotationType;
   
   /**
    * Create a new AbstractAnnotationImpl.
    * 
    * <p>This constructor guesses the annotationType.
    */
   @SuppressWarnings("unchecked")
   public AbstractAnnotationImpl()
   {
      for (Class clazz : getClass().getInterfaces())
      {
         if (clazz.equals(Annotation.class) == false && (clazz.isAnnotation()))
         {
            annotationType = clazz;
            return;
         }
      }
   }
   
   /**
    * Create a new AbstractAnnotationImpl.
    * 
    * @param annotationType the annotation type
    */
   public AbstractAnnotationImpl(Class<? extends Annotation> annotationType)
   {
      this.annotationType = annotationType;
   }
   
   public Class<? extends Annotation> annotationType()
   {
      return annotationType;
   }
   
   public boolean equals(Object object)
   {
      if (object == this)
         return true;
      if (object == null || object instanceof Annotation == false)
         return false;
      
      Annotation other = (Annotation) object;
      return annotationType.equals(other.annotationType());
   }
   
   public int hashCode()
   {
      return annotationType.hashCode();
   }
}
