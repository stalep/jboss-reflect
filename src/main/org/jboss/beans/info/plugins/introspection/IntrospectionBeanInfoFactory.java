/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins.introspection;

import java.util.Map;

import org.jboss.beans.info.plugins.AbstractBeanInfo;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.beans.info.spi.ClassInfo;
import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.util.CollectionsFactory;

/**
 * A bean info factory that uses introspection.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class IntrospectionBeanInfoFactory implements BeanInfoFactory
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------
   
   /** The cache */
   protected Map cache = CollectionsFactory.createMap(); 
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new introspection bean info factory
    */
   public IntrospectionBeanInfoFactory()
   {
   }
   
   // Public --------------------------------------------------------
   
   // BeanFactory implementation ------------------------------------

   public BeanInfo getBeanInfo(String className)
   {
      BeanInfo info = (BeanInfo) cache.get(className);
      if (info == null)
      {
         info = generateBeanInfo(className);
         cache.put(className, info);
      }
      return info;
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Generate the bean info
    * 
    * @param name the name of the bean
    */
   protected BeanInfo generateBeanInfo(String name)
   {
      TypeInfo typeInfo = Introspection.getTypeInfo(name);
      if (typeInfo instanceof ClassInfo)
      {
         ClassInfo classInfo = (ClassInfo) typeInfo;
         return new AbstractBeanInfo(this, classInfo);
      }
      throw new IllegalArgumentException(name + " is an interface");
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
