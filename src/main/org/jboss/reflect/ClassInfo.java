/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;


/**
 * Class info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ClassInfo extends AnnotatedInfo, TypeInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the class name
    * 
    * @return the name
    */
   String getName();

   /**
    * Whether it is an interface
    * 
    * @return true when an interface
    */
   boolean isInterface();
   
   /**
    * Get the modifiers
    * 
    * @return the modifiers
    */
   int getModifiers();

   /**
    * Get the interfaces
    *
    * @return the interfaces
    */
   InterfaceInfo[] getInterfaces();
   
   /**
    * Get the declared method
    * 
    * @param name the method name
    * @param parameters the parameters
    * @return the method info
    */
   MethodInfo getDeclaredMethod(String name, TypeInfo[] parameters);

   /**
    * Get the declared methods
    * 
    * @return the methods
    */
   MethodInfo[] getDeclaredMethods();

   /**
    * Get the declared field
    * 
    * @param name the field name
    * @return the field
    */
   FieldInfo getDeclaredField(String name);

   /**
    * Get the declared fields
    * 
    * @return the fields
    */
   FieldInfo[] getDeclaredFields();

   /**
    * Get the declared constructors
    * 
    * @return the constructors
    */
   ConstructorInfo[] getDeclaredConstructors();

   /**
    * Get the super class
    * 
    * @return the super class
    */
   ClassInfo getSuperclass();
   
   // Inner classes -------------------------------------------------
}
