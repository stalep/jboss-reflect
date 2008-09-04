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
package org.jboss.classloading.spi;

import java.lang.instrument.IllegalClassFormatException;
import java.lang.instrument.Instrumentation;
import java.security.ProtectionDomain;

/**
 * An implementation of this interface in order to transform class files.
 * The transformation occurs before the class is defined by the JVM.
 * @see java.lang.instrument.ClassFileTransformer
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision:$
 */
public interface Translator
{
   /**
    * The implementation of this method may transform the supplied class file and 
    * return a new replacement class file.
    *
    * @param loader - the DomainClassLoader owning the defining loader of the
    *    class to be transformed, may be <code>null</code> if the bootstrap loader
    * @param className - the name of the class in the internal form of fully
    *    qualified class and interface names.
    * @param classBeingRedefined - if this is a redefine, the class being redefined, 
    *    otherwise <code>null</code>
    * @param protectionDomain  - the protection domain of the class being defined or redefined
    * @param classfileBuffer - the input byte buffer in class file format - must not be modified
    *
    * @throws IllegalClassFormatException if the input does not represent a well-formed class file
    * @return  a well-formed class file buffer (the result of the transform), 
               or <code>null</code> if no transform is performed.
    * @see Instrumentation#redefineClasses
    */
   public byte[] transform(DomainClassLoader loader,
         String className,
         Class<?> classBeingRedefined,
         ProtectionDomain protectionDomain,
         byte[] classfileBuffer)
         throws IllegalClassFormatException;

   /** Called to indicate that the ClassLoader is being discarded by the server.
   *
   * @param loader - a class loader that has possibly been used previously
   *    as an argument to transform.
   */
   public void unregisterClassLoader(DomainClassLoader loader);
}
