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
package org.jboss.annotation.factory;

import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Map;

/**
 * Validates if all attributes have been filled in for an annotation. 
 * Makes no attempt to read default values
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class SimpleAnnotationValidator implements AnnotationValidator
{
   public void validate(Map<String, Object> map, Class<?> annotation)
   {
      ArrayList<String> notAssignedAttributes = null;
      Method[] methods = getDeclaredMethods(annotation);
      for (int i = 0 ; i < methods.length ; i++)
      {
         if (map.get(methods[i].getName()) == null)
         {
            if (notAssignedAttributes == null)
            {
               notAssignedAttributes = new ArrayList<String>();
            }
            notAssignedAttributes.add(methods[i].getName());
         }
      }

      if (notAssignedAttributes != null)
      {
         throw new AnnotationValidationException("Unable to fill in default attributes for " + annotation + " " + notAssignedAttributes);
      }
   }
   
   private Method[] getDeclaredMethods(final Class<?> clazz)
   {
      return AccessController.doPrivileged(new PrivilegedAction<Method[]>()
      {
         public Method[] run() 
         {
            return clazz.getDeclaredMethods();
         };  
      });
   }

}
