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
import org.jboss.beans.info.plugins.BeanInfoUtil;
import org.jboss.beans.info.spi.BeanAccessMode;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.test.beaninfo.support.NestedBean;
import org.jboss.test.beaninfo.support.PrivateGetterNestedBean;
import org.jboss.test.beaninfo.support.PrivateMixNestedBean;
import org.jboss.test.beaninfo.support.PrivateNestedBean;
import org.jboss.test.beaninfo.support.PrivateSetterNestedBean;
import org.jboss.test.beaninfo.support.PubGetterNestedBean;
import org.jboss.test.beaninfo.support.PubMixNestedBean;
import org.jboss.test.beaninfo.support.PubNestedBean;
import org.jboss.test.beaninfo.support.PubSetterNestedBean;
import org.jboss.test.beaninfo.support.SetGetHook;
import org.jboss.test.beaninfo.support.SubPubSetterNestedBean;
import org.jboss.test.beaninfo.support.SubPubNestedBean;
import org.jboss.test.beaninfo.support.SubPrivateMixNestedBean;
import org.jboss.test.beaninfo.support.SubPrivateNestedBean;
import org.jboss.test.beaninfo.support.SubPrivateSetterNestedBean;
import org.jboss.test.beaninfo.support.SubPubMixNestedBean;
import org.jboss.test.beaninfo.support.SubPubGetterNestedBean;
import org.jboss.test.beaninfo.support.SubPrivateGetterNestedBean;

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

   protected <T extends SetGetHook<T>> void checkSimpleGet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      T parent = clazz.newInstance();
      T child = clazz.newInstance();
      parent.doSetHook(child);
      assertSame(child, BeanInfoUtil.get(getBeanInfo(clazz, mode), parent, "bean"));
      assertTrue(parent.valid());
   }

   protected <T extends SetGetHook<T>> void failSimpleGet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      try
      {
         checkSimpleGet(clazz, mode);
         fail("Should not be here.");
      }
      catch(Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testSimpleGet() throws Throwable
   {
      checkSimpleGet(NestedBean.class, BeanAccessMode.STANDARD);
      checkSimpleGet(NestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleGet(NestedBean.class, BeanAccessMode.ALL);

      checkSimpleGet(PubNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleGet(PubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleGet(PubGetterNestedBean.class, BeanAccessMode.FIELDS);
      failSimpleGet(PubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkSimpleGet(SubPubNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleGet(SubPubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleGet(SubPubGetterNestedBean.class, BeanAccessMode.FIELDS);
      failSimpleGet(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkSimpleGet(PrivateNestedBean.class, BeanAccessMode.ALL);
      checkSimpleGet(PrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkSimpleGet(PrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkSimpleGet(PrivateGetterNestedBean.class, BeanAccessMode.ALL);

      checkSimpleGet(SubPrivateNestedBean.class, BeanAccessMode.ALL);
      checkSimpleGet(SubPrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkSimpleGet(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkSimpleGet(SubPrivateGetterNestedBean.class, BeanAccessMode.ALL);
   }

   protected <T extends SetGetHook<T>> void checkSimpleSet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      T parent = clazz.newInstance();
      T child = clazz.newInstance();
      BeanInfoUtil.set(getBeanInfo(clazz, mode), parent, "bean", child);
      assertTrue(parent.valid());
      assertSame(child, parent.doGetHook());
   }

   protected <T extends SetGetHook<T>> void failSimpleSet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      try
      {
         checkSimpleSet(clazz, mode);
         fail("Should not be here.");
      }
      catch(Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testSimpleSet() throws Throwable
   {
      checkSimpleSet(NestedBean.class, BeanAccessMode.STANDARD);
      checkSimpleSet(NestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleSet(NestedBean.class, BeanAccessMode.ALL);

      checkSimpleSet(PubNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleSet(PubMixNestedBean.class, BeanAccessMode.FIELDS);
      failSimpleSet(PubGetterNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleSet(PubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkSimpleSet(SubPubNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleSet(SubPubMixNestedBean.class, BeanAccessMode.FIELDS);
      failSimpleSet(SubPubGetterNestedBean.class, BeanAccessMode.FIELDS);
      checkSimpleSet(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkSimpleSet(PrivateNestedBean.class, BeanAccessMode.ALL);
      checkSimpleSet(PrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkSimpleSet(PrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkSimpleSet(PrivateGetterNestedBean.class, BeanAccessMode.ALL);

      checkSimpleSet(SubPrivateNestedBean.class, BeanAccessMode.ALL);
      checkSimpleSet(SubPrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkSimpleSet(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkSimpleSet(SubPrivateGetterNestedBean.class, BeanAccessMode.ALL);
   }

   protected <T extends SetGetHook<T>> void checkNestedGet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      T parent = clazz.newInstance();
      T child = clazz.newInstance();
      T grandchild = clazz.newInstance();
      parent.doSetHook(child);
      child.doSetHook(grandchild);
      assertSame(grandchild, BeanInfoUtil.get(getBeanInfo(clazz, mode), parent, "bean.bean"));
   }

   protected <T extends SetGetHook<T>> void failNestedGet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      try
      {
         checkNestedGet(clazz, mode);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testNestedGet() throws Throwable
   {
      checkNestedGet(NestedBean.class, BeanAccessMode.STANDARD);
      checkNestedGet(NestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGet(NestedBean.class, BeanAccessMode.ALL);

      failNestedGet(PubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGet(PubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGet(PubGetterNestedBean.class, BeanAccessMode.FIELDS);
      failNestedGet(PubSetterNestedBean.class, BeanAccessMode.FIELDS);

      failNestedGet(SubPubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGet(SubPubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGet(SubPubGetterNestedBean.class, BeanAccessMode.FIELDS);
      failNestedGet(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);

      failNestedGet(PrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedGet(PrivateMixNestedBean.class, BeanAccessMode.ALL);
      failNestedGet(PrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkNestedGet(PrivateGetterNestedBean.class, BeanAccessMode.ALL);

      failNestedGet(SubPrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedGet(SubPrivateMixNestedBean.class, BeanAccessMode.ALL);
      failNestedGet(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkNestedGet(SubPrivateGetterNestedBean.class, BeanAccessMode.ALL);
   }

   protected <T extends SetGetHook<T>> void checkNestedSet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      T parent = clazz.newInstance();
      T child = clazz.newInstance();
      T grandchild = clazz.newInstance();
      parent.doSetHook(child);
      BeanInfoUtil.set(getBeanInfo(clazz, mode), parent, "bean.bean", grandchild);
      assertTrue(parent.valid());
      assertTrue(child.valid());
      assertSame(grandchild, parent.doGetHook().doGetHook());
   }

   protected <T extends SetGetHook<T>> void failNestedSet(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      try
      {
         checkNestedSet(clazz, mode);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testNestedSet() throws Throwable
   {
      checkNestedSet(NestedBean.class, BeanAccessMode.STANDARD);
      checkNestedSet(NestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSet(NestedBean.class, BeanAccessMode.ALL);

      failNestedSet(PubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSet(PubMixNestedBean.class, BeanAccessMode.FIELDS);
      failNestedSet(PubGetterNestedBean.class, BeanAccessMode.FIELDS);
      failNestedSet(PubSetterNestedBean.class, BeanAccessMode.FIELDS);

      failNestedSet(SubPubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSet(SubPubMixNestedBean.class, BeanAccessMode.FIELDS);
      failNestedSet(SubPubGetterNestedBean.class, BeanAccessMode.FIELDS);
      failNestedSet(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);

      failNestedSet(PrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedSet(PrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkNestedSet(PrivateSetterNestedBean.class, BeanAccessMode.ALL);
      failNestedSet(PrivateGetterNestedBean.class, BeanAccessMode.ALL);

      failNestedSet(SubPrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedSet(SubPrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkNestedSet(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
      failNestedSet(SubPrivateGetterNestedBean.class, BeanAccessMode.ALL);
   }

   protected <T extends SetGetHook<T>> void checkNestedGetFail(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      T parent = clazz.newInstance();
      T child = clazz.newInstance();
      T grandchild = clazz.newInstance();
      T grandgrandchild = clazz.newInstance();
      parent.doSetHook(child);
      // missing link
      grandchild.doSetHook(grandgrandchild);
      try
      {
         BeanInfoUtil.get(getBeanInfo(clazz, mode), parent, "bean.bean.bean");
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testNestedGetFail() throws Throwable
   {
      checkNestedGetFail(NestedBean.class, BeanAccessMode.STANDARD);
      checkNestedGetFail(NestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(NestedBean.class, BeanAccessMode.ALL);

      checkNestedGetFail(PubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(PubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(PubGetterNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(PubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkNestedGetFail(SubPubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(SubPubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(SubPubGetterNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedGetFail(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkNestedGetFail(PrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedGetFail(PrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkNestedGetFail(PrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkNestedGetFail(PrivateGetterNestedBean.class, BeanAccessMode.ALL);

      checkNestedGetFail(SubPrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedGetFail(SubPrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkNestedGetFail(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkNestedGetFail(SubPrivateGetterNestedBean.class, BeanAccessMode.ALL);
   }

   protected <T extends SetGetHook<T>> void checkNestedSetFail(Class<T> clazz, BeanAccessMode mode) throws Throwable
   {
      T parent = clazz.newInstance();
      T child = clazz.newInstance();
      // missing link
      T grandgrandchild = clazz.newInstance();
      parent.doSetHook(child);
      try
      {
         BeanInfoUtil.set(getBeanInfo(clazz, mode), parent, "bean.bean.bean", grandgrandchild);
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }

   public void testNestedSetFail() throws Throwable
   {
      checkNestedSetFail(NestedBean.class, BeanAccessMode.STANDARD);
      checkNestedSetFail(NestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(NestedBean.class, BeanAccessMode.ALL);

      checkNestedSetFail(PubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(PubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(PubSetterNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(PubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkNestedSetFail(SubPubNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(SubPubMixNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);
      checkNestedSetFail(SubPubSetterNestedBean.class, BeanAccessMode.FIELDS);

      checkNestedSetFail(PrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedSetFail(PrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkNestedSetFail(PrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkNestedSetFail(PrivateSetterNestedBean.class, BeanAccessMode.ALL);

      checkNestedSetFail(SubPrivateNestedBean.class, BeanAccessMode.ALL);
      checkNestedSetFail(SubPrivateMixNestedBean.class, BeanAccessMode.ALL);
      checkNestedSetFail(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
      checkNestedSetFail(SubPrivateSetterNestedBean.class, BeanAccessMode.ALL);
   }

   public void testNestedPropertyInfo() throws Throwable
   {
      NestedBean grandchild = new NestedBean();
      NestedBean child = new NestedBean();
      child.doSetHook(grandchild);
      NestedBean parent = new NestedBean();
      parent.doSetHook(child);
      BeanInfo beanInfo = getBeanInfo(NestedBean.class);
      PropertyInfo propertyInfo = beanInfo.getProperty("string");
      PropertyInfo nestedPropertyInfo = BeanInfoUtil.getPropertyInfo(beanInfo, parent, "bean.otherBean.string");
      assertEquals(propertyInfo, nestedPropertyInfo);
   }

   public void testNestedPropertyInfoFail() throws Throwable
   {
      try
      {
         NestedBean child = new NestedBean();
         NestedBean parent = new NestedBean();
         parent.doSetHook(child);
         BeanInfo beanInfo = getBeanInfo(NestedBean.class);
         BeanInfoUtil.getPropertyInfo(beanInfo, parent, "bean.differentGetter.string");
         fail("Should not be here.");
      }
      catch (Throwable t)
      {
         assertInstanceOf(t, IllegalArgumentException.class);
      }
   }
}
