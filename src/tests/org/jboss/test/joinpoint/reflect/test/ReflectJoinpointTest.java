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
package org.jboss.test.joinpoint.reflect.test;

import org.jboss.joinpoint.plugins.config.Config;
import org.jboss.joinpoint.plugins.reflect.ReflectJoinpointFactory;
import org.jboss.joinpoint.spi.Joinpoint;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.ContainerTest;
import org.jboss.test.joinpoint.reflect.support.SimpleBean;

/**
 * Joinpoint Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class ReflectJoinpointTest extends ContainerTest
{
   public ReflectJoinpointTest(String name)
   {
      super(name);
   }

   public void testSimpleConstructor() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      Object object = Config.instantiate(jpf, new String[0], new Object[0]);
      assertNotNull(object);
      assertTrue(object instanceof SimpleBean);
      SimpleBean bean = (SimpleBean) object;
      assertEquals("()", bean.getConstructorUsed());
   }

   public void testConstructorOneParam() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      Object object = Config.instantiate(jpf, new String[] { String.class.getName() }, new Object[] { "String" });
      assertNotNull(object);
      assertTrue(object instanceof SimpleBean);
      SimpleBean bean = (SimpleBean) object;
      assertEquals("String", bean.getConstructorUsed());
   }

   public void testConstructorTwoParam() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      Object object = Config.instantiate(jpf, new String[] { String.class.getName(), Object.class.getName() }, new Object[] { "StringObject", new Object() });
      assertNotNull(object);
      assertTrue(object instanceof SimpleBean);
      SimpleBean bean = (SimpleBean) object;
      assertEquals("StringObject", bean.getConstructorUsed());
   }

   public void testSetField() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      SimpleBean bean = new SimpleBean();
      Config.configure(bean, jpf, "publicField", "Hello");
      assertEquals("Hello", bean.publicField);
   }

   public void testUnSetField() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      SimpleBean bean = new SimpleBean();
      Config.unconfigure(bean, jpf, "publicField");
      assertEquals(null, bean.publicField);
   }

   public void testGetField() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      SimpleBean bean = new SimpleBean();
      Joinpoint joinpoint = Config.getFieldGetJoinpoint(bean, jpf, "publicField");
      assertEquals("DefaultValue", joinpoint.dispatch());
   }

   public void testMethodInvoke() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      SimpleBean bean = new SimpleBean();
      Joinpoint joinpoint = Config.getMethodJoinpoint(bean, jpf, "echo", new String[] { String.class.getName() }, new Object[] { "ping" });
      assertEquals("ping", joinpoint.dispatch());
   }

   public void testObjectMethodInvoke() throws Throwable
   {
      JoinpointFactory jpf = getJointpointFactory(SimpleBean.class);
      SimpleBean bean = new SimpleBean();
      Joinpoint joinpoint = Config.getMethodJoinpoint(bean, jpf, "toString", new String[0], new Object[0]);
      assertEquals(bean.toString(), joinpoint.dispatch());
   }

   protected JoinpointFactory getJointpointFactory(Class clazz)
   {
      TypeInfoFactory typeFactory = getTypeInfoFactory();
      ClassInfo info = (ClassInfo) typeFactory.getTypeInfo(clazz);
      return new ReflectJoinpointFactory(info);
   }
   
   protected abstract TypeInfoFactory getTypeInfoFactory();
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.joinpoint");
   }
}