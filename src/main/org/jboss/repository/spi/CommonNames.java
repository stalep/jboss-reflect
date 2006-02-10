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
package org.jboss.repository.spi;

/**
 @author Scott.Stark@jboss.org
 @version $Revision$ */
public interface CommonNames
{
   public int DOMAIN_LEVEL = 0;
   public int CLUSTER_LEVEL = 1;
   public int SERVER_LEVEL = 2;
   public int APPLICATION_LEVEL = 3;
   public int DEPLOYMENT_LEVEL = 4;
   public int SESSION_LEVEL = 5;
   public int THREAD_LEVEL = 6;
   public int REQUEST_LEVEL = 7;
   public int N_LEVELS = 8;
   
   public static final String REQUEST = "REQUEST";
   public static final String THREAD = "THREAD";
   public static final String SESSION = "SESSION";
   public static final String DEPLOYMENT = "DEPLOYMENT";
   public static final String APPLICATION = "APPLICATION";
   public static final String SERVER = "SERVER";
   public static final String CLUSTER = "CLUSTER";
   public static final String DOMAIN = "DOMAIN";
   public static final String[] LEVELS = {
      DOMAIN, CLUSTER, SERVER, APPLICATION, DEPLOYMENT, SESSION, THREAD, REQUEST
   };
}
