/*
 * JBoss, the OpenSource J2EE webOS
 * 
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.beans.info.plugins;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.jboss.beans.info.spi.BeanInfo;
import org.jboss.beans.info.spi.ClassInfo;
import org.jboss.beans.info.spi.ConstructorInfo;
import org.jboss.beans.info.spi.MethodInfo;
import org.jboss.util.JBossObject;

/**
 * BeanInfo.
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision$
 */
public class AbstractBeanInfo extends JBossObject implements BeanInfo
{
   // Constants -----------------------------------------------------

   // Attributes ----------------------------------------------------

   /** The class name */
   protected String name;
   
   /** The class info */
   protected ClassInfo classInfo;
   
   /** The attributes */
   protected Set attributes;
   
   /** The constructors */
   protected Set constructors;
   
   /** The methods */
   protected Set methods;
   
   /** The events */
   protected Set events;
   
   // Static --------------------------------------------------------
   
   // Constructors --------------------------------------------------

   /**
    * Create a new bean info
    * 
    * @param classInfo the class info
    */
   public AbstractBeanInfo(ClassInfo classInfo)
   {
      this(classInfo, Collections.EMPTY_SET);
   }

   /**
    * Create a new bean info
    * 
    * @param classInfo the class info
    * @param events the events
    */
   public AbstractBeanInfo(ClassInfo classInfo, Set events)
   {
      this(classInfo, classInfo.getAttributes(), classInfo.getConstructors(),
            classInfo.getMethods(), events);
   }

   /**
    * Create a new bean info
    * 
    * @param classInfo the class info
    * @param attributes the attributes
    * @param constructors the constructors
    * @param methods the methods
    * @param events the events
    */
   public AbstractBeanInfo(ClassInfo classInfo, Set attributes, Set constructors,
         Set methods, Set events)
   {
      this.name = classInfo.getName();
      this.classInfo = classInfo;
      this.attributes = attributes;
      this.constructors = new HashSet();
      if (constructors != null && constructors.isEmpty() == false)
      {
         for (Iterator i = constructors.iterator(); i.hasNext();)
         {
            ConstructorInfo cinfo = (ConstructorInfo) i.next();
            if (cinfo.isPublic() && cinfo.isStatic() == false)
               this.constructors.add(cinfo);
         }
      }
      this.methods = new HashSet();
      if (methods.isEmpty() == false)
      {
         for (Iterator i = methods.iterator(); i.hasNext();)
         {
            MethodInfo minfo = (MethodInfo) i.next();
            if (minfo.isPublic() && minfo.isStatic() == false)
               this.methods.add(minfo);
         }
      }
      this.events = events;
   }
   
   // Public --------------------------------------------------------

   // BeanInfo implementation ---------------------------------------

   public String getName()
   {
      return name;
   }
   
   public Set getAttributes()
   {
      return attributes;
   }
   
   public void setAttributes(Set attributes)
   {
      this.attributes = attributes;
   }
   
   public ClassInfo getClassInfo()
   {
      return classInfo;
   }

   public void setClassInfo(ClassInfo classInfo)
   {
      this.classInfo = classInfo;
   }
   
   public Set getConstructors()
   {
      return constructors;
   }

   public void setConstructors(Set constructors)
   {
      this.constructors = constructors;
   }
   
   public Set getEvents()
   {
      return events;
   }

   public void setEvents(Set events)
   {
      this.events = events;
   }
   
   public Set getMethods()
   {
      return methods;
   }

   public void setMethods(Set methods)
   {
      this.methods = methods;
   }

   // Object overrides -----------------------------------------------
   
   public boolean equals(Object object)
   {
      if (object == null || object instanceof AbstractBeanInfo == false)
         return false;
      
      AbstractBeanInfo other = (AbstractBeanInfo) object;
      if (notEqual(name, other.name))
         return false;
      else if (notEqual(classInfo, other.classInfo))
         return false;
      else if (notEqual(attributes, other.attributes))
         return false;
      else if (notEqual(methods, other.methods))
         return false;
      else if (notEqual(constructors, other.constructors))
         return false;
      else if (notEqual(events, other.events))
         return false;
      return true;
   }

   // JBossObject overrides ------------------------------------------
   
   public void toString(StringBuffer buffer)
   {
      buffer.append("name=").append(name);
      buffer.append(" classInfo=");
      classInfo.toShortString(buffer);
      buffer.append(" attributes=");
      JBossObject.list(buffer, attributes);
      buffer.append(" methods=");
      JBossObject.list(buffer, methods);
      buffer.append(" constructors=");
      JBossObject.list(buffer, constructors);
      buffer.append(" events=");
      JBossObject.list(buffer, events);
   }
   
   public void toShortString(StringBuffer buffer)
   {
      buffer.append(name);
   }
   
   public int getHashCode()
   {
      return name.hashCode();
   }
   
   // Package protected ---------------------------------------------

   // Protected -----------------------------------------------------
   
   // Private -------------------------------------------------------
   
   // Inner classes -------------------------------------------------
}
