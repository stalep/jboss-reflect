/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.spi;

import java.lang.reflect.Method;
import java.util.List;

import org.jboss.util.JBossInterface;

/**
 * Information about a method.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface MethodInfo extends JBossInterface
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the method name
    * 
    * @return the name
    */
   String getName();

   /**
    * Get the return type
    * 
    * @return the return type
    */
   TypeInfo getReturnType();

   /**
    * Set the return type
    * 
    * @param typeInfo the return type
    */
   void setReturnType(TypeInfo typeInfo);

   /**
    * Get the parameters
    * 
    * @return List<ParameterInfo>
    */
   List getParameters();

   /**
    * Set the parameters
    * 
    * @param parameters List<ParameterInfo>
    */
   void setParameters(List parameters);
   
   /**
    * Is this a getter method
    * 
    * @return true when it follows the javabean definition
    */
   boolean isGetter();
   
   /**
    * Is this a setter method
    * 
    * @return true when it follows the javabean definition
    */
   boolean isSetter();

   /**
    * Whether the method is static
    * 
    * @return true when the method is static
    */
   boolean isStatic();

   /**
    * Whether the method is public
    * 
    * @return true when the method is public
    */
   boolean isPublic();
   
   /**
    * Get the method
    * 
    * @return the method
    */
   Method getMethod();
   
   // Inner classes -------------------------------------------------
}
