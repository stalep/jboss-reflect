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
 * An attribute getter join point
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class GetterJoinPoint extends AbstractJoinPoint
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------
 
   /** The attribute name */
   protected String attributeName;
   
   /** The attribute info */
   protected AttributeInfo attributeInfo;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------
   
   /**
    * Create a new attribute getter join point
    * 
    * @param attributeInfo the attribute info
    */
   public GetterJoinPoint(AttributeInfo attributeInfo)
   {
      this.attributeInfo = attributeInfo;
      this.attributeName = attributeInfo.getName();
   }
   
   /**
    * Create a new attribute getter join point
    * 
    * @param name the name of the attribute
    */
   public GetterJoinPoint(String name)
   {
      this.attributeName = name;
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
   
   // AbstractJoinPoint overrides -----------------------------------
   
   public Object dispatch() throws Throwable
   {
      MethodInfo getter = attributeInfo.getGetter();
      if (getter == null)
         throw new JoinPointException("Error on " + this, new IllegalStateException("No setter for attribute " + attributeInfo));
      Method method = getter.getMethod();
      try
      {
         return method.invoke(target, null);
      }
      catch (InvocationTargetException e)
      {
         throw e.getTargetException();
      }
      catch (Throwable t)
      {
         throw new JoinPointException("Error invoking getter " + method + " on " + Classes.getDescription(target), t);
      }
   }

   public String toHumanReadableString()
   {
      // TODO human readable
      return "GET " + attributeName;
   }
   
   // Object overrides ----------------------------------------------
   
   public boolean equals(Object obj)
   {
      if (obj == null || obj instanceof GetterJoinPoint == false)
         return false;
      GetterJoinPoint other = (GetterJoinPoint) obj;
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
