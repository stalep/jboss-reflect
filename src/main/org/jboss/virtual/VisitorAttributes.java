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
package org.jboss.virtual;

/**
 * Attributes used when visiting a virtual file system
 * 
 * @author <a href="adrian@jboss.com">Adrian Brock</a>
 * @version $Revision: 1.1 $
 */
public class VisitorAttributes
{
   /** The default attributes */
   public static final VisitorAttributes DEFAULT = new ImmutableVisitorAttributes();

   /** No directories */
   public static final VisitorAttributes NO_DIRECTORIES = new ImmutableVisitorAttributes(false, false);

   /** Recurse directories */
   public static final VisitorAttributes RECURSE_DIRECTORIES = new ImmutableVisitorAttributes(true, true);

   /** Recurse but don't visit directories */
   public static final VisitorAttributes RECURSE_NO_DIRECTORIES = new ImmutableVisitorAttributes(false, true);
   
   /** Whether to include the root */
   private boolean includeRoot = false;

   /** Whether to include directories */
   private boolean includeDirectories = true;

   /** Whether to recurse directories */
   private boolean recurseDirectories = false;

   /** Whether to ignore individual file errors */
   private boolean ignoreErrors = false;

   /** Whether to ignore hidden files */
   private boolean ignoreHidden = true;
   
   /**
    * Whether to include the directories<p>
    * 
    * Default: true
    * 
    * @return the includeDirectories.
    */
   public boolean isIncludeDirectories()
   {
      return includeDirectories;
   }

   /**
    * Set the includeDirectories.
    * 
    * @param includeDirectories the includeDirectories.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setIncludeDirectories(boolean includeDirectories)
   {
      this.includeDirectories = includeDirectories;
   }

   /**
    * Whether to recurse into directories<p>
    * 
    * Default: false
    * 
    * @return the recurseDirectories.
    */
   public boolean isRecurseDirectories()
   {
      return recurseDirectories;
   }

   /**
    * Set the recurseDirectories.
    * 
    * @param recurseDirectories the recurseDirectories.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setRecurseDirectories(boolean recurseDirectories)
   {
      this.recurseDirectories = recurseDirectories;
   }

   /**
    * Whether to include the root of the visit<p>
    * 
    * Default: false
    * 
    * @return the includeRoot.
    */
   public boolean isIncludeRoot()
   {
      return includeRoot;
   }

   /**
    * Set the includeRoot.
    * 
    * @param includeRoot the includeRoot.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setIncludeRoot(boolean includeRoot)
   {
      this.includeRoot = includeRoot;
   }

   /**
    * Whether to ignore individual errors<p>
    * 
    * Default: true
    * 
    * @return the ignoreErrors.
    */
   public boolean isIgnoreErrors()
   {
      return ignoreErrors;
   }

   /**
    * Set the ignoreErrors.
    * 
    * @param ignoreErrors the ignoreErrors.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setIgnoreErrors(boolean ignoreErrors)
   {
      this.ignoreErrors = ignoreErrors;
   }

   /**
    * Whether to ignore hidden files<p>
    * 
    * Default: true
    * 
    * @return the ignoreHidden.
    */
   public boolean isIgnoreHidden()
   {
      return ignoreHidden;
   }

   /**
    * Set the ignoreHidden.
    * 
    * @param ignoreHidden the ignoreHidden.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setIgnoreHidden(boolean ignoreHidden)
   {
      this.ignoreHidden = ignoreHidden;
   }

   /**
    * Immutable version of the attribues
    */
   private static class ImmutableVisitorAttributes extends VisitorAttributes
   {
      /**
       * Create a new ImmutableVirtualFileVisitorAttributes with default values
       */
      public ImmutableVisitorAttributes()
      {
      }
      
      /**
       * Create a new ImmutableVirtualFileVisitorAttributes.
       * 
       * @param includeDirectories whether to include directories 
       * @param recurseDirectories whether to recurse into directories
       */
      public ImmutableVisitorAttributes(boolean includeDirectories, boolean recurseDirectories)
      {
         super.setIncludeDirectories(includeDirectories);
         super.setRecurseDirectories(recurseDirectories);
      }
      
      @Override
      public void setIncludeDirectories(boolean includeDirectories)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setIncludeRoot(boolean includeRoot)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setRecurseDirectories(boolean recurseDirectories)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setIgnoreErrors(boolean ignoreErrors)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setIgnoreHidden(boolean ignoreHidden)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }
   }
}
