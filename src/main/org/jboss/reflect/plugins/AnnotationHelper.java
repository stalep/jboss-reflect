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
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.AnnotationValue;

/**
 * AnnotationHelper.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface AnnotationHelper
{
   /**
    * Get the annotations for the annotated object
    * 
    * @param object the annotated object
    * @return the annotations
    */
   AnnotationValue[] getAnnotations(Object object);

   /**
    * Create an annotation from the underlying implementation and its info type
    * @param info the annotation info
    * @param ann the underlying annotation
    * @return the annotation value
    */
   AnnotationValue createAnnotationValue(AnnotationInfo info, Object ann);
}
