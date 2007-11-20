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
package org.jboss.test.metadata.loader.reflection.test;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Collection;

import org.jboss.metadata.plugins.loader.reflection.AnnotatedElementMetaDataLoader;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.loader.reflection.support.TestAnnotationScopeBean;

/**
 * AnnotatedElementLoaderScopeUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AnnotatedElementLoaderScopeUnitTestCase extends AbstractMetaDataTest
{
   public AnnotatedElementLoaderScopeUnitTestCase(String name)
   {
      super(name);
   }

   public void testClassScope() throws Exception
   {
      AnnotatedElementMetaDataLoader loader = new AnnotatedElementMetaDataLoader(TestAnnotationScopeBean.class);
      ScopeKey scopeKey = loader.getScope();
      
      assertNotNull(scopeKey);
      
      Collection<Scope> scopes = scopeKey.getScopes();
      assertEquals(1, scopes.size());
      
      Scope scope = scopes.iterator().next();
      
      assertEquals(CommonLevels.CLASS, scope.getScopeLevel());
      assertEquals(TestAnnotationScopeBean.class, scope.getQualifier());
   }
   
   public void testFieldScope() throws Exception
   {
      Field field = TestAnnotationScopeBean.class.getField("something");
      testMember(field);
   }

   public void testConstructorScope() throws Exception
   {
      Constructor<TestAnnotationScopeBean> constructor = TestAnnotationScopeBean.class.getConstructor(null);
      testMember(constructor);
   }

   public void testMethodScope() throws Exception
   {
      Method method = TestAnnotationScopeBean.class.getMethod("doSomething", null);
      testMember(method);
   }

   protected void testMember(Member member) throws Exception
   {
      assertNotNull(member);
      
      AnnotatedElementMetaDataLoader loader = new AnnotatedElementMetaDataLoader((AnnotatedElement) member);
      ScopeKey scopeKey = loader.getScope();
      assertNotNull(scopeKey);
      
      Collection<Scope> scopes = scopeKey.getScopes();
      assertEquals(1, scopes.size());
      
      Scope scope = scopes.iterator().next();
      
      assertEquals(CommonLevels.JOINPOINT, scope.getScopeLevel());
      assertEquals(member, scope.getQualifier());
   }
}
