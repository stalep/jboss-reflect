/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins.introspection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.ConstructorInfo;
import org.jboss.reflect.FieldInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.TypeInfo;
import org.jboss.reflect.TypeInfoFactory;
import org.jboss.reflect.plugins.ConstructorInfoImpl;

/**
 * A constructor info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectConstructorInfo extends ConstructorInfoImpl
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The constructor object */
   protected Constructor constructor;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new constructor info
    */
   public ReflectConstructorInfo()
   {
   }

   /**
    * Create a new ConstructorInfo.
    *
    * @param factory the type info factory
    * @param declaring the declaring class
    * @param constructor the constructor
    */
   public ReflectConstructorInfo(IntrospectionTypeInfoFactory factory, ClassInfo declaring, Constructor constructor)
   {
      super(null, factory.getTypeInfos(constructor.getParameterTypes()), factory.getClassInfos(constructor.getExceptionTypes()), constructor.getModifiers(), declaring);
      this.constructor = constructor;
   }

   // Public --------------------------------------------------------

   /**
    * Get the constructor
    * 
    * @return the constructor
    */
   public Constructor getConstructor()
   {
      return constructor;
   }
   
   // Object overrides ----------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
