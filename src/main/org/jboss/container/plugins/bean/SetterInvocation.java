/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocation;
import org.jboss.container.plugins.joinpoint.bean.SetterJoinPoint;
import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.JoinPoint;

/**
 * A setter invocation.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SetterInvocation extends AbstractInvocation
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The invocation context */
   protected SetterInvocationContext ctx;
   
   /** The join point */
   protected SetterJoinPoint joinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new invocation object
    * 
    * @param ctx the context
    * @param joinPoint the joinpoint
    */
   public SetterInvocation(SetterInvocationContext ctx, SetterJoinPoint joinPoint)
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
   protected void setSetterInvocationContext(SetterInvocationContext ctx)
   {
      this.ctx = ctx;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
