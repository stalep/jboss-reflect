/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.container.plugins.joinpoint.bean;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.jboss.beans.info.spi.AttributeInfo;
import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.container.plugins.joinpoint.AbstractJoinPoint;
import org.jboss.container.spi.JoinPointException;
import org.jboss.util.Classes;

/**
 * An attribute setter join point
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SetterJoinPoint extends AbstractJoinPoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
 
   /** The attribute name */
   protected String attributeName;
   
   /** The attribute info */
   protected AttributeInfo attributeInfo;
   
   /** The value */
   protected Object value;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new attribute setter join point
    * 
    * @param attributeInfo the attribute info
    */
   public SetterJoinPoint(AttributeInfo attributeInfo)
   {
      this(attributeInfo, null);
   }
   
   /**
    * Create a new attribute setter join point
    * 
    * @param attributeInfo the attribute info
    * @param value the value
    */
   public SetterJoinPoint(AttributeInfo attributeInfo, Object value)
   {
      this.attributeInfo = attributeInfo;
      this.attributeName = attributeInfo.getName();
      this.value = value;
   }
   
   /**
    * Create a new attribute setter join point
    * 
    * @param name the attribute name
    */
   public SetterJoinPoint(String name)
   {
      this(name, null);
   }
   
   /**
    * Create a new attribute setter join point
    * 
    * @param name the attribute name
    * @param value the value
    */
   public SetterJoinPoint(String name, Object value)
   {
      this.attributeName = name;
      this.value = value;
   }
   
   // Public --------------------------------------------------------

   /**
    * Get the attribute info
    * 
    * @return the attribute info
    */
   public AttributeInfo getAttributeInfo()
   {
      return attributeInfo;
   }

   /**
    * Set the attribute info
    * 
    * @param info the attribute info
    */
   public void setAttributeInfo(AttributeInfo info)
   {
      this.attributeInfo = info;
   }
   
   /**
    * Get the value
    * 
    * @return the value
    */
   public Object getValue()
   {
      return value;
   }
   
   /**
    * Set the value 
    * 
    * @param value the value
    */
   public void setValue(Object value)
   {
      this.value = value;
   }
   
   // AbstractJoinPoint overrides -----------------------------------
   
   public Object dispatch() throws Throwable
   {
      MethodInfo setter = attributeInfo.getSetter();
      if (setter == null)
         throw new JoinPointException("Error on " + this, new IllegalStateException("No setter for attribute " + attributeInfo));
      Method method = setter.getMethod();
      try
      {
         return method.invoke(target, new Object[] { value });
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
      catch (Throwable t)
      {
         throw new JoinPointException("Error invoking setter " + method + " on " + Classes.getDescription(target), t);
      }
   }
   
   public String toHumanReadableString()
   {
      // TODO human readable
      return "SET " + attributeName;
   }
   
   // Object overrides ----------------------------------------------
   
   public boolean equals(Object obj)
   {
      if (obj == null || obj instanceof SetterJoinPoint == false)
         return false;
      SetterJoinPoint other = (SetterJoinPoint) obj;
      return attributeName.equals(other.attributeName);
   }
   
   public int hashCode()
   {
      return attributeName.hashCode();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
