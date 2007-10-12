package org.jboss.test.metadata.spi.signature;

import java.lang.reflect.Method;

import junit.framework.Test;
import org.jboss.metadata.spi.signature.Signature;
import org.jboss.test.ContainerTest;

/**
 * @author genman
 */
public class SignatureTestCase extends ContainerTest
{
   public SignatureTestCase(String name)
   {
      super(name);
   }

   public static Test suite()
   {
      return suite(SignatureTestCase.class);
   }

   public void testArray() throws Exception
   {
      Method method = getClass().getMethod("method", String.class, byte[].class, Byte[].class, String[][].class);
      Signature s = getSignature(method);
      Class<?>[] parametersTypes = s.getParametersTypes(getClass());
      assertEquals(4, parametersTypes.length);

      String p[] = { String.class.getName(), byte[].class.getName(), Byte[].class.getName(), String[][].class.getName()};
      Signature s2 = new Signature("method", p);
      Class<?>[] parametersTypes2 = s2.getParametersTypes(getClass());
      assertEquals(parametersTypes, parametersTypes2);
   }

   // here for a reason - this test uses it
   public void method(String s, byte b[], Byte b2[], String[][] sa)
   {
   }

   protected Signature getSignature(Method method)
   {
      return new Signature(method.getName(), method.getParameterTypes());
   }
}
