package org.jboss.test.metadata.spi.signature;

import java.lang.reflect.Method;
import java.util.Arrays;

import org.jboss.metadata.spi.signature.Signature;

import junit.framework.TestCase;

public class SignatureTest extends TestCase
{

   public void testArray() throws Exception
   {
      Method method = getClass().getMethod("method", String.class, byte[].class, Byte[].class, String[][].class);
      Signature s = new Signature(method);
      Class<?>[] parametersTypes = s.getParametersTypes(getClass());
      assertEquals(4, parametersTypes.length);

      String p[] = { String.class.getName(), byte[].class.getName(), Byte[].class.getName(), String[][].class.getName()};
      Signature s2 = new Signature("method", p);
      Class<?>[] parametersTypes2 = s2.getParametersTypes(getClass());
      assert Arrays.equals(parametersTypes, parametersTypes2);
   }

   public void method(String s, byte b[], Byte b2[], String[][] sa)
   {
   }

}
