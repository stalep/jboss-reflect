/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.Arrays;

/**
 * Array value
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ArrayValue extends Value
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the values
    * 
    * @return the values
    */
   Value[] getValues();
   
   // Inner classes -------------------------------------------------
}
