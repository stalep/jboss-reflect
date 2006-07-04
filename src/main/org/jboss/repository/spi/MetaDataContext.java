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
package org.jboss.repository.spi;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public interface MetaDataContext
{
   KernelRepository getRepository();
   
   Map getScope();
   
   void setTarget(Object tgt);
   
   Object getAnnotation(Class ann);
   
   List getAnnotations();
   
   boolean hasAnnotation(String ann);

   Object getAnnotation(Method m, Class ann);
   
   List getAnnotationsForMethod(String methodName);
   
   List getAnnotationsForMethods(String[] methodNames);
   
   boolean hasAnnotation(Method m, String ann);
   
   void addAnnotations(Set annotations);
   
   void addPropertyAnnotations(String propertyName, Set propertyInfos, Set annotations);   
}
