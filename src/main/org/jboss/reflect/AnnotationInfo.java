/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

import java.util.HashMap;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class AnnotationInfo extends InterfaceInfo
{
   protected AnnotationAttribute[] attributes;
   protected HashMap attributeMap;

   public AnnotationInfo()
   {
   }

   public AnnotationInfo(String name, int modifiers, AnnotationValue[] annotations)
   {
      super(name, modifiers, null, annotations);
   }

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
      if (attributeMap == null) return null;
      return (AnnotationAttribute)attributeMap.get(name);
   }

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

}
