/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.List;

import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.beans.info.spi.TypeInfo;
import org.jboss.util.JBossObject;

/**
 * Method info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractMethodInfo extends JBossObject implements MethodInfo
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The method name */
   protected String name;
   
   /** The return type */
   protected TypeInfo returnType;
   
   /** The parameters */
   protected List parameters;
   
   /** The modifier */
   protected int modifier;
   
   /** The method */
   protected Method method;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new method info with no parameters or return type
    * 
    * @param name the method name
    */
   public AbstractMethodInfo(String name)
   {
      this(name, AbstractClassInfo.VOID, Collections.EMPTY_LIST, AbstractTypeInfo.PUBLIC_MEMBER);
   }

   /**
    * Create a new method info with no parameters
    * 
    * @param name the method name
    * @param returnType the return type
    */
   public AbstractMethodInfo(String name, TypeInfo returnType)
   {
      this(name, returnType, Collections.EMPTY_LIST, AbstractTypeInfo.PUBLIC_MEMBER);
   }

   /**
    * Create a new method info with no return type
    * 
    * @param name the method name
    * @param parameters List<ParameterInfo>
    */
   public AbstractMethodInfo(String name, List parameters)
   {
      this(name, AbstractClassInfo.VOID, parameters, AbstractTypeInfo.PUBLIC_MEMBER);
   }

   /**
    * Create a new method info
    * 
    * @param name the method name
    * @param returnType the return type
    * @param parameters List<ParameterInfo>
    */
   public AbstractMethodInfo(String name, TypeInfo returnType, List parameters)
   {
      this(name, returnType, parameters, AbstractTypeInfo.PUBLIC_MEMBER);
   }

   /**
    * Create a new method info
    * 
    * @param name the method name
    * @param returnType the return type
    * @param parameters List<ParameterInfo>
    * @param modifier the modifier
    */
   public AbstractMethodInfo(String name, TypeInfo returnType, List parameters, int modifier)
   {
      this.name = name;
      this.returnType = returnType;
      this.parameters = parameters;
      this.modifier = modifier;
   }
   
   // Public --------------------------------------------------------

   // MethodInfo implementation -------------------------------------
   
   public String getName()
   {
      return name;
   }
   
   public TypeInfo getReturnType()
   {
      return returnType;
   }
   
   public void setReturnType(TypeInfo returnType)
   {
      this.returnType = returnType;
   }
   
   public List getParameters()
   {
      return parameters;
   }
   
   public void setParameters(List parameters)
   {
      this.parameters = parameters;
   }
   
   public boolean isGetter()
   {
      if ((name.length() > 3 && name.startsWith("get")) || (name.length() > 2 && name.startsWith("is")))
      {
         if (parameters.size() == 0 && Void.TYPE.getName().equals(returnType.getName()) == false)
               return true;
      }
      return false;
   }
   
   public boolean isSetter()
   {
      if ((name.length() > 3 && name.startsWith("set")))
      {
         if (parameters.size() == 1 && Void.TYPE.getName().equals(returnType.getName()))
               return true;
      }
      return false;
   }
   
   public boolean isStatic()
   {
      return Modifier.isStatic(modifier);
   }
   
   public boolean isPublic()
   {
      return Modifier.isPublic(modifier);
   }
   
   public Method getMethod()
   {
      return method;
   }
   
   public void setMethod(Method method)
   {
      this.method = method;
   }
   
   // Object overrides -----------------------------------------------
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractMethodInfo == false)
         return false;
      
      AbstractMethodInfo other = (AbstractMethodInfo) object;
      if (notEqual(name, other.name))
         return false;
      else if (notEqual(returnType, other.returnType))
         return false;
      else if (notEqual(parameters, other.parameters))
         return false;
      return true;
   }
   
   // JBossObject overrides ------------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" returnType=");
      returnType.toShortString(buffer);
      buffer.append(" parameters=");
      AbstractParameterInfo.list(buffer, parameters);
      AbstractTypeInfo.appendModifier(buffer, modifier);
   }
   
   public void toShortString(StringBuffer buffer)
   {
      returnType.toShortString(buffer);
      buffer.append(" ").append(name);
      AbstractParameterInfo.list(buffer, parameters);
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
