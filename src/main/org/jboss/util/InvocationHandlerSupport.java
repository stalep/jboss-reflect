/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.util;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * A handler to proxy notification listener requests.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class InvocationHandlerSupport implements InvocationHandler
{
   // Constants -----------------------------------------------------

   /** Object equals */
   protected static final Method EQUALS;

   /** Object hashCode */
   protected static final Method HASHCODE;

   /** Object toString */
   protected static final Method TOSTRING;
   
   // Attributes ----------------------------------------------------

   /** The target */
   protected Object target;
   
   // Static --------------------------------------------------------

   static
   {
      try
      {
         Class objectClass = Object.class;
         EQUALS = objectClass.getMethod("equals", new Class[] { objectClass });
         HASHCODE = objectClass.getMethod("hashCode", null);
         TOSTRING = objectClass.getMethod("toString", null);
      }
      catch (Exception e)
      {
         throw new RuntimeException(e);
      }
   }
   
   public static Object createProxy(Object target)
   {
      InvocationHandlerSupport handler = new InvocationHandlerSupport(target);
      return createProxy(handler);
   }
   
   public static Object createProxy(InvocationHandlerSupport handler)
   {
      ClassLoader loader = handler.getClassLoader();
      Class[] interfaces = handler.getInterfaces();
      return Proxy.newProxyInstance(loader, interfaces, handler);
   }
   
   // Constructors --------------------------------------------------

   /**
    * Create a new invocation handler support object
    * 
    * @param target the target
    */
   public InvocationHandlerSupport(Object target)
   {
      this.target = target;
   }

   // Public --------------------------------------------------------
   
   // InvocationHandler implementation ------------------------------

   public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
   {
      if (method.equals(EQUALS))
         return handleEquals(args);
      else if (method.equals(HASHCODE))
         return handleHashCode();
      else if (method.equals(TOSTRING))
         return handleToString();
      else
         return handleInvoke(method, args);
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Get the target
    * 
    * @return the target
    */
   protected Object getTarget()
   {
      return target;
   }

   /**
    * Get the classloader
    * 
    * @return the classloader
    */
   protected ClassLoader getClassLoader()
   {
      return target.getClass().getClassLoader();
   }

   /**
    * Get the interfaces
    * 
    * @return the interfaces
    */
   protected Class[] getInterfaces()
   {
      return target.getClass().getInterfaces();
   }
   
   /**
    * Handle Object.equals(Object)
    * 
    * @param args the arguments
    * @return true when equals, false otherwise
    * @throws Throwable for any error
    */
   protected Object handleEquals(Object[] args) throws Throwable
   {
      Object other = mapProxy(args[0]);
      return Boolean.valueOf(getTarget().equals(other));
   }

   /**
    * Map a proxy to its target
    * 
    * @param other the other object
    * @return the target
    * @throws Throwable for any error
    */
   protected Object mapProxy(Object other) throws Throwable
   {
      if (other != null && other instanceof Proxy)
      {
         Proxy proxy = (Proxy) other;
         InvocationHandler handler = Proxy.getInvocationHandler(proxy);
         if (handler instanceof InvocationHandlerSupport)
         {
            InvocationHandlerSupport support = (InvocationHandlerSupport) handler;
            return support.getTarget();
         }
      }
      return other;
   }
   
   /**
    * Handle Object.hashCode()
    * 
    * @return the result
    * @throws Throwable for any error
    */
   protected Object handleHashCode() throws Throwable
   {
      return new Integer(getTarget().hashCode());
   }
   
   /**
    * Handle Object.toString()
    * 
    * @return the result 
    * @throws Throwable for any error
    */
   protected Object handleToString() throws Throwable
   {
      return getTarget().toString();
   }
   
   /**
    * Handle the invocation
    * 
    * @param method the method
    * @param args the arguments
    * @return the result
    * @throws Throwable for any error
    */
   protected Object handleInvoke(Method method, Object[] args) throws Throwable
   {
      try
      {
         return method.invoke(target, args);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
   }
   
   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------
}