/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.util.Iterator;
import java.util.List;

import org.jboss.beans.info.spi.ParameterInfo;
import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.util.JBossObject;

/**
 * Parameter info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractParameterInfo extends JBossObject implements ParameterInfo
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The name of the parameter */
   protected String name;

   /** The type info */
   protected TypeInfo typeInfo;
   
   // Static --------------------------------------------------------

   /**
    * List the set of parameters
    * 
    * @param buffer the buffer to append
    * @param parameters the parameters
    */
   protected static void list(StringBuffer buffer, List parameters)
   {
      if (parameters == null)
         return;

      buffer.append('(');
      for (Iterator i = parameters.iterator(); i.hasNext();)
      {
         ParameterInfo info = (ParameterInfo) i.next();
         info.toShortString(buffer);
         if (i.hasNext())
            buffer.append(", ");
      }
      buffer.append(')');
   }

   // Constructors --------------------------------------------------

   /**
    * Create a new parameter info
    * 
    * @param name the name of the parameter
    * @param typeInfo the type info of the parameter
    */
   public AbstractParameterInfo(String name, TypeInfo typeInfo)
   {
      this.name = name;
      this.typeInfo = typeInfo;
   }
   
   // Public --------------------------------------------------------

   // ParameterInfo implementation ----------------------------------
   
   public String getName()
   {
      return name;
   }
   
   public TypeInfo getTypeInfo()
   {
      return typeInfo;
   }
   
   public void setTypeInfo(TypeInfo typeInfo)
   {
      this.typeInfo = typeInfo;
   }

   // Object overrides -----------------------------------------------
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractParameterInfo == false)
         return false;
      
      AbstractParameterInfo other = (AbstractParameterInfo) object;
      if (notEqual(name, other.name))
         return false;
      else if (notEqual(typeInfo, other.typeInfo))
         return false;
      return true;
   }

   // JBossObject overrides ------------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" typeInfo=");
      typeInfo.toShortString(buffer);
   }
   
   public void toShortString(StringBuffer buffer)
   {
      typeInfo.toShortString(buffer);
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
