/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;

/**
 * A detyped join point with additional metadata.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface Invocation extends JoinPoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Invoke the next interceptor
    * 
    * @return the result
    * @throws Throwable for any error
    */
   Object invokeNext() throws Throwable;
   
   /**
    * Get the join point
    * 
    * @return the join point
    */
   JoinPoint getJoinPoint();
   
   /**
    * Get any response
    * 
    * @param create pass true to create a response when one does not exist
    * @return the response
    */
   Response getResponse(boolean create);

   /**
    * Get the invocation context for this invocation
    * 
    * @return the context
    */
   InvocationContext getInvocationContext();

   // Inner classes -------------------------------------------------
}
