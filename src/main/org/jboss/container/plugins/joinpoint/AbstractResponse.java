/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint;

import org.jboss.container.spi.Response;

/**
 * An abstract response.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractResponse implements Response
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The result */
   protected Object result;
   
   /** Any throwable */
   protected Throwable throwable;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new Abstract response
    */
   public AbstractResponse()
   {
   }

   // Public --------------------------------------------------------

   // Response implementation ---------------------------------------

   public Object getResult()
   {
      return result;
   }

   public void setResult(Object value)
   {
      this.result = value;
   }

   public Throwable getThrowable()
   {
      return throwable;
   }

   public void setThrowable(Throwable t)
   {
      this.throwable = t;
   }

   public void merge(Response response)
   {
      this.result = response.getResult();
      this.throwable = response.getThrowable();
   }

   // Object overrides ----------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------

}