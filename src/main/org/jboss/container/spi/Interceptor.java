/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;


/**
 * An interceptor.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface Interceptor
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Handle the invocation.
    * 
    * @param invocation the invocation
    * @return the result of the invocation
    * @throws Throwable for any error
    */
   Object invoke(Invocation invocation) throws Throwable;
   
   // Inner classes -------------------------------------------------
}
