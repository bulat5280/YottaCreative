package org.apache.commons.math3.analysis.function;

import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.util.FastMath;

public class Log10 implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction {
   public double value(double x) {
      return FastMath.log10(x);
   }

   /** @deprecated */
   @Deprecated
   public UnivariateFunction derivative() {
      return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
   }

   public DerivativeStructure value(DerivativeStructure t) {
      return t.log10();
   }
}
