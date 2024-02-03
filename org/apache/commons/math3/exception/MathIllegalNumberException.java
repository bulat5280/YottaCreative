package org.apache.commons.math3.exception;

import org.apache.commons.math3.exception.util.Localizable;

public class MathIllegalNumberException extends MathIllegalArgumentException {
   private static final long serialVersionUID = -7447085893598031110L;
   private final Number argument;

   protected MathIllegalNumberException(Localizable pattern, Number wrong, Object... arguments) {
      super(pattern, wrong, arguments);
      this.argument = wrong;
   }

   public Number getArgument() {
      return this.argument;
   }
}
