/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.jboss.joinpoint.ConstructorJoinpoint;
import org.jboss.util.UnreachableStatementException;

/**
 * A constructor joinpoint
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectConstructorJoinPoint implements ConstructorJoinpoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The constructor */
   protected Constructor constructor;

   /** The arguments */
   protected Object[] arguments;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new constructor join point
    * 
    * @param constructor the constructor
    */
   public ReflectConstructorJoinPoint(Constructor constructor)
   {
      this.constructor = constructor;
   }
   
   // Public --------------------------------------------------------
   
   // ConstructorJoinpoint implementation ---------------------------

   public Object[] getArguments()
   {
      return arguments;
   }

   public void setArguments(Object[] args)
   {
      this.arguments = args;
   }
   
   // Joinpoint implementation --------------------------------------

   public Object clone()
   {
      try
      {
         return super.clone();
      }
      catch (CloneNotSupportedException e)
      {
         throw new UnreachableStatementException();
      }
   }

   public Object dispatch() throws Throwable
   {
      try
      {
         return constructor.newInstance(arguments);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
   }
   
   public String toHumanReadableString()
   {
      return constructor.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
