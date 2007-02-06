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
package org.jboss.javabean.plugins.xml;

import javax.xml.namespace.QName;

import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingInitializer;
import org.jboss.xb.binding.sunday.unmarshalling.TypeBinding;

/**
 * JavaBeanSchemaInitializer version 2.0. This extends the
 * urn:jboss:javabean:1.0 schema by adding a constructor element to
 * specify the javabean constructor to use. 
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision: 59176 $
 */
public class JavaBeanSchemaInitializer20 implements SchemaBindingInitializer
{
   /** The namespace */
   private static final String JAVABEAN_NS = "urn:jboss:javabean:2.0";

   /** The javabean binding */
   private static final QName javabeanTypeQName = new QName(JAVABEAN_NS, "javabeanType");

   /** The constructor binding */
   private static final QName constructorTypeQName = new QName(JAVABEAN_NS, "constructorType");

   /** The property binding */
   private static final QName propertyTypeQName = new QName(JAVABEAN_NS, "propertyType");

   /** The constructor element name */
   private static final QName constructorQName = new QName(JAVABEAN_NS, "constructor");

   /** The property element name */
   private static final QName propertyQName = new QName(JAVABEAN_NS, "property");

   static
   {
      ConfigurationUtil.init();
   }

   public SchemaBinding init(SchemaBinding schema)
   {
      // ignore XB property replacement
      schema.setReplacePropertyRefs(false);

      // javabean binding
      TypeBinding beanType = schema.getType(javabeanTypeQName);
      beanType.setHandler(JavaBeanHandler.HANDLER);
      // bean has constructor
      beanType.pushInterceptor(constructorQName, ConstructorInterceptor.INTERCEPTOR);
      // bean has properties
      beanType.pushInterceptor(propertyQName, PropertyInterceptor.INTERCEPTOR);

      // constructor binding
      TypeBinding constructorType = schema.getType(constructorTypeQName);
      constructorType.setHandler(ConstructorHandler.HANDLER);
      // constructor has properties
      constructorType.pushInterceptor(propertyQName, PropertyInterceptor.INTERCEPTOR);

      // property binding
      TypeBinding propertyType = schema.getType(propertyTypeQName);
      propertyType.setHandler(PropertyHandler.HANDLER);

      return schema;
   }
}
