package org.jboss.profileservice.spi;

import java.io.Serializable;

/**
 * The key for a Profile. It consists fo the domain, server and name.
 * 
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class ProfileKey
   implements Comparable, Serializable
{
   private static final long serialVersionUID = 1;
   /** The DEFAULT value for domain, server, name */
   public static final String DEFAULT = "default";

   /** The profile domain/cluster */
   private String domain;
   /** The server/node */
   private String server;
   /** The profile name */
   private String name;

   /**
    * Calls this this(DEFAULT, DEFAULT, name)
    * @param name - the profile name
    */
   public ProfileKey(String name)
   {
      this(DEFAULT, DEFAULT, name);
   }

   /**
    * Build a profile key from the domain, server and name. If any parameter
    * is null it defaulted to "default".
    * 
    * @param domain - the admin domain
    * @param server - the server instance name
    * @param name - the profile name
    */
   public ProfileKey(String domain, String server, String name)
   {
      if( domain == null )
         domain = DEFAULT;
      this.domain = domain;
      if( server == null )
         server = DEFAULT;
      this.server = server;
      if( name == null )
         name = DEFAULT;
      this.name = name;
   }

   /**
    * Compare based on domain, then server and finally name.
    * 
    * @param o - the ProfileKey instance to compare to
    * @return < 0, 0, > 0 based on whether this is less than, equal to
    *    or greater than o.
    */
   public int compareTo(Object o)
   {
      ProfileKey pk = (ProfileKey) o;
      int compareTo = domain.compareTo(pk.domain);
      if( compareTo == 0 )
      {
         compareTo = server.compareTo(pk.server);
         if( compareTo == 0 )
         {
            compareTo = name.compareTo(pk.name);
         }
      }
      return compareTo;
   }
   public boolean equals(Object o)
   {
      return compareTo(o) == 0;
   }
   public int hashCode()
   {
      int hash = domain.hashCode() + server.hashCode() + name.hashCode();
      return hash;
   }
}
