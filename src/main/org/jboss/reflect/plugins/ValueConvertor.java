/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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

import java.beans.PropertyEditor;
import java.beans.PropertyEditorManager;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import org.jboss.logging.Logger;
import org.jboss.reflect.plugins.introspection.ReflectionUtils;
import org.jboss.reflect.spi.ProgressionConvertor;
import org.jboss.reflect.spi.ProgressionConvertorFactory;
import org.jboss.util.StringPropertyReplacer;
import org.jboss.util.propertyeditor.PropertyEditors;

/**
 * PropertyEditorHelper.
 * <p/>
 * TODO JBMICROCONT-118 fix the introspection assumption
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ValueConvertor
{
   /**
    * The log
    */
   private static final Logger log = Logger.getLogger(ValueConvertor.class);

   static
   {
      try
      {
         PropertyEditors.init();
      }
      catch (Throwable t)
      {
         log.debug("Unable to initialise property editors", t);
      }
   }

   /**
    * Convert a value
    * <p/>
    *
    * @param clazz the class
    * @param value the value
    * @return the value or null if there is no editor
    * @throws Throwable for any error
    */
   public static Object convertValue(Class<? extends Object> clazz, Object value) throws Throwable
   {
      return convertValue(clazz, value, false);
   }

   /**
    * Convert a value
    *
    * @param clazz             the class
    * @param value             the value
    * @param replaceProperties whether to replace system properties
    * @return the value or null if there is no editor
    * @throws Throwable for any error
    */
   @SuppressWarnings("unchecked")
   public static Object convertValue(Class<? extends Object> clazz, Object value, boolean replaceProperties) throws Throwable
   {
      return convertValue(clazz, value, replaceProperties, false);
   }

   /**
    * Convert a value
    *
    * @param clazz             the class
    * @param value             the value
    * @param replaceProperties whether to replace system properties
    * @param trim whether to trim string value
    * @return the value or null if there is no editor
    * @throws Throwable for any error
    */
   @SuppressWarnings("unchecked")
   public static Object convertValue(Class<? extends Object> clazz, Object value, boolean replaceProperties, boolean trim) throws Throwable
   {
      if (clazz == null)
         throw new IllegalArgumentException("Null class");
      if (value == null)
         return null;

      Class<? extends Object> valueClass = value.getClass();

      // If we have a string, trim and replace any system properties when requested
      if (valueClass == String.class)
      {
         String string = (String)value;
         if (trim)
            string = string.trim();
         if (replaceProperties)
            value = StringPropertyReplacer.replaceProperties(string);
      }

      if (clazz.isAssignableFrom(valueClass))
         return value;

      // First see if this is an Enum
      if (clazz.isEnum())
      {
         Class<? extends Enum> eclazz = clazz.asSubclass(Enum.class);
         return Enum.valueOf(eclazz, value.toString());
      }

      // Next look for a property editor
      if (valueClass == String.class)
      {
         PropertyEditor editor = PropertyEditorManager.findEditor(clazz);
         if (editor != null)
         {
            editor.setAsText((String)value);
            return editor.getValue();
         }
      }

      // Try a static clazz.valueOf(value)
      try
      {
         Method method = clazz.getMethod("valueOf", valueClass);
         int modifiers = method.getModifiers();
         if (Modifier.isPublic(modifiers) && Modifier.isStatic(modifiers)
               && clazz.isAssignableFrom(method.getReturnType()))
            return ReflectionUtils.invoke(null, method, new Object[]{value});
      }
      catch (Exception ignored)
      {
      }

      // TODO JBMICROCONT-132 improve <init>(String) might not be relevent?
      if (valueClass == String.class)
      {
         try
         {
            Constructor constructor = clazz.getConstructor(valueClass);
            if (Modifier.isPublic(constructor.getModifiers()))
               return ReflectionUtils.newInstance(constructor, new Object[]{value});
         }
         catch (Exception ignored)
         {
         }
      }

      return value;
   }

   /**
    * Progress a value
    * <p/>
    *
    * @param clazz the class
    * @param value the value
    * @return the progressed value or null if unsupported
    * @throws Throwable for any error
    */
   public static Object progressValue(Class<? extends Object> clazz, Object value) throws Throwable
   {
      if (value != null)
      {
         ProgressionConvertor convertor = ProgressionConvertorFactory.getInstance().getConvertor();
         if (convertor.canProgress(clazz, value.getClass()))
         {
            return convertor.doProgression(clazz, value);
         }
         else
         {
            return null;
         }
      }
      return value;
   }

}
