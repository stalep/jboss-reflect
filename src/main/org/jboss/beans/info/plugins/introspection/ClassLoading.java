/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins.introspection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import org.jboss.logging.Logger;

// TODO Review

/**
 * Reflection utility.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ClassLoading
{
   // Constants -----------------------------------------------------

   /** The log */
   private static final Logger log = Logger.getLogger(ClassLoading.class);
   
   /** The primitive classes */
   private static final Map primitives; 

   // Attributes ----------------------------------------------------
   
   // Static --------------------------------------------------------

   static
   {
      primitives = new HashMap();
      primitives.put("byte", Byte.TYPE);
      primitives.put("boolean", Boolean.TYPE);
      primitives.put("char", Character.TYPE);
      primitives.put("short", Short.TYPE);
      primitives.put("int", Integer.TYPE);
      primitives.put("long", Long.TYPE);
      primitives.put("float", Float.TYPE);
      primitives.put("double", Double.TYPE);
   }

   public static Class loadClass(ClassLoader cl, String name) throws Exception
   {
      return loadClass(false, null, cl, name);
   }
   
   public static Class loadClass(boolean trace, String ctx, ClassLoader cl, String name) throws Exception
   {
      if (trace)
         log.trace("Loading " + ctx + " class=" + name + " classloader=" + cl);
      Class clazz = (Class) primitives.get(name);
      try
      {
         if (clazz == null)
            clazz = cl.loadClass(name);
      }
      catch (ClassNotFoundException e)
      {
         clazz = Class.forName(name);
      }
      if (trace)
         log.trace("Loaded " + ctx + " " + clazz + " classloader=" + clazz.getClassLoader());
      return clazz;
   }
   
   // Constructors --------------------------------------------------
   
   // Public --------------------------------------------------------
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
