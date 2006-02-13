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
package org.jboss.classadapter.spi;

/**
 * A class adapter factory.<p>
 * 
 * The ClassAdapterFactory serves as the entry point
 * for deciding whether it supports the class. e.g.
 * the AOP ClassAdapterFactory may return null
 * allowing the microcontainer to default back to
 * reflection for the class.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ClassAdapterFactory
{
   /**
    * Get a class adapter
    * 
    * @param name the class name
    * @param cl the classloader
    * @return the class adapter
    * @throws ClassNotFoundException when there is no such class
    */
   ClassAdapter getClassAdapter(String name, ClassLoader cl) throws ClassNotFoundException;

   /**
    * Get a class adapter
    * 
    * @param clazz the class
    * @return the class adapter
    */
   ClassAdapter getClassAdapter(Class clazz);
}
