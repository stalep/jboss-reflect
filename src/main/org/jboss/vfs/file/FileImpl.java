/*
* JBoss, the OpenSource J2EE webOS
*
* Distributable under LGPL license.
* See terms of license at gnu.org.
*/
package org.jboss.vfs.file;

import org.jboss.vfs.spi.VirtualFile;
import org.jboss.vfs.spi.VirtualFileFilter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectStreamField;
import java.io.Serializable;
import java.io.ObjectInputStream.GetField;
import java.io.ObjectOutputStream.PutField;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/** A java.io.File based implementation of VirtualFile
 *
 * @author Scott.Stark@jboss.org
 * @version $Revision$
 */
public class FileImpl
   implements VirtualFile, Serializable
{
   private static final long serialVersionUID = 1;
   /** The class serial fields */
   private static final ObjectStreamField[] serialPersistentFields = {
      new ObjectStreamField("rootURL", URL.class),
      new ObjectStreamField("path", URL.class),
      new ObjectStreamField("vfsPath", String.class)
   };

   private URL path;
   private String vfsPath;
   private transient FileSystemVFS vfs;
   private transient File file;
   private transient VirtualFile[] children;
   private transient List<VirtualFile> recursiveChildren;
   private transient InputStream contentIS;

   public FileImpl(URL path, String vfsPath, FileSystemVFS vfs)
   {
      this.path = path;
      this.vfsPath = vfsPath;
      this.file = new File(path.getPath());
      this.vfs = vfs;
   }
   public FileImpl(File file, String vfsPath, FileSystemVFS vfs) throws MalformedURLException
   {
      this.path = file.toURL();
      this.vfsPath = vfsPath;
      this.file = file;
      this.vfs = vfs;
   }

   public String getName()
   {
      return file.getName();
   }
   public String getPathName()
   {
      return vfsPath;
   }

   public VirtualFile[] getChildren()
      throws IOException
   {
      if( isDirectory() && children == null )
      {
         File[] listing = file.listFiles();
         ArrayList<VirtualFile> tmp = new ArrayList<VirtualFile>();
         for(File f : listing)
         {
            VirtualFile child = getChild(f.getName());
            tmp.add(child);
         }
         children = new VirtualFile[tmp.size()];
         tmp.toArray(children);
      }
      return children;
   }

   public List<VirtualFile> getChildrenRecursively() throws IOException
   {
      if (isDirectory() && recursiveChildren == null)
      {
         recursiveChildren = new ArrayList<VirtualFile>();
         getChildren();
         if (children != null  && children.length > 0)
         {
            for (VirtualFile vf : children)
            {
               recursiveChildren.add(vf);
               if (vf.isDirectory() && !vf.isArchive())
               {
                  recursiveChildren.addAll(vf.getChildrenRecursively());
               }
            }
         }
      }
      return recursiveChildren;
   }

   public List<VirtualFile> getChildrenRecursively(VirtualFileFilter filter) throws IOException
   {
      getChildrenRecursively();
      ArrayList<VirtualFile> filtered = new ArrayList<VirtualFile>();
      for (VirtualFile vf : recursiveChildren)
      {
         if (filter.accepts(vf)) filtered.add(vf);
      }
      return filtered;
   }

   public VirtualFile findChild(String name)
      throws IOException
   {
      VirtualFile child = getChild(name);
      if( child == null )
      {
         String path = file.getAbsolutePath();
         throw new FileNotFoundException(name+", not found under: "+path);
      }
      return child;
   }

   // Convience attribute accessors
   public long getLastModified()
   {
      return file.lastModified();
   }

   public long getSize()
   {
      return file.length();
   }

   public boolean isDirectory()
   {
      return file.isDirectory();
   }

   public boolean isArchive()
   {
      return file.isDirectory() && JarImpl.isJar(getName());
   }

   public boolean isFile()
   {
      return file.isFile();
   }

   public InputStream openStream()
      throws IOException
   {
      if( contentIS == null )
      {
         contentIS = path.openStream();
      }
      return contentIS;
   }

   public void close()
      throws IOException
   {
      if( contentIS != null )
         contentIS.close();
   }

   public URL toURL()
   {
      return path;
   }

   private VirtualFile getChild(String name)
      throws IOException
   {
      VirtualFile child = vfs.getChild(this.path, name);
      return child;
   }

   private void writeObject(ObjectOutputStream out)
      throws IOException
   {
      // Write out the serialPersistentFields
      PutField fields = out.putFields();
      fields.put("rootURL", vfs.getRootURL());
      fields.put("path", path);
      fields.put("vfsPath", vfsPath);
      out.writeFields();
   }
   private void readObject(ObjectInputStream in)
      throws IOException, ClassNotFoundException
   {
      // Read in the serialPersistentFields
      GetField fields = in.readFields();
      URL rootURL = (URL) fields.get("rootURL", null);
      this.path = (URL) fields.get("path", null);
      this.vfsPath = (String) fields.get("vfsPath", null);
      // Initialize the transient values
      this.file = new File(path.getPath());
      this.vfs = FileSystemVFS.getFileSystemVFS(rootURL);
   }
}
