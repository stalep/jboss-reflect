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
package org.jboss.test.metadata.scope.test;

import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * ScopeUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ScopeUnitTestCase extends AbstractMetaDataTest
{
   private static ScopeLevel testLevel1 = new ScopeLevel(1, "TEST");
   private static ScopeLevel testLevel2 = new ScopeLevel(2, "TEST");
   
   public ScopeUnitTestCase(String name)
   {
      super(name);
   }

   public void testBasicScope() throws Exception
   {
      Scope test = new Scope(testLevel1, "localhost");
      assertEquals(testLevel1, test.getScopeLevel());
      assertEquals("localhost", test.getQualifier());
   }

   public void testScopeEquals() throws Exception
   {
      Scope test1 = new Scope(testLevel1, "localhost");
      Scope test2 = new Scope(testLevel1, "localhost");
      assertEquals(test1, test2);
   }

   public void testScopeNotEquals() throws Exception
   {
      Scope test1 = new Scope(testLevel1, "localhost");
      Scope test2 = new Scope(testLevel1, "remotehost");
      assertFalse(test1.equals(test2));
      
      Scope test3 = new Scope(testLevel2, "localhost");
      assertFalse(test1.equals(test3));
   }

   public void testScopeSerialization() throws Exception
   {
      Scope test1 = new Scope(testLevel1, "localhost");

      byte[] bytes = serialize(test1);
      Scope test2 = (Scope) deserialize(bytes);

      assertEquals(testLevel1, test2.getScopeLevel());
      assertEquals("localhost", test2.getQualifier());
      
      assertEquals(test1, test2);
   }
}
