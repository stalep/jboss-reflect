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

import javax.xml.namespace.QName;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.PropertyInfo;
import org.jboss.javabean.plugins.xml.Common.Ctor;
import org.jboss.javabean.plugins.xml.Common.Holder;
import org.jboss.javabean.plugins.xml.Common.Property;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultElementInterceptor;

/**
 * Interceptor for the property element that adds the Property to the
 * Holder parent.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public class PropertyInterceptor extends DefaultElementInterceptor
{
   /** The interceptor */
   public static final PropertyInterceptor INTERCEPTOR = new PropertyInterceptor();

   /**
    * Add a property to the bean. If the parent value is a Ctor the bean
    * had no explicit contstructor element and the instance must be created by
    * the default ctor. Otherwise, the property is added to teh Ctor as
    * params.
    * 
    * If the parent value is not a Ctor the Property is a value to set on
    * the bean.
    * 
    * @param parent Holder containing either a Ctor or javabean instance.
    * @param child - the Property instance to add
    */
   public void add(Object parent, Object child, QName name)
   {
      Holder holder = (Holder) parent;
      Object parentValue = holder.getValue();
      Property prop = (Property) child;
      Object value = prop.getValue();
      String property = prop.getProperty();

      try
      {
         if( parentValue instanceof Ctor )
         {
            Ctor ctor = (Ctor) parentValue;
            if( ctor.isCtorWasDeclared() )
            {
               BeanInfo beanInfo = ConfigurationUtil.getBeanInfo(ctor.getClassName());
               PropertyInfo propertyInfo = beanInfo.getProperty(property);
               value = ConfigurationUtil.convertValue(propertyInfo, prop.getType(), value);
               ctor.addParam(propertyInfo.getType().getName(), value);
            }
            else
            {
               // There was no explicit ctor to create the bean and reset the parent value
               parentValue = ctor.newInstance();
               holder.setValue(parentValue);
               add(parent, child, name);
            }
         }
         else
         {
            BeanInfo beanInfo = ConfigurationUtil.getBeanInfo(parentValue);
            value = ConfigurationUtil.convertValue(parentValue, property, prop.getType(), value);
            beanInfo.setProperty(parentValue, property, value);
         }
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

}
