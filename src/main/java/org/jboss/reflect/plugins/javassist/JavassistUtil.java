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

import org.jboss.reflect.spi.ClassInfo;

import javassist.CannotCompileException;
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
   //TODO: need to change the classpool/loader fetching
   static ClassPool pool = ClassPool.getDefault();
   static ClassLoader loader = JavassistUtil.class.getClassLoader();

   public static Class<?> ctClassToClass(CtClass ct)
   {
      if(ct.isModified())
      {
         try
         {
            ct.toClass();
         }
         catch (CannotCompileException e)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e.toString());
         }
      }
      
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
         throw new org.jboss.reflect.spi.NotFoundException(e.toString());
      }
      catch (ClassNotFoundException e)
      {
         try
         {
            return ct.toClass();
         }
         catch (CannotCompileException e1)
         {
            throw new org.jboss.reflect.spi.CannotCompileException(e1.toString());
         }
      }
   }
   
   public static CtClass toCtClass(String name)
   {
      try
      {
         return pool.get(name);
      }
      catch (NotFoundException e)
      {
        throw new org.jboss.reflect.spi.NotFoundException(e.toString());
      }
   }
   
   public static CtClass[] toCtClass(String[] names)
   {
      if(names == null)
         return new CtClass[0];
      CtClass[] classes = new CtClass[names.length];
      for(int i=0; i < names.length; i++)
      {
         classes[i] = toCtClass(names[i]);
      }
      return classes;
   }
   

   public static CtClass toCtClass(ClassInfo clazz)
   {
      if(clazz instanceof JavassistTypeInfo)
         return ((JavassistTypeInfo) clazz).getCtClass();
      else
      {
         try
         {
            return pool.get(clazz.getName());
         }
         catch (NotFoundException e)
         {
            throw new org.jboss.reflect.spi.NotFoundException(e.toString());
         }
      }
   }
   
   public static CtClass[] toCtClass(ClassInfo[] classes)
   {
      CtClass[] clazzes = new CtClass[classes.length];
      for(int i=0; i < classes.length; i++)
      {
         clazzes[i] = toCtClass(classes[i]);
      }
      return clazzes;
   }
   
   

}
