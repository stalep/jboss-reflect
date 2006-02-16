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
package org.jboss.beans.info.plugins;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.BeanInfoFactory;
import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.PrimitiveInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.util.collection.WeakValueHashMap;

/**
 * A bean info factory.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractBeanInfoFactory implements BeanInfoFactory
{
   /** The cache */
   protected Map cache = new WeakHashMap(); 
   
   protected static boolean isGetter(MethodInfo minfo)
   {
      String name = minfo.getName();
      TypeInfo returnType = minfo.getReturnType();
      TypeInfo[] parameters = minfo.getParameterTypes();
      if ((name.length() > 3 && name.startsWith("get")) || (name.length() > 2 && name.startsWith("is")))
      {
         if (parameters.length == 0 && PrimitiveInfo.VOID.equals(returnType) == false)
            return true;
      }
      return false;
   }
   
   protected static boolean isSetter(MethodInfo minfo)
   {
      String name = minfo.getName();
      TypeInfo returnType = minfo.getReturnType();
      TypeInfo[] parameters = minfo.getParameterTypes();
      if ((name.length() > 3 && name.startsWith("set")))
      {
         if (parameters.length == 1 && PrimitiveInfo.VOID.equals(returnType))
            return true;
      }
      return false;
   }
   
   protected static String getUpperPropertyName(String name)
   {
      int start = 3;
      if (name.startsWith("is"))
         start = 2;
      
      return name.substring(start);
   }
   
   protected static String getLowerPropertyName(String name)
   {
      StringBuffer buffer = new StringBuffer(name.length());
      buffer.append(Character.toLowerCase(name.charAt(0)));
      if (name.length() > 1)
         buffer.append(name.substring(1));
      return buffer.toString();
   }

   /**
    * Create a new bean info factory
    */
   public AbstractBeanInfoFactory()
   {
   }

   public BeanInfo getBeanInfo(ClassAdapter classAdapter)
   {
      synchronized (cache)
      {
         ClassLoader cl = classAdapter.getClassLoader();
         ClassInfo classInfo = classAdapter.getClassInfo();
         String className = classInfo.getName();
         Map map = (Map) cache.get(cl);
         if (map != null)
         {
            BeanInfo info = (BeanInfo) map.get(className);
            if (info != null)
               return info;
         }

         if (classInfo.isInterface())
            throw new IllegalArgumentException(classInfo.getName() + " is an interface");

         Set constructors = getConstructors(classInfo);
         Set methods = getMethods(classInfo);
         Set properties = getProperties(methods);
         Set events = getEvents(classInfo);
         
         BeanInfo result = createBeanInfo(classAdapter, properties, constructors, methods, events);
         if (map == null)
         {
            map = new WeakValueHashMap();
            cache.put(cl, map);
         }
         map.put(className, result);
         return result;
      }
   }
   
   /**
    * Create the bean info
    * 
    * @param classAdapter the class adapter
    * @param properties the properties
    * @param constructors the constructors
    * @param methods the methods
    * @param events the events
    */
   protected BeanInfo createBeanInfo(ClassAdapter classAdapter, Set properties, Set constructors, Set methods, Set events)
   {
      return new AbstractBeanInfo(this, classAdapter, properties, constructors, methods, events);
   }
   
   /**
    * Get the constructors
    * 
    * @param classInfo the class info
    * @return the constructors
    */
   protected Set getConstructors(ClassInfo classInfo)
   {
      ConstructorInfo[] cinfos = classInfo.getDeclaredConstructors();
      if (cinfos == null || cinfos.length == 0)
         return null;

      HashSet result = new HashSet();
      for (int i = 0; i < cinfos.length; ++i)
         result.add(cinfos[i]);
      return result;
   }
   
   /**
    * Get the methods
    * 
    * @param classInfo the class info
    * @return the methods
    */
   protected Set getMethods(ClassInfo classInfo)
   {
      HashSet result = new HashSet();
      while (classInfo != null)
      {
         MethodInfo[] minfos = classInfo.getDeclaredMethods();
         if (minfos != null && minfos.length > 0)
         {
            for (int i = 0; i < minfos.length; ++i)
            {
               if (result.contains(minfos[i]) == false)
                  result.add(minfos[i]);
            }
         }
         
         classInfo = classInfo.getSuperclass();
      }
      return result;
   }
   
   /**
    * Get the properties
    * 
    * @param methods the methods
    * @return the properties
    */
   protected Set getProperties(Set methods)
   {
      HashMap getters = new HashMap();
      HashMap setters = new HashMap();
      if (methods.isEmpty() == false)
      {
         for (Iterator i = methods.iterator(); i.hasNext();)
         {
            MethodInfo methodInfo = (MethodInfo) i.next();
            if (methodInfo.isPublic() && methodInfo.isStatic() == false)
            {
               String name = methodInfo.getName();
               String upperName = getUpperPropertyName(name);
               if (isGetter(methodInfo))
               {
                  getters.put(upperName, methodInfo);
               }
               else if (isSetter(methodInfo))
               {
                  ArrayList list = (ArrayList) setters.get(upperName);
                  if (list == null)
                  {
                     list = new ArrayList();
                     setters.put(upperName, list);
                  }
                  list.add(methodInfo);
               }
            }
         }
      }

      HashSet properties = new HashSet();
      if (getters.isEmpty() == false)
      {
         for (Iterator i = getters.entrySet().iterator(); i.hasNext();)
         {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            MethodInfo getter = (MethodInfo) entry.getValue();
            MethodInfo setter = null;
            ArrayList setterList = (ArrayList) setters.remove(name);
            if (setterList != null && setterList.size() != 0)
            {
               for (int j = 0; j < setterList.size(); ++j)
               {
                  MethodInfo thisSetter = (MethodInfo) setterList.get(j);
                  TypeInfo pinfo = thisSetter.getParameterTypes()[0];
                  if (getter.getReturnType().equals(pinfo) == true)
                  {
                     setter = thisSetter;
                     break;
                  }
               }
            }
            String lowerName = getLowerPropertyName(name);
            properties.add(new AbstractPropertyInfo(lowerName, name, getter.getReturnType(), getter, setter));
         }
      }
      if (setters.isEmpty() == false)
      {
         for (Iterator i = setters.entrySet().iterator(); i.hasNext();)
         {
            Map.Entry entry = (Map.Entry) i.next();
            String name = (String) entry.getKey();
            ArrayList setterList = (ArrayList) entry.getValue();
            // Review: Maybe should just create duplicate propertyInfo and let the configurator guess?
            if (setterList.size() == 1)
            {
               MethodInfo setter = (MethodInfo) setterList.get(0);
               TypeInfo pinfo = setter.getParameterTypes()[0];
               String lowerName = getLowerPropertyName(name);
               properties.add(new AbstractPropertyInfo(lowerName, name, pinfo, null, setter));
            }
         }
      }
      return properties;
   }
   
   /**
    * Get the events
    * 
    * @param classInfo the class info
    * @return the events
    */
   protected Set getEvents(ClassInfo classInfo)
   {
      return null;
   }
}
