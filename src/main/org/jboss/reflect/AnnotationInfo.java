/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * Annotation Info
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class AnnotationInfo extends InterfaceInfo
{
   // Constants -----------------------------------------------------
   
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
   public AnnotationInfo()
   {
   }

   /**
    * Create a new AnnotationInfo.
    * 
    * @param name the name
    * @param modifiers the modifiers
    * @param annotations the annotation values
    */
   public AnnotationInfo(String name, int modifiers, AnnotationValue[] annotations)
   {
      super(name, modifiers, null, annotations);
   }

   // Public --------------------------------------------------------

   /**
    * Set the attributes
    * 
    * @param attributes the attributes
    */
   public void setAttributes(AnnotationAttribute[] attributes)
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

   /**
    * Get the name
    * 
    * @return the name
    */
   public String getName()
   {
      return name;
   }

   /**
    * Get the modifiers
    * 
    * return the modifiers
    */
   public int getModifiers()
   {
      return modifiers;
   }

   /**
    * Get the attributes
    * 
    * @return the attributes
    */
   public AnnotationAttribute[] getAttributes()
   {
      return attributes;
   }

   /**
    * Get an attribute
    * 
    * @param name the attribute name
    * @return the attribute
    */
   public AnnotationAttribute getAttribute(String name)
   {
      if (attributeMap == null) return null;
      return (AnnotationAttribute)attributeMap.get(name);
   }

   // Object overrides ----------------------------------------------

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AnnotationInfo)) return false;

      final AnnotationInfo annotationInfo = (AnnotationInfo) o;

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
