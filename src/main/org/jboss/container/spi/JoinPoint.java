/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;

/**
 * A Join Point represents somewhere an advice can configured.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface JoinPoint extends Cloneable
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Get the target
    * 
    * @return target the target object
    */
   Object getTarget();
   
   /**
    * Set the target
    * 
    * @param target the target object
    */
   void setTarget(Object target);
   
   /**
    * Dispatch the join point
    * 
    * @return the result
    * @throws Throwable for any error
    */
   Object dispatch() throws Throwable;
   
   /**
    * A human readable version of the join point
    * 
    * @return a human readable description of the join point
    */
   String toHumanReadableString();
   
   /**
    * Clone the object
    */
   Object clone();
   
   // Inner classes -------------------------------------------------
}
