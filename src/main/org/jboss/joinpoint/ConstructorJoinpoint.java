/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

import org.jboss.reflect.ConstructorInfo;

/**
 * A constructor join point.
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ConstructorJoinpoint extends Joinpoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the constructor info for this join point
    * 
    * @return the constructor info
    */
   ConstructorInfo getConstructorInfo();
   
   /**
    * Get the arguments for the constructor
    * 
    * @return the arguments
    */
   Object[] getArguments();
   
   /**
    * Set the arguments for the constructor
    * 
    * @param args the arguments
    */
   void setArguments(Object[] args);
   
   // Inner classes -------------------------------------------------
}
