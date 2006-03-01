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
package org.jboss.test.repository.test;

import java.util.TreeMap;

import junit.framework.TestCase;

import org.jboss.repository.spi.Key;

/**
 * Registry Test Case.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class KeyOrderingTestCase extends TestCase
{
   public KeyOrderingTestCase(String name)
   {
      super(name);
   }

   public void testGotRegistry() throws Throwable
   {
      TreeMap map = new TreeMap();
      Key key0 = new Key("permissions:domain=d0,cluster=*,server=host0,app=ear0");
      map.put(key0, "#p0");
      Key key0a = new Key("permissions:domain=d0,cluster=*,server=host0,app=ear0,deployment=ejb0");
      map.put(key0a, "#p0-ejb0");
      Key key2 = new Key("permissions:domain=d0,cluster=*,server=host0,app=ear0,deployment=ejb1");
      map.put(key2, "p0-ejb1");
      Key key3 = new Key("permissions:domain=d0,cluster=*,server=host0,app=ear0,deployment=ejb2");
      map.put(key3, "p0-ejb2");

      Key key0b = new Key("permissions:domain=d0,cluster=*,server=host0,app=ear0,deployment=ejb0,session=s0");
      map.put(key0b, "#p0-ejb0-s0");
   }

}
