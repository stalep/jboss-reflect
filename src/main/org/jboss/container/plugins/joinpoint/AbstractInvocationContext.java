/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint;

import java.util.ArrayList;

import org.jboss.container.spi.InvocationContext;
import org.jboss.container.spi.JoinPoint;
import org.jboss.container.spi.MetaData;

/**
 * An abstract invocation context.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractInvocationContext implements InvocationContext
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The interceptors */
   protected ArrayList interceptors;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new Abstract invocation context
    */
   public AbstractInvocationContext()
   {
   }
   
   // Public --------------------------------------------------------
   
   /**
    * Set the interceptors
    * 
    * @param interceptors the interceptors
    */
   public void setInterceptors(ArrayList interceptors)
   {
      this.interceptors = interceptors;
   }
   
   // InvocationContext implementation ------------------------------
   
   public abstract JoinPoint getJoinPoint();
   
   public ArrayList getInterceptors()
   {
      return interceptors;
   }
   
   public MetaData getMetaData()
   {
      // TODO getMetaData
      return null;
   }
   
   // Object overrides ----------------------------------------------
   
   public String toString()
   {
      return getJoinPoint().toString();
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
