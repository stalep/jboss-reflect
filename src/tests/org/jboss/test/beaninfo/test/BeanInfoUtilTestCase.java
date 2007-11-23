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
package org.jboss.test.beaninfo.test;

import junit.framework.Test;
import org.jboss.test.beaninfo.support.NestedBean;
import org.jboss.beans.info.plugins.BeanInfoUtil;
import org.jboss.beans.info.spi.BeanInfo;

/**
 * BeanInfoUtil Test Case.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class BeanInfoUtilTestCase extends AbstractBeanInfoTest
{
   public static Test suite()
   {
      return suite(BeanInfoUtilTestCase.class);
   }

   public BeanInfoUtilTestCase(String name)
   {
      super(name);
   }

   protected BeanInfo getBeanInfo() throws Throwable
   {
      return getBeanInfo(NestedBean.class);
   }

   public void testSimpleGet() throws Throwable
   {
      NestedBean child = new NestedBean();
      NestedBean parent = new NestedBean(child);
      assertSame(child, BeanInfoUtil.get(getBeanInfo(), parent, "nestedBean"));
   }

   public void testSimpleSet() throws Throwable
   {
      NestedBean child = new NestedBean();
      NestedBean parent = new NestedBean();
      BeanInfoUtil.set(getBeanInfo(), parent, "nestedBean", child);
      assertSame(child, parent.getNestedBean());
   }

   public void testNestedGet() throws Throwable
   {
      NestedBean grandchild = new NestedBean();
      NestedBean child = new NestedBean(grandchild);
      NestedBean parent = new NestedBean(child);
      assertSame(grandchild, BeanInfoUtil.get(getBeanInfo(), parent, "nestedBean.nestedBean"));
   }

   public void testNestedSet() throws Throwable
   {
      NestedBean grandchild = new NestedBean();
      NestedBean child = new NestedBean();
      NestedBean parent = new NestedBean(child);
      BeanInfoUtil.set(getBeanInfo(), parent, "nestedBean.nestedBean", grandchild);
      assertSame(child, parent.getNestedBean());
      assertSame(grandchild, child.getNestedBean());
   }

   public void testNestedGetFail() throws Throwable
   {
      try
      {
         NestedBean grandchild = new NestedBean();
         NestedBean child = new NestedBean(grandchild);
         NestedBean parent = new NestedBean(child);
         assertSame(grandchild, BeanInfoUtil.get(getBeanInfo(), parent, "differentGetter.nestedBean"));
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testNestedSetFail() throws Throwable
   {
      try
      {
         NestedBean grandchild = new NestedBean();
         NestedBean child = new NestedBean();
         NestedBean parent = new NestedBean(child);
         BeanInfoUtil.set(getBeanInfo(), parent, "differentGetter.nestedBean", grandchild);
         assertSame(child, parent.getNestedBean());
         assertSame(grandchild, child.getNestedBean());
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }
}
