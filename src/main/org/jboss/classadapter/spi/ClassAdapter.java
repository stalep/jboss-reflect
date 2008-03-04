/*
* JBoss, Home of Professional Open Source
* Copyright 2005, JBoss Inc., and individual contributors as indicated
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* This is free software; you can redistribute it and/or modify it
* under the terms of the GNU Lesser General Public License as
* published by the Free Software Foundation; either version 2.1 of
* the License, or (at your option) any later version.
*
* This software is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* Lesser General Public License for more details.
*
* You should have received a copy of the GNU Lesser General Public
* License along with this software; if not, write to the Free
* Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA, or see the FSF site: http://www.fsf.org.
*/
package org.jboss.classadapter.spi;

import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.util.JBossInterface;

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
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public interface ClassAdapter extends JBossInterface
{
   /**
    * Get the class info.
    * 
    * @return the class info
    */
   ClassInfo getClassInfo();

   /**
    * Get the Joinpoint Factory for this adapter.
    * 
    * @return the joinpoint factory
    */
   JoinpointFactory getJoinpointFactory();
   
   /**
    * Get the classloader associated with this class adapter
    * 
    * @return the classloader
    */
   ClassLoader getClassLoader();
}
