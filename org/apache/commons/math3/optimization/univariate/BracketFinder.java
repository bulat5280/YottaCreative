package org.apache.commons.math3.optimization.univariate;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.exception.MaxCountExceededException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.TooManyEvaluationsException;
import org.apache.commons.math3.optimization.GoalType;
import org.apache.commons.math3.util.Incrementor;

/** @deprecated */
@Deprecated
public class BracketFinder {
   private static final double EPS_MIN = 1.0E-21D;
   private static final double GOLD = 1.618034D;
   private final double growLimit;
   private final Incrementor evaluations;
   private double lo;
   private double hi;
   private double mid;
   private double fLo;
   private double fHi;
   private double fMid;

   public BracketFinder() {
      this(100.0D, 50);
   }

   public BracketFinder(double growLimit, int maxEvaluations) {
      this.evaluations = new Incrementor();
      if (growLimit <= 0.0D) {
         throw new NotStrictlyPositiveException(growLimit);
      } else if (maxEvaluations <= 0) {
         throw new NotStrictlyPositiveException(maxEvaluations);
      } else {
         this.growLimit = growLimit;
         this.evaluations.setMaximalCount(maxEvaluations);
      }
   }

   public void search(UnivariateFunction func, GoalType goal, double xA, double xB) {
      boolean isMinim;
      double fA;
      double fB;
      double xC;
      label86: {
         this.evaluations.resetCount();
         isMinim = goal == GoalType.MINIMIZE;
         fA = this.eval(func, xA);
         fB = this.eval(func, xB);
         if (isMinim) {
            if (!(fA < fB)) {
               break label86;
            }
         } else if (!(fA > fB)) {
            break label86;
         }

         xC = xA;
         xA = xB;
         xB = xC;
         xC = fA;
         fA = fB;
         fB = xC;
      }

      xC = xB + 1.618034D * (xB - xA);
      double fC = this.eval(func, xC);

      double tmp;
      while(true) {
         if (isMinim) {
            if (!(fC < fB)) {
               break;
            }
         } else if (!(fC > fB)) {
            break;
         }

         double w;
         double fW;
         label76: {
            tmp = (xB - xA) * (fB - fC);
            double tmp2 = (xB - xC) * (fB - fA);
            double val = tmp2 - tmp;
            double denom = Math.abs(val) < 1.0E-21D ? 2.0E-21D : 2.0D * val;
            w = xB - ((xB - xC) * tmp2 - (xB - xA) * tmp) / denom;
            double wLim = xB + this.growLimit * (xC - xB);
            if ((w - xC) * (xB - w) > 0.0D) {
               label91: {
                  fW = this.eval(func, w);
                  if (isMinim) {
                     if (fW < fC) {
                        break label76;
                     }
                  } else if (fW > fC) {
                     break label76;
                  }

                  label72: {
                     if (isMinim) {
                        if (fW > fB) {
                           break label72;
                        }
                     } else if (fW < fB) {
                        break label72;
                     }

                     w = xC + 1.618034D * (xC - xB);
                     fW = this.eval(func, w);
                     break label91;
                  }

                  xC = w;
                  fC = fW;
                  break;
               }
            } else if ((w - wLim) * (wLim - xC) >= 0.0D) {
               w = wLim;
               fW = this.eval(func, wLim);
            } else if ((w - wLim) * (xC - w) > 0.0D) {
               label58: {
                  fW = this.eval(func, w);
                  if (isMinim) {
                     if (!(fW < fC)) {
                        break label58;
                     }
                  } else if (!(fW > fC)) {
                     break label58;
                  }

                  xB = xC;
                  xC = w;
                  w += 1.618034D * (w - xB);
                  fB = fC;
                  fC = fW;
                  fW = this.eval(func, w);
               }
            } else {
               w = xC + 1.618034D * (xC - xB);
               fW = this.eval(func, w);
            }

            xA = xB;
            fA = fB;
            xB = xC;
            fB = fC;
            xC = w;
            fC = fW;
            continue;
         }

         xA = xB;
         xB = w;
         fA = fB;
         fB = fW;
         break;
      }

      this.lo = xA;
      this.fLo = fA;
      this.mid = xB;
      this.fMid = fB;
      this.hi = xC;
      this.fHi = fC;
      if (this.lo > this.hi) {
         tmp = this.lo;
         this.lo = this.hi;
         this.hi = tmp;
         tmp = this.fLo;
         this.fLo = this.fHi;
         this.fHi = tmp;
      }

   }

   public int getMaxEvaluations() {
      return this.evaluations.getMaximalCount();
   }

   public int getEvaluations() {
      return this.evaluations.getCount();
   }

   public double getLo() {
      return this.lo;
   }

   public double getFLo() {
      return this.fLo;
   }

   public double getHi() {
      return this.hi;
   }

   public double getFHi() {
      return this.fHi;
   }

   public double getMid() {
      return this.mid;
   }

   public double getFMid() {
      return this.fMid;
   }

   private double eval(UnivariateFunction f, double x) {
      try {
         this.evaluations.incrementCount();
      } catch (MaxCountExceededException var5) {
         throw new TooManyEvaluationsException(var5.getMax());
      }

      return f.value(x);
   }
}
