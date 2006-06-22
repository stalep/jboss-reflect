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
package org.jboss.test.metadata.context;

import java.util.ArrayList;
import java.util.List;

import org.jboss.metadata.spi.MetaData;
import org.jboss.metadata.spi.context.MetaDataContext;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDataRetrievalToMetaDataBridge;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * AbstractMetaDataContextTest.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public abstract class AbstractMetaDataContextTest extends AbstractMetaDataTest
{
   public AbstractMetaDataContextTest(String name)
   {
      super(name);
   }

   protected MetaDataRetrieval firstChild;
   protected MetaDataRetrieval secondChild;
   protected MetaDataRetrieval firstParent;
   protected MetaDataRetrieval secondParent;
   
   /**
    * Create a new context
    * 
    * @param parent the parent
    * @param retrievals the retrievals
    * @return the context
    */
   protected abstract MetaDataContext createContext(MetaDataContext parent, List<MetaDataRetrieval> retrievals);

   /**
    * Create a new parent context
    * 
    * @param retrievals the retrievals
    * @return the context
    */
   protected MetaDataContext createParentContext(List<MetaDataRetrieval> retrievals)
   {
      return createContext(null, retrievals);
   }

   /**
    * Create a new child context
    * 
    * @param parent the parent
    * @param retrievals the retrievals
    * @return the context
    */
   protected MetaDataContext createChildContext(MetaDataContext parent, List<MetaDataRetrieval> retrievals)
   {
     return createContext(parent, retrievals);
   }
   
   /**
    * Create the retrieval
    * 
    * @return the retrieval
    */
   protected abstract MetaDataRetrieval createRetrieval();
   
   /**
    * Create a test context
    * 
    * @return the test context
    */
   protected MetaData createTestContext()
   {
      firstParent = createRetrieval();
      secondParent = createRetrieval();
      List<MetaDataRetrieval> retrievals = new ArrayList<MetaDataRetrieval>();
      retrievals.add(firstParent);
      retrievals.add(secondParent);
      MetaDataContext parentContext = createParentContext(retrievals);

      firstChild = createRetrieval();
      secondChild = createRetrieval();
      retrievals = new ArrayList<MetaDataRetrieval>();
      retrievals.add(firstChild);
      retrievals.add(secondChild);

      MetaDataContext context = createChildContext(parentContext, retrievals);
      return new MetaDataRetrievalToMetaDataBridge(context);
   }
}
