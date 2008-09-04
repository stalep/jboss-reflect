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
package org.jboss.test.classinfo.support;

/**
 * ClassInfoAbstractMethodsClass
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public abstract class ClassInfoAbstractMethodsClass
{
   abstract void voidMethodVoidPackage();
   abstract long longMethodVoidPackage();
   abstract void voidMethodintPackage(int p1);
   abstract long longMethodintPackage(int p1);
   abstract void voidMethodintStringPackage(int p1, String p2);
   abstract long longMethodintStringPackage(int p1, String p2);
   abstract long longMethodintStringThrowsPackage(int p1, String p2) throws IllegalArgumentException, IllegalStateException;
   protected abstract void voidMethodVoidProtected();
   protected abstract long longMethodVoidProtected();
   protected abstract void voidMethodintProtected(int p1);
   protected abstract long longMethodintProtected(int p1);
   protected abstract void voidMethodintStringProtected(int p1, String p2);
   protected abstract long longMethodintStringProtected(int p1, String p2);
   protected abstract long longMethodintStringThrowsProtected(int p1, String p2) throws IllegalArgumentException, IllegalStateException;
   public abstract void voidMethodVoidPublic();
   public abstract long longMethodVoidPublic();
   public abstract void voidMethodintPublic(int p1);
   public abstract long longMethodintPublic(int p1);
   public abstract void voidMethodintStringPublic(int p1, String p2);
   public abstract long longMethodintStringPublic(int p1, String p2);
   public abstract long longMethodintStringThrowsPublic(int p1, String p2) throws IllegalArgumentException, IllegalStateException;
}
