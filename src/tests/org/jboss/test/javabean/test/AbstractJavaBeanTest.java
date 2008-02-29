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
package org.jboss.test.javabean.test;

import org.jboss.javabean.plugins.jaxb.JavaBean10;
import org.jboss.javabean.plugins.jaxb.JavaBean20;
import org.jboss.test.xb.builder.AbstractBuilderTest;

/**
 * AbstractJavaBeanTest.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractJavaBeanTest extends AbstractBuilderTest
{
   public AbstractJavaBeanTest(String name)
   {
      super(name);
   }

   protected <T> T unmarshalJavaBean(Class<T> expected, Class<?>... others) throws Exception
   {
      return unmarshalJavaBean10(expected, others);
   }

   protected <T> T unmarshalJavaBean10(Class<T> expected, Class<?>... others) throws Exception
   {
      return unmarshalObject(expected, JavaBean10.class, others);
   }

   protected <T> T unmarshalJavaBean20(Class<T> expected, Class<?>... others) throws Exception
   {
      return unmarshalObject(expected, JavaBean20.class, others);
   }
}
