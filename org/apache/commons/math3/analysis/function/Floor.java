package org.apache.commons.math3.analysis.function;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.util.FastMath;

public class Floor implements UnivariateFunction {
   public double value(double x) {
      return FastMath.floor(x);
   }
}
