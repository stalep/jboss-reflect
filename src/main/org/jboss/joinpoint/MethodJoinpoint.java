/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

import org.jboss.reflect.MethodInfo;

/**
 * A method join point
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface MethodJoinpoint extends TargettedJoinpoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the method info
    * 
    * @return the method info
    */
   MethodInfo getMethodInfo();
   
   /**
    * Get the arguments of the join point
    * 
    * @return the arguments
    */
   Object[] getArguments();
   
   /**
    * Set the arguments of the method invocation
    * 
    * @param args the arguments
    */
   void setArguments(Object[] args);
   
   // Inner classes -------------------------------------------------
}
