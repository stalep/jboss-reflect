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
      
   }
   
   public void testNewModifiers()
   {
      assertEquals(Modifier.PRIVATE, ModifierInfo.getNewModifier(Modifier.PRIVATE).getModifiers());
   }
   
   public void testIsPublic()
   {
      assertTrue(ModifierInfo.PUBLIC.isPublic());
      assertTrue(ModifierInfo.PUBLIC_ABSTRACT_ANNOTATION.isPublic());
      assertTrue(ModifierInfo.PUBLIC_ABSTRACT_INTERFACE.isPublic());
      assertFalse(ModifierInfo.STATIC.isPublic());
      assertFalse(ModifierInfo.PRIVATE_CONSTANT_SYNTHETIC.isPublic());
      assertFalse(ModifierInfo.PRIVATE_CONSTANT_ENUM.isPublic());
   }
   
   public void testIsStatic()
   {
      assertTrue(ModifierInfo.STATIC.isStatic());
      assertTrue(ModifierInfo.PUBLIC_STATIC.isStatic());
      assertTrue(ModifierInfo.PROTECTED_STATIC_TRANSIENT.isStatic());
      assertFalse(ModifierInfo.PROTECTED_ABSTRACT_INTERFACE_ANNOTATION.isStatic());
      assertFalse(ModifierInfo.PACKAGE_ABSTRACT.isStatic());
         
   }

}
