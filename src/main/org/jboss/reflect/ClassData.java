/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public interface ClassData extends AnnotatedData, TypeData
{
   String getName();

   ClassData getSuperclass();

   int getModifiers();

   InterfaceData[] getInterfaces();

   MethodData[] getMethods();
   MethodData[] getDeclaredMethods();

   FieldData[] getFields();
   FieldData[] getDeclaredFields();
   FieldData getField(String name);

   ConstructorData[] getConstructors();
   ConstructorData[] getDeclaredConstructors();



}
