/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.bean;

import org.jboss.container.plugins.joinpoint.AbstractInvocation;
import org.jboss.container.plugins.joinpoint.bean.MethodJoinPoint;
import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.JoinPoint;

/**
 * A method invocation.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MethodInvocation extends AbstractInvocation
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The method invocation context */
   protected MethodInvocationContext ctx;
   
   /** The method join point */
   protected MethodJoinPoint joinPoint;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   /**
    * Create a new method invocation object
    * 
    * @param ctx the context
    * @param joinPoint the joinpoint
    */
   public MethodInvocation(MethodInvocationContext ctx, MethodJoinPoint joinPoint)
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
   protected void setMethodInvocationContext(MethodInvocationContext ctx)
   {
      this.ctx = ctx;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
