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
public interface MethodInfo extends AnnotatedInfo
{
   String getName();

   TypeInfo[] getParameterTypes();

   ClassInfo[] getExceptionTypes();

   TypeInfo getReturnType();

   int getModifiers();

   ClassInfo getDeclaringClass();
}
