/*
 * JBoss, the OpenSource J2EE webOS
 *
 * Distributable under LGPL license.
 * See terms of license at gnu.org.
 */
package org.jboss.reflect.spi;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.security.DigestOutputStream;
import java.security.MessageDigest;

/**
 * Create a unique hash for MethodInfo
 *
 * @author <a href="mailto:bill@jboss.org">Bill Burke</a>
 * @version $Revision$
 */
public class MethodInfoHashing
{
   public static long methodHash(MethodInfo method)
           throws Exception
   {
      TypeInfo[] parameterTypes = method.getParameterTypes();
      String methodDesc = method.getName() + "(";
      for (int j = 0; j < parameterTypes.length; j++)
      {
         methodDesc += getTypeString(parameterTypes[j]);
      }
      methodDesc += ")" + getTypeString(method.getReturnType());
      return createHash(methodDesc);
   }

   public static long createHash(String methodDesc)
           throws Exception
   {
      long hash = 0;
      ByteArrayOutputStream bytearrayoutputstream = new ByteArrayOutputStream(512);
      MessageDigest messagedigest = MessageDigest.getInstance("SHA");
      DataOutputStream dataoutputstream = new DataOutputStream(new DigestOutputStream(bytearrayoutputstream, messagedigest));
      dataoutputstream.writeUTF(methodDesc);
      dataoutputstream.flush();
      byte abyte0[] = messagedigest.digest();
      for (int j = 0; j < Math.min(8, abyte0.length); j++)
         hash += (long) (abyte0[j] & 0xff) << j * 8;
      return hash;

   }

   static String getTypeString(TypeInfo cl)
   {

      if (cl == PrimitiveInfo.BYTE)
      {
         return "B";
      }
      else if (cl == PrimitiveInfo.CHAR)
      {
         return "C";
      }
      else if (cl == PrimitiveInfo.DOUBLE)
      {
         return "D";
      }
      else if (cl == PrimitiveInfo.FLOAT)
      {
         return "F";
      }
      else if (cl == PrimitiveInfo.INT)
      {
         return "I";
      }
      else if (cl == PrimitiveInfo.LONG)
      {
         return "J";
      }
      else if (cl == PrimitiveInfo.SHORT)
      {
         return "S";
      }
      else if (cl == PrimitiveInfo.BOOLEAN)
      {
         return "Z";
      }
      else if (cl == PrimitiveInfo.VOID)
      {
         return "V";
      }
      else if (cl instanceof ArrayInfo)
      {
         ArrayInfo ai = (ArrayInfo) cl;
         return "[" + getTypeString(ai.getComponentType());
      }
      else
      {
         return "L" + cl.getName().replace('.', '/') + ";";
      }
   }
}
