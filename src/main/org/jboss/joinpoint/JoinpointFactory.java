/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

import org.jboss.joinpoint.ConstructorJoinpoint;
import org.jboss.joinpoint.FieldGetJoinpoint;
import org.jboss.joinpoint.FieldSetJoinpoint;
import org.jboss.joinpoint.JoinpointException;
import org.jboss.joinpoint.MethodJoinpoint;

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
    * Get a constructor join point
    * 
    * @param argumentTypes the argument types
    * @return the constructor join point
    * @throws JoinpointException when no such constructor
    */
   ConstructorJoinpoint getConstructorJoinpoint(Class[] argumentTypes) throws JoinpointException;

   /**
    * Get a field get join point
    * 
    * @param field the field name
    * @return the field get join point
    * @throws JoinpointException when no such field
    */
   FieldGetJoinpoint getFieldGetJoinpoint(String field) throws JoinpointException;

   /**
    * Get a field set join point
    * 
    * @param field the field name
    * @return the field set join point
    * @throws JoinpointException when no such field
    */
   FieldSetJoinpoint getFieldSetJoinpoint(String field) throws JoinpointException;

   /**
    * Get a method join point
    * 
    * @param name the method name
    * @param argumentTypes the argument types
    * @param isStatic whether to only look for static methods
    * @return the method join point
    * @throws JoinpointException when no such method
    */
   MethodJoinpoint getMethodJoinpoint(String name, Class[] argumentTypes, boolean isStatic) throws JoinpointException;
   
   // Inner classes -------------------------------------------------
}
