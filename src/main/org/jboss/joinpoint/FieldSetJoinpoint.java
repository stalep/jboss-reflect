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
public interface FieldSetJoinpoint extends Joinpoint
{
   Object getTarget();
   void setTarget(Object target);
   void setValue();
}
