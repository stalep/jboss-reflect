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

import java.io.Serializable;
import java.net.URL;

/**
 * A simple bean
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class SimpleBean implements Serializable, SimpleInterface
{
   // Constants -----------------------------------------------------

   private static final long serialVersionUID = 3256728398394177849L;

   public static final Object PUBLIC_CONSTANT = new Object();

   static final Object PACKAGE_PRIVATE_CONSTANT = new Object();

   protected static final Object PROTECTED_CONSTANT = new Object();
   
   private static final Object PRIVATE_CONSTANT = new Object();

   // Attributes ----------------------------------------------------

   public Object publicAttribute;
   
   Object packagePrivateAttribute;
   
   protected Object protectedAttribute;
   
   private Object privateAttribute;
   
   // Static --------------------------------------------------------

   public static void publicStaticMethod()
   {
      SimpleBean bean = new SimpleBean(true);
      if (bean.privateAttribute == null)
         bean.privateAttribute = PRIVATE_CONSTANT;
      bean.privateMethod();
      privateStaticMethod();
   }
   
   static void packagePrivateStaticMethod()
   {
   }
   
   protected static void protectedStaticMethod()
   {
   }
   
   private static void privateStaticMethod()
   {
   }
   
   // Constructors --------------------------------------------------

   public SimpleBean()
   {
   }

   public SimpleBean(String s)
   {
   }

   SimpleBean(Object o)
   {
   }

   protected SimpleBean(int i)
   {
   }

   private SimpleBean(boolean b)
   {
   }

   // Public --------------------------------------------------------
   
   // SimpleInterface Implementation --------------------------------
   
   public String getA()
   {
      return null;
   }

   public void setA(String s)
   {
   }

   public String getWithSetter()
   {
      return null;
   }

   // Setter
   public void setWithSetter(String s)
   {
   }

   public String getWithoutSetter()
   {
      return null;
   }

   public void setWithoutGetter(String s)
   {
   }

   public String getDoesNotMatchSetter()
   {
      return null;
   }

   public void setDoesNotMatchGetter(Object o)
   {
   }

   public String getWithNoSetterOnInterface()
   {
      return null;
   }

   public void setWithNoGetterOnInterface(Object o)
   {
   }

   public boolean isB()
   {
      return false;
   }

   public void setB(boolean b)
   {
   }

   public boolean isPrimitiveIS()
   {
      return false;
   }

   public Boolean isBooleanIS()
   {
      return Boolean.FALSE;
   }

   public void methodWithNoReturnTypeNoParameters()
   {
   }

   public void methodWithNoReturnTypeOneParameter(String s)
   {
   }

   public void methodWithNoReturnTypeTwoParameters(String s, URL u)
   {
   }

   public void methodWithPrimitiveParameter(int i)
   {
   }

   public int methodWithPrimitiveReturnType()
   {
      return 0;
   }

   public Object methodWithReturnTypeNoParameters()
   {
      return null;
   }

   public String methodWithReturnTypeOneParameter(String s)
   {
      return null;
   }

   public URL methodWithReturnTypeTwoParameters(String s, URL u)
   {
      return null;
   }

   public void overloadedMethod(Object o)
   {
   }

   public void overloadedMethod(String s)
   {
   }
   
   public Object get()
   {
      return null;
   }
   
   public boolean is()
   {
      return false;
   }
   
   public void set(Object o)
   {
   }
   
   // Package protected ---------------------------------------------

   void packagePrivateMethod()
   {
   }
   
   // Protected -----------------------------------------------------

   protected void protectedMethod()
   {
   }
   
   // Private -------------------------------------------------------

   private void privateMethod()
   {
   }
   
   // Inner classes -------------------------------------------------
}