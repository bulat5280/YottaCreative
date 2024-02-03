package org.jooq.types;

import java.math.BigInteger;

public abstract class UNumber extends Number {
   private static final long serialVersionUID = -7666221938815339843L;

   public BigInteger toBigInteger() {
      return new BigInteger(this.toString());
   }
}
