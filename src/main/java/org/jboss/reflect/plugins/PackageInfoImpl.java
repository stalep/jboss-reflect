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
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.PackageInfo;
import org.jboss.util.JBossStringBuilder;

/**
 * Class info
 *
 * TODO JBMICROCONT-118 fix the introspection assumption
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class PackageInfoImpl extends AnnotationHolder implements PackageInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3545798779904340792L;
   
   /** The package name */
   protected String name;
   
   /**
    * Create a new abstract PackageInfo.
    */
   public PackageInfoImpl()
   {
   }

   /**
    * Create a new package info
    * 
    * @param name the package name
    */
   public PackageInfoImpl(String name)
   {
      this.name = name;
   }

   /**
    * Create a new package info
    * 
    * @param name the package name
    * @param annotations the annotations
    */
   public PackageInfoImpl(String name, AnnotationValue[] annotations)
   {
      super(annotations);
      this.name = name;
   }

   public String getName()
   {
      return name;
   }
   
   @Override
   protected void toString(JBossStringBuilder buffer)
   {
      buffer.append("name=").append(getName());
   }

   @Override
   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof PackageInfo == false)
         return false;

      final PackageInfo other = (PackageInfo) obj;

      String thisName = getName();
      if (thisName != null ? thisName.equals(other.getName()) == false : other.getName() != null)
         return false;
      return true;
   }

   @Override
   public int hashCode()
   {
      return (name != null ? name.hashCode() : 0);
   }
}
