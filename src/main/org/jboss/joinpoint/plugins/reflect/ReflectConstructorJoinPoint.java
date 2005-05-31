/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import java.lang.reflect.Constructor;

import org.jboss.joinpoint.spi.ConstructorJoinpoint;
import org.jboss.reflect.spi.ConstructorInfo;
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

   /** The constructor info */
   protected ConstructorInfo constructorInfo;

   /** The arguments */
   protected Object[] arguments;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new constructor join point
    * 
    * @param constructorInfo the constructor info
    */
   public ReflectConstructorJoinPoint(ConstructorInfo constructorInfo)
   {
      this.constructorInfo = constructorInfo;
   }
   
   // Public --------------------------------------------------------
   
   // ConstructorJoinpoint implementation ---------------------------

   public ConstructorInfo getConstructorInfo()
   {
      return constructorInfo;
   }
   
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
      Constructor constructor = constructorInfo.getConstructor();
      try
      {
         return constructor.newInstance(arguments);
      }
      catch (Throwable t)
      {
         ReflectJoinpointFactory.handleErrors("new", constructor.getParameterTypes(), arguments, t);
         throw new UnreachableStatementException();
      }
   }
   
   public String toHumanReadableString()
   {
      return constructorInfo.toString();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
