/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;

import java.util.ArrayList;

/**
 * An invocation context holds information used to process an invocation
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface InvocationContext
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Create an invocation
    * 
    * @param joinPoint the joinpoint 
    */
   Invocation createInvocation(JoinPoint joinPoint) throws Throwable;

   /**
    * Update an invocation
    * 
    * @param invocation the original invocation 
    */
   Invocation updateInvocation(Invocation invocation) throws Throwable;
   
   /**
    * Get the join point
    * 
    * @return the join point
    */
   public JoinPoint getJoinPoint();
  
   /**
    * Get the interceptors for this context
    * 
    * @return the interceptors
    */
   ArrayList getInterceptors();
   
   /**
    * Get the metadata
    * 
    * @return the metadata
    */
   MetaData getMetaData();
   
   // Inner classes -------------------------------------------------
}
