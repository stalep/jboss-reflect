/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocationContext;
import org.jboss.container.plugins.joinpoint.bean.SetterJoinPoint;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.JoinPoint;

/**
 * An attribute setter invocation context.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SetterInvocationContext extends AbstractInvocationContext
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The join point */
   protected SetterJoinPoint setterJoinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new getter invocation context
    *
    * @param joinPoint the join point
    */
   public SetterInvocationContext(SetterJoinPoint joinPoint)
   {
      this.setterJoinPoint = joinPoint;
   }
   
   // Public --------------------------------------------------------
   
   // AbstractInvocationContext overrides ---------------------------

   public Invocation createInvocation(JoinPoint joinPoint) throws Throwable
   {
      SetterJoinPoint setter = (SetterJoinPoint) joinPoint;
      updateSetterJoinPoint(setter);
      return new SetterInvocation(this, setter);
   }
   
   public Invocation updateInvocation(Invocation invocation) throws Throwable
   {
      SetterInvocation setterInvocation = (SetterInvocation) invocation;
      setterInvocation.setSetterInvocationContext(this);
      SetterJoinPoint joinPoint = (SetterJoinPoint) invocation.getJoinPoint();
      updateSetterJoinPoint(joinPoint);
      return invocation;
   }

   public JoinPoint getJoinPoint()
   {
      return setterJoinPoint;
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Update a joinpoint
    * 
    * @param other the joinpoint to update 
    */
   protected void updateSetterJoinPoint(SetterJoinPoint other) throws Throwable
   {
      other.setTarget(setterJoinPoint.getTarget());
      other.setAttributeInfo(setterJoinPoint.getAttributeInfo());
   }

   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
