/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.spi;

import org.jboss.reflect.spi.FieldInfo;

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
    * Get the field info for this join point
    * 
    * @return the field info
    */
   FieldInfo getFieldInfo();

   /**
    * The value to set
    * 
    * @param value the value to set
    */
   void setValue(Object value);
   
   // Inner classes -------------------------------------------------
}
