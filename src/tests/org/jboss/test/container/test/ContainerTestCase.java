/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.container.test;

import org.jboss.beans.info.plugins.introspection.IntrospectionBeanInfoFactory;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.container.plugins.bean.BeanContainer;
import org.jboss.container.plugins.bean.BeanContainerFactory;
import org.jboss.container.plugins.joinpoint.bean.GetterJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.MethodJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.SetterJoinPoint;
import org.jboss.container.spi.Container;
import org.jboss.test.BaseTestCase;
import org.jboss.test.container.support.SimpleBean;
import org.jboss.test.container.support.SimpleInterface;

/**
 * Container Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ContainerTestCase extends BaseTestCase
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   public ContainerTestCase(String name)
   {
      super(name);
   }
   
   // Public --------------------------------------------------------

   public void testMethodNoReturnTypeNoParameters() throws Throwable
   {
      SimpleBean target = new SimpleBean();
      Container container = getBeanContainer(target);
      
      assertFalse(target.invokedMethodWithNoReturnTypeNoParameters);

      MethodJoinPoint joinPoint = new MethodJoinPoint("methodWithNoReturnTypeNoParameters");
      container.invoke(joinPoint);

      assertTrue(target.invokedMethodWithNoReturnTypeNoParameters);
   }

   public void testGetA() throws Throwable
   {
      SimpleBean target = new SimpleBean();
      Container container = getBeanContainer(target);
      
      assertNull(target.getA());
      target.setA("testGetA");

      GetterJoinPoint joinPoint = new GetterJoinPoint("a");
      Object result = container.invoke(joinPoint);

      assertEquals("testGetA", result);
   }

   public void testSetA() throws Throwable
   {
      SimpleBean target = new SimpleBean();
      Container container = getBeanContainer(target);
      
      assertNull(target.getA());

      SetterJoinPoint joinPoint = new SetterJoinPoint("a", "testSetA");
      container.invoke(joinPoint);

      assertEquals("testSetA", target.getA());
   }

   public void testProxyMethodNoReturnTypeNoParameters() throws Throwable
   {
      SimpleBean target = new SimpleBean();
      SimpleInterface proxy = (SimpleInterface) getBeanProxy(target);
      
      assertFalse(target.invokedMethodWithNoReturnTypeNoParameters);

      proxy.methodWithNoReturnTypeNoParameters();
      
      assertTrue(target.invokedMethodWithNoReturnTypeNoParameters);
   }
   
   // TestCase overrides --------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   protected Object getBeanProxy(Object target) throws Throwable
   {
      BeanContainer container = getBeanContainer(target);
      return container.getProxy();
   }
   
   protected BeanContainer getBeanContainer(Object target) throws Throwable
   {
      IntrospectionBeanInfoFactory infoFactory = new IntrospectionBeanInfoFactory();
      BeanInfo info = infoFactory.getBeanInfo(target.getClass().getName());
      
      BeanContainerFactory containerFactory = new BeanContainerFactory();
      return containerFactory.createBeanContainer(info, target, null);
   }
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.container");
   }

   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
