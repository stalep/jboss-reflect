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
public interface MethodJoinpoint extends Joinpoint
{
   public void setArguments(Object[] args);
   public Object[] getArguments();

   Object getTarget();
   public void setTarget(Object obj);
}
