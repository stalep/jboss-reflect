/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.lang.reflect.Modifier;
import java.util.Set;

import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.util.JBossObject;

/**
 * Type info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractTypeInfo extends JBossObject implements TypeInfo
{
   // Constants -----------------------------------------------------
   
   /** Public member */
   public static final int PUBLIC_MEMBER = Modifier.PUBLIC;

   // Attributes ----------------------------------------------------

   /** The class name */
   protected String name;
   
   /** The super interfaces */
   protected Set superInterfaces;
   
   /** The attributes */
   protected Set attributes;
   
   /** The methods */
   protected Set methods;
   
   /** The class */
   protected Class type;
   
   // Static --------------------------------------------------------

   /**
    * Append the modifier
    * 
    * @param buffer the buffer to append
    * @param modifier the modifier
    */
   protected static void appendModifier(StringBuffer buffer, int modifier)
   {
      if (Modifier.isPrivate(modifier))
         buffer.append(" PRIVATE");
      else if (Modifier.isProtected(modifier))
         buffer.append(" PROTECTED");
      
      if (Modifier.isStatic(modifier))
         buffer.append(" STATIC");
   }

   // Constructors --------------------------------------------------

   /**
    * Create a new type info
    * 
    * @param name the class name
    */
   public AbstractTypeInfo(String name)
   {
      this.name = name;
   }

   /**
    * Create a new type info
    * 
    * @param name the class name
    * @param superInterfaces the super interfaces
    * @param attributes the attributes
    * @param methods the methods
    */
   public AbstractTypeInfo(String name, Set superInterfaces, Set attributes, Set methods)
   {
      this.name = name;
      this.superInterfaces = superInterfaces;
      this.attributes = attributes;
      this.methods = methods;
   }
   
   // Public --------------------------------------------------------

   // InterfaceInfo implementation ----------------------------------

   public String getName()
   {
      return name;
   }
   
   public Set getSuperInterfaceInfo()
   {
      return superInterfaces;
   }
   
   public void setSuperInterfaceInfo(Set superInterfaces)
   {
      this.superInterfaces = superInterfaces;
   }

   public Set getAttributes()
   {
      return attributes;
   }

   public void setAttributes(Set attributes)
   {
      this.attributes = attributes;
   }
   
   public Set getMethods()
   {
      return methods;
   }
   
   public void setMethods(Set methods)
   {
      this.methods = methods;
   }

   public Class getType()
   {
      return type;
   }
   
   public void setType(Class type)
   {
      this.type = type;
   }
   
   // Object overrides -----------------------------------------------
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractTypeInfo == false)
         return false;
      
      AbstractTypeInfo other = (AbstractTypeInfo) object;
      return equals(name, other.name);
   }

   // JBossObject overrides ------------------------------------------
   
   protected void toString(StringBuffer buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" superInterfaces=");
      JBossObject.list(buffer, superInterfaces);
      buffer.append(" attributes=");
      JBossObject.list(buffer, attributes);
      buffer.append(" methods=");
      JBossObject.list(buffer, methods);
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
