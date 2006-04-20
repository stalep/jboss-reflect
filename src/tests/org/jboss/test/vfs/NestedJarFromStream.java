/*
 * Copyright (c) 2005 Your Corporation. All Rights Reserved.
 */
package org.jboss.test.vfs;

import java.util.jar.JarInputStream;
import java.util.jar.JarEntry;
import java.util.HashMap;
import java.util.Iterator;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class NestedJarFromStream
{
   private JarInputStream jis;
   private HashMap<String, JarEntryContents> entries = new HashMap<String, JarEntryContents>();
   private boolean inited;

   NestedJarFromStream(JarInputStream jis)
   {
      this.jis = jis;
   }

   public int size()
   {
      return entries.size();
   }

   public Iterator<JarEntryContents> getEntries()
      throws IOException
   {
      if( inited == false )
         init();
      return entries.values().iterator();
   }
   public JarEntryContents getEntry(String name)
      throws IOException
   {
      if( inited == false )
         init();
      JarEntryContents jec = entries.get(name);
      return jec;
   }
   public JarEntry getJarEntry(String name)
      throws IOException
   {
      if( inited == false )
         init();
      JarEntryContents jec = entries.get(name);
      JarEntry entry = (jec != null ? jec.getEntry() : null);
      return entry;
   }
   public byte[] getContents(String name)
      throws IOException
   {
      if( inited == false )
         init();
      JarEntryContents jec = entries.get(name);
      byte[] contents = (jec != null ? jec.getContents() : null);
      return contents;
   }

   public void close()
      throws IOException
   {
      entries.clear();
      if( jis != null )
         jis.close();
   }

   protected void init()
      throws IOException
   {
      inited = true;
      JarEntry entry = jis.getNextJarEntry();
      while( entry != null )
      {
         JarEntryContents jec = new JarEntryContents(entry, jis);
         entries.put(entry.getName(), jec);
         entry = jis.getNextJarEntry();
      }
      jis.close();
      jis = null;
   }

   public static class JarEntryContents
   {
      private JarEntry entry;
      private byte[] contents;

      JarEntryContents(JarEntry entry, JarInputStream jis)
         throws IOException
      {
         this.entry = entry;
         int size = (int) entry.getSize();
         if( size <= 0 )
            return;

         ByteArrayOutputStream baos = new ByteArrayOutputStream(size);
         byte[] tmp = new byte[1024];
         while( jis.available() > 0 )
         {
            int length = jis.read(tmp);
            if( length > 0 )
               baos.write(tmp, 0, length);
         }
         contents = baos.toByteArray();
      }
      public JarEntry getEntry()
      {
         return entry;
      }
      public byte[] getContents()
      {
         return contents;
      }
      public String toString()
      {
         StringBuffer tmp = new StringBuffer(super.toString());
         tmp.append('[');
         tmp.append("name=");
         tmp.append(entry.getName());
         tmp.append(",size=");
         tmp.append(entry.getSize());
         tmp.append(",time=");
         tmp.append(entry.getTime());
         tmp.append(']');
         return tmp.toString();
      }
   }
}
