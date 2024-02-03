package org.apache.commons.math3.util;

import org.apache.commons.math3.exception.ConvergenceException;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.util.LocalizedFormats;

public abstract class ContinuedFraction {
   private static final double DEFAULT_EPSILON = 1.0E-8D;

   protected ContinuedFraction() {
   }

   protected abstract double getA(int var1, double var2);

   protected abstract double getB(int var1, double var2);

   public double evaluate(double x) throws ConvergenceException {
      return this.evaluate(x, 1.0E-8D, Integer.MAX_VALUE);
   }

   public double evaluate(double x, double epsilon) throws ConvergenceException {
      return this.evaluate(x, epsilon, Integer.MAX_VALUE);
   }

   public double evaluate(double x, int maxIterations) throws ConvergenceException, MaxCountExceededException {
      return this.evaluate(x, 1.0E-8D, maxIterations);
   }

   public double evaluate(double x, double epsilon, int maxIterations) throws ConvergenceException, MaxCountExceededException {
      double small = 1.0E-50D;
      double hPrev = this.getA(0, x);
      if (Precision.equals(hPrev, 0.0D, 1.0E-50D)) {
         hPrev = 1.0E-50D;
      }

      int n = 1;
      double dPrev = 0.0D;
      double cPrev = hPrev;
      double hN = hPrev;

      while(true) {
         if (n < maxIterations) {
            double a = this.getA(n, x);
            double b = this.getB(n, x);
            double dN = a + b * dPrev;
            if (Precision.equals(dN, 0.0D, 1.0E-50D)) {
               dN = 1.0E-50D;
            }

            double cN = a + b / cPrev;
            if (Precision.equals(cN, 0.0D, 1.0E-50D)) {
               cN = 1.0E-50D;
            }

            dN = 1.0D / dN;
            double deltaN = cN * dN;
            hN = hPrev * deltaN;
            if (Double.isInfinite(hN)) {
               throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_INFINITY_DIVERGENCE, new Object[]{x});
            }

            if (Double.isNaN(hN)) {
               throw new ConvergenceException(LocalizedFormats.CONTINUED_FRACTION_NAN_DIVERGENCE, new Object[]{x});
            }

            if (!(FastMath.abs(deltaN - 1.0D) < epsilon)) {
               dPrev = dN;
               cPrev = cN;
               hPrev = hN;
               ++n;
               continue;
            }
         }

         if (n >= maxIterations) {
            throw new MaxCountExceededException(LocalizedFormats.NON_CONVERGENT_CONTINUED_FRACTION, maxIterations, new Object[]{x});
         }

         return hN;
      }
   }
}
