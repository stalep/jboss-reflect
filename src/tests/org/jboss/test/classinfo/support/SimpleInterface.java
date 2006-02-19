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
package org.jboss.test.classinfo.support;

import java.net.URL;

/**
 * A simple interface
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface SimpleInterface
{
   // Constants -----------------------------------------------------

   static final Object A_CONSTANT = new Object();

   // Public --------------------------------------------------------
   
   String getA();
   
   void setA(String s);
   
   String getWithSetter();

   void setWithSetter(String s);

   String getWithoutSetter();

   void setWithoutGetter(String s);

   String getDoesNotMatchSetter();

   void setDoesNotMatchGetter(Object o);

   String getWithNoSetterOnInterface();

   void setWithNoGetterOnInterface(Object o);
   
   boolean isB();
   
   void setB(boolean b);
   
   boolean isPrimitiveIS();

   Boolean isBooleanIS();

   void methodWithNoReturnTypeNoParameters();

   void methodWithNoReturnTypeOneParameter(String s);   

   void methodWithNoReturnTypeTwoParameters(String s, URL u);   

   Object methodWithReturnTypeNoParameters();

   String methodWithReturnTypeOneParameter(String s);   

   URL methodWithReturnTypeTwoParameters(String s, URL u);   

   void methodWithPrimitiveParameter(int i);   

   int methodWithPrimitiveReturnType();   

   void overloadedMethod(Object o);   

   void overloadedMethod(String s);
   
   boolean is();
   
   Object get();
   
   void set(Object o);
   
   // Inner classes -------------------------------------------------

}