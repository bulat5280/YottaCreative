package org.apache.commons.math3.analysis.function;

import java.util.Arrays;
import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.ParametricUnivariateFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.util.FastMath;

public class Sigmoid implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction {
   private final double lo;
   private final double hi;

   public Sigmoid() {
      this(0.0D, 1.0D);
   }

   public Sigmoid(double lo, double hi) {
      this.lo = lo;
      this.hi = hi;
   }

   /** @deprecated */
   @Deprecated
   public UnivariateFunction derivative() {
      return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
   }

   public double value(double x) {
      return value(x, this.lo, this.hi);
   }

   private static double value(double x, double lo, double hi) {
      return lo + (hi - lo) / (1.0D + FastMath.exp(-x));
   }

   public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
      double[] f = new double[t.getOrder() + 1];
      double exp = FastMath.exp(-t.getValue());
      if (Double.isInfinite(exp)) {
         f[0] = this.lo;
         Arrays.fill(f, 1, f.length, 0.0D);
      } else {
         double[] p = new double[f.length];
         double inv = 1.0D / (1.0D + exp);
         double coeff = this.hi - this.lo;

         for(int n = 0; n < f.length; ++n) {
            double v = 0.0D;
            p[n] = 1.0D;

            for(int k = n; k >= 0; --k) {
               v = v * exp + p[k];
               if (k > 1) {
                  p[k - 1] = (double)(n - k + 2) * p[k - 2] - (double)(k - 1) * p[k - 1];
               } else {
                  p[0] = 0.0D;
               }
            }

            coeff *= inv;
            f[n] = coeff * v;
         }

         f[0] += this.lo;
      }

      return t.compose(f);
   }

   public static class Parametric implements ParametricUnivariateFunction {
      public double value(double x, double... param) throws NullArgumentException, DimensionMismatchException {
         this.validateParameters(param);
         return Sigmoid.value(x, param[0], param[1]);
      }

      public double[] gradient(double x, double... param) throws NullArgumentException, DimensionMismatchException {
         this.validateParameters(param);
         double invExp1 = 1.0D / (1.0D + FastMath.exp(-x));
         return new double[]{1.0D - invExp1, invExp1};
      }

      private void validateParameters(double[] param) throws NullArgumentException, DimensionMismatchException {
         if (param == null) {
            throw new NullArgumentException();
         } else if (param.length != 2) {
            throw new DimensionMismatchException(param.length, 2);
         }
      }
   }
}
