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
public interface ClassInfo extends AnnotatedInfo, TypeInfo
{
   String getName();

   ClassInfo getSuperclass();

   int getModifiers();

   InterfaceInfo[] getInterfaces();

   MethodInfo[] getMethods();
   MethodInfo[] getDeclaredMethods();

   FieldInfo[] getFields();
   FieldInfo[] getDeclaredFields();
   FieldInfo getField(String name);

   ConstructorInfo[] getConstructors();
   ConstructorInfo[] getDeclaredConstructors();



}
