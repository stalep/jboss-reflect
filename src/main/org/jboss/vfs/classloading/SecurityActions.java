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
package org.jboss.vfs.classloading;

import java.security.PrivilegedAction;
import java.security.AccessController;

import org.jboss.vfs.spi.ReadOnlyVFS;

/**
 Package priviledged actions
 @author Scott.Stark@jboss.org
 @version $Revision$
 */
public class SecurityActions
{
   interface ClassLoaderActions
   {
      ClassLoaderActions PRIVILEGED = new ClassLoaderActions()
      {
         public VFSClassLoader newClassLoader(final String[] paths, final ReadOnlyVFS vfs)
         {
            PrivilegedAction<VFSClassLoader> action = new PrivilegedAction<VFSClassLoader>()
            {
               public VFSClassLoader run()
               {
                  return new VFSClassLoader(paths, vfs);
               }
            };
            return AccessController.doPrivileged(action);
         }
      };

      ClassLoaderActions NON_PRIVILEGED = new ClassLoaderActions()
      {
         public VFSClassLoader newClassLoader(final String[] paths, final ReadOnlyVFS vfs)
         {
            return new VFSClassLoader(paths, vfs);
         }
      };

      VFSClassLoader newClassLoader(final String[] paths, final ReadOnlyVFS vfs);
   }

   static VFSClassLoader newClassLoader(final String[] paths, final ReadOnlyVFS vfs)
   {
      if(System.getSecurityManager() == null)
      {
         return ClassLoaderActions.NON_PRIVILEGED.newClassLoader(paths, vfs);
      }
      else
      {
         return ClassLoaderActions.PRIVILEGED.newClassLoader(paths, vfs);
      }
   }

}
