/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.spi;

import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * A join point factory.
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface JoinpointFactory
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the class info for this join point factory
    * 
    * @return the class info
    */
   ClassInfo getClassInfo();
   
   /**
    * Get a constructor join point
    * 
    * @param constructorInfo the constructor info
    * @return the constructor join point
    * @throws JoinpointException when no such constructor
    */
   ConstructorJoinpoint getConstructorJoinpoint(ConstructorInfo constructorInfo) throws JoinpointException;

   /**
    * Get a field get join point
    * 
    * @param fieldInfo the field info
    * @return the field get join point
    * @throws JoinpointException when no such field
    */
   FieldGetJoinpoint getFieldGetJoinpoint(FieldInfo fieldInfo) throws JoinpointException;

   /**
    * Get a field set join point
    * 
    * @param fieldInfo the field info
    * @return the field set join point
    * @throws JoinpointException when no such field
    */
   FieldSetJoinpoint getFieldSetJoinpoint(FieldInfo fieldInfo) throws JoinpointException;

   /**
    * Get a method join point
    * 
    * @param methodInfo the method info
    * @return the method join point
    * @throws JoinpointException when no such method
    */
   MethodJoinpoint getMethodJoinpoint(MethodInfo methodInfo) throws JoinpointException;
   
   // Inner classes -------------------------------------------------
}
