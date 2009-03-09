package org.jboss.test.plugins.javassist;

import org.jboss.reflect.plugins.javassist.JavassistTypeInfoFactoryImpl;
import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.MutableFieldInfo;
import org.jboss.test.ContainerTest;

public class JavassistFieldInfoTestCase extends ContainerTest
{

   public JavassistFieldInfoTestCase(String name)
   {
      super(name);
   }
   
   public void testAddField()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.PojoField", null);
      MutableFieldInfo foo = mci.getDeclaredField("foo");
      assertEquals("java.lang.String", foo.getType().getName());
      assertEquals(ModifierInfo.PUBLIC, foo.getModifiers());
      MutableFieldInfo mfi1 = mci.createMutableField(ModifierInfo.PUBLIC, "java.lang.String", "test1");
      mci.addField(mfi1);
      assertEquals(2, mci.getDeclaredFields().length);
   }
   
   public void testRemoveField()
   {
      MutableClassInfo mci = new JavassistTypeInfoFactoryImpl().getMutable("org.jboss.test.plugins.javassist.PojoField", null);
      MutableFieldInfo foo = mci.getDeclaredField("foo");
      mci.removeField(foo);
      assertEquals(1, mci.getDeclaredFields().length);
      
   }

}
