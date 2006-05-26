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
package org.jboss.test.profileservice.test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.jboss.annotation.management.ManagedObject;
import org.jboss.test.profileservice.profiles.p0.beans.DataSourceThing;

public class SimpleProfileServiceTestCase extends TestCase
{

   public void testManagementView()
   {
      Annotation[] as = DataSourceThing.class.getAnnotations();
      System.out.println("DataSourceThing has annotations: "+as.length);
      for(Annotation a : as)
      {
         System.out.println(a);
         Class type = a.annotationType();
         for(Method m : type.getMethods())
         {
            if( m.getDeclaringClass().isAssignableFrom(ManagedObject.class) == false )
               System.out.println(m);
         }
      }
   }

   public static void main(String[] args)
   {
      Annotation[] as = DataSourceThing.class.getAnnotations();
      System.out.println("DataSourceThing has annotations: "+as.length);
      for(Annotation a : as)
      {
         System.out.println(a);
         Class type = a.annotationType();
         for(Field f : type.getFields())
         {
            System.out.println(f);
         }
      }
      
   }
}
