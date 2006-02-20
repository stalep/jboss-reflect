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

import org.jboss.reflect.spi.AnnotatedInfo;
import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.util.JBossObject;

/**
 * JavassistAnnotatedInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavassistAnnotatedInfo extends JBossObject implements AnnotatedInfo
{
   public AnnotationValue getAnnotation(String name)
   {
      // @todo getAnnotation
      throw new org.jboss.util.NotImplementedException("getAnnotation");
   }

   public AnnotationValue[] getAnnotations()
   {
      // @todo getAnnotations
      return null;
   }

   public boolean isAnnotationPresent(String name)
   {
      // @todo isAnnotationPresent
      throw new org.jboss.util.NotImplementedException("isAnnotationPresent");
   }
}
