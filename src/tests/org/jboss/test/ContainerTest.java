/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test;

/**
 * A ContainerTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ContainerTest extends AbstractTestCaseWithSetup
{
   /**
    * Create a new ContainerTest.
    * 
    * @param name the test name
    */
   public ContainerTest(String name)
   {
      super(name);
   }
   
   /**
    * Default setup with security manager enabled
    * 
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class clazz) throws Exception
   {
      AbstractTestDelegate delegate = new AbstractTestDelegate(clazz);
      delegate.enableSecurity = true;
      return delegate;
   }
}
