/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test.beaninfo.introspection.support;

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