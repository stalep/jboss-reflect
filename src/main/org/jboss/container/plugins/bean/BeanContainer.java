/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

import org.jboss.container.plugins.AbstractContainer;
import org.jboss.container.plugins.joinpoint.bean.MethodJoinPoint;
import org.jboss.util.InvocationHandlerSupport;

/**
 * A bean container.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class BeanContainer extends AbstractContainer
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The interfaces */
   protected Class[] interfaces;
   
   /** The proxy for this container */
   protected Object proxy;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new Bean container
    */
   public BeanContainer()
   {
   }
   
   /**
    * Create a new Bean container
    * 
    * @param contexts Map<JoinPoint, InvocationContext>
    */
   public BeanContainer(Map contexts, Class[] interfaces)
   {
      super(contexts);
      this.interfaces = interfaces;
   }
   
   // Public --------------------------------------------------------

   public Object getProxy()
   {
      if (proxy == null)
      {
         ClassLoader cl = Thread.currentThread().getContextClassLoader();
         proxy = Proxy.newProxyInstance(cl, interfaces, new BeanContainerInvocationHandler(this));
      }
      return proxy;
   }
   
   // AbstractContainer overrides -----------------------------------
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
   
   private class BeanContainerInvocationHandler extends InvocationHandlerSupport
   {
      public BeanContainerInvocationHandler(Object target)
      {
         super(target);
      }
      
      public Object handleInvoke(Method method, Object[] args) throws Throwable
      {
         MethodJoinPoint joinPoint = new MethodJoinPoint(method);
         joinPoint.setParameters(args);
         return BeanContainer.this.invoke(joinPoint);
      }
   }
}
