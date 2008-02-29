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
package org.jboss.javabean.plugins.jaxb;

import javax.xml.bind.annotation.XmlNsForm;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.jboss.xb.annotations.JBossXmlSchema;
import org.jboss.xb.annotations.JBossXmlType;
import org.jboss.xb.spi.BeanAdapterFactory;

/**
 * JavaBean version 1.
 *
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@JBossXmlSchema(namespace="urn:jboss:javabean:1.0", elementFormDefault=XmlNsForm.QUALIFIED)
@XmlRootElement(name="javabean")
@XmlType(name="javabeanType")
@JBossXmlType(beanAdapterBuilder=JavaBeanBuilder10.class)
public class JavaBean10 extends JavaBean
{
   /**
    * Create a new JavaBean10.
    * 
    * @param beanAdapterFactory the bean adapter factory
    */
   public JavaBean10(BeanAdapterFactory beanAdapterFactory)
   {
      super(beanAdapterFactory);
   }

   @XmlTransient // Version 1 didn't have a constructor
   public void setConstructor(Constructor constructor)
   {
      super.setConstructor(constructor);
   }
}
