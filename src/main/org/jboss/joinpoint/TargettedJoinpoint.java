/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

/**
 * A join point with a target
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface TargettedJoinpoint extends Joinpoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the target of the join point 
    * 
    * @return the target of the join point
    */
   Object getTarget();
   
   /**
    * Set the target of the join point
    * 
    * @param target the target
    */
   void setTarget(Object target);
   
   // Inner classes -------------------------------------------------
}
