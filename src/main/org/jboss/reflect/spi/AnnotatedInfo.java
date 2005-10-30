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
package org.jboss.reflect.spi;

import org.jboss.util.JBossInterface;

/**
 * Annotated info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface AnnotatedInfo extends JBossInterface
{
   // Constants -----------------------------------------------------
   
   // Public --------------------------------------------------------

   /**
    * Get the annotations
    * 
    * @return the annotations
    */
   AnnotationValue[] getAnnotations();
   
   /**
    * Get an annotation
    * 
    * @param name the name
    * @return the annotation
    */
   AnnotationValue getAnnotation(String name);
   
   /**
    * Test whether an annotation is present
    * 
    * @param name the name
    * @return true when the annotation is present
    */
   boolean isAnnotationPresent(String name);
   
   // Inner classes -------------------------------------------------
}
