package org.jboss.reflect.plugins.javassist.expr;

import org.jboss.reflect.spi.CannotCompileException;
import org.jboss.reflect.spi.MutableClassInfo;
import org.jboss.reflect.spi.NotFoundException;

public interface Cast extends Expression
{
   
   /**
    * Returns the <code>CtClass</code> object representing
    * the type specified by the cast.
    * 
    * @return
    * @throws NotFoundException
    */
   public MutableClassInfo getType() throws NotFoundException;

   /**
    * Replaces the explicit cast operator with the bytecode derived from
    * the given source text.
    */
   public void replace(String statement) throws CannotCompileException;
   
}
