/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.beaninfo.introspection.test;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jboss.beans.info.plugins.AbstractAttributeInfo;
import org.jboss.beans.info.plugins.AbstractClassInfo;
import org.jboss.beans.info.plugins.AbstractConstructorInfo;
import org.jboss.beans.info.plugins.AbstractMethodInfo;
import org.jboss.beans.info.plugins.AbstractParameterInfo;
import org.jboss.beans.info.plugins.introspection.IntrospectionBeanInfoFactory;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.ClassInfo;
import org.jboss.beans.info.spi.InterfaceInfo;
import org.jboss.beans.info.spi.ParameterInfo;
import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.test.BaseTestCase;
import org.jboss.test.beaninfo.introspection.support.SimpleBean;
import org.jboss.test.beaninfo.introspection.support.SimpleInterface;

/**
 * Introspection Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class IntrospectionTestCase extends BaseTestCase
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   public IntrospectionTestCase(String name)
   {
      super(name);
   }

   // Public --------------------------------------------------------

   public void testSimpleBeanBeanInfo() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      log.debug(info);
      assertNotNull(info);
      assertEquals(SimpleBean.class.getName(), info.getName());
   }

   public void testSimpleBeanClassInfo() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      ClassInfo cinfo = info.getClassInfo();
      log.debug(cinfo);
      assertNotNull(cinfo);
      assertEquals(SimpleBean.class.getName(), cinfo.getName());
      ClassInfo supercinfo = cinfo.getSuperClassInfo();
      assertNotNull(supercinfo);
      assertEquals(Object.class.getName(), supercinfo.getName());
      
      HashSet expected = new HashSet();
      expected.add(Serializable.class.getName());
      expected.add(SimpleInterface.class.getName());
      checkTypeSet(expected, cinfo.getSuperInterfaceInfo());
   }

   public void testSimpleInterfaceClassInfo() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      ClassInfo cinfo = info.getClassInfo();
      assertNotNull(cinfo);
      
      InterfaceInfo simpleInterfaceInfo = null;
      for (Iterator i = cinfo.getSuperInterfaceInfo().iterator(); i.hasNext();)
      {
         InterfaceInfo sinfo = (InterfaceInfo) i.next();
         if (SimpleInterface.class.getName().equals(sinfo.getName()))
         {
            simpleInterfaceInfo = sinfo;
            break;
         }
      }
      assertNotNull(simpleInterfaceInfo);
      log.debug(simpleInterfaceInfo);
      
      assertEquals(SimpleInterface.class.getName(), simpleInterfaceInfo.getName());
      assertTrue("No super interfaces", simpleInterfaceInfo.getSuperInterfaceInfo().isEmpty());
   }

   public void testSimpleInterfaceAttributes() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      ClassInfo cinfo = info.getClassInfo();
      assertNotNull(cinfo);
      
      InterfaceInfo simpleInterfaceInfo = null;
      for (Iterator i = cinfo.getSuperInterfaceInfo().iterator(); i.hasNext();)
      {
         InterfaceInfo sinfo = (InterfaceInfo) i.next();
         if (SimpleInterface.class.getName().equals(sinfo.getName()))
         {
            simpleInterfaceInfo = sinfo;
            break;
         }
      }
      assertNotNull(simpleInterfaceInfo);
      
      checkAttributes(getSimpleInterfaceAttributes(), simpleInterfaceInfo.getAttributes());
   }

   public void testSimpleInterfaceMethods() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      ClassInfo cinfo = info.getClassInfo();
      assertNotNull(cinfo);
      
      InterfaceInfo simpleInterfaceInfo = null;
      for (Iterator i = cinfo.getSuperInterfaceInfo().iterator(); i.hasNext();)
      {
         InterfaceInfo sinfo = (InterfaceInfo) i.next();
         if (SimpleInterface.class.getName().equals(sinfo.getName()))
         {
            simpleInterfaceInfo = sinfo;
            break;
         }
      }
      assertNotNull(simpleInterfaceInfo);
      
      checkMethods(getSimpleInterfaceMethods(), simpleInterfaceInfo.getMethods());
   }

   public void testSimpleBeanAttributes() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      
      checkAttributes(getSimpleBeanAttributes(), info.getAttributes());
   }

   public void testSimpleBeanMethods() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      
      checkMethods(getSimpleBeanMethods(), info.getMethods());
   }

   public void testSimpleBeanConstructors() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      
      checkConstructors(getSimpleBeanConstructors(), info.getConstructors());
   }

   public void testSimpleBeanEvents() throws Throwable
   {
      IntrospectionBeanInfoFactory factory = new IntrospectionBeanInfoFactory();
      BeanInfo info = factory.getBeanInfo(SimpleBean.class.getName());
      assertNotNull(info);
      
      assertTrue(info.getEvents().isEmpty());
   }

   // TestCase overrides --------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   protected void checkTypeSet(HashSet expected, Set typeInfos) throws Throwable
   {
      HashSet actual = new HashSet();
      for (Iterator i = typeInfos.iterator(); i.hasNext();)
      {
         TypeInfo tinfo = (TypeInfo) i.next();
         actual.add(tinfo.getName());
      }
      
      HashSet expectClone = new HashSet(expected);
      HashSet actualClone = new HashSet(actual);
      
      log.debug("checkTypeSet expect=" + expected);
      log.debug("checkTypeSet actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }

   protected void checkAttributes(Set expected, Set actual) throws Throwable
   {
      HashSet expectClone = new HashSet(expected);
      HashSet actualClone = new HashSet(actual);
      
      log.debug("checkAttributes expect=" + expected);
      log.debug("checkAttributes actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }

   protected void checkMethods(Set expected, Set actual) throws Throwable
   {
      HashSet expectClone = new HashSet(expected);
      HashSet actualClone = new HashSet(actual);
      
      log.debug("checkMethods expect=" + expected);
      log.debug("checkMethods actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }

   protected void checkConstructors(Set expected, Set actual) throws Throwable
   {
      HashSet expectClone = new HashSet(expected);
      HashSet actualClone = new HashSet(actual);
      
      log.debug("checkConstructors expect=" + expected);
      log.debug("checkConstructors actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }
   
   protected Set getSimpleInterfaceAttributes()
   {
      TypeInfo boolType = new AbstractClassInfo(Boolean.TYPE.getName());
      ParameterInfo boolParam = new AbstractParameterInfo("arg0", boolType);
      List boolParameters = Collections.singletonList(boolParam);

      TypeInfo booleanType = new AbstractClassInfo(Boolean.class.getName());

      TypeInfo objectType = new AbstractClassInfo(Object.class.getName());
      ParameterInfo objectParam = new AbstractParameterInfo("arg0", objectType);
      List objectParameters = Collections.singletonList(objectParam);

      TypeInfo stringType = new AbstractClassInfo(String.class.getName());
      ParameterInfo stringParam = new AbstractParameterInfo("arg0", stringType);
      List stringParameters = Collections.singletonList(stringParam);
      
      HashSet result = new HashSet();
      
      AbstractAttributeInfo withoutSetter = new AbstractAttributeInfo("withoutSetter");
      withoutSetter.setGetter(new AbstractMethodInfo("getWithoutSetter", stringType));
      result.add(withoutSetter);
      
      AbstractAttributeInfo withNoSetterOnInterface = new AbstractAttributeInfo("withNoSetterOnInterface");
      withNoSetterOnInterface.setGetter(new AbstractMethodInfo("getWithNoSetterOnInterface", stringType));
      result.add(withNoSetterOnInterface);
      
      AbstractAttributeInfo withNoGetterOnInterface = new AbstractAttributeInfo("withNoGetterOnInterface");
      withNoGetterOnInterface.setSetter(new AbstractMethodInfo("setWithNoGetterOnInterface", objectParameters));
      result.add(withNoGetterOnInterface);
      
      AbstractAttributeInfo withoutGetter = new AbstractAttributeInfo("withoutGetter");
      withoutGetter.setSetter(new AbstractMethodInfo("setWithoutGetter", stringParameters));
      result.add(withoutGetter);

      AbstractAttributeInfo withSetter = new AbstractAttributeInfo("withSetter");
      withSetter.setGetter(new AbstractMethodInfo("getWithSetter", stringType));
      withSetter.setSetter(new AbstractMethodInfo("setWithSetter", stringParameters));
      result.add(withSetter);
      
      AbstractAttributeInfo primitiveIS = new AbstractAttributeInfo("primitiveIS");
      primitiveIS.setGetter(new AbstractMethodInfo("isPrimitiveIS", boolType));
      result.add(primitiveIS);
      
      AbstractAttributeInfo booleanIS = new AbstractAttributeInfo("booleanIS");
      booleanIS.setGetter(new AbstractMethodInfo("isBooleanIS", booleanType));
      result.add(booleanIS);

      AbstractAttributeInfo a = new AbstractAttributeInfo("a");
      a.setGetter(new AbstractMethodInfo("getA", stringType));
      a.setSetter(new AbstractMethodInfo("setA", stringParameters));
      result.add(a);

      AbstractAttributeInfo b = new AbstractAttributeInfo("b");
      b.setGetter(new AbstractMethodInfo("isB", boolType));
      b.setSetter(new AbstractMethodInfo("setB", boolParameters));
      result.add(b);
      
      AbstractAttributeInfo doesNotMatchSetter = new AbstractAttributeInfo("doesNotMatchSetter");
      doesNotMatchSetter.setGetter(new AbstractMethodInfo("getDoesNotMatchSetter", stringType));
      result.add(doesNotMatchSetter);
      
      AbstractAttributeInfo doesNotMatchGetter = new AbstractAttributeInfo("doesNotMatchGetter");
      doesNotMatchGetter.setSetter(new AbstractMethodInfo("setDoesNotMatchGetter", objectParameters));
      result.add(doesNotMatchGetter);
      
      return result;
   }
   
   protected Set getSimpleInterfaceMethods()
   {
      TypeInfo boolType = new AbstractClassInfo(Boolean.TYPE.getName());
      ParameterInfo boolParam = new AbstractParameterInfo("arg0", boolType);
      List boolParameters = Collections.singletonList(boolParam);

      TypeInfo booleanType = new AbstractClassInfo(Boolean.class.getName());

      TypeInfo intType = new AbstractClassInfo(Integer.TYPE.getName());
      ParameterInfo intParam = new AbstractParameterInfo("arg0", intType);
      List intParameters = Collections.singletonList(intParam);

      TypeInfo objectType = new AbstractClassInfo(Object.class.getName());
      ParameterInfo objectParam = new AbstractParameterInfo("arg0", objectType);
      List objectParameters = Collections.singletonList(objectParam);

      TypeInfo stringType = new AbstractClassInfo(String.class.getName());
      ParameterInfo stringParam = new AbstractParameterInfo("arg0", stringType);
      List stringParameters = Collections.singletonList(stringParam);

      TypeInfo urlType = new AbstractClassInfo(URL.class.getName());
      ParameterInfo urlParam = new AbstractParameterInfo("arg1", urlType);
      ArrayList twoParameters = new ArrayList();
      twoParameters.add(stringParam);
      twoParameters.add(urlParam);
      
      HashSet result = new HashSet();
      
      result.add(new AbstractMethodInfo("getWithoutSetter", stringType));
      result.add(new AbstractMethodInfo("getWithNoSetterOnInterface", stringType));
      result.add(new AbstractMethodInfo("setWithNoGetterOnInterface", objectParameters));
      result.add(new AbstractMethodInfo("setWithoutGetter", stringParameters));
      result.add(new AbstractMethodInfo("getWithSetter", stringType));
      result.add(new AbstractMethodInfo("setWithSetter", stringParameters));
      result.add(new AbstractMethodInfo("isPrimitiveIS", boolType));
      result.add(new AbstractMethodInfo("isBooleanIS", booleanType));
      result.add(new AbstractMethodInfo("getA", stringType));
      result.add(new AbstractMethodInfo("setA", stringParameters));
      result.add(new AbstractMethodInfo("isB", boolType));
      result.add(new AbstractMethodInfo("setB", boolParameters));
      result.add(new AbstractMethodInfo("getDoesNotMatchSetter", stringType));
      result.add(new AbstractMethodInfo("setDoesNotMatchGetter", objectParameters));
      result.add(new AbstractMethodInfo("methodWithNoReturnTypeNoParameters"));
      result.add(new AbstractMethodInfo("methodWithNoReturnTypeOneParameter", stringParameters));
      result.add(new AbstractMethodInfo("methodWithNoReturnTypeTwoParameters", twoParameters));
      result.add(new AbstractMethodInfo("methodWithReturnTypeNoParameters", objectType));
      result.add(new AbstractMethodInfo("methodWithReturnTypeOneParameter", stringType, stringParameters));
      result.add(new AbstractMethodInfo("methodWithReturnTypeTwoParameters", urlType, twoParameters));
      result.add(new AbstractMethodInfo("get", objectType));
      result.add(new AbstractMethodInfo("is", boolType));
      result.add(new AbstractMethodInfo("set", objectParameters));
      result.add(new AbstractMethodInfo("methodWithPrimitiveReturnType", intType));
      result.add(new AbstractMethodInfo("methodWithPrimitiveParameter", intParameters));
      result.add(new AbstractMethodInfo("overloadedMethod", objectParameters));
      result.add(new AbstractMethodInfo("overloadedMethod", stringParameters));
      
      return result;
   }
   
   protected Set getSimpleBeanAttributes()
   {
      TypeInfo objectType = new AbstractClassInfo(Object.class.getName());
      ParameterInfo objectParam = new AbstractParameterInfo("arg0", objectType);
      List objectParameters = Collections.singletonList(objectParam);

      TypeInfo stringType = new AbstractClassInfo(String.class.getName());
      ParameterInfo stringParam = new AbstractParameterInfo("arg0", stringType);
      List stringParameters = Collections.singletonList(stringParam);

      Set result = getSimpleInterfaceAttributes();
      for (Iterator i = result.iterator(); i.hasNext();)
      {
         AbstractAttributeInfo info = (AbstractAttributeInfo) i.next();
         if (info.getName().equals("withNoGetterOnInterface"))
            i.remove();
         else if (info.getName().equals("withNoSetterOnInterface"))
            i.remove();
      }
      
      AbstractAttributeInfo withNoSetterOnInterface = new AbstractAttributeInfo("withNoSetterOnInterface");
      withNoSetterOnInterface.setGetter(new AbstractMethodInfo("getWithNoSetterOnInterface", stringType));
      withNoSetterOnInterface.setSetter(new AbstractMethodInfo("setWithNoSetterOnInterface", stringParameters));
      result.add(withNoSetterOnInterface);
      
      AbstractAttributeInfo withNoGetterOnInterface = new AbstractAttributeInfo("withNoGetterOnInterface");
      withNoGetterOnInterface.setGetter(new AbstractMethodInfo("getWithNoGetterOnInterface", objectType));
      withNoGetterOnInterface.setSetter(new AbstractMethodInfo("setWithNoGetterOnInterface", objectParameters));
      result.add(withNoGetterOnInterface);
      
      AbstractAttributeInfo attributeNotSimpleInterface = new AbstractAttributeInfo("attributeNotSimpleInterface");
      attributeNotSimpleInterface.setGetter(new AbstractMethodInfo("getAttributeNotSimpleInterface", objectType));
      result.add(attributeNotSimpleInterface);
      
      result.addAll(getObjectAttributes());
      
      return result;
   }
   
   protected Set getSimpleBeanMethods()
   {
      TypeInfo objectType = new AbstractClassInfo(Object.class.getName());
      ParameterInfo objectParam = new AbstractParameterInfo("arg0", objectType);
      List objectParameters = Collections.singletonList(objectParam);

      TypeInfo stringType = new AbstractClassInfo(String.class.getName());
      ParameterInfo stringParam = new AbstractParameterInfo("arg0", stringType);
      List stringParameters = Collections.singletonList(stringParam);

      Set result = getSimpleInterfaceMethods();

      result.add(new AbstractMethodInfo("getWithNoSetterOnInterface", stringType));
      result.add(new AbstractMethodInfo("setWithNoSetterOnInterface", stringParameters));
      result.add(new AbstractMethodInfo("getWithNoGetterOnInterface", objectType));
      result.add(new AbstractMethodInfo("setWithNoGetterOnInterface", objectParameters));
      result.add(new AbstractMethodInfo("getAttributeNotSimpleInterface", objectType));
      result.add(new AbstractMethodInfo("methodNotSimpleInterface"));
      
      result.addAll(getObjectMethods());
      
      return result;
   }
   
   protected Set getSimpleBeanConstructors()
   {
      TypeInfo stringType = new AbstractClassInfo(String.class.getName());
      ParameterInfo stringParam = new AbstractParameterInfo("arg0", stringType);
      List stringParameters = Collections.singletonList(stringParam);

      HashSet result = new HashSet();
      
      result.add(new AbstractConstructorInfo());
      result.add(new AbstractConstructorInfo(stringParameters));
      
      return result;
   }
   
   protected Set getObjectAttributes()
   {
      TypeInfo classType = new AbstractClassInfo(Class.class.getName());

      HashSet result = new HashSet();
      
      AbstractAttributeInfo clazz = new AbstractAttributeInfo("class");
      clazz.setGetter(new AbstractMethodInfo("getClass", classType));
      result.add(clazz);
      
      return result;
   }
   
   protected Set getObjectMethods()
   {
      TypeInfo classType = new AbstractClassInfo(Class.class.getName());

      TypeInfo intType = new AbstractClassInfo(Integer.TYPE.getName());
      ParameterInfo intParam = new AbstractParameterInfo("arg1", intType);

      TypeInfo longType = new AbstractClassInfo(Long.TYPE.getName());
      ParameterInfo longParam = new AbstractParameterInfo("arg0", longType);
      List longParameters = Collections.singletonList(longParam);

      TypeInfo stringType = new AbstractClassInfo(String.class.getName());

      TypeInfo boolType = new AbstractClassInfo(Boolean.TYPE.getName());

      TypeInfo objectType = new AbstractClassInfo(Object.class.getName());
      ParameterInfo objectParam = new AbstractParameterInfo("arg0", objectType);
      List objectParameters = Collections.singletonList(objectParam);

      ArrayList longintParameters = new ArrayList();
      longintParameters.add(longParam);
      longintParameters.add(intParam);
      
      HashSet result = new HashSet();
      
      result.add(new AbstractMethodInfo("getClass", classType));
      result.add(new AbstractMethodInfo("equals", boolType, objectParameters));
      result.add(new AbstractMethodInfo("hashCode", intType));
      result.add(new AbstractMethodInfo("toString", stringType));
      result.add(new AbstractMethodInfo("notify"));
      result.add(new AbstractMethodInfo("notifyAll"));
      result.add(new AbstractMethodInfo("wait"));
      result.add(new AbstractMethodInfo("wait", longParameters));
      result.add(new AbstractMethodInfo("wait", longintParameters));
      
      return result;
   }
   
   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------

}