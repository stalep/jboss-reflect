/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.container.support;

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

   private static final long serialVersionUID = 3257004371450277943L;

   public static final Object PUBLIC_CONSTANT = new Object();

   static final Object PACKAGE_PRIVATE_CONSTANT = new Object();

   protected static final Object PROTECTED_CONSTANT = new Object();
   
   private static final Object PRIVATE_CONSTANT = new Object();

   // Attributes ----------------------------------------------------

   private String a;
   
   public boolean invokedMethodWithNoReturnTypeNoParameters = false;

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
      bean.setPrivate(bean.getPrivate());
      bean.privateMethod();
      privateStaticMethod();
      setPrivateStatic(getPrivateStatic());
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
   
   public static String getPublicStatic()
   {
      return null;
   }
   
   public static void setPublicStatic(String s)
   {
   }
   
   static String getPackagePrivateStatic()
   {
      return null;
   }
   
   static void setPackagePrivateStatic(String s)
   {
   }
   
   protected static String getProtectedStatic()
   {
      return null;
   }
   
   protected static void setProtectedStatic(String s)
   {
   }
   
   private static String getPrivateStatic()
   {
      return null;
   }
   
   private static void setPrivateStatic(String s)
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
   
   public String getA()
   {
      return a;
   }

   public void setA(String s)
   {
      a = s;
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
      invokedMethodWithNoReturnTypeNoParameters = true;
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
   
   String getPackagePrivate()
   {
      return null;
   }
   
   void setPackagePrivate(String s)
   {
   }
   
   // Protected -----------------------------------------------------

   protected void protectedMethod()
   {
   }
   
   protected String getProtected()
   {
      return null;
   }
   
   protected void setProtected(String s)
   {
   }
   
   // Private -------------------------------------------------------

   private void privateMethod()
   {
   }
   
   private String getPrivate()
   {
      return null;
   }
   
   private void setPrivate(String s)
   {
   }
   
   // Inner classes -------------------------------------------------
}