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
package org.jboss.metadata.spi.signature;

import java.lang.reflect.Method;

import org.jboss.reflect.spi.MethodInfo;

/**
 * Method parameters Signature.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class MethodParametersSignature extends Signature
{
   /** The parameter number */
   private int param;
   
   /**
    * Create a new Signature.
    * 
    * @param name the name
    * @param param the parameter number
    * @param parameters the parameters
    */
   public MethodParametersSignature(String name, String[] parameters, int param)
   {
      super(name, parameters);
      this.param = param;
      checkParam();
   }

   /**
    * Create a new Signature.
    * 
    * @param name the name
    * @param param the parameter number
    * @param parameters the parameters
    */
   public MethodParametersSignature(String name, int param, Class<?>... parameters)
   {
      super(name, parameters);
      this.param = param;
      checkParam();
   }

   /**
    * Create a new Signature.
    * 
    * @param method the method
    * @param param the parameter number
    */
   public MethodParametersSignature(Method method, int param)
   {
      super(method.getName(), method.getParameterTypes());
      this.param = param;
      checkParam();
   }
   
   /**
    * Create a new Signature.
    *
    * @param method the method info
    * @param param the parameter number
    */
   public MethodParametersSignature(MethodInfo method, int param)
   {
      super(method.getName(), convertParameterTypes(method.getParameterTypes()));
      this.param = param;
      checkParam();
   }

   /**
    * Get the param.
    * 
    * @return the param.
    */
   public int getParam()
   {
      return param;
   }

   /**
    * Check the param number makes sense
    */
   protected void checkParam()
   {
      if (param < 0 || param >= getParameters().length)
         throw new IllegalArgumentException("param must be between 0 and " + getParameters().length);
   }

   protected void internalToString(StringBuilder builder)
   {
      super.internalToString(builder);
      builder.append("#").append(param);
   }
}
