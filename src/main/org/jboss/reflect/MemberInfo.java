/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * Member info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface MemberInfo extends ModifierInfo
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------
   
   /**
    * Get the declaring class
    * 
    * @return the declaring class 
    */
   ClassInfo getDeclaringClass();
   
   // Inner classes -------------------------------------------------
}
