/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.joinpoint.reflect.test;

import org.jboss.joinpoint.Joinpoint;
import org.jboss.joinpoint.JoinpointFactory;
import org.jboss.joinpoint.config.Config;
import org.jboss.joinpoint.plugins.reflect.ReflectJoinpointFactory;
import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.test.BaseTestCase;
import org.jboss.test.joinpoint.reflect.support.SimpleBean;

/**
 * Joinpoint Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ReflectJoinpointTestCase extends BaseTestCase
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   public ReflectJoinpointTestCase(String name)
   {
      super(name);
   }

   // Public --------------------------------------------------------

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
   
   // TestCase overrides --------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   protected JoinpointFactory getJointpointFactory(Class clazz)
   {
      IntrospectionTypeInfoFactory typeFactory = new IntrospectionTypeInfoFactory();
      ClassInfo info = (ClassInfo) typeFactory.getTypeInfo(clazz);
      return new ReflectJoinpointFactory(info);
   }
   
   protected void configureLogging()
   {
      enableTrace("org.jboss.joinpoint");
   }
   
   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------

}