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

/**
 * A MutableTypeInfoFactoryInterface.
 * 
 * @author <a href="stalep@gmail.com">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface MutableTypeInfoFactoryInterface
{

   /**
    * Create a new MutableClass
    * 
    * @param name
    * @return
    */
   MutableClassInfo createNewMutableClass(String name);
   
   /**
    * Get the information for a MutableClass.
    * Note that this will not put the MutableClass in any cache
    * 
    * @param name
    * @param cl
    * @return
    */
   MutableClassInfo getMutable(String name, ClassLoader cl);
   
}
