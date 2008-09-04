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
 * ClassInfoMethodsClass
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class ClassInfoMethodsClass
{
   private void voidMethodVoidPrivate() {}
   private long longMethodVoidPrivate() { return 0; }
   private void voidMethodintPrivate(int p1) {}
   private long longMethodintPrivate(int p1) { return 0; }
   private void voidMethodintStringPrivate(int p1, String p2) {}
   private long longMethodintStringPrivate(int p1, String p2) { return 0; }
   private long longMethodintStringThrowsPrivate(int p1, String p2) throws IllegalArgumentException, IllegalStateException { return 0; }
   void voidMethodVoidPackage() { voidMethodVoidPrivate(); }
   long longMethodVoidPackage() { return longMethodVoidPrivate(); }
   void voidMethodintPackage(int p1) { voidMethodintPrivate(p1); }
   long longMethodintPackage(int p1) { return longMethodintPrivate(p1); }
   void voidMethodintStringPackage(int p1, String p2) { voidMethodintStringPrivate(p1, p2); }
   long longMethodintStringPackage(int p1, String p2) { return longMethodintStringPrivate(p1, p2); }
   long longMethodintStringThrowsPackage(int p1, String p2) throws IllegalArgumentException, IllegalStateException { return longMethodintStringThrowsPrivate(p1, p2); }
   protected void voidMethodVoidProtected() {}
   protected long longMethodVoidProtected() { return 0; }
   protected void voidMethodintProtected(int p1) {}
   protected long longMethodintProtected(int p1) { return 0; }
   protected void voidMethodintStringProtected(int p1, String p2) {}
   protected long longMethodintStringProtected(int p1, String p2) { return 0; }
   protected long longMethodintStringThrowsProtected(int p1, String p2) throws IllegalArgumentException, IllegalStateException { return 0; }
   public void voidMethodVoidPublic() {}
   public long longMethodVoidPublic() { return 0; }
   public void voidMethodintPublic(int p1) {}
   public long longMethodintPublic(int p1) { return 0; }
   public void voidMethodintStringPublic(int p1, String p2) {}
   public long longMethodintStringPublic(int p1, String p2) { return 0; }
   public long longMethodintStringThrowsPublic(int p1, String p2) throws IllegalArgumentException, IllegalStateException { return 0; }
}
