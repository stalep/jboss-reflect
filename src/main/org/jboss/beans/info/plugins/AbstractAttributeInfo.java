/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import org.jboss.beans.info.spi.AttributeInfo;
import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.util.JBossObject;

/**
 * Attribute info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractAttributeInfo extends JBossObject implements AttributeInfo
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The attribute name */
   protected String name;

   /** The upper attribute name */
   protected String upperName;
   
   /** The type */
   protected TypeInfo type;
   
   /** The getter */
   protected MethodInfo getter;
   
   /** The setter */
   protected MethodInfo setter;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new attribute info
    */
   public AbstractAttributeInfo()
   {
      this(null, null, null, null, null);
   }

   /**
    * Create a new attribute info
    * 
    * @param name the name
    */
   public AbstractAttributeInfo(String name)
   {
      this(name, name, null, null, null);
   }

   /**
    * Create a new attribute info
    * 
    * @param name the name
    * @param type the type
    * @param getter the getter
    * @param setter the setter
    */
   public AbstractAttributeInfo(String name, String upperName, TypeInfo type, MethodInfo getter, MethodInfo setter)
   {
      this.name = name;
      this.upperName = upperName;
      this.type = type;
      this.getter = getter;
      this.setter = setter;
   }
   
   // Public --------------------------------------------------------

   // AttributeInfo implementation ----------------------------------
   
   public String getName()
   {
      return name;
   }
   
   public String getUpperName()
   {
      return upperName;
   }
   
   public TypeInfo getType()
   {
      return type;
   }

   public void setType(TypeInfo type)
   {
      this.type = type;
   }
   
   public MethodInfo getGetter()
   {
      return getter;
   }

   public void setGetter(MethodInfo getter)
   {
      this.getter = getter;
   }
   
   public MethodInfo getSetter()
   {
      return setter;
   }

   public void setSetter(MethodInfo setter)
   {
      this.setter = setter;
   }

   // Object overrides -----------------------------------------------
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractAttributeInfo == false)
         return false;
      
      AbstractAttributeInfo other = (AbstractAttributeInfo) object;
      if (notEqual(name, other.name))
         return false;
      else if (notEqual(getter, other.getter))
         return false;
      else if (notEqual(setter, other.setter))
         return false;
      return true;
   }
   
   // JBossObject overrides ------------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" getter=").append(getter);
      buffer.append(" setter=").append(setter);
   }
   
   public void toShortString(StringBuffer buffer)
   {
      buffer.append(name);
   }

   public int getHashCode()
   {
      return name.hashCode();
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
