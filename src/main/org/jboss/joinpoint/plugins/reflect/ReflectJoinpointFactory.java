/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint.plugins.reflect;

import org.jboss.joinpoint.spi.ConstructorJoinpoint;
import org.jboss.joinpoint.spi.FieldGetJoinpoint;
import org.jboss.joinpoint.spi.FieldSetJoinpoint;
import org.jboss.joinpoint.spi.JoinpointException;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.joinpoint.spi.MethodJoinpoint;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.ConstructorInfo;
import org.jboss.reflect.spi.FieldInfo;
import org.jboss.reflect.spi.MethodInfo;

/**
 * A join point factory based on reflection
 *
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectJoinpointFactory implements JoinpointFactory
{
   // Constants -----------------------------------------------------
   
   // Attributes ----------------------------------------------------

   /** The class info */
   protected ClassInfo classInfo;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   public ReflectJoinpointFactory(ClassInfo classInfo)
   {
      this.classInfo = classInfo;
   }
   
   // Public --------------------------------------------------------
   
   // JoinpointFactory implementation -------------------------------
   
   public ClassInfo getClassInfo()
   {
      return classInfo;
   }

   public ConstructorJoinpoint getConstructorJoinpoint(ConstructorInfo constructorInfo) throws JoinpointException
   {
      return new ReflectConstructorJoinPoint(constructorInfo);
   }

   public FieldGetJoinpoint getFieldGetJoinpoint(FieldInfo fieldInfo) throws JoinpointException
   {
      return new ReflectFieldGetJoinPoint(fieldInfo);
   }

   public FieldSetJoinpoint getFieldSetJoinpoint(FieldInfo fieldInfo) throws JoinpointException
   {
      return new ReflectFieldSetJoinPoint(fieldInfo);
   }

   public MethodJoinpoint getMethodJoinpoint(MethodInfo methodInfo) throws JoinpointException
   {
      return new ReflectMethodJoinPoint(methodInfo);
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
