/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.config;

import java.util.Arrays;

import org.jboss.joinpoint.ConstructorJoinpoint;
import org.jboss.joinpoint.FieldGetJoinpoint;
import org.jboss.joinpoint.FieldSetJoinpoint;
import org.jboss.joinpoint.JoinpointFactory;
import org.jboss.joinpoint.MethodJoinpoint;
import org.jboss.logging.Logger;

/**
 * Config utilities.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class Config
{
   // Constants -----------------------------------------------------

   /** The log */
   protected static final Logger log = Logger.getLogger(Config.class);
   
   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------
   
   /**
    * Instantiate an object
    * 
    * @param jpf the join point factory
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the instantiated object
    * @throws Throwable for any error
    */
   public static Object instantiate(JoinpointFactory jpf, Class[] paramTypes, Object[] params) throws Throwable
   {
      ConstructorJoinpoint joinpoint = getConstructorJoinpoint(jpf, paramTypes, params);
      return joinpoint.dispatch();
   }

   /**
    * Configure a field
    * 
    * @param object the object to configure
    * @param jpf the join point factory
    * @param name the name of the field
    * @param value the value
    * @throws Throwable for any error
    */
   public static void configure(Object object, JoinpointFactory jpf, String name, Object value) throws Throwable
   {
      FieldSetJoinpoint joinpoint = getFieldSetJoinpoint(object, jpf, name, value);
      joinpoint.dispatch();
   }

   /**
    * Invoke a method
    * 
    * @param object the object to invoke
    * @param jpf the join point factory
    * @param name the name of the method
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the result of the invocation
    * @throws Throwable for any error
    */
   public static Object invoke(Object object, JoinpointFactory jpf, String name, Class[] paramTypes, Object[] params) throws Throwable
   {
      MethodJoinpoint joinpoint = getMethodJoinpoint(object, jpf, name, paramTypes, params);
      return joinpoint.dispatch();
   }

   /**
    * Unconfigure a field
    * 
    * @param object the object to unconfigure
    * @param jpf the join point factory
    * @param name the name of the field
    * @throws Throwable for any error
    */
   public static void unconfigure(Object object, JoinpointFactory jpf, String name) throws Throwable
   {
      FieldSetJoinpoint joinpoint = getFieldSetJoinpoint(object, jpf, name, null);
      joinpoint.dispatch();
   }
   
   /**
    * Get a constructor Joinpoint
    * 
    * @param jpf the join point factory
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the Joinpoint
    * @throws Throwable for any error
    */
   public static ConstructorJoinpoint getConstructorJoinpoint(JoinpointFactory jpf, Class[] paramTypes, Object[] params) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      if (trace)
         log.trace("Get constructor Joinpoint jpf=" + jpf + " paramTypes=" + Arrays.asList(paramTypes) + " params=" + Arrays.asList(params));

      ConstructorJoinpoint joinpoint = jpf.getConstructorJoinpoint(paramTypes);
      joinpoint.setArguments(params);
      return joinpoint;
   }

   /**
    * Get a field get joinpoint
    * 
    * @param object the object to configure
    * @param jpf the join point factory
    * @param name the name of the field
    * @return the Joinpoint
    * @throws Throwable for any error
    */
   public static FieldGetJoinpoint getFieldGetJoinpoint(Object object, JoinpointFactory jpf, String name) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      if (trace)
         log.trace("Get field get Joinpoint jpf=" + jpf + " target=" + object + " name=" + name);
      
      FieldGetJoinpoint joinpoint = jpf.getFieldGetJoinpoint(name);
      joinpoint.setTarget(object);
      return joinpoint;
   }

   /**
    * Get a field set joinpoint
    * 
    * @param object the object to configure
    * @param jpf the join point factory
    * @param name the name of the field
    * @param value the value
    * @return the Joinpoint
    * @throws Throwable for any error
    */
   public static FieldSetJoinpoint getFieldSetJoinpoint(Object object, JoinpointFactory jpf, String name, Object value) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      if (trace)
         log.trace("Get field set Joinpoint jpf=" + jpf + " target=" + object + " name=" + name + " value=" + value);
      
      FieldSetJoinpoint joinpoint = jpf.getFieldSetJoinpoint(name);
      joinpoint.setTarget(object);
      joinpoint.setValue(value);
      return joinpoint;
   }

   /**
    * Get a method joinpoint
    * 
    * @param object the object to invoke
    * @param jpf the join point factory
    * @param name the name of the method
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the join point
    * @throws Throwable for any error
    */
   public static MethodJoinpoint getMethodJoinpoint(Object object, JoinpointFactory jpf, String name, Class[] paramTypes, Object[] params) throws Throwable
   {
      MethodJoinpoint joinpoint = jpf.getMethodJoinpoint(name, paramTypes);
      joinpoint.setTarget(object);
      joinpoint.setArguments(params);
      return joinpoint;
   }
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
