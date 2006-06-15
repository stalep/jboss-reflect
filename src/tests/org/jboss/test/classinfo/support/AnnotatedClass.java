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

/**
 * 
 * @author <a href="kabir.khan@jboss.com">Kabir Khan</a>
 * @version $Revision$
 */
@ComplexAnnotation(
      stringValue="_class", 
      intValue=1, 
      annotationValue=@ValueAnnotation("class_"), 
      clazzValue=AnnotatedClass.class, 
      enumValue=TestEnum.CLASS,
      stringArrayValue= {"A", "class"},
      intArrayValue= {10, 1},
      annotationArrayValue= {@ValueAnnotation("stuffer"), @ValueAnnotation("class")},
      clazzArrayValue= {AnnotatedClass.class},
      enumArrayValue= {TestEnum.CLASS, TestEnum.ARRAY_STUFF}
      )
@SimpleAnnotation
public class AnnotatedClass
{
   @ComplexAnnotation(
         stringValue="_field", 
         intValue=2, 
         annotationValue=@ValueAnnotation("field_"), 
         clazzValue=AnnotatedClass.class, 
         enumValue=TestEnum.FIELD,
         stringArrayValue= {"A", "field"},
         intArrayValue= {10, 2},
         annotationArrayValue= {@ValueAnnotation("stuffer"), @ValueAnnotation("field")},
         clazzArrayValue = {AnnotatedClass.class},
         enumArrayValue= {TestEnum.FIELD, TestEnum.ARRAY_STUFF}
         )
   @SimpleAnnotation
   public int field;
   
   @ComplexAnnotation(
         stringValue="_ctor", 
         intValue=3, 
         annotationValue=@ValueAnnotation("ctor_"), 
         clazzValue=AnnotatedClass.class, 
         enumValue=TestEnum.CONSTRUCTOR,
         stringArrayValue= {"A", "ctor"},
         intArrayValue= {10, 3},
         annotationArrayValue= {@ValueAnnotation("stuffer"), @ValueAnnotation("ctor")},
         clazzArrayValue= {AnnotatedClass.class},
         enumArrayValue= {TestEnum.CONSTRUCTOR, TestEnum.ARRAY_STUFF})
   @SimpleAnnotation
   public AnnotatedClass(
         @ValueAnnotation("int") @ComplexAnnotation(stringValue="1", intArrayValue={1}) int i, 
         @ValueAnnotation("string") @SimpleAnnotation String x)
   {
   }
   
   @ComplexAnnotation
   @SimpleAnnotation
   public void method(
         @ValueAnnotation("int") @ComplexAnnotation(stringValue="1", intArrayValue={1}) int i, 
         @ValueAnnotation("string") @SimpleAnnotation String x)
   {
      
   }
}
