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
package org.jboss.annotation.factory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * InvocationHandler implementation for creating an annotation proxy.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:kabir.khan@jboss.org">Kabir Khan</a>
 * @version $Revision$
 */
public class AnnotationProxy implements InvocationHandler, Serializable
{
   private static final long serialVersionUID = 1;
   private Map map;
   private Class annotationType;

   public AnnotationProxy(Class annotationType, Map valueMap)
   {
      this.annotationType = annotationType;
      map = valueMap;
   }

   public Object invoke(Object proxy, Method method, Object[] args)
           throws Throwable
   {
      String name = method.getName();
      if ("equals".equals(name))
      {
         return doEquals(proxy, args[0]);
      }
      else if ("hashCode".equals(name))
      {
         return doHashCode();
      }
      else if ("toString".equals(name))
      {
         return map.toString();
      }
      else if ("annotationType".equals(name))
      {
         return annotationType;
      }

      /*
      Object obj = map.get(method.getName());
      if (!method.getReturnType().equals(obj.getClass()))
      {
         System.err.println("***** " + method.toString() + " has bad return type: " + obj.getClass().getName());
      }
      return obj;
      */
      return map.get(name);
   }

   public Object getValue(String name)
   {
      return map.get(name);
   }

   @SuppressWarnings("unchecked")
   private Object doEquals(Object proxy, Object obj)
   {
      if (obj == proxy)
         return Boolean.TRUE;
      if (obj == null)
         return Boolean.FALSE;

      Class[] intfs = proxy.getClass().getInterfaces();
      if (intfs[0].isAssignableFrom(obj.getClass()) == false)
      {
         return Boolean.FALSE;
      }
      try
      {
         Proxy.getInvocationHandler(obj);
      }
      catch (Exception ex)
      {
         return Boolean.FALSE;
      }
      return Boolean.TRUE;
   }

   private Object doHashCode()
   {
      return new Integer(map.hashCode());
   }

   /**
    * Create a proxy implementation for the annotation class.
    * @param map - map of the annotation values
    * @param annotation - the annotation class
    * @return an instance implementing the annotation
    * @throws Exception for any error
    */
   public static Object createProxy(Map map, Class annotation) throws Exception
   {
      AnnotationProxy proxyHandler = new AnnotationProxy(annotation, map);
      return java.lang.reflect.Proxy.newProxyInstance(annotation.getClassLoader(), new Class[]{annotation}, proxyHandler);
   }
}
