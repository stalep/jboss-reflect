/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins.introspection;

import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * An introspection type factory that uses a static delegate.<p>
 * 
 * This avoids recalculating things everytime an factory is
 * constructed inside the same classloader
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class IntrospectionTypeInfoFactory implements TypeInfoFactory
{
   // Constants -----------------------------------------------------
   
   /** The delegate */
   private static IntrospectionTypeInfoFactoryImpl delegate = new IntrospectionTypeInfoFactoryImpl();
   
   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   // TypeInfoFactory implementation --------------------------------

   public TypeInfo getTypeInfo(Class clazz)
   {
      return delegate.getTypeInfo(clazz);
   }
   
   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      return delegate.getTypeInfo(name, cl);
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
