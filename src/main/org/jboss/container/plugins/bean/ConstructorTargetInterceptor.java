/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import java.util.Iterator;
import java.util.Map;

import org.jboss.container.plugins.joinpoint.AbstractInterceptor;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.InvocationContext;

/**
 * An interceptor that sets the result of a constructor as the
 * target on the contexts.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ConstructorTargetInterceptor extends AbstractInterceptor
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The invocation contexts */
   protected Map contexts;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new only once constructor interceptor
    * 
    * @param contexts the invocation contexts
    */
   public ConstructorTargetInterceptor(Map contexts)
   {
      this.contexts = contexts;
   }
   
   // AbstractInterceptor overrides ---------------------------------

   public Object invoke(Invocation invocation) throws Throwable
   {
      Object result = invocation.invokeNext();
      for (Iterator i = contexts.values().iterator(); i.hasNext();)
      {
         InvocationContext ctx = (InvocationContext) i.next();
         ctx.getJoinPoint().setTarget(result);
      }
      return result;
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
