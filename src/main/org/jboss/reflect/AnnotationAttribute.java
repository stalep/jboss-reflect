/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public class AnnotationAttribute
{
   protected String name;
   protected TypeInfo type;
   protected Value defaultValue;
   protected int hash = -1;

   public AnnotationAttribute()
   {
   }

   public AnnotationAttribute(String name, TypeInfo type, Value defaultValue)
   {
      this.name = name;
      this.type = type;
      this.defaultValue = defaultValue;
      calcHashCode();
   }

   public String getName()
   {
      return name;
   }

   public TypeInfo getType()
   {
      return type;
   }

   public Value getDefaultValue()
   {
      return defaultValue;
   }

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AnnotationAttribute)) return false;

      final AnnotationAttribute annotationAttribute = (AnnotationAttribute) o;

      if (!name.equals(annotationAttribute.name)) return false;
      if (!type.equals(annotationAttribute.type)) return false;

      return true;
   }

   public int hashCode()
   {
      return hash;
   }

   public void calcHashCode()
   {
      int result;
      result = name.hashCode();
      result = 29 * result + type.hashCode();
      hash = result;
   }


}
