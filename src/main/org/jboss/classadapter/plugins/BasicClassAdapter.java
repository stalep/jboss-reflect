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
package org.jboss.classadapter.plugins;

import java.util.List;

import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.classadapter.spi.ClassAdapterFactory;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.joinpoint.spi.JoinpointFactoryBuilder;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.repository.spi.MetaDataContext;
import org.jboss.repository.spi.MetaDataContextFactory;
import org.jboss.util.JBossObject;

/**
 * A class adapter.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class BasicClassAdapter extends JBossObject implements ClassAdapter
{
   /** The class adapter factory */
   protected ClassAdapterFactory classAdapterFactory;
   
   /** The class info */
   protected ClassInfo classInfo;

   /** The metadata context */
   protected MetaDataContext metaDataContext;
   
   /**
    * Create a new reflected class adapter
    * 
    * @param factory class adapter factory
    * @param classInfo class info
    */
   public BasicClassAdapter(ClassAdapterFactory factory, ClassInfo classInfo)
   {
      this.classAdapterFactory = factory;
      this.classInfo = classInfo;
   }

   public ClassInfo getClassInfo()
   {
      return classInfo;
   }

   public ClassAdapter getInstanceAdapter(ClassInfo classInfo)
   {
      BasicClassAdapter clone = (BasicClassAdapter) clone();
      clone.classInfo = classInfo;
      return clone;
   }

   public List getDependencies()
   {
      return null;
   }

   public JoinpointFactory getJoinpointFactory()
   {
      JoinpointFactoryBuilder builder = classAdapterFactory.getConfiguration().getJoinpointFactoryBuilder();
      return builder.createJoinpointFactory(classInfo, metaDataContext);
   }

   public ClassLoader getClassLoader()
   {
      return classInfo.getType().getClassLoader();
   }

   public MetaDataContextFactory getMetaDataContextFactory()
   {
      return classAdapterFactory.getConfiguration().getMetaDataContextFactory();
   }


   public MetaDataContext getMetaDataContext()
   {
      return metaDataContext;
   }

   public void setMetaDataContext(MetaDataContext metaCtx)
   {
      metaDataContext = metaCtx;
   }
}
