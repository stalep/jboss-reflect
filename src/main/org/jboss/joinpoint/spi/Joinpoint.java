/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.spi;

/**
 * A join point
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface Joinpoint extends Cloneable
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Invoke on the actual joinpoint
    *
    * @return the result of the invocation
    * @throws Throwable for any error
    */
   Object dispatch() throws Throwable;

   /**
    * Make a copy of the joinpoint
    * 
    * @return a copy of the join point
    */
   Object clone();

   /**
    * A human readable version of the join point
    *
    * @return a human readable description of the join point
    */
   String toHumanReadableString();
   
   // Inner classes -------------------------------------------------
}
