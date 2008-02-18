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
package org.jboss.test.beaninfo.test;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Test;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.test.beaninfo.support.BeanInfoAnnotatedGetterAndSetter;
import org.jboss.test.beaninfo.support.BeanInfoAnnotatedGetterAndSetterSimpleMerge;
import org.jboss.test.beaninfo.support.BeanInfoAnnotatedGetterAndSetterWithInterface;
import org.jboss.test.beaninfo.support.BeanInfoAnnotatedGetterOnly;
import org.jboss.test.beaninfo.support.BeanInfoAnnotatedSetterOnly;
import org.jboss.test.beaninfo.support.BeanInfoAnnotation;
import org.jboss.test.beaninfo.support.BeanInfoBooleanProperties;
import org.jboss.test.beaninfo.support.BeanInfoConstructors;
import org.jboss.test.beaninfo.support.BeanInfoDefaultConstructor;
import org.jboss.test.beaninfo.support.BeanInfoDoubleCovariantImpl;
import org.jboss.test.beaninfo.support.BeanInfoEmpty;
import org.jboss.test.beaninfo.support.BeanInfoGenericGetterAndSetter;
import org.jboss.test.beaninfo.support.BeanInfoGenericGetterOnly;
import org.jboss.test.beaninfo.support.BeanInfoGenericInconsistentTypes;
import org.jboss.test.beaninfo.support.BeanInfoGenericInterfaceImpl;
import org.jboss.test.beaninfo.support.BeanInfoGenericSetterOnly;
import org.jboss.test.beaninfo.support.BeanInfoGetterAndSetter;
import org.jboss.test.beaninfo.support.BeanInfoGetterOnly;
import org.jboss.test.beaninfo.support.BeanInfoInconsistentTypes;
import org.jboss.test.beaninfo.support.BeanInfoInterface;
import org.jboss.test.beaninfo.support.BeanInfoParameterConstructor;
import org.jboss.test.beaninfo.support.BeanInfoProperties;
import org.jboss.test.beaninfo.support.BeanInfoSetterOnly;
import org.jboss.test.beaninfo.support.BeanInfoUpperPropertyName;

/**
 * BeanInfo Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 45663 $
 */
public class BeanInfoUnitTestCase extends AbstractBeanInfoTest
{
   public static Test suite()
   {
      return suite(BeanInfoUnitTestCase.class);
   }
   
   public BeanInfoUnitTestCase(String name)
   {
      super(name);
   }
   
   public void testEmptyBean() throws Throwable
   {
      testBean(BeanInfoEmpty.class, null);
   }
   
   public void testBeanConstructors() throws Throwable
   {
      testBean(BeanInfoConstructors.class, null);
   }
   
   public void testBeanMethods() throws Throwable
   {
      testBean(BeanInfoConstructors.class, null);
   }
   
   public void testBeanGetterOnly() throws Throwable
   {
      testBean(BeanInfoGetterOnly.class, new String[] { "something" });
   }
   
   public void testBeanSetterOnly() throws Throwable
   {
      testBean(BeanInfoSetterOnly.class, new String[] { "something" });
   }
   
   public void testBeanGetterAndSetter() throws Throwable
   {
      testBean(BeanInfoGetterAndSetter.class, new String[] { "something" });
   }
   
   public void testBeanBooleanProperties() throws Throwable
   {
      testBean(BeanInfoBooleanProperties.class, new String[] { "something", "somethingElse" });
   }
   
   public void testBeanUpperPropertyName() throws Throwable
   {
      testBean(BeanInfoUpperPropertyName.class, new String[] { "MBean" });
   }
   
   public void testBeanInconsistentTypes() throws Throwable
   {
      testBean(BeanInfoInconsistentTypes.class, new String[] { "something" });
   }
   
   public void testBeanGenericGetterOnly() throws Throwable
   {
      testBean(BeanInfoGenericGetterOnly.class, new String[] { "something" });
   }
   
   public void testBeanGenericSetterOnly() throws Throwable
   {
      testBean(BeanInfoGenericSetterOnly.class, new String[] { "something" });
   }
   
   public void testBeanGenericGetterAndSetter() throws Throwable
   {
      testBean(BeanInfoGenericGetterAndSetter.class, new String[] { "something" });
   }
   
   public void testBeanGenericInconsistentTypes() throws Throwable
   {
      testBean(BeanInfoGenericInconsistentTypes.class, new String[] { "something" });
   }
   
   public void testBeanAnnotatedGetterOnly() throws Throwable
   {
      testBean(BeanInfoAnnotatedGetterOnly.class, new String[] { "something" });
   }
   
   public void testBeanAnnotatedSetterOnly() throws Throwable
   {
      testBean(BeanInfoAnnotatedSetterOnly.class, new String[] { "something" });
   }
   
   public void testBeanAnnotatedGetterAndSetter() throws Throwable
   {
      testBean(BeanInfoAnnotatedGetterAndSetter.class, new String[] { "something" });
   }
   
   public void testBeanAnnotatedGetterAndSetterWithInterface() throws Throwable
   {
      testBean(BeanInfoAnnotatedGetterAndSetterWithInterface.class, new String[] { "something" });
   }
   
   public void testBeanAnnotatedGetterAndSetterSimpleMerge() throws Throwable
   {
      testBean(BeanInfoAnnotatedGetterAndSetterSimpleMerge.class, new String[] { "something" });
   }
   
   public void testBeanInterface() throws Throwable
   {
      testBean(BeanInfoInterface.class, new String[] { "something" });
   }
   
   public void testBeanAnnotation() throws Throwable
   {
      testBean(BeanInfoAnnotation.class, new String[] { "something" });
   }

   public void testDefaultConstructor() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoDefaultConstructor.class);
      assertNotNull(beanInfo);
      Object object = beanInfo.newInstance();
      BeanInfoDefaultConstructor bean = assertInstanceOf(object, BeanInfoDefaultConstructor.class);
      assertTrue(bean.invoked);
   }

   public void testParameterConstructor() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoParameterConstructor.class);
      assertNotNull(beanInfo);
      String invoked = "invoked";
      Object object = beanInfo.newInstance(new String[] { String.class.getName() }, new Object[] { invoked });
      BeanInfoParameterConstructor bean = assertInstanceOf(object, BeanInfoParameterConstructor.class);
      assertTrue(invoked == bean.invoked);
   }

   public void testGet() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoProperties.class);
      assertNotNull(beanInfo);
      String invoked = "invoked";
      BeanInfoProperties bean = assertInstanceOf(beanInfo.newInstance(), BeanInfoProperties.class);
      bean.notInvoked = invoked;
      assertTrue(invoked == beanInfo.getProperty(bean, "invoked"));
   }

   public void testSet() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoProperties.class);
      assertNotNull(beanInfo);
      String invoked = "invoked";
      BeanInfoProperties bean = assertInstanceOf(beanInfo.newInstance(), BeanInfoProperties.class);
      assertNull(bean.notInvoked);
      beanInfo.setProperty(bean, "invoked", invoked);
      assertTrue(invoked == bean.notInvoked);
   }

   public void testInvokeNoParametersAndResult() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoProperties.class);
      assertNotNull(beanInfo);
      String invoked = "invoked";
      BeanInfoProperties bean = assertInstanceOf(beanInfo.newInstance(), BeanInfoProperties.class);
      bean.notInvoked = invoked;
      assertTrue(invoked == beanInfo.invoke(bean, "getInvoked"));
   }

   public void testInvokeWithParameters() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoProperties.class);
      assertNotNull(beanInfo);
      String invoked = "invoked";
      BeanInfoProperties bean = assertInstanceOf(beanInfo.newInstance(), BeanInfoProperties.class);
      assertNull(bean.notInvoked);
      beanInfo.invoke(bean, "setInvoked", new String[] { String.class.getName() }, new Object[] { invoked });
      assertTrue(invoked == bean.notInvoked);
   }
   
   public void testGenericInterfaceImpl() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoGenericInterfaceImpl.class);
      assertNotNull(beanInfo);
      PropertyInfo property = beanInfo.getProperty("property");
      assertNotNull(property);
      assertEquals("java.lang.String", property.getType().getName());
   }
   
   public void testCovariantImpl() throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(BeanInfoDoubleCovariantImpl.class);
      assertNotNull(beanInfo);
      PropertyInfo property = beanInfo.getProperty("property");
      assertNotNull(property);
      assertEquals("java.lang.Double", property.getType().getName());
   }

   protected void testBean(Class<?> clazz, String[] beanNames) throws Throwable
   {
      BeanInfo beanInfo = getBeanInfo(clazz);
      assertBeanInfo(beanInfo, clazz);
      if (beanNames != null)
      {
         Set<PropertyInfo> properties = beanInfo.getProperties();
         Set<String> props = new HashSet<String>();
         for (PropertyInfo p : properties)
            props.add(p.getName());
         Set<String> expected = new HashSet<String>();
         for (String beanName : beanNames)
            expected.add(beanName);
         if (clazz.isInterface() == false)
            expected.add("class");
         assertEquals(expected, props);
      }
   }
}