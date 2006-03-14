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
package org.jboss.config.plugins;

import java.lang.reflect.Constructor;

import org.jboss.beans.info.plugins.AbstractBeanInfoFactory;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.classadapter.plugins.BasicClassAdapterFactory;
import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.config.spi.Configuration;
import org.jboss.logging.Logger;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.util.NestedRuntimeException;

/**
 * Abstract configuration.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractConfiguration implements Configuration
{
   /** The log */
   private static final Logger log = Logger.getLogger(AbstractConfiguration.class);
   
   /** The default bean info factory */
   private BeanInfoFactory beanInfoFactory;
   
   /** The default class adaptor factory */
   private ClassAdapterFactory classAdapterFactory;
   
   /** The default type info factory */
   private TypeInfoFactory typeInfoFactory;

   /**
    * Create an abstract configuration
    */
   public AbstractConfiguration()
   {
   }
   
   public BeanInfo getBeanInfo(String className, ClassLoader cl) throws Throwable
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(className, cl);
      return getBeanInfoFactory().getBeanInfo(classAdapter);
   }
   
   public BeanInfo getBeanInfo(Class clazz) throws Throwable
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(clazz);
      return getBeanInfoFactory().getBeanInfo(classAdapter);
   }
   
   public BeanInfo getBeanInfo(TypeInfo typeInfo) throws Throwable
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(typeInfo);
      return getBeanInfoFactory().getBeanInfo(classAdapter);
   }
   
   public ClassInfo getClassInfo(String className, ClassLoader cl) throws Throwable
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(className, cl);
      return classAdapter.getClassInfo();
   }
   
   public ClassInfo getClassInfo(Class clazz) throws Throwable
   {
      ClassAdapter classAdapter = getClassAdapterFactory().getClassAdapter(clazz);
      return classAdapter.getClassInfo();
   }

   public TypeInfoFactory getTypeInfoFactory()
   {
      if (typeInfoFactory == null)
      {
         try
         {
            typeInfoFactory = createDefaultTypeInfoFactory();
         }
         catch (RuntimeException e)
         {
            throw e;
         }
         catch (Error e)
         {
            throw e;
         }
         catch (Throwable t)
         {
            throw new NestedRuntimeException("Cannot create TypeInfoFactory", t);
         }
      }
      return typeInfoFactory;
   }

   /**
    * Get the BeanInfoFactory
    * 
    * @return the BeanInfoFactory
    * @throws Throwable for any error
    */
   protected BeanInfoFactory getBeanInfoFactory() throws Throwable
   {
      if (beanInfoFactory == null)
         beanInfoFactory = createDefaultBeanInfoFactory();
      return beanInfoFactory;
   }

   /**
    * Get the class adapter factory
    * 
    * @return the ClassAdapterFactory
    * @throws Throwable for any error
    */
   protected ClassAdapterFactory getClassAdapterFactory() throws Throwable
   {
      if (classAdapterFactory == null)
         classAdapterFactory = createDefaultClassAdapterFactory();
      return classAdapterFactory;
   }
   
   /**
    * Create the default bean info factory
    * 
    * @return the bean info factory
    * @throws Throwable for any error
    */
   protected BeanInfoFactory createDefaultBeanInfoFactory() throws Throwable
   {
      return new AbstractBeanInfoFactory();
   }

   /**
    * Create the default class adapter factory
    * 
    * @return the class adapter factory
    * @throws Throwable for any error
    */
   protected ClassAdapterFactory createDefaultClassAdapterFactory() throws Throwable
   {
      ClassAdapterFactory result = new BasicClassAdapterFactory();
      
      // FIXME This is a temporary hack while I am refactoring
      try
      {
         Class clazz = getClass().getClassLoader().loadClass("org.jboss.aop.microcontainer.prototype.AOPClassAdapterFactory");
         Constructor constructor = clazz.getConstructor(new Class[] { ClassAdapterFactory.class } );
         result = (ClassAdapterFactory) constructor.newInstance(new Object[] { result });
      }
      catch (ClassNotFoundException ignored)
      {
         log.trace("No AOP in classpath " + ignored.getMessage());
      }
      result.setConfiguration(this);
      return result;
   }

   /**
    * Create the default type info factory
    * 
    * @return the type info factory
    * @throws Throwable for any error
    */
   protected TypeInfoFactory createDefaultTypeInfoFactory() throws Throwable
   {
      return new IntrospectionTypeInfoFactory();
   }
}
