package org.jboss.test.plugins.javassist;

import org.jboss.reflect.plugins.javassist.JavassistTypeInfoFactoryImpl;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableConstructorInfo;
import org.jboss.reflect.spi.MutableFieldInfo;
import org.jboss.test.ContainerTest;

public class JavassistConstructorInfoTestCase extends ContainerTest
{

   public JavassistConstructorInfoTestCase(String name)
   {
      super(name);
   }
   
   public void testAddConstructor()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.PojoConstructor", null);
      try
      {
         MutableConstructorInfo intConstructor = mci.getDeclaredConstructor(new String[] {"int"});
         assertNotNull(intConstructor);
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }
    
      MutableConstructorInfo mci1 = mci.createMutableConstructor(ModifierInfo.PUBLIC, new String[] {"java.lang.String"}, new String[0]);
      mci.addConstructor(mci1);
      assertEquals(3, mci.getDeclaredConstructors().length);
   }
   
   public void testRemoveConstructor()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.PojoConstructor", null);
      MutableConstructorInfo constructor;
      try
      {
         constructor = mci.getDeclaredConstructor(new String[] {"int"});
         mci.removeConstructor(constructor);
         assertEquals(2, mci.getDeclaredConstructors().length);
         
      }
      catch (ClassNotFoundException e)
      {
         e.printStackTrace();
      }
      
   }

}
