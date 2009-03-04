package org.jboss.reflect.plugins.javassist.expr;

import org.jboss.reflect.spi.MutableConstructorInfo;
import org.jboss.reflect.spi.NotFoundException;

public interface ConstructorCall extends MethodCall
{

   /**
    * Returns the called constructor.
    * 
    * @return
    * @throws NotFoundException
    */
   public MutableConstructorInfo getConstructor() throws NotFoundException;
}
