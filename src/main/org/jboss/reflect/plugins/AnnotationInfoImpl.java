/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins;

import java.util.HashMap;

import org.jboss.reflect.AnnotationAttribute;
import org.jboss.reflect.AnnotationInfo;
import org.jboss.reflect.AnnotationValue;

/**
 * Annotation Info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationInfoImpl extends InterfaceInfoImpl implements AnnotationInfo
{
   // Constants -----------------------------------------------------

   /** serialVersionUID */
   private static final long serialVersionUID = 3546645408219542832L;
   
   // Attributes ----------------------------------------------------
   
   /** The attributes */
   protected AnnotationAttribute[] attributes;
   
   /** Attribute Map<String, AnnotationAttribute> */
   protected HashMap attributeMap;

   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new AnnotationInfo.
    */
   public AnnotationInfoImpl()
   {
   }

   /**
    * Create a new AnnotationInfo.
    * 
    * @param name the name
    * @param modifiers the modifiers
    * @param annotations the annotation values
    */
   public AnnotationInfoImpl(String name, int modifiers, AnnotationValue[] annotations)
   {
      super(name, modifiers, null, annotations);
   }

   // Public --------------------------------------------------------

   /**
    * Set the attributes
    * 
    * @param attributes the attributes
    */
   public void setAttributes(AnnotationAttributeImpl[] attributes)
   {
      this.attributes = attributes;
      if (attributes != null && attributes.length > 0)
      {
         this.attributes = attributes;
         attributeMap = new HashMap();
         for (int i = 0; i < attributes.length; i++)
         {
            attributeMap.put(attributes[i].getName(), attributes[i]);
         }
      }
   }

   // AnnotationInfo implementation ---------------------------------

   public String getName()
   {
      return name;
   }

   public int getModifiers()
   {
      return modifiers;
   }

   public AnnotationAttribute[] getAttributes()
   {
      return attributes;
   }

   public AnnotationAttribute getAttribute(String name)
   {
      if (attributeMap == null)
         return null;
      return (AnnotationAttribute) attributeMap.get(name);
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AnnotationInfoImpl)) return false;

      final AnnotationInfoImpl annotationInfo = (AnnotationInfoImpl) o;

      if (!name.equals(annotationInfo.name)) return false;

      return true;
   }

   public int hashCode()
   {
      return name.hashCode();
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}