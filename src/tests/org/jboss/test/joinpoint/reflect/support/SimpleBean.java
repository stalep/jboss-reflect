/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.joinpoint.reflect.support;

/**
 * A simple bean
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SimpleBean implements SimpleInterface
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------
   
   public Object publicField = "DefaultValue";
   
   private String constructorUsed;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   public SimpleBean()
   {
      constructorUsed = "()";
   }

   public SimpleBean(String string)
   {
      constructorUsed = string;
   }

   public SimpleBean(String string, Object object)
   {
      constructorUsed = string;
   }
   
   // Public --------------------------------------------------------
   
   public String getConstructorUsed()
   {
      return constructorUsed;
   }
   
   public String echo(String value)
   {
      return value;
   }
   
   // SimpleInterface Implementation --------------------------------
   
   // Package protected ---------------------------------------------
   
   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}