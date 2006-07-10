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
package org.jboss.test.metadata.scope.test;

import java.util.TreeSet;

import org.jboss.metadata.spi.scope.CommonLevels;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.test.metadata.AbstractMetaDataTest;

/**
 * CommonLevelsUnitTestCase.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class CommonLevelsUnitTestCase extends AbstractMetaDataTest
{
   public CommonLevelsUnitTestCase(String name)
   {
      super(name);
   }

   public void testCommonLevels() throws Exception
   {
      ScopeLevel[] commonLevels = new ScopeLevel[]
      {
         CommonLevels.DOMAIN,
         CommonLevels.CLUSTER,
         CommonLevels.MACHINE,
         CommonLevels.NODE,
         CommonLevels.JVM,
         CommonLevels.SERVER,
         CommonLevels.SUBSYSTEM,
         CommonLevels.APPLICATION,
         CommonLevels.DEPLOYMENT,
         CommonLevels.CLASS,
         CommonLevels.INSTANCE,
         CommonLevels.JOINPOINT,
         CommonLevels.JOINPOINT_OVERRIDE,
         CommonLevels.THREAD,
         CommonLevels.WORK,
         CommonLevels.REQUEST,
      };
      
      int lastLevel = 0;
      String lastName = "";
      for (ScopeLevel level : commonLevels)
      {
         assertTrue(level.getLevel() - lastLevel == 100);
         assertFalse(lastName.equals(level.getName()));
         
         lastLevel = level.getLevel();
         lastName = level.getName();
      }
      
      TreeSet<ScopeLevel> set = new TreeSet<ScopeLevel>();
      for (ScopeLevel level : commonLevels)
         set.add(level);
      
      assertTrue(commonLevels.length == set.size());
      int index = 0;
      for (ScopeLevel level : set)
         assertTrue(commonLevels[index++] == level);
   }
}
