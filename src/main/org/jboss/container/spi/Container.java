/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;

/**
 * A Container. Responsible for advice and metadata injection.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface Container
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Make an invocation on a join point
    * 
    * @param joinPoint the join point
    * @return the result of the invocation
    * @throws Throwable for any error
    */
   Object invoke(JoinPoint joinPoint) throws Throwable;
   
   // Inner classes -------------------------------------------------
}
