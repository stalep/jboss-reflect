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
package org.jboss.reflect.spi;

/**
 * A MutableMethod Info.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface MutableMethodInfo extends MethodInfo
{

   /**
    * Set modifier
    * If not set it will default to public (non-static)
    * 
    * @param mi
    */
   void setModifier(ModifierInfo mi);

   /**
    * Set type of the returned value
    * 
    * @param returnType
    */
   void setReturnType(String returnType);
   
   /**
    * Set type of the returned value
    * 
    * @param returnType
    */
   void setReturnType(ClassInfo returnType);
   
   /**
    * Method name
    * 
    * @param name
    */
   void setName(String name);
   
   /**
    * The source text of the method body
    * 
    * @param body
    */
   void setBody(Body body);
   
   /**
    * A list of the parameter types
    * 
    * @param parameters
    */
   void setParameters(String[] parameters);
   
   /**
    * A list of the parameter types
    * 
    * @param parameters
    */
   void setParameters(ClassInfo[] parameters);
   
   /**
    * A list of the exception types
    * 
    * @param exceptions
    */
   void setExceptions(String[] exceptions);
   
   /**
    * A list of the exception types
    * 
    * @param exceptions
    */
   void setExceptions(ClassInfo[] exceptions);
  
   /**
    * TODO: something similar to CtBehavior.instrument(...)
    * 
    * @param mmc
    */
   void executeCommand(MutableMethodInfoCommand mmc);
}
