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
package org.jboss.test.metadata;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.TimeZone;

import org.jboss.config.plugins.property.PropertyConfiguration;
import org.jboss.config.spi.Configuration;
import org.jboss.metadata.spi.signature.ConstructorSignature;
import org.jboss.metadata.spi.signature.FieldSignature;
import org.jboss.metadata.spi.signature.MethodParametersSignature;
import org.jboss.metadata.spi.signature.MethodSignature;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.test.metadata.shared.support.SignatureTester;

/**
 * SignatureMetaDataTest.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 */
public abstract class SignatureMetaDataTest extends AbstractMetaDataTest
{
   private static final Configuration configuration = new PropertyConfiguration();

   public SignatureMetaDataTest(String name)
   {
      super(name);
   }

   protected ConstructorSignature getStringParameterConstructorSignature()
   {
      return new ConstructorSignature(new String[]{Calendar.class.getName()});
   }

   protected ConstructorSignature getDefaultConstructorSignature()
   {
      return new ConstructorSignature();
   }

   protected ConstructorSignature getClassParameterConstructorSignature() throws Exception
   {
      return new ConstructorSignature(getConstructor());
   }

   protected ConstructorSignature getClassInfoParameterConstructorSignature() throws Exception
   {
      return new ConstructorSignature(getConstructorInfo());
   }

   protected MethodSignature getStringMethodSignature() throws Exception
   {
      return new MethodSignature("applyTimeZone", new String[]{Calendar.class.getName(), TimeZone.class.getName()});
   }

   protected MethodSignature getClassMethodSignature() throws Exception
   {
      return new MethodSignature("applyTimeZone", Calendar.class, TimeZone.class);
   }

   protected MethodSignature getMethodSignature() throws Exception
   {
      return new MethodSignature(getMethod());
   }

   protected MethodSignature getMethodInfoSignature() throws Exception
   {
      return new MethodSignature(getMethodInfo());
   }

   protected FieldSignature getStringFieldSignature()
   {
      return new FieldSignature("calendar");
   }

   protected FieldSignature getFieldSignature() throws Exception
   {
      return new FieldSignature(getField());
   }

   protected FieldSignature getFieldInfoSignature()
   {
      return new FieldSignature(getFieldInfo());
   }

   protected MethodParametersSignature getStringMethodParametersSignature()
   {
      return new MethodParametersSignature("applyTimeZone", new String[]{Calendar.class.getName(), TimeZone.class.getName()}, 0);
   }

   protected MethodParametersSignature getClassMethodParametersSignature() throws Exception
   {
      return new MethodParametersSignature("applyTimeZone", 0, Calendar.class, TimeZone.class);
   }

   protected MethodParametersSignature getMethodParametersSignature() throws Exception
   {
      return new MethodParametersSignature(getMethod(), 0);
   }

   protected MethodParametersSignature getMethodInfoParametersSignature() throws Exception
   {
      return new MethodParametersSignature(getMethodInfo(), 0);
   }

   protected Constructor getConstructor() throws Exception
   {
      return SignatureTester.class.getDeclaredConstructor(Calendar.class);
   }
   
   protected ConstructorInfo getConstructorInfo()
   {
      ClassInfo classInfo = configuration.getClassInfo(SignatureTester.class);
      TypeInfo calendarTypeInfo = configuration.getTypeInfo(Calendar.class);
      return classInfo.getDeclaredConstructor(new TypeInfo[]{calendarTypeInfo});
   }

   protected Method getMethod() throws Exception
   {
      return SignatureTester.class.getDeclaredMethod("applyTimeZone", Calendar.class, TimeZone.class);
   }

   protected MethodInfo getMethodInfo()
   {
      ClassInfo classInfo = configuration.getClassInfo(SignatureTester.class);
      TypeInfo calendarTypeInfo = configuration.getTypeInfo(Calendar.class);
      TypeInfo timeZoneTypeInfo = configuration.getTypeInfo(TimeZone.class);
      return classInfo.getDeclaredMethod("applyTimeZone", new TypeInfo[]{calendarTypeInfo, timeZoneTypeInfo});
   }

   protected Field getField() throws Exception
   {
      return SignatureTester.class.getDeclaredField("calendar");
   }

   protected FieldInfo getFieldInfo()
   {
      ClassInfo classInfo = configuration.getClassInfo(SignatureTester.class);
      return classInfo.getDeclaredField("calendar");
   }

   protected Signature[] getSignatures() throws Exception
   {
      Signature[] signatures = new Signature[15];
      signatures[0] = getDefaultConstructorSignature();
      signatures[1] = getStringParameterConstructorSignature();
      signatures[2] = getClassParameterConstructorSignature();
      signatures[3] = getClassInfoParameterConstructorSignature();
      signatures[4] = getStringMethodSignature();
      signatures[5] = getClassMethodSignature();
      signatures[6] = getMethodSignature();
      signatures[7] = getMethodInfoSignature();
      signatures[8] = getStringFieldSignature();
      signatures[9] = getFieldSignature();
      signatures[10] = getFieldInfoSignature();
      signatures[11] = getClassMethodParametersSignature();
      signatures[12] = getStringMethodParametersSignature();
      signatures[13] = getMethodParametersSignature();
      signatures[14] = getMethodInfoParametersSignature();
      return signatures;
   }

   protected ConstructorSignature[] getConstructorSignatures() throws Exception
   {
      ConstructorSignature[] signatures = new ConstructorSignature[4];
      signatures[0] = getDefaultConstructorSignature();
      signatures[1] = getStringParameterConstructorSignature();
      signatures[2] = getClassParameterConstructorSignature();
      signatures[3] = getClassInfoParameterConstructorSignature();
      return signatures;
   }

   protected MethodSignature[] getMethodSignatures() throws Exception
   {
      MethodSignature[] signatures = new MethodSignature[4];
      signatures[0] = getStringMethodSignature();
      signatures[1] = getClassMethodSignature();
      signatures[2] = getMethodSignature();
      signatures[3] = getMethodInfoSignature();
      return signatures;
   }

   protected FieldSignature[] getFieldSignatures() throws Exception
   {
      FieldSignature[] signatures = new FieldSignature[3];
      signatures[1] = getStringFieldSignature();
      signatures[2] = getFieldSignature();
      signatures[3] = getFieldInfoSignature();
      return signatures;
   }

   protected MethodParametersSignature[] getMethodParametersSignatures() throws Exception
   {
      MethodParametersSignature[] signatures = new MethodParametersSignature[4];
      signatures[0] = getClassMethodParametersSignature();
      signatures[1] = getStringMethodParametersSignature();
      signatures[2] = getMethodParametersSignature();
      signatures[3] = getMethodInfoParametersSignature();
      return signatures;
   }
}
