/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

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
