/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classadapter.plugins.reflect;

import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;

/**
 * A reflected class adapter factory.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectClassAdapterFactory implements ClassAdapterFactory
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The type info factory */
   protected TypeInfoFactory typeInfoFactory = new IntrospectionTypeInfoFactory();
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   // ClassAdapterFactory implementation ----------------------------

   public ClassAdapter getClassAdapter(Class clazz)
   {
      TypeInfo typeInfo = typeInfoFactory.getTypeInfo(clazz);
      return createClassAdapter(typeInfo);
   }
   
   public ClassAdapter getClassAdapter(String name, ClassLoader cl) throws ClassNotFoundException
   {
      TypeInfo typeInfo = typeInfoFactory.getTypeInfo(name, cl);
      return createClassAdapter(typeInfo);
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   protected ClassAdapter createClassAdapter(TypeInfo typeInfo)
   {
      if (typeInfo instanceof ClassInfo == false)
         throw new IllegalArgumentException("Not a class " + typeInfo.getName());
      ClassInfo classInfo = (ClassInfo) typeInfo;
      if (classInfo.isInterface())
         throw new IllegalArgumentException("Interface not allowed " + typeInfo.getName());
      
      return new ReflectClassAdapter(classInfo);
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
