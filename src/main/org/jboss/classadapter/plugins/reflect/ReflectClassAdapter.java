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
package org.jboss.classadapter.plugins.reflect;

import java.util.List;

import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.joinpoint.plugins.reflect.ReflectJoinpointFactory;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.repository.plugins.basic.BasicMetaDataContextFactory;
import org.jboss.repository.spi.MetaDataContextFactory;
import org.jboss.util.JBossObject;

/**
 * A reflected class adapter.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectClassAdapter extends JBossObject implements ClassAdapter
{
   /** The class info */
   protected ClassInfo classInfo;

   /**
    * Create a new reflected class adapter
    * 
    * @param classInfo the class info
    */
   public ReflectClassAdapter(ClassInfo classInfo)
   {
      this.classInfo = classInfo;
   }

   public ClassInfo getClassInfo()
   {
      return classInfo;
   }

   public ClassAdapter getInstanceAdapter(ClassInfo classInfo)
   {
      ReflectClassAdapter clone = (ReflectClassAdapter) clone();
      clone.classInfo = classInfo;
      return clone;
   }

   public List getDependencies()
   {
      return null;
   }

   public JoinpointFactory getJoinpointFactory()
   {
      return new ReflectJoinpointFactory(classInfo);
   }

   public ClassLoader getClassLoader()
   {
      return classInfo.getType().getClassLoader();
   }

   public MetaDataContextFactory getMetaDataContextFactory()
   {
      return new BasicMetaDataContextFactory();
   }
   
   
}
