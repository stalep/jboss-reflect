/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocationContext;
import org.jboss.container.plugins.joinpoint.bean.ConstructorJoinPoint;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.JoinPoint;

/**
 * A constructor invocation context.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ConstructorInvocationContext extends AbstractInvocationContext
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The constructor join point */
   protected ConstructorJoinPoint constructorJoinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new constructor invocation context
    *
    * @param joinPoint the join point
    */
   public ConstructorInvocationContext(ConstructorJoinPoint joinPoint)
   {
      this.constructorJoinPoint = joinPoint;
   }
   
   // Public --------------------------------------------------------
   
   // AbstractInvocationContext overrides ---------------------------

   public Invocation createInvocation(JoinPoint joinPoint) throws Throwable
   {
      ConstructorJoinPoint constructor = (ConstructorJoinPoint) joinPoint;
      updateConstructorJoinPoint(constructor);
      return new ConstructorInvocation(this, constructor);
   }
   
   public Invocation updateInvocation(Invocation invocation) throws Throwable
   {
      ConstructorInvocation constructorInvocation = (ConstructorInvocation) invocation;
      constructorInvocation.setConstructorInvocationContext(this);
      ConstructorJoinPoint joinPoint = (ConstructorJoinPoint) invocation.getJoinPoint();
      updateConstructorJoinPoint(joinPoint);
      return invocation;
   }

   public JoinPoint getJoinPoint()
   {
      return constructorJoinPoint;
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Update a joinpoint
    * 
    * @param other the joinpoint to update 
    */
   protected void updateConstructorJoinPoint(ConstructorJoinPoint other) throws Throwable
   {
      other.setConstructorInfo(constructorJoinPoint.getConstructorInfo());
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
