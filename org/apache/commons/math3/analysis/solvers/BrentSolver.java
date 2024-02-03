package org.apache.commons.math3.analysis.solvers;

import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NumberIsTooLargeException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

public class BrentSolver extends AbstractUnivariateSolver {
   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;

   public BrentSolver() {
      this(1.0E-6D);
   }

   public BrentSolver(double absoluteAccuracy) {
      super(absoluteAccuracy);
   }

   public BrentSolver(double relativeAccuracy, double absoluteAccuracy) {
      super(relativeAccuracy, absoluteAccuracy);
   }

   public BrentSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy) {
      super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
   }

   protected double doSolve() throws NoBracketingException, TooManyEvaluationsException, NumberIsTooLargeException {
      double min = this.getMin();
      double max = this.getMax();
      double initial = this.getStartValue();
      double functionValueAccuracy = this.getFunctionValueAccuracy();
      this.verifySequence(min, initial, max);
      double yInitial = this.computeObjectiveValue(initial);
      if (FastMath.abs(yInitial) <= functionValueAccuracy) {
         return initial;
      } else {
         double yMin = this.computeObjectiveValue(min);
         if (FastMath.abs(yMin) <= functionValueAccuracy) {
            return min;
         } else if (yInitial * yMin < 0.0D) {
            return this.brent(min, initial, yMin, yInitial);
         } else {
            double yMax = this.computeObjectiveValue(max);
            if (FastMath.abs(yMax) <= functionValueAccuracy) {
               return max;
            } else if (yInitial * yMax < 0.0D) {
               return this.brent(initial, max, yInitial, yMax);
            } else {
               throw new NoBracketingException(min, max, yMin, yMax);
            }
         }
      }
   }

   private double brent(double lo, double hi, double fLo, double fHi) {
      double a = lo;
      double fa = fLo;
      double b = hi;
      double fb = fHi;
      double c = lo;
      double fc = fLo;
      double d = hi - lo;
      double e = d;
      double t = this.getAbsoluteAccuracy();
      double eps = this.getRelativeAccuracy();

      while(true) {
         if (FastMath.abs(fc) < FastMath.abs(fb)) {
            a = b;
            b = c;
            c = a;
            fa = fb;
            fb = fc;
            fc = fa;
         }

         double tol = 2.0D * eps * FastMath.abs(b) + t;
         double m = 0.5D * (c - b);
         if (FastMath.abs(m) <= tol || Precision.equals(fb, 0.0D)) {
            return b;
         }

         if (!(FastMath.abs(e) < tol) && !(FastMath.abs(fa) <= FastMath.abs(fb))) {
            double s = fb / fa;
            double p;
            double q;
            if (a == c) {
               p = 2.0D * m * s;
               q = 1.0D - s;
            } else {
               q = fa / fc;
               double r = fb / fc;
               p = s * (2.0D * m * q * (q - r) - (b - a) * (r - 1.0D));
               q = (q - 1.0D) * (r - 1.0D) * (s - 1.0D);
            }

            if (p > 0.0D) {
               q = -q;
            } else {
               p = -p;
            }

            s = e;
            e = d;
            if (!(p >= 1.5D * m * q - FastMath.abs(tol * q)) && !(p >= FastMath.abs(0.5D * s * q))) {
               d = p / q;
            } else {
               d = m;
               e = m;
            }
         } else {
            d = m;
            e = m;
         }

         a = b;
         fa = fb;
         if (FastMath.abs(d) > tol) {
            b += d;
         } else if (m > 0.0D) {
            b += tol;
         } else {
            b -= tol;
         }

         fb = this.computeObjectiveValue(b);
         if (fb > 0.0D && fc > 0.0D || fb <= 0.0D && fc <= 0.0D) {
            c = a;
            fc = fa;
            d = b - a;
            e = d;
         }
      }
   }
}
