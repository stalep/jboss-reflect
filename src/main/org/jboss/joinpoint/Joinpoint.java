/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.joinpoint;

/**
 * comment
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 */
public interface Joinpoint extends Cloneable
{
   /**
    * Invoke on the actual joinpoint
    *
    * @return
    * @throws Throwable
    */
   Object dispatch() throws Throwable;

   /**
    * Make a copy of the joinpoint
    * @return
    */
   Object clone();

   /**
    * A human readable version of the join point
    *
    * @return a human readable description of the join point
    */
   String toHumanReadableString();

}
