package org.apache.commons.math3.dfp;

import org.apache.commons.math3.analysis.solvers.AllowedSolution;
import org.apache.commons.math3.exception.MathInternalError;
import org.apache.commons.math3.exception.NoBracketingException;
import org.apache.commons.math3.exception.NullArgumentException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;
import org.apache.commons.math3.util.Incrementor;
import org.apache.commons.math3.util.MathUtils;

public class BracketingNthOrderBrentSolverDFP {
   private static final int MAXIMAL_AGING = 2;
   private final int maximalOrder;
   private final Dfp functionValueAccuracy;
   private final Dfp absoluteAccuracy;
   private final Dfp relativeAccuracy;
   private final Incrementor evaluations = new Incrementor();

   public BracketingNthOrderBrentSolverDFP(Dfp relativeAccuracy, Dfp absoluteAccuracy, Dfp functionValueAccuracy, int maximalOrder) throws NumberIsTooSmallException {
      if (maximalOrder < 2) {
         throw new NumberIsTooSmallException(maximalOrder, 2, true);
      } else {
         this.maximalOrder = maximalOrder;
         this.absoluteAccuracy = absoluteAccuracy;
         this.relativeAccuracy = relativeAccuracy;
         this.functionValueAccuracy = functionValueAccuracy;
      }
   }

   public int getMaximalOrder() {
      return this.maximalOrder;
   }

   public int getMaxEvaluations() {
      return this.evaluations.getMaximalCount();
   }

   public int getEvaluations() {
      return this.evaluations.getCount();
   }

   public Dfp getAbsoluteAccuracy() {
      return this.absoluteAccuracy;
   }

   public Dfp getRelativeAccuracy() {
      return this.relativeAccuracy;
   }

   public Dfp getFunctionValueAccuracy() {
      return this.functionValueAccuracy;
   }

   public Dfp solve(int maxEval, UnivariateDfpFunction f, Dfp min, Dfp max, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
      return this.solve(maxEval, f, min, max, min.add(max).divide(2), allowedSolution);
   }

   public Dfp solve(int maxEval, UnivariateDfpFunction f, Dfp min, Dfp max, Dfp startValue, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
      MathUtils.checkNotNull(f);
      this.evaluations.setMaximalCount(maxEval);
      this.evaluations.resetCount();
      Dfp zero = startValue.getZero();
      Dfp nan = zero.newInstance((byte)1, (byte)3);
      Dfp[] x = new Dfp[this.maximalOrder + 1];
      Dfp[] y = new Dfp[this.maximalOrder + 1];
      x[0] = min;
      x[1] = startValue;
      x[2] = max;
      this.evaluations.incrementCount();
      y[1] = f.value(x[1]);
      if (y[1].isZero()) {
         return x[1];
      } else {
         this.evaluations.incrementCount();
         y[0] = f.value(x[0]);
         if (y[0].isZero()) {
            return x[0];
         } else {
            int nbPoints;
            int signChangeIndex;
            if (y[0].multiply(y[1]).negativeOrNull()) {
               nbPoints = 2;
               signChangeIndex = 1;
            } else {
               this.evaluations.incrementCount();
               y[2] = f.value(x[2]);
               if (y[2].isZero()) {
                  return x[2];
               }

               if (!y[1].multiply(y[2]).negativeOrNull()) {
                  throw new NoBracketingException(x[0].toDouble(), x[2].toDouble(), y[0].toDouble(), y[2].toDouble());
               }

               nbPoints = 3;
               signChangeIndex = 2;
            }

            Dfp[] tmpX = new Dfp[x.length];
            Dfp xA = x[signChangeIndex - 1];
            Dfp yA = y[signChangeIndex - 1];
            Dfp absXA = xA.abs();
            Dfp absYA = yA.abs();
            int agingA = 0;
            Dfp xB = x[signChangeIndex];
            Dfp yB = y[signChangeIndex];
            Dfp absXB = xB.abs();
            Dfp absYB = yB.abs();
            int agingB = 0;

            while(true) {
               Dfp maxX = absXA.lessThan(absXB) ? absXB : absXA;
               Dfp maxY = absYA.lessThan(absYB) ? absYB : absYA;
               Dfp xTol = this.absoluteAccuracy.add(this.relativeAccuracy.multiply(maxX));
               if (xB.subtract(xA).subtract(xTol).negativeOrNull() || maxY.lessThan(this.functionValueAccuracy)) {
                  switch(allowedSolution) {
                  case ANY_SIDE:
                     return absYA.lessThan(absYB) ? xA : xB;
                  case LEFT_SIDE:
                     return xA;
                  case RIGHT_SIDE:
                     return xB;
                  case BELOW_SIDE:
                     return yA.lessThan(zero) ? xA : xB;
                  case ABOVE_SIDE:
                     return yA.lessThan(zero) ? xB : xA;
                  default:
                     throw new MathInternalError((Throwable)null);
                  }
               }

               Dfp targetY;
               if (agingA >= 2) {
                  targetY = yB.divide(16).negate();
               } else if (agingB >= 2) {
                  targetY = yA.divide(16).negate();
               } else {
                  targetY = zero;
               }

               int start = 0;
               int end = nbPoints;

               Dfp nextX;
               do {
                  System.arraycopy(x, start, tmpX, start, end - start);
                  nextX = this.guessX(targetY, tmpX, y, start, end);
                  if (!nextX.greaterThan(xA) || !nextX.lessThan(xB)) {
                     if (signChangeIndex - start >= end - signChangeIndex) {
                        ++start;
                     } else {
                        --end;
                     }

                     nextX = nan;
                  }
               } while(nextX.isNaN() && end - start > 1);

               if (nextX.isNaN()) {
                  nextX = xA.add(xB.subtract(xA).divide(2));
                  start = signChangeIndex - 1;
                  end = signChangeIndex;
               }

               this.evaluations.incrementCount();
               Dfp nextY = f.value(nextX);
               if (nextY.isZero()) {
                  return nextX;
               }

               if (nbPoints > 2 && end - start != nbPoints) {
                  nbPoints = end - start;
                  System.arraycopy(x, start, x, 0, nbPoints);
                  System.arraycopy(y, start, y, 0, nbPoints);
                  signChangeIndex -= start;
               } else if (nbPoints == x.length) {
                  --nbPoints;
                  if (signChangeIndex >= (x.length + 1) / 2) {
                     System.arraycopy(x, 1, x, 0, nbPoints);
                     System.arraycopy(y, 1, y, 0, nbPoints);
                     --signChangeIndex;
                  }
               }

               System.arraycopy(x, signChangeIndex, x, signChangeIndex + 1, nbPoints - signChangeIndex);
               x[signChangeIndex] = nextX;
               System.arraycopy(y, signChangeIndex, y, signChangeIndex + 1, nbPoints - signChangeIndex);
               y[signChangeIndex] = nextY;
               ++nbPoints;
               if (nextY.multiply(yA).negativeOrNull()) {
                  xB = nextX;
                  yB = nextY;
                  absYB = nextY.abs();
                  ++agingA;
                  agingB = 0;
               } else {
                  xA = nextX;
                  yA = nextY;
                  absYA = nextY.abs();
                  agingA = 0;
                  ++agingB;
                  ++signChangeIndex;
               }
            }
         }
      }
   }

   private Dfp guessX(Dfp targetY, Dfp[] x, Dfp[] y, int start, int end) {
      int j;
      for(int i = start; i < end - 1; ++i) {
         j = i + 1 - start;

         for(int j = end - 1; j > i; --j) {
            x[j] = x[j].subtract(x[j - 1]).divide(y[j].subtract(y[j - j]));
         }
      }

      Dfp x0 = targetY.getZero();

      for(j = end - 1; j >= start; --j) {
         x0 = x[j].add(x0.multiply(targetY.subtract(y[j])));
      }

      return x0;
   }
}
