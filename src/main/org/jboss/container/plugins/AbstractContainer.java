/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins;

import java.util.Map;

import org.jboss.container.spi.Container;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.InvocationContextNotFoundException;
import org.jboss.container.spi.JoinPoint;
import org.jboss.container.spi.Response;
import org.jboss.logging.Logger;

/**
 * An abstract container.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractContainer implements Container
{
   // Constants -----------------------------------------------------
   
   /** The log */
   protected Logger log = Logger.getLogger(getClass());
   
   // Attributes ----------------------------------------------------
   
   /** The contexts */
   protected Map contexts;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new Abstract container
    */
   public AbstractContainer()
   {
   }
   
   /**
    * Create a new Abstract container
    * 
    * @param contexts Map<JoinPoint, InvocationContext>
    */
   public AbstractContainer(Map contexts)
   {
      this.contexts = contexts;
   }
   
   // Public --------------------------------------------------------
   
   // Container implementation --------------------------------------

   public Object invoke(JoinPoint joinPoint) throws Throwable
   {
      if (joinPoint == null)
         throw new IllegalArgumentException("Null joinpoint");
      
      Invocation original = null;
      if (joinPoint instanceof Invocation)
         original = (Invocation) joinPoint;
      
      boolean trace = log.isTraceEnabled();
      
      Invocation invocation;
      if (original != null)
         invocation = updateInvocation(trace, original);
      else
         invocation = createInvocation(trace, joinPoint);

      if (trace)
         log.trace("Dispatching " + invocation);
      try
      {
         Object result = invocation.dispatch();
         if (trace)
            log.trace("Dispatched " + invocation + " result=" + result);
         return result;
      }
      catch (Throwable t)
      {
         if (trace)
            log.trace("Error in invocation " + invocation, t);
         if (original != null)
         {
            Response response = invocation.getResponse(true);
            response.setThrowable(t);
         }
         throw t;
      }
      finally
      {
         if (original != null)
            restoreInvocation(original, invocation);
      }
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   /**
    * Create an invocation from a join point
    * 
    * @param trace whether trace is enabled
    * @param joinPoint the join point
    * @return the invocation
    * @throws Throwable for any error
    */
   protected Invocation createInvocation(boolean trace, JoinPoint joinPoint) throws Throwable
   {
      InvocationContext ctx = getInvocationContext(trace, joinPoint);
      if (ctx == null)
         throw new InvocationContextNotFoundException("Unknown context " + joinPoint.toString() + " validContexts=" + contexts);
      return ctx.createInvocation(joinPoint);
   }
   
   /**
    * Update an invocation for this container
    * 
    * @param trace whether trace is enabled
    * @param original the original invocation
    * @return the invocation
    * @throws Throwable for any error
    */
   protected Invocation updateInvocation(boolean trace, Invocation original) throws Throwable
   {
      InvocationContext ctx = getInvocationContext(trace, original);
      if (ctx == null)
         throw new InvocationContextNotFoundException("Unknown context " + original.toString() + " validContexts=" + contexts);
      Invocation copy = (Invocation) original.clone();
      return ctx.updateInvocation(copy);
   }
   
   /**
    * Restore an invocation
    *  
    * @param original the original invocation
    * @param invocation the invocation
    * @throws Throwable for any error
    */
   protected void restoreInvocation(Invocation original, Invocation invocation) throws Throwable
   {
      Response response = invocation.getResponse(false);
      if (response != null)
      {
         Response other = invocation.getResponse(true);
         other.merge(response);
      }
   }
   
   /**
    * Get the invocation context for this joinpoint
    * 
    * @param trace whether trace is enabled
    * @param joinPoint the join point
    * @return the invocation context
    * @throws Throwable for any error
    */
   protected InvocationContext getInvocationContext(boolean trace, JoinPoint joinPoint) throws Throwable
   {
      InvocationContext result = (InvocationContext) contexts.get(joinPoint);
      if (trace)
      {
         if (result == null)
            log.trace("No invocation context for " + joinPoint + " in " + contexts);
         else
            log.trace("Using " + result + " for " + joinPoint);
      }
      return result;
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
