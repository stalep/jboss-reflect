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
package org.jboss.metadata.spi.context;

import java.util.List;

import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;

/**
 * MetaDataContext.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public interface MetaDataContext extends MetaDataRetrieval
{
   /**
    * Get the parent
    */
   MetaDataContext getParent();
   
   /**
    * Get the retrievals
    * 
    * @return the retrievals
    */
   List<MetaDataRetrieval> getRetrievals();

   /**
    * Get the local retrievals
    * 
    * @return the local retrievals
    */
   List<MetaDataRetrieval> getLocalRetrievals();
   
   /**
    * Append a meta data retrieval
    * 
    * @param retrieval the meta data retrieval
    */
   void append(MetaDataRetrieval retrieval);

   /**
    * Prepend a meta data retrieval
    * 
    * @param retrieval the meta data retrieval
    */
   void prepend(MetaDataRetrieval retrieval);

   /**
    * Remove a meta data retrieval
    * 
    * @param retrieval the meta data retrieval
    */
   void remove(MetaDataRetrieval retrieval);
}
