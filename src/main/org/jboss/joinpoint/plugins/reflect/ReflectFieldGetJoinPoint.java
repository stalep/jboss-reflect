/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.Field;

import org.jboss.joinpoint.FieldGetJoinpoint;

/**
 * A field get joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldGetJoinPoint extends ReflectTargettedJoinPoint implements FieldGetJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The field */
   protected Field field;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new field get join point
    * 
    * @param field the field
    */
   public ReflectFieldGetJoinPoint(Field field)
   {
      this.field = field;
   }
   
   // Public --------------------------------------------------------
   
   // FieldGetJoinpoint implementation ------------------------------
   
   // Joinpoint implementation --------------------------------------

   public Object dispatch() throws Throwable
   {
      return field.get(target);
   }
   
   public String toHumanReadableString()
   {
      return "GET " + field.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
