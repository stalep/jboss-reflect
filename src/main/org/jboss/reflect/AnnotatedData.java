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
public interface AnnotatedData
{
   AnnotationData[] getAnnotations();
   AnnotationData[] getDeclaredAnnotations();
   AnnotationData getAnnotation(String name);
   boolean isAnnotationPresent(String name);
}
