package org.apache.commons.math3.analysis.function;

import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;

public class Constant implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction {
   private final double c;

   public Constant(double c) {
      this.c = c;
   }

   public double value(double x) {
      return this.c;
   }

   /** @deprecated */
   @Deprecated
   public DifferentiableUnivariateFunction derivative() {
      return new Constant(0.0D);
   }

   public DerivativeStructure value(DerivativeStructure t) {
      return new DerivativeStructure(t.getFreeParameters(), t.getOrder(), this.c);
   }
}
