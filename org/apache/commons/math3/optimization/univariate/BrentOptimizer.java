package org.apache.commons.math3.optimization.univariate;

import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.optimization.ConvergenceChecker;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.util.FastMath;
import org.apache.commons.math3.util.Precision;

/** @deprecated */
@Deprecated
public class BrentOptimizer extends BaseAbstractUnivariateOptimizer {
   private static final double GOLDEN_SECTION = 0.5D * (3.0D - FastMath.sqrt(5.0D));
   private static final double MIN_RELATIVE_TOLERANCE = 2.0D * FastMath.ulp(1.0D);
   private final double relativeThreshold;
   private final double absoluteThreshold;

   public BrentOptimizer(double rel, double abs, ConvergenceChecker<UnivariatePointValuePair> checker) {
      super(checker);
      if (rel < MIN_RELATIVE_TOLERANCE) {
         throw new NumberIsTooSmallException(rel, MIN_RELATIVE_TOLERANCE, true);
      } else if (abs <= 0.0D) {
         throw new NotStrictlyPositiveException(abs);
      } else {
         this.relativeThreshold = rel;
         this.absoluteThreshold = abs;
      }
   }

   public BrentOptimizer(double rel, double abs) {
      this(rel, abs, (ConvergenceChecker)null);
   }

   protected UnivariatePointValuePair doOptimize() {
      boolean isMinim = this.getGoalType() == GoalType.MINIMIZE;
      double lo = this.getMin();
      double mid = this.getStartValue();
      double hi = this.getMax();
      ConvergenceChecker<UnivariatePointValuePair> checker = this.getConvergenceChecker();
      double a;
      double b;
      if (lo < hi) {
         a = lo;
         b = hi;
      } else {
         a = hi;
         b = lo;
      }

      double x = mid;
      double v = mid;
      double w = mid;
      double d = 0.0D;
      double e = 0.0D;
      double fx = this.computeObjectiveValue(mid);
      if (!isMinim) {
         fx = -fx;
      }

      double fv = fx;
      double fw = fx;
      UnivariatePointValuePair previous = null;
      UnivariatePointValuePair current = new UnivariatePointValuePair(mid, isMinim ? fx : -fx);
      UnivariatePointValuePair best = current;
      int iter = 0;

      while(true) {
         double m = 0.5D * (a + b);
         double tol1 = this.relativeThreshold * FastMath.abs(x) + this.absoluteThreshold;
         double tol2 = 2.0D * tol1;
         boolean stop = FastMath.abs(x - m) <= tol2 - 0.5D * (b - a);
         if (stop) {
            return this.best(best, this.best(previous, current, isMinim), isMinim);
         }

         double p = 0.0D;
         double q = 0.0D;
         double r = 0.0D;
         double u = 0.0D;
         if (FastMath.abs(e) > tol1) {
            r = (x - w) * (fx - fv);
            q = (x - v) * (fx - fw);
            p = (x - v) * q - (x - w) * r;
            q = 2.0D * (q - r);
            if (q > 0.0D) {
               p = -p;
            } else {
               q = -q;
            }

            r = e;
            e = d;
            if (p > q * (a - x) && p < q * (b - x) && FastMath.abs(p) < FastMath.abs(0.5D * q * r)) {
               d = p / q;
               u = x + d;
               if (u - a < tol2 || b - u < tol2) {
                  if (x <= m) {
                     d = tol1;
                  } else {
                     d = -tol1;
                  }
               }
            } else {
               if (x < m) {
                  e = b - x;
               } else {
                  e = a - x;
               }

               d = GOLDEN_SECTION * e;
            }
         } else {
            if (x < m) {
               e = b - x;
            } else {
               e = a - x;
            }

            d = GOLDEN_SECTION * e;
         }

         if (FastMath.abs(d) < tol1) {
            if (d >= 0.0D) {
               u = x + tol1;
            } else {
               u = x - tol1;
            }
         } else {
            u = x + d;
         }

         double fu = this.computeObjectiveValue(u);
         if (!isMinim) {
            fu = -fu;
         }

         previous = current;
         current = new UnivariatePointValuePair(u, isMinim ? fu : -fu);
         best = this.best(best, this.best(previous, current, isMinim), isMinim);
         if (checker != null && checker.converged(iter, previous, current)) {
            return best;
         }

         if (fu <= fx) {
            if (u < x) {
               b = x;
            } else {
               a = x;
            }

            v = w;
            fv = fw;
            w = x;
            fw = fx;
            x = u;
            fx = fu;
         } else {
            if (u < x) {
               a = u;
            } else {
               b = u;
            }

            if (!(fu <= fw) && !Precision.equals(w, x)) {
               if (fu <= fv || Precision.equals(v, x) || Precision.equals(v, w)) {
                  v = u;
                  fv = fu;
               }
            } else {
               v = w;
               fv = fw;
               w = u;
               fw = fu;
            }
         }

         ++iter;
      }
   }

   private UnivariatePointValuePair best(UnivariatePointValuePair a, UnivariatePointValuePair b, boolean isMinim) {
      if (a == null) {
         return b;
      } else if (b == null) {
         return a;
      } else if (isMinim) {
         return a.getValue() <= b.getValue() ? a : b;
      } else {
         return a.getValue() >= b.getValue() ? a : b;
      }
   }
}
