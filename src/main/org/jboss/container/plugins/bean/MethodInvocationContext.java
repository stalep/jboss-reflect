/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocationContext;
import org.jboss.container.plugins.joinpoint.bean.MethodJoinPoint;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.JoinPoint;

/**
 * A method invocation context.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MethodInvocationContext extends AbstractInvocationContext
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The method join point */
   protected MethodJoinPoint methodJoinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new method invocation context
    *
    * @param joinPoint the join point
    */
   public MethodInvocationContext(MethodJoinPoint joinPoint)
   {
      this.methodJoinPoint = joinPoint;
   }
   
   // Public --------------------------------------------------------
   
   // AbstractInvocationContext overrides ---------------------------

   public Invocation createInvocation(JoinPoint joinPoint) throws Throwable
   {
      MethodJoinPoint method = (MethodJoinPoint) joinPoint;
      updateMethodJoinPoint(method);
      return new MethodInvocation(this, method);
   }
   
   public Invocation updateInvocation(Invocation invocation) throws Throwable
   {
      MethodInvocation methodInvocation = (MethodInvocation) invocation;
      methodInvocation.setMethodInvocationContext(this);
      MethodJoinPoint joinPoint = (MethodJoinPoint) invocation.getJoinPoint();
      updateMethodJoinPoint(joinPoint);
      return invocation;
   }

   public JoinPoint getJoinPoint()
   {
      return methodJoinPoint;
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   /**
    * Update a joinpoint
    * 
    * @param other the joinpoint to update 
    */
   protected void updateMethodJoinPoint(MethodJoinPoint other) throws Throwable
   {
      other.setTarget(methodJoinPoint.getTarget());
      other.setMethodInfo(methodJoinPoint.getMethodInfo());
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
