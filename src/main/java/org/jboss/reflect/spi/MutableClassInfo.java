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
package org.jboss.reflect.spi;

/**
 * A MutableClassInfo.
 * 
 * @author <a href="stale.pedersen@jboss.org">Stale W. Pedersen</a>
 * @version $Revision: 1.1 $
 */
public interface MutableClassInfo extends ClassInfo
{
   
   /**
    * Compiles the code included in Body and returns a MutableMethodInfo representation of it.
    * The Body must include the whole declaration of the method.
    * 
    * @param body
    * @return
    */
   MutableMethodInfo createMutableMethod(Body body);
   
   /**
    * Compiles an empty method with the signature given by the params.
    * 
    * @param modifier
    * @param name
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableMethodInfo createMutableMethod(ModifierInfo modifier, String name, 
         String[] parameters, String[] exceptions);
   
   /**
    * Compiles an empty method with the signature given by the params.
    * 
    * @param modifier
    * @param name
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableMethodInfo createMutableMethod(ModifierInfo modifier, String name, 
         ClassInfo[] parameters, ClassInfo[] exceptions);
   
   
   /**
    * Compile a method with the signature and body given by the params.
    * Note that the source code of the body must be surrounded by <code>{}</code>.
    * 
    * @param modifier
    * @param name
    * @param body
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableMethodInfo createMutableMethod(ModifierInfo modifier, String name, Body body, 
         String[] parameters, String[] exceptions);

 
   /**
    * Compile a method with the signature and body given by the params.
    * Note that the source code of the body must be surrounded by <code>{}</code>.
    * 
    * @param modifier
    * @param name
    * @param body
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableMethodInfo createMutableMethod(ModifierInfo modifier, String name, Body body,
         ClassInfo[] parameters, ClassInfo[] exceptions);

   
   /**
    * Compiles the code included in the Body parameter and returns a MutableConstructorInfo
    * representation of it. The Body must include the whole declaration.
    * The 
    * 
    * @param body
    * @return
    */
   MutableConstructorInfo createMutableConstructor(Body body);
   
   /**
    * Creates an emptry constructor with params given.
    * 
    * @param modifier
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, String[] parameters, 
         String[] exceptions);
   
   /**
    * Creates an emptry constructor with params given.
    * 
    * @param modifier
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, ClassInfo[] parameters, 
         ClassInfo[] exceptions);
   
   /**
    * Create a constructor with the params given.
    * Note that the source text of the Body must be surrounded by <code>{}</code>.
    * 
    * @param modifier
    * @param body
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, Body body,
         String[] parameters, String[] exceptions);
   
   /**
    * Create a constructor with the params given.
    * Note that the source text of the Body must be surrounded by <code>{}</code>.
    * 
    * @param modifier
    * @param body
    * @param parameters
    * @param exceptions
    * @return
    */
   MutableConstructorInfo createMutableConstructor(ModifierInfo modifier, Body body,
         ClassInfo[] parameters, ClassInfo[] exceptions);
   
   /**
    * Create a field connected to this class with the params given.
    * 
    * @param modifier
    * @param type
    * @return
    */
   MutableFieldInfo createMutableField(ModifierInfo modifier, String type);
   
   /**
    * Create a field connected to this class with the params given.
    * 
    * @param modifier
    * @param type
    * @return
    */
   MutableFieldInfo createMutableField(ModifierInfo modifier, ClassInfo type);
   
   /**
    * Add a method
    * 
    * @param mmi
    */
   void addMethod(MutableMethodInfo mmi);
   
   /**
    * Remove the specified method from this class
    * 
    * @param mmi
    */
   void removeMethod(MutableMethodInfo mmi);
   
   /**
    * Add a constructor
    * 
    * @param mci
    */
   void addConstructor(MutableConstructorInfo mci);
   
   /**
    * Remove the specified constructor from this class
    * 
    * @param mci
    */
   void removeConstructor(MutableConstructorInfo mci);
   
   /**
    * Add a field
    * 
    * @param mfi
    */
   void addField(MutableFieldInfo mfi);
   
   /**
    * Remove a field
    * 
    * @param mfi
    */
   void removeField(MutableFieldInfo mfi);
   
   /**
    * Converts the class to a Class file. 
    * After this method is called, no modifications to the class is allowed.
    * 
    * @return
    */
   byte[] toByteCode();
   
}