/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;

/**
 * A response.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface Response
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Merge this response with another response
    * 
    * @param response the other response
    */
   void merge(Response response);
   
   /**
    * Get the return value
    * 
    * @return the return value
    */
   Object getResult();

   /**
    * Set the return value
    * 
    * @param value the return value
    */
   void setResult(Object value);

   /**
    * Get any exception
    * 
    * @return the throwable
    */
   Throwable getThrowable();

   /**
    * Set the throwable
    * 
    * @param t the throwable
    */
   void setThrowable(Throwable t);
   
   // Inner classes -------------------------------------------------
}
