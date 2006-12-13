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
package org.jboss.metadata.spi;

import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.signature.Signature;

/**
 * ComponentMutableMetaData.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 57133 $
 */
public interface ComponentMutableMetaData
{
   /**
    * Add a component metadata
    * 
    * @param signature the signature
    * @param component the component
    * @return any previous component at that signature
    */
   MetaDataRetrieval addComponentMetaDataRetrieval(Signature signature, MetaDataRetrieval component);

   /**
    * Remove a component metadata
    * 
    * @param signature the signature
    * @return any previous component at that signature
    */
   MetaDataRetrieval removeComponentMetaDataRetrieval(Signature signature);
}
