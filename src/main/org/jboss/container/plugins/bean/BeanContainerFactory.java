/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.jboss.beans.info.spi.AttributeInfo;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.ClassInfo;
import org.jboss.beans.info.spi.ConstructorInfo;
import org.jboss.beans.info.spi.InterfaceInfo;
import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.container.plugins.joinpoint.bean.ConstructorJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.GetterJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.MethodJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.SetterJoinPoint;
import org.jboss.util.CollectionsFactory;

/**
 * A bean container factory.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BeanContainerFactory 
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new BeanContainer
    * 
    * @param info the bean info
    * @param target the target
    * @param interceptors the interceptors
    * @throws Throwable for any error
    */
   public BeanContainer createBeanContainer(BeanInfo info, Object target, ArrayList interceptors) throws Throwable
   {
      Map contexts = CollectionsFactory.createMap();
      initConstructorContexts(contexts, info, target, interceptors);
      initAttributeContexts(contexts, info, target, interceptors);
      initMethodContexts(contexts, info, target, interceptors);
      Class[] interfaces = getInterfaces(info, target);
      return new BeanContainer(contexts, interfaces);
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   /**
    * Initialise the constructor contexts
    * 
    * @param contexts the contexts
    * @param info the bean info
    * @param target the target
    * @param interceptors the interceptors
    * @throws Throwable for any error
    */
   protected void initConstructorContexts(Map contexts, BeanInfo info, Object target, ArrayList interceptors)
   {
      Set constructors = info.getConstructors();
      if (constructors != null && constructors.isEmpty() == false)
      {
         ConstructorTargetInterceptor targetSetter = new ConstructorTargetInterceptor(contexts);
         ArrayList interceptorsClone = new ArrayList();
         interceptorsClone.add(targetSetter);
         if (interceptors != null)
            interceptorsClone.addAll(interceptors);
         
         for (Iterator i = constructors.iterator(); i.hasNext();)
         {
            ConstructorInfo cinfo = (ConstructorInfo) i.next();
            ConstructorJoinPoint constructor = new ConstructorJoinPoint(cinfo);
            ConstructorInvocationContext ctx = new ConstructorInvocationContext(constructor);
            ctx.setInterceptors(interceptorsClone);
            contexts.put(constructor, ctx);
         }
      }
   }
   
   /**
    * Initialise the attribute contexts
    * 
    * @param contexts the contexts
    * @param info the bean info
    * @param target the target
    * @param interceptors the interceptors
    * @throws Throwable for any error
    */
   protected void initAttributeContexts(Map contexts, BeanInfo info, Object target, ArrayList interceptors)
   {
      Set attributes = info.getAttributes();
      if (attributes != null && attributes.isEmpty() == false)
      {
         ArrayList interceptorsClone = new ArrayList();
         if (interceptors != null)
            interceptorsClone.addAll(interceptors);
         for (Iterator i = attributes.iterator(); i.hasNext();)
         {
            AttributeInfo ainfo = (AttributeInfo) i.next();
            if (ainfo.getGetter() != null)
            {
               GetterJoinPoint getter = new GetterJoinPoint(ainfo);
               getter.setTarget(target);
               GetterInvocationContext getterCtx = new GetterInvocationContext(getter);
               getterCtx.setInterceptors(interceptorsClone);
               contexts.put(getter, getterCtx);
            }
            if (ainfo.getSetter() != null)
            {
               SetterJoinPoint setter = new SetterJoinPoint(ainfo);
               setter.setTarget(target);
               SetterInvocationContext setterCtx = new SetterInvocationContext(setter);
               setterCtx.setInterceptors(interceptorsClone);
               contexts.put(setter, setterCtx);
            }
         }
      }
   }
   
   /**
    * Initialise the method contexts
    * 
    * @param contexts the contexts
    * @param info the bean info
    * @param target the target
    * @param interceptors the interceptors
    * @throws Throwable for any error
    */
   protected void initMethodContexts(Map contexts, BeanInfo info, Object target, ArrayList interceptors) throws Throwable
   {
      Set methods = info.getMethods();
      if (methods != null && methods.isEmpty() == false)
      {
         ArrayList interceptorsClone = new ArrayList();
         if (interceptors != null)
            interceptorsClone.addAll(interceptors);
         for (Iterator i = methods.iterator(); i.hasNext();)
         {
            MethodInfo minfo = (MethodInfo) i.next();
            MethodJoinPoint method = new MethodJoinPoint(minfo);
            method.setTarget(target);
            MethodInvocationContext ctx = new MethodInvocationContext(method);
            ctx.setInterceptors(interceptorsClone);
            contexts.put(method, ctx);
         }
      }
   }
   
   /**
    * Get the interfaces
    * 
    * @param info the bean info
    * @param target the target
    * @return the interfaces
    * @throws Throwable for any error
    */
   protected Class[] getInterfaces(BeanInfo info, Object target) throws Throwable
   {
      ClassInfo type = info.getClassInfo();
      Set intfs = type.getSuperInterfaceInfo();
      Class[] result = new Class[intfs.size()];
      int x = 0;
      if (intfs.isEmpty() == false)
      {
         for (Iterator i = intfs.iterator(); i.hasNext();)
         {
            InterfaceInfo intf = (InterfaceInfo) i.next();
            result[x++] = intf.getType();
         }
      }
      return result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
