/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.plugins.introspection;

import java.lang.reflect.Field;

import org.jboss.reflect.ClassInfo;
import org.jboss.reflect.FieldInfo;
import org.jboss.reflect.TypeInfoFactory;
import org.jboss.reflect.plugins.FieldInfoImpl;

/**
 * A field info
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectFieldInfo extends FieldInfoImpl
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The field object */
   protected Field field;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new field info
    */
   public ReflectFieldInfo()
   {
   }

   /**
    * Create a new FieldInfo.
    *
    * @param factory the type info factory
    * @param declaring the declaring class
    * @param field the field
    */
   public ReflectFieldInfo(TypeInfoFactory factory, ClassInfo declaring, Field field)
   {
      super(null, field.getName(), factory.getTypeInfo(field.getType()), field.getModifiers(), declaring);
      this.field = field;
   }

   // Public --------------------------------------------------------

   /**
    * Get the field
    * 
    * @return the field
    */
   public Field getField()
   {
      return field;
   }
   
   // Object overrides ----------------------------------------------

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
