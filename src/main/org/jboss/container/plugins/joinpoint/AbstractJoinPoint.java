/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint;

import org.jboss.container.spi.JoinPoint;

/**
 * An abstract join point.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractJoinPoint implements JoinPoint, Cloneable
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The target */
   protected Object target;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new abstract joinpoint
    */
   public AbstractJoinPoint()
   {
   }
   
   // JoinPoint implementation --------------------------------------

   public Object getTarget()
   {
      return target;
   }
   
   public void setTarget(Object target)
   {
      this.target = target;
   }
   
   public void update(JoinPoint joinPoint)
   {
      if (target == null)
         target = joinPoint.getTarget();
   }
   
   // Object overrides ----------------------------------------------
   
   public String toString()
   {
      return toHumanReadableString();
   }
   
   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         throw new RuntimeException("Impossible!", e);
      }
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
