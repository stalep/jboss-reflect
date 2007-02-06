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

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.javabean.plugins.xml.Common.Holder;
import org.jboss.javabean.plugins.xml.Common.Property;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultElementHandler;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultElementInterceptor;
import org.jboss.xb.binding.sunday.unmarshalling.ElementBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingInitializer;
import org.jboss.xb.binding.sunday.unmarshalling.TypeBinding;
import org.xml.sax.Attributes;

/**
 * JavaBeanSchemaInitializer.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class JavaBeanSchemaInitializer implements SchemaBindingInitializer
{
   /** The namespace */
   private static final String JAVABEAN_NS = "urn:jboss:javabean:1.0";

   /** The javabean binding */
   private static final QName javabeanTypeQName = new QName(JAVABEAN_NS, "javabeanType");

   /** The property binding */
   private static final QName propertyTypeQName = new QName(JAVABEAN_NS, "propertyType");

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
      beanType.setHandler(new DefaultElementHandler()
      {
         
         public Object startElement(Object parent, QName name, ElementBinding element)
         {
            return new Holder();
         }

         public void attributes(Object o, QName elementName, ElementBinding element, Attributes attrs, NamespaceContext nsCtx)
         {
            Holder holder = (Holder) o;
            String className = null;
            for (int i = 0; i < attrs.getLength(); ++i)
            {
               String localName = attrs.getLocalName(i);
               if ("class".equals(localName))
                  className = attrs.getValue(i);
            }
            
            if (className == null)
               throw new IllegalArgumentException("No class attribute for " + elementName);
            
            try
            {
               BeanInfo beanInfo = ConfigurationUtil.getBeanInfo(className);
               Object object = beanInfo.newInstance();
               holder.setValue(object);
            }
            catch (RuntimeException e)
            {
               throw e;
            }
            catch (Error e)
            {
               throw e;
            }
            catch (Throwable t)
            {
               throw new RuntimeException("Error instantiating class " + className, t);
            }
         }

         public Object endElement(Object o, QName qName, ElementBinding element)
         {
            Holder holder = (Holder) o;
            return holder.getValue();
         }
      });

      // bean has properties
      beanType.pushInterceptor(propertyQName, new DefaultElementInterceptor()
      {
         public void add(Object parent, Object child, QName name)
         {
            Holder holder = (Holder) parent;
            Object parentValue = holder.getValue();
            
            Property prop = (Property) child;
            String property = prop.getProperty();
            Object value = prop.getValue();
            try
            {
               BeanInfo info = ConfigurationUtil.getBeanInfo(parentValue.getClass());
               value = ConfigurationUtil.convertValue(parentValue, property, prop.getType(), value);
               info.setProperty(parentValue, property, value);
            }
            catch (RuntimeException e)
            {
               throw e;
            }
            catch (Error e)
            {
               throw e;
            }
            catch (Throwable t)
            {
               throw new RuntimeException("Error setting property " + property + " on object" + parentValue + " with value " + value, t);
            }
         }
      });

      // property binding
      TypeBinding propertyType = schema.getType(propertyTypeQName);
      propertyType.setHandler(new DefaultElementHandler()
      {
         
         public Object startElement(Object parent, QName name, ElementBinding element)
         {
            return new Property();
         }

         public void attributes(Object o, QName elementName, ElementBinding element, Attributes attrs, NamespaceContext nsCtx)
         {
            Property property = (Property) o;
            for (int i = 0; i < attrs.getLength(); ++i)
            {
               String localName = attrs.getLocalName(i);
               if ("name".equals(localName))
                  property.setProperty(attrs.getValue(i));
               else if ("class".equals(localName))
                  property.setType(attrs.getValue(i));
            }
         }
      });

      return schema;
   }
   
}
