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
package org.jboss.reflect.plugins.javassist;

import org.jboss.reflect.plugins.ArrayInfoImpl;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Javassist array info
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class JavassistArrayInfoImpl extends ArrayInfoImpl
{
   /** The serialVersionUID */
   private static final long serialVersionUID = 9195834689976459024L;

   public JavassistArrayInfoImpl()
   {
      super();
   }

   public JavassistArrayInfoImpl(TypeInfo componentType)
   {
      super(componentType);
   }

   public boolean isArray()
   {
      return true;
   }


}
