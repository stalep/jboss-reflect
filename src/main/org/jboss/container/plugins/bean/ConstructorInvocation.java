/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocation;
import org.jboss.container.plugins.joinpoint.bean.ConstructorJoinPoint;
import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.JoinPoint;

/**
 * A constructor invocation.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ConstructorInvocation extends AbstractInvocation
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The constructor invocation context */
   protected ConstructorInvocationContext ctx;
   
   /** The constructor join point */
   protected ConstructorJoinPoint joinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new constructor invocation object
    * 
    * @param ctx the context
    * @param joinPoint the joinpoint
    */
   public ConstructorInvocation(ConstructorInvocationContext ctx, ConstructorJoinPoint joinPoint)
   {
      this.ctx = ctx;
      this.joinPoint = joinPoint;
   }
   
   // AbstractInvocation overrides ----------------------------------

   public InvocationContext getInvocationContext()
   {
      return ctx;
   }

   public JoinPoint getJoinPoint()
   {
      return joinPoint;
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   /**
    * Set the invocation context
    * 
    * @param ctx the context
    */   
   protected void setConstructorInvocationContext(ConstructorInvocationContext ctx)
   {
      this.ctx = ctx;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
