/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins.introspection;

import java.lang.reflect.Method;

import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.MethodInfo;
import org.jboss.reflect.TypeInfoFactory;

/**
 * A method info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectMethodInfo extends MethodInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The method object */
   protected Method method;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new method info
    */
   public ReflectMethodInfo()
   {
   }

   /**
    * Create a new MethodInfo.
    *
    * @param factory the type info factory
    * @param declaring the declaring class
    * @param method the method
    */
   public ReflectMethodInfo(IntrospectionTypeInfoFactory factory, ClassInfo declaring, Method method)
   {
      super(null, method.getName(), factory.getTypeInfo(method.getReturnType()), factory.getTypeInfos(method.getParameterTypes()), factory.getClassInfos(method.getExceptionTypes()), method.getModifiers(), declaring);
      this.method = method;
   }

   // Public --------------------------------------------------------

   /**
    * Get the method
    * 
    * @return the method
    */
   public Method getMethod()
   {
      return method;
   }
   
   // Object overrides ----------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
