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
package org.jboss.test.memorytests;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.HashSet;

import org.jboss.profiler.jvmti.JVMTIInterface;
import org.jboss.reflect.plugins.introspection.IntrospectionTypeInfoFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.reflect.spi.TypeInfoFactory;
import org.jboss.test.JBossMemoryTestCase;

/**
 * ClassInfo Test Case.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @author <a href="clebert.suconic@jboss.com">Clebert Suconic</a>
 * @author Source Forge User Chung Chung
 * @version $Revision$
 */
public class ClassInfoMemoryTestCase extends JBossMemoryTestCase
{
   public ClassInfoMemoryTestCase(String name)
   {
      super(name);
   }
   public ClassInfoMemoryTestCase()
   {
      super();
   }
   public void testSimpleBeanClassInfo() throws Throwable
   {
     ClassLoader oldloader = Thread.currentThread().getContextClassLoader();
     System.out.println("+++oldloader =" + oldloader.toString());
     ClassLoader loader = newClassLoader(ClassInfoMemoryTestCase.class);
     WeakReference<ClassLoader> weakReferenceOnLoader = new WeakReference<ClassLoader>(loader);
     
     System.out.println("+++newloader =" + loader.toString());
     //step1
     
     Class<?> simpleBeanClass = loader.loadClass("org.jboss.test.classinfo.support.SimpleBean");
     Class<?> simpleBeanInterface = loader.loadClass("org.jboss.test.classinfo.support.SimpleInterface");
     ClassInfo cinfo = getClassInfo(loader,simpleBeanClass);
     cinfo=null;
     
     // Test if the reference on ClassInfo is not too weak
     JVMTIInterface jvmti = new JVMTIInterface();
     if (jvmti.isActive())
     {
        jvmti.forceGC();
        assertEquals(1,jvmti.getAllObjects(ClassInfo.class).length);
     }

     
     cinfo = getClassInfo(loader,simpleBeanClass);
      assertEquals(simpleBeanClass.getName(), cinfo.getName());
      ClassInfo supercinfo = cinfo.getSuperclass();
      assertNotNull(supercinfo);
      assertEquals(Object.class.getName(), supercinfo.getName());
      
      HashSet<String> expected = new HashSet<String>();
      expected.add(Serializable.class.getName());
      expected.add(simpleBeanInterface.getName());
      checkTypeSet(expected, cinfo.getInterfaces());
     //step2 
      
      
      oldloader =null;
      loader=null;
      cinfo=null;
      supercinfo=null;
      expected=null;
      simpleBeanClass = null;
      simpleBeanInterface = null;
      checkUnload( weakReferenceOnLoader,"org.jboss.test.classinfo.support.SimpleBean","./leak-report.html");          
   }
   
   protected ClassInfo getClassInfo(ClassLoader loader,Class<?> clazz)
   {
      TypeInfoFactory factory = getTypeInfoFactory();
      TypeInfo info=null;
      try{
          info = factory.getTypeInfo(clazz.getName(),loader);
      }catch(Exception e){
          System.out.println(e);
      }
      assertNotNull(info);
      assertTrue(info instanceof ClassInfo);
      //getLog().debug(cinfo);
      return (ClassInfo) info;
   }
   
   protected void checkTypeSet(HashSet<String> expected, TypeInfo[] typeInfos) throws Throwable
   {
      HashSet<String> actual = new HashSet<String>();
      for (int i = 0; i < typeInfos.length; ++i)
         actual.add(typeInfos[i].getName());
      
      HashSet<String> expectClone = new HashSet<String>(expected);
      HashSet<String> actualClone = new HashSet<String>(actual);
      
      //getLog().debug("checkTypeSet expect=" + expected);
      //getLog().debug("checkTypeSet actual=" + actual);
      
      expectClone.removeAll(actual);
      assertTrue("Expected " + expectClone, expectClone.isEmpty());

      actualClone.removeAll(expected);
      assertTrue("Did not expect " + actualClone, actualClone.isEmpty());
   }

   protected TypeInfoFactory getTypeInfoFactory()
   {
      return new IntrospectionTypeInfoFactory();
   }
 
}
