package org.apache.commons.math3.analysis.solvers;

import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.util.FastMath;

public class RiddersSolver extends AbstractUnivariateSolver {
   private static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6D;

   public RiddersSolver() {
      this(1.0E-6D);
   }

   public RiddersSolver(double absoluteAccuracy) {
      super(absoluteAccuracy);
   }

   public RiddersSolver(double relativeAccuracy, double absoluteAccuracy) {
      super(relativeAccuracy, absoluteAccuracy);
   }

   protected double doSolve() throws TooManyEvaluationsException, NoBracketingException {
      double min = this.getMin();
      double max = this.getMax();
      double x1 = min;
      double y1 = this.computeObjectiveValue(min);
      double x2 = max;
      double y2 = this.computeObjectiveValue(max);
      if (y1 == 0.0D) {
         return min;
      } else if (y2 == 0.0D) {
         return max;
      } else {
         this.verifyBracketing(min, max);
         double absoluteAccuracy = this.getAbsoluteAccuracy();
         double functionValueAccuracy = this.getFunctionValueAccuracy();
         double relativeAccuracy = this.getRelativeAccuracy();
         double oldx = Double.POSITIVE_INFINITY;

         while(true) {
            double x3 = 0.5D * (x1 + x2);
            double y3 = this.computeObjectiveValue(x3);
            if (FastMath.abs(y3) <= functionValueAccuracy) {
               return x3;
            }

            double delta = 1.0D - y1 * y2 / (y3 * y3);
            double correction = FastMath.signum(y2) * FastMath.signum(y3) * (x3 - x1) / FastMath.sqrt(delta);
            double x = x3 - correction;
            double y = this.computeObjectiveValue(x);
            double tolerance = FastMath.max(relativeAccuracy * FastMath.abs(x), absoluteAccuracy);
            if (FastMath.abs(x - oldx) <= tolerance) {
               return x;
            }

            if (FastMath.abs(y) <= functionValueAccuracy) {
               return x;
            }

            if (correction > 0.0D) {
               if (FastMath.signum(y1) + FastMath.signum(y) == 0.0D) {
                  x2 = x;
                  y2 = y;
               } else {
                  x1 = x;
                  x2 = x3;
                  y1 = y;
                  y2 = y3;
               }
            } else if (FastMath.signum(y2) + FastMath.signum(y) == 0.0D) {
               x1 = x;
               y1 = y;
            } else {
               x1 = x3;
               x2 = x;
               y1 = y3;
               y2 = y;
            }

            oldx = x;
         }
      }
   }
}
