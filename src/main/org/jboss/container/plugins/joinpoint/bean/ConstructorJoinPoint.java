/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint.bean;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

import org.jboss.beans.info.spi.ConstructorInfo;
import org.jboss.beans.info.spi.ParameterInfo;
import org.jboss.container.plugins.joinpoint.AbstractJoinPoint;
import org.jboss.container.spi.JoinPointException;

/**
 * A constructor join point
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ConstructorJoinPoint extends AbstractJoinPoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The constructor types */
   protected String[] paramTypes;
   
   /** The constructor info */
   protected ConstructorInfo constructorInfo;
   
   /** The parameters */
   protected Object[] params;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new constructor join point
    * 
    * @param constructorInfo the constructor info
    */
   public ConstructorJoinPoint(ConstructorInfo constructorInfo)
   {
      this.constructorInfo = constructorInfo;
      List paramInfo = constructorInfo.getParameters();
      this.paramTypes = new String[paramInfo.size()];
      for (int i = 0; i < paramInfo.size(); ++i)
      {
         ParameterInfo pinfo = (ParameterInfo) paramInfo.get(i);
         paramTypes[i] = pinfo.getTypeInfo().getName();
      }
   }
   
   /**
    * Create a new constructor join point
    * 
    * @param paramTypes the parameter types
    */
   public ConstructorJoinPoint(String[] paramTypes)
   {
      this.paramTypes = paramTypes;
   }
   
   // Public --------------------------------------------------------

   /**
    * Get the constructor info
    * 
    * @return the info
    */
   public ConstructorInfo getConstructorInfo()
   {
      return constructorInfo;
   }

   /**
    * Set the constructor info
    * 
    * @param info the constructor info
    */
   public void setConstructorInfo(ConstructorInfo info)
   {
      this.constructorInfo = info;
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
      Constructor constructor = constructorInfo.getConstructor();
      try
      {
         return constructor.newInstance(params);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
      catch (IllegalArgumentException e)
      {
         throw new IllegalArgumentException("Wrong types, expected " + toHumanReadableString() + " was " + Arrays.asList(params));
      }
      catch (Throwable t)
      {
         throw new JoinPointException("Error invoking constructor " + constructor, t);
      }
   }
 
   public String toHumanReadableString()
   {
      // TODO human readable
      return "constructor params=" + Arrays.asList(paramTypes).toString();
   }
   
   // Object overrides ----------------------------------------------
   
   public boolean equals(Object obj)
   {
      if (obj == null || obj instanceof ConstructorJoinPoint == false)
         return false;
      ConstructorJoinPoint other = (ConstructorJoinPoint) obj;
      return Arrays.equals(paramTypes, other.paramTypes);
   }
   
   public int hashCode()
   {
      return Arrays.asList(paramTypes).hashCode();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
