/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.config;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.jboss.beans.info.spi.AttributeInfo;
import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.ConstructorInfo;
import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.beans.info.spi.ParameterInfo;
import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.container.plugins.joinpoint.bean.ConstructorJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.MethodJoinPoint;
import org.jboss.container.plugins.joinpoint.bean.SetterJoinPoint;
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
    * Instantiate a bean
    * 
    * @param info the bean info
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the instantiated object
    * @throws Throwable for any error
    */
   public static Object instantiate(BeanInfo info, String[] paramTypes, Object[] params) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      if (trace)
         log.trace("Instantiating info=" + info + " paramTypes=" + Arrays.asList(paramTypes) + " params=" + Arrays.asList(params));

      ConstructorJoinPoint joinPoint = getConstructorJoinPoint(info, paramTypes, params);
      return joinPoint.dispatch();
   }
   
   /**
    * Get a constructor joinpoint
    * 
    * @param info the bean info
    * @param paramTypes the parameter types
    * @param params the parameters
    * @return the joinpoint
    * @throws Throwable for any error
    */
   public static ConstructorJoinPoint getConstructorJoinPoint(BeanInfo info, String[] paramTypes, Object[] params) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      
      if (trace)
         log.trace("Get constructor joinpoint info=" + info + " paramTypes=" + Arrays.asList(paramTypes) + " params=" + Arrays.asList(params));

      ConstructorJoinPoint joinPoint = findConstructor(trace, info, paramTypes);
      joinPoint.setParameters(params);
      return joinPoint;
   }
   
   /**
    * Find a constructor
    * 
    * @param trace whether trace is enabled
    * @param info the bean info
    * @param paramTypes the parameter types
    * @return the constructor join point
    * @throws Exception for any error
    */
   public static ConstructorJoinPoint findConstructor(boolean trace, BeanInfo info, String[] paramTypes) throws Exception
   {
      ConstructorInfo cinfo = resolveConstructor(trace, info, paramTypes);
      return new ConstructorJoinPoint(cinfo);
   }
   
   /**
    * Resolved a constructor
    * 
    * @param trace whether trace is enabled
    * @param info the bean info
    * @param paramTypes the parameter types
    * @return the constructor info
    */
   public static ConstructorInfo resolveConstructor(boolean trace, BeanInfo info, String[] paramTypes)
   {
      if (paramTypes == null)
         paramTypes = new String[0];

      Set constructors = info.getConstructors();
      if (constructors.isEmpty() == false)
      {
         for (Iterator i = info.getConstructors().iterator(); i.hasNext();)
         {
            ConstructorInfo cinfo = (ConstructorInfo) i.next();
            if (cinfo.getParameters().size() != paramTypes.length)
               continue;
            int x = 0;
            boolean found = true;
            for (Iterator j = cinfo.getParameters().iterator(); j.hasNext();)
            {
               ParameterInfo pinfo = (ParameterInfo) j.next();
               if (paramTypes[x++].equals(pinfo.getTypeInfo().getName()) == false)
               {
                  found = false;
                  break;
               }
            }
            if (found)
            {
               if (trace)
                  log.trace("Found constructor " + cinfo);
               return cinfo;
            }
         }
      }
      throw new IllegalArgumentException("Constructor " + Arrays.asList(paramTypes) + " not found for " + info);
   }

   /**
    * Configure a bean attribute
    * 
    * @param object the object to configure
    * @param info the bean info
    * @param name the name of the attribute
    * @param value the value
    * @throws Throwable for any error
    */
   public static void configure(Object object, BeanInfo info, String name, Object value) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      AttributeInfo ainfo = resolveAttribute(trace, info, name);
      configure(trace, object, ainfo, value);
   }

   /**
    * Configure a bean attribute
    * 
    * @param object the object to configure
    * @param info the attribute info
    * @param value the value
    * @throws Throwable for any error
    */
   public static void configure(Object object, AttributeInfo info, Object value) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      configure(trace, object, info, value);
   }

   /**
    * Configure a bean attribute
    *
    * @param trace whether trace is enabled
    * @param object the object to configure
    * @param info the attribute info
    * @param value the value
    * @throws Throwable for any error
    */
   public static void configure(boolean trace, Object object, AttributeInfo info, Object value) throws Throwable
   {
      if (trace)
         log.trace("Configuring info=" + info + " value=" + value);
      
      SetterJoinPoint joinPoint = getAttributeSetterJoinPoint(trace, info, value);
      joinPoint.setTarget(object);
      
      if (trace)
         log.trace("Setting attribute " + joinPoint + " target=" + object + " value=" + value);
      
      joinPoint.dispatch();
   }

   /**
    * Get attribute setter for an attribute
    * 
    * @param info the bean info
    * @param name the name of the attribute
    * @param value the value
    * @return the joinpoint
    * @throws Throwable for any error
    */
   public static SetterJoinPoint getAttributeSetterJoinPoint(BeanInfo info, String name, Object value) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      AttributeInfo ainfo = resolveAttribute(trace, info, name);
      return getAttributeSetterJoinPoint(trace, ainfo, value);
   }

   /**
    * Get attribute setter for an attribute
    * 
    * @param info the attribute info
    * @param value the value
    * @return the joinpoint
    * @throws Throwable for any error
    */
   public static SetterJoinPoint getAttributeSetterJoinPoint(AttributeInfo info, Object value) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      return getAttributeSetterJoinPoint(trace, info, value);
   }

   /**
    * Get an attribute setter joinpoint
    *
    * @param trace whether trace is enabled
    * @param info the attribute info
    * @param value the value
    * @return the joinpoint
    * @throws Throwable for any error
    */
   public static SetterJoinPoint getAttributeSetterJoinPoint(boolean trace, AttributeInfo info, Object value) throws Throwable
   {
      if (trace)
         log.trace("Get attribute setter join point info=" + info + " value=" + value);
      
      SetterJoinPoint joinPoint = findAttributeSetter(trace, info);
      TypeInfo type = info.getType();
      joinPoint.setValue(value);
      return joinPoint;
   }

   /**
    * Unconfigure a bean attribute
    * 
    * @param object the object to unconfigure
    * @param info the bean info
    * @param name the name of the attribute
    * @throws Throwable for any error
    */
   public static void unconfigure(Object object, BeanInfo info, String name) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      AttributeInfo ainfo = resolveAttribute(trace, info, name);
      unconfigure(trace, object, ainfo);
   }

   /**
    * Unconfigure a bean attribute
    * 
    * @param object the object to unconfigure
    * @param info the attribute info
    * @throws Throwable for any error
    */
   public static void unconfigure(Object object, AttributeInfo info) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      unconfigure(trace, object, info);
   }

   /**
    * Unconfigure a bean attribute
    *
    * @param trace whether trace is enabled
    * @param object the object to unconfigure
    * @param info the attribute info
    * @throws Throwable for any error
    */
   public static void unconfigure(boolean trace, Object object, AttributeInfo info) throws Throwable
   {
      if (trace)
         log.trace("Unconfiguring info=" + info);
      
      SetterJoinPoint joinPoint = getAttributeNullerJoinPoint(trace, info);
      joinPoint.setValue(null);
      joinPoint.setTarget(object);
      
      if (trace)
         log.trace("Unsetting attribute " + joinPoint + " target=" + object + " value=null");

      try
      {
         joinPoint.dispatch();
      }
      catch (Throwable t)
      {
         if (trace)
            log.trace("Error unsetting attribute - probably doesn't support a null value? " + info, t);
      }
   }

   /**
    * Get attribute nuller joinpoint for an attribute
    * 
    * @param info the bean info
    * @param name the name of the attribute
    * @return the join point
    * @throws Throwable for any error
    */
   public static SetterJoinPoint getAttributeNullerJoinPoint(BeanInfo info, String name) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      AttributeInfo ainfo = resolveAttribute(trace, info, name);
      return getAttributeNullerJoinPoint(trace, ainfo);
   }

   /**
    * Get attribute nuller joinpoint for an attribute
    * 
    * @param info the attribute info
    * @return the joinpoint
    * @throws Throwable for any error
    */
   public static SetterJoinPoint getAttributeNullerJoinPoint(AttributeInfo info) throws Throwable
   {
      boolean trace = log.isTraceEnabled();
      return getAttributeNullerJoinPoint(trace, info);
   }

   /**
    * Get an attribute nuller joinpoint
    *
    * @param trace whether trace is enabled
    * @param info the attribute info
    * @return the joinpoint
    * @throws Throwable for any error
    */
   public static SetterJoinPoint getAttributeNullerJoinPoint(boolean trace, AttributeInfo info) throws Throwable
   {
      if (trace)
         log.trace("Get attribute nuller join point info=" + info);
      
      SetterJoinPoint joinPoint = findAttributeSetter(trace, info);
      joinPoint.setValue(null);
      return joinPoint;
   }
   
   /**
    * Find an attribute settter
    * 
    * @param trace whether trace is enabled
    * @param info the attribute info
    * @return the join point
    * @throws Exception for any error
    */
   public static SetterJoinPoint findAttributeSetter(boolean trace, AttributeInfo info) throws Exception
   {
      if (info.getSetter() == null)
         throw new IllegalArgumentException("Attribute is read only " + info);
      return new SetterJoinPoint(info);
   }
   
   /**
    * Resolved an attribute
    * 
    * @param trace whether trace is enabled
    * @param info the bean info
    * @param name the name of the attribute
    * @return the attribute info
    */
   public static AttributeInfo resolveAttribute(boolean trace, BeanInfo info, String name)
   {
      Set attributes = info.getAttributes();
      if (attributes.isEmpty() == false)
      {
         for (Iterator i = info.getAttributes().iterator(); i.hasNext();)
         {
            AttributeInfo ainfo = (AttributeInfo) i.next();
            if (name.equals(ainfo.getName()))
            {
               if (trace)
                  log.trace("Found attribute " + ainfo);
               return ainfo;
            }
         }
      }
      throw new IllegalArgumentException("Attribute " + name + " not found for " + info);
   }
   
   /**
    * Find a method
    * 
    * @param info the bean info
    * @param name the name of the method
    * @param paramTypes the parameter types
    * @param isStatic whether the method is static
    * @param isPublic whether the method is public
    * @return the join point
    * @throws Exception for any error
    */
   public static MethodJoinPoint findMethod(BeanInfo info, String name, String[] paramTypes, boolean isStatic, boolean isPublic) throws Exception
   {
      boolean trace = log.isTraceEnabled();
      return findMethod(trace, info, name, paramTypes, isStatic, isPublic);
   }
   
   /**
    * Find a method
    * 
    * @param trace whether trace is enabled
    * @param info the bean info
    * @param name the name of the method
    * @param paramTypes the parameter types
    * @param isStatic whether the method is static
    * @param isPublic whether the method is public
    * @return the join point
    * @throws Exception for any error
    */
   public static MethodJoinPoint findMethod(boolean trace, BeanInfo info, String name, String[] paramTypes, boolean isStatic, boolean isPublic) throws Exception
   {
      MethodInfo minfo = resolveMethod(trace, info, name, paramTypes, isStatic, isPublic);
      return new MethodJoinPoint(minfo);
   }
   
   /**
    * Resolve a method
    * 
    * @param trace whether trace is enabled
    * @param info the bean info
    * @param name the name of the method
    * @param paramTypes the parameter types
    * @param isStatic whether the method is static
    * @param isPublic whether the method is public
    * @return the constructor info
    */
   public static MethodInfo resolveMethod(boolean trace, BeanInfo info, String name, String[] paramTypes, boolean isStatic, boolean isPublic)
   {
      if (paramTypes == null)
         paramTypes = new String[0];

      Set methods = info.getMethods();
      if (methods.isEmpty() == false)
      {
         for (Iterator i = info.getClassInfo().getMethods().iterator(); i.hasNext();)
         {
            MethodInfo minfo = (MethodInfo) i.next();
            if (isStatic != minfo.isStatic())
               continue;
            if (isPublic == true && minfo.isPublic() != true)
               continue;
            if (minfo.getName().equals(name) == false)
               continue;
            if (minfo.getParameters().size() != paramTypes.length)
               continue;
            int x = 0;
            boolean found = true;
            for (Iterator j = minfo.getParameters().iterator(); j.hasNext();)
            {
               ParameterInfo pinfo = (ParameterInfo) j.next();
               if (paramTypes[x++].equals(pinfo.getTypeInfo().getName()) == false)
               {
                  found = false;
                  break;
               }
            }
            if (found)
            {
               if (trace)
                  log.trace("Found method " + minfo);
               return minfo;
            }
         }
      }
      throw new IllegalArgumentException("Method " + name + Arrays.asList(paramTypes) + " not found for " + info);
   }
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
