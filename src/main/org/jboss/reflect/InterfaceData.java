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
public interface InterfaceData extends TypeData
{
   String getName();

   int getModifiers();

   InterfaceData[] getInterfaces();

   MethodData[] getDeclaredMethods();
   MethodData[] getMethods();
}
