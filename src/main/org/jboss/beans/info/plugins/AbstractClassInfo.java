/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.util.Set;

import org.jboss.beans.info.spi.ClassInfo;
import org.jboss.util.JBossObject;

/**
 * Class info.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractClassInfo extends AbstractTypeInfo implements ClassInfo
{
   // Constants -----------------------------------------------------

   /** The VOID class information */
   public static final ClassInfo VOID = new AbstractClassInfo(Void.TYPE.getName());
   
   // Attributes ----------------------------------------------------
   
   /** The super class info */
   protected ClassInfo superClassInfo;
   
   /** The constructors */
   protected Set constructors;

   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------
   
   /**
    * Create a new class info
    * 
    * @param name the class name
    */
   public AbstractClassInfo(String name)
   {
      super(name);
   }
   
   /**
    * Create a new class info
    * 
    * @param name the class name
    * @param superInterfaces the super interfaces
    */
   public AbstractClassInfo(String name, Set superInterfaces, Set attributes, 
         Set methods, Set constructors, ClassInfo superClassInfo)
   {
      super(name, superInterfaces, attributes, methods);
      this.constructors = constructors;
      this.superClassInfo = superClassInfo;
   }
   
   // Public --------------------------------------------------------

   // ClassInfo implementation --------------------------------------

   public ClassInfo getSuperClassInfo()
   {
      return superClassInfo;
   }

   public void setSuperClassInfo(ClassInfo superClassInfo)
   {
      this.superClassInfo = superClassInfo;
   }

   public Set getConstructors()
   {
      return constructors;
   }

   public void setConstructors(Set constructors)
   {
      this.constructors = constructors;
   }
   
   // Package protected ---------------------------------------------

   // JBossObject overrides ------------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      super.toString(buffer);
      if (superClassInfo != null)
      {
         buffer.append(" superClassInfo=");
         superClassInfo.toShortString(buffer);
      }
      buffer.append(" constructors=");
      JBossObject.list(buffer, constructors);
   }

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
