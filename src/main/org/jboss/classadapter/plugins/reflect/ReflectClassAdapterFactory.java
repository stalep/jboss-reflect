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
package org.jboss.classadapter.plugins.reflect;

import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * A reflected class adapter factory.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectClassAdapterFactory implements ClassAdapterFactory
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The type info factory */
   protected TypeInfoFactory typeInfoFactory = new IntrospectionTypeInfoFactory();
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   // ClassAdapterFactory implementation ----------------------------

   public ClassAdapter getClassAdapter(Class clazz)
   {
      TypeInfo typeInfo = typeInfoFactory.getTypeInfo(clazz);
      return createClassAdapter(typeInfo);
   }
   
   public ClassAdapter getClassAdapter(String name, ClassLoader cl) throws ClassNotFoundException
   {
      TypeInfo typeInfo = typeInfoFactory.getTypeInfo(name, cl);
      return createClassAdapter(typeInfo);
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   /**
    * Create a class adapter for the type info
    * 
    * @param typeInfo the type info
    * @return the class adapter
    */
   protected ClassAdapter createClassAdapter(TypeInfo typeInfo)
   {
      if (typeInfo instanceof ClassInfo == false)
         throw new IllegalArgumentException("Not a class " + typeInfo.getName());
      ClassInfo classInfo = (ClassInfo) typeInfo;
      
      return new ReflectClassAdapter(classInfo);
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
