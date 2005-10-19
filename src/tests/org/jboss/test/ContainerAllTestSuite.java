/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.test;

import junit.framework.Test;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

import org.jboss.test.joinpoint.reflect.test.ReflectJoinpointTestSuite;
import org.jboss.test.reflect.ClassInfoTestSuite;

/**
 * All Test Suite.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ContainerAllTestSuite extends TestSuite
{
   public static void main(String[] args)
   {
      TestRunner.run(suite());
   }

   public static Test suite()
   {
      TestSuite suite = new TestSuite("All Tests");

      suite.addTest(ClassInfoTestSuite.suite());
      suite.addTest(ReflectJoinpointTestSuite.suite());
      
      return suite;
   }
}
