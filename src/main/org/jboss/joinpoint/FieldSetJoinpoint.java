/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

/**
 * A field set join point
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface FieldSetJoinpoint extends TargettedJoinpoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * The value to set
    * 
    * @param value the value to set
    */
   void setValue(Object value);
   
   // Inner classes -------------------------------------------------
}
