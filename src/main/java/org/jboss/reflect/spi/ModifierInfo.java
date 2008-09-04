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

import java.lang.reflect.Modifier;

/**
 * Modifier info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ModifierInfo
{
   /** Final */
   public static final int FINAL = Modifier.FINAL;

   /** Static */
   public static final int STATIC = Modifier.STATIC;

   /** Abstract */
   public static final int ABSTRACT = Modifier.ABSTRACT;

   /** Public */
   public static final int PUBLIC = Modifier.PUBLIC;

   /** Protected */
   public static final int PROTECTED = Modifier.PROTECTED;

   /** Package */
   public static final int PACKAGE = 0;

   /** Private */
   public static final int PRIVATE = Modifier.PRIVATE;

   /** A constant */
   public static final int CONSTANT = STATIC + FINAL;

   /** A public constant */
   public static final int PUBLIC_CONSTANT = PUBLIC + CONSTANT;

   /** A protected constant */
   public static final int PROTECTED_CONSTANT = PROTECTED + CONSTANT;

   /** A package constant */
   public static final int PACKAGE_CONSTANT = CONSTANT;

   /** A private constant */
   public static final int PRIVATE_CONSTANT = PRIVATE + CONSTANT;

   /** Public Static */
   public static final int PUBLIC_STATIC = PUBLIC + STATIC;

   /** Protected Static */
   public static final int PROTECTED_STATIC = PROTECTED + STATIC;

   /** Package Static */
   public static final int PACKAGE_STATIC = STATIC;

   /** Private Static */
   public static final int PRIVATE_STATIC = PRIVATE + STATIC;

   /** Public Abstract */
   public static final int PUBLIC_ABSTRACT = PUBLIC + ABSTRACT;

   /** Protected Abstract */
   public static final int PROTECTED_ABSTRACT = PROTECTED + ABSTRACT;

   /** Package Abstract */
   public static final int PACKAGE_ABSTRACT = ABSTRACT;

   /**
    * Get the modifiers
    * 
    * @return the modifiers
    */
   int getModifiers();
   
   /**
    * Whether it is public
    * 
    * @return true when public
    */
   boolean isPublic();
   
   /**
    * Whether it is static
    * 
    * @return true when static
    */
   boolean isStatic();

   /**
    * Whether it is volatile
    *
    * @return true if volatile
    */
   boolean isVolatile();
}
