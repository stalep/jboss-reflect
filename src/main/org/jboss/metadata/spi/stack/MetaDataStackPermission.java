/*
* JBoss, Home of Professional Open Source
* Copyright 2006, JBoss Inc., and individual contributors as indicated
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
package org.jboss.metadata.spi.stack;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.BasicPermission;
import java.security.Permission;
import java.security.PermissionCollection;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;

/** 
 * This permission represents "trust" in a signer or codebase.
 * 
 * It contains a target name but no actions list. The targets are
 * <ul>
 * <li> modify - whether the caller can push/pop onto the stack
 * <li> peek - whether the caller can peek onto the stack
 * <li> * - all
 * </ul>
 * 
 * @author adrian@jboss.com
 * @version $Revision: 57306 $
 */
public class MetaDataStackPermission extends BasicPermission
{
   /** For serialization */
   private static final long serialVersionUID = 5661980843569388590L;

   /** The modify permission */
   static MetaDataStackPermission MODIFY = new MetaDataStackPermission("modify");

   /** The peek permission */
   static MetaDataStackPermission PEEK = new MetaDataStackPermission("peek");
   
   /** Whether we have all names */
   private transient boolean allNames;

   /** 
    * Create a new Permission
    * 
    * @param name the target
    * @throws IllegalArgumentException for invalid name 
    * @throws NullPointerException for null name
    */ 
   public MetaDataStackPermission(String name)
   {
      this(name, null);
   }

   /** 
    * Create a new Permission
    * 
    * @param name the target
    * @param actions the actions
    * @throws IllegalArgumentException for an invalid name or target 
    * @throws NullPointerException for null name
    */ 
   public MetaDataStackPermission(String name, String actions)
   {
      super(name, actions);
      init(name, actions);
   }

   /**
    * @return human readable string.
    */
   public String toString()
   {
      StringBuffer buffer = new StringBuffer(100);
      buffer.append(getClass().getName()).append(":");
      buffer.append(" name=").append(getName());
      buffer.append(" actions=").append(getActions());
      return buffer.toString();
   }

   /**
    * Checks if this KernelPermission object "implies" the specified
    * permission. More specifically, this method returns true if:
    * p is an instance of KernelPermission,
    * p's target names are a subset of this object's target names
    * 
    * The configure permission implies the access permission.
    * 
    * @param p the permission
    * @return true when the permission is implied
    */ 
   public boolean implies(Permission p)
   {
      if( (p instanceof MetaDataStackPermission) == false)
         return false;

      return (allNames == true);
   }

   /** 
    * Must override to handle the configure implies access relationship.
    * 
    * @return the permission collection
    */ 
   public PermissionCollection newPermissionCollection()
   {
      return new MetaDataStackPermissionCollection();
   }

   private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException
   {
      ois.defaultReadObject();
      init(getName(), getActions());
   }

   /**
    * Construct a new KernelPermission for a given name
    *
    * @param name the name of the permission to grant
    * @param actions unused
    * @exception NullPointerException if the name is null
    * @exception IllegalArgumentException if the name is not * or one of the
    * allowed names or a comma-separated list of the allowed names, or if
    * actions is a non-null non-empty string.
    */
   private void init(String name, String actions)
   {
      if( name == null )
         throw new NullPointerException("name cannot be null");

      if( actions != null && actions.length() > 0 )
         throw new IllegalArgumentException("actions must be null or empty");

      if (name.equals("*") == false &&
          name.equals("modify") == false &&
          name.equals("peek") == false)
         throw new IllegalArgumentException("Unknown name: " + name);
      allNames = name.equals("*");
   }

   /**
    * A MetaDataStackPermissionCollection.
    */
   class MetaDataStackPermissionCollection extends PermissionCollection
   {
      /** The serialVersionUID */
      private static final long serialVersionUID = 3256442516797665329L;

      /** The permissions */
      private HashSet<Permission> permissions = new HashSet<Permission>();
      
      /** Whether we have all permissions */
      private boolean hasAll;

      public void add(Permission p)
      {
         if (isReadOnly())
            throw new SecurityException("Collection is read-only");
         if (p instanceof MetaDataStackPermission)
            permissions.add(p);
         if (p.getName().equals("*"))
            hasAll = true;
      }

      public boolean implies(Permission p)
      {
         boolean implies = false;
         if (p instanceof MetaDataStackPermission)
         {
            implies = hasAll;
            if (implies == false)
               implies = permissions.contains(p);
         }
         return implies;
      }

      public Enumeration<Permission> elements()
      {
         final Iterator<Permission> iter = permissions.iterator();
         return new Enumeration<Permission>()
         {
            public boolean hasMoreElements()
            {
               return iter.hasNext();
            }

            public Permission nextElement()
            {
               return iter.next();
            }
         };
      }
   }
}