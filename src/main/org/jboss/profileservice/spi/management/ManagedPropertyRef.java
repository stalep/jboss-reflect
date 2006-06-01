/*
 * JBoss, Home of Professional Open Source
 * Copyright 2005, JBoss Inc., and individual contributors as indicated
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
package org.jboss.profileservice.spi.management;

/**
 * A management view wrapper of a bean property.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ManagedPropertyRef
{
   /**
    * A namespace context that maps the managed property onto a managed view
    * context (e.g., /, /DataSource, /DataSource/Pool).
    */
   private String context;
   /**
    * The DeploymentBean name which sources the property
    * @return
    */
   private String beanName;
   /**
    * The DeploymentBean property name
    */
   private String propertyName;
   /**
    * An option management view description of the property
    */
   private String description;

   /**
    * Create a managed property ref for the indicated bean property.
    * @param context
    * @param beanName
    * @param propertyName
    * @param description
    */
   public ManagedPropertyRef(String context, String beanName,
      String propertyName, String description)
   {
      this.context = context;
      this.beanName = beanName;
      this.propertyName = propertyName;
      this.description = description;
   }

   /**
    * @return Returns the beanName.
    */
   public String getBeanName()
   {
      return this.beanName;
   }

   /**
    * @return Returns the context.
    */
   public String getContext()
   {
      return this.context;
   }

   /**
    * @return Returns the description.
    */
   public String getDescription()
   {
      return this.description;
   }

   /**
    * @return Returns the propertyName.
    */
   public String getPropertyName()
   {
      return this.propertyName;
   }

   /**
    * Equality based on beanName + propertyName
    */
   public boolean equals(Object obj)
   {
      ManagedPropertyRef ref = (ManagedPropertyRef) obj;
      boolean equals = beanName.equals(ref.beanName);
      if( equals )
      {
         equals = propertyName.equals(ref.propertyName);
      }
      return equals;
   }

   /**
    * Hash based on beanName + propertyName
    */
   public int hashCode()
   {
      return beanName.hashCode() + propertyName.hashCode();
   }
}
