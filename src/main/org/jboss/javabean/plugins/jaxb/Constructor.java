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

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Constructor.
 *
 * @author <a href="mailto:ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
@XmlRootElement(name="constructor")
public class Constructor
{
   private String factoryClass;

   private String factoryMethod;

   private Parameter[] parameters;

   public String getFactoryClass()
   {
      return factoryClass;
   }

   @XmlAttribute(name="factoryClass")
   public void setFactoryClass(String factoryClass)
   {
      this.factoryClass = factoryClass;
   }

   public String getFactoryMethod()
   {
      return factoryMethod;
   }

   @XmlAttribute(name="factoryMethod")
   public void setFactoryMethod(String factoryMethod)
   {
      this.factoryMethod = factoryMethod;
   }

   public Parameter[] getParameters()
   {
      return parameters;
   }

   @XmlElement(name="parameter")
   public void setParameters(Parameter[] parameters)
   {
      this.parameters = parameters;
   }
}
