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
package org.jboss.config.spi;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * Configuration.<p>
 * 
 * Provides configuration options. 
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface Configuration
{
   /**
    * Get the bean info
    * 
    * @param className the class name
    * @param cl the classloader
    * @return the bean info
    * @throws Throwable for any error
    */
   BeanInfo getBeanInfo(String className, ClassLoader cl) throws Throwable;

   /**
    * Get the bean info
    * 
    * @param clazz the class
    * @return the bean info
    * @throws Throwable for any error
    */
   BeanInfo getBeanInfo(Class clazz) throws Throwable;

   /**
    * Get the bean info
    * 
    * @param type the type info
    * @return the bean info
    * @throws Throwable for any error
    */
   BeanInfo getBeanInfo(TypeInfo type) throws Throwable;
   
   /**
    * Get the class info for a class
    * 
    * @param className the class name
    * @param cl the classloader
    * @return the class info
    * @throws Throwable for any error
    */
   ClassInfo getClassInfo(String className, ClassLoader cl) throws Throwable;
   
   /**
    * Get the class info for a class
    * 
    * @param clazz the class
    * @return the class info
    * @throws Throwable for any error
    */
   ClassInfo getClassInfo(Class clazz) throws Throwable;

   /**
    * Get the type info factory
    * 
    * @return the TypeInfoFactory
    */
   TypeInfoFactory getTypeInfoFactory();
}
