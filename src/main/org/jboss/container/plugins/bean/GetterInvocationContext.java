/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocationContext;
import org.jboss.container.plugins.joinpoint.bean.GetterJoinPoint;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.JoinPoint;

/**
 * An attribute getter invocation context.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class GetterInvocationContext extends AbstractInvocationContext
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The join point */
   protected GetterJoinPoint getterJoinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new getter invocation context
    *
    * @param joinPoint the join point
    */
   public GetterInvocationContext(GetterJoinPoint joinPoint)
   {
      this.getterJoinPoint = joinPoint;
   }
   
   // Public --------------------------------------------------------
   
   // AbstractInvocationContext overrides ---------------------------

   public Invocation createInvocation(JoinPoint joinPoint) throws Throwable
   {
      GetterJoinPoint getter = (GetterJoinPoint) joinPoint;
      updateGetterJoinPoint(getter);
      return new GetterInvocation(this, getter);
   }
   
   public Invocation updateInvocation(Invocation invocation) throws Throwable
   {
      GetterInvocation getterInvocation = (GetterInvocation) invocation;
      getterInvocation.setGetterInvocationContext(this);
      GetterJoinPoint joinPoint = (GetterJoinPoint) invocation.getJoinPoint();
      updateGetterJoinPoint(joinPoint);
      return invocation;
   }

   public JoinPoint getJoinPoint()
   {
      return getterJoinPoint;
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Update a joinpoint
    * 
    * @param other the joinpoint to update 
    */
   protected void updateGetterJoinPoint(GetterJoinPoint other) throws Throwable
   {
      other.setTarget(getterJoinPoint.getTarget());
      other.setAttributeInfo(getterJoinPoint.getAttributeInfo());
   }

   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
