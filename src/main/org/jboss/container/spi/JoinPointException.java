/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.spi;

/**
 * Thrown when an invocation context does not exist
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JoinPointException extends RuntimeException
{
   // Constants -----------------------------------------------------

   /** The serialVersionUID */
   private static final long serialVersionUID = 3257572793343030583L;
   
   // Static --------------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   // Constructors --------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Create a new join point exception
    * 
    * @param description the description
    */
   public JoinPointException(String description)
   {
      super(description);
   }

   /**
    * Create a new join point exception
    * 
    * @param description the description
    * @param cause the cause
    */
   public JoinPointException(String description, Throwable cause)
   {
      super(description, cause);
   }
   
   // Object overrides ----------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
