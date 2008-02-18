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
package org.jboss.test.xb.builder;

import java.net.URL;
import java.util.Collection;

import org.jboss.test.AbstractTestCaseWithSetup;
import org.jboss.test.AbstractTestDelegate;
import org.jboss.util.UnreachableStatementException;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultHandlers;
import org.jboss.xb.binding.sunday.unmarshalling.DefaultSchemaResolver;
import org.jboss.xb.binding.sunday.unmarshalling.ParticleBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBinding;
import org.jboss.xb.binding.sunday.unmarshalling.SchemaBindingResolver;
import org.jboss.xb.binding.sunday.unmarshalling.SequenceBinding;
import org.jboss.xb.binding.sunday.unmarshalling.TermBinding;
import org.jboss.xb.binding.sunday.unmarshalling.ParticleHandler;
import org.jboss.xb.builder.JBossXBBuilder;

/**
 * AbstractBuilderTest.
 *
 * @author <a href="ales.justin@jboss.com">Ales Justin</a>
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class AbstractBuilderTest extends AbstractTestCaseWithSetup
{
   protected String rootName = getRootName();

   private static final DebugElementHandler DEBUG_ELEMENT_HANDLER = new DebugElementHandler();
   private ParticleHandler elementHandler;
   private ParticleHandler simpleHandler;

   public AbstractBuilderTest(String name)
   {
      super(name);
   }

   /**
    * Setup the test delegate
    *
    * @param clazz the class
    * @return the delegate
    * @throws Exception for any error
    */
   public static AbstractTestDelegate getDelegate(Class<?> clazz) throws Exception
   {
      return new JBossXBTestDelegate(clazz);
   }

   protected JBossXBTestDelegate getJBossXBDelegate()
   {
      return (JBossXBTestDelegate) getDelegate();
   }

   @Override
   protected void setUp() throws Exception
   {
      elementHandler = DefaultHandlers.ELEMENT_HANDLER;
      simpleHandler = DefaultHandlers.SIMPLE_HANDLER;
      DefaultHandlers.ELEMENT_HANDLER = DEBUG_ELEMENT_HANDLER;
      DefaultHandlers.SIMPLE_HANDLER = DefaultHandlers.ELEMENT_HANDLER;

      super.setUp();
      configureLogging();
   }

   protected void tearDown() throws Exception
   {
      super.tearDown();

      DefaultHandlers.ELEMENT_HANDLER = elementHandler;
      elementHandler = null;
      DefaultHandlers.SIMPLE_HANDLER = simpleHandler;
      simpleHandler = null;
   }

   protected <T> T unmarshalObjectFromSchema(Class<T> expected) throws Exception
   {
      DefaultSchemaResolver resolver = new DefaultSchemaResolver();
      // TODO this is a mess
      String nsURI = "http://www.jboss.org/test/xml/" + rootName;
      String packageName = getClass().getPackage().getName();
      packageName = packageName.replace(".", "/");
      String name = getClass().getName();
      int dot = name.lastIndexOf('.');
      if (dot != -1)
         name = name.substring(dot + 1);
      dot = name.lastIndexOf("UnitTestCase");
      if (dot != -1)
         name = name.substring(0, dot);
      String testXsd = packageName + '/' + name + ".xsd";
      resolver.addSchemaLocation(nsURI, testXsd);
      resolver.addSchemaInitializer(nsURI, JBossXBBuilder.newInitializer(expected));

      String testXml = findTestXml();
      Object o = unmarshal(testXml, expected, resolver);
      assertNotNull(o);
      getLog().debug("Unmarshalled " + o + " of type " + o.getClass().getName());
      return expected.cast(o);
   }

   protected <T, U> T unmarshalObject(Class<T> expected, Class<U> reference, Class<?>... others) throws Exception
   {
      TestSchemaResolver resolver = new TestSchemaResolver();

      SchemaBinding schemaBinding = JBossXBBuilder.build(reference);
      resolver.addSchemaBinding(schemaBinding);
      if (others != null)
      {
         for (Class<?> other : others)
         {
            SchemaBinding otherBinding = JBossXBBuilder.build(other);
            resolver.addSchemaBinding(otherBinding);
         }
      }

      String testXml = findTestXml();
      Object o = unmarshal(testXml, schemaBinding);
      assertNotNull(o);
      getLog().debug("Unmarshalled " + o + " of type " + o.getClass().getName());
      try
      {
         return expected.cast(o);
      }
      catch (ClassCastException e)
      {
         fail("Expected " + expected.getName() + " got " + o.getClass().getName());
         throw new UnreachableStatementException();
      }
   }

   /**
    * Unmarshal some xml
    *
    * @param name the name
    * @param schema the schema
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected Object unmarshal(String name, SchemaBinding schema) throws Exception
   {
      String url = findXML(name);
      return getJBossXBDelegate().unmarshal(url, schema);
   }

   protected <T, U> T unmarshalObject(Class<T> expected, Class<U> reference) throws Exception
   {
      return unmarshalObject(expected, reference, null);
   }

   protected <T> T unmarshalObject(Class<T> expected) throws Exception
   {
      return unmarshalObject(expected, expected, null);
   }

   /**
    * Unmarshal some xml
    *
    * @param name the name
    * @param expected the expected type
    * @param resolver the resolver
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected Object unmarshal(String name, Class<?> expected, SchemaBindingResolver resolver) throws Exception
   {
      Object object = unmarshal(name, resolver);
      if (object == null)
         fail("No object from " + name);
      assertTrue("Object '" + object + "' cannot be assigned to " + expected.getName(), expected.isAssignableFrom(object.getClass()));
      return object;
   }

   /**
    * Unmarshal some xml
    *
    * @param name the name
    * @param resolver the resolver
    * @return the unmarshalled object
    * @throws Exception for any error
    */
   protected Object unmarshal(String name, SchemaBindingResolver resolver) throws Exception
   {
      String url = findXML(name);
      return getJBossXBDelegate().unmarshal(url, resolver);
   }

   protected String findTestXml()
   {
      String result = rootName + "_" + getName() + ".xml";
      if (getResource(result) == null)
         result = rootName + ".xml";
      return result;
   }

   /**
    * Get the package root name
    *
    * @return the root name
    */
   protected String getRootName()
   {
      String longName = getClass().getName();
      int dot = longName.lastIndexOf('.');
      if (dot != -1)
         longName = longName.substring(dot + 1);
      dot = longName.lastIndexOf("UnitTestCase");
      if (dot != -1)
         longName = longName.substring(0, dot);
      return longName;
   }

   @SuppressWarnings("unchecked")
   protected TermBinding assertSingleSequence(TermBinding term)
   {
      assertNotNull(term);
      assertTrue(term instanceof SequenceBinding);
      SequenceBinding sequence = (SequenceBinding) term;
      Collection<ParticleBinding> particles = sequence.getParticles();
      assertTrue(particles.size() == 1);
      ParticleBinding particle = particles.iterator().next();
      term = particle.getTerm();
      assertNotNull(term);
      return term;
   }

   /**
    * Find the xml
    *
    * @param name the name
    * @return the url of the xml
    */
   protected String findXML(String name)
   {
      URL url = getResource(name);
      if (url == null)
         fail(name + " not found");
      return url.toString();
   }

   @Override
   public void configureLogging()
   {
      //enableTrace("org.jboss.xb");
      //enableTrace("org.jboss.test.xb");
   }
}
