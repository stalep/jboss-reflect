/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/ 
package org.jboss.reflect.plugins.javassist;

import java.util.HashMap;

import javassist.CtClass;

import org.jboss.reflect.plugins.AnnotationAttributeImpl;
import org.jboss.reflect.plugins.AnnotationInfoImpl;
import org.jboss.reflect.spi.AnnotationAttribute;
import org.jboss.reflect.spi.AnnotationInfo;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class JavassistAnnotationInfo extends JavassistTypeInfo implements AnnotationInfo
{
   /** serialVersionUID */
   private static final long serialVersionUID = 3546645408219542832L;
   
   /** The attributes */
   protected AnnotationAttribute[] attributes;
   
   /** Attribute Map<String, AnnotationAttribute> */
   protected HashMap attributeMap;

   public JavassistAnnotationInfo(JavassistTypeInfoFactoryImpl factory, CtClass ctClass, Class clazz)
   {
      // FIXME JavassistAnnotationInfoImpl constructor
      super(factory, ctClass, clazz);
   }

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

   public boolean equals(Object o)
   {
      if (this == o) return true;
      if (!(o instanceof AnnotationInfoImpl)) return false;

      final AnnotationInfoImpl annotationInfo = (AnnotationInfoImpl) o;

      if (!getName().equals(annotationInfo.getName())) return false;

      return true;
   }

   public int hashCode()
   {
      return getName().hashCode();
   }
   
}
