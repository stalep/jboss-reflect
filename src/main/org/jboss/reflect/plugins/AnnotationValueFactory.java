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
package org.jboss.reflect.plugins;

import org.jboss.reflect.spi.AnnotationInfo;
import org.jboss.reflect.spi.ArrayInfo;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.EnumInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.PrimitiveValue;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.Value;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class AnnotationValueFactory 
{

   public static Value createValue(AnnotationHelper annotationHelper, TypeInfo type, Object value)
   {
      Value rtnValue = null;
      if (type instanceof ArrayInfo)
      {
         Object[] objects = getArray((ArrayInfo)type, value);
         Value[] values = new Value[objects.length];
         for (int i = 0 ; i < objects.length ; i++)
         {
            values[i] = createValue(annotationHelper, ((ArrayInfo)type).getComponentType(), objects[i]);
         }
         rtnValue = new ArrayValueImpl(type, values);
      }
      else if (type instanceof PrimitiveInfo)
      {
         rtnValue = new PrimitiveValue(value.toString(), (PrimitiveInfo)type); 
      }
      else if (type.getName().equals("java.lang.String"))
      {
         rtnValue = new StringValueImpl((String)value, type);
      }
      else if (type instanceof AnnotationInfo)
      {
         rtnValue = annotationHelper.createAnnotationValue((AnnotationInfo)type, value);
      }
      else if (type instanceof EnumInfo)
      {
         rtnValue = new EnumValueImpl(type, value.toString());
      }
      else if (type instanceof ClassInfo)
      {
         rtnValue = new ClassValueImpl(((Class)value).getName(), type); //FixMe - do not depend on Class
      }
      
      return rtnValue;
   }

   private static Object[] getArray(ArrayInfo arrayInfo, Object value)
   {
      TypeInfo componentType = arrayInfo.getComponentType();
      if (!(componentType instanceof PrimitiveInfo))
      {
         return (Object[])value;
      }
      else
      {
         Object[] ret = null;
         String typeName = ((PrimitiveInfo)componentType).getName();

         if (typeName.equals("boolean"))
         {
            boolean[] input = (boolean[])value;
            ret = new Boolean[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Boolean(input[i]);
            }
         }
         else if (typeName.equals("char"))
         {
            char[] input = (char[])value;
            ret = new Character[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Character(input[i]);
            }
         }
         else if (typeName.equals("double"))
         {
            double[] input = (double[])value;
            ret = new Double[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Double(input[i]);
            }
         }
         else if (typeName.equals("float"))
         {
            float[] input = (float[])value;
            ret = new Float[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Float(input[i]);
            }

         }
         else if (typeName.equals("int"))
         {
            int[] input = (int[])value;
            ret = new Integer[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Integer(input[i]);
            }
            
         }
         else if (typeName.equals("long"))
         {
            long[] input = (long[])value;
            ret = new Long[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Long(input[i]);
            }

         }
         else if (typeName.equals("short"))
         {
            short[] input = (short[])value;
            ret = new Short[input.length];
            for (int i = 0 ; i < ret.length ; i++)
            {
               ret[i] = new Short(input[i]);
            }
            
         }
         
         if (ret == null)
         {
            throw new RuntimeException("Array component type " + componentType + " is not handled");
         }
          
         return ret;
      }
   }
}
