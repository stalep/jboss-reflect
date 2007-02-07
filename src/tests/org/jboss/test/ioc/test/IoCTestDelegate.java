/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.ioc.test;

import org.jboss.test.AbstractTestDelegate;
import org.jboss.xb.binding.Unmarshaller;
import org.jboss.xb.binding.UnmarshallerFactory;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SingletonSchemaResolverFactory;

/**
 * Test delegate support for other IoC / XML bindings than MC's.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 */
public class IoCTestDelegate extends AbstractTestDelegate
{
   /** The unmarshaller factory */
   protected UnmarshallerFactory unmarshallerFactory;

   /** The resolver */
   protected SchemaBindingResolver resolver;

   /**
    * Create a new JBossXBTestDelegate.
    *
    * @param clazz the test class
    */
   public IoCTestDelegate(Class clazz)
   {
      super(clazz);
   }

   public void setUp() throws Exception
   {
      super.setUp();
      unmarshallerFactory = UnmarshallerFactory.newInstance();
      resolver = SingletonSchemaResolverFactory.getInstance().getSchemaBindingResolver();
   }

   /**
    * Unmarshal an object
    *
    * @param url the url
    * @return the object
    * @throws Exception for any error
    */
   public Object unmarshal(String url) throws Exception
   {
      long start = System.currentTimeMillis();
      Unmarshaller unmarshaller = unmarshallerFactory.newUnmarshaller();
      log.debug("Initialized parsing in " + (System.currentTimeMillis() - start) + "ms");
      try
      {
         Object result = unmarshaller.unmarshal(url, resolver);
         log.debug("Total parse for " + url + " took " + (System.currentTimeMillis() - start) + "ms");
         return result;
      }
      catch (Exception e)
      {
         log.debug("Error during parsing: " + url, e);
         throw e;
      }
   }
}
