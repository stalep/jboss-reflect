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
package org.jboss.test.metadata.loader.threadlocal.test;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;

import org.jboss.metadata.plugins.loader.thread.ThreadLocalMetaDataLoader;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * AnnotatedElementLoaderScopeUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class ThreadLocalLoaderScopeUnitTestCase extends AbstractMetaDataTest
{
   ThreadLocalMetaDataLoader loader = ThreadLocalMetaDataLoader.INSTANCE;
   
   CountDownLatch entryLatch;

   public ThreadLocalLoaderScopeUnitTestCase(String name)
   {
      super(name);
   }

   public void testThreadScope() throws Exception
   {
      TestScopeRunnable[] runnables = new TestScopeRunnable[5];
      Thread[] threads = new Thread[runnables.length];
      entryLatch = new CountDownLatch(runnables.length);
      for (int i = 0; i < 5; ++i)
      {
         runnables[i] = new TestScopeRunnable();
         threads[i] = new Thread(runnables[i], "Thread" + i);
         threads[i].start();
      }
      
      for (int i = 0; i< 5; ++i)
      {
         threads[i].join();
         assertNull(runnables[i].error);
      }
   }
   
   public class TestScopeRunnable implements Runnable
   {
      Throwable error;
      
      public void run()
      {
         try
         {
            entryLatch.countDown();
            entryLatch.await();

            ScopeKey scopeKey = loader.getScope();
            assertNotNull(scopeKey);
            
            Collection<Scope> scopes = scopeKey.getScopes();
            assertEquals(1, scopes.size());
            
            Scope scope = scopes.iterator().next();
            
            assertEquals(CommonLevels.THREAD, scope.getScopeLevel());
            assertEquals(Thread.currentThread().getName(), scope.getQualifier());
         }
         catch (Throwable t)
         {
            log.error("Error", t);
            error = t;
         }
      }
   }
}
