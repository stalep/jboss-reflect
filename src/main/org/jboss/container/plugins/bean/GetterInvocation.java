/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocation;
import org.jboss.container.plugins.joinpoint.bean.GetterJoinPoint;
import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.JoinPoint;

/**
 * A getter invocation.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class GetterInvocation extends AbstractInvocation
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The invocation context */
   protected GetterInvocationContext ctx;
   
   /** The join point */
   protected GetterJoinPoint joinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new invocation object
    * 
    * @param ctx the context
    * @param joinPoint the joinpoint
    */
   public GetterInvocation(GetterInvocationContext ctx, GetterJoinPoint joinPoint)
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
   protected void setGetterInvocationContext(GetterInvocationContext ctx)
   {
      this.ctx = ctx;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
