/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import org.jboss.joinpoint.spi.TargettedJoinpoint;
import org.jboss.util.UnreachableStatementException;

/**
 * A targetted joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public abstract class ReflectTargettedJoinPoint implements TargettedJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The target */
   protected Object target;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   // TargettedJoinpoint implementation -----------------------------

   public Object getTarget()
   {
      return target;
   }

   public void setTarget(Object target)
   {
      this.target = target;
   }
   
   // Joinpoint implementation --------------------------------------

   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         throw new UnreachableStatementException();
      }
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
