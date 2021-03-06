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
package org.jboss.test.plugins.javassist;

import java.lang.reflect.Method;

import org.jboss.reflect.plugins.javassist.JavassistTypeInfoFactory;
import org.jboss.reflect.spi.InsertAfterJavassistBody;
import org.jboss.reflect.spi.InsertBeforeJavassistBody;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableMethodInfo;
import org.jboss.reflect.spi.TypeInfo;
import org.jboss.test.ContainerTest;

/**
 * A JavassistBodyTestCase.
 * 
 * @author <a href="mailto:stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public class JavassistBodyTestCase extends ContainerTest
{

   /**
    * Create a new JavassistBodyTestCase.
    * 
    * @param name
    */
   public JavassistBodyTestCase(String name)
   {
      super(name);
   }
   
   @SuppressWarnings("deprecation")
   public void testBody()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactory().getMutable("org.jboss.test.plugins.javassist.PojoBody", null);
      try
      {
         MutableMethodInfo mmi = mci.getDeclaredMethod("foo", new TypeInfo[] {(TypeInfo) new JavassistTypeInfoFactory().getMutable("int", null) });

         System.out.println("got method: "+mmi.getName());
         mmi.setBody(new InsertBeforeJavassistBody("i = 42;"));
         MutableMethodInfo mmi2 = mci.getDeclaredMethod("bar", new TypeInfo[] {});
         System.out.println("mmi2: "+mmi2.getName());
         mmi2.setBody(new InsertAfterJavassistBody("s = \"after\" + s; return s;"));
         System.out.println("mmi2: "+mmi2.getName());

         Class<?> clazz = mci.getType();

         Object pojoBody = clazz.newInstance();
         Method m1 = clazz.getDeclaredMethods()[0];
         assertEquals(42, m1.invoke(pojoBody, new Object[] {1}));
         Method m2 = clazz.getDeclaredMethods()[1];
         assertEquals("afterbar", m2.invoke(pojoBody, new Object[] {}));
         
         System.out.println();
      }
      catch (Exception e)
      {
         e.printStackTrace();
      }
   }

}
