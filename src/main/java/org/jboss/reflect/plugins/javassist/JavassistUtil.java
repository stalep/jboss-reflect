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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.Map;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtPrimitiveType;
import javassist.NotFoundException;

/**
 * A JavassistUtil.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class JavassistUtil
{
   static ClassPool pool = ClassPool.getDefault();
   static ClassLoader loader = JavassistUtil.class.getClassLoader();

   final static Map<String, Class<?>> primitives;
   static
   {
      primitives = new HashMap<String, Class<?>>();
      primitives.put(CtClass.booleanType.getName(), Boolean.TYPE);
      primitives.put(CtClass.byteType.getName(), Byte.TYPE);
      primitives.put(CtClass.charType.getName(), Character.TYPE);
      primitives.put(CtClass.doubleType.getName(), Double.TYPE);
      primitives.put(CtClass.floatType.getName(), Float.TYPE);
      primitives.put(CtClass.intType.getName(), Integer.TYPE);
      primitives.put(CtClass.longType.getName(), Long.TYPE);
      primitives.put(CtClass.shortType.getName(), Short.TYPE);
      primitives.put(CtClass.voidType.getName(), Void.TYPE);

   }
   
   public static void main(String[] args) throws Exception
   {
      System.out.println(new String[0][].getClass().getName());
      System.out.println(new JavassistUtil[0].getClass().getName());
      System.out.println(new byte[0][].getClass().getName());
      Class.forName(new String[0][].getClass().getName());

      loadAndDisplayClass("int");
      loadAndDisplayClass("byte[][][]");
      loadAndDisplayClass("java.lang.String[][]");
      loadAndDisplayClass("java.lang.String[][][]");
      loadAndDisplayClass("org.jboss.reflect.plugins.javassist.JavassistUtil[]");
   }
   
   private static void loadAndDisplayClass(String name) throws Exception
   {
      Class<?> clazz = loadClass(name);
      System.out.println(name + " -> " + clazz.getName() + " : " + clazz);
   }
   
   private static Class<?> loadClass(String name) throws Exception
   {
      if (primitives.containsKey(name))
      {
         return primitives.get(name);
      }
      
      return ctClassToClass(pool.get(name));
   }

   public static Class<?> ctClassToClass(CtClass ct)
   {
      try
      {
      if (ct.isArray())
      {
         int dim = 0;
        
            while (ct.getComponentType() != null)
            {
               dim++;
               ct = ct.getComponentType();
            }
        
         if (ct.isPrimitive())
         {
            StringBuilder sb = new StringBuilder();
            for (int i = 0 ; i < dim ; i++)
            {
               sb.append("[");
            }
   
            sb.append(((CtPrimitiveType)ct).getDescriptor());
            try
            {
            return loader.loadClass(sb.toString());
            }
            catch(ClassNotFoundException cnfe)
            {
               return Class.forName(sb.toString(), false, loader);
            }
         }
         else
         {
            return Array.newInstance(ctClassToClass(ct), new int[dim]).getClass();
         }
      }
      else
      {
         
            return loader.loadClass(ct.getName());
         
         
      }
      }
      catch (NotFoundException e)
      {
         e.printStackTrace();
         return null;
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
         return null;
      }
   }

}
