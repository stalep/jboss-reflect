/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.classadapter.plugins.reflect;

import java.util.List;

import org.jboss.classadapter.spi.ClassAdapter;
import org.jboss.joinpoint.plugins.reflect.ReflectJoinpointFactory;
import org.jboss.joinpoint.spi.JoinpointFactory;
import org.jboss.reflect.spi.ClassInfo;
import org.jboss.util.JBossObject;

/**
 * A reflected class adapter.
 * 
 * @author <a href="mailto:adrian@jboss.org">Adrian Brock</a>
 */
public class ReflectClassAdapter extends JBossObject implements ClassAdapter
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The class info */
   protected ClassInfo classInfo;
   
   // Static --------------------------------------------------------

   // Constructors --------------------------------------------------

   /**
    * Create a new reflected class adapter
    * 
    * @param classInfo the class info
    */
   public ReflectClassAdapter(ClassInfo classInfo)
   {
      this.classInfo = classInfo;
   }
   
   // Public --------------------------------------------------------

   // ClassAdapter implementation -----------------------------------

   public ClassInfo getClassInfo()
   {
      return classInfo;
   }

   public ClassAdapter getInstanceAdapter(ClassInfo classInfo)
   {
      ReflectClassAdapter clone = (ReflectClassAdapter) clone();
      clone.classInfo = classInfo;
      return clone;
   }

   public List getDependencies()
   {
      return null;
   }

   public JoinpointFactory getJoinpointFactory()
   {
      return new ReflectJoinpointFactory(classInfo);
   }

   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------

   // Private -------------------------------------------------------

   // Inner classes -------------------------------------------------
}
