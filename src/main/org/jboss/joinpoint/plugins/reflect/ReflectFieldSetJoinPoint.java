/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.Field;

import org.jboss.joinpoint.FieldSetJoinpoint;

/**
 * A field set joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldSetJoinPoint extends ReflectTargettedJoinPoint implements FieldSetJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The field */
   protected Field field;

   /** The value */
   protected Object value;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new field set join point
    * 
    * @param field the field
    */
   public ReflectFieldSetJoinPoint(Field field)
   {
      this.field = field;
   }
   
   // Public --------------------------------------------------------
   
   // FieldSetJoinpoint implementation ------------------------------

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
      field.set(target, value);
      return null;
   }
   
   public String toHumanReadableString()
   {
      return "SET " + field.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
