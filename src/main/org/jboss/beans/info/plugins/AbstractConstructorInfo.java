/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import org.jboss.beans.info.spi.ConstructorInfo;
import org.jboss.util.JBossObject;

/**
 * Constructor info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractConstructorInfo extends JBossObject implements ConstructorInfo
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The parameters */
   protected List parameters;
   
   /** The modifier */
   protected int modifier;
   
   /** The constructor */
   protected Constructor constructor;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new constructor info
    */
   public AbstractConstructorInfo()
   {
      this(Collections.EMPTY_LIST);
   }

   /**
    * Create a new constructor info
    * 
    * @param parameters the parameters
    */
   public AbstractConstructorInfo(List parameters)
   {
      this(parameters, AbstractTypeInfo.PUBLIC_MEMBER);
   }

   /**
    * Create a new constructor info
    * 
    * @param parameters the parameters
    * @param modifier the modifier
    */
   public AbstractConstructorInfo(List parameters, int modifier)
   {
      this.parameters = parameters;
      this.modifier = modifier;
   }
   
   // Public --------------------------------------------------------

   // ConstructorInfo implementation --------------------------------
   
   public List getParameters()
   {
      return parameters;
   }
   
   public void setParameters(List parameters)
   {
      this.parameters = parameters;
   }
   
   public boolean isStatic()
   {
      return Modifier.isStatic(modifier);
   }
   
   public boolean isPublic()
   {
      return Modifier.isPublic(modifier);
   }

   public Constructor getConstructor()
   {
      return constructor;
   }
   
   public void setConstructor(Constructor constructor)
   {
      this.constructor = constructor;
   }

   // Object overrides -----------------------------------------------
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractConstructorInfo == false)
         return false;
      
      AbstractConstructorInfo other = (AbstractConstructorInfo) object;
      if (notEqual(parameters, other.parameters))
         return false;
      return true;
   }

   // JBossObject overrides ------------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      buffer.append("parameters=");
      AbstractParameterInfo.list(buffer, parameters);
      AbstractTypeInfo.appendModifier(buffer, modifier);
   }

   public void toShortString(StringBuffer buffer)
   {
      toString(buffer);
   }
   
   public int getHashCode()
   {
      return parameters.hashCode();
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
