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
public class InterfaceInfo extends ClassInfo
{
   public InterfaceInfo()
   {
   }

   public InterfaceInfo(String name, int modifiers, InterfaceInfo[] interfaces, MethodInfo[] methods, FieldInfo[] fields, AnnotationValue[] annotations)
   {
      super(name, modifiers, interfaces, methods, null, fields, null, annotations);
   }
}
