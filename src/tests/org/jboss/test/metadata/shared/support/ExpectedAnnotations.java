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
package org.jboss.test.metadata.shared.support;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.Set;

/**
 * ExpectedAnnotations.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ExpectedAnnotations
{
   /** The annotations */
   private Set<Class<? extends Annotation>> annotationTypes = new HashSet<Class<? extends Annotation>>();
   
   /**
    * Add an annotation type
    * 
    * @param annotationType the annotation type
    */
   public void add(Class<? extends Annotation> annotationType)
   {
      annotationTypes.add(annotationType);
   }
   
   /**
    * Remove an annotation type
    * 
    * @param annotationType the annotation type
    */
   public void remove(Class<? extends Annotation> annotationType)
   {
      annotationTypes.remove(annotationType);
   }
   
   /**
    * Get the annotation types
    * 
    * @return the annotation types
    */
   public Set<Class<? extends Annotation>> get()
   {
      return annotationTypes;
   }
}
