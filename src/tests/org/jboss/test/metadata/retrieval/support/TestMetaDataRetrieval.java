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
package org.jboss.test.metadata.retrieval.support;

import java.lang.annotation.Annotation;

import org.jboss.metadata.spi.retrieval.AnnotationItem;
import org.jboss.metadata.spi.retrieval.AnnotationsItem;
import org.jboss.metadata.spi.retrieval.MetaDataItem;
import org.jboss.metadata.spi.retrieval.MetaDataRetrieval;
import org.jboss.metadata.spi.retrieval.MetaDatasItem;
import org.jboss.metadata.spi.retrieval.ValidTime;
import org.jboss.metadata.spi.scope.Scope;
import org.jboss.metadata.spi.scope.ScopeKey;
import org.jboss.metadata.spi.scope.ScopeLevel;
import org.jboss.metadata.spi.signature.Signature;

/**
 * TestMetaDataRetrieval.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class TestMetaDataRetrieval implements MetaDataRetrieval
{
   private static final ScopeLevel testLevel = new ScopeLevel(1, "TEST");
   private static final ScopeKey testScopeKey = new ScopeKey(new Scope(testLevel, "TEST"));
   
   public String lastMethod;
   
   public ScopeKey getScope()
   {
      return testScopeKey;
   }

   public ValidTime getValidTime()
   {
      lastMethod = "getValidTime";
      return new ValidTime();
   }

   public <T extends Annotation> AnnotationItem<T> retrieveAnnotation(Class<T> annotationType)
   {
      lastMethod = "retrieveAnnotation";
      return null;
   }

   public AnnotationsItem retrieveAnnotations()
   {
      lastMethod = "retrieveAnnotations";
      return null;
   }

   public AnnotationsItem retrieveLocalAnnotations()
   {
      lastMethod = "retrieveLocalAnnotations";
      return null;
   }

   public MetaDatasItem retrieveMetaData()
   {
      lastMethod = "retrieveMetaData";
      return null;
   }

   public MetaDatasItem retrieveLocalMetaData()
   {
      lastMethod = "retrieveLocalMetaData";
      return null;
   }

   public <T> MetaDataItem<T> retrieveMetaData(Class<T> type)
   {
      lastMethod = "retrieveMetaData(Class)";
      return null;
   }

   public MetaDataItem retrieveMetaData(String name)
   {
      lastMethod = "retrieveMetaData(String)";
      return null;
   }

   public MetaDataRetrieval getComponentMetaDataRetrieval(Signature signature)
   {
      return null;
   }
}
