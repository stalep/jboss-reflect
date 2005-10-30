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

import org.jboss.reflect.spi.AnnotationAttribute;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.Value;
import org.jboss.util.JBossObject;

/**
 * An annotation attribute
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationAttributeImpl extends JBossObject implements AnnotationAttribute, Serializable
{
   // Constants -----------------------------------------------------
   
   /** serialVersionUID */
   private static final long serialVersionUID = 3546645408219542832L;
   
   // Attributes ----------------------------------------------------

   /** The name */
   protected String name;
   
   /** The attribute type */
   protected TypeInfo type;
   
   /** The default value */
   protected Value defaultValue;
   
   /** The hash code */
   protected int hash = -1;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new annotation attribute
    */
   public AnnotationAttributeImpl()
   {
   }

   /**
    * Create a new AnnotationAttribute.
    * 
    * @param name the annotation name
    * @param type the attribute type
    * @param defaultValue the default value
    */
   public AnnotationAttributeImpl(String name, TypeInfo type, Value defaultValue)
   {
      this.name = name;
      this.type = type;
      this.defaultValue = defaultValue;
      calcHashCode();
   }

   // Public --------------------------------------------------------

   // AnnotationAttribute implementation ----------------------------

   public String getName()
   {
      return name;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public Value getDefaultValue()
   {
      return defaultValue;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof AnnotationAttributeImpl == false)
         return false;

      final AnnotationAttributeImpl other = (AnnotationAttributeImpl) obj;

      if (!name.equals(other.name))
         return false;
      if (!type.equals(other.type))
         return false;

      return true;
   }

   public int hashCode()
   {
      return hash;
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Calculate the hash code
    */
   protected void calcHashCode()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + type.hashCode();
      hash = result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
