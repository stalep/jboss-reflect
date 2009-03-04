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
 * Note that modifiers that are not specified with any restrictions
 * are by default package protected. eg: ModifierInfo.STATIC.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public enum ModifierInfo
{
   /** Final */
   FINAL(Modifier.FINAL),

   /** Static */
   STATIC(Modifier.STATIC),

   /** Abstract */
   ABSTRACT(Modifier.ABSTRACT),

   /** Public */
   PUBLIC(Modifier.PUBLIC),

   /** Protected */
   PROTECTED(Modifier.PROTECTED),

   /** Package */
   PACKAGE(0),

   /** Private */
   PRIVATE(Modifier.PRIVATE),

   /** A constant */
   CONSTANT(STATIC.getModifiers() + FINAL.getModifiers()),

   /** A public constant */
   PUBLIC_CONSTANT(PUBLIC.getModifiers() + CONSTANT.getModifiers()),

   /** A protected constant */
   PROTECTED_CONSTANT(PROTECTED.getModifiers() + CONSTANT.getModifiers()),

   /** A package constant */
//   PACKAGE_CONSTANT(CONSTANT.getModifiers()),

   /** A private constant */
   PRIVATE_CONSTANT(PRIVATE.getModifiers() + CONSTANT.getModifiers()),

   /** Public Static */
   PUBLIC_STATIC(PUBLIC.getModifiers() + STATIC.getModifiers()),

   /** Protected Static */
   PROTECTED_STATIC(PROTECTED.getModifiers() + STATIC.getModifiers()),

   /** Package Static */
//   PACKAGE_STATIC(STATIC.getModifiers()),

   /** Private Static */
   PRIVATE_STATIC(PRIVATE.getModifiers() + STATIC.getModifiers()),

   /** Public Abstract */
   PUBLIC_ABSTRACT(PUBLIC.getModifiers() + ABSTRACT.getModifiers()),

   /** Protected Abstract */
   PROTECTED_ABSTRACT(PROTECTED.getModifiers() + ABSTRACT.getModifiers()),

   /** Package Abstract */
   PACKAGE_ABSTRACT(ABSTRACT.getModifiers()),
   
   PUBLIC_FINAL(PUBLIC.getModifiers() + FINAL.getModifiers()),
   
   PROTECTED_FINAL(PROTECTED.getModifiers() + FINAL.getModifiers()),
   
   PRIVATE_FINAL(PRIVATE.getModifiers() + FINAL.getModifiers()),

   PUBLIC_FINAL_ABSTRACT(PUBLIC_FINAL.getModifiers() + ABSTRACT.getModifiers()),
   
   PROTECTED_FINAL_ABSTRACT(PROTECTED_FINAL.getModifiers() + ABSTRACT.getModifiers()),
   
   PRIVATE_FINAL_ABSTRACT(PRIVATE_FINAL.getModifiers() + ABSTRACT.getModifiers()),
   
   /** Volatile */
   VOLATILE(Modifier.VOLATILE),
   
   PUBLIC_VOLATILE(PUBLIC.getModifiers() + VOLATILE.getModifiers()),
   
   PROTECTED_VOLATILE(PROTECTED.getModifiers() + VOLATILE.getModifiers()),
   
   PRIVATE_VOLATILE(PRIVATE.getModifiers() + VOLATILE.getModifiers()),
   
   SYNCHRONIZED(Modifier.SYNCHRONIZED),
   
   PUBLIC_SYNCHRONIZED(PUBLIC.getModifiers() + SYNCHRONIZED.getModifiers()),
   
   PROTECTED_SYNCHRONIZED(PROTECTED.getModifiers() + SYNCHRONIZED.getModifiers()),
   
   PRIVATE_SYNCHRONIZED(PRIVATE.getModifiers() + SYNCHRONIZED.getModifiers()),
   
   INTERFACE(Modifier.INTERFACE),
   PUBLIC_INTERFACE(PUBLIC.getModifiers() + INTERFACE.getModifiers()),
   PROTECTED_INTERFACE(PROTECTED.getModifiers() + INTERFACE.getModifiers()),
   PRIVATE_INTERFACE(PRIVATE.getModifiers() + INTERFACE.getModifiers()),
   
   PUBLIC_ABSTRACT_INTERFACE(PUBLIC_ABSTRACT.getModifiers() + INTERFACE.getModifiers()),
   PROTECTED_ABSTRACT_INTERFACE(PROTECTED_ABSTRACT.getModifiers() + INTERFACE.getModifiers()),
   
   
   NATIVE(Modifier.NATIVE),
   
   PUBLIC_NATIVE(PUBLIC.getModifiers() + NATIVE.getModifiers()),
   
   PROTECTED_NATIVE(PROTECTED.getModifiers() + NATIVE.getModifiers()),
   
   PRIVATE_NATIVE(PRIVATE.getModifiers() + NATIVE.getModifiers()),
   
   PUBLIC_FINAL_NATIVE(PUBLIC_FINAL.getModifiers() + NATIVE.getModifiers()),
   PROTECTED_FINAL_NATIVE(PROTECTED_FINAL.getModifiers() + NATIVE.getModifiers()),
   PRIVATE_FINAL_NATIVE(PRIVATE_FINAL.getModifiers() + NATIVE.getModifiers()),

   PUBLIC_STATIC_NATIVE(PUBLIC_STATIC.getModifiers() + NATIVE.getModifiers()),
   PROTECTED_STATIC_NATIVE(PROTECTED_STATIC.getModifiers() + NATIVE.getModifiers()),
   PRIVATE_STATIC_NATIVE(PRIVATE_STATIC.getModifiers() + NATIVE.getModifiers()),

   STRICTH(Modifier.STRICT),
   
   TRANSIENT(Modifier.TRANSIENT),
   PUBLIC_TRANSIENT(PUBLIC.getModifiers() + TRANSIENT.getModifiers()),
   PROTECTED_TRANSIENT(PROTECTED.getModifiers() + TRANSIENT.getModifiers()),
   PRIVATE_TRANSIENT(PRIVATE.getModifiers() + TRANSIENT.getModifiers()),
   
   PUBLIC_STATIC_TRANSIENT(PUBLIC_STATIC.getModifiers() + TRANSIENT.getModifiers()),
   PROTECTED_STATIC_TRANSIENT(PROTECTED_STATIC.getModifiers() + TRANSIENT.getModifiers()),
   PRIVATE_STATIC_TRANSIENT(PRIVATE_STATIC.getModifiers() + TRANSIENT.getModifiers()),
   
   SYNTHETIC(0x00001000),
   PUBLIC_SYNTHETIC(PUBLIC.getModifiers() + SYNTHETIC.getModifiers()),
   PROTECTED_SYNTHETIC(PROTECTED.getModifiers() + SYNTHETIC.getModifiers()),
   PRIVATE_SYNTHETIC(PRIVATE.getModifiers() + SYNTHETIC.getModifiers()),
   
   PUBLIC_CONSTANT_SYNTHETIC(PUBLIC_CONSTANT.getModifiers() + SYNTHETIC.getModifiers()),
   PROTECTED_CONSTANT_SYNTHETIC(PROTECTED_CONSTANT.getModifiers() + SYNTHETIC.getModifiers()),
   PRIVATE_CONSTANT_SYNTHETIC(PRIVATE_CONSTANT.getModifiers() + SYNTHETIC.getModifiers()),
   
   PUBLIC_VOLATILE_SYNTHETIC(PUBLIC_VOLATILE.getModifiers() + SYNTHETIC.getModifiers()),
   PROTECTED_VOLATILE_SYNTHETIC(PROTECTED_VOLATILE.getModifiers() + SYNTHETIC.getModifiers()),
   PRIVATE_VOLATILE_SYNTHETIC(PRIVATE_VOLATILE.getModifiers() + SYNTHETIC.getModifiers()),
   
   
   ANNOTATION(0x00002000),
   PUBLIC_ANNOTATION(PUBLIC.getModifiers() + ANNOTATION.getModifiers()),
   PROTECTED_ANNOTATION(PROTECTED.getModifiers() + ANNOTATION.getModifiers()),
   PRIVATE_ANNOTATION(PRIVATE.getModifiers() + ANNOTATION.getModifiers()),

   PUBLIC_ABSTRACT_ANNOTATION(PUBLIC_ABSTRACT.getModifiers() + ANNOTATION.getModifiers()),
   PROTECTED_ABSTRACT_ANNOTATION(PROTECTED_ABSTRACT.getModifiers() + ANNOTATION.getModifiers()),
   
   PUBLIC_INTERFACE_ANNOTATION(PUBLIC_INTERFACE.getModifiers() + ANNOTATION.getModifiers()),
   PROTECTED_INTERFACE_ANNOTATION(PROTECTED_INTERFACE.getModifiers() + ANNOTATION.getModifiers()),
   
   PUBLIC_ABSTRACT_INTERFACE_ANNOTATION(PUBLIC_ABSTRACT_INTERFACE.getModifiers() + ANNOTATION.getModifiers()),
   PROTECTED_ABSTRACT_INTERFACE_ANNOTATION(PROTECTED_ABSTRACT_INTERFACE.getModifiers() + ANNOTATION.getModifiers()),
   
   
   ENUM(0x00004000),
   PUBLIC_ENUM(PUBLIC.getModifiers() + ENUM.getModifiers()),
   PROTECTED_ENUM(PROTECTED.getModifiers() + ENUM.getModifiers()),
   PRIVATE_ENUM(PRIVATE.getModifiers() +ENUM.getModifiers()),
   
   PUBLIC_CONSTANT_ENUM(PUBLIC_CONSTANT.getModifiers() + ENUM.getModifiers()),
   PROTECTED_CONSTANT_ENUM(PROTECTED_CONSTANT.getModifiers() + ENUM.getModifiers()),
   PRIVATE_CONSTANT_ENUM(PRIVATE_CONSTANT.getModifiers() +ENUM.getModifiers()),
   
   PUBLIC_FINAL_ENUM(PUBLIC_FINAL.getModifiers() + ENUM.getModifiers()),
   PROTECTED_FINAL_ENUM(PROTECTED_FINAL.getModifiers() + ENUM.getModifiers()),
   PRIVATE_FINAL_ENUM(PRIVATE_FINAL.getModifiers() +ENUM.getModifiers()),
   
   
   ;
   
   private final int modifier;
   
   ModifierInfo(int modifier)
   {
      this.modifier = modifier;
   }
   
   /**
    * Get the modifiers
    * 
    * @return the modifiers
    */
   public int getModifiers()
   {
      return modifier;
   }
   
   public boolean isPublic()
   {
      return (this.name().startsWith("PUBLIC"));
   }
   
   /**
    * Whether it is static
    * 
    * @return true when static
    */
    public boolean isStatic()
    {
       return (this.name().contains("STATIC"));
    }

   /**
    * Whether it is volatile
    *
    * @return true if volatile
    */
    public boolean isVolatile()
    {
       return (this.name().contains("VOLATILE"));
    }

   public static ModifierInfo getNewModifier(int modifiers)
   {
      for(ModifierInfo modifier : values())
         if(modifier.getModifiers() == modifiers)
            return modifier;
      
      throw new RuntimeException("Couldnt find Modifier for: "+modifiers);
   }
}
