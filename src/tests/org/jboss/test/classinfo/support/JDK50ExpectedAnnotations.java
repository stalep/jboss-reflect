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
package org.jboss.test.classinfo.support;

import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
public class JDK50ExpectedAnnotations extends ExpectedAnnotations
{
   final static Class[] EXPECTED_ANNOTATIONS = {SimpleAnnotation.class, ComplexAnnotation.class};
   final static Class[] ANNOTATION_EXPECTED_ANNOTATIONS = {Target.class, Retention.class, Inherited.class};   
   final static Class[] COMPLEXANNOTATION_EXPECTED_ANNOTATIONS = {Target.class, Retention.class, SimpleAnnotation.class, ValueAnnotation.class};
   final static Class[] FIRST_PARAM_EXPECTED_ANNOTATIONS = {ValueAnnotation.class, ComplexAnnotation.class};
   final static Class[] SECOND_PARAM_EXPECTED_ANNOTATIONS = {ValueAnnotation.class, SimpleAnnotation.class};
   
   public Class[] getAnnotationExpectedAnnotations()
   {
      return ANNOTATION_EXPECTED_ANNOTATIONS;
   }
   public Class[] getComplexExpectedAnnotations()
   {
      return COMPLEXANNOTATION_EXPECTED_ANNOTATIONS;
   }
   public Class[] getExpectedAnnotations()
   {
      return EXPECTED_ANNOTATIONS;
   }
   public Class[] getFirstParamExpectedAnnotations()
   {
      return FIRST_PARAM_EXPECTED_ANNOTATIONS;
   }
   public Class[] getSecondParamExpectedAnnotations()
   {
      return SECOND_PARAM_EXPECTED_ANNOTATIONS;
   }

}
