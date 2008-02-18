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
package org.jboss.test.metadata.loader.memory.test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;

import org.jboss.metadata.plugins.loader.memory.MemoryMetaDataLoader;
import org.jboss.metadata.plugins.loader.thread.ThreadLocalMetaDataLoader;
import org.jboss.metadata.spi.ComponentMutableMetaData;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.signature.ConstructorSignature;
import org.jboss.metadata.spi.signature.FieldSignature;
import org.jboss.metadata.spi.signature.MethodSignature;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MemberInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.test.metadata.SignatureMetaDataTest;
import org.jboss.test.metadata.shared.support.TestAnnotation;
import org.jboss.test.metadata.shared.support.TestAnnotationImpl;

/**
 * MemoryLoaderComponentMutableUnitTestCase.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public class MemoryLoaderComponentMutableUnitTestCase extends SignatureMetaDataTest
{
   public MemoryLoaderComponentMutableUnitTestCase(String name)
   {
      super(name);
   }

   protected ComponentMutableMetaData[] getArray()
   {
      ComponentMutableMetaData[] array = new ComponentMutableMetaData[10];
      array[0] = new MemoryMetaDataLoader();
      array[1] = new MemoryMetaDataLoader(true, true);
      array[2] = new MemoryMetaDataLoader(true, false);
      array[3] = new MemoryMetaDataLoader(false, true);
      array[4] = new MemoryMetaDataLoader(false, false);
      ScopeKey key = new ScopeKey(CommonLevels.APPLICATION, "SignatureTester");
      array[5] = new MemoryMetaDataLoader(key);
      array[6] = new MemoryMetaDataLoader(key, true, true);
      array[7] = new MemoryMetaDataLoader(key, true, false);
      array[8] = new MemoryMetaDataLoader(key, false, true);
      array[9] = new MemoryMetaDataLoader(key, false, false);
      return array;
   }

   public void testComponentMetaDataRetrieval() throws Exception
   {
      ComponentMutableMetaData[] array = getArray();
      Signature[] signatures = getSignatures();
      MetaDataRetrieval retrieval = ThreadLocalMetaDataLoader.INSTANCE;
      for(ComponentMutableMetaData cmmd : array)
      {
         for(Signature sig : signatures)
         {
            assertNull(cmmd.addComponentMetaDataRetrieval(sig, retrieval));
            MetaDataRetrieval mdr = cmmd.removeComponentMetaDataRetrieval(sig);
            assertSame(retrieval, mdr);
         }
      }
   }

   public void testAnnotations() throws Exception
   {
      ComponentMutableMetaData[] array = getArray();
      Signature[] signatures = getSignatures();
      TestAnnotation annotation = new TestAnnotationImpl();
      for(ComponentMutableMetaData cmmd : array)
      {
         for(Signature sig : signatures)
         {
            assertNull(cmmd.addAnnotation(sig, annotation));
            assertSame(annotation, cmmd.removeAnnotation(sig, TestAnnotation.class));
         }
      }
      Constructor<?> c = getConstructor();
      ConstructorInfo ci = getConstructorInfo();
      Signature sc = new ConstructorSignature(c);
      checkAnnotationCycle(sc, c, ci);
      Method m = getMethod();
      MethodInfo mi = getMethodInfo();
      Signature sm = new MethodSignature(m);
      checkAnnotationCycle(sm, m, mi);
      Field f = getField();
      FieldInfo fi = getFieldInfo();
      Signature sf = new FieldSignature(f);
      checkAnnotationCycle(sf, f, fi);
   }

   public void testMetaData() throws Exception
   {
      ComponentMutableMetaData[] array = getArray();
      Signature[] signatures = getSignatures();
      Object metadata = new Object();
      for(ComponentMutableMetaData cmmd : array)
      {
         for(Signature sig : signatures)
         {
            assertNull(cmmd.addMetaData(sig, metadata, Object.class));
            Object object = cmmd.removeMetaData(sig, Object.class);
            assertSame(metadata, object);
         }
      }
      Constructor<?> c = getConstructor();
      ConstructorInfo ci = getConstructorInfo();
      Signature sc = new ConstructorSignature(c);
      checkMetaDataCycle(sc, c, ci);
      Method m = getMethod();
      MethodInfo mi = getMethodInfo();
      Signature sm = new MethodSignature(m);
      checkMetaDataCycle(sm, m, mi);
      Field f = getField();
      FieldInfo fi = getFieldInfo();
      Signature sf = new FieldSignature(f);
      checkMetaDataCycle(sf, f, fi);
   }

   protected void checkAnnotationCycle(Signature signature, Member member, MemberInfo memberInfo) throws Exception
   {
      TestAnnotation annotation = new TestAnnotationImpl();
      ComponentMutableMetaData[] array = getArray();
      for(ComponentMutableMetaData cmmd : array)
      {
         assertNull(cmmd.addAnnotation(signature, annotation));
         assertSame(annotation, cmmd.removeAnnotation(member, annotation.annotationType()));
         assertNull(cmmd.addAnnotation(memberInfo, annotation));
         assertSame(annotation, cmmd.removeAnnotation(signature, annotation.annotationType()));
      }
   }

   protected void checkMetaDataCycle(Signature signature, Member member, MemberInfo memberInfo) throws Exception
   {
      Object metadata = new Object();
      ComponentMutableMetaData[] array = getArray();
      for(ComponentMutableMetaData cmmd : array)
      {
         assertNull(cmmd.addMetaData(signature, metadata, Object.class));
         assertSame(metadata, cmmd.removeMetaData(member, Object.class));
         assertNull(cmmd.addMetaData(memberInfo, metadata, Object.class));
         assertSame(metadata, cmmd.removeMetaData(signature, Object.class));
      }
   }
}
