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
