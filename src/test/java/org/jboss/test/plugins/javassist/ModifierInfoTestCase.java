package org.jboss.test.plugins.javassist;

import javassist.Modifier;

import org.jboss.reflect.spi.ModifierInfo;
import org.jboss.test.ContainerTest;

public class ModifierInfoTestCase extends ContainerTest
{

   public ModifierInfoTestCase(String name)
   {
      super(name);
   }
   
   public void testModifiers()
   {
      assertEquals(ModifierInfo.PUBLIC.getModifiers(), Modifier.PUBLIC);
      assertEquals(ModifierInfo.PRIVATE.getModifiers(), Modifier.PRIVATE);
      assertEquals(ModifierInfo.PROTECTED.getModifiers(), Modifier.PROTECTED);
      assertEquals(ModifierInfo.STATIC.getModifiers(), Modifier.STATIC);
      assertEquals(ModifierInfo.PUBLIC_STATIC.getModifiers(), Modifier.PUBLIC + Modifier.STATIC);
      
      System.out.println("Value of Public is: "+Modifier.PUBLIC);
      
      System.out.println("Value of Private is: "+Modifier.PRIVATE);
      
      for(ModifierInfo mi : ModifierInfo.values())
         System.out.println(mi.name()+": "+mi.getModifiers());
      
   }
   
   public void testNewModifiers()
   {
      assertEquals(Modifier.PRIVATE, ModifierInfo.getNewModifier(Modifier.PRIVATE).getModifiers());
   }

}
