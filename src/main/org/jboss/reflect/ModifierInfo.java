/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.lang.reflect.Modifier;

/**
 * Modifier info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ModifierInfo
{
   // Constants -----------------------------------------------------

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
   
   // Public --------------------------------------------------------

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
   
   // Inner classes -------------------------------------------------
}
