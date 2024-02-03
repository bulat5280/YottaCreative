package org.apache.commons.math3.analysis.function;

import org.apache.commons.math3.analysis.DifferentiableUnivariateFunction;
import org.apache.commons.math3.analysis.FunctionUtils;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.DerivativeStructure;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.util.FastMath;

public class Sinc implements UnivariateDifferentiableFunction, DifferentiableUnivariateFunction {
   private static final double SHORTCUT = 0.006D;
   private final boolean normalized;

   public Sinc() {
      this(false);
   }

   public Sinc(boolean normalized) {
      this.normalized = normalized;
   }

   public double value(double x) {
      double scaledX = this.normalized ? 3.141592653589793D * x : x;
      if (FastMath.abs(scaledX) <= 0.006D) {
         double scaledX2 = scaledX * scaledX;
         return ((scaledX2 - 20.0D) * scaledX2 + 120.0D) / 120.0D;
      } else {
         return FastMath.sin(scaledX) / scaledX;
      }
   }

   /** @deprecated */
   @Deprecated
   public UnivariateFunction derivative() {
      return FunctionUtils.toDifferentiableUnivariateFunction(this).derivative();
   }

   public DerivativeStructure value(DerivativeStructure t) throws DimensionMismatchException {
      double scaledX = (this.normalized ? 3.141592653589793D : 1.0D) * t.getValue();
      double scaledX2 = scaledX * scaledX;
      double[] f = new double[t.getOrder() + 1];
      double inv;
      if (FastMath.abs(scaledX) <= 0.006D) {
         for(int i = 0; i < f.length; ++i) {
            int k = i / 2;
            if ((i & 1) == 0) {
               f[i] = (double)((k & 1) == 0 ? 1 : -1) * (1.0D / (double)(i + 1) - scaledX2 * (1.0D / (double)(2 * i + 6) - scaledX2 / (double)(24 * i + 120)));
            } else {
               f[i] = ((k & 1) == 0 ? -scaledX : scaledX) * (1.0D / (double)(i + 2) - scaledX2 * (1.0D / (double)(6 * i + 24) - scaledX2 / (double)(120 * i + 720)));
            }
         }
      } else {
         inv = 1.0D / scaledX;
         double cos = FastMath.cos(scaledX);
         double sin = FastMath.sin(scaledX);
         f[0] = inv * sin;
         double[] sc = new double[f.length];
         sc[0] = 1.0D;
         double coeff = inv;

         for(int n = 1; n < f.length; ++n) {
            double s = 0.0D;
            double c = 0.0D;
            int kStart;
            if ((n & 1) == 0) {
               sc[n] = 0.0D;
               kStart = n;
            } else {
               sc[n] = sc[n - 1];
               c = sc[n];
               kStart = n - 1;
            }

            for(int k = kStart; k > 1; k -= 2) {
               sc[k] = (double)(k - n) * sc[k] - sc[k - 1];
               s = s * scaledX2 + sc[k];
               sc[k - 1] = (double)(k - 1 - n) * sc[k - 1] + sc[k - 2];
               c = c * scaledX2 + sc[k - 1];
            }

            sc[0] *= (double)(-n);
            s = s * scaledX2 + sc[0];
            coeff *= inv;
            f[n] = coeff * (s * sin + c * scaledX * cos);
         }
      }

      if (this.normalized) {
         inv = 3.141592653589793D;

         for(int i = 1; i < f.length; ++i) {
            f[i] *= inv;
            inv *= 3.141592653589793D;
         }
      }

      return t.compose(f);
   }
}
