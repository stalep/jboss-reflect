/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import org.jboss.joinpoint.FieldSetJoinpoint;
import org.jboss.reflect.FieldInfo;

/**
 * A field set joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldSetJoinPoint extends ReflectTargettedJoinPoint implements FieldSetJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The field info */
   protected FieldInfo fieldInfo;

   /** The value */
   protected Object value;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new field set join point
    * 
    * @param fieldInfo the field info
    */
   public ReflectFieldSetJoinPoint(FieldInfo fieldInfo)
   {
      this.fieldInfo = fieldInfo;
   }
   
   // Public --------------------------------------------------------
   
   // FieldSetJoinpoint implementation ------------------------------

   public FieldInfo getFieldInfo()
   {
      return fieldInfo;
   }
   
   public Object getValue()
   {
      return value;
   }

   public void setValue(Object value)
   {
      this.value = value;
   }
   
   // Joinpoint implementation --------------------------------------

   public Object dispatch() throws Throwable
   {
      fieldInfo.getField().set(target, value);
      return null;
   }
   
   public String toHumanReadableString()
   {
      return "SET " + fieldInfo.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
