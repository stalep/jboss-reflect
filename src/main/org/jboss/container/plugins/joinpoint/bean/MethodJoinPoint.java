/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.beans.info.spi.ParameterInfo;
import org.jboss.container.plugins.joinpoint.AbstractJoinPoint;
import org.jboss.container.spi.JoinPointException;
import org.jboss.util.Classes;

/**
 * A method join point
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class MethodJoinPoint extends AbstractJoinPoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
   
   /** The method name */
   protected String methodName;
   
   /** The method parameters */
   protected String[] paramTypes;
   
   /** The method info */
   protected MethodInfo methodInfo;
   
   /** The parameters */
   protected Object[] params;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new method join point
    * 
    * @param methodInfo the method info
    */
   public MethodJoinPoint(MethodInfo methodInfo)
   {
      this.methodInfo = methodInfo;
      this.methodName = methodInfo.getName();
      List paramInfo = methodInfo.getParameters();
      this.paramTypes = new String[paramInfo.size()];
      for (int i = 0; i < paramInfo.size(); ++i)
      {
         ParameterInfo pinfo = (ParameterInfo) paramInfo.get(i);
         paramTypes[i] = pinfo.getTypeInfo().getName();
      }
   }
   
   /**
    * Create a new method join point
    *
    * @param name the method name
    */
   public MethodJoinPoint(String name)
   {
      this(name, null);
   }
   
   /**
    * Create a new method join point
    *
    * @param name the method name
    * @param paramTypes the parameter types 
    */
   public MethodJoinPoint(String name, String[] paramTypes)
   {
      this.methodName = name;
      if (paramTypes == null)
         paramTypes = new String[0];
      this.paramTypes = paramTypes;
   }
   
   /**
    * Create a new method join point
    *
    * @param method the method
    */
   public MethodJoinPoint(Method method)
   {
      this.methodName = method.getName();
      Class[] paramClasses = method.getParameterTypes();
      this.paramTypes = new String[paramClasses.length];
      for (int i = 0; i < paramClasses.length; ++i)
         paramTypes[i] = paramClasses[i].getName();
   }
   
   // Public --------------------------------------------------------

   /**
    * Get the method info
    * 
    * @return the method info
    */
   public MethodInfo getMethodInfo()
   {
      return methodInfo;
   }

   /**
    * Set the method info
    * 
    * @param info the method info
    */
   public void setMethodInfo(MethodInfo info)
   {
      this.methodInfo = info;
   }

   /**
    * Get the parameters
    * 
    * @return the parameters
    */
   public Object[] getParameters()
   {
      return params;
   }
   
   /**
    * Set the parameters 
    * 
    * @param params the parameters
    */
   public void setParameters(Object[] params)
   {
      this.params = params;
   }
   
   // AbstractJoinPoint overrides -----------------------------------
   
   public Object dispatch() throws Throwable
   {
      Method method = methodInfo.getMethod();
      try
      {
         return method.invoke(target, params);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
      catch (Throwable t)
      {
         throw new JoinPointException("Error invoking method " + method + " on " + Classes.getDescription(target), t);
      }
   }
   
   public String toHumanReadableString()
   {
      // TODO human readable
      return methodName + Arrays.asList(paramTypes);
   }
   
   // Object overrides ----------------------------------------------
   
   public boolean equals(Object obj)
   {
      if (obj == null || obj instanceof MethodJoinPoint == false)
         return false;
      MethodJoinPoint other = (MethodJoinPoint) obj;
      if (methodName.equals(other.methodName) == false)
         return false;
      return Arrays.equals(paramTypes, other.paramTypes);
   }
   
   public int hashCode()
   {
      return methodName.hashCode();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
