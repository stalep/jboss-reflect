/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.vfs;

import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.jar.Manifest;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.io.FileInputStream;

/**
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class JarTraversal
{
   static String[] jarSuffixes = {
      ".jar", ".zip", ".ear", ".war", ".sar"
   };
   static String jarPrefix = "+- ";
   static String entryPrefix = "|  ";
   static byte[] buffer = new byte[65535];
   static boolean showOnlyArchives = false;

   static
   {
      for(int i = 0; i < 10; i ++)
      {
         jarPrefix += "+- ";
         entryPrefix += "|  ";
      }
   }

   public static boolean isJar(String name)
   {
      boolean isJar = false;
      for(int s = 0; isJar == false && s < jarSuffixes.length; s ++)
      {
         String suffix = jarSuffixes[s];
         isJar |= name.endsWith(suffix);
      }
      return isJar;
   }

   static void processEntry(ZipInputStream zis, ZipEntry entry, int level) throws Exception
   {
      String name = entry.getName();
      boolean isDirectory = entry.isDirectory();
      if( isDirectory == true )
         return;

      // See if this is a jar archive
      if( JarTraversal.isJar(name) )
      {
         System.out.print(jarPrefix.substring(0, 3*level));
         System.out.println(name+" (archive)");
         try
         {
            ZipInputStream entryZIS = new ZipInputStream(zis);
            processJar(entryZIS, ++ level);
         }
         catch(Exception e)
         {
            e.printStackTrace();
         }
      }
      else if( name.endsWith("MANIFEST.MF") )
      {
         System.out.print(entryPrefix.substring(0, 3*(level-1)));
         System.out.print("+- ");
         System.out.print(name);
         Manifest mf = new Manifest(zis);
         Attributes main = mf.getMainAttributes();

         String cp = main.getValue(Attributes.Name.CLASS_PATH);
         if( cp != null )
         {
            System.out.print(" Class-Path: ");
            System.out.print(cp);
            System.out.print(' ');
         }
         System.out.println();
      }
      else if( showOnlyArchives == false )
      {
         System.out.print(entryPrefix.substring(0, 3*(level-1)));
         System.out.print("+- ");
         System.out.println(name);
      }
   }

   static void processJar(ZipInputStream zis, int level) throws Exception
   {
      ZipEntry entry;
      while( (entry = zis.getNextEntry()) != null )
      {
         processEntry(zis, entry, level);
      }
   }

   /** List the jar contents
    * @param args the command line arguments
    */
   public static void main(String[] args) throws Exception
   {
      String name = args[0];
      JarFile jarFile = new JarFile(name);
      Manifest mf = jarFile.getManifest();
      FileInputStream fis = new FileInputStream(name);
      ZipInputStream zis = new ZipInputStream(fis);
      System.out.println(name);
      processJar(zis, 1);
      System.out.println("Done");
   }

}
