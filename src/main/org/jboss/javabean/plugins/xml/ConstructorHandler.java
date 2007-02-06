/*
 * JBoss, Home of Professional Open Source
 * Copyright 2006, Red Hat Middleware LLC, and individual contributors
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

import org.jboss.javabean.plugins.xml.Common.Ctor;
import org.jboss.javabean.plugins.xml.Common.Holder;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultElementHandler;
import org.jboss.xb.binding.sunday.unmarshalling.ElementBinding;
import org.xml.sax.Attributes;

/**
 * Handler for the constructor element.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision: 43020 $
 */
public class ConstructorHandler extends DefaultElementHandler
{
   /** The handler */
   public static final ConstructorHandler HANDLER = new ConstructorHandler();

   public Object startElement(Object parent, QName name, ElementBinding element)
   {
      Holder holder = (Holder) parent;
      Ctor ctor = (Ctor) holder.getValue();
      ctor.setCtorWasDeclared(true);
      return holder;
   }

   public void attributes(Object o, QName elementName, ElementBinding element, Attributes attrs, NamespaceContext nsCtx)
   {
      Holder holder = (Holder) o;
      Ctor ctor = (Ctor) holder.getValue();
      Common.Constructor constructor = ctor.getConstructor();
      for (int i = 0; i < attrs.getLength(); ++i)
      {
         String localName = attrs.getLocalName(i);
         if ("factoryClass".equals(localName))
            constructor.setFactoryClass(attrs.getValue(i));
         else if ("factoryMethod".equals(localName))
            constructor.setFactoryMethod(attrs.getValue(i));
      }
      if( constructor.getFactoryMethod() != null && constructor.getFactoryClass() == null )
         constructor.setFactoryClass(ctor.getClassName());
   }

   public Object endElement(Object o, QName qName, ElementBinding element)
   {
      Holder holder = (Holder) o;
      Ctor ctor = (Ctor) holder.getValue();
      try
      {
         return ctor.newInstance();
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
         throw new RuntimeException("Error instantiating class " + ctor.getClassName(), t);
      }

   }
}

