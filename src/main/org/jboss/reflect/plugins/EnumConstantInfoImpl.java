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

import java.io.Serializable;

import org.jboss.reflect.spi.EnumConstantInfo;
import org.jboss.reflect.spi.EnumInfo;
import org.jboss.util.JBossObject;

/**
 * An enumeration constant
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class EnumConstantInfoImpl extends JBossObject implements EnumConstantInfo, Serializable
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3761411923568243761L;
   
   // Attributes ----------------------------------------------------

   /** The constant name */
   protected String name;
   
   /** The enumeration */
   protected EnumInfo declaring;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new constant
    */
   public EnumConstantInfoImpl()
   {
   }
      
   /**
    * Create a new constant
    * 
    * @param name the name
    * @param declaring the enumeration
    */
   public EnumConstantInfoImpl(String name, EnumInfo declaring)
   {
      this.name = name;
      this.declaring = declaring;
      calculateHash();
   }

   // Public --------------------------------------------------------

   // EnumContstantInfo implementation ------------------------------

   /**
    * Get the name
    * 
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the declaring enumeration
    * 
    * @return the enumeration
    */
   public EnumInfo getDeclaring()
   {
      return declaring;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof EnumConstantInfoImpl)) return false;

      final EnumConstantInfoImpl enumConstantInfo = (EnumConstantInfoImpl) o;

      if (name != enumConstantInfo.name)
         return false;
      if (!declaring.equals(enumConstantInfo.declaring))
         return false;

      return true;
   }

   public int hashCode() { return hash; }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Calculate the hash code
    */
   protected void calculateHash()
   {
      int result = name.hashCode();
      result = 29 * result + declaring.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
