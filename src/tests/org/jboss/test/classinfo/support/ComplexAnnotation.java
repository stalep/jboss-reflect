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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME)
@SimpleAnnotation
@ValueAnnotation("annotation_")
public @interface ComplexAnnotation 
{
   String stringValue() default "The answer is";
   int intValue() default 42;
   ValueAnnotation annotationValue() default @ValueAnnotation("method_");
   Class<?> clazzValue() default Integer.class;
   TestEnum enumValue() default TestEnum.METHOD;
   
   String[] stringArrayValue() default {"The", "answer", "is"};
   int[] intArrayValue() default {4, 2};
   ValueAnnotation[] annotationArrayValue() default {@ValueAnnotation("Ann1"), @ValueAnnotation("Ann2")};
   Class<?>[] clazzArrayValue() default {Integer.class, Long.class};
   TestEnum[] enumArrayValue() default {TestEnum.ARRAY_STUFF, TestEnum.METHOD};
}
