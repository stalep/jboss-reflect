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
 * This avoids recalculating things everytime a factory is
 * constructed inside the same classloader
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class IntrospectionTypeInfoFactory implements TypeInfoFactory
{
   /** The delegate */
   private static IntrospectionTypeInfoFactoryImpl delegate = new IntrospectionTypeInfoFactoryImpl();

   public TypeInfo getTypeInfo(Class clazz)
   {
      return delegate.getTypeInfo(clazz);
   }
   
   public TypeInfo getTypeInfo(String name, ClassLoader cl) throws ClassNotFoundException
   {
      return delegate.getTypeInfo(name, cl);
   }
}
