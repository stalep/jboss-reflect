/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import org.jboss.reflect.AnnotationValue;
import org.jboss.reflect.ParameterInfo;
import org.jboss.reflect.TypeInfo;

/**
 * Parameter info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ParameterInfoImpl extends AnnotationHolder implements ParameterInfo
{
   // Constants -----------------------------------------------------
   
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

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
