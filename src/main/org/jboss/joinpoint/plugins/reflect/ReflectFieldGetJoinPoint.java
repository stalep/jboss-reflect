/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import org.jboss.joinpoint.spi.FieldGetJoinpoint;
import org.jboss.reflect.spi.FieldInfo;

/**
 * A field get joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldGetJoinPoint extends ReflectTargettedJoinPoint implements FieldGetJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The field info */
   protected FieldInfo fieldInfo;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new field get join point
    * 
    * @param fieldInfo the field info
    */
   public ReflectFieldGetJoinPoint(FieldInfo fieldInfo)
   {
      this.fieldInfo = fieldInfo;
   }
   
   // Public --------------------------------------------------------
   
   // FieldGetJoinpoint implementation ------------------------------

   public FieldInfo getFieldInfo()
   {
      return fieldInfo;
   }
   
   // Joinpoint implementation --------------------------------------
   
   public Object dispatch() throws Throwable
   {
      return fieldInfo.getField().get(target);
   }
   
   public String toHumanReadableString()
   {
      return "GET " + fieldInfo.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
