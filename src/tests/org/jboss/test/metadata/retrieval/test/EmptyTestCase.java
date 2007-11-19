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
package org.jboss.test.metadata.retrieval.test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

import org.jboss.metadata.plugins.context.AbstractMetaDataContext;
import org.jboss.metadata.plugins.context.CachingMetaDataContext;
import org.jboss.metadata.plugins.loader.SimpleMetaDataLoader;
import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.loader.reflection.AnnotatedElementMetaDataLoader;
import org.jboss.metadata.plugins.loader.thread.ThreadLocalMetaDataLoader;
import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.test.metadata.AbstractMetaDataTest;
import org.jboss.test.metadata.retrieval.support.TestAnnotated;
import org.jboss.test.metadata.retrieval.support.TestAnnotation;

/**
 * EmptyTestCase.
 * Tests isEmpty and scope.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class EmptyTestCase extends AbstractMetaDataTest
{
   public EmptyTestCase(String name)
   {
      super(name);
   }

   protected MetaDataRetrieval[] getSimpleMetaDataRetrievals()
   {
      MetaDataRetrieval[] retrievals = new MetaDataRetrieval[4];
      retrievals[0] = new AnnotatedElementMetaDataLoader(getClass());
      retrievals[1] = new MemoryMetaDataLoader();
      retrievals[2] = new SimpleMetaDataLoader(new Annotation[]{});
      retrievals[3] = ThreadLocalMetaDataLoader.INSTANCE;
      return retrievals;
   }

   protected MetaDataContext[] getMetaDataContexts(
         MetaDataRetrieval[] retrievals,
         MetaDataContext emptyContext,
         MetaDataRetrieval emptyRetrieval)
   {
      boolean mix = emptyContext != null && emptyRetrieval != null;
      int size = mix ? 10 : 6;
      MetaDataContext[] contexts = new MetaDataContext[size];
      contexts[0] = new AbstractMetaDataContext(retrievals[0]);
      contexts[1] = new AbstractMetaDataContext(contexts[0], retrievals[1]);
      contexts[2] = new AbstractMetaDataContext(contexts[0], Arrays.asList(retrievals));
      contexts[3] = new CachingMetaDataContext(contexts[0]);
      contexts[4] = new CachingMetaDataContext(contexts[0], retrievals[1]);
      contexts[5] = new CachingMetaDataContext(contexts[0], Arrays.asList(retrievals));
      if (mix)
      {
         contexts[6] = new AbstractMetaDataContext(emptyContext, retrievals[0]);
         contexts[7] = new AbstractMetaDataContext(contexts[0], emptyRetrieval);
         contexts[8] = new CachingMetaDataContext(emptyContext, retrievals[0]);
         contexts[9] = new CachingMetaDataContext(contexts[0], emptyRetrieval);
      }
      return contexts;
   }

   protected TestAnnotation getAnnotation()
   {
      return new TestAnnotation()
      {
         public Class<? extends Annotation> annotationType()
         {
            return TestAnnotation.class;
         }
      };
   }

   public void testEmpty() throws Exception
   {
      MetaDataRetrieval[] retrievals = getSimpleMetaDataRetrievals();
      for(MetaDataRetrieval rdr : retrievals)
         assertTrue(rdr.toString(), rdr.isEmpty());

      MetaDataContext[] contexts = getMetaDataContexts(retrievals, null, null);
      for(MetaDataRetrieval rdr : contexts)
         assertTrue(rdr.toString(), rdr.isEmpty());

      retrievals[0] = new AnnotatedElementMetaDataLoader(TestAnnotated.class);
      MemoryMetaDataLoader mmdl = new MemoryMetaDataLoader();
      TestAnnotation annotation = getAnnotation();
      mmdl.addMetaData(annotation, TestAnnotation.class);
      retrievals[1] = mmdl;
      retrievals[2] = new SimpleMetaDataLoader(new Annotation[]{annotation});
      ThreadLocalMetaDataLoader tlmdl = ThreadLocalMetaDataLoader.INSTANCE;
      tlmdl.addAnnotation(annotation);
      retrievals[3] = tlmdl;
      for(MetaDataRetrieval rdr : retrievals)
         assertFalse(rdr.toString(), rdr.isEmpty());

      mmdl.removeMetaData(TestAnnotation.class);
      assertTrue(mmdl.toString(), mmdl.isEmpty());
      tlmdl.removeAnnotation(TestAnnotation.class);
      assertTrue(tlmdl.toString(), tlmdl.isEmpty());      

      MetaDataRetrieval emptyRetrieval = new MemoryMetaDataLoader();
      MetaDataContext emptyContext = new AbstractMetaDataContext(emptyRetrieval);

      contexts = getMetaDataContexts(retrievals, emptyContext, emptyRetrieval);
      for(MetaDataRetrieval rdr : contexts)
         assertFalse(rdr.toString(), rdr.isEmpty());
   }

   public void testScopedRetrieval() throws Exception
   {
      ScopeLevel[] commonLevels = new ScopeLevel[]
      {
         CommonLevels.DOMAIN,
         CommonLevels.CLUSTER,
         CommonLevels.MACHINE,
         CommonLevels.NODE,
         CommonLevels.JVM,
         CommonLevels.SERVER,
         CommonLevels.SUBSYSTEM,
         CommonLevels.APPLICATION,
         CommonLevels.DEPLOYMENT,
         CommonLevels.CLASS,
         CommonLevels.INSTANCE,
         CommonLevels.JOINPOINT,
         CommonLevels.JOINPOINT_OVERRIDE,
         CommonLevels.THREAD,
         CommonLevels.WORK,
         CommonLevels.REQUEST,
      };

      MetaDataRetrieval[] retrievals = getSimpleMetaDataRetrievals();
      for(ScopeLevel level : commonLevels)
      {
         for(MetaDataRetrieval rdr : retrievals)
         {
            MetaDataRetrieval sr = rdr.getScopedRetrieval(level);
            if (sr != null)
               assertSame(sr, rdr);
         }
      }

      MetaDataRetrieval aemdl = new AnnotatedElementMetaDataLoader(TestAnnotated.class);
      assertNotNull(aemdl.getScopedRetrieval(CommonLevels.CLASS));
      assertNull(aemdl.getScopedRetrieval(CommonLevels.INSTANCE));

      for(ScopeLevel level : commonLevels)
      {
         MemoryMetaDataLoader mmdl = new MemoryMetaDataLoader(new ScopeKey(level, "123"));
         for(ScopeLevel sl : commonLevels)
         {
            if (sl != level)
            {
               assertNull(mmdl.getScopedRetrieval(sl));
            }
         }
      }

      MetaDataContext parent = new AbstractMetaDataContext(retrievals[0]);
      MetaDataContext context = new AbstractMetaDataContext(parent, aemdl);
      assertSame(aemdl, context.getScopedRetrieval(CommonLevels.CLASS));

      MetaDataRetrieval mmdl = new MemoryMetaDataLoader(new ScopeKey(CommonLevels.INSTANCE, "123"));

      context = new AbstractMetaDataContext(parent, mmdl);
      assertSame(mmdl, context.getScopedRetrieval(CommonLevels.INSTANCE));

      context = new AbstractMetaDataContext(parent, Arrays.asList(aemdl, mmdl));
      assertNotNull(context.getScopedRetrieval(CommonLevels.CLASS));
      assertNotNull(context.getScopedRetrieval(CommonLevels.INSTANCE));

      context = new CachingMetaDataContext(parent, Arrays.asList(aemdl, mmdl, aemdl, mmdl));
      assertNotNull(context.getScopedRetrieval(CommonLevels.CLASS));
      assertNotNull(context.getScopedRetrieval(CommonLevels.INSTANCE));

      MetaDataRetrieval expected1 = context.getScopedRetrieval(CommonLevels.CLASS);
      assertSame(expected1, context.getScopedRetrieval(CommonLevels.CLASS));
      MetaDataRetrieval expected2 = context.getScopedRetrieval(CommonLevels.INSTANCE);
      assertSame(expected2, context.getScopedRetrieval(CommonLevels.INSTANCE));

      context.append(ThreadLocalMetaDataLoader.INSTANCE);
      context.remove(ThreadLocalMetaDataLoader.INSTANCE);

      assertNotNull(context.getScopedRetrieval(CommonLevels.CLASS));
      assertNotNull(context.getScopedRetrieval(CommonLevels.INSTANCE));
      assertNotSame(expected1, context.getScopedRetrieval(CommonLevels.CLASS));
      assertNotSame(expected2, context.getScopedRetrieval(CommonLevels.INSTANCE));

      context = new AbstractMetaDataContext(parent, Arrays.asList(mmdl, mmdl));
      assertNull(context.getScopedRetrieval(CommonLevels.CLASS));
      context = new AbstractMetaDataContext(parent, Arrays.asList(aemdl, aemdl));
      assertNull(context.getScopedRetrieval(CommonLevels.INSTANCE));

      parent = new AbstractMetaDataContext(new MemoryMetaDataLoader(new ScopeKey(CommonLevels.INSTANCE, "0123")));
      context = new CachingMetaDataContext(parent, Arrays.asList(mmdl, mmdl));
      assertNull(context.getScopedRetrieval(CommonLevels.CLASS));
      MetaDataRetrieval scr1 = context.getScopedRetrieval(CommonLevels.INSTANCE);
      assertNotNull(scr1);
      assertTrue(scr1.isEmpty());

      parent = new AbstractMetaDataContext(new MemoryMetaDataLoader(new ScopeKey(CommonLevels.CLASS, "0123")));
      context = new CachingMetaDataContext(parent, Arrays.asList(aemdl, aemdl));
      assertNull(context.getScopedRetrieval(CommonLevels.INSTANCE));
      MetaDataRetrieval scr2 = context.getScopedRetrieval(CommonLevels.CLASS);
      assertNotNull(scr2);
      assertFalse(scr2.isEmpty());
   }
}
