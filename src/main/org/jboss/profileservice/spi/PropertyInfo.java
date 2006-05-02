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

package org.jboss.profileservice.spi;

import java.util.Set;

/**
 * Modelled after the JMX OpenMBeanParameterInfo
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public interface PropertyInfo
{
   public String getName();
   public String getDescription();
   public Set<?> getLegalValues();
   public Comparable<?> getMinValue();
   public Comparable<?> getMaxValue();
   public boolean isValue(Object obj);
   public OpenType<?> getOpenType();

   /**
    * Get the current property value. Must interact with the
    * Policy somehow.
    * @return current property value.
    */
   public Object getValue();

   /**
    * Set the current property value. Must interact with the
    * Policy somehow.
    * @param value - current property value.
    */
   public void setValue(Object value);
}
