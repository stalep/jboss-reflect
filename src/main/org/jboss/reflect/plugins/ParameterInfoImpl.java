/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.AnnotationValue;
import org.jboss.reflect.spi.ParameterInfo;
import org.jboss.reflect.spi.TypeInfo;

/**
 * Parameter info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ParameterInfoImpl extends AnnotationHolder implements ParameterInfo
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3256725082746664754L;
   
   // Attributes ----------------------------------------------------

   /** The parameter name */
   protected String name;
   
   /** The parameter type */
   protected TypeInfo parameterType;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new method info
    */
   public ParameterInfoImpl()
   {
   }

   /**
    * Create a new MethodInfo.
    * 
    * @param annotations the annotations
    * @param name the method name
    * @param parameterType the parameter type
    */
   public ParameterInfoImpl(AnnotationValue[] annotations, String name, TypeInfo parameterType)
   {
      super(annotations);
      this.name = name;
      this.parameterType = parameterType;
   }

   // Public --------------------------------------------------------

   // ParameterInfo implementation ----------------------------------

   public String getName()
   {
      return name;
   }

   public TypeInfo getParameterType()
   {
      return parameterType;
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object obj)
   {
      if (this == obj)
         return true;
      if (obj == null || obj instanceof ParameterInfo == false)
         return false;
      
      ParameterInfo other = (ParameterInfo) obj;
      return parameterType.equals(other.getParameterType());
   }
   
   public int hashCode()
   {
      return parameterType.hashCode();
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
