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
package org.jboss.test.xb.builder;

import java.util.HashMap;

import org.jboss.logging.Logger;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
import org.w3c.dom.ls.LSInput;

/**
 * TestSchemaResolver.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class TestSchemaResolver implements SchemaBindingResolver
{
   private static final Logger log = Logger.getLogger(TestSchemaResolver.class);

   private HashMap<String, SchemaBinding> bindings = new HashMap<String, SchemaBinding>();

   public String getBaseURI()
   {
      return null;
   }

   public void addSchemaBinding(SchemaBinding schemaBinding)
   {
      schemaBinding.setSchemaResolver(this);
      String nsURI = (String) schemaBinding.getNamespaces().iterator().next();
      bindings.put(nsURI, schemaBinding);
      if (log.isTraceEnabled())
      {
         String schema = SchemaPrinter.printSchema(schemaBinding);
         log.trace("Bound: " + nsURI + "\n" + schema);
      }
   }

   public SchemaBinding resolve(String nsUri, String baseURI, String schemaLocation)
   {
      SchemaBinding result = bindings.get(nsUri);
      if (result == null)
         throw new RuntimeException("Schema not bound: " + nsUri + " available: " + bindings.keySet());
      return result;
   }

   public LSInput resolveAsLSInput(String nsUri, String baseUri, String schemaLocation)
   {
      throw new UnsupportedOperationException();
   }

   public void setBaseURI(String baseURI)
   {
      throw new org.jboss.util.NotImplementedException("setBaseURI");
   }
}
