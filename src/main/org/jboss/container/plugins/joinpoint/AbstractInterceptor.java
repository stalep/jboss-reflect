/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint;

import org.jboss.container.spi.Interceptor;
import org.jboss.container.spi.Invocation;

/**
 * An abstract interceptor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractInterceptor implements Interceptor
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new Abstract interceptor
    */
   public AbstractInterceptor()
   {
   }
   
   // Public --------------------------------------------------------
   
   // Interceptor implementation ------------------------------------

   public Object invoke(Invocation invocation) throws Throwable
   {
      return invocation.invokeNext();
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
