/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.spi;

import org.jboss.reflect.spi.FieldInfo;

/**
 * A field get join point
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface FieldGetJoinpoint extends TargettedJoinpoint
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the field info for this join point
    * 
    * @return the field info
    */
   FieldInfo getFieldInfo();
   
   // Inner classes -------------------------------------------------
}
