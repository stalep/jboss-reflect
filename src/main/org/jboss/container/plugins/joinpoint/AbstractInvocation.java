/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint;

import java.util.ArrayList;

import org.jboss.container.spi.Interceptor;
import org.jboss.container.spi.Invocation;
import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.MetaData;
import org.jboss.container.spi.Response;

/**
 * An abstract join point.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractInvocation implements Invocation
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The response */
   protected Response response;
   
   /** The meta data */
   protected MetaData metaData;
   
   /** The interceptors */
   protected ArrayList interceptors;
   
   /** The next interceptor */
   protected int current;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new Abstract invocation
    */
   public AbstractInvocation()
   {
   }
   
   // Public --------------------------------------------------------
   
   // JoinPoint implementation --------------------------------------

   public Object dispatch() throws Throwable
   {
      initInvocation();

      return invokeNext();
   }
   
   public Object getTarget()
   {
      return getJoinPoint().getTarget();
   }
   
   public void setTarget(Object target)
   {
      getJoinPoint().setTarget(target);
   }
   
   public String toHumanReadableString()
   {
      return getJoinPoint().toHumanReadableString();
   }
   
   // Invocation implementation -------------------------------------
   
   public Object invokeNext() throws Throwable
   {
      if (interceptors == null || current >= interceptors.size())
         return getJoinPoint().dispatch();

      Interceptor interceptor = (Interceptor) interceptors.get(current++);
      try
      {
         return interceptor.invoke(this);
      }
      finally
      {
         --current;
      }
   }
   
   public Response getResponse(boolean create)
   {
      if (response == null && create)
         response = createResponse();
      return response;
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

   /**
    * Create a new response object
    * 
    * @return the response object
    */
   protected Response createResponse()
   {
      return new AbstractResponse();
   }
   
   /**
    * Initialise the invocation
    * 
    * @throws Throwable for any error
    */
   protected void initInvocation() throws Throwable
   {
      initResponse();
      initInterceptors();
      initMetaData();
   }
   
   /**
    * Initialise the response
    * 
    * @throws Throwable for any error
    */
   protected void initResponse() throws Throwable
   {
      if (response != null)
      {
         response.setResult(null);
         response.setThrowable(null);
      }
   }

   /**
    * Initialise the interceptors
    * 
    * @throws Throwable for any error
    */
   protected void initInterceptors() throws Throwable
   {
      InvocationContext ctx = getInvocationContext();
      interceptors = ctx.getInterceptors();
      current = 0;
   }
   
   /**
    * Initialise the metadata
    * 
    * @throws Throwable for any error
    */
   protected void initMetaData() throws Throwable
   {
      // TODO include any previous metaData
      InvocationContext ctx = getInvocationContext();
      metaData = ctx.getMetaData();
   }
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
