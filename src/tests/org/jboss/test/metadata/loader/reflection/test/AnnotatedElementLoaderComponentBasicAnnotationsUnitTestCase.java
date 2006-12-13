/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.test.metadata.loader.reflection.test;

import org.jboss.metadata.plugins.loader.reflection.AnnotatedElementMetaDataLoader;
import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.test.metadata.loader.reflection.support.ConstructorBean;
import org.jboss.test.metadata.loader.reflection.support.FieldBean;
import org.jboss.test.metadata.loader.reflection.support.MethodBean;
import org.jboss.test.metadata.loader.reflection.support.MethodParamBean;
import org.jboss.test.metadata.shared.ComponentBasicAnnotationsTest;

/**
 * AnnotatedElementLoaderBasicAnnotationsUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 46146 $
 */
public class AnnotatedElementLoaderComponentBasicAnnotationsUnitTestCase extends ComponentBasicAnnotationsTest
{
   public AnnotatedElementLoaderComponentBasicAnnotationsUnitTestCase(String name)
   {
      super(name, true);
   }

   protected MetaData setupField()
   {
      AnnotatedElementMetaDataLoader loader = new AnnotatedElementMetaDataLoader(FieldBean.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupConstructor()
   {
      AnnotatedElementMetaDataLoader loader = new AnnotatedElementMetaDataLoader(ConstructorBean.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupMethod()
   {
      AnnotatedElementMetaDataLoader loader = new AnnotatedElementMetaDataLoader(MethodBean.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }

   protected MetaData setupMethodParams()
   {
      AnnotatedElementMetaDataLoader loader = new AnnotatedElementMetaDataLoader(MethodParamBean.class);
      return new MetaDataRetrievalToMetaDataBridge(loader);
   }
}
