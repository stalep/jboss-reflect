/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classadapter.spi;

import java.util.List;

import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.reflect.spi.ClassInfo;

/**
 * A class adapter.<p>
 * 
 * A class adapter is the integration point for manipulating
 * class information at runtime, e.g. overriding annotations
 * or obtaining an aop instance advisor.<p>
 * 
 * The class adapter has the following protocol.
 * 
 * <ol>
 * <li> Use getClassInfo to obtain information about
 *        the class.
 * <li> Obtain an Instance ClassAdapter if the class information
 *        should be overridden at the instance level, e.g. annotations
 * <li> Obtain the dependencies of the Class/Instance and any
 *       advice factories, e.g. @Depends annotations
 * <li> Obtain the JoinpointFactory so the instance can be
 *       constructed.
 * </ol>
 *
 * FIXME: This class deals with too many concerns
 * FIXME: Need a mechanism to allow different use cases
 *              for the unadvised case.
 *              i.e. whether in the absence of aop requirements 
 *              the adapter should give a proxy advisor,
 *              instrument the class for aop anyway
 *              or default back to reflection (most likely!)
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ClassAdapter
{
   // Constants -----------------------------------------------------

   // Public --------------------------------------------------------

   /**
    * Get the class info.
    * 
    * @return the class info
    */
   ClassInfo getClassInfo();

   /**
    * Get an instance adapter.
    * 
    * @param classInfo the changed class info
    * @return instance adapter
    */
   ClassAdapter getInstanceAdapter(ClassInfo classInfo);

   /**
    * Get the dependencies of this adapter
    * 
    * @return the dependencies List<Object names>
    */
   List getDependencies();

   /**
    * Get the Joinpoint Factory for this adapter.
    * 
    * @return the joinpoint factory
    */
   JoinpointFactory getJoinpointFactory();
   
   // Inner classes -------------------------------------------------
}
