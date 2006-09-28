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

   /** Leaves only */
   public static final VisitorAttributes LEAVES_ONLY = new ImmutableVisitorAttributes(true, false);

   /** Recurse */
   public static final VisitorAttributes RECURSE = new ImmutableVisitorAttributes(false, true);

   /** Recurse but only visit leaves */
   public static final VisitorAttributes RECURSE_LEAVES_ONLY = new ImmutableVisitorAttributes(true, true);
   
   /** Whether to include the root */
   private boolean includeRoot = false;

   /** Whether to only visit leaves */
   private boolean leavesOnly = false;

   /** Whether to recurse */
   private boolean recurse = false;

   /** Whether to ignore individual file errors */
   private boolean ignoreErrors = false;

   /** Whether to include hidden files */
   private boolean includeHidden = false;
   
   /**
    * Whether to visit leaves only<p>
    * 
    * Default: false
    * 
    * @return the visit leaves only.
    */
   public boolean isLeavesOnly()
   {
      return leavesOnly;
   }

   /**
    * Set the leaves only.
    * 
    * @param leavesOnly the leaves only
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setLeavesOnly(boolean leavesOnly)
   {
      this.leavesOnly = leavesOnly;
   }

   /**
    * Whether to recurse<p>
    * 
    * Default: false
    * 
    * @return the recurse.
    */
   public boolean isRecurse()
   {
      return recurse;
   }

   /**
    * Set the recurse.
    * 
    * @param recurse the recurse.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setRecurse(boolean recurse)
   {
      this.recurse = recurse;
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
    * Default: false
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
    * Whether to include hidden files<p>
    * 
    * Default: false
    * 
    * @return the includeHidden.
    */
   public boolean isIncludeHidden()
   {
      return includeHidden;
   }

   /**
    * Set the includeHidden.
    * 
    * @param includeHidden the includeHidden.
    * @throws IllegalStateException if you attempt to modify one of the preconfigured static values of this class
    */
   public void setIncludeHidden(boolean includeHidden)
   {
      this.includeHidden = includeHidden;
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
       * @param leavesOnly whether to visit leaves only 
       * @param recurse whether to recurse
       */
      public ImmutableVisitorAttributes(boolean leavesOnly, boolean recurse)
      {
         super.setLeavesOnly(leavesOnly);
         super.setRecurse(recurse);
      }
      
      @Override
      public void setLeavesOnly(boolean leavesOnly)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setIncludeRoot(boolean includeRoot)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setRecurse(boolean recurse)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setIgnoreErrors(boolean ignoreErrors)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }

      @Override
      public void setIncludeHidden(boolean includeHidden)
      {
         throw new IllegalStateException("The preconfigured attributes are immutable");
      }
   }
}
